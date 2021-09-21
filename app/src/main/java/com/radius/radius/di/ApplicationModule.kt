package com.radius.radius.di

import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import com.radius.domain.Constant
import com.radius.domain.util.AppExecutionThread
import com.radius.domain.util.ExecutionThread
import com.radius.radius.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            clientBuilder.let {
                it.addInterceptor(loggingInterceptor)
                it.addInterceptor(OkHttpProfilerInterceptor())
            }
        }

        clientBuilder.let {
            it.connectTimeout(2, TimeUnit.MINUTES)
            it.readTimeout(2, TimeUnit.MINUTES)
            it.writeTimeout(2, TimeUnit.MINUTES)
            it.interceptors()
                .add(addHeaderInterceptor())
        }
        return clientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideJacksonConverter(): Converter.Factory = JacksonConverterFactory.create()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        jacksonConverter: Converter.Factory,
        baseURL: String
    ): Retrofit {
        return provideRetrofitClient(okHttpClient, jacksonConverter, baseURL)
    }

    private fun provideRetrofitClient(
        okHttpClient: OkHttpClient,
        jacksonConverter: Converter.Factory,
        baseURL: String
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(jacksonConverter)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(baseURL)
            .client(okHttpClient)
            .build()
    }

    private fun addHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original: Request = chain.request()

            // Customize the request
            val builder = original.newBuilder()
                .header("ContentEntity-Type", "application/json")
                .header("Accept-Charset", "UTF-8")
                .header("Accept", "application/json")


            val request = builder.method(original.method, original.body)
                .build()

            println(request.url.toString() + " URL OF REQUEST")

            val response = chain.proceed(request)

            response
        }
    }

    @Provides
    @Singleton
    fun providesAppBaseURL(): String = Constant.BASE_URL

    @Provides
    @Singleton
    fun provideExecutorThreadService(imp: AppExecutionThread): ExecutionThread = imp

}
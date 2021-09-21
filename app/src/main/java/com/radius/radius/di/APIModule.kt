package com.radius.radius.di

import com.radius.data.apis.FacilitiesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class APIModule {

    @Provides
    fun provideFacilityAPI(retrofit: Retrofit): FacilitiesApi =
        retrofit.create(FacilitiesApi::class.java)

}
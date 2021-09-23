package com.radius.radius.di

import com.radius.data.repository.FacilityLocalDataSourceImp
import com.radius.data.repository.FacilityRemoteDataSourceImp
import com.radius.data.repository.FacilityRepositoryImp
import com.radius.domain.repository.FacilityLocalDataSource
import com.radius.domain.repository.FacilityRemoteDataSource
import com.radius.domain.repository.FacilityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    fun provideFacilityRemoteDataSource(imp: FacilityRemoteDataSourceImp): FacilityRemoteDataSource = imp

    @Provides
    fun provideFacilityRepository(imp: FacilityRepositoryImp): FacilityRepository = imp

    @Provides
    fun provideFacilityLocalDataSource(imp: FacilityLocalDataSourceImp): FacilityLocalDataSource = imp

}
package com.radius.data.repository

import com.radius.data.mapper.FacilityOptionMapper
import com.radius.domain.model.business.FacilityData
import com.radius.domain.repository.FacilityRemoteDataSource
import com.radius.domain.repository.FacilityRepository
import com.radius.domain.util.ExecutionResult
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class FacilityRepositoryImp @Inject constructor(
    private val remoteDataSource: FacilityRemoteDataSource,
    private val facilityOptionMapper: FacilityOptionMapper
) : FacilityRepository {
    override fun getFacilityInfo(): Observable<ExecutionResult<FacilityData>> {
        return Observable.concat(Observable.just(ExecutionResult.progress(true)),
            remoteDataSource.getFacilityInfo()
                .map {
                    facilityOptionMapper.map(it)
                }
                .map {
                    it?.let {
                        ExecutionResult.success(it)
                    }
                },
            Observable.just(ExecutionResult.progress(false)))
    }
}
package com.radius.data.repository

import com.radius.domain.model.business.FacilityData
import com.radius.domain.repository.FacilityRemoteDataSource
import com.radius.domain.repository.FacilityRepository
import com.radius.domain.util.ExecutionResult
import io.reactivex.rxjava3.core.Observable

class FacilityRepositoryImp: FacilityRepository {
    override fun getFacilityInfo(): Observable<ExecutionResult<FacilityData>> {

    }
}
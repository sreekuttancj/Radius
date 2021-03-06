package com.radius.data.repository

import com.radius.data.apis.FacilitiesApi
import com.radius.domain.model.remote.FacilityRemoteData
import com.radius.domain.repository.FacilityRemoteDataSource
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class FacilityRemoteDataSourceImp @Inject constructor(private val facilityApi: FacilitiesApi): FacilityRemoteDataSource {
    override fun getFacilityInfo(): Observable<FacilityRemoteData> {
        return facilityApi.getFacilities()
    }
}
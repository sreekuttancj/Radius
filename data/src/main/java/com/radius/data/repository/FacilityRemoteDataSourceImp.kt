package com.radius.data.repository

import android.util.Log
import com.radius.data.apis.FacilitiesApi
import com.radius.domain.model.remote.FacilityRemoteData
import com.radius.domain.repository.FacilityRemoteDataSource
import io.reactivex.rxjava3.core.Observable
import java.util.*
import javax.inject.Inject

class FacilityRemoteDataSourceImp @Inject constructor(private val facilityApi: FacilitiesApi): FacilityRemoteDataSource {
    override fun getFacilityInfo(): Observable<FacilityRemoteData> {
        return facilityApi.getFacilities().also {
            Log.i("check_data","remote_data_source: $it")
        }
    }
}
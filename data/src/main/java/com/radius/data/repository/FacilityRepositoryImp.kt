package com.radius.data.repository

import android.util.Log
import com.radius.data.mapper.FacilityOptionMapper
import com.radius.domain.model.business.FacilityData
import com.radius.domain.repository.FacilityRemoteDataSource
import com.radius.domain.repository.FacilityRepository
import com.radius.domain.util.ExecutionResult
import com.radius.domain.util.NetworkConnection
import com.radius.domain.util.NetworkConnectionError
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class FacilityRepositoryImp @Inject constructor(
    private val remoteDataSource: FacilityRemoteDataSource,
    private val facilityOptionMapper: FacilityOptionMapper,
    private val networkConnection: NetworkConnection
) : FacilityRepository {
    override fun getFacilityInfo(): Observable<ExecutionResult<FacilityData>> {
        if (networkConnection.isInternetConnected) {
            return Observable.concat(Observable.just(ExecutionResult.progress(true)),
                remoteDataSource.getFacilityInfo()
                    .map {
                        Log.i("check_data", "remote_data_source: facility_data: $it")
                        facilityOptionMapper.map(it)
                    }
                    .map {
                        it?.let {
                            ExecutionResult.success(it)
                        }
                    },
                Observable.just(ExecutionResult.progress(false))
            )
        }
        return Observable.just(ExecutionResult.error(error = NetworkConnectionError()))
    }
}
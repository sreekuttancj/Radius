package com.radius.domain.repository

import com.radius.domain.model.remote.FacilityRemoteData
import io.reactivex.rxjava3.core.Observable

interface FacilityRemoteDataSource {
    fun getFacilityInfo():Observable<FacilityRemoteData>
}
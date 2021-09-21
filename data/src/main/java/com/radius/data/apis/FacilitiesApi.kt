package com.radius.data.apis

import com.radius.domain.Constant
import com.radius.domain.model.remote.FacilityRemoteData
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface FacilitiesApi {

    @GET(Constant.FACILITIES_URL)
    fun getFacilities(): Observable<FacilityRemoteData>
}
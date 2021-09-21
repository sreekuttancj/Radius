package com.radius.domain.repository

import com.radius.domain.model.business.FacilityData
import com.radius.domain.util.ExecutionResult
import io.reactivex.rxjava3.core.Observable

interface FacilityRepository {
    fun getFacilityInfo(): Observable<ExecutionResult<FacilityData>>
}
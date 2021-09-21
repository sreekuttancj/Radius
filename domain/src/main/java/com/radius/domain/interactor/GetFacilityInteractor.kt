package com.radius.domain.interactor

import com.radius.domain.model.business.FacilityData
import com.radius.domain.repository.FacilityRepository
import com.radius.domain.util.ExecutionResult
import com.radius.domain.util.ExecutionThread
import com.radius.domain.util.InteractorService
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetFacilityInteractor @Inject constructor(
    postExecutionThread: ExecutionThread,
    private val repository: FacilityRepository
): InteractorService <Unit, FacilityData>(postExecutionThread) {
    override fun getObservable(param: Unit): Observable<ExecutionResult<FacilityData>> {
        return repository.getFacilityInfo()
    }
}

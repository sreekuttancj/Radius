package com.radius.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radius.domain.interactor.GetFacilityInteractor
import com.radius.domain.model.business.FacilityData
import javax.inject.Inject

class FacilityViewModel @Inject constructor(
    private val getFacilityInteractor: GetFacilityInteractor
) : ViewModel() {

    private val facilityMutableLiveData = MutableLiveData<FacilityData>()
    val facilityLiveData: LiveData<FacilityData> = facilityMutableLiveData

    fun getFacilityInfo() {
        getFacilityInteractor.execute(
            param = Unit,
            onProgress = { progress, _ ->
            },
            onError = { error, _ ->
            }) {
            facilityMutableLiveData.value = it
        }
    }
}
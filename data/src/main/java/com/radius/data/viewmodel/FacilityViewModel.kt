package com.radius.data.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radius.domain.interactor.GetFacilityInteractor
import com.radius.domain.model.business.FacilityData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FacilityViewModel @Inject constructor(
    private val getFacilityInteractor: GetFacilityInteractor
) : ViewModel() {

    private val facilityMutableLiveData = MutableLiveData<FacilityData>()
    val facilityLiveData: LiveData<FacilityData> = facilityMutableLiveData

    fun getFacilityInfo() {
        Log.i("check_data","viewmodel: enter")


        getFacilityInteractor.execute(
            param = Unit,
            onProgress = { progress, _ ->
            },
            onError = { error, _ ->
            }) {
            facilityMutableLiveData.value = it

            Log.i("check_data","viewmodel: $it")
        }
    }
}
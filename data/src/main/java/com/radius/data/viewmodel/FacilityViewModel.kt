package com.radius.data.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radius.domain.interactor.GetFacilityInteractor
import com.radius.domain.model.business.Facility
import com.radius.domain.model.business.FacilityData
import com.radius.domain.model.business.FacilityOption
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FacilityViewModel @Inject constructor(
    private val getFacilityInteractor: GetFacilityInteractor
) : ViewModel() {

    private val facilityMutableLiveData = MutableLiveData<FacilityData>()
    val facilityLiveData: LiveData<FacilityData> = facilityMutableLiveData

    fun getFacilityInfo() {
        Log.i("check_data", "viewmodel: enter")


        getFacilityInteractor.execute(
            param = Unit,
            onProgress = { progress, _ ->
            },
            onError = { error, _ ->
            }) {
            facilityMutableLiveData.value = it

            Log.i("check_data", "viewmodel: $it")
        }
    }

    fun updateUserSelectedOption(option: FacilityOption) {
        val currentFacilities = facilityMutableLiveData.value?.facilitiesList?.toMutableList()
        var userSelectedFacility: Facility? = null
        var userSelectedOption: FacilityOption? = null

        val userSelectedFacilityIndex =
            currentFacilities?.indexOfFirst { it.id == option.facilityId }
        if (userSelectedFacilityIndex != null && userSelectedFacilityIndex != -1) {
            userSelectedFacility = currentFacilities[userSelectedFacilityIndex].copy()
        }

        val userSelectedOptionIndex =
            userSelectedFacility?.options?.indexOfFirst { it.id == option.id }
        if (userSelectedOptionIndex != null && userSelectedOptionIndex != -1) {
            userSelectedOption = userSelectedFacility?.options?.get(userSelectedOptionIndex)
        }

        var updatedFacility: Facility? = null
        val updatedOptionList = userSelectedFacility?.options?.toMutableList()

        userSelectedFacility?.options?.forEachIndexed { index, facilityOption ->
            if (facilityOption.id == userSelectedOption?.id) {
                if (facilityOption.isSelected) {
                    val updatedOption = facilityOption.copy(isSelected = false)
                    updatedOptionList?.removeAt(index)
                    updatedOptionList?.add(index, updatedOption)
                    updatedFacility = userSelectedFacility.copy(
                        options = updatedOptionList?.toList() ?: emptyList()
                    )
                } else {
                    val updatedOption = facilityOption.copy(isSelected = true)
                    updatedOptionList?.removeAt(index)
                    updatedOptionList?.add(index, updatedOption)
                    updatedFacility = userSelectedFacility.copy(
                        options = updatedOptionList?.toList() ?: emptyList()
                    )
                }
            } else if (facilityOption.isSelected) {
                val updatedOption = facilityOption.copy(isSelected = false)
                updatedOptionList?.removeAt(index)
                updatedOptionList?.add(index, updatedOption)
                updatedFacility =
                    userSelectedFacility.copy(options = updatedOptionList?.toList() ?: emptyList())
            }
        }

        var updatedCurrentFacilityData: FacilityData? = null

        if (userSelectedFacilityIndex != null && userSelectedFacilityIndex != -1) {
            currentFacilities.removeAt(userSelectedFacilityIndex)
            updatedFacility?.let { currentFacilities.add(userSelectedFacilityIndex, it) }
        }
        updatedCurrentFacilityData =
            currentFacilities?.let { facilityMutableLiveData.value?.copy(facilitiesList = it) }

        facilityMutableLiveData.value = updatedCurrentFacilityData
    }
}
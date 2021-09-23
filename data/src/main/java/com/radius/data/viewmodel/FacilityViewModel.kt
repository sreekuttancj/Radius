package com.radius.data.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.radius.data.BaseViewModel
import com.radius.domain.interactor.GetFacilityInteractor
import com.radius.domain.model.business.Facility
import com.radius.domain.model.business.FacilityData
import com.radius.domain.model.business.FacilityOption
import com.radius.domain.util.BaseProgress
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FacilityViewModel @Inject constructor(
    private val getFacilityInteractor: GetFacilityInteractor
) : BaseViewModel() {

    private val facilityMutableLiveData = MutableLiveData<FacilityData>()
    val facilityLiveData: LiveData<FacilityData> = facilityMutableLiveData

    fun getFacilityInfo() {

        getFacilityInteractor.execute(
            param = Unit,
            onProgress = { progress, _ ->
                _showActivityProgress.value = BaseProgress(inProgress = progress)
            },
            onError = { error, _ ->
                _showError.value = error
            }) {
            facilityMutableLiveData.value = it
        }
    }

    private val saveUserSelectedOptions = mutableListOf<FacilityOption>()

    private val invalidCombinationMutableLiveData = MutableLiveData<Unit>()
    val invalidCombinationLiveData:LiveData<Unit> = invalidCombinationMutableLiveData

    fun updateUserSelectedOption(option: FacilityOption) {
        if (isValidFacilityOptionCombination(option)) {

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
                    //When user uncheck the option
                    if (facilityOption.isSelected) {
                        val updatedOption = facilityOption.copy(isSelected = false)
                        updatedOptionList?.removeAt(index)
                        updatedOptionList?.add(index, updatedOption)
                        updatedFacility = userSelectedFacility.copy(
                            options = updatedOptionList?.toList() ?: emptyList()
                        )

                        val savedOptionIndex = saveUserSelectedOptions.indexOfFirst { it.facilityId == updatedOption.facilityId && it.id == updatedOption.id }
                        if (savedOptionIndex!=-1){
                            saveUserSelectedOptions.removeAt(savedOptionIndex)
                        }

                    } else {
                        //When user check the option
                        if (isValidFacilityOptionCombination(option)) {
                            val updatedOption = facilityOption.copy(isSelected = true)
                            updatedOptionList?.removeAt(index)
                            updatedOptionList?.add(index, updatedOption)
                            updatedFacility = userSelectedFacility.copy(
                                options = updatedOptionList?.toList() ?: emptyList()
                            )
                            saveUserSelectedOptions.add(updatedOption)
                        }
                    }
                } else if (facilityOption.isSelected) {
                    //uncheck all the other options
                    val updatedOption = facilityOption.copy(isSelected = false)
                    updatedOptionList?.removeAt(index)
                    updatedOptionList?.add(index, updatedOption)
                    updatedFacility =
                        userSelectedFacility.copy(
                            options = updatedOptionList?.toList() ?: emptyList()
                        )

                    val savedOptionIndex = saveUserSelectedOptions.indexOfFirst { it.facilityId == updatedOption.facilityId && it.id == updatedOption.id }
                    if (savedOptionIndex!=-1){
                        saveUserSelectedOptions.removeAt(savedOptionIndex)
                    }
                }
            }

            if (userSelectedFacilityIndex != null && userSelectedFacilityIndex != -1) {
                currentFacilities.removeAt(userSelectedFacilityIndex)
                updatedFacility?.let { currentFacilities.add(userSelectedFacilityIndex, it) }
            }

            val updatedCurrentFacilityData: FacilityData? =
                currentFacilities?.let { facilityMutableLiveData.value?.copy(facilitiesList = it) }

            facilityMutableLiveData.value = updatedCurrentFacilityData
        }else {
            invalidCombinationMutableLiveData.value = Unit
        }
    }

    private fun isValidFacilityOptionCombination(option: FacilityOption): Boolean{
        var isValidCombo = true
        option.exclusionGroup?.forEach { exclusionItem ->
            val isExcludedIndex = saveUserSelectedOptions.indexOfFirst { it.facilityId == exclusionItem.facilityId && it.id == exclusionItem.optionId}
            if ( isExcludedIndex==-1){
                isValidCombo = true
            }else {
                isValidCombo = false
                return isValidCombo
            }
        }

        return isValidCombo
    }
}
package com.radius.data.mapper

import com.radius.domain.model.business.*
import com.radius.domain.model.remote.FacilityRemoteData
import com.radius.domain.util.BaseMapper
import javax.inject.Inject

class FacilityOptionMapper @Inject constructor() : BaseMapper<FacilityRemoteData, FacilityData> {
    override fun map(data: FacilityRemoteData?): FacilityData {
        data?.let {
            val facilities = mutableListOf<Facility>()

            val exclusionList = mutableListOf<List<ExclusionItem>>()

            it.exclusionList?.forEach { exclusionListRemote ->
                val exclusionItemList = mutableListOf<ExclusionItem>()
                exclusionListRemote.forEach { exclusionItemRemote ->

                    val exclusionItem = ExclusionItem(
                        facilityId = exclusionItemRemote.facilityId,
                        optionId = exclusionItemRemote.optionId
                    )
                    exclusionItemList.add(exclusionItem)
                }

                exclusionList.add(exclusionItemList)
            }

            it.facilitiesList?.forEach { facilityRemote ->
                val facilityOptions = mutableListOf<FacilityOption>()

                facilityRemote.options.forEach { optionRemote ->
                    val option = FacilityOption(
                        id = optionRemote.id,
                        facilityId = facilityRemote.id,
                        name = optionRemote.name,
                        icon = if (optionRemote.icon == "no-room") "no_room" else optionRemote.icon
                    )
                    option.exclusionGroup = getOptionExclusionList(exclusionList, option)
                    facilityOptions.add(option)
                }
                val facility = Facility(
                    id = facilityRemote.id,
                    name = facilityRemote.name,
                    options = facilityOptions
                )
                facilities.add(facility)
            }

            return FacilityData(facilitiesList = facilities, exclusionList = exclusionList)
        }

        return FacilityData(facilitiesList = emptyList(), exclusionList = emptyList())
    }

    private fun getOptionExclusionList(
        exclusionList: List<List<ExclusionItem>>,
        option: FacilityOption
    ): List<ExclusionItem> {
        val optionExclusionList = mutableListOf<ExclusionItem>()
        exclusionList.forEach { group ->
            val currentOptionIndex = group.indexOfFirst { it.facilityId == option.facilityId && it.optionId == option.id }
            if (currentOptionIndex!=null && currentOptionIndex!=-1) {
                var isOptionPresent = false
                group.forEach {
                    if (it.facilityId == option.facilityId && it.optionId == option.id) {
                        isOptionPresent = true
                    }else{
                        isOptionPresent = false
                    }

                    if (!isOptionPresent) {
                        optionExclusionList.add(it)
                    }
                }
            }
        }
        return optionExclusionList
    }
}
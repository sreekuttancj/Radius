package com.radius.data.mapper

import com.radius.domain.model.business.ExclusionItem
import com.radius.domain.model.business.Facility
import com.radius.domain.model.business.FacilityData
import com.radius.domain.model.business.FacilityOption
import com.radius.domain.model.database.FacilityEntityData
import com.radius.domain.util.BaseMapper
import javax.inject.Inject

class FacilityOptionLocalMapper @Inject constructor() : BaseMapper<FacilityEntityData, FacilityData> {
    override fun map(data: FacilityEntityData?): FacilityData? {
        data?.let {
            val facilities = mutableListOf<Facility>()

            val exclusionList = mutableListOf<List<ExclusionItem>>()

            it.exclusionList.exclusionList?.forEach { exclusionListEntity ->
                val exclusionItemList = mutableListOf<ExclusionItem>()
                exclusionListEntity.forEach { exclusionItemRemote ->

                    val exclusionItem = ExclusionItem(
                        facilityId = exclusionItemRemote.facilityId,
                        optionId = exclusionItemRemote.optionId
                    )
                    exclusionItemList.add(exclusionItem)
                }

                exclusionList.add(exclusionItemList)
            }

            it?.facilitiesList?.forEach { facilityEntity ->
                val facilityOptions = mutableListOf<FacilityOption>()

                facilityEntity.options.forEach { optionRemote ->
                    val option = FacilityOption(
                        id = optionRemote.id,
                        facilityId = facilityEntity.facilityId,
                        name = optionRemote.name,
                        icon = if (optionRemote.icon == "no-room") "no_room" else optionRemote.icon
                    )
                    option.exclusionGroup = getOptionExclusionList(exclusionList, option)
                    facilityOptions.add(option)
                }
                val facility = Facility(
                    id = facilityEntity.facilityId,
                    name = facilityEntity.name,
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
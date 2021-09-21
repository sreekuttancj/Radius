package com.radius.data.mapper

import com.radius.domain.model.business.*
import com.radius.domain.model.remote.FacilityRemoteData
import com.radius.domain.util.BaseMapper
import javax.inject.Inject

class FacilityOptionMapper @Inject constructor() : BaseMapper<FacilityRemoteData, FacilityData> {
    override fun map(data: FacilityRemoteData?): FacilityData {
        data?.let {
            val facilityOptions = mutableListOf<FacilityOption>()
            val facilities = mutableListOf<Facility>()

            it.facilitiesList.forEach { facilityRemote ->
                facilityRemote.options.forEach { optionRemote ->
                    val option = FacilityOption(
                        id = optionRemote.id,
                        name = optionRemote.name,
                        icon = optionRemote.icon
                    )
                    facilityOptions.add(option)
                }
                val facility = Facility(
                    id = facilityRemote.id,
                    name = facilityRemote.name,
                    options = facilityOptions
                )
                facilities.add(facility)
            }

            val exclusionItemList = mutableListOf<ExclusionItem>()
            val exclusionGroupList = mutableListOf<ExclusionGroup>()

            it.exclusionList.forEach { exclusionListRemote ->
                exclusionListRemote.exclusionGroupListRemote.forEach { exclusionGroupRemote ->

                    exclusionGroupRemote.exclusionGroupRemote.forEach {
                        val exclusionItem =
                            ExclusionItem(facilityId = it.facilityId, optionId = it.optionId)
                        exclusionItemList.add(exclusionItem)
                    }

                    val exclusionGroup = ExclusionGroup(exclusionGroup = exclusionItemList)
                    exclusionGroupList.add(exclusionGroup)
                }


            }

            return FacilityData(facilitiesList = facilities, exclusionList = exclusionGroupList)
        }

        return FacilityData(facilitiesList = emptyList(), exclusionList = emptyList())
    }
}
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

            it.facilitiesList?.forEach { facilityRemote ->
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
            val exclusionList = mutableListOf<List<ExclusionItem>>()

            it.exclusionList?.forEach { exclusionListRemote ->
                exclusionListRemote.forEach { exclusionItemRemote ->

                    val exclusionItem = ExclusionItem(
                        facilityId = exclusionItemRemote.facilityId,
                        optionId = exclusionItemRemote.optionId
                    )
                    exclusionItemList.add(exclusionItem)
                }

                exclusionList.add(exclusionItemList)
            }

            return FacilityData(facilitiesList = facilities, exclusionList = exclusionList)
        }

        return FacilityData(facilitiesList = emptyList(), exclusionList = emptyList())
    }
}
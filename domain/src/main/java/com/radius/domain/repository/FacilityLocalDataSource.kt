package com.radius.domain.repository

import com.radius.domain.model.database.ExclusionEntity
import com.radius.domain.model.database.FacilityEntity

interface FacilityLocalDataSource {
    fun insertFacilities(facilities: List<FacilityEntity>)

    fun getFacilityInfo(): List<FacilityEntity>

    fun insertExclusionList(exclusionList: ExclusionEntity)

    fun getExclusionList(): ExclusionEntity
}
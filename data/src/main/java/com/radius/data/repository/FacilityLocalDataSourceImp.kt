package com.radius.data.repository

import com.radius.data.database.RadiusDb
import com.radius.domain.model.database.ExclusionEntity
import com.radius.domain.model.database.FacilityEntity
import com.radius.domain.repository.FacilityLocalDataSource
import javax.inject.Inject

class FacilityLocalDataSourceImp @Inject constructor(
    private val radiusDb: RadiusDb
): FacilityLocalDataSource {
    override fun insertFacilities(facilities: List<FacilityEntity>) {
        radiusDb.facilityDao().insertFacilities(facilities)
    }

    override fun getFacilityInfo(): List<FacilityEntity> {
        return radiusDb.facilityDao().getFacilities()
    }

    override fun insertExclusionList(exclusionList: ExclusionEntity) {
       radiusDb.exclusionDao().insertExclusionList(exclusionList)
    }

    override fun getExclusionList(): ExclusionEntity {
       return  radiusDb.exclusionDao().getExclusionList()
    }

}
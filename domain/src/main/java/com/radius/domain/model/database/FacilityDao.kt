package com.radius.domain.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.radius.domain.Constant

@Dao
interface FacilityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFacilities(facilities: List<FacilityEntity>)

    @Query("SELECT * FROM ${Constant.FACILITY_LIST_TABLE}")
    fun getFacilities(): List<FacilityEntity>
}
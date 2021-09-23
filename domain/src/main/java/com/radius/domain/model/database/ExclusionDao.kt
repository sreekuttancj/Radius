package com.radius.domain.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.radius.domain.Constant

@Dao
interface ExclusionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExclusionList(facilities: ExclusionEntity)

    @Query("SELECT * FROM ${Constant.EXCLUSION_LIST_TABLE}")
    fun getExclusionList(): ExclusionEntity
}
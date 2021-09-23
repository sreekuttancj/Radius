package com.radius.domain.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.radius.domain.Constant.Companion.FACILITY_LIST_TABLE

@Entity(tableName = FACILITY_LIST_TABLE)
data class FacilityEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val facilityId: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "options")
    val options: List<FacilityOptionEntity>
)
package com.radius.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.radius.domain.model.database.*

@Database(entities = [FacilityEntity::class, ExclusionEntity::class], version = 1, exportSchema = false)
@TypeConverters(value = [FacilityTypeConverter::class, ExclusionLTypeConverter::class])
abstract class RadiusDb: RoomDatabase() {

    abstract fun facilityDao(): FacilityDao

    abstract fun exclusionDao(): ExclusionDao
}
package com.radius.domain.model.database

import android.util.Log
import androidx.room.TypeConverter
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import java.lang.Exception

class FacilityTypeConverter {
    @TypeConverter
    fun getOptionsListFromString(options: String): List<FacilityOptionEntity> {
        val mapper = ObjectMapper()
        try {
            return mapper.readValue(options, object : TypeReference<List<FacilityOptionEntity>>() {})
        }catch (e: Exception){
            return emptyList()
        }

    }

    @TypeConverter
    fun getOptionsListFromList(options: List<FacilityOptionEntity>): String {
        val mapper = ObjectMapper()
        return mapper.writeValueAsString(options)
    }
}
package com.radius.domain.model.database

import androidx.room.TypeConverter
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.radius.domain.model.business.ExclusionItem
import java.lang.Exception

class ExclusionLTypeConverter {
        @TypeConverter
        fun getExclusionListFromString(options: String): List<List<ExclusionItem>> {
            val mapper = ObjectMapper()
            try {
                return mapper.readValue(options, object : TypeReference<List<List<ExclusionItem>>>() {})
            }catch (e: Exception){
                return emptyList()
            }

        }

        @TypeConverter
        fun getExclusionListFromList(options: List<List<ExclusionItem>>): String {
            val mapper = ObjectMapper()
            return mapper.writeValueAsString(options)
        }
    }
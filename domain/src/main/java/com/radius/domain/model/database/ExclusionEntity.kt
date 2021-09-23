package com.radius.domain.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.radius.domain.Constant
import com.radius.domain.model.business.ExclusionItem

@Entity (tableName = Constant.EXCLUSION_LIST_TABLE)
data class ExclusionEntity (
    @PrimaryKey (autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "exclusion_list")
    val exclusionList: List<List<ExclusionItem>>
)
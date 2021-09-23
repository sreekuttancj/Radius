package com.radius.domain.model.business

import androidx.room.Entity

@Entity
data class FacilityOption (
    val id: String,

    val facilityId: String,

    val name: String,

    val icon: String,

    val isSelected: Boolean = false,

    val isExcluded: Boolean = false,

    var exclusionGroup: List<ExclusionItem> ?= emptyList()
    )
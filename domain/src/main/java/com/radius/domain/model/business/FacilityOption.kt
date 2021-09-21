package com.radius.domain.model.business

data class FacilityOption (
    val id: String,

    val name: String,

    val icon: String,

    val isSelected: Boolean = false,

    val isExcluded: Boolean = false
    )
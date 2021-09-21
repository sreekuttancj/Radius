package com.radius.domain.model.business

data class Facility (
    val id: String,

    val name: String,

    val options: List<FacilityOption>
    )
package com.radius.domain.model.database

import com.fasterxml.jackson.annotation.JsonProperty

data class FacilityOptionEntity(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("name")
    val name: String,

    @JsonProperty("icon")
    val icon: String,
)
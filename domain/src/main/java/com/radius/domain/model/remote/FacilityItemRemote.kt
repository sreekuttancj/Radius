package com.radius.domain.model.remote

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class FacilityItemRemote (
    @JsonProperty("facility_id")
    val id: String,

    @JsonProperty("name")
    val name: String,

    @JsonProperty("options")
    val options: List<FacilityOptionRemote>
    )
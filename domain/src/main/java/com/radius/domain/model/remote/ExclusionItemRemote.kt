package com.radius.domain.model.remote

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ExclusionItem (
    @JsonProperty("facility_id")
    val facilityId: String,

    @JsonProperty("options_id")
    val optionId: String
)
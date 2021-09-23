package com.radius.domain.model.business

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ExclusionItem (
    @JsonProperty("facilityId")
    val facilityId: String,

    @JsonProperty("optionId")
    val optionId: String
)
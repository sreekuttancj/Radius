package com.radius.domain.model.remote

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ExclusionList (
    @JsonProperty("exclusions")
    val exclusionList: List<ExclusionGroup>
    )
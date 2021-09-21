package com.radius.domain.model.remote

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ExclusionListRemote (
    @JsonProperty("exclusions")
    val exclusionListRemote: List<ExclusionGroupRemote>
    )
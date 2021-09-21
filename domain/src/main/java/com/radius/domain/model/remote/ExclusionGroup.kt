package com.radius.domain.model.remote

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ExclusionGroup (
    val exclusionGroup: List<ExclusionItem>
    )
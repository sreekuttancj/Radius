package com.radius.domain.model.business

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ExclusionItem (
    val facilityId: String,

    val optionId: String
)
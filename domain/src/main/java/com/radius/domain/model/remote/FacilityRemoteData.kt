package com.radius.domain.model.remote

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class FacilityRemoteData (
    @JsonProperty("facilities")
    val facilitiesList: List<FacilityRemote>,

    @JsonProperty("exclusions")
    val exclusionList: List<ExclusionListRemote>
    )
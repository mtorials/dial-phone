package de.mtorials.dialphone.exceptions

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class APIException(
    @JsonProperty("errcode")
    errorCode: String,
    error: String
) : RuntimeException("$errorCode: $error")
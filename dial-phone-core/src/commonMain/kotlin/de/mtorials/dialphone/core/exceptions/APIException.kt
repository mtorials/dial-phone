package de.mtorials.dialphone.core.exceptions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class APIException(
    @SerialName("errcode")
    val errorCode: String,
    private val error: String
) : RuntimeException("$errorCode: $error")
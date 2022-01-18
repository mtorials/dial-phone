package de.mtorials.dialphone.api.authentication.responses

import kotlinx.serialization.Serializable

@Serializable
data class AuthApiResponse(
    val flows: List<Flow> = listOf(),
    val params: Map<String, Map<String, String>> = mapOf(),
    val session: String,
) {
    @Serializable
    data class Flow(
        val stages: List<String> = listOf()
    )
}
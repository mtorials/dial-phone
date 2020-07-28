package de.mtorials.dialphone

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitStringResponse

object Registrator {

    private const val registrationEndpoint = "/_matrix/client/r0/register"

    suspend fun registerGuest(homeserverUrl: String) : GuestResponse {
        val response = Fuel.post(homeserverUrl + registrationEndpoint, listOf(Pair("kind", "guest")))
            .body("{}")
            .awaitStringResponse()
        return jacksonObjectMapper().readValue(response.third)
    }

    data class GuestResponse(
        @JsonProperty("device_id")
        val deviceId: String,
        @JsonProperty("user_id")
        val userId: String,
        @JsonProperty("access_token")
        val token: String,
        @JsonProperty("home_server")
        val homeserverName: String
    )
}
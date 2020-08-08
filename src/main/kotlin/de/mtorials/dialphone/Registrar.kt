package de.mtorials.dialphone

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.http4k.client.OkHttp
import org.http4k.core.Method

object Registrar {

    private const val registrationEndpoint = "/_matrix/client/r0/register"
    private val client = OkHttp()

    suspend fun registerGuest(homeserverUrl: String) : GuestResponse {
        val request = org.http4k.core.Request(Method.POST, homeserverUrl + registrationEndpoint)
            .query("kind", "guest")
            .body("{}")
        val responseString = client(request).bodyString()
        return jacksonObjectMapper().readValue(responseString)
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
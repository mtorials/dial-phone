package de.mtorials.dialphone

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class Registrar(
    private val client: HttpClient
) {

    suspend fun registerGuest(homeserverUrl: String) : GuestResponse {
        return client.post {
            url(homeserverUrl + registrationEndpoint)
            parameter("kind", "guest")
            body = "{}"
        }
    }

    @Serializable
    data class GuestResponse(
        @SerialName("device_id")
        val deviceId: String,
        @SerialName("user_id")
        val userId: String,
        @SerialName("access_token")
        val token: String,
        @SerialName("home_server")
        val homeserverName: String
    )

    companion object {
        private const val registrationEndpoint = "/_matrix/client/r0/register"
    }
}
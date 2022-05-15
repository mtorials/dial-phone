package de.mtorials.dialphone.api.authentication

import de.mtorials.dialphone.api.serialization.EmptySerializable
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class Registrar(
    private val client: HttpClient
) {

    suspend fun registerGuest(homeserverUrl: String) : GuestRegResponse {
        return client.post {
            url(homeserverUrl + registrationEndpoint)
            parameter("kind", "guest")
            contentType(ContentType.Application.Json)
            setBody(EmptySerializable())
        }.body()
    }

    suspend fun registerUser(homeserverUrl: String, password: String, username: String) : UserRegResponse {
        val kind = "user"
        val interactiveAuthApi = InteractiveAuthApi(
            httpClient = client,
            homeserverUrl = homeserverUrl
        )
        val initialResponse = interactiveAuthApi.initialCall(registrationEndpoint, kind)
        return client.post {
            url(homeserverUrl + registrationEndpoint)
            parameter("kind", kind)
            contentType(ContentType.Application.Json)
            setBody(RegisterRequest(
                password,
                username,
                // TODO login type!
                auth = mapOf("session" to initialResponse.session, "type" to "m.login.dummy")
            ))
        }.body()
    }

    @Serializable
    data class RegisterRequest(
        val password: String,
        val username: String,
        val auth: Map<String, String>,
    )

    @Serializable
    data class GuestRegResponse(
        @SerialName("device_id")
        val deviceId: String,
        @SerialName("user_id")
        val userId: String,
        @SerialName("access_token")
        val token: String,
        @SerialName("home_server")
        val homeserverName: String
    )

    @Serializable
    data class UserRegResponse(
        @SerialName("access_token")
        val accessToken: String,
        @SerialName("device_id")
        val deviceId: String,
        @SerialName("user_id")
        val userId: String,
    )

    companion object {
        private const val registrationEndpoint = "/_matrix/client/v3/register"
    }
}
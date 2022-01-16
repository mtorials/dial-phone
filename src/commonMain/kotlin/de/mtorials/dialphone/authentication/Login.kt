package de.mtorials.dialphone.authentication

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class Login(
    private val httpClient: HttpClient,
    val homeserverUrl: String,
) {
    suspend fun login(username: String, password: String) : PasswordLoginResponse = httpClient.post {
        url(homeserverUrl + LOGIN_ENDPOINT)
        contentType(ContentType.Application.Json)
        body = PasswordLoginRequest(
            identifier = MatrixUserIdIdentifier(username),
            password = password,
        )
    }

    @Serializable
    class PasswordLoginRequest(
        val identifier: MatrixUserIdIdentifier,
        val password: String,
    ) {
        val type = "m.login.password"
    }

    @Serializable
    class PasswordLoginResponse(
        @SerialName("access_token")
        val accessToken: String,
        @SerialName("device_id")
        val deviceId: String,
        @SerialName("user_id")
        val userId: String,
    )

    companion object {
        const val LOGIN_ENDPOINT = "/_matrix/client/v3/login"
    }
}
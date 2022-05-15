package de.mtorials.dialphone.api.authentication

import de.mtorials.dialphone.api.authentication.responses.AuthApiResponse
import de.mtorials.dialphone.api.serialization.EmptySerializable
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*

class InteractiveAuthApi(
    private val httpClient: HttpClient,
    val homeserverUrl: String,
) {

    suspend fun initialCall(endpoint: String, kind: String) : AuthApiResponse = httpClient.post {
        url(homeserverUrl + endpoint)
        contentType(ContentType.Application.Json)
        parameter("kind", kind)
        expectSuccess = false
        setBody(EmptySerializable())
    }.body()

//    suspend fun loginPassword(username: String, password: String) {
//        initialCall(DUMMY_AUTH_ENDPOINT)
//        val response: AuthApiResponse = httpClient.post {
//            url(homeserverUrl + DUMMY_AUTH_ENDPOINT)
//            body = AuthDict(
//                type = AuthDict.AuthType.PASSWORD,
//                password = password,
//                identifier = mapOf(
//                    "type" to "m.id.user",
//                    "user" to username
//                ),
//                session = sessionKey
//            )
//        }
//    }
}
package de.mtorials.dialphone.api

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.date.*
import kotlin.random.Random

abstract class MatrixClient(
    protected val homeserverUrl: String,
    protected val client: HttpClient,
    protected val token: String,
) {

    protected val random = Random(getTimeMillis().toInt() * 2834)

    protected suspend inline fun <reified T> request(
        httpMethod: HttpMethod,
        path: String,
        vararg parameters: Pair<String, String> = arrayOf(),
        bodyValue: Any? = null
    ) : T {
        val newPath = homeserverUrl + DialPhoneApi.MATRIX_PATH + path
        return client.request {
            url(newPath)
            method = httpMethod
            header("Authorization", "Bearer $token")
            header("Content-Type", "application/json")
            parameters.forEach { parameter(it.first, it.second) }
            if (bodyValue != null) body = bodyValue
        }
    }

    // Useless hopefully
    protected fun encode(input: String) = input
}
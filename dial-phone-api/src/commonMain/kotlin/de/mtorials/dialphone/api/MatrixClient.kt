package de.mtorials.dialphone.api

import de.mtorials.dialphone.api.ids.MatrixID
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.date.*
import kotlin.random.Random

abstract class MatrixClient(
    protected val homeserverUrl: String,
    protected val client: HttpClient,
    protected val token: String,
) {

    // TODO only positive txids
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
            if (bodyValue is String) contentType(ContentType.Application.Any)
            else contentType(ContentType.Application.Json)
            parameters.forEach { parameter(it.first, it.second) }
            setBody(bodyValue)
        }.body()
    }

    // Useless hopefully
    protected fun encode(input: String) = input
    protected fun encode(input: MatrixID) = input.value
}
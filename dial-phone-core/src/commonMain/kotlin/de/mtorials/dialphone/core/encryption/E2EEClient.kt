package de.mtorials.dialphone.core.encryption

import de.mtorials.dialphone.api.MatrixClient
import de.mtorials.dialphone.api.requests.encryption.KeyUploadRequest
import de.mtorials.dialphone.api.responses.encryption.KeyUploadResponse
import io.ktor.client.*
import io.ktor.http.*

class E2EEClient(
    client: HttpClient,
    token: String,
    homeserverUrl: String,
) : MatrixClient(
    client = client,
    token = token,
    homeserverUrl = homeserverUrl,
) {

    suspend fun uploadKeys(request: KeyUploadRequest) : KeyUploadResponse =
        request(HttpMethod.Post, "keys/upload", bodyValue = request)

}
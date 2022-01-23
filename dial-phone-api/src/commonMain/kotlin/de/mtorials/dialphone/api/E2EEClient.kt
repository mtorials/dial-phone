package de.mtorials.dialphone.api

import de.mtorials.dialphone.api.requests.SendToDeviceRequest
import de.mtorials.dialphone.api.requests.encryption.KeyClaimRequest
import de.mtorials.dialphone.api.requests.encryption.KeyUploadRequest
import de.mtorials.dialphone.api.responses.EventResponse
import de.mtorials.dialphone.api.responses.encryption.KeyClaimResponse
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

    /**
     * https://spec.matrix.org/v1.1/client-server-api/#post_matrixclientv3keysclaim
     */
    suspend fun claimKeys(request: KeyClaimRequest) : KeyClaimResponse =
        request(HttpMethod.Post, "keys/claim", bodyValue = request)

    suspend fun sendEventToDevice(
        eventType: String,
        content: SendToDeviceRequest,
    ) : String {
        return request<EventResponse>(
            httpMethod = HttpMethod.Put,
            path = "sendToDevice/$eventType/${random.nextInt()}",
            bodyValue = content
        ).id
    }
}
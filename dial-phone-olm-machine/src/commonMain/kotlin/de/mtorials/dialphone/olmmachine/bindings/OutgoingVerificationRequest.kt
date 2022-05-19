package de.mtorials.dialphone.olmmachine.bindings

sealed class OutgoingVerificationRequest {
    data class ToDevice(
        val requestId: String,
        val eventType: String,
        val body: String
    ) : OutgoingVerificationRequest()
    data class InRoom(
        val requestId: String,
        val roomId: String,
        val eventType: String,
        val content: String
    ) : OutgoingVerificationRequest()
}
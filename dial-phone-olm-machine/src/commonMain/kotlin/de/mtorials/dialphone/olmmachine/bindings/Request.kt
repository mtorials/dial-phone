package de.mtorials.dialphone.olmmachine.bindings

sealed class Request {
    data class ToDevice(
        val requestId: String,
        val eventType: String,
        val body: String
    ) : Request()
    data class KeysUpload(
        val requestId: String,
        val body: String
    ) : Request()
    data class KeysQuery(
        val requestId: String,
        val users: List<String>
    ) : Request()
    data class KeysClaim(
        val requestId: String,
        val oneTimeKeys: Map<String, Map<String, String>>
    ) : Request()
    data class KeysBackup(
        val requestId: String,
        val version: String,
        val rooms: String
    ) : Request()
    data class RoomMessage(
        val requestId: String,
        val roomId: String,
        val eventType: String,
        val content: String
    ) : Request()
    data class SignatureUpload(
        val requestId: String,
        val body: String
    ) : Request()
}
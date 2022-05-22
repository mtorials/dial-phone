package de.mtorials.dialphone.olmmachine.bindings

data class Device(
    var userId: String,
    var deviceId: String,
    var keys: Map<String, String>,
    var algorithms: List<String>,
    var displayName: String?,
    var isBlocked: Boolean,
    var locallyTrusted: Boolean,
    var crossSigningTrusted: Boolean
)
package de.mtorials.dialphone.encyption.olm

data class PickledInboundGroupSession(
    var pickle: String,
    var senderKey: String,
    var signingKey: Map<String, String>,
    var roomId: String,
    var forwardingChains: List<String>,
    var imported: Boolean,
    var backedUp: Boolean
)
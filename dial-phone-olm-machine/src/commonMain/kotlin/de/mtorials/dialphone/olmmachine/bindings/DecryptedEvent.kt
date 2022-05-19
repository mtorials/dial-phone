package de.mtorials.dialphone.olmmachine.bindings

data class DecryptedEvent(
    var clearEvent: String,
    var senderCurve25519Key: String,
    var claimedEd25519Key: String?,
    var forwardingCurve25519Chain: List<String>
)
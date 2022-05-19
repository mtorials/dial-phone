package de.mtorials.dialphone.encyption.olm

data class DecryptedEvent(
    var clearEvent: String,
    var senderCurve25519Key: String,
    var claimedEd25519Key: String?,
    var forwardingCurve25519Chain: List<String>
)
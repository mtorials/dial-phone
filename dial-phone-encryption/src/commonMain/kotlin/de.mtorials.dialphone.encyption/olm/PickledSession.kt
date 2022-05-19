package de.mtorials.dialphone.encyption.olm

data class PickledSession(
    var pickle: String,
    var senderKey: String,
    var createdUsingFallbackKey: Boolean,
    var creationTime: String,
    var lastUseTime: String
)
package de.mtorials.dialphone.encyption.olm

data class CancelInfo(
    var cancelCode: String,
    var reason: String,
    var cancelledByUs: Boolean
)
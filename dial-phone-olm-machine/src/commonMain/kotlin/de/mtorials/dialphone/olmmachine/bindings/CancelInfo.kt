package de.mtorials.dialphone.olmmachine.bindings

data class CancelInfo(
    var cancelCode: String,
    var reason: String,
    var cancelledByUs: Boolean
)
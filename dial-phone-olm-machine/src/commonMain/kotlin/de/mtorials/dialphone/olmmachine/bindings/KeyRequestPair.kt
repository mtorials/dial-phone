package de.mtorials.dialphone.olmmachine.bindings

data class KeyRequestPair(
    var cancellation: Request?,
    var keyRequest: Request
)
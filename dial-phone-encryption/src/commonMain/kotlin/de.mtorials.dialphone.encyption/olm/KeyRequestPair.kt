package de.mtorials.dialphone.encyption.olm

data class KeyRequestPair(
    var cancellation: Request?,
    var keyRequest: Request
)
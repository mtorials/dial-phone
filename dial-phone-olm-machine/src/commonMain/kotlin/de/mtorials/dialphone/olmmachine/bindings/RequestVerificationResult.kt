package de.mtorials.dialphone.olmmachine.bindings

data class RequestVerificationResult(
    var verification: VerificationRequest,
    var request: OutgoingVerificationRequest
)
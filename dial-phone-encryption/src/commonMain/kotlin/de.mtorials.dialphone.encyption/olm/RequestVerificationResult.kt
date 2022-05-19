package de.mtorials.dialphone.encyption.olm

data class RequestVerificationResult(
    var verification: VerificationRequest,
    var request: OutgoingVerificationRequest
)
package de.mtorials.dialphone.olmmachine.bindings

data class ConfirmVerificationResult(
    var requests: List<OutgoingVerificationRequest>,
    var signatureRequest: SignatureUploadRequest?
)
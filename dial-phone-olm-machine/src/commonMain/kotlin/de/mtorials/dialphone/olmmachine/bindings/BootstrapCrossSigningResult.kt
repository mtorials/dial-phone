package de.mtorials.dialphone.olmmachine.bindings

data class BootstrapCrossSigningResult(
    var uploadSigningKeysRequest: UploadSigningKeysRequest,
    var signatureRequest: SignatureUploadRequest
)
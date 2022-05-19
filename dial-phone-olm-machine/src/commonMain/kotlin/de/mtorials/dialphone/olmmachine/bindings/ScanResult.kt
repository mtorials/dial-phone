package de.mtorials.dialphone.olmmachine.bindings

data class ScanResult(
    var qr: QrCode,
    var request: OutgoingVerificationRequest
)
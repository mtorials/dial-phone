package de.mtorials.dialphone.encyption.olm

data class PickledAccount(
    var userId: String,
    var deviceId: String,
    var pickle: String,
    var shared: Boolean,
    var uploadedSignedKeyCount: Long
)
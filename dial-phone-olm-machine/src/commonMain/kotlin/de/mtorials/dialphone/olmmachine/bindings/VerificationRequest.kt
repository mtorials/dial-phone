package de.mtorials.dialphone.olmmachine.bindings

data class VerificationRequest(
    var otherUserId: String,
    var otherDeviceId: String?,
    var flowId: String,
    var roomId: String?,
    var weStarted: Boolean,
    var isReady: Boolean,
    var isPassive: Boolean,
    var isDone: Boolean,
    var isCancelled: Boolean,
    var cancelInfo: CancelInfo?,
    var theirMethods: List<String>?,
    var ourMethods: List<String>?
)
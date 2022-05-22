package de.mtorials.dialphone.olmmachine.bindings

data class QrCode(
    var otherUserId: String,
    var otherDeviceId: String,
    var flowId: String,
    var roomId: String?,
    var weStarted: Boolean,
    var otherSideScanned: Boolean,
    var hasBeenConfirmed: Boolean,
    var reciprocated: Boolean,
    var isDone: Boolean,
    var isCancelled: Boolean,
    var cancelInfo: CancelInfo?
)
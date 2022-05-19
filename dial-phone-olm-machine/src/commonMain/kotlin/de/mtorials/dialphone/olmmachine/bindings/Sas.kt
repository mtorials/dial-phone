package de.mtorials.dialphone.olmmachine.bindings

data class Sas(
    var otherUserId: String,
    var otherDeviceId: String,
    var flowId: String,
    var roomId: String?,
    var weStarted: Boolean,
    var hasBeenAccepted: Boolean,
    var canBePresented: Boolean,
    var supportsEmoji: Boolean,
    var haveWeConfirmed: Boolean,
    var isDone: Boolean,
    var isCancelled: Boolean,
    var cancelInfo: CancelInfo?
)
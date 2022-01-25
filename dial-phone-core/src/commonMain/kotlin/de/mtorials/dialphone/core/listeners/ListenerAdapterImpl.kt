package de.mtorials.dialphone.core.listeners

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.api.model.enums.Membership
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import de.mtorials.dialphone.api.model.mevents.roommessage.MRoomMessage
import de.mtorials.dialphone.api.model.mevents.roomstate.MRoomMember
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.EventCallback
import de.mtorials.dialphone.core.dialevents.MessageReceivedEvent
import de.mtorials.dialphone.core.dialevents.RoomInviteEvent
import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.ids.roomId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Extendable, if you want to add support for custom events here
 */
open class ListenerAdapterImpl(
    private val receivePastEvents: Boolean = false,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) : ListenerAdapter {

    protected open var callbackOnMessageReceived : EventCallback<MessageReceivedEvent> = {}
    protected open var callbackOnRoomInvited : EventCallback<RoomInviteEvent> = {}

    override fun onRoomMessageReceived(block: suspend (MessageReceivedEvent) -> Unit) {
        callbackOnMessageReceived = block
    }

    override fun onRoomInvited(block: suspend (RoomInviteEvent) -> Unit) {
        callbackOnRoomInvited = block
    }

    override fun onRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhone, isOld: Boolean) {
        // TODO not an unchecked cast?
        if (!isOld || receivePastEvents) onEvent(event, roomId.roomId(), phone as DialPhone)
    }

    protected fun onEvent(event: MatrixEvent, roomId: RoomId, phone: DialPhone) {
        when(event) {
            is MRoomMessage -> coroutineScope.launch { callbackOnMessageReceived(
                MessageReceivedEvent(
                    roomID = roomId,
                    phone = phone,
                    event = event
                )
            ) }
            is MRoomMember -> when (event.content.membership) {
                Membership.INVITE -> if (event.stateKey == phone.ownId) coroutineScope.launch {
                    callbackOnRoomInvited(
                        RoomInviteEvent(
                            roomId = roomId,
                            phone = phone,
                            event = event
                        )
                    )
                }
            }
        }
    }
}
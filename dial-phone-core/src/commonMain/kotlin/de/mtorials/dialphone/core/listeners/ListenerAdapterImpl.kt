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
import de.mtorials.dialphone.core.ids.RoomId
import de.mtorials.dialphone.core.ids.roomId
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Extendable, if you want to add support for custom events here
 */
open class ListenerAdapterImpl(
    private val receivePastEvents: Boolean = false,
) : ListenerAdapter {

    protected open var callbackOnMessageReceived : EventCallback<MessageReceivedEvent> = {}
    protected open var callbackOnRoomInvited : EventCallback<RoomInviteEvent> = {}

    override suspend fun onRoomMessageReceived(block: suspend (MessageReceivedEvent) -> Unit) {
        callbackOnMessageReceived = block
    }

    override suspend fun onRoomInvited(block: suspend (RoomInviteEvent) -> Unit) {
        callbackOnRoomInvited = block
    }

    override fun onRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhoneApi, isOld: Boolean) {
        // TODO not an unchecked cast?
        if (!isOld || receivePastEvents) onEvent(event, roomId.roomId(), phone as DialPhone)
    }

    protected fun onEvent(event: MatrixEvent, roomId: RoomId, phone: DialPhone) {
        when(event) {
            is MRoomMessage -> GlobalScope.launch { callbackOnMessageReceived(
                MessageReceivedEvent(
                    roomID = roomId,
                    phone = phone,
                    event = event
                )
            ) }
            is MRoomMember -> when (event.content.membership) {
                Membership.INVITE -> if (event.stateKey == phone.ownId) GlobalScope.launch {
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
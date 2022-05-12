package de.mtorials.dialphone.core.listeners

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
import de.mtorials.dialphone.api.listeners.GenericListener
import de.mtorials.dialphone.api.model.mevents.roomstate.MatrixStateEvent
import de.mtorials.dialphone.core.DialPhoneImpl
import de.mtorials.dialphone.core.entities.MemberImpl
import de.mtorials.dialphone.core.entities.MessageImpl
import de.mtorials.dialphone.core.entities.room.InvitedRoom
import de.mtorials.dialphone.core.entities.room.InvitedRoomImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Extendable, if you want to add support for custom events here
 */
open class ListenerAdapterImpl(
    private val receivePastEvents: Boolean = false,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) : ListenerAdapter, GenericListener<DialPhoneImpl> {

    protected open var callbackOnMessageReceived : EventCallback<MessageReceivedEvent> = {}
    protected open var callbackOnRoomInvited : EventCallback<RoomInviteEvent> = {}

    override fun onRoomMessageReceived(block: suspend (MessageReceivedEvent) -> Unit) {
        callbackOnMessageReceived = block
    }

    override fun onRoomInvited(block: suspend (RoomInviteEvent) -> Unit) {
        callbackOnRoomInvited = block
    }

    override fun onJoinedRoomMessageEvent(event: MatrixEvent, roomId: RoomId, phone: DialPhoneImpl, isOld: Boolean) {
        if (!isOld || receivePastEvents) onEvent(event, roomId, phone)
    }
    override fun onJoinedRoomStateEvent(event: MatrixStateEvent, roomId: RoomId, phone: DialPhoneImpl, isOld: Boolean) {
        if (!isOld || receivePastEvents) onEvent(event, roomId, phone)
    }
    override fun onInvitedRoomStateEvent(event: MatrixStateEvent, roomId: RoomId, phone: DialPhoneImpl, isOld: Boolean) {
        if (!isOld || receivePastEvents) onEvent(event, roomId, phone)
    }

    protected fun onEvent(event: MatrixEvent, roomId: RoomId, phone: DialPhoneImpl) {
        when(event) {
            is MRoomMessage -> coroutineScope.launch {
                val room = phone.getJoinedRoomById(roomId) ?: error("Can not find joined room")
                callbackOnMessageReceived(
                    MessageReceivedEvent(
                        phone = phone,
                        id = event.id,
                        room = room,
                        message = MessageImpl(
                            phone,
                            id = event.id,
                            room = room,
                            author = MemberImpl(phone.getUserById(event.sender) ?: error("Can not find user"), room),
                            messageType = event.content.msgType,
                            content = event.content,
                        )
                    )
                )
            }
            is MRoomMember -> when (event.content.membership) {
                Membership.INVITE -> if (event.stateKey == phone.ownId.toString()) coroutineScope.launch {
                    callbackOnRoomInvited(
                        RoomInviteEvent(
                            phone = phone,
                            room = InvitedRoomImpl(
                                phone = phone,
                                id = roomId,
                            ),
                            sender = phone.getUserById(event.sender) ?: error("Can not find user"),
                            content = event.content,
                        )
                    )
                }
            }
        }
    }
}
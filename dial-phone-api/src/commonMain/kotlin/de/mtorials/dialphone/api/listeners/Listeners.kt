package de.mtorials.dialphone.api.listeners

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

fun <T : MatrixEvent> listenFor(eventType: KClass<T>, block: suspend DialPhoneApi.(T, String)->Unit) : Listener {
    return object : Listener {
        override fun onNewRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhoneApi) {
            if (eventType.isInstance(event)) GlobalScope.launch { phone.block(event as T, roomId) }
        }
        override fun onOldRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhoneApi) = Unit
    }
}
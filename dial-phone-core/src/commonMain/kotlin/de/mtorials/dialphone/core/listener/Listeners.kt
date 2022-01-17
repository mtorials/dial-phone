package de.mtorials.dialphone.core.listener

import de.mtorials.dialphone.core.DialPhoneCore
import de.mtorials.dialphone.core.model.mevents.MatrixEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

fun <T : MatrixEvent> listenFor(eventType: KClass<T>, block: suspend DialPhoneCore.(T, String)->Unit) : Listener {
    return object : Listener {
        override fun onNewRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhoneCore) {
            if (eventType.isInstance(event)) GlobalScope.launch { phone.block(event as T, roomId) }
        }
        override fun onOldRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhoneCore) = Unit
    }
}
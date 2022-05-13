package de.mtorials.dialphone.core.cache

import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.model.enums.Membership
import de.mtorials.dialphone.api.model.mevents.roomstate.MatrixStateEvent
import io.ktor.util.reflect.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.serializer
import kotlin.reflect.KClass
import kotlin.reflect.KType

class InMemoryStateCache : StateCache {

    private val mutex = Mutex()

    private val states: MutableMap<RoomId, MutableList<MatrixStateEvent>> = mutableMapOf()

    override fun getRoomStateEvents(roomId: RoomId): List<MatrixStateEvent> = states[roomId] ?: emptyList()

    override fun <T : MatrixStateEvent> getRoomStateEvents(roomId: RoomId, type: String): List<T> =
        getRoomStateEvents(roomId).filter { it.getTypeName() == type } as List<T>

    // TODO check if concurrent modification
    override fun insertRoomStateEvent(roomId: RoomId, event: MatrixStateEvent) {
        val state = states[roomId]?.filter {
            // filter out every event that is same type and state key before inserting
            !(event::class.isInstance(it) && event.stateKey == it.stateKey)
            // if null, create mutable list
        }?.toMutableList() ?: mutableListOf()
        state.add(event)
        states[roomId] = state
    }

    override val roomIds: Map<Membership, MutableList<RoomId>> = Membership.values().associateWith { mutableListOf() }

    override suspend fun insertRoomId(membership: Membership, roomId: RoomId) {
        mutex.withLock {
            roomIds.values.forEach { it.remove(roomId) }
            roomIds[membership]?.add(roomId)
        }
    }

}
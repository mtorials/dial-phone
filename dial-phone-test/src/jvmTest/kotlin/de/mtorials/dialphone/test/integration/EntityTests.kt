package de.mtorials.dialphone.test.integration

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.listeners.ApiListener
import de.mtorials.dialphone.api.model.mevents.roomstate.MatrixStateEvent
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.listeners.ListenerAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class EntityTests {

    lateinit var user1: DialPhone
    lateinit var user2: DialPhone

    @Before
    fun `register and login as user`() {
        runBlocking {
            user1 = DialPhone(Configs.HOMESERVER_URL) {
                asUser(Configs.TEST_USER, Configs.TEST_PWD, true)
//                addListeners(object : ApiListener {
//                    override suspend fun onJoinedRoomStateEvent(
//                        event: MatrixStateEvent,
//                        roomId: RoomId,
//                        phone: DialPhoneApi,
//                        isOld: Boolean
//                    ) {
//                        println(event)
//                    }
//                })
            }
            delay(1000)
            user2 = DialPhone(Configs.HOMESERVER_URL) {
                asUser(Configs.USER2_USER, Configs.USER2_PWD, true)
                addListeners(ListenerAdapter {
                    onRoomInvited {
                        println("Join")
                        it.room.join()
                    }
                })
            }
            user2.sync()
            user1.sync()
            // TODO remove rate limit in synapse
            delay(1000)
        }
    }

    @Test
    fun `check own user`() {
        runBlocking {
            assertEquals(user1.getMe().id.toString(), "@${Configs.TEST_USER}:example.org")
        }
    }

    @Test
    fun `check room membership`() {
        runBlocking {
            val u = user1.getUserById(user2.ownId) ?: error("Cant get user")
            val r = user1.createRoom("test-room") {
                invite(u)
            }
            delay(4000)
            println(user2.getJoinedRooms().map { it.name })
            println(user2.ownId)
            println(r.members)
            r.members.any { user2.ownId == it.id }.run { assert(this) }

        }
    }
}
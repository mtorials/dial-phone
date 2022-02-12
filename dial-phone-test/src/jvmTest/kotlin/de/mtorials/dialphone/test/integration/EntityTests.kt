package de.mtorials.dialphone.test.integration

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
            // TODO remove rate limit in synapse
            delay(1000)
            user2.sync()
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
                // TODO should be ale to use id as not every user is public
                invite(u)
            }
            delay(1000)
            r.members.any { user2.ownId == it.id }.run { assert(this) }

        }
    }
}
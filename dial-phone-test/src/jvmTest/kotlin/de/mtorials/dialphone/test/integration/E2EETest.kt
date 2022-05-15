package de.mtorials.dialphone.test.integration

import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.entities.room.JoinedRoom
import de.mtorials.dialphone.core.listeners.ListenerAdapter
import de.mtorials.dialphone.core.sendTextMessage
import de.mtorials.dialphone.encyption.useEncryption
import de.mtorials.dialphone.test.Helper
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class E2EETest {
    private val text = "This is a hopefully encrypted message!"

    lateinit var user1: DialPhone
    lateinit var user2: DialPhone

    lateinit var room: JoinedRoom

    @Before
    fun `register and login as user`() {
        runBlocking {
            user1 = DialPhone(Configs.HOMESERVER_URL) {
                asUser(Configs.TEST_USER, Configs.TEST_PWD, true)
                useEncryption()
            }
            user2 = DialPhone(Configs.HOMESERVER_URL) {
                asUser(Configs.USER2_USER, Configs.USER2_PWD, true)
                useEncryption()
                addListeners(ListenerAdapter {
                    onRoomInvited { it.room.join() }
                })
            }
            // TODO remove rate limit in synapse
            delay(1000)
            room = user1.createRoom("e2ee-test") {
                makePublic()
                encrypt()
                invite(user2.ownId)
            }
            user1.sync()
            user2.sync()
        }
    }

    @Test
    fun `send and receive`() {
        runBlocking {
            delay(2000)
            user2.addListeners(ListenerAdapter {
                onRoomMessageReceived {
                    if (it.room.encrypted) assertEquals(it.message.content.body, text)
                }
            })
            room.sendTextMessage(text)
        }
    }
}
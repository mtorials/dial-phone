package de.mtorials.dialphone.test.integration

import de.mtorials.dialphone.api.logging.DialPhoneLogLevel
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.entities.room.JoinedRoom
import de.mtorials.dialphone.core.listeners.ListenerAdapter
import de.mtorials.dialphone.core.sendTextMessage
import de.mtorials.dialphone.encyption.useEncryption
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class E2EETest {

    lateinit var user1: DialPhone
    lateinit var user2: DialPhone

    private lateinit var room: JoinedRoom

    @Before
    fun `register and login as user`() {
        runBlocking {
            user1 = DialPhone(Configs.HOMESERVER_URL) {
                asUser(Configs.TEST_USER, Configs.TEST_PWD, true)
                useEncryption("../stores/one")
                dialPhoneLogLevel = DialPhoneLogLevel.ALL_MESSAGE
            }
            user2 = DialPhone(Configs.HOMESERVER_URL) {
                asUser(Configs.USER2_USER, Configs.USER2_PWD, true)
                useEncryption("../stores/two")
                dialPhoneLogLevel = DialPhoneLogLevel.ALL_MESSAGE
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
    fun `send and receive`() : Unit = runBlocking {
        delay(2000)
        user2.addListeners(ListenerAdapter {
            onRoomMessageReceived {
                if (it.room.encrypted) assertEquals(it.message.content.body, TEXT)
            }
        })
        room.sendTextMessage(TEXT)
        delay(5000)
    }

    @Test
    fun `send and receive reversed`(): Unit = runBlocking {
        user2.addListeners(ListenerAdapter {
            onRoomMessageReceived {
                if (it.room.encrypted) assertEquals(it.message.content.body, TEXT_ANSWER)
            }
        })
        user2.getJoinedRoomById(room.id)?.sendTextMessage(TEXT_ANSWER)
        delay(5000)
    }

    companion object {
        private const val TEXT = "This is a hopefully encrypted message!"
        private const val TEXT_ANSWER = "This is an pls encrypted answer!"
    }
}
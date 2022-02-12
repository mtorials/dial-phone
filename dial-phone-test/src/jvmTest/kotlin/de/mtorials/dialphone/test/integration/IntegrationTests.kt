package de.mtorials.dialphone.test.integration

import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.listeners.ListenerAdapter
import de.mtorials.dialphone.core.sendTextMessage
import de.mtorials.dialphone.test.Helper
import kotlinx.coroutines.*
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals


class IntegrationTests {

    private val text = Helper.getRandomName(50)

    lateinit var user1: DialPhone
    lateinit var user2: DialPhone

    @Before
    fun `register and login as user`() {
        runBlocking {
            user1 = DialPhone(Configs.HOMESERVER_URL) {
                asUser(Configs.TEST_USER, Configs.TEST_PWD, true)
            }
            user2 = DialPhone(Configs.HOMESERVER_URL) {
                asUser(Configs.USER2_USER, Configs.USER2_PWD, true)
            }
            // TODO remove rate limit in synapse
            delay(1000)
        }
    }

    @Test
    fun `check guest access`() {
        runBlocking {
            DialPhone(Configs.HOMESERVER_URL) {
                asGuest()
            }
        }
    }

    @Test
    fun `create room`() {
        val syncJob: Job = user1.sync()
        runBlocking {
            println("started syncing!")
            delay(100)
            val name = Helper.getRandomName(10)
            val room = user1.createRoom(name) {
                topic = "This is a room topic"
                makePublic()
            }
            assertEquals(room.name, name)
            user1.addListeners(ListenerAdapter {
                onRoomMessageReceived {
                    assertEquals(it.message.content.body, text)
                    syncJob.cancelAndJoin()
                }
            })
            room.sendTextMessage(text)
            syncJob.join()
        }
    }

    companion object {

    }
}
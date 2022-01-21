import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.listeners.ListenerAdapter
import de.mtorials.dialphone.core.sendTextMessage
import kotlinx.coroutines.*
import kotlinx.coroutines.GlobalScope.coroutineContext
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals


class JvmTest {

    private val text = Helper.getRandomName(50)

    lateinit var dialPhone: DialPhone

    @Before
    fun `register and login as user`() {
        runBlocking {
            DialPhone(HOMESERVER_URL) {
                asUser(TEST_USER, TEST_PWD, true)
            }
            // TODO remove rate limit in synapse
            delay(1000)
            dialPhone = DialPhone(HOMESERVER_URL) {
                asUser(TEST_USER, TEST_PWD)
            }
        }
    }

    @Test
    fun `check guest access`() {
        runBlocking {
            DialPhone(HOMESERVER_URL) {
                asGuest()
            }
        }
    }

    @Test
    fun `create room`() {
        val syncJob: Job = dialPhone.sync()
        runBlocking {
            println("started syncing!")
            delay(100)
            val name = Helper.getRandomName(10)
            val room = dialPhone.createRoom(name) {
                topic = "This is a room topic"
                makePublic()
            }
            assertEquals(room.receive().name, name)
            dialPhone.addListener(ListenerAdapter {
                onRoomMessageReceived {
                    assertEquals(it.message.body, text)
                    syncJob.cancelAndJoin()
                }
            })
            room.sendTextMessage(text)
            syncJob.join()
        }
    }

    companion object {
        const val HOMESERVER_URL = "http://localhost:8008"
        const val TEST_USER = "helloasd"
        const val TEST_PWD = "superduper"
    }
}
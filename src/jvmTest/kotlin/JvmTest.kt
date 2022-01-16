import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.DialPhoneBuilder
import de.mtorials.dialphone.authentication.Login
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals


class JvmTest {

    lateinit var dialPhone: DialPhone

    @Test
    fun `register user`() {
        runBlocking {
            dialPhone = DialPhone("http://localhost:8008") {
                asUser("test", "test", true)
            }
        }
    }

    @Test
    fun `send and receive`() {
        assertEquals("Hi", "Hi")
    }

    companion object {
        const val TEST_USER = "helloasd"
        const val TEST_PWD = "superduper"
    }
}
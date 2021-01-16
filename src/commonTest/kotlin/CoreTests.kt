import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.listener.MessageListener
import de.mtorials.dialphone.sendTextMessage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.test.assertEquals

class CoreTests {

    suspend fun basicTestWithToken(config: Config) {
        println("hello")
        val phone = DialPhone {
            withToken(config.token)
            homeserverUrl = config.homeserverUrl
        }
        println("created dialphone")
        phone.addListener(MessageListener(false) {
            println(it.message.body)
            assertEquals("hi", it.message.body)
        })
        phone.sync()
        //phone.getRoomByAlias("#open:mtorials.de").join().sendTextMessage("hi!")
    }
}
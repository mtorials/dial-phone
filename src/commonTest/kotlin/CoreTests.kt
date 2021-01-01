import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.sendTextMessage

class CoreTests {

    suspend fun basicTestWithToken(config: Config) {
        val phone = DialPhone {
            withToken(config.token)
            homeserverUrl = config.homeserverUrl
        }
        phone.sync()
        phone.getRoomByAlias("#open:mtorials.de").join().sendTextMessage("hi!")
        println("sent")
    }
}
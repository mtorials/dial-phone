package de.mtorials.dpexample

import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.sendTextMessage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlin.test.Test

class CoreTests {

    suspend fun getPhone() : DialPhone {
        return DialPhone {
            isGuest()
            homeserverUrl = "https://matrix.mtorials.de"
        }
    }

    @Test
    fun test1() {
        val job = GlobalScope.launch {
            val phone = getPhone()
            phone.sync()
            phone.getRoomByAlias("#open:mtorials.de").join().sendTextMessage("hi!")
            println("sent")
        }
        awaitAll()
    }
}
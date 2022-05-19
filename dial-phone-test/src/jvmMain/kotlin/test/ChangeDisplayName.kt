package test

import de.mtorials.dialhone.bot.Command
import de.mtorials.dialhone.bot.CommandListener
import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.api.model.enums.Membership
import de.mtorials.dialphone.api.model.mevents.roomstate.MRoomMember
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.dialevents.answer
import de.mtorials.dialphone.core.listeners.ListenerAdapter
import de.mtorials.dialphone.core.sendTextMessage
import de.mtorials.dialphone.encyption.useEncryption
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import java.util.*

const val MATRIX_SERVER = "https://matrix.mtorials.de"

@kotlinx.serialization.Serializable
data class ChangeConfig(val token: String, val username: String, val password: String)
val changeConfig : ChangeConfig = Json.decodeFromString(File("config.json").readText())

val ping = Command("ping", "ping the bot", "ping") {
    this answer "pong!"
}

val changeName = Command("cname", "change my display name in this room", "cname <new_name>") {
    if (it.isEmpty()) {
        this answer "Ich brauch nen namen!"
        return@Command
    }
    room.sendStateEvent(MRoomMember.Content(
        Membership.JOIN,
        null,
        displayName = it[0],
    ), "m.room.member")
    this answer "Did this work?"
}


fun main() {
    runBlocking {
        val phone = DialPhone(MATRIX_SERVER) {
            withToken(changeConfig.token)
            addListeners(CommandListener("!", listOf(ping, changeName), ping))
//                useEncryption()
        }
//            val job = phone.sync()
//                delay(10000)
        println("Hello")
//            delay(5000)
        println(phone.getJoinedRooms().map { it.name })
        val trium = phone.getJoinedRoomByName("temp trium machina") ?: error("404")
//            trium.sendStateEvent(MRoomMember.Content(
//                Membership.JOIN,
//                null,
//                displayName = "mt",
//            ), "m.room.member")
        trium.sendTextMessage("try !cname and !ping")
        phone.sync().join()
    }
}
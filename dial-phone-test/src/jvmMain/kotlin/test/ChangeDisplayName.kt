package test

import de.mtorials.dialphone.api.model.enums.Membership
import de.mtorials.dialphone.api.model.mevents.roomstate.MRoomMember
import de.mtorials.dialphone.core.DialPhone
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import java.util.*

const val MATRIX_SERVER = "https://matrix.mtorials.de"

@kotlinx.serialization.Serializable
data class ChangeConfig(val token: String)
val changeConfig : ChangeConfig = Json.decodeFromString(File("config.json").readText())

    fun main() {
        runBlocking {
            val phone = DialPhone(MATRIX_SERVER) {
                withToken(changeConfig.token)
            }
//            val job = phone.sync()
//                delay(10000)
            println("Hello")
//            delay(5000)
            println(phone.getJoinedRooms().map { it.name })
//            phone.getJoinedRooms().filter { room ->
//                room.name?.lowercase(Locale.getDefault())?.startsWith("temp trium") ?: false
//            }[0].sendStateEvent(MRoomMember.Content(
//                Membership.JOIN,
//                null,
//                displayName = "keine sorge ben, manchmal klappt mein code...",
//            ), "m.room.member")
        }
    }
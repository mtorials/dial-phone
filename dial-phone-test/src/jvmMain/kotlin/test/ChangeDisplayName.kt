package test

import de.mtorials.dialphone.api.model.enums.Membership
import de.mtorials.dialphone.api.model.mevents.roomstate.MRoomMember
import de.mtorials.dialphone.core.DialPhone
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

const val MATRIX_SERVER  = "https://matrix.mtorials.de"

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun main() {
        runBlocking {
            val phone = DialPhone(MATRIX_SERVER) {
                withToken("")
            }
            val job = phone.sync()
            job.join()
            println(phone.getJoinedRooms())
        }

//        phone.getJoinedRooms().filter { room ->
//            room.name.lowercase(Locale.getDefault()).startsWith("temp trium")
//        }[0].sendStateEvent(MRoomMember.Content(
//            Membership.JOIN,
//            null,
//            displayName = "the testcase",
//        ), "m.room.member")
    }
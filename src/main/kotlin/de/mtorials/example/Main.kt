package de.mtorials.example

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.entities.Room
import de.mtorials.dialphone.mevents.roommessage.MRoomMessage
import de.mtorials.dialphone.mevents.roommessage.MessageEventContent
import de.mtorials.dialphone.mevents.roomstate.MRoomName
import de.mtorials.dialphone.rename
import de.mtorials.example.cutstomevents.PositionEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File

// Simple config Map
val config : Map<String, String> =
    jacksonObjectMapper().readValue(File("config.json").readText(Charsets.UTF_8))

fun main() {

    val phone = DialPhone(
        homeServerURL = config["homeServerUrl"] ?: throw Error(),
        token = config["matrixToken"] ?: throw Error(),
        listeners = listOf(ExampleListener(), PositionListener(), StateListener()),
        customEventTypes = arrayOf(PositionEvent::class)
    )

    val job1 = GlobalScope.launch { phone.sync() }

    val myRoom : Room
    runBlocking {
        myRoom = (phone.getJoinedRoomFutureById("!YIqYutrrBUdGDombnI:mtorials.de")
            ?: throw RuntimeException("Not Found")).receive()
        myRoom.members.forEach { member -> println(member.displayName) }
        println(myRoom.avatarUrl)
        println(myRoom.name)
    }
    myRoom rename "The Name!"
    runBlocking {
        job1.join()
    }
}
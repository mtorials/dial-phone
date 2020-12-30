package de.mtorials.dpexample

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.mtorials.dialphone.*
import de.mtorials.dialphone.entities.Room
import de.mtorials.dpexample.cutstomevents.PositionEvent
import de.mtorials.dpexample.cutstomevents.TestStateEvent
import java.io.File
import java.util.*

// Simple config Map
val config : Map<String, String> =
    jacksonObjectMapper().readValue(File("config.json").readText(Charsets.UTF_8))

const val roomId: String = "!YIqYutrrBUdGDombnI:mtorials.de"

suspend fun main() {
    val phone = DialPhone {
        homeserverUrl = config["homeServerUrl"] ?: throw Error()
        client withToken (config["matrixToken"] ?: throw Error())
        client hasCommandPrefix "!"
        addListeners(
            PrintChat(roomId),
            ExampleListener()
            //StateListener()
        )
        addCustomEventTypes(
            PositionEvent::class,
            TestStateEvent::class
        )
    }

    //runBlocking { test(phone) }
    val room = phone.getJoinedRoomFutureById(roomId) ?: error("Room not found!")
    val scanner = Scanner(System.`in`)

    val openRoomFuture = phone.getRoomByAlias("#open:mtorials.de").join()
    phone.getJoinedRoomFutures()

    openRoomFuture.receive().members[0].receive().displayName.let {
        println(it)
    }

    phone.sync()
    
    //test(phone)
    while(true) {
        val input = scanner.nextLine()
        if (input == "exit") break
        room.sendTextMessage(input)
        checkCache(phone)
    }
}

const val userid = "@mtorials:mtorials.de"

suspend fun checkCache(phone: DialPhone) {
    println(phone.getUserById(userid)?.displayName)
}

suspend fun test(phone: DialPhone) {
    val myRoom: Room = (phone.getJoinedRoomFutureById(roomId)
        ?: throw RuntimeException("Not Found")).receive()
    myRoom.members.forEach { member -> println(member.receive().displayName) }
    println(myRoom.avatarUrl)
    println(myRoom.name)
    val dn = phone.getUserById("@mtorials:mtorials.de")?.displayName
    println(dn)
    myRoom.sendHtmlMessage("<h1>Hallo!</h1>")
}
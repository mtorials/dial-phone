package de.mtorials.dpex2

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.sendTextMessage
import java.io.File


// Simple config Map
val config : Map<String, String> =
    jacksonObjectMapper().readValue(File("config.json").readText(Charsets.UTF_8))

suspend fun main() {
    val phone = DialPhone {
        homeserverUrl = de.mtorials.dpexample.config["homeServerUrl"] ?: throw Error()
        isGuest()
    }

    println("Your current display name is ${phone.profile.receiveDisplayName()}.")

    phone.profile.setDisplayName("#BestGuest")

    phone.getJoinedRoomFutures().forEach {
        print("Room $it")
    }
    val roomFuture = phone.discoverRooms()[0].first.join()
    roomFuture.sendTextMessage("Hi!")

    phone.syncBlocking()
}

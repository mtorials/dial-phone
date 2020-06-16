package de.mtorials.dialphone.entities

class Message (
    val body: String,
    //val format: String = "org.matrix.custom.html",
    val msgtype: String = "m.text"
)
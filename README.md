<a href="https://matrix.org" target="_blank">
<img align="right" width="98" height="41" src="https://matrix.org/docs/projects/images//made-for-matrix.png"></a>

# DialPhone

![dialphone-logo](https://raw.githubusercontent.com/mtorials/dial-phone/master/logo.png)

A simple-to-use [Matrix](https://matrix.org/) client-server sdk for kotlin multiplatform in alpha.
The library uses kotlinx.serialization and kotlinx.coroutines.

Supported platforms are jvm and js, but testing is mostly done on the jvm.

See my [dial-bot](https://github.com/mtorials/dialbot) repository for a reference bot implementation.
(outdated currently)

Basic e2ee is implemented, but very experimental, not secure, and it only works on the jvm.

## Installation

The library is currently published via my gitlab instance.

#### Gradle

Releases are [here](https://git.mt32.net/mtorials/dial-phone/-/packages/).

Add to repositories:

```kotlin
// build.gradle.kts
maven { url = uri("https://git.mt32.net/api/v4/projects/59/packages/maven") }
```

Add to dependencies:

Always check for a newer version!

```kotlin
// build.gradle.kts
implementation("de.mtorials.dail-phone:dial-phone-api:0.3.0")
implementation("de.mtorials.dail-phone:dial-phone-core:0.3.0")
// If you want to use encryption (jvm only and AGPL license!!!)
implementation("de.mtorials.dail-phone:dial-phone-encryption:0.3.0")
// If you want to use bot features, not well-supported at the moment
implementation("de.mtorials.dail-phone:dial-phone-bot:0.3.0")
// If you are interested in the matrix-sdk-crypto bindings
implementation("de.mtorials.dail-phone:dial-phone-olm-machine:0.3.0")
// A ktor client of your choice, here okhttp
// please check for a newer version!
implementation("io.ktor:ktor-client-okhttp:1.5.0")
```

## Documentation

Specific documentation can be found in `/docs`. At the time there is:

- [Events](docs/events.md)
- [Login and Registration](docs/login.md)
- [Building](docs/BUILDING.md)

## Getting Started

To use the SDK first create the DialPhone object.

Use can create a guest account or login with a access token you can receive for example from the element client.
Login and registration with username and password will be available in the future.
```kotlin
val phone = DialPhone("<HOMESERVER_URL>") { // this: DialPhoneBuilder
    asUser("myusername", "mypassword") // login with credentials
    addListeners(
        ExampleListener(),
        SecondExampleListener()
    )
    useEncryption() // To enable E2EE (experimental)
}

```
After initializing the `DialPhone` object one sync request is made to update the state of the sdk.

To receive new events you have to start syncing.

If you want to block your method after this use you can call the join() on the returned coroutine job.

```kotlin
val syncJob = phone.sync()
syncJob.join();
```

### A simple command line client

```kotlin
fun main() = runBlocking {

    var activeRoom: JoinedRoom? = null

    val phone = DialPhone(MATRIX_SERVER) {
        asUser("name", "password")
        addListeners(ListenerAdapter {
            onRoomMessageReceived listener@{
                if (activeRoom?.id != it.room.id) return@listener
                it.run { println("${room.name} :: ${message.author.displayName ?: message.author.id} :: ${message.content.body}") }
            }
        })
    }.apply { sync() }

    while(true) {
        val input = readLine() ?: continue
        when (input) {
            "!rooms" -> phone.getJoinedRooms().run {
                forEachIndexed { index, joinedRoom ->
                    println("$index: ${joinedRoom.name}")
                }
                println("Select room by number: ")
                activeRoom = this[readLine()?.toInt() ?: return@run]
                println("---- Selected room is ${activeRoom?.name} ----")
            }
            else -> activeRoom?.sendTextMessage(input)
        }
    }
}
```

## IDs

Ids have wrapper classes for extra type safety:

```kotlin
val userId = UserId("@mtorials:mtorials.de")
```

## Entities

There are several types of room entity, depending on the join state.

```kotlin
val myRoom: JoinedRoom = phone.getJoinedRoomById("!YIqYutrrBUdGDombnI:mtorials.de")
    ?: error("Room Not Found!")
```

You can perform actions on these entities and get data, like getting the name or sending a message:

```kotlin
myRoom.sendTextMessage("Hello World!")
// Log all members of the room in the console
println(myRoom.members.map { it.displayName ?: it.id })
```

All events and entities also have a `phone` property to access the `DialPhone` object.

### Reacting to an event

There are (infix) extension functions that allow you to react to an DialPhone event easily.

```kotlin
// if event is RoomMessageReceivedEvent
event answer "I received a message!"
```

### Redacting

To delete a message the message class has a according method:

```kotlin
val m: Message // could be for instance event.message
m.redact()
```

To redact other types of events you have to use this function:

```kotlin
val phone: DialPhone // phone is a property of every entity in the library
phone.requestObject.redactEventWithIdInRoom(roomId = roomId, id = eventId)
```

## Rooms

### Create a room 

To create a room use the room builder:

```kotlin
val room = dialPhone.createRoom(name) {
    topic = "This is a room topic"
    alias = "alias"
    makePublic()
    invite(someUser)
}
```

### Invite Listener and Join

This example listens for an invite event and joins the room.

```kotlin
val inviteListener = ListenerAdapter {
    onRoomInvited { event ->
        event.room.join()
    }
}
```
# Dependencies

All packages depend on kotlinx.coroutines and kotlinx.serialization.

I use the `rust-sdk-crypto-ffi` bindings for kotlin to use the olm state machine.
See the [matrix-rus-sdk repo](https://github.com/matrix-org/matrix-rust-sdk).

# Contact

If you want to contact me join the [#dial-phone:mtorials.de](https://matrix.to/#/#dial-phone:mtorials.de) room.
You can of course also just open an issue.

# Contributing

Everyone is welcome to contribute.
See the [CONTRIBUTING.md](CONTRIBUTING.md) for more information.
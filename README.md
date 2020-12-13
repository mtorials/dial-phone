<a href="https://matrix.org" target="_blank">
<img align="right" width="98" height="41" src="https://matrix.org/docs/projects/images//made-for-matrix.png"></a>

# DialPhone

[![](https://jitci.com/gh/mtorials/dial-phone/svg)](https://jitci.com/gh/mtorials/dial-phone)

*Work in progress*


![dialphone-logo](https://raw.githubusercontent.com/mtorials/dial-phone/master/logo.png)


A simple to use [Matrix](https://matrix.org/) client-server API (CS-API) SDK for the JVM written in Kotlin.
The library uses Jackson and Kotlin coroutines.

See my [dial-bot](https://github.com/mtorials/dialbot) repository for a reference bot implementation.

## Installation

The library is published with [JitPack]("https://jitpack.io/").

#### Maven

Add the repo:

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

Add the dependencies:

```xml
<dependency>
    <groupId>de.mtorials</groupId>
    <artifactId>dial-phone</artifactId>
    <version>SEE RELEASES</version>
</dependency>
```

#### Gradle

Add to repositories:

```groovy
maven { url 'https://jitpack.io' }
```

Add to dependencies:

```groovy
implementation 'de.mtorials:dial-phone:<SEE_RELEASES>'
```

## Getting Started

To use the SDK first create the DialPhone object.

Use can create a guest account or login with a access token you can receive for example from the element client.
Login and registration with username and password will be availibe in the future.
```kotlin
val phone = DialPhone { // this: DialPhoneBuilder
    homeserverUrl = "<YOUR_HOMESERVER_URL>" // This is 
    client withToken "<YOUR TOKEN>" // If you want to login with an access token
    isGuets() // If you want to create a guest account
    client hasCommandPrefix "!" // If you want to use the command listener, default is "!"
    addListeners {
        add(ExampleListener())
        // use add() to add as many listeners you want
    }
    addCustomEventTypes {
        add(TestStateEvent::class)
        // use add to add more custom events
    }
}

```

To receive events you have to start syncing. Do not forget to wait for the returned job. Otherwise, your code
will just stop executing at the end of the main function.

```kotlin
val syncJob = phone.sync()
// ...
syncJob.join()
```

## Entities

To get a room you first have to get the corresponding entity future object.
```kotlin
val myRoomFuture : RoomFuture = phone.getJoinedRoomFutureById("!YIqYutrrBUdGDombnI:mtorials.de")
    ?: throw Exception("Not Found!")
```
You can perform actions on the entity future. To get the entity data you have to receive it first:
```kotlin
myRoomFuture.sendMessage("Hi!")
val myRoom : Room = myRoomFuture.receive()
println(myRoom.name)
```
All events, entity futures and entities also have a `phone` property to access the `DialPhone` object.

## Events

### Types of Events

There are classes that represent native Matrix events, all inheriting from `MatrixEvent`, but there are also DialPhone
events. These DialPhone events offer a higher level api and are more convenient to use.

#### DialPhone Events

- inherit from `DialEvent`
- are passed to the `ListenerAdapter`
- contain the `DialPhone` object

These events are passed to the ListenerAdapter.

#### Matrix Events

- inherit from `MatrixEvent`
- there are multiple sub types (`RoomStateEvent`, `RoomMessageEvent`)
- can be sent by using the `sendMessageEvent` or `sendStateEvent` method on `RoomAction`.
- you can use custom matrix events (see *Custom Events*)

See the matrix.org specifications on how Matrix events work.

### Listening for Events

To react to events you have to implement either the `Listener` interface
or one of the abstract classes `Listener Adapter` or `Command Adapter` if you want to use
the command feature of this SDK to use it as a bot.
You can pass these as parameters to the `DialPhone` constructor or add them later with:

```kotlin
phone.addListener(MyListener())
```

You can set in the constructor of each of these abstract classes 
whether you want to listen to new events only or also receive past events on startup.

### Sending Events

You can use the `sendMessageEvent` or `sendStateEvent` method on every type which inherits from RoomActions.
These are RoomFuture and Room.
To send the event you have to instantiate the Content class inside the actual event class.
Only matrix events are supported.

```kotlin
myRoomFuture.sendMessageEvent(MRoomMessage.Content("Hi!"))
```

You can also use the extension functions to send specific events like for example:

```kotlin
myRoomFuture.sendTextMessage("Hi!")
```

In this case both examples are equivalent.

#### Reacting to an event

There are (infix) extension functions that allow you to react to an DialPhone event easily.

```kotlin
// if event is RoomMessageReceivedEvent
event answer "I received a message!"
```

#### Redacting

To delete a message the message class has a according method:

```kotlin
val m: Message // could be for instance event.message
m.redact()
```

To redact other types of events you have you have to use this function:

```kotlin
val phone: DialPhone // phone is a property of every entity in the library
phone.requestObject.redactEventWithIdInRoom(roomId = roomId, id = eventId)
```

## Rooms

Room menagement is not fully implemented yet.

### Invite Listener and Join

This example listens for an invite event and joins the room.

```kotlin
class InviteListener : ListenerAdapter() {
    override suspend fun onRoomInvite(event: RoomInviteEvent) {
        event.invitedRoomActions.join()
    }
}
```

## Custom Events

This library has the ability to send custom events. To do so, you have to first create a class inheriting from
the `MatrixEvent` interface or sub classes depending on what type of event you want to create.
The content field has to be an extra class marked with the according `EventContent` interface.
To specify the name of the event use the `@JsonTypeName` annotation of the Jackson library.
Use the `@ContentEventType` annotation to specify the according event type of the `EventContent` type.

An example of a room message event that carries a positional data (x,y,z coordinates):
```kotlin
@JsonTypeName("com.example.matrix.event.position")
class PositionEvent(
    override val sender: String,
    @JsonProperty("event_id")
    override val id: String,
    override val content: Content
) : MatrixMessageEvent {
    @ContentEventType(PositionEvent::class)
    data class Content(
        val x: Int,
        val y: Int,
        val z: Int
    ) : MessageEventContent
}
```

If you want to receive your custom events you have to register these when creating the `DailPhone` object.

### Send Custom Events

See *Sending Events*.

### Receive Custom Events

To receive custom events you have to implement the interface `Listener` directly:
With the second constructor parameter you can control wether you want to receive past events also.

```kotlin
class CustomListener : MatrixEventAdapter<MyEvent>(MyEvent::class, true) {
    override fun onMatrixEvent(event: MyEvent, roomFuture: RoomFuture) {
        println("Received custom event with payload ${event.content.payload}.")
    }
}
```
# Contact

If you want to contact me join the [#dial-phone:mtorials.de](https://matrix.to/#/#dial-phone:mtorials.de) room.
You can of course also just open an issue.

# Contributing

Everyone is welcome to contribute. See the [CONTRIBUTING.md](https://github.com/mtorials/dial-phone/blob/master/CONTRIBUTING.md) for more information.

# TODO

- support for the most used Matrix events
- complete room management
- support for all Matrix events
- DialPhone events for the most used native Matrix events
- usability as full client
- lazy loading
- group management


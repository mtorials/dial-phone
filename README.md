# DialPhone

*Work in progress*

A simple to use [Matrix](https://matrix.org/) client-server API (CS-API) SDK written in Kotlin for JVM.
The library uses Jackson and Kotlin coroutines. This SDK is not (yet) intended to be used as full client.
It does only listen to new events and does not keep state of these.

## Getting Started

Currently, you have to include the SDK as jar file. Download it under releases (if available).

To use the SDK first create the DialPhone object.
```kotlin
val phone = DialPhone(
    homeServerURL = "matrix.example.com",
    token = "Your token",
    // Optional. If you want to use it as bot, ! by default
    commandPrefix = "&",
    // Optional, see Listeners
    listeners = listOf(MyListener1(), MyListener2())
)
```

To receive events you have to start syncing.

```kotlin
runBlocking {
    phone.sync()
}
```

## Entities

To get a Room or User you first have to get the corresponding EntityFuture object.
```kotlin
val myRoomFuture : RoomFuture = phone.getJoinedRoomFutureById("!YIqYutrrBUdGDombnI:mtorials.de")
    ?: throw Exception("Not Found!")
```
You can perform actions on the EntityFuture. To get the entity data you have to receive it first:
```kotlin
myRoomFuture.sendMessage("Hi!")
val myRoom : Room = myRoomFuture.receive()
println(myRoom.name)
```
All events, entity futures and entities also have a `phone` property to access the `DialPhone` object.

## Events

### Types of events

There classes that represent native Matrix events, all inheriting from `MatrixEvent`, but there are also DialPhone
events. These DialPhone events offer a higher level api and are more convenient to use.

#### DialPhone Events

- inherit from `DialEvent`
- are passed to the `ListenerAdapter`
- contain the `DialPhone` object

These events are passed to the ListenerAdapter and represent a native Matrix event.

#### Matrix Native Events

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

## Custom Events

This library has the ability to send custom events. To do so, you have to first create a class inheriting from
the `MatrixEvent` interface or sub classes depending on what type of event you want to create.
The content field has to be an extra class marked with the according `EventContent` interface.
To specify the name of the event use the `@JsonTypeName` annotation of the Jackson library.
Use the `@ContentEventType` annotation to specify the according event type of the `EventContent` type.

An example for a room message event.
```kotlin
@JsonTypeName("com.example.matrix.events.myevent")
class MyEvent(
    sender: String,
    content: Content
) : MatrixMessageEvent(sender, content) {
    @ContentEventType(MyEvent::class)
    data class Content(
        val payload: String
    ) : MesssageEventContent
}
```

If you want to receive your custom events you have to register these when initializing `DailPhone`.

```kotlin
val phone = DialPhone(
    homeServerURL = "matrix.example.com",
    token = "Your token",
    customEventTypes = arrayOf(MyEvent::class)
)
```

### Send Custom Events

See *Sending Events*.

### Receive Custom Events

To receive custom events you have to implement the interface `Listener` directly or
the abstract class `MatrixEventAdapter`:

```kotlin
class CustomListener : MatrixEventAdapter<MyEvent>(MyEvent::class) {
    override fun onMatrixEvent(event: MyEvent, roomFuture: RoomFuture) {
        println("Received custom event with payload ${event.content.payload}.")
    }
}
```

# Possible Future Features

- support for the most used native Matrix events
- support for all native Matrix events
- error handling
- DialPhone events for most used native Matrix events
- usability for full clients
- Application API support
- lazy loading
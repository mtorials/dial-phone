# Events

## Types of Events

There are classes that represent native Matrix events, all inheriting from `MatrixEvent`, but there are also DialPhone
events. These DialPhone events offer a higher level api and are more convenient to use.

## DialPhone Events

- inherit from `DialEvent`
- are passed to the `ListenerAdapter`
- contain the `DialPhone` object

These events are passed to the ListenerAdapter.

## Matrix Events

- inherit from `MatrixEvent`
- there are multiple subtypes (`RoomStateEvent`, `RoomMessageEvent`)
- can be sent by using the `sendMessageEvent` or `sendStateEvent` method on `RoomAction`.
- you can use custom matrix events (see *Custom Events*)

See the matrix.org specifications on how Matrix events work.

## Listening for Events

To react to events you have to implement either the `ApiListener` interface
or instance the `Listener Adapter`. The `ApiListener` wil pass low level MatrixEvents, while the Listener Adapter
will offer you higher level DialPhoneEvents.

You can create a Listener Adapter like this:

```kotlin
val myListener = ListenerAdapter {
    onRoomInvited { event ->
        event.actions.join()
        event.room.sendTextMessage("Hey, I join everywhere!")
    }
    onRoomMessageReceived { event ->
        if (event.message.body == "ping") event answer "pong!"
    }
}
```

You can pass all types of listeners as parameters to the `DialPhone` constructor or add them later with:

```kotlin
phone.addListener(MyListener())
```

You can set in the constructor of each of these abstract classes
whether you want to listen to new events only or also receive past events on startup.

## Sending Events

You can use the `sendMessageEvent` or `sendStateEvent` method on every type which inherits from RoomActions.
These are RoomFuture and Room.
To send the event you have to instantiate the Content class inside the actual event class.
Only matrix events can be sent this way.

```kotlin
myRoomFuture.sendMessageEvent(MRoomMessage.Content("Hi!"))
```

You can also use the extension functions to send specific events like for example:

```kotlin
myRoomFuture.sendTextMessage("Hi!")
```

In this specific case both examples are equivalent.

## Custom Events

This library has the ability to send and receive custom events.
To do so you first have to create a class inheriting from
the `MatrixEvent` interface or subclasses depending on what type of event you want to create.
The content field has to be an extra class marked with the according `EventContent` interface.
To specify the name of the event use the `@SerialName` annotation of the kotlinx.serialization library.
Use the `@ContentEventType` annotation to specify the according event type of the `EventContent` type.

An example of a room message event that carries a positional data (x,y,z coordinates):
```kotlin
@Serializable
class PositionEvent(
    override val sender: String,
    @SerialName("event_id")
    override val id: String,
    override val content: PositionEventContent
) : MatrixMessageEvent {
    @Serializable
    data class PositionEventContent(
        val x: Int,
        val y: Int,
        val z: Int
    ) : MessageEventContent
}
```

To use your custom events you have to register these when creating the `DailPhone` object.
This is done with a `SerializersModule` and may look something like this:

```kotlin
val mySerializerModule = SerializersModule {
    polymorphic(MatrixMessageEvent::class) {
        subclass(PositionEvent::class)
    }

    polymorphic(MessageEventContent::class) {
        subclass(PositionEventContent::class)
    }
}

// Add it in the builder like this:
DialPhone("https://matrix.exmaple.org") {
    addCustomSerializersModule(mySerializerModule)
}
```

### Send Custom Events

Custom events can be sent just as easily as other matrix events. See *Sending Events*.

### Receive Custom Events

To receive custom events you have to implement the interface `Listener` directly:
With the second constructor parameter you can control if you want to receive past events too.

```kotlin
class CustomListener : MatrixEventAdapter<MyEvent>(MyEvent::class, true) {
    override fun onMatrixEvent(event: MyEvent, roomFuture: RoomFuture) {
        println("Received custom event with payload ${event.content.payload}.")
    }
}
```

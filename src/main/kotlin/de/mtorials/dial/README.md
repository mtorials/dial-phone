# DialPhone

A simple to use [Matrix](https://matrix.org/) client-server API (CS-API) SDK written in Kotlin for JVM
using coroutines.

## Getting Started

Currently, you have to include the SDK as jar file. Download it under releases.

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
### Listening for Events

To react to events you have to implement either the `Listener` interface
or one of the abstract classes `Listener Adapter` or `Command Adapter` if you want to use
the command feature of this SDK to use it as a bot.
You can pass these as parameters of the `DialPhone` constructor or by adding them with:
All events and entities also have a `phone` property to access the `DialPhone` object.

```kotlin
phone.addListener(MyListener())
```

### Sending Events

#### Reacting to an event

There are infix extension functions that allow you to react to an event easily.

```kotlin
// RoomMessageReceivedEvent
event answer "I received a message!"
```
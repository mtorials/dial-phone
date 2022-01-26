# Login and Registration

There are several methods to sign in to your account. You can (of course) only choose one method.
For bots, it is recommended to use an access token, but you can also sign in as guest if the homeserver allows
guest sign up and with credentials. If you pass a true `createUserIfNotRegistered` parameter to the `asUser` function
the builder will register the user if it does not already exist.

```kotlin
DialPhone("http://matrix.homeserver.org") {
    withToken("<YOUR_TOKEN>") // If you want to login with an access token
    asGuets() // If you want to create a guest account
    asUser("username", "password") // as user with credentials
    asUser("I want username", "I want password", true) // if not existent, create user
}
```

# Logout

*Currently not implemented*
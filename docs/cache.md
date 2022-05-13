# Caching

## Matrix State

dial-phone-core caches the (matrix) state of every room. Entities access this cache to receive information, like getting
members, the name of a room, etc. This drastically reduces the networks requests that are necessary to retrieve the same
information from the server, but comes at the expanse of memory.

When implementing the interface, make sure to replace state events if they habe matching room ids and state keys.

## Timeline cache

Not yet implemented
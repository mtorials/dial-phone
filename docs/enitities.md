# Entities

## Notes

0. Caches only (if in use) room Ids and states and MessageCache caches message
1. Entity has a Listener, listens for state events
2. On entity creation -> check cache, fallback is request
3. listener updates the entity
4. entity has method to force update (request)
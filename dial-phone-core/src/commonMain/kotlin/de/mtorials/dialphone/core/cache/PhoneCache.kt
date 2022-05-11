package de.mtorials.dialphone.core.cache

interface PhoneCache {
    val roomCache: RoomCache
    val messageCache: MessageCache?
    val userCache: UserCache
}
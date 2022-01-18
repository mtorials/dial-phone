package de.mtorials.dialphone.core

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.core.cache.PhoneCache

interface CachedDialPhone : DialPhoneApi {
    val cache: PhoneCache
}
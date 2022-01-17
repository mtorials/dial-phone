package de.mtorials.dialphone.core

class Profile(
    private val phone: DialPhoneImpl
) {

    suspend fun receiveDisplayName() : String? {
        return phone.requestObject.getMe().displayName
    }

    suspend fun setDisplayName(displayName: String) {
        phone.requestObject.setDisplayName(phone.ownId, displayName)
    }
}
package de.mtorials.dialphone.api

class Profile(
    private val phone: DialPhoneApiImpl
) {

    suspend fun receiveDisplayName() : String? {
        return phone.requestObject.getMe().displayName
    }

    suspend fun setDisplayName(displayName: String) {
        phone.requestObject.setDisplayName(phone.ownId, displayName)
    }
}
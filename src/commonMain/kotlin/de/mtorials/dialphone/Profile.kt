package de.mtorials.dialphone

class Profile(
    private val phone: DialPhoneImpl
) {
    suspend fun receiveDisplayName() : String? {
        return phone.requestObject.getMe().displayName
    }

    suspend fun setDisplayName(displayName: String) {
        phone.requestObject.setDisplayName(receiveID(), displayName)
    }

    suspend fun receiveID(): String {
        return phone.requestObject.getMe().id
    }
}
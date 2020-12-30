package de.mtorials.dialphone

class Profile(
    private val phone: DialPhoneImpl
) {

    private var ownId: String? = null

    suspend fun receiveDisplayName() : String? {
        return phone.requestObject.getMe().displayName
    }

    suspend fun setDisplayName(displayName: String) {
        phone.requestObject.setDisplayName(receiveID(), displayName)
    }

    suspend fun receiveID(): String {
        if (ownId == null) ownId = phone.requestObject.getMe().id
        return ownId!!
    }
}
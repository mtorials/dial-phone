package de.mtorials.dialphone

class Profile(
    private val phone: DialPhoneImpl,
    val id: String
) {
    suspend fun receiveDisplayName() : String {
        return phone.requestObject.getDisplayName(phone.ownId).displayName
    }

    suspend fun setDisplayName(displayName: String) {
        phone.requestObject.setDisplayName(id, displayName)
    }
}
package de.mtorials.dialphone.core.encryption

import de.mtorials.dialphone.api.requests.encryption.DeviceKeys
import de.mtorials.dialphone.api.requests.encryption.KeyUploadRequest
import de.mtorials.dialphone.api.requests.encryption.SignedDeviceKeys
import de.mtorials.dialphone.core.DialPhoneImpl
import io.github.matrixkt.olm.Account

// TODO abstract the account management
class EncryptionManager(
    private val keyStore: KeyStore,
    private val phone: DialPhoneImpl,
) {

    private val account = Account()
    private val deviceId = phone.deviceId ?: error("Need device id for encryption")

    suspend fun publishKeys() {
        val identityKeys = account.identityKeys
        val deviceKeys = DeviceKeys(
            // TODO wrong...
            algorithms = listOf("m.olm.v1.curve25519-aes-sha2", "m.megolm.v1.aes-sha2"),
            deviceId = deviceId,
            userId = phone.ownId,
            keys = mapOf(
                "$ED25519:$deviceId" to identityKeys.ed25519,
                "$CURVE25519:$deviceId" to identityKeys.curve25519
            )
        )
        val signedDeviceKeys = SignedDeviceKeys(
            deviceKeys,
            mapOf(phone.ownId to
                    mapOf("$ED25519:$deviceId" to account.sign(EncryptionUtilities.getCanonicalJson(deviceKeys)))
            )
        )
        val request = KeyUploadRequest(
            deviceKeys = signedDeviceKeys,
            // TODO impl
            oneTimeKeys = createOnTimeKeyMap()
        )
        phone.e2eeClient.uploadKeys(request)
        account.markOneTimeKeysAsPublished()
    }

    // TODO signing of the keys!
    private fun createOnTimeKeyMap() : Map<String, String> {
        val onetimes = account.oneTimeKeys
        val keyMap: MutableMap<String, String> = mutableMapOf()
        onetimes.curve25519.forEach { (id, key) ->
            keyMap[id] = key
        }
        return keyMap
    }

    companion object {
        const val ED25519 = "ed25519"
        const val CURVE25519 = "curve25519"
    }
}
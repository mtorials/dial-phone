package de.mtorials.dialphone.core.encryption

import de.mtorials.dialphone.api.E2EEClient
import de.mtorials.dialphone.api.model.enums.MessageEncryptionAlgorithm
import de.mtorials.dialphone.api.model.enums.RoomEncryptionAlgorithm
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import de.mtorials.dialphone.api.model.mevents.roommessage.MRoomEncrypted
import de.mtorials.dialphone.api.model.mevents.todevice.MRoomKey
import de.mtorials.dialphone.api.requests.SendToDeviceRequest
import de.mtorials.dialphone.api.requests.encryption.*
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.exceptions.MalformedEncryptedEvent
import de.mtorials.dialphone.core.exceptions.OlmSessionNotFound
import de.mtorials.dialphone.core.exceptions.RoomKeyHandleException
import de.mtorials.dialphone.core.ids.RoomId
import de.mtorials.dialphone.core.ids.UserId
import de.mtorials.dialphone.core.ids.userId
import io.github.matrixkt.olm.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import kotlin.random.Random

// TODO abstract the account management
// TODO remember account
class EncryptionManager(
    private val store: E2EEStore,
    private val client: E2EEClient,
    private val deviceId: String,
    private val ownId: String,
    private val phone: DialPhone,
    private val dialPhoneJson: Json,
) {

    private val account = Account()

    private fun getOlmSession(senderKey: String) : Session? {
        return store.olmSessionsBySenderKey[senderKey]
    }

    private fun getPreKeySession(senderKey: String, body: String) : Session {
        val oldSession: Session? = getOlmSession(senderKey)
        if (oldSession != null) return oldSession
        // TODO matches inbound session
        val newSession = Session.createInboundSessionFrom(account, senderKey, body)
        account.removeOneTimeKeys(newSession)
        store.olmSessionsBySenderKey[senderKey] = newSession
        return newSession
    }

//    fun decryptMegolm() : String {
//        // TODO use secure random
//        OutboundGroupSession()
//    }

    fun decryptOlm(event: MRoomEncrypted) : MatrixEvent {
        // TODO does this work?
        val cypher: Pair<String, TypeAndBody> = Json.decodeFromString(event.content.cipherText)
        val session: Session = when (cypher.second.type) {
            0 -> getPreKeySession(
                senderKey = event.content.senderKey ?: throw MalformedEncryptedEvent(event),
                body = cypher.second.body
            )
            else -> getOlmSession(
                senderKey = event.content.senderKey ?: throw MalformedEncryptedEvent(event),
            ) ?: throw OlmSessionNotFound(event)
        }
        // Really type and body?
        val plainText = session.decrypt(
            Message.invoke(
            cipherText = cypher.second.body,
            type = cypher.second.type.toLong())
        )
        println(plainText)
        // TODO check sender, recipient, keys, recipient keys
        TODO("impl serialization")
    }

    private suspend fun encryptOlm(
        event: MatrixEvent,
        userId: UserId,
        deviceId: String,
        identityKey: String
    ) : MRoomEncrypted.MRoomEncryptedContent {
        val session = startOlmSession(userId, deviceId, identityKey)
        val plainTextEvent = dialPhoneJson.encodeToString(event)
        // TODO use secure random?
        val encryptedText = session.encrypt(plainTextEvent, Random)
        return MRoomEncrypted.MRoomEncryptedContent(
            senderKey = account.identityKeys.curve25519,
            algorithm = MessageEncryptionAlgorithm.OLM_V1_CURVE25519_AES_SHA1,
            // TODO fix double serialization
            cipherText = dialPhoneJson.encodeToString(mapOf(
                "type" to encryptedText.type,
                "body" to encryptedText.cipherText,
            ))
        )
    }

    suspend fun startOlmSession(userId: UserId, deviceId: String, identityKey: String) : Session {
        val res = client.claimKeys(KeyClaimRequest(
            mapOf(userId.toString() to mapOf(
                deviceId to ED25519
            ))
        ))
        // TODO check the signature
        val oneTimeKey = res.oneTimeKeys[userId.toString()]?.get(deviceId) ?: throw RuntimeException("can get one time key")
        return Session.createOutboundSession(
            account = account,
            theirIdentityKey = identityKey,
            theirOneTimeKey = oneTimeKey
        )
    }

    // necessary when sending encrypted event without established session
    suspend fun startMegolmSession(roomId: RoomId) {
        // TODO call olm_init_outbound_group_session, and store the details of the outbound session for future use.
        val outbound = OutboundGroupSession()
        store.inboundSessionsBySessionId[outbound.sessionId] = InboundGroupSession(outbound.sessionKey)
        val devices = downloadDeviceList(roomId = roomId)
        devices.forEach { (userId, d) ->
            d.forEach { (deviceId, key) ->
                val mRoomKey = MRoomKey(
                    content = MRoomKey.MRoomKeyContent(
                        algorithm = RoomEncryptionAlgorithm.MEGOLM_V1_AES_SHA2,
                        roomId = roomId.toString(),
                        sessionId = outbound.sessionId,
                        sessionKey = outbound.sessionKey,
                    )
                )
                val encrypted = encryptOlm(mRoomKey, userId.userId(), deviceId, key)
                client.sendEventToDevice("m.room.encrypted", SendToDeviceRequest(
                    mapOf(userId to mapOf(deviceId to encrypted))
                ))
            }
        }
    }

    fun decryptMegolm(event: MRoomEncrypted) : MatrixEvent {
        // TODO replay attack message index
        val megolmSession = store.inboundSessionsBySessionId[event.content.sessionId]
            ?: throw RuntimeException("Cant find megolm session to decrypt event")
        val plainText = megolmSession.decrypt(event.content.cipherText)
        println(plainText)
        TODO("no serialization impl for decryption")
    }

    fun handleKeyEvent(event: MRoomKey, senderKey: String) {
        val roomId = event.content.roomId
        val sessionId = event.content.sessionId
        // TODO check if session is known
        // TODO store session
        val session = InboundGroupSession(event.content.sessionKey)
        store.inboundSessionsBySessionId[sessionId] = session
        //downloadDeviceList(roomId.roomId())
    }

    /**
     * userid to device id to device information
     */
    private suspend fun downloadDeviceList(roomId: RoomId) : Map<String, Map<String, String>> {
        val deviceList = client.queryKeys(KeyQueryRequest(
            deviceKeys = phone.getJoinedRoomFutureById(roomId)?.receive()?.members?.associate {
                it.userId.toString() to emptyList()
            } ?: throw RoomKeyHandleException("Cant find room with provided it")
        ))
        // TODO a lot of checks
        return deviceList.deviceKeys ?: throw RuntimeException("No devices Found in device list")
    }

    suspend fun publishKeys() {
        val identityKeys = account.identityKeys
        val deviceKeys = DeviceKeys(
            algorithms = listOf(
                "m.olm.v1.curve25519-aes-sha2",
                "m.megolm.v1.aes-sha2",
            ),
            deviceId = deviceId,
            userId = ownId,
            keys = mapOf(
                "$ED25519:$deviceId" to identityKeys.ed25519,
                "$CURVE25519:$deviceId" to identityKeys.curve25519
            )
        )
        val plainTextToSign = EncryptionUtilities.getCanonicalJson(deviceKeys)
        // TODO remove
        println(plainTextToSign)
        val signedDeviceKeys = SignedDeviceKeys(
            deviceKeys,
            mapOf(ownId to
                    mapOf("$ED25519:$deviceId" to account.sign(plainTextToSign))
            )
        )
        val request = KeyUploadRequest(
            deviceKeys = signedDeviceKeys,
            // TODO impl
            oneTimeKeys = createOnTimeKeyMap()
        )
        // TODO remove
        Json { prettyPrint = true }.encodeToString(request).run { println(this) }
        client.uploadKeys(request)
        account.markOneTimeKeysAsPublished()
    }

    // TODO signing of the keys!
    private fun createOnTimeKeyMap() : Map<String, JsonElement> {
        // TODO use secure random?
        account.generateOneTimeKeys(10, random = Random)
        val curve = account.oneTimeKeys.curve25519
        val keyMap : Map<String, JsonElement> = curve.map { (id, key) ->
            //keyMap["$CURVE25519:$id"] = key
            val preSigned = buildJsonObject {
                put("key", key)
            }
            val signature = account.sign(dialPhoneJson.encodeToString(preSigned))
            "$SIGNED_CURVE25519:$id" to buildJsonObject {
                put("key", key)
                put("signatures", buildJsonObject {
                    put(phone.ownId, buildJsonObject {
                        put("$ED25519:$deviceId", signature)
                    })
                })
            }
        }.toMap()
        return keyMap
    }

    companion object {
        const val ED25519 = "ed25519"
        const val CURVE25519 = "curve25519"
        const val SIGNED_CURVE25519 = "signed_curve25519"
    }
}
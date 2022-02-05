package de.mtorials.dialphone.encyption

import de.mtorials.dialphone.api.E2EEClient
import de.mtorials.dialphone.api.model.enums.MessageEncryptionAlgorithm
import de.mtorials.dialphone.api.model.enums.RoomEncryptionAlgorithm
import de.mtorials.dialphone.api.model.mevents.EventContent
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import de.mtorials.dialphone.api.model.mevents.MRoomEncrypted
import de.mtorials.dialphone.api.model.mevents.todevice.MRoomKey
import de.mtorials.dialphone.api.requests.SendToDeviceRequest
import de.mtorials.dialphone.api.requests.encryption.*
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.encyption.exceptions.MalformedEncryptedEvent
import de.mtorials.dialphone.encyption.exceptions.MissingKeyException
import de.mtorials.dialphone.encyption.exceptions.OlmSessionNotFound
import de.mtorials.dialphone.encyption.exceptions.RoomKeyHandleException
import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.ids.UserId
import io.github.matrixkt.olm.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlin.random.Random

// TODO abstract the account management
// TODO remember account
class EncryptionManager(
    // TODO store has no purpose
    private val store: E2EEStore,
    private val client: E2EEClient,
    private val deviceId: String,
    private val ownId: UserId,
    private val phone: DialPhone,
    private val dialPhoneJson: Json,
) {

    private val account = Account()
    private val keys = account.identityKeys

    /**
     * OLM
     */

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

    private suspend fun startOlmSession(userId: UserId, theirDeviceId: String, theirIdentityKey: String) : Session {
        val res = client.claimKeys(KeyClaimRequest(
            mapOf(userId to mapOf(
                theirDeviceId to SIGNED_CURVE25519
            ))
        ))
        // TODO check the signature
        val theirOneTimeKey = res.oneTimeKeys[userId]?.get(theirDeviceId)?.jsonObject?.entries?.first()?.value
            ?.jsonObject?.get("key")?.jsonPrimitive?.content
                ?: throw RuntimeException("can get one time key")
        val olmSession = Session.createOutboundSession(
            account = account,
            theirIdentityKey = theirIdentityKey,
            theirOneTimeKey = theirOneTimeKey
        )
        // TODO check if identity key (seems to work though)
        store.olmSessionsBySenderKey[theirIdentityKey] = olmSession
        return olmSession
    }

    fun decryptOlm(event: MRoomEncrypted) : MatrixEvent {
        val ct = event.content.cipherText
        if (ct !is JsonObject) throw MalformedEncryptedEvent(event)
        var type: Int? = null
        var body: String? = null
        var senderDeviceKey: String? = null
        ct.forEach { (key, el) ->
            senderDeviceKey = key
            type = (el as JsonObject)["type"]?.jsonPrimitive?.int ?: throw MalformedEncryptedEvent(event)
            body = el["body"]?.jsonPrimitive?.content ?: throw MalformedEncryptedEvent(event)
        }
        if (type == null || body == null || senderDeviceKey == null) throw MalformedEncryptedEvent(event)
        val session: Session = when (type) {
            0 -> getPreKeySession(
                senderKey = event.content.senderKey ?: throw MalformedEncryptedEvent(event),
                body = body!!
            )
            else -> getOlmSession(
                senderKey = event.content.senderKey ?: throw MalformedEncryptedEvent(event),
            ) ?: throw OlmSessionNotFound(event)
        }
        // Really type and body?
        val plainText = session.decrypt(
            Message.invoke(
                cipherText = body!!,
                type = type!!.toLong(),
            )
        )
        // TODO check sender, recipient, keys, recipient keys
        return dialPhoneJson.decodeFromString(plainText)
    }

    private suspend fun encryptOlm(
        eventContent: MRoomKey.MRoomKeyContent,
        theirUserId: UserId,
        theirDeviceId: String,
        theirCurveKey: String,
        theirEDKey: String,
    ) : JsonElement {
        //val fullEventAsJson = dialPhoneJson.encodeToJsonElement(event)
        val session = startOlmSession(theirUserId, theirDeviceId, theirCurveKey)
        val payload = OlmMessagePayload(
            // if not standard json -> trying to get type will result in error
            content = dialPhoneJson.encodeToJsonElement(MRoomKey.MRoomKeyContent.serializer(), eventContent),
            type = MRoomKeyType,
            sender = ownId,
            recipient = theirUserId.toString(),
            recipientKeys = mapOf(
                ED25519 to theirEDKey,
            ),
            keys = mapOf(
                ED25519 to keys.ed25519,
            ),
        )
        val plainTextPayload = dialPhoneJson.encodeToString(payload)
        // TODO use secure random?
        val encryptedText = session.encrypt(plainTextPayload, Random)
        val content = MRoomEncrypted.MRoomEncryptedContent(
            senderKey = keys.curve25519,
            algorithm = MessageEncryptionAlgorithm.OLM_V1_CURVE25519_AES_SHA1,
            cipherText = buildJsonObject {
                // TODO should be theirCruveKey, tring if working with ED
                put(theirCurveKey, buildJsonObject {
                    put("type", encryptedText.type)
                    put("body", encryptedText.cipherText)
                })
            }
        )
        return dialPhoneJson.encodeToJsonElement(MRoomEncrypted.MRoomEncryptedContent.serializer(), content)
    }

    /**
     * MEGOLM
     */

    // necessary when sending encrypted event without established session
    private suspend fun startMegolmSession(roomId: RoomId) : OutboundGroupSession {
        // TODO call olm_init_outbound_group_session, and store the details of the outbound session for future use.
        val outbound = OutboundGroupSession()
        store.outboundSessionByRoomId[roomId] = outbound
        val inbound = InboundGroupSession(outbound.sessionKey)
        store.inboundSessionsBySessionId[outbound.sessionId] = inbound
        val devices = downloadDeviceList(roomId = roomId)
        val userToDeviceToEncrypted : MutableMap<UserId, MutableMap<String, JsonElement>> = mutableMapOf()
        devices.forEach { (userId, d) ->
            userToDeviceToEncrypted[userId] = mutableMapOf()
            d.forEach { (theirDeviceId, deviceKeys) ->
                //val theirDeviceId = deviceKeys.deviceId
                val theirCurveKey = deviceKeys.keys["$CURVE25519:$theirDeviceId"]
                    ?: throw MissingKeyException("Cant find $CURVE25519 identity key in query.")
                val theirEDKey = deviceKeys.keys["$ED25519:$theirDeviceId"]
                    ?: throw MissingKeyException("Cant find $ED25519 identity key in query.")
                val content = MRoomKey.MRoomKeyContent(
                    algorithm = RoomEncryptionAlgorithm.MEGOLM_V1_AES_SHA2,
                    roomId = roomId,
                    sessionId = outbound.sessionId,
                    sessionKey = outbound.sessionKey,
                )
                val encrypted = encryptOlm(content, userId, theirDeviceId, theirCurveKey, theirEDKey)
                userToDeviceToEncrypted[userId]?.put(theirDeviceId, encrypted)
            }
        }
        val request = SendToDeviceRequest(userToDeviceToEncrypted)
        client.sendEventToDevice("m.room.encrypted", request)
        return outbound
    }

    @OptIn(InternalSerializationApi::class)
    suspend fun encryptMegolm(
        content: EventContent,
        roomId: RoomId,
        type: String
    ) : MRoomEncrypted.MRoomEncryptedContent {
        val outbound = store.outboundSessionByRoomId[roomId] ?: startMegolmSession(roomId)
        val jsonContent: JsonElement = dialPhoneJson.encodeToJsonElement(content)
        val toEncrypt = buildJsonObject {
            put("content", jsonContent)
            put("room_id", roomId.toString())
            put("type", type)
        }
        val encryptedText = outbound.encrypt(dialPhoneJson.encodeToString(toEncrypt))
        val encryptedContent = MRoomEncrypted.MRoomEncryptedContent(
            senderKey = keys.curve25519,
            cipherText = JsonPrimitive(encryptedText),
            algorithm = MessageEncryptionAlgorithm.MEGOLM_V1_AES_SHA2,
            sessionId = outbound.sessionId,
            deviceId = deviceId,
        )
        return encryptedContent
    }

    fun decryptMegolm(event: MRoomEncrypted) : MatrixEvent {
        // TODO replay attack message index
        val megolmSession = store.inboundSessionsBySessionId[event.content.sessionId]
            ?: throw RuntimeException("Cant find megolm session to decrypt event")
        if (event.content.cipherText !is JsonPrimitive) throw MalformedEncryptedEvent(event)
        val plainText = megolmSession.decrypt((event.content.cipherText as JsonPrimitive).content)
        val jsonObject : JsonObject = dialPhoneJson.decodeFromString(plainText.message)
        val newObj = jsonObject.toMutableMap().apply {
            this["sender"] = JsonPrimitive(event.sender.toString())
            this["event_id"] = JsonPrimitive(event.id.toString())
        }.run { JsonObject(this) }
        return dialPhoneJson.decodeFromJsonElement(newObj)
    }

    /**
     * HELPER
     */

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
    private suspend fun downloadDeviceList(roomId: RoomId) : Map<UserId, Map<String, SignedDeviceKeys>> {
        val deviceList = client.queryKeys(KeyQueryRequest(
            deviceKeys = phone.getJoinedRoomFutureById(roomId)?.receive()?.members?.associate {
                it.userId to emptyList()
            } ?: throw RoomKeyHandleException("Cant find room with provided it")
        ))
        // TODO a lot of checks
        return deviceList.deviceKeys ?: throw RuntimeException("No devices Found in device list")
    }

    suspend fun publishKeys() {
        val deviceKeys = DeviceKeys(
            algorithms = listOf(
                MessageEncryptionAlgorithm.MEGOLM_V1_AES_SHA2,
                MessageEncryptionAlgorithm.OLM_V1_CURVE25519_AES_SHA1,
            ),
            deviceId = deviceId,
            userId = ownId,
            keys = mapOf(
                "$ED25519:$deviceId" to keys.ed25519,
                "$CURVE25519:$deviceId" to keys.curve25519
            )
        )
        val plainTextToSign = EncryptionUtilities.getCanonicalJson(deviceKeys)
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
                    put(ownId.toString(), buildJsonObject {
                        put("$ED25519:$deviceId", signature)
                    })
                })
            }
        }.toMap()
        return keyMap
    }

    fun checkIfRoomEncrypted(roomId: RoomId) : Boolean = store.encryptedRooms.contains(roomId)
    fun markRoomEncrypted(roomId: RoomId) {
        // no duplicates
        if (checkIfRoomEncrypted(roomId)) return
        store.encryptedRooms.add(roomId)
    }

    companion object {
        const val ED25519 = "ed25519"
        const val CURVE25519 = "curve25519"
        const val SIGNED_CURVE25519 = "signed_curve25519"
        const val MRoomKeyType = "m.room_key"
    }
}
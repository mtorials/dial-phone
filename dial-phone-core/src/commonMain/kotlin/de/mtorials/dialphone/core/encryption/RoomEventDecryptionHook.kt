package de.mtorials.dialphone.core.encryption

import de.mtorials.dialphone.api.RoomEventHook
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import de.mtorials.dialphone.api.model.mevents.roommessage.MRoomEncrypted
import de.mtorials.dialphone.api.model.enums.MessageEncryptionAlgorithm.*
import de.mtorials.dialphone.core.exceptions.MalformedEncryptedEvent
import de.mtorials.dialphone.core.exceptions.OlmSessionNotFound
import io.github.matrixkt.olm.Message
import io.github.matrixkt.olm.Session
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class RoomEventDecryptionHook(
    private val manager: EncryptionManager,
) : RoomEventHook {
    override fun manipulateEvent(event: MatrixEvent): MatrixEvent {
        if (event !is MRoomEncrypted) return event
        return when (event.content.algorithm) {
            OLM_V1_CURVE25519_AES_SHA1 -> handleOlm(event)
            MEGOLM_V1_AES_SHA2 -> handleMegOlm(event)
        }
    }

    private fun handleMegOlm(event: MRoomEncrypted) : MatrixEvent {
        // TODO replay attack message index
        TODO("implement megolm")
    }

    private fun handleOlm(event: MRoomEncrypted) : MatrixEvent {
        // TODO does this work?
        val cypher: Pair<String, TypeAndBody> = Json.decodeFromString(event.content.cipherText)
        val session: Session = when (cypher.second.type) {
            0 -> manager.getPreKeySession(
                    senderKey = event.content.senderKey ?: throw MalformedEncryptedEvent(event),
                    body = cypher.second.body
            )
            else -> manager.getOlmSession(
                senderKey = event.content.senderKey ?: throw MalformedEncryptedEvent(event),
            ) ?: throw OlmSessionNotFound(event)
        }
        // Really type and body?
        val plainText = session.decrypt(Message.invoke(
            cipherText = cypher.second.body,
            type = cypher.second.type.toLong())
        )
        println(plainText)
        // TODO check sender, recipeint, keys, recipient keys
        TODO("impl serialization")
    }
}
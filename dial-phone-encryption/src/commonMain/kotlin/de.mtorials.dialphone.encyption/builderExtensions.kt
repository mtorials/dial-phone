package de.mtorials.dialphone.encyption

import de.mtorials.dialphone.api.E2EEClient
import de.mtorials.dialphone.core.DialPhoneBuilder
import kotlinx.coroutines.launch
import net.folivo.trixnity.olm.OlmAccount

suspend fun DialPhoneBuilder.useEncryption() {
    // E2EE
    // TODO impl keystore
    afterInitialization {
        val encryptionManager = EncryptionManager(
            store = InMemoryE2EEStore(),
            client = E2EEClient(client, token ?: error("No token supplied"), homeserverUrl),
            deviceId = deviceId ?: throw RuntimeException("DialPhone has no device id. This is possible if used as an appservice"),
            ownId = ownId ?: error("Got no id"),
            phone = it,
            dialPhoneJson = format,
            account = OlmAccount.create(),
        )
        val roomEventEncryption = RoomEventEncryption(encryptionManager)
        it.beforeRoomEventListener { event ->
            roomEventEncryption.manageIncomingRoomEvent(event)
        }
        it.beforeMessageEventPublish { roomId, type, content ->
            roomEventEncryption.manageOutgoingMessageEvent(roomId, type, content)
        }
        it.addListeners(EncryptionListener(encryptionManager))
        coroutineScope.launch { encryptionManager.publishKeys() }
    }
}
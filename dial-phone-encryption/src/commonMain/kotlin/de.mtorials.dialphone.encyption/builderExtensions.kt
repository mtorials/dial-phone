package de.mtorials.dialphone.encyption

import de.mtorials.dialphone.api.E2EEClient
import de.mtorials.dialphone.core.DialPhoneBuilder
import de.mtorials.dialphone.olmmachine.OlmMachineBuilder

suspend fun DialPhoneBuilder.useEncryption(pathToStore: String = "./", passphrase: String? = null) {
    // E2EE
    // TODO impl keystore
    afterInitialization {
        val encryptionManager = EncryptionManager(
            machine = OlmMachineBuilder.create(
                userId = it.ownId.toString(),
                deviceId = deviceId ?: error("No device id"),
                path = pathToStore,
                passphrase = passphrase,
            ),
            client = E2EEClient(client, token ?: error("No token supplied"), homeserverUrl),
            deviceId = deviceId ?: throw RuntimeException("DialPhone has no device id. This is possible if used as an appservice"),
            phone = it,
            json = format,
        )
        val roomEventEncryption = RoomEventEncryption(encryptionManager, it)
        it.beforeRoomEventListener { roomId, event ->
            roomEventEncryption.manageIncomingRoomEvent(roomId, event)
        }
        it.beforeMessageEventPublish = { roomId, type, content ->
            roomEventEncryption.manageOutgoingMessageEvent(roomId, type, content)
        }
        it.addListeners(EncryptionListener(encryptionManager))
        // coroutineScope.launch { encryptionManager.publishKeys() }
    }
}
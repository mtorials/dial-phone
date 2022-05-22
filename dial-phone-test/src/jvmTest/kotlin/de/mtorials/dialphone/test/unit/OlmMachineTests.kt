package de.mtorials.dialphone.test.unit

import de.mtorials.dialphone.olmmachine.OlmMachineBuilder
import de.mtorials.dialphone.olmmachine.bindings.OlmMachineInterface
import org.junit.Test
import kotlin.test.assertEquals

class OlmMachineTests {

    @Test
    fun `init olm machine`() {
        val olmMachine: OlmMachineInterface = OlmMachineBuilder.create("@mtorials:mtorials.de", DEVICE_ID, "../", null)
        assertEquals(DEVICE_ID, olmMachine.deviceId())
    }

    companion object {
        const val DEVICE_ID = "testid"
    }
}
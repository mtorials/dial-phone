package de.mtorials.dialphone.test.unit

import de.mtorials.dialphone.encyption.olm.OlmMachineBuilder
import de.mtorials.dialphone.encyption.olm.OlmMachineInterface
import org.junit.Test

class OlmMachineTests {

    @Test
    fun `init olm machine`() {
        val olmMachine: OlmMachineInterface = OlmMachineBuilder.create("@mtorials:mtorials.de", "asdasdasd", "/", null)

    }
}
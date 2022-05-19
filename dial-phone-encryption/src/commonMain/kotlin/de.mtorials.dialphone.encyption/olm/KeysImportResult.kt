package de.mtorials.dialphone.encyption.olm

data class KeysImportResult(
    var imported: Long,
    var total: Long,
    var keys: Map<String, Map<String, List<String>>>
)
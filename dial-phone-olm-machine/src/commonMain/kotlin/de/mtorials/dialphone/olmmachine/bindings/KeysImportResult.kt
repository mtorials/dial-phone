package de.mtorials.dialphone.olmmachine.bindings

data class KeysImportResult(
    var imported: Long,
    var total: Long,
    var keys: Map<String, Map<String, List<String>>>
)
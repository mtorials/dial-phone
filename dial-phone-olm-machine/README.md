# matrix-sdk-crypto bindings

The code in the packages `uniffi.olm` and `de.mtorials.dialphone.ommachine.bindings` is generated by uniffi, but I
put a lot of the data classes (and others) in the common module.

Build working for linux only at this point.

**For newer versions of uniffi bindings the symbols in the dynamic library change (like `olm_eb49`).
Check these when check this when upgrading the version and replace in case of errors.**
package de.mtorials.dialphone.olmmachine.bindings

public interface ProgressListener {
    fun onProgress(progress: Int, total: Int)
}
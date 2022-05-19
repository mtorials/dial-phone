package de.mtorials.dialphone.encyption.olm

public interface ProgressListener {
    fun onProgress(progress: Int, total: Int)
}
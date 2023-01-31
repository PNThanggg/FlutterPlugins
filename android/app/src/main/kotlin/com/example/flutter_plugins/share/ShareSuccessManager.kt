package com.example.flutter_plugins.share

import android.content.Intent
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.PluginRegistry
import java.util.concurrent.atomic.AtomicBoolean

internal class ShareSuccessManager : PluginRegistry.ActivityResultListener {
    private var callback: MethodChannel.Result? = null
    private var isCalledBack: AtomicBoolean = AtomicBoolean(true)

    fun setCallback(callback: MethodChannel.Result): Boolean {
        return if (isCalledBack.compareAndSet(true, false)) {
            // Prepare all state for new share
            SharePlusPendingIntent.result = ""
            isCalledBack.set(false)
            this.callback = callback
            true
        } else {
            callback.error(
                "Share callback error",
                "prior share-sheet did not call back, did you await it? Maybe use non-result variant",
                null,
            )
            false
        }
    }

    /**
     * Must be called if `.startActivityForResult` is not available to avoid deadlocking.
     */
    fun unavailable() {
        returnResult(RESULT_UNAVAILABLE)
    }

    /**
     * Send the result to flutter by invoking the previously set callback.
     */
    private fun returnResult(result: String) {
        if (isCalledBack.compareAndSet(false, true) && callback != null) {
            callback!!.success(result)
            callback = null
        }
    }

    /**
     * Handler called after a share sheet was closed. Called regardless of success or
     * dismissal.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        return if (requestCode == ACTIVITY_CODE) {
            returnResult(SharePlusPendingIntent.result)
            true
        } else {
            false
        }
    }

    /**
     * Companion object holds constants used throughout the plugin when attempting to return
     * the share result.
     */
    companion object {
        const val ACTIVITY_CODE = 0x5873
        const val RESULT_UNAVAILABLE = "com.example.flutter_plugins/share/unavailable"
    }
}

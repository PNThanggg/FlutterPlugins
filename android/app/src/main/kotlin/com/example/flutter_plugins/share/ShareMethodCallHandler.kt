package com.example.flutter_plugins.share

import android.content.Context
import android.os.Build
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import java.io.IOException

class ShareMethodCallHandler(
    private val applicationContext: Context,
) : MethodCallHandler {
    private lateinit var share: Share
    private lateinit var manager: ShareSuccessManager

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        // The user used a *WithResult method
        val isResultRequested = call.method.endsWith("WithResult")
        // We don't attempt to return a result if the current API version doesn't support it
        val isWithResult = isResultRequested && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1

        manager = ShareSuccessManager()
        share = Share(context = applicationContext, activity = null, manager = manager)

        when (call.method) {
            "share", "shareWithResult" -> {
                expectMapArguments(call)
                if (isWithResult && !manager.setCallback(result)) return@onMethodCall

                // Android does not support showing the share sheet at a particular point on screen.
                share.share(
                    call.argument<Any>("text") as String,
                    call.argument<Any>("subject") as String?,
                    isWithResult,
                )

                if (!isWithResult) {
                    if (isResultRequested) {
                        result.success("com.example.flutter_plugins/share/unavailable")
                    } else {
                        result.success(null)
                    }
                }
            }

            "shareFiles", "shareFilesWithResult" -> {
                expectMapArguments(call)
                if (isWithResult && !manager.setCallback(result)) return@onMethodCall

                // Android does not support showing the share sheet at a particular point on screen.
                try {
                    share.shareFiles(
                        call.argument<List<String>>("paths")!!,
                        call.argument<List<String>?>("mimeTypes"),
                        call.argument<String?>("text"),
                        call.argument<String?>("subject"),
                        isWithResult,
                    )

                    if (!isWithResult) {
                        if (isResultRequested) {
                            result.success("com.example.flutter_plugins/share/unavailable")
                        } else {
                            result.success(null)
                        }
                    }
                } catch (e: IOException) {
                    result.error("Share failed", e.message, null)
                }
            }

            else -> result.notImplemented()
        }
    }

    @Throws(IllegalArgumentException::class)
    private fun expectMapArguments(call: MethodCall) {
        require(call.arguments is Map<*, *>) { "Map arguments expected" }
    }
}
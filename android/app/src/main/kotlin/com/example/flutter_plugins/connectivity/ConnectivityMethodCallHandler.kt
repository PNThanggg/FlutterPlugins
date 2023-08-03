package com.example.flutter_plugins.connectovity

import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler

class ConnectivityMethodCallHandler(private val connectivity: Connectivity) : MethodCallHandler {
    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "check" -> {
                result.success(connectivity.getNetworkType())
            }

            else -> result.notImplemented()
        }
    }
}
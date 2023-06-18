package com.example.flutter_plugins.native_timezone

import android.os.Build
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import java.time.ZoneId
import java.util.*

class NativeTimezoneMethodCallHandler : MethodCallHandler {
    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "getLocalTimezone" -> result.success(getLocalTimezone())

            "getAvailableTimezones" -> result.success(getAvailableTimezones())

            else -> result.notImplemented()
        }
    }

    private fun getLocalTimezone(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ZoneId.systemDefault().id
        } else {
            TimeZone.getDefault().id
        }
    }

    private fun getAvailableTimezones(): List<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ZoneId.getAvailableZoneIds().toCollection(ArrayList())
        } else {
            TimeZone.getAvailableIDs().toCollection(ArrayList())
        }
    }
}
package com.example.flutter_plugins.native_timezone

import android.os.Build
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.*

class NativeTimezoneMethodCallHandler : MethodCallHandler {
    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "getLocalTimezone" -> result.success(getLocalTimezone())

            "getAvailableTimezones" -> result.success(getAvailableTimezones())

            "getDisplayName" -> result.success(getDisplayName())

            "getOffset" -> result.success(getOffset())

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

    private fun getDisplayName() : String? {
        return TimeZone.getDefault().displayName
    }

    private fun getOffset(): String {
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val currentDateTime: OffsetDateTime = OffsetDateTime.now()
            val offsetHours = currentDateTime.offset.totalSeconds / 3600

            val zoneOffset: ZoneOffset = ZoneOffset.ofHours(offsetHours)

            val zoneId = ZoneId.ofOffset("UTC", zoneOffset)

            zoneId.id
        } else {
            "Not support"
        }
    }
}
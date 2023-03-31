package com.example.flutter_plugins.battery

import android.content.*
import android.os.BatteryManager
import android.os.Build
import io.flutter.plugin.common.EventChannel

internal class BatteryStreamHandlerImpl(private val applicationContext: Context) : EventChannel.StreamHandler {
    private var chargingStateChangeReceiver: BroadcastReceiver? = null

    private fun publishBatteryStatus(events: EventChannel.EventSink, status: String?) {
        if (status != null) {
            events.success(status)
        } else {
            events.error("UNAVAILABLE", "Charging status unavailable", null)
        }
    }

    override fun onListen(arguments: Any?, events: EventChannel.EventSink) {
        chargingStateChangeReceiver = createChargingStateChangeReceiver(events)
        applicationContext.registerReceiver(chargingStateChangeReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        val status = getBatteryStatus()
        publishBatteryStatus(events, status)
    }

    override fun onCancel(arguments: Any?) {
        applicationContext.unregisterReceiver(chargingStateChangeReceiver)
        chargingStateChangeReceiver = null
    }

    private fun getBatteryStatus(): String? {
        val status: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val batteryManager = applicationContext.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
            batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_STATUS)
        } else {
            val intent = ContextWrapper(applicationContext).registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            intent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        }

        return convertBatteryStatus(status)
    }

    private fun createChargingStateChangeReceiver(events: EventChannel.EventSink) : BroadcastReceiver {
        return object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
                publishBatteryStatus(events, convertBatteryStatus(status))
            }
        }
    }

    private fun convertBatteryStatus(status: Int): String? {
        return when (status) {
            BatteryManager.BATTERY_STATUS_CHARGING -> "charging"
            BatteryManager.BATTERY_STATUS_FULL -> "full"
            BatteryManager.BATTERY_STATUS_DISCHARGING, BatteryManager.BATTERY_STATUS_NOT_CHARGING -> "discharging"
            BatteryManager.BATTERY_STATUS_UNKNOWN -> "unknown"
            else -> null
        }
    }
}
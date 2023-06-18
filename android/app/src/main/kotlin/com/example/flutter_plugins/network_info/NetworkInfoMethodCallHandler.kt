package com.example.flutter_plugins.network_info

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import io.flutter.embedding.android.FlutterActivity
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler

class NetworkInfoMethodCallHandler(
    private val context: Context
) : MethodCallHandler {
    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        var connectivityManager = context.getSystemService(FlutterActivity.CONNECTIVITY_SERVICE) as ConnectivityManager

        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            connectivityManager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        }

        val networkInfo = NetworkInfo(wifiManager, connectivityManager)

        when (call.method) {
            "wifiName" -> result.success(networkInfo.getWifiName())
            "wifiBSSID" -> result.success(networkInfo.getWifiBSSID())
            "wifiIPAddress" -> result.success(networkInfo.getWifiIPAddress())
            "wifiBroadcast" -> result.success(networkInfo.getBroadcastIP())
            "wifiSubmask" -> result.success(networkInfo.getWifiSubnetMask())
            "wifiGatewayAddress" -> result.success(networkInfo.getGatewayIPAddress())
            "wifiIPv6Address" -> result.success(networkInfo.getIpV6())

            else -> result.notImplemented()
        }
    }
}
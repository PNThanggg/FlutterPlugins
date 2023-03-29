package com.example.flutter_plugins.connectovity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.os.Build
import android.os.Handler
import android.os.Looper
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.EventChannel.EventSink


class ConnectivityBroadcastReceiver(private val context: Context, private val connectivity: Connectivity) : BroadcastReceiver(), EventChannel.StreamHandler {
    private var events: EventSink? = null
    private val mainHandler: Handler = Handler(Looper.getMainLooper())
    private var networkCallback: NetworkCallback? = null

    val CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE"

    override fun onReceive(context: Context?, intent: Intent?) {
        if (events != null) {
            events!!.success(connectivity.getNetworkType())
        }
    }

    override fun onListen(arguments: Any?, events: EventSink?) {
        this.events = events

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            networkCallback = object : NetworkCallback() {
                override fun onAvailable(network: Network) {
                    val runnable = Runnable { events!!.success(connectivity.getNetworkType()) }
                    mainHandler.post(runnable)
                }

                override fun onLost(network: Network) {
                    val runnable = Runnable { events!!.success(Connectivity.CONNECTIVITY_NONE) }
                    mainHandler.post(runnable)
                }
            }

            connectivity.connectivityManager!!.registerDefaultNetworkCallback(networkCallback as NetworkCallback)
        } else {
            context.registerReceiver(this, IntentFilter(CONNECTIVITY_ACTION))
        }
    }

    override fun onCancel(arguments: Any?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (networkCallback != null) {
                connectivity.connectivityManager!!.unregisterNetworkCallback(networkCallback!!)
                networkCallback = null
            }
        } else {
            try {
                context.unregisterReceiver(this)
            } catch (e: Exception) {
                // listen never called, ignore the error
            }
        }
    }
}
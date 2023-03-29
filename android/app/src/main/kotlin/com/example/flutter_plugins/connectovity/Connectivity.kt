package com.example.flutter_plugins.connectovity

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class Connectivity(val connectivityManager: ConnectivityManager?) {
    companion object {
        const val CONNECTIVITY_NONE = "none"
        const val CONNECTIVITY_WIFI = "wifi"
        const val CONNECTIVITY_MOBILE = "mobile"
        const val CONNECTIVITY_ETHERNET = "ethernet"
        const val CONNECTIVITY_BLUETOOTH = "bluetooth"
        const val CONNECTIVITY_VPN = "vpn"
    }

    fun getNetworkType(): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager!!.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return CONNECTIVITY_NONE
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return CONNECTIVITY_WIFI
            }
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                return CONNECTIVITY_ETHERNET
            }
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                return CONNECTIVITY_VPN
            }
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return CONNECTIVITY_MOBILE
            }
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)) {
                return CONNECTIVITY_BLUETOOTH
            }
        }
        return getNetworkTypeLegacy()
    }

    private fun getNetworkTypeLegacy(): String {
        // handle type for Android versions less than Android 6
        val info = connectivityManager!!.activeNetworkInfo
        if (info == null || !info.isConnected) {
            return CONNECTIVITY_NONE
        }

        return when (info.type) {
            ConnectivityManager.TYPE_BLUETOOTH -> CONNECTIVITY_BLUETOOTH
            ConnectivityManager.TYPE_ETHERNET -> CONNECTIVITY_ETHERNET
            ConnectivityManager.TYPE_WIFI, ConnectivityManager.TYPE_WIMAX -> CONNECTIVITY_WIFI
            ConnectivityManager.TYPE_VPN -> CONNECTIVITY_VPN
            ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_MOBILE_DUN, ConnectivityManager.TYPE_MOBILE_HIPRI -> CONNECTIVITY_MOBILE
            else -> CONNECTIVITY_NONE
        }
    }

//    fun getConnectivityManager(): ConnectivityManager? {
//        return connectivityManager
//    }
}
package com.example.flutter_plugins.device_info

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.FeatureInfo
import android.os.Build
import android.util.DisplayMetrics
import android.view.Display
import android.view.WindowManager
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler

class DeviceInfoMethodCallHandler(
    private val applicationContext: Context,
    private val windowManager: WindowManager
) : MethodCallHandler {
    @SuppressLint("HardwareIds", "ObsoleteSdkInt")
    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "getDeviceInfo" -> {
                val build: MutableMap<String, Any> = HashMap()

                build["board"] = Build.BOARD
                build["bootloader"] = Build.BOOTLOADER
                build["brand"] = Build.BRAND
                build["device"] = Build.DEVICE
                build["display"] = Build.DISPLAY
                build["fingerprint"] = Build.FINGERPRINT
                build["hardware"] = Build.HARDWARE
                build["host"] = Build.HOST
                build["id"] = Build.ID
                build["manufacturer"] = Build.MANUFACTURER
                build["model"] = Build.MODEL
                build["product"] = Build.PRODUCT

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    build["supported32BitAbis"] = listOf(*Build.SUPPORTED_32_BIT_ABIS)
                    build["supported64BitAbis"] = listOf(*Build.SUPPORTED_64_BIT_ABIS)
                    build["supportedAbis"] = listOf<String>(*Build.SUPPORTED_ABIS)
                } else {
                    build["supported32BitAbis"] = emptyList<String>()
                    build["supported64BitAbis"] = emptyList<String>()
                    build["supportedAbis"] = emptyList<String>()
                }

                build["tags"] = Build.TAGS
                build["type"] = Build.TYPE
                build["isPhysicalDevice"] = !isEmulator
                build["systemFeatures"] = getSystemFeatures()

                val version: MutableMap<String, Any> = HashMap()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    version["baseOS"] = Build.VERSION.BASE_OS
                    version["previewSdkInt"] = Build.VERSION.PREVIEW_SDK_INT
                    version["securityPatch"] = Build.VERSION.SECURITY_PATCH
                }
                version["codename"] = Build.VERSION.CODENAME
                version["incremental"] = Build.VERSION.INCREMENTAL
                version["release"] = Build.VERSION.RELEASE
                version["sdkInt"] = Build.VERSION.SDK_INT
                build["version"] = version

                @Suppress("DEPRECATION")
                val display: Display = windowManager.defaultDisplay
                val metrics = DisplayMetrics()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    @Suppress("DEPRECATION")
                    display.getRealMetrics(metrics)
                } else {
                    @Suppress("DEPRECATION")
                    display.getMetrics(metrics)
                }

                val displayResult: MutableMap<String, Any> = HashMap()
                displayResult["widthPx"] = metrics.widthPixels.toDouble()
                displayResult["heightPx"] = metrics.heightPixels.toDouble()
                displayResult["xDpi"] = metrics.xdpi
                displayResult["yDpi"] = metrics.ydpi
                build["displayMetrics"] = displayResult

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    build["serialNumber"] = try {
                        Build.getSerial()
                    } catch (ex: SecurityException) {
                        Build.UNKNOWN
                    }
                } else {
                    @Suppress("DEPRECATION")
                    build["serialNumber"] = Build.SERIAL
                }

                result.success(build)
            }

            else -> result.notImplemented()
        }
    }

    /**
     * A simple emulator-detection based on the flutter tools detection logic and a couple of legacy
     * detection systems
     */
    private val isEmulator: Boolean
        get() = (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                || Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.PRODUCT.contains("sdk_google")
                || Build.PRODUCT.contains("google_sdk")
                || Build.PRODUCT.contains("sdk")
                || Build.PRODUCT.contains("sdk_x86")
                || Build.PRODUCT.contains("vbox86p")
                || Build.PRODUCT.contains("emulator")
                || Build.PRODUCT.contains("simulator"))

    private fun getSystemFeatures(): List<String> {
        val packageManager = applicationContext.packageManager
        val featureInfos: Array<FeatureInfo> = packageManager.systemAvailableFeatures
        return featureInfos
            .filterNot { featureInfo -> featureInfo.name == null }
            .map { featureInfo -> featureInfo.name }
    }
}
package com.example.flutter_plugins

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.FeatureInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorManager
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.BatteryManager
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.Display
import androidx.annotation.RequiresApi
import com.example.flutter_plugins.battery.BatteryStreamHandlerImpl
import com.example.flutter_plugins.connectovity.Connectivity
import com.example.flutter_plugins.connectovity.ConnectivityBroadcastReceiver
import com.example.flutter_plugins.network_info.NetworkInfo
import com.example.flutter_plugins.sensor.SensorStreamHandlerImpl
import com.example.flutter_plugins.share.Share
import com.example.flutter_plugins.share.ShareSuccessManager
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import java.io.IOException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


class MainActivity : FlutterActivity() {
    private lateinit var sensorAccelerometerChannel: EventChannel
    private lateinit var sensorUserAccelChannel: EventChannel
    private lateinit var sensorGyroscopeChannel: EventChannel
    private lateinit var sensorMagnetometerChannel: EventChannel

    private lateinit var accelerationStreamHandler: SensorStreamHandlerImpl
    private lateinit var linearAccelerationStreamHandler: SensorStreamHandlerImpl
    private lateinit var gyroScopeStreamHandler: SensorStreamHandlerImpl
    private lateinit var magnetometerStreamHandler: SensorStreamHandlerImpl

    private var packageInfoMethodChannel: MethodChannel? = null

    private var shareMethodChannel: MethodChannel? = null
    private lateinit var share: Share
    private lateinit var manager: ShareSuccessManager

    private var connectivityMethodChannel: MethodChannel? = null
    private lateinit var connectivityEventChannel: EventChannel

    private var networkInfoMethodChannel: MethodChannel? = null

    private var deviceInfoMethodChannel: MethodChannel? = null

    private var batteryMethodChannel: MethodChannel? = null
    private var batteryEventChannel: EventChannel? = null

    companion object {
        private const val SENSOR_ACCELEROMETER_EVENT_CHANNEL = "com.example.flutter_plugins/sensors/accelerometer"
        private const val SENSOR_GYROSCOPE_EVENT_CHANNEL = "com.example.flutter_plugins/sensors/gyroscope"
        private const val SENSOR_USER_ACCELEROMETER_EVENT_CHANNEL = "com.example.flutter_plugins/sensors/user_accel"
        private const val SENSOR_MAGNETOMETER_EVENT_CHANNEL = "com.example.flutter_plugins/sensors/magnetometer"

        private const val PACKAGE_INFO_CHANNEL_NAME = "com.example.flutter_plugins/package_info"

        private const val SHARE_CHANNEL_NAME = "com.example.flutter_plugins/share"

        private const val CONNECTIVITY_CHANNEL_NAME = "com.example.flutter_plugins/connectivity"
        private const val CONNECTIVITY_EVENT_CHANNEL = "com.example.flutter_plugins/connectivity_status"

        private const val NETWORK_INFO_CHANNEL_NAME = "com.example.flutter_plugins/network_info"

        private const val DEVICE_INFO_CHANNEL_NAME = "com.example.flutter_plugins/device_info"

        private const val BATTERY_METHOD_CHANNEL_NAME = "com.example.flutter_plugins/battery"
        private const val BATTERY_EVENT_CHANNEL_NAME = "com.example.flutter_plugins/charging"

        private const val POWER_SAVE_MODE_SAMSUNG = "1"
        private const val POWER_SAVE_MODE_XIAOMI = 1
        private const val POWER_SAVE_MODE_HUAWEI = 4
    }

    @Suppress("deprecation")
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        // Sensor plugin
        val sensorsManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        sensorAccelerometerChannel = EventChannel(flutterEngine.dartExecutor.binaryMessenger, SENSOR_ACCELEROMETER_EVENT_CHANNEL)
        accelerationStreamHandler = SensorStreamHandlerImpl(
            sensorsManager,
            Sensor.TYPE_ACCELEROMETER
        )
        sensorAccelerometerChannel.setStreamHandler(accelerationStreamHandler)

        sensorUserAccelChannel = EventChannel(flutterEngine.dartExecutor.binaryMessenger, SENSOR_USER_ACCELEROMETER_EVENT_CHANNEL)
        linearAccelerationStreamHandler = SensorStreamHandlerImpl(
            sensorsManager,
            Sensor.TYPE_LINEAR_ACCELERATION
        )
        sensorUserAccelChannel.setStreamHandler(linearAccelerationStreamHandler)

        sensorGyroscopeChannel = EventChannel(flutterEngine.dartExecutor.binaryMessenger, SENSOR_GYROSCOPE_EVENT_CHANNEL)
        gyroScopeStreamHandler = SensorStreamHandlerImpl(
            sensorsManager,
            Sensor.TYPE_GYROSCOPE
        )
        sensorGyroscopeChannel.setStreamHandler(gyroScopeStreamHandler)

        sensorMagnetometerChannel = EventChannel(flutterEngine.dartExecutor.binaryMessenger, SENSOR_MAGNETOMETER_EVENT_CHANNEL)
        magnetometerStreamHandler = SensorStreamHandlerImpl(
            sensorsManager,
            Sensor.TYPE_MAGNETIC_FIELD
        )
        sensorMagnetometerChannel.setStreamHandler(magnetometerStreamHandler)

        // Package info plugin
        packageInfoMethodChannel = MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            PACKAGE_INFO_CHANNEL_NAME
        )

        packageInfoMethodChannel!!.setMethodCallHandler { call, result ->
            try {
                if (call.method == "getAll") {
                    val packageManager = applicationContext!!.packageManager
                    val info = packageManager.getPackageInfo(applicationContext!!.packageName, 0)

                    val buildSignature = getBuildSignature(packageManager)

                    val installerPackage = packageManager.getInstallerPackageName(applicationContext!!.packageName)

                    val infoMap = HashMap<String, String>()
                    infoMap.apply {
                        put("appName", info.applicationInfo.loadLabel(packageManager).toString())
                        put("packageName", applicationContext!!.packageName)
                        put("version", info.versionName)
                        put("buildNumber", getLongVersionCode(info).toString())
                        if (buildSignature != null) put("buildSignature", buildSignature)
                        if (installerPackage != null) put("installerStore", installerPackage)
                    }.also { resultingMap ->
                        result.success(resultingMap)
                    }
                } else {
                    result.notImplemented()
                }
            } catch (ex: PackageManager.NameNotFoundException) {
                result.error("Name not found", ex.message, null)
            }
        }

        // Share plugin
        shareMethodChannel = MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            SHARE_CHANNEL_NAME
        )

        manager = ShareSuccessManager()
        share = Share(context = applicationContext, activity = null, manager = manager)

        shareMethodChannel!!.setMethodCallHandler { call, result ->
            // The user used a *WithResult method
            val isResultRequested = call.method.endsWith("WithResult")
            // We don't attempt to return a result if the current API version doesn't support it
            val isWithResult = isResultRequested && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1

            when (call.method) {
                "share", "shareWithResult" -> {
                    expectMapArguments(call)
                    if (isWithResult && !manager.setCallback(result)) return@setMethodCallHandler

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
                    if (isWithResult && !manager.setCallback(result)) return@setMethodCallHandler

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

        var connectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        // Connectivity plugin
        val connectivity = Connectivity(connectivityManager)
        val receiver = ConnectivityBroadcastReceiver(context, connectivity)

        connectivityMethodChannel = MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            CONNECTIVITY_CHANNEL_NAME
        )
        connectivityMethodChannel!!.setMethodCallHandler { call, result ->
            when (call.method) {
                "check" -> {
                    result.success(connectivity.getNetworkType())
                }

                else -> result.notImplemented()
            }
        }

        connectivityEventChannel = EventChannel(flutterEngine.dartExecutor.binaryMessenger, CONNECTIVITY_EVENT_CHANNEL)
        connectivityEventChannel.setStreamHandler(receiver)


        // Network info plugin
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            connectivityManager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        }

        val networkInfo = NetworkInfo(wifiManager, connectivityManager)

        networkInfoMethodChannel = MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            NETWORK_INFO_CHANNEL_NAME
        )
        networkInfoMethodChannel!!.setMethodCallHandler { call, result ->
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

        // Device info plugin
        deviceInfoMethodChannel = MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            DEVICE_INFO_CHANNEL_NAME
        )
        deviceInfoMethodChannel!!.setMethodCallHandler { call, result ->
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

                    val display: Display = windowManager.defaultDisplay
                    val metrics = DisplayMetrics()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        display.getRealMetrics(metrics)
                    } else {
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
                        build["serialNumber"] = Build.SERIAL
                    }

                    result.success(build)
                }

                else -> result.notImplemented()
            }
        }

        // Battery plugin
        batteryMethodChannel = MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            BATTERY_METHOD_CHANNEL_NAME
        )
        batteryMethodChannel!!.setMethodCallHandler { call, result ->
            when (call.method) {
                "getBatteryLevel" -> {
                    val currentBatteryLevel = getBatteryLevel()
                    if (currentBatteryLevel != -1) {
                        result.success(currentBatteryLevel)
                    } else {
                        result.error("UNAVAILABLE", "Battery level not available.", null)
                    }
                }

                "getBatteryState" -> {
                    val currentBatteryStatus = getBatteryStatus()
                    if (currentBatteryStatus != null) {
                        result.success(currentBatteryStatus)
                    } else {
                        result.error("UNAVAILABLE", "Charging status not available.", null)
                    }
                }

                "isInBatterySaveMode" -> {
                    val isInPowerSaveMode = isInPowerSaveMode()
                    if (isInPowerSaveMode != null) {
                        result.success(isInPowerSaveMode)
                    } else {
                        result.error("UNAVAILABLE", "Battery save mode not available.", null)
                    }
                }

                else -> result.notImplemented()
            }
        }

        batteryEventChannel = EventChannel(flutterEngine.dartExecutor.binaryMessenger, BATTERY_EVENT_CHANNEL_NAME)
        batteryEventChannel!!.setStreamHandler(BatteryStreamHandlerImpl(context))
    }


    @Suppress("deprecation")
    private fun getLongVersionCode(info: PackageInfo): Long {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            info.longVersionCode
        } else {
            info.versionCode.toLong()
        }
    }

    @Suppress("deprecation", "PackageManagerGetSignatures")
    private fun getBuildSignature(pm: PackageManager): String? {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val packageInfo = pm.getPackageInfo(
                    applicationContext!!.packageName,
                    PackageManager.GET_SIGNING_CERTIFICATES
                )
                val signingInfo = packageInfo.signingInfo ?: return null

                if (signingInfo.hasMultipleSigners()) {
                    signatureToSha1(signingInfo.apkContentsSigners.first().toByteArray())
                } else {
                    signatureToSha1(signingInfo.signingCertificateHistory.first().toByteArray())
                }
            } else {
                val packageInfo = pm.getPackageInfo(
                    applicationContext!!.packageName,
                    PackageManager.GET_SIGNATURES
                )
                val signatures = packageInfo.signatures

                if (signatures.isNullOrEmpty() || packageInfo.signatures.first() == null) {
                    null
                } else {
                    signatureToSha1(signatures.first().toByteArray())
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            null
        } catch (e: NoSuchAlgorithmException) {
            null
        }
    }

    @Throws(NoSuchAlgorithmException::class)
    private fun signatureToSha1(sig: ByteArray): String {
        val digest = MessageDigest.getInstance("SHA1")
        digest.update(sig)
        val hashText = digest.digest()
        return bytesToHex(hashText)
    }

    private fun bytesToHex(bytes: ByteArray): String {
        val hexArray = charArrayOf(
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        )
        val hexChars = CharArray(bytes.size * 2)
        var v: Int
        for (j in bytes.indices) {
            v = bytes[j].toInt() and 0xFF
            hexChars[j * 2] = hexArray[v ushr 4]
            hexChars[j * 2 + 1] = hexArray[v and 0x0F]
        }
        return String(hexChars)
    }

    override fun onDestroy() {
        super.onDestroy()

        sensorAccelerometerChannel.setStreamHandler(null)
        sensorUserAccelChannel.setStreamHandler(null)
        sensorGyroscopeChannel.setStreamHandler(null)
        sensorMagnetometerChannel.setStreamHandler(null)

        accelerationStreamHandler.onCancel(null)
        linearAccelerationStreamHandler.onCancel(null)
        gyroScopeStreamHandler.onCancel(null)
        magnetometerStreamHandler.onCancel(null)

        packageInfoMethodChannel!!.setMethodCallHandler(null)
        packageInfoMethodChannel = null

        shareMethodChannel!!.setMethodCallHandler(null)
        shareMethodChannel = null

        connectivityMethodChannel!!.setMethodCallHandler(null)
        connectivityMethodChannel = null

        networkInfoMethodChannel!!.setMethodCallHandler(null)
        networkInfoMethodChannel = null

        batteryMethodChannel!!.setMethodCallHandler(null)
        batteryMethodChannel = null
        batteryEventChannel!!.setStreamHandler(null)
        batteryEventChannel = null
    }

    @Throws(IllegalArgumentException::class)
    private fun expectMapArguments(call: MethodCall) {
        require(call.arguments is Map<*, *>) { "Map arguments expected" }
    }

    private fun getSystemFeatures(): List<String> {
        val featureInfos: Array<FeatureInfo> = packageManager.systemAvailableFeatures
        return featureInfos
            .filterNot { featureInfo -> featureInfo.name == null }
            .map { featureInfo -> featureInfo.name }
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

    /// Battery
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private fun getBatteryProperty(property: Int): Int {
        val batteryManager = applicationContext!!.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        return batteryManager.getIntProperty(property)
    }

    private fun getBatteryLevel(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getBatteryProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        } else {
            val intent = ContextWrapper(applicationContext).registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            val level = intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            (level * 100 / scale)
        }
    }

    private fun getBatteryStatus(): String? {
        val status: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getBatteryProperty(BatteryManager.BATTERY_PROPERTY_STATUS)
        } else {
            val intent = ContextWrapper(applicationContext).registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            intent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        }
        return convertBatteryStatus(status)
    }

    private fun isInPowerSaveMode(): Boolean? {
        val deviceManufacturer = Build.MANUFACTURER.lowercase(Locale.getDefault())

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            when (deviceManufacturer) {
                "xiaomi" -> isXiaomiPowerSaveModeActive()
                "huawei" -> isHuaweiPowerSaveModeActive()
                "samsung" -> isSamsungPowerSaveModeActive()
                else -> {
                    val powerManager = applicationContext!!.getSystemService(Context.POWER_SERVICE) as PowerManager
                    powerManager.isPowerSaveMode
                }
            }
        } else {
            null
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

    private fun isSamsungPowerSaveModeActive(): Boolean {
        val mode = Settings.System.getString(applicationContext!!.contentResolver, "psm_switch")
        return if (mode == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val powerManager = applicationContext!!.getSystemService(Context.POWER_SERVICE) as PowerManager
            powerManager.isPowerSaveMode
        } else {
            POWER_SAVE_MODE_SAMSUNG == mode
        }
    }

    private fun isHuaweiPowerSaveModeActive(): Boolean? {
        val mode = Settings.System.getInt(applicationContext!!.contentResolver, "SmartModeStatus", -1)
        return if (mode != -1) {
            mode == POWER_SAVE_MODE_HUAWEI
        } else {
            null
        }
    }

    private fun isXiaomiPowerSaveModeActive(): Boolean? {
        val mode = Settings.System.getInt(applicationContext!!.contentResolver, "POWER_SAVE_MODE_OPEN", -1)
        return if (mode != -1) {
            mode == POWER_SAVE_MODE_XIAOMI
        } else {
            null
        }
    }
}

package com.example.flutter_plugins

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorManager
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import com.example.flutter_plugins.connectovity.Connectivity
import com.example.flutter_plugins.connectovity.ConnectivityBroadcastReceiver
import com.example.flutter_plugins.network_info.NetworkInfo
import com.example.flutter_plugins.sensor.StreamHandlerImpl
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


class MainActivity : FlutterActivity() {
    private lateinit var sensorAccelerometerChannel: EventChannel
    private lateinit var sensorUserAccelChannel: EventChannel
    private lateinit var sensorGyroscopeChannel: EventChannel
    private lateinit var sensorMagnetometerChannel: EventChannel

    private lateinit var accelerationStreamHandler: StreamHandlerImpl
    private lateinit var linearAccelerationStreamHandler: StreamHandlerImpl
    private lateinit var gyroScopeStreamHandler: StreamHandlerImpl
    private lateinit var magnetometerStreamHandler: StreamHandlerImpl

    private var packageInfoMethodChannel: MethodChannel? = null

    private var shareMethodChannel: MethodChannel? = null
    private lateinit var share: Share
    private lateinit var manager: ShareSuccessManager

    private var connectivityMethodChannel: MethodChannel? = null
    private lateinit var connectivityEventChannel: EventChannel

    private var networkInfoMethodChannel: MethodChannel? = null

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
    }

    @Suppress("deprecation")
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        // Sensor plugin
        val sensorsManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        sensorAccelerometerChannel = EventChannel(flutterEngine.dartExecutor.binaryMessenger, SENSOR_ACCELEROMETER_EVENT_CHANNEL)
        accelerationStreamHandler = StreamHandlerImpl(
            sensorsManager,
            Sensor.TYPE_ACCELEROMETER
        )
        sensorAccelerometerChannel.setStreamHandler(accelerationStreamHandler)

        sensorUserAccelChannel = EventChannel(flutterEngine.dartExecutor.binaryMessenger, SENSOR_USER_ACCELEROMETER_EVENT_CHANNEL)
        linearAccelerationStreamHandler = StreamHandlerImpl(
            sensorsManager,
            Sensor.TYPE_LINEAR_ACCELERATION
        )
        sensorUserAccelChannel.setStreamHandler(linearAccelerationStreamHandler)

        sensorGyroscopeChannel = EventChannel(flutterEngine.dartExecutor.binaryMessenger, SENSOR_GYROSCOPE_EVENT_CHANNEL)
        gyroScopeStreamHandler = StreamHandlerImpl(
            sensorsManager,
            Sensor.TYPE_GYROSCOPE
        )
        sensorGyroscopeChannel.setStreamHandler(gyroScopeStreamHandler)

        sensorMagnetometerChannel = EventChannel(flutterEngine.dartExecutor.binaryMessenger, SENSOR_MAGNETOMETER_EVENT_CHANNEL)
        magnetometerStreamHandler = StreamHandlerImpl(
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

        // connectivity plugin
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


        // network info plugin
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

        shareMethodChannel!!.setMethodCallHandler(null)

        connectivityMethodChannel!!.setMethodCallHandler(null)

        networkInfoMethodChannel!!.setMethodCallHandler(null)
    }

    @Throws(IllegalArgumentException::class)
    private fun expectMapArguments(call: MethodCall) {
        require(call.arguments is Map<*, *>) { "Map arguments expected" }
    }
}

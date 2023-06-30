package com.example.flutter_plugins

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.net.ConnectivityManager
import com.example.flutter_plugins.battery.BatteryMethodCallHandler
import com.example.flutter_plugins.battery.BatteryStreamHandlerImpl
import com.example.flutter_plugins.connectovity.Connectivity
import com.example.flutter_plugins.connectovity.ConnectivityBroadcastReceiver
import com.example.flutter_plugins.connectovity.ConnectivityMethodCallHandler
import com.example.flutter_plugins.device_info.DeviceInfoMethodCallHandler
import com.example.flutter_plugins.native_timezone.NativeTimezoneMethodCallHandler
import com.example.flutter_plugins.network_info.NetworkInfoMethodCallHandler
import com.example.flutter_plugins.open_setting.OpenSettingMethodCallHandler
import com.example.flutter_plugins.package_info.PackageInfoMethodCallHandler
import com.example.flutter_plugins.sensor.SensorStreamHandlerImpl
import com.example.flutter_plugins.share.ShareMethodCallHandler
import com.example.flutter_plugins.shared_preferences.SharedPreferencesMethodCallHandler
import com.example.flutter_plugins.sms.SmsMethodCallHandler
import com.example.flutter_plugins.toast.ToastMethodCallHandler
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.android.TransparencyMode
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodChannel


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

    private var connectivityMethodChannel: MethodChannel? = null
    private lateinit var connectivityEventChannel: EventChannel

    private var networkInfoMethodChannel: MethodChannel? = null

    private var deviceInfoMethodChannel: MethodChannel? = null

    private var batteryMethodChannel: MethodChannel? = null
    private var batteryEventChannel: EventChannel? = null

    private var toastMethodChannel: MethodChannel? = null

    private var nativeTimezoneMethodChannel: MethodChannel? = null

    private var smsMethodChannel: MethodChannel? = null

    private var sharedPreferencesMethodChannel: MethodChannel? = null

    private var openSettingMethodChannel: MethodChannel? = null

    companion object {
        private const val SENSOR_ACCELEROMETER_EVENT_CHANNEL =
            "com.example.flutter_plugins/sensors/accelerometer"
        private const val SENSOR_GYROSCOPE_EVENT_CHANNEL =
            "com.example.flutter_plugins/sensors/gyroscope"
        private const val SENSOR_USER_ACCELEROMETER_EVENT_CHANNEL =
            "com.example.flutter_plugins/sensors/user_accel"
        private const val SENSOR_MAGNETOMETER_EVENT_CHANNEL =
            "com.example.flutter_plugins/sensors/magnetometer"

        private const val PACKAGE_INFO_CHANNEL_NAME = "com.example.flutter_plugins/package_info"

        private const val SHARE_CHANNEL_NAME = "com.example.flutter_plugins/share"

        private const val CONNECTIVITY_CHANNEL_NAME = "com.example.flutter_plugins/connectivity"
        private const val CONNECTIVITY_EVENT_CHANNEL =
            "com.example.flutter_plugins/connectivity_status"

        private const val NETWORK_INFO_CHANNEL_NAME = "com.example.flutter_plugins/network_info"

        private const val DEVICE_INFO_CHANNEL_NAME = "com.example.flutter_plugins/device_info"

        private const val BATTERY_METHOD_CHANNEL_NAME = "com.example.flutter_plugins/battery"
        private const val BATTERY_EVENT_CHANNEL_NAME = "com.example.flutter_plugins/charging"

        private const val TOAST_METHOD_CHANNEL_NAME = "com.example.flutter_plugins/toast"

        private const val NATIVE_TIMEZONE_CHANNEL_NAME =
            "com.example.flutter_plugins/native_timezone"

        private const val SMS_CHANNEL_NAME = "com.example.flutter_plugins/sms"

        private const val SHARED_PREFERENCES_CHANNEL_NAME =
            "com.example.flutter_plugins/shared_preferences"

        private const val OPEN_SETTING_CHANNEL_NAME = "open_settings"
    }

    override fun getTransparencyMode(): TransparencyMode {
        return TransparencyMode.transparent
    }

    @Suppress("deprecation")
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        // Sensor plugin
        val sensorsManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        sensorAccelerometerChannel = EventChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            SENSOR_ACCELEROMETER_EVENT_CHANNEL
        )
        accelerationStreamHandler = SensorStreamHandlerImpl(
            sensorsManager,
            Sensor.TYPE_ACCELEROMETER
        )
        sensorAccelerometerChannel.setStreamHandler(accelerationStreamHandler)

        sensorUserAccelChannel = EventChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            SENSOR_USER_ACCELEROMETER_EVENT_CHANNEL
        )
        linearAccelerationStreamHandler = SensorStreamHandlerImpl(
            sensorsManager,
            Sensor.TYPE_LINEAR_ACCELERATION
        )
        sensorUserAccelChannel.setStreamHandler(linearAccelerationStreamHandler)

        sensorGyroscopeChannel =
            EventChannel(flutterEngine.dartExecutor.binaryMessenger, SENSOR_GYROSCOPE_EVENT_CHANNEL)
        gyroScopeStreamHandler = SensorStreamHandlerImpl(
            sensorsManager,
            Sensor.TYPE_GYROSCOPE
        )
        sensorGyroscopeChannel.setStreamHandler(gyroScopeStreamHandler)

        sensorMagnetometerChannel = EventChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            SENSOR_MAGNETOMETER_EVENT_CHANNEL
        )
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

        packageInfoMethodChannel!!.setMethodCallHandler(
            PackageInfoMethodCallHandler(
                applicationContext
            )
        )

        // Share plugin
        shareMethodChannel = MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            SHARE_CHANNEL_NAME
        )
        shareMethodChannel!!.setMethodCallHandler(ShareMethodCallHandler(applicationContext))


        // Connectivity plugin
        val connectivityManager =
            context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val receiver = ConnectivityBroadcastReceiver(context)

        connectivityMethodChannel = MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            CONNECTIVITY_CHANNEL_NAME
        )
        connectivityMethodChannel!!.setMethodCallHandler(
            ConnectivityMethodCallHandler(
                Connectivity(
                    connectivityManager
                )
            )
        )

        connectivityEventChannel = EventChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            CONNECTIVITY_EVENT_CHANNEL
        )
        connectivityEventChannel.setStreamHandler(receiver)


        // Network info plugin
        networkInfoMethodChannel = MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            NETWORK_INFO_CHANNEL_NAME
        )
        networkInfoMethodChannel!!.setMethodCallHandler(NetworkInfoMethodCallHandler(context))

        // Device info plugin
        deviceInfoMethodChannel = MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            DEVICE_INFO_CHANNEL_NAME
        )
        deviceInfoMethodChannel!!.setMethodCallHandler(
            DeviceInfoMethodCallHandler(
                applicationContext,
                windowManager
            )
        )

        // Battery plugin
        batteryMethodChannel = MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            BATTERY_METHOD_CHANNEL_NAME
        )
        batteryMethodChannel!!.setMethodCallHandler(BatteryMethodCallHandler(applicationContext))

        batteryEventChannel =
            EventChannel(flutterEngine.dartExecutor.binaryMessenger, BATTERY_EVENT_CHANNEL_NAME)
        batteryEventChannel!!.setStreamHandler(BatteryStreamHandlerImpl(context))

        // Toast
        toastMethodChannel = MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            TOAST_METHOD_CHANNEL_NAME
        )
        toastMethodChannel!!.setMethodCallHandler(ToastMethodCallHandler(context))

        // Native timezone
        nativeTimezoneMethodChannel = MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            NATIVE_TIMEZONE_CHANNEL_NAME
        )
        nativeTimezoneMethodChannel!!.setMethodCallHandler(NativeTimezoneMethodCallHandler())

        // Sms
        smsMethodChannel = MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            SMS_CHANNEL_NAME
        )
        smsMethodChannel!!.setMethodCallHandler(SmsMethodCallHandler(context))

        // Shared preferences
        sharedPreferencesMethodChannel = MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            SHARED_PREFERENCES_CHANNEL_NAME
        )
        sharedPreferencesMethodChannel!!.setMethodCallHandler(
            SharedPreferencesMethodCallHandler(
                context = context
            )
        )

        // Open setting
        openSettingMethodChannel = MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            OPEN_SETTING_CHANNEL_NAME
        )
        openSettingMethodChannel!!.setMethodCallHandler(OpenSettingMethodCallHandler(
            context = context
        ))
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

        toastMethodChannel!!.setMethodCallHandler(null)
        toastMethodChannel = null

        nativeTimezoneMethodChannel!!.setMethodCallHandler(null)
        nativeTimezoneMethodChannel = null

        smsMethodChannel!!.setMethodCallHandler(null)
        smsMethodChannel = null

        sharedPreferencesMethodChannel!!.setMethodCallHandler(null)
        sharedPreferencesMethodChannel = null

        openSettingMethodChannel!!.setMethodCallHandler(null)
        openSettingMethodChannel = null
    }
}

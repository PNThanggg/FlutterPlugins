package com.example.flutter_plugins.open_setting

import android.content.Context
import android.content.Intent
import android.util.Log
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler

class OpenSettingMethodCallHandler(
    private val context: Context
) : MethodCallHandler {
    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        val target: String
        val setting = call.argument<String>("argument")

        Log.e("Open setting", "Setting: $setting")

        when (call.method) {
            "openSettings" -> {
                target = when (setting) {
                    "wifi" -> "android.settings.WIFI_SETTINGS"
                    "data_roaming" -> "android.settings.DATA_ROAMING_SETTINGS"
                    "location_source" -> "android.settings.LOCATION_SOURCE_SETTINGS"
                    "app_settings" -> "android.settings.APPLICATION_SETTINGS"
                    "bluetooth" -> "android.settings.BLUETOOTH_SETTINGS"
                    "notification" -> "android.settings.NOTIFICATION_SETTINGS"
                    "security" -> "android.settings.SECURITY_SETTINGS"
                    "sound" -> "android.settings.SOUND_SETTINGS"
                    "settings" -> "android.settings.SETTINGS"
                    "date" -> "android.settings.DATE_SETTINGS"
                    "display" -> "android.settings.DISPLAY_SETTINGS"
                    "accessibility" -> "android.settings.ACCESSIBILITY_SETTINGS"
                    "add_account" -> "android.settings.ADD_ACCOUNT_SETTINGS"
                    "airplane_mode" -> "android.settings.AIRPLANE_MODE_SETTINGS"
                    "apn" -> "android.settings.APN_SETTINGS"
                    "application_details" -> "android.settings.APPLICATION_DETAILS_SETTINGS"
                    "application_development" -> "android.settings.APPLICATION_DEVELOPMENT_SETTINGS"
                    "app_notification_bubble" -> "android.settings.APP_NOTIFICATION_BUBBLE_SETTINGS"
                    "app_notification" -> "android.settings.APP_NOTIFICATION_SETTINGS"
                    "search" -> "android.search.action.SEARCH_SETTINGS"
                    "battery_saver" -> "android.settings.BATTERY_SAVER_SETTINGS"
                    "biometric_enroll" -> "android.settings.BIOMETRIC_ENROLL"
                    "captioning" -> "android.settings.CAPTIONING_SETTINGS"
                    "cast" -> "android.settings.CAST_SETTINGS"
                    "channel_notification" -> "android.settings.CHANNEL_NOTIFICATION_SETTINGS"
                    "action_condition_provider" -> "android.settings.ACTION_CONDITION_PROVIDER_SETTINGS"
                    "data_usage" -> "android.settings.DATA_USAGE_SETTINGS"
                    "device_info" -> "android.settings.DEVICE_INFO_SETTINGS"
                    "dream" -> "android.settings.DREAM_SETTINGS"
                    "hard_keyboard" -> "android.settings.HARD_KEYBOARD_SETTINGS"
                    "home" -> "android.settings.HOME_SETTINGS"
                    "ignore_background_data_restrictions" -> "android.settings.IGNORE_BACKGROUND_DATA_RESTRICTIONS_SETTINGS"
                    "ignore_battery_optimization" -> "android.settings.IGNORE_BATTERY_OPTIMIZATION_SETTINGS"
                    "input_method" -> "android.settings.INPUT_METHOD_SETTINGS"
                    "input_method_subtype" -> "android.settings.INPUT_METHOD_SUBTYPE_SETTINGS"
                    "internal_storage" -> "android.settings.INTERNAL_STORAGE_SETTINGS"
                    "locale" -> "android.settings.LOCALE_SETTINGS"
                    "manage_all_applications" -> "android.settings.MANAGE_ALL_APPLICATIONS_SETTINGS"
                    "manage_all_files_access_permission" -> "android.settings.MANAGE_ALL_FILES_ACCESS_PERMISSION"
                    "manage_application" -> "android.settings.MANAGE_APPLICATIONS_SETTINGS"
                    "manage_app_all_files_access_permission" -> "android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION"
                    "manage_default_apps" -> "android.settings.MANAGE_DEFAULT_APPS_SETTINGS"
                    "manage_overlay_permission" -> "android.settings.MANAGE_OVERLAY_PERMISSION"
                    "manage_unknown_app_source" -> "android.settings.MANAGE_UNKNOWN_APP_SOURCES"
                    "manage_write" -> "android.settings.MANAGE_WRITE_SETTINGS"
                    "memory_card" -> "android.settings.MEMORY_CARD_SETTINGS"
                    "network_operator" -> "android.settings.NETWORK_OPERATOR_SETTINGS"
                    "nfcsharings" -> "android.settings.NFCSHARING_SETTINGS"
                    "nfc_payment" -> "android.settings.NFC_PAYMENT_SETTINGS"
                    "nfc" -> "android.settings.NFC_SETTINGS"
                    "night_display" -> "android.settings.NIGHT_DISPLAY_SETTINGS"
                    "notification_assistant" -> "android.settings.NOTIFICATION_ASSISTANT_SETTINGS"
                    "notification_listener_detail" -> "android.settings.NOTIFICATION_LISTENER_DETAIL_SETTINGS"
                    "action_notification_listener" -> "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"
                    "notification_policy_access" -> "android.settings.NOTIFICATION_POLICY_ACCESS_SETTINGS"
                    "action_print" -> "android.settings.ACTION_PRINT_SETTINGS"
                    "privacy" -> "android.settings.PRIVACY_SETTINGS"
                    "process_wifi_easy_connect_uri" -> "android.settings.PROCESS_WIFI_EASY_CONNECT_URI"
                    "quick_access_wallet" -> "android.settings.QUICK_ACCESS_WALLET_SETTINGS"
                    "quick_launch" -> "android.settings.QUICK_LAUNCH_SETTINGS"
                    "request_ignore_battery_optimizations" -> "android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS"
                    "request_set_autofill_service" -> "android.settings.REQUEST_SET_AUTOFILL_SERVICE"
                    "show_regulatory_info" -> "android.settings.SHOW_REGULATORY_INFO"
                    "show_work_policy_info" -> "android.settings.SHOW_WORK_POLICY_INFO"
                    "storage_volume_access" -> "android.settings.STORAGE_VOLUME_ACCESS_SETTINGS"
                    else -> return
                }

                handleJumpToSetting(target = target)
            }

            else -> result.notImplemented()
        }
    }

    private fun handleJumpToSetting(target: String) {
        val intent = Intent(target)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
}
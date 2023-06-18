package com.example.flutter_plugins.sms

import android.annotation.SuppressLint
import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.telephony.SmsManager
import androidx.annotation.RequiresApi
import io.flutter.Log
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler

class SmsMethodCallHandler(private val context: Context) : MethodCallHandler {
    private val REQUEST_CODE_SEND_SMS = 205
    private var activity: Activity? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "sendSMS" -> {
                if (!canSendSMS()) {
                    result.error(
                        "device_not_capable",
                        "The current device is not capable of sending text messages.",
                        "A device may be unable to send messages if it does not support messaging or if it is not currently configured to send messages. This only applies to the ability to send text messages via iMessage, SMS, and MMS."
                    )
                    return
                }
                val message = call.argument<String?>("message") ?: ""
                val recipients = call.argument<String?>("recipients") ?: ""
                val sendDirect = call.argument<Boolean?>("sendDirect") ?: false
                sendSMS(result, recipients, message, sendDirect)
            }

            "canSendSMS" -> result.success(canSendSMS())

            else -> result.notImplemented()
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun canSendSMS(): Boolean {
        if (!activity!!.packageManager.hasSystemFeature(PackageManager.FEATURE_TELEPHONY))
            return false
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("smsto:")
        val activityInfo = intent.resolveActivityInfo(activity!!.packageManager, intent.flags.toInt())
        return !(activityInfo == null || !activityInfo.exported)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun sendSMS(result: MethodChannel.Result, phones: String, message: String, sendDirect: Boolean) {
        if (sendDirect) {
            sendSMSDirect(result, phones, message)
        } else {
            sendSMSDialog(result, phones, message)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun sendSMSDirect(result: MethodChannel.Result, phones: String, message: String) {
        // SmsManager is android.telephony
        val sentIntent = PendingIntent.getBroadcast(activity, 0, Intent("SMS_SENT_ACTION"), PendingIntent.FLAG_IMMUTABLE)
        val smsManager: SmsManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.getSystemService(SmsManager::class.java)
        } else {
            SmsManager.getDefault()
        }
        val numbers = phones.split(";")

        for (num in numbers) {
            Log.d("Flutter SMS", "msg.length() : " + message.toByteArray().size)
            if (message.toByteArray().size > 80) {
                val partMessage = smsManager.divideMessage(message)
                smsManager.sendMultipartTextMessage(num, null, partMessage, null, null)
            } else {
                smsManager.sendTextMessage(num, null, message, sentIntent, null)
            }
        }

        result.success("SMS Sent!")
    }

    private fun sendSMSDialog(result: MethodChannel.Result, phones: String, message: String) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("smsto:$phones")
        intent.putExtra("sms_body", message)
        intent.putExtra(Intent.EXTRA_TEXT, message)
        activity?.startActivityForResult(intent, REQUEST_CODE_SEND_SMS)
        result.success("SMS Sent!")
    }
}
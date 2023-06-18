package com.example.flutter_plugins.toast

import android.app.Activity
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.example.flutter_plugins.R
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler

class ToastMethodCallHandler(private val context: Context) : MethodCallHandler {
    private var mToast: Toast? = null

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "showToast" -> {
                val mMessage = call.argument<Any>("msg").toString()
                val bgColor = call.argument<Number>("bgColor")
                val textColor = call.argument<Number>("textColor")
                val textSize = call.argument<Number>("fontSize")

                val mGravity: Int = when (call.argument<Any>("gravity").toString()) {
                    "top" -> Gravity.TOP
                    "center" -> Gravity.CENTER
                    else -> Gravity.BOTTOM
                }

                val mDuration: Int = if (call.argument<Any>("length").toString() == "long") {
                    Toast.LENGTH_LONG
                } else {
                    Toast.LENGTH_SHORT
                }

                if (bgColor != null && Build.VERSION.SDK_INT <= 31) {
                    val layout = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.toast_custom, null)
                    val text = layout.findViewById<TextView>(R.id.text)
                    text.text = mMessage

                    val gradientDrawable: Drawable = if (Build.VERSION.SDK_INT >= 21) {
                        context.getDrawable(R.drawable.corner)!!
                    } else {
                        context.resources.getDrawable(R.drawable.corner)
                    }

                    gradientDrawable.setColorFilter(bgColor.toInt(), PorterDuff.Mode.SRC_IN)
                    text.background = gradientDrawable
                    if (textSize != null) {
                        text.textSize = textSize.toFloat()
                    }
                    if (textColor != null) {
                        text.setTextColor(textColor.toInt())
                    }
                    mToast = Toast(context)
                    mToast?.duration = mDuration
                    mToast?.view = layout
                } else {
                    mToast = Toast.makeText(context, mMessage, mDuration)
                    if (Build.VERSION.SDK_INT <= 31) {
                        try {
                            val textView: TextView = mToast?.view!!.findViewById(android.R.id.message)
                            if (textSize != null) {
                                textView.textSize = textSize.toFloat()
                            }
                            if (textColor != null) {
                                textView.setTextColor(textColor.toInt())
                            }
                        } catch (_: Exception) {

                        }
                    }
                }

                if (Build.VERSION.SDK_INT <= 31) {
                    when (mGravity) {
                        Gravity.CENTER -> {
                            mToast?.setGravity(mGravity, 0, 0)
                        }
                        Gravity.TOP -> {
                            mToast?.setGravity(mGravity, 0, 100)
                        }
                        else -> {
                            mToast?.setGravity(mGravity, 0, 100)
                        }
                    }
                }

                if (context is Activity) {
                    (context as Activity).runOnUiThread { mToast?.show() }
                } else {
                    mToast?.show()
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    mToast?.addCallback(object : Toast.Callback() {
                        override fun onToastHidden() {
                            super.onToastHidden()
                            mToast = null
                        }
                    })
                }
                result.success(true)
            }

            "cancel" -> {
                if (mToast != null) {
                    mToast?.cancel()
                    mToast = null
                }
                result.success(true)
            }

            else -> result.notImplemented()
        }
    }
}
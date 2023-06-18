package com.example.flutter_plugins.shared_preferences

import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import java.io.IOException
import java.util.concurrent.ExecutorService
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class SharedPreferencesMethodCallHandler(
    context: Context
) : MethodCallHandler, SharedPreferencesApi {
    companion object {
        private const val SHARED_PREFERENCES_NAME = "FlutterSharedPreferences"
        private const val LIST_IDENTIFIER = "VGhpcyBpcyB0aGUgcHJlZml4IGZvciBhIGxpc3Qu"
        private const val BIG_INTEGER_PREFIX = "VGhpcyBpcyB0aGUgcHJlZml4IGZvciBCaWdJbnRlZ2Vy"
        private const val DOUBLE_PREFIX = "VGhpcyBpcyB0aGUgcHJlZml4IGZvciBEb3VibGUu"
    }

    private var preferences: SharedPreferences = context.getSharedPreferences(
        SHARED_PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )
    private var executor: ExecutorService =
        ThreadPoolExecutor(0, 1, 30L, TimeUnit.SECONDS, LinkedBlockingQueue<Runnable>())
    private var handler: Handler = Handler(Looper.getMainLooper())

    private val listEncoder: SharedPreferencesListEncoder? = null

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        val key = call.argument<String>("key")

        try {
            when(call.method) {
                "setBool" -> {
                    val value = call.argument<Boolean>("value")
                    commitAsync(
                        preferences.edit().putBoolean(
                            key,
                            value!!
                        ),
                        result
                    )
                }
                else -> result.notImplemented()
            }
        } catch (e: IOException) {
            result.error("IOException encountered", call.method, e)
        }
    }

    private fun commitAsync(
        editor: SharedPreferences.Editor,
        result: MethodChannel.Result
    ) {
//        executor.execute(
////            Runnable {
////                val response = editor.commit()
////                handler.post(
////                    Runnable {
////                        result.success(response)
////                    }
////                )
////            }
//        )
    }

    override fun remove(key: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun setBool(key: String?, value: Boolean?): Boolean {
        TODO("Not yet implemented")
    }

    override fun setString(key: String?, value: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun setInt(key: String?, value: Int?): Boolean {
        TODO("Not yet implemented")
    }

    override fun setDouble(key: String?, value: Double?): Boolean {
        TODO("Not yet implemented")
    }

    override fun setStringList(key: String?, value: List<String>?): Boolean {
        TODO("Not yet implemented")
    }

    override fun clearWithPrefix(key: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun getAllWithPrefix(prefix: String?): Map<String, Any> {
        TODO("Not yet implemented")
    }
}
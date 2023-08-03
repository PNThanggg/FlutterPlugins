package com.example.flutter_plugins.logcat

import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class LogcatMethodCallHandle : MethodCallHandler {
    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "execLogcat" -> {
                val logs = getLogs()

                if (logs.contains("EXCEPTION")) {
                    result.error("UNAVAILABLE", "logs not available.", null)
                } else {
                    result.success(logs)
                }
            }

            else -> result.notImplemented()
        }
    }

    private fun getLogs(): String {
        return try {
            val process = Runtime.getRuntime().exec("logcat -d")
            val bufferedReader = BufferedReader(InputStreamReader(process.inputStream))
            val log = StringBuilder()
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                log.append(line)
                log.append('\n')
            }
            log.toString()
        } catch (e: IOException) {
            "EXCEPTION: $e"
        }
    }
}
package com.example.flutter_plugins.shared_preferences

import android.util.Log
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MessageCodec
import io.flutter.plugin.common.StandardMessageCodec


interface SharedPreferencesApi {
    companion object {
        private fun wrapError(exception: Throwable): ArrayList<Any?> {
            val errorList = ArrayList<Any?>(3)
            if (exception is FlutterError) {
                errorList.add(exception.code)
                errorList.add(exception.message)
                errorList.add(exception.details)
            } else {
                errorList.add(exception.toString())
                errorList.add(exception.javaClass.simpleName)
                errorList.add(
                    "Cause: " + exception.cause + ", Stacktrace: " + Log.getStackTraceString(
                        exception
                    )
                )
            }
            return errorList
        }

        private fun getCodec(): MessageCodec<Any> {
            return StandardMessageCodec()
        }

        fun setup(
            binaryMessenger: BinaryMessenger,
            api: SharedPreferencesApi?
        ) {
            run {
                val taskQueue = binaryMessenger.makeBackgroundTaskQueue()
                val channel = BasicMessageChannel(
                    binaryMessenger,
                    "share_preferences_api.remove",
                    getCodec(),
                    taskQueue
                )
                if (api != null) {
                    channel.setMessageHandler { message: Any?, reply: BasicMessageChannel.Reply<Any> ->
                        var wrapped = ArrayList<Any?>()
                        val args = message as ArrayList<*>?
                        val keyArg = args!![0] as String
                        try {
                            val output = api.remove(keyArg)
                            wrapped.add(0, output)
                        } catch (exception: Throwable) {
                            val wrappedError = wrapError(exception)
                            wrapped = wrappedError
                        }
                        reply.reply(wrapped)
                    }
                } else {
                    channel.setMessageHandler(null)
                }
            }
            run {
                val taskQueue = binaryMessenger.makeBackgroundTaskQueue()
                val channel = BasicMessageChannel(
                    binaryMessenger,
                    "share_preferences_api.setBool",
                    getCodec(),
                    taskQueue
                )
                if (api != null) {
                    channel.setMessageHandler { message: Any?, reply: BasicMessageChannel.Reply<Any> ->
                        var wrapped = ArrayList<Any?>()
                        val args = message as ArrayList<*>?
                        val keyArg = args!![0] as String
                        val valueArg = args[1] as Boolean
                        try {
                            val output = api.setBool(keyArg, valueArg)
                            wrapped.add(0, output)
                        } catch (exception: Throwable) {
                            val wrappedError = wrapError(exception)
                            wrapped = wrappedError
                        }
                        reply.reply(wrapped)
                    }
                } else {
                    channel.setMessageHandler(null)
                }
            }
            run {
                val taskQueue = binaryMessenger.makeBackgroundTaskQueue()
                val channel = BasicMessageChannel(
                    binaryMessenger,
                    "share_preferences_api.setString",
                    getCodec(),
                    taskQueue
                )
                if (api != null) {
                    channel.setMessageHandler { message: Any?, reply: BasicMessageChannel.Reply<Any> ->
                        var wrapped = ArrayList<Any?>()
                        val args = message as ArrayList<*>?
                        val keyArg = args!![0] as String
                        val valueArg = args[1] as String
                        try {
                            val output = api.setString(keyArg, valueArg)
                            wrapped.add(0, output)
                        } catch (exception: Throwable) {
                            val wrappedError = wrapError(exception)
                            wrapped = wrappedError
                        }
                        reply.reply(wrapped)
                    }
                } else {
                    channel.setMessageHandler(null)
                }
            }
            run {
                val taskQueue = binaryMessenger.makeBackgroundTaskQueue()
                val channel = BasicMessageChannel(
                    binaryMessenger,
                    "share_preferences_api.setInt",
                    getCodec(),
                    taskQueue
                )
                if (api != null) {
                    channel.setMessageHandler { message: Any?, reply: BasicMessageChannel.Reply<Any> ->
                        var wrapped = ArrayList<Any?>()
                        val args = message as ArrayList<*>?
                        val keyArg = args!![0] as String
                        val valueArg = args[1] as Number
                        try {
                            val output = api.setInt(
                                keyArg, valueArg.toInt()
                            )
                            wrapped.add(0, output)
                        } catch (exception: Throwable) {
                            val wrappedError = wrapError(exception)
                            wrapped = wrappedError
                        }
                        reply.reply(wrapped)
                    }
                } else {
                    channel.setMessageHandler(null)
                }
            }
            run {
                val taskQueue = binaryMessenger.makeBackgroundTaskQueue()
                val channel = BasicMessageChannel(
                    binaryMessenger,
                    "share_preferences_api.setDouble",
                    getCodec(),
                    taskQueue
                )
                if (api != null) {
                    channel.setMessageHandler { message: Any?, reply: BasicMessageChannel.Reply<Any> ->
                        var wrapped = ArrayList<Any?>()
                        val args = message as ArrayList<*>?
                        val keyArg = args!![0] as String
                        val valueArg = args[1] as Double
                        try {
                            val output = api.setDouble(keyArg, valueArg)
                            wrapped.add(0, output)
                        } catch (exception: Throwable) {
                            val wrappedError = wrapError(exception)
                            wrapped = wrappedError
                        }
                        reply.reply(wrapped)
                    }
                } else {
                    channel.setMessageHandler(null)
                }
            }
            run {
                val taskQueue = binaryMessenger.makeBackgroundTaskQueue()
                val channel = BasicMessageChannel(
                    binaryMessenger,
                    "share_preferences_api.setStringList",
                    getCodec(),
                    taskQueue
                )
                if (api != null) {
                    channel.setMessageHandler { message: Any?, reply: BasicMessageChannel.Reply<Any> ->
                        var wrapped = ArrayList<Any?>()
                        val args = message as ArrayList<Any>?
                        val keyArg = args!![0] as String
                        val valueArg = args[1] as List<String>
                        try {
                            val output = api.setStringList(keyArg, valueArg)
                            wrapped.add(0, output)
                        } catch (exception: Throwable) {
                            val wrappedError = wrapError(exception)
                            wrapped = wrappedError
                        }
                        reply.reply(wrapped)
                    }
                } else {
                    channel.setMessageHandler(null)
                }
            }
            run {
                val taskQueue = binaryMessenger.makeBackgroundTaskQueue()
                val channel = BasicMessageChannel(
                    binaryMessenger,
                    "dev.flutter.pigeon.SharedPreferencesApi.clearWithPrefix",
                    getCodec(),
                    taskQueue
                )
                if (api != null) {
                    channel.setMessageHandler { message: Any?, reply: BasicMessageChannel.Reply<Any> ->
                        var wrapped = ArrayList<Any?>()
                        val args = message as ArrayList<*>?
                        val prefixArg = args!![0] as String
                        try {
                            val output = api.clearWithPrefix(prefixArg)
                            wrapped.add(0, output)
                        } catch (exception: Throwable) {
                            val wrappedError = wrapError(exception)
                            wrapped = wrappedError
                        }
                        reply.reply(wrapped)
                    }
                } else {
                    channel.setMessageHandler(null)
                }
            }
            run {
                val taskQueue = binaryMessenger.makeBackgroundTaskQueue()
                val channel = BasicMessageChannel(
                    binaryMessenger,
                    "share_preferences_api.getAllWithPrefix",
                    getCodec(),
                    taskQueue
                )
                if (api != null) {
                    channel.setMessageHandler { message: Any?, reply: BasicMessageChannel.Reply<Any> ->
                        var wrapped = ArrayList<Any?>()
                        val args = message as ArrayList<*>?
                        val prefixArg = args!![0] as String
                        try {
                            val output = api.getAllWithPrefix(prefixArg)
                            wrapped.add(0, output)
                        } catch (exception: Throwable) {
                            val wrappedError = wrapError(exception)
                            wrapped = wrappedError
                        }
                        reply.reply(wrapped)
                    }
                } else {
                    channel.setMessageHandler(null)
                }
            }
        }
    }

    /** Removes property from shared preferences data set. */
    fun remove(key: String?): Boolean

    fun setBool(key: String?, value: Boolean?): Boolean
    fun setString(key: String?, value: String?): Boolean
    fun setInt(key: String?, value: Int?): Boolean
    fun setDouble(key: String?, value: Double?): Boolean
    fun setStringList(key: String?, value: List<String>?): Boolean

    /** Removes all properties from shared preferences data set with matching prefix. */
    fun clearWithPrefix(key: String?): Boolean

    fun getAllWithPrefix(prefix: String?): Map<String, Any>
}
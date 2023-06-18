package com.example.flutter_plugins.shared_preferences

class FlutterError(val code: String, message: String, val details: Any) :
    RuntimeException(message)
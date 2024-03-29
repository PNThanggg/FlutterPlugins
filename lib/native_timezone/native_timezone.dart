import 'dart:async';

import 'package:flutter/services.dart';

class NativeTimezone {
  static const MethodChannel _channel =
      MethodChannel('com.example.flutter_plugins/native_timezone');

  ///
  /// Returns local timezone from the native layer.
  ///
  static Future<String> getLocalTimezone() async {
    final String? localTimezone = await _channel.invokeMethod("getLocalTimezone");
    if (localTimezone == null) {
      throw ArgumentError("Invalid return from platform getLocalTimezone()");
    }

    return localTimezone;
  }

  ///
  /// Gets the list of available timezones from the native layer.
  ///
  static Future<List<String>> getAvailableTimezones() async {
    final List<String>? availableTimezones =
        await _channel.invokeListMethod<String>("getAvailableTimezones");

    if (availableTimezones == null) {
      throw ArgumentError("Invalid return from platform getAvailableTimezones()");
    }

    return availableTimezones;
  }

  static Future<String> getDisplayName() async {
    final String? displayName = await _channel.invokeMethod("getDisplayName");

    if (displayName == null) {
      throw ArgumentError("Invalid return from platform getDisplayName()");
    }

    return displayName;
  }

  static Future<String> getOffset() async {
    final String? offset = await _channel.invokeMethod<String>("getOffset");

    if (offset == null) {
      throw ArgumentError("Invalid return from platform getOffset()");
    }

    return offset;
  }
}

import 'dart:async';
import 'dart:io';

import 'package:flutter/services.dart';

/// Plugin for fetching the app logs
class Logcat {
  static const platform = MethodChannel('logcat');

  static Future<String> get platformVersion async =>
      await platform.invokeMethod('getPlatformVersion') ?? '42';

  /// Fetches the app logs by executing the logcat command-line tool.
  /// May throw [PlatformException] from [MethodChannel].
  static Future<String> execute() async {
    if (Platform.isIOS) {
      return 'Logs can only be fetched from Android Devices presently.';
    }
    String logs;
    try {
      logs = await platform.invokeMethod('execLogcat') ?? "";
    } on PlatformException catch (e) {
      logs = "Failed to get logs: '${e.message}'.";
    }

    return logs;
  }
}
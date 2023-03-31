import 'dart:async';

import 'package:flutter/services.dart';

import '../../meta/meta.dart';
import '../device_info_plus_platform_interface.dart';

/// An implementation of [DeviceInfoPlatform] that uses method channels.
class MethodChannelDeviceInfo extends DeviceInfoPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  MethodChannel channel = const MethodChannel('com.example.flutter_plugins/device_info');

  // Generic method channel for all devices
  @override
  Future<BaseDeviceInfo> deviceInfo() async {
    return BaseDeviceInfo((await channel.invokeMethod('getDeviceInfo')).cast<String, dynamic>());
  }
}

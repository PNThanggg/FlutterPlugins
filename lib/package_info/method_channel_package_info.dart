import 'package:flutter/services.dart';

import 'package_info_data.dart';
import 'package_info_platform_interface.dart';

const MethodChannel _channel =
    MethodChannel('com.example.flutter_plugins/package_info');

class MethodChannelPackageInfo extends PackageInfoPlatform {
  @override
  Future<PackageInfoData> getAll() async {
    final map = await _channel.invokeMapMethod<String, dynamic>('getAll');

    return PackageInfoData(
      appName: map!['appName'] ?? '',
      packageName: map['packageName'] ?? '',
      version: map['version'] ?? '',
      buildNumber: map['buildNumber'] ?? '',
      buildSignature: map['buildSignature'] ?? '',
      installerStore: map['installerStore'] as String?,
    );
  }
}

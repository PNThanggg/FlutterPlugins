import 'dart:async';

import 'package:flutter/services.dart';

import '../meta/meta.dart';
import 'connectivity_plus_platform_interface.dart';
import 'src/utils.dart';

class MethodChannelConnectivity extends ConnectivityPlatform {
  @visibleForTesting
  MethodChannel methodChannel = const MethodChannel('com.example.flutter_plugins/connectivity');

  @visibleForTesting
  EventChannel eventChannel = const EventChannel('com.example.flutter_plugins/connectivity_status');

  Stream<ConnectivityResult>? _onConnectivityChanged;

  @override
  Stream<ConnectivityResult> get onConnectivityChanged {
    _onConnectivityChanged ??= eventChannel
        .receiveBroadcastStream()
        .map((dynamic result) => result.toString())
        .map(parseConnectivityResult);
    return _onConnectivityChanged!;
  }

  @override
  Future<ConnectivityResult> checkConnectivity() {
    return methodChannel
        .invokeMethod<String>('check')
        .then((value) => parseConnectivityResult(value ?? ''));
  }
}

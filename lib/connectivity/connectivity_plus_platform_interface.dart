import 'dart:async';

import '../plugin_platform_interface.dart';
import 'method_channel_connectivity.dart';
import 'src/enums.dart';

export 'src/enums.dart';

abstract class ConnectivityPlatform extends PlatformInterface {
  ConnectivityPlatform() : super(token: _token);

  static final Object _token = Object();

  static ConnectivityPlatform _instance = MethodChannelConnectivity();

  static ConnectivityPlatform get instance => _instance;

  static set instance(ConnectivityPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<ConnectivityResult> checkConnectivity() {
    throw UnimplementedError('checkConnectivity() has not been implemented.');
  }

  Stream<ConnectivityResult> get onConnectivityChanged {
    throw UnimplementedError('get onConnectivityChanged has not been implemented.');
  }
}

import 'dart:async';

import 'connectivity_plus_platform_interface.dart';

class Connectivity {
  factory Connectivity() {
    _singleton ??= Connectivity._();
    return _singleton!;
  }

  Connectivity._();

  static Connectivity? _singleton;

  static ConnectivityPlatform get _platform {
    return ConnectivityPlatform.instance;
  }

  Stream<ConnectivityResult> get onConnectivityChanged {
    return _platform.onConnectivityChanged;
  }

  Future<ConnectivityResult> checkConnectivity() {
    return _platform.checkConnectivity();
  }
}

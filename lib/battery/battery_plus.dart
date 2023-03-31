import 'dart:async';

import 'battery_plus_platform_interface.dart';

/// API for accessing information about the battery of the device the Flutter app is running on.
class Battery {
  /// Constructs a singleton instance of [Battery].
  ///
  /// [Battery] is designed to work as a singleton.
  factory Battery() {
    _singleton ??= Battery._();
    return _singleton!;
  }

  Battery._();

  static Battery? _singleton;

  static BatteryPlatform get _platform {
    return BatteryPlatform.instance;
  }

  /// get battery level
  Future<int> get batteryLevel {
    return _platform.batteryLevel;
  }

  /// check if device is on battery save mode
  ///
  /// Currently only implemented on Android, iOS and Windows.
  Future<bool> get isInBatterySaveMode {
    return _platform.isInBatterySaveMode;
  }

  /// Get battery state
  Future<BatteryState> get batteryState {
    return _platform.batteryState;
  }

  /// Fires whenever the battery state changes.
  Stream<BatteryState> get onBatteryStateChanged {
    return _platform.onBatteryStateChanged;
  }
}

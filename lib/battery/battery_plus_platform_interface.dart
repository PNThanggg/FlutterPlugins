import 'dart:async';

import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'method_channel_battery_plus.dart';
import 'src/enums.dart';

export 'src/enums.dart';

abstract class BatteryPlatform extends PlatformInterface {
  /// Constructs a BatteryPlatform.
  BatteryPlatform() : super(token: _token);

  static final Object _token = Object();

  static BatteryPlatform _instance = MethodChannelBattery();

  /// The default instance of [BatteryPlatform] to use.
  ///
  /// Defaults to [MethodChannelBattery].
  static BatteryPlatform get instance => _instance;

  /// Platform-specific plugins should set this with their own platform-specific
  /// class that extends [BatteryPlatform] when they register themselves.
  static set instance(BatteryPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  /// Returns the current battery level in percent.
  Future<int> get batteryLevel {
    throw UnimplementedError('batteryLevel() has not been implemented.');
  }

  /// Returns true if the device is on battery save mode
  Future<bool> get isInBatterySaveMode {
    throw UnimplementedError('isInBatterySaveMode() has not been implemented.');
  }

  /// Returns the current battery state in percent.
  Future<BatteryState> get batteryState {
    throw UnimplementedError('batteryState() has not been implemented.');
  }

  /// Returns a Stream of BatteryState changes.
  Stream<BatteryState> get onBatteryStateChanged {
    throw UnimplementedError('get onBatteryStateChanged has not been implemented.');
  }
}

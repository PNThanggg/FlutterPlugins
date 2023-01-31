import 'dart:async';

import 'package:flutter/services.dart';

import '../sensors_plus_platform_interface.dart';

class MethodChannelSensors extends SensorsPlatform {
  static const EventChannel _accelerometerEventChannel = EventChannel('com.example.flutter_plugins/sensors/accelerometer');
  static const EventChannel _userAccelerometerEventChannel = EventChannel('com.example.flutter_plugins/sensors/user_accel');
  static const EventChannel _gyroscopeEventChannel = EventChannel('com.example.flutter_plugins/sensors/gyroscope');
  static const EventChannel _magnetometerEventChannel = EventChannel('com.example.flutter_plugins/sensors/magnetometer');

  Stream<AccelerometerEvent>? _accelerometerEvents;
  Stream<GyroscopeEvent>? _gyroscopeEvents;
  Stream<UserAccelerometerEvent>? _userAccelerometerEvents;
  Stream<MagnetometerEvent>? _magnetometerEvents;

  /// A broadcast stream of events from the device accelerometer.
  @override
  Stream<AccelerometerEvent> get accelerometerEvents {
    _accelerometerEvents ??= _accelerometerEventChannel.receiveBroadcastStream().map((dynamic event) {
      final list = event.cast<double>();
      return AccelerometerEvent(list[0]!, list[1]!, list[2]!);
    });
    return _accelerometerEvents!;
  }

  /// A broadcast stream of events from the device gyroscope.
  @override
  Stream<GyroscopeEvent> get gyroscopeEvents {
    _gyroscopeEvents ??= _gyroscopeEventChannel.receiveBroadcastStream().map((dynamic event) {
      final list = event.cast<double>();
      return GyroscopeEvent(list[0]!, list[1]!, list[2]!);
    });
    return _gyroscopeEvents!;
  }

  /// Events from the device accelerometer with gravity removed.
  @override
  Stream<UserAccelerometerEvent> get userAccelerometerEvents {
    _userAccelerometerEvents ??= _userAccelerometerEventChannel.receiveBroadcastStream().map((dynamic event) {
      final list = event.cast<double>();
      return UserAccelerometerEvent(list[0]!, list[1]!, list[2]!);
    });
    return _userAccelerometerEvents!;
  }

  /// A broadcast stream of events from the device magnetometer.
  @override
  Stream<MagnetometerEvent> get magnetometerEvents {
    _magnetometerEvents ??= _magnetometerEventChannel.receiveBroadcastStream().map((dynamic event) {
      final list = event.cast<double>();
      return MagnetometerEvent(list[0]!, list[1]!, list[2]!);
    });
    return _magnetometerEvents!;
  }
}

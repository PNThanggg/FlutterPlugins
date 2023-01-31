import '../sensors_plus_platform_interface.dart';

class Sensors extends SensorsPlatform {
  factory Sensors() => _singleton ??= Sensors._();

  Sensors._();

  static Sensors? _singleton;

  static SensorsPlatform get _platform => SensorsPlatform.instance;

  /// A broadcast stream of events from the device accelerometer.
  @override
  Stream<AccelerometerEvent> get accelerometerEvents {
    return _platform.accelerometerEvents;
  }

  /// A broadcast stream of events from the device gyroscope.
  @override
  Stream<GyroscopeEvent> get gyroscopeEvents {
    return _platform.gyroscopeEvents;
  }

  /// Events from the device accelerometer with gravity removed.
  @override
  Stream<UserAccelerometerEvent> get userAccelerometerEvents {
    return _platform.userAccelerometerEvents;
  }

  /// A broadcast stream of events from the device magnetometer.
  @override
  Stream<MagnetometerEvent> get magnetometerEvents {
    return _platform.magnetometerEvents;
  }
}

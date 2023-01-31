import 'src/accelerometer_event.dart';
import 'src/gyroscope_event.dart';
import 'src/magnetometer_event.dart';
import 'src/sensors.dart';
import 'src/user_accelerometer_event.dart';

final _sensors = Sensors();

/// A broadcast stream of events from the device accelerometer.
Stream<AccelerometerEvent> get accelerometerEvents {
  return _sensors.accelerometerEvents;
}

/// A broadcast stream of events from the device gyroscope.
Stream<GyroscopeEvent> get gyroscopeEvents {
  return _sensors.gyroscopeEvents;
}

/// Events from the device accelerometer with gravity removed.
Stream<UserAccelerometerEvent> get userAccelerometerEvents {
  return _sensors.userAccelerometerEvents;
}

/// A broadcast stream of events from the device magnetometer.
Stream<MagnetometerEvent> get magnetometerEvents {
  return _sensors.magnetometerEvents;
}

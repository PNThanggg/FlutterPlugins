class UserAccelerometerEvent {
  UserAccelerometerEvent(this.x, this.y, this.z);

  final double x;
  final double y;
  final double z;

  @override
  String toString() => '[UserAccelerometerEvent (x: $x, y: $y, z: $z)]';
}

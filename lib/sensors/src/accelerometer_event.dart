class AccelerometerEvent {
  AccelerometerEvent(this.x, this.y, this.z);

  final double x;
  final double y;
  final double z;

  @override
  String toString() => '[AccelerometerEvent (x: $x, y: $y, z: $z)]';
}

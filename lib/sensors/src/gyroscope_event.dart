class GyroscopeEvent {
  GyroscopeEvent(this.x, this.y, this.z);

  final double x;
  final double y;
  final double z;

  @override
  String toString() => '[GyroscopeEvent (x: $x, y: $y, z: $z)]';
}

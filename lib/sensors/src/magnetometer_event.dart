class MagnetometerEvent {
  MagnetometerEvent(this.x, this.y, this.z);

  final double x, y, z;

  @override
  String toString() => '[MagnetometerEvent (x: $x, y: $y, z: $z)]';
}

/// The base class for platform's device info.
class BaseDeviceInfo {
  BaseDeviceInfo(this.data);

  final Map<String, dynamic> data;

  /// For legacy purposes
  @Deprecated('Use [data] getter instead')
  Map<String, dynamic> toMap() => data;

  @override
  String toString() {
    return 'BaseDeviceInfo{data: $data}';
  }
}

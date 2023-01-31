import '../plugin_platform_interface.dart';
import 'method_channel_package_info.dart';
import 'package_info_data.dart';

abstract class PackageInfoPlatform extends PlatformInterface {
  /// Constructs a PackageInfoPlusPlatform.
  PackageInfoPlatform() : super(token: _token);

  static final Object _token = Object();

  /// The default instance of [PackageInfoPlatform] to use.
  static PackageInfoPlatform _instance = MethodChannelPackageInfo();

  /// Defaults to [MethodChannelPackageInfo].
  static PackageInfoPlatform get instance => _instance;

  /// Platform-specific plugins should set this with their own platform-specific
  /// class that extends [PackageInfoPlatform] when they register themselves.
  static set instance(PackageInfoPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  ///Returns a map with the following keys : appName, packageName, version, buildNumber
  Future<PackageInfoData> getAll() {
    throw UnimplementedError('getAll() has not been implemented.');
  }
}

import 'dart:async';

import 'device_info_plus_platform_interface.dart';
import 'model/android_device_info.dart';

/// Provides device and operating system information.
class DeviceInfoPlugin {
  /// No work is done when instantiating the plugin. It's safe to call this
  /// repeatedly or in performance-sensitive blocks.
  DeviceInfoPlugin();

  static DeviceInfoPlatform get _platform {
    return DeviceInfoPlatform.instance;
  }

  /// This information does not change from call to call. Cache it.
  AndroidDeviceInfo? _cachedAndroidDeviceInfo;

  /// Information derived from `android.os.Build`.

  Future<AndroidDeviceInfo> get androidInfo async =>
      _cachedAndroidDeviceInfo ??= AndroidDeviceInfo.fromMap((await _platform.deviceInfo()).data);

  // /// This information does not change from call to call. Cache it.
  // IosDeviceInfo? _cachedIosDeviceInfo;
  //
  // /// Information derived from `UIDevice`.
  // ///
  // /// See: https://developer.apple.com/documentation/uikit/uidevice
  // Future<IosDeviceInfo> get iosInfo async => _cachedIosDeviceInfo ??=
  //     IosDeviceInfo.fromMap((await _platform.deviceInfo()).data);
  //
  // /// This information does not change from call to call. Cache it.
  // LinuxDeviceInfo? _cachedLinuxDeviceInfo;
  //
  // /// Information derived from `/etc/os-release`.
  // Future<LinuxDeviceInfo> get linuxInfo async => _cachedLinuxDeviceInfo ??=
  //     await _platform.deviceInfo() as LinuxDeviceInfo;
  //
  // /// This information does not change from call to call. Cache it.
  // WebBrowserInfo? _cachedWebBrowserInfo;
  //
  // /// Information derived from `Navigator`.
  // Future<WebBrowserInfo> get webBrowserInfo async =>
  //     _cachedWebBrowserInfo ??= await _platform.deviceInfo() as WebBrowserInfo;
  //
  // /// This information does not change from call to call. Cache it.
  // MacOsDeviceInfo? _cachedMacosDeviceInfo;
  //
  // /// Returns device information for macos. Information sourced from Sysctl.
  // Future<MacOsDeviceInfo> get macOsInfo async => _cachedMacosDeviceInfo ??=
  //     MacOsDeviceInfo.fromMap((await _platform.deviceInfo()).data);
  //
  // WindowsDeviceInfo? _cachedWindowsDeviceInfo;
  //
  // /// Returns device information for Windows.
  // Future<WindowsDeviceInfo> get windowsInfo async =>
  //     _cachedWindowsDeviceInfo ??=
  //         await _platform.deviceInfo() as WindowsDeviceInfo;

  /// Returns device information for the current platform.
  Future<BaseDeviceInfo> get deviceInfo async {
    // if (kIsWeb) {
    //   return webBrowserInfo;
    // } else {
    //   if (Platform.isAndroid) {
    //     return androidInfo;
    //   } else if (Platform.isIOS) {
    //     return iosInfo;
    //   } else if (Platform.isLinux) {
    //     return linuxInfo;
    //   } else if (Platform.isMacOS) {
    //     return macOsInfo;
    //   } else if (Platform.isWindows) {
    //     return windowsInfo;
    //   }
    // }
    //
    // // allow for extension of the plugin
    // return _platform.deviceInfo();

    return androidInfo;
  }
}

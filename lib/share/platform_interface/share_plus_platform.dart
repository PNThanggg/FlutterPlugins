import 'dart:async';
import 'dart:ui';

import '../../cross_file/cross_file.dart';
import '../../plugin_platform_interface.dart';
import '../method_channel/method_channel_share.dart';

class SharePlatform extends PlatformInterface {
  SharePlatform() : super(token: _token);

  static final Object _token = Object();

  static SharePlatform _instance = MethodChannelShare();

  static SharePlatform get instance => _instance;

  static set instance(SharePlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  /// Share text.
  Future<void> share(
    String text, {
    String? subject,
    Rect? sharePositionOrigin,
  }) {
    return _instance.share(
      text,
      subject: subject,
      sharePositionOrigin: sharePositionOrigin,
    );
  }

  /// Share files.
  @Deprecated("Use shareXFiles instead.")
  Future<void> shareFiles(
    List<String> paths, {
    List<String>? mimeTypes,
    String? subject,
    String? text,
    Rect? sharePositionOrigin,
  }) {
    return _instance.shareFiles(
      paths,
      mimeTypes: mimeTypes,
      subject: subject,
      text: text,
      sharePositionOrigin: sharePositionOrigin,
    );
  }

  /// Share text with Result.
  Future<ShareResult> shareWithResult(
    String text, {
    String? subject,
    Rect? sharePositionOrigin,
  }) async {
    await _instance.share(
      text,
      subject: subject,
      sharePositionOrigin: sharePositionOrigin,
    );

    return _resultUnavailable;
  }

  /// Share files with Result.
  @Deprecated("Use shareXFiles instead.")
  Future<ShareResult> shareFilesWithResult(
    List<String> paths, {
    List<String>? mimeTypes,
    String? subject,
    String? text,
    Rect? sharePositionOrigin,
  }) async {
    await _instance.shareFiles(
      paths,
      mimeTypes: mimeTypes,
      subject: subject,
      text: text,
      sharePositionOrigin: sharePositionOrigin,
    );

    return _resultUnavailable;
  }

  /// Share [XFile] objects with Result.
  Future<ShareResult> shareXFiles(
    List<XFile> files, {
    String? subject,
    String? text,
    Rect? sharePositionOrigin,
  }) async {
    return _instance.shareXFiles(
      files,
      subject: subject,
      text: text,
      sharePositionOrigin: sharePositionOrigin,
    );
  }
}

class ShareResult {
  /// The raw return value from the share.
  final String raw;

  /// The action the user has taken
  final ShareResultStatus status;

  const ShareResult(this.raw, this.status);
}

/// How the user handled the share-sheet
enum ShareResultStatus {
  success,
  dismissed,
  unavailable,
}

const _resultUnavailable = ShareResult(
  'com.example.flutter_plugins/share/unavailable',
  ShareResultStatus.unavailable,
);

import 'dart:typed_data';

class Digest {
  final List<int> bytes;

  Digest(this.bytes);

  /// Returns whether this is equal to another digest.
  ///
  /// This should be used instead of manual comparisons to avoid leaking
  /// information via timing.
  @override
  bool operator ==(Object other) {
    if (other is Digest) {
      final a = bytes;
      final b = other.bytes;
      final n = a.length;
      if (n != b.length) {
        return false;
      }
      var mismatch = 0;
      for (var i = 0; i < n; i++) {
        mismatch |= a[i] ^ b[i];
      }
      return mismatch == 0;
    }
    return false;
  }

  @override
  int get hashCode => Object.hashAll(bytes);

  /// The message digest as a string of hexadecimal digits.
  @override
  String toString() => _hexEncode(bytes);
}

String _hexEncode(List<int> bytes) {
  const hexDigits = '0123456789abcdef';
  var charCodes = Uint8List(bytes.length * 2);
  for (var i = 0, j = 0; i < bytes.length; i++) {
    var byte = bytes[i];
    charCodes[j++] = hexDigits.codeUnitAt((byte >> 4) & 0xF);
    charCodes[j++] = hexDigits.codeUnitAt(byte & 0xF);
  }
  return String.fromCharCodes(charCodes);
}

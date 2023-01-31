class MimeMultipartException implements Exception {
  final String message;

  const MimeMultipartException([this.message = '']);

  @override
  String toString() => 'MimeMultipartException: $message';
}

abstract class MimeMultipart extends Stream<List<int>> {
  Map<String, String> get headers;
}

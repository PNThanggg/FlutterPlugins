import 'digest.dart';

class DigestSink extends Sink<Digest> {
  Digest get value => _value!;

  Digest? _value;

  @override
  void add(Digest data) {
    if (_value != null) throw StateError('add may only be called once.');
    _value = data;
  }

  @override
  void close() {
    if (_value == null) throw StateError('add must be called once.');
  }
}

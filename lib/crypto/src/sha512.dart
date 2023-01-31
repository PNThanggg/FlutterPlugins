import 'dart:convert';

import 'digest.dart';
import 'hash.dart';
// ignore: uri_does_not_exist
import 'sha512_fastsinks.dart' if (dart.library.js) 'sha512_slowsinks.dart';
import 'utils.dart';

const Hash sha384 = _Sha384._();

const Hash sha512 = _Sha512._();

const Hash sha512224 = _Sha512224();

const Hash sha512256 = _Sha512256();

class _Sha384 extends Hash {
  @override
  final int blockSize = 32 * bytesPerWord;

  const _Sha384._();

  @override
  ByteConversionSink startChunkedConversion(Sink<Digest> sink) => ByteConversionSink.from(Sha384Sink(sink));
}

class _Sha512 extends Hash {
  @override
  final int blockSize = 32 * bytesPerWord;

  const _Sha512._();

  @override
  ByteConversionSink startChunkedConversion(Sink<Digest> sink) => ByteConversionSink.from(Sha512Sink(sink));
}

class _Sha512224 extends Hash {
  @override
  final int blockSize = 32 * bytesPerWord;

  const _Sha512224();

  @override
  ByteConversionSink startChunkedConversion(Sink<Digest> sink) => ByteConversionSink.from(Sha512224Sink(sink));
}

class _Sha512256 extends Hash {
  @override
  final int blockSize = 32 * bytesPerWord;

  const _Sha512256();

  @override
  ByteConversionSink startChunkedConversion(Sink<Digest> sink) => ByteConversionSink.from(Sha512256Sink(sink));
}

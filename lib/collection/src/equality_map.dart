import 'dart:collection';

import 'equality.dart';
import 'wrappers.dart';

/// A [Map] whose key equality is determined by an [Equality] object.
class EqualityMap<K, V> extends DelegatingMap<K, V> {
  /// Creates a map with equality based on [equality].
  EqualityMap(Equality<K> equality)
      : super(LinkedHashMap(
            equals: equality.equals,
            hashCode: equality.hash,
            isValidKey: equality.isValidKey));

  /// Creates a map with equality based on [equality] that contains all
  /// key-value pairs of [other].
  ///
  /// If [other] has multiple keys that are equivalent according to [equality],
  /// the last one reached during iteration takes precedence.
  EqualityMap.from(Equality<K> equality, Map<K, V> other)
      : super(LinkedHashMap(
            equals: equality.equals,
            hashCode: equality.hash,
            isValidKey: equality.isValidKey)) {
    addAll(other);
  }
}

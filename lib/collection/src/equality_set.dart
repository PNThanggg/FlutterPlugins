import 'dart:collection';

import 'equality.dart';
import 'wrappers.dart';

/// A [Set] whose key equality is determined by an [Equality] object.
class EqualitySet<E> extends DelegatingSet<E> {
  /// Creates a set with equality based on [equality].
  EqualitySet(Equality<E> equality)
      : super(LinkedHashSet(
            equals: equality.equals,
            hashCode: equality.hash,
            isValidKey: equality.isValidKey));

  /// Creates a set with equality based on [equality] that contains all
  /// elements in [other].
  ///
  /// If [other] has multiple values that are equivalent according to
  /// [equality], the first one reached during iteration takes precedence.
  EqualitySet.from(Equality<E> equality, Iterable<E> other)
      : super(LinkedHashSet(
            equals: equality.equals,
            hashCode: equality.hash,
            isValidKey: equality.isValidKey)) {
    addAll(other);
  }
}

import 'enums.dart';

/// Convert a String to a LocationAuthorizationStatus value.
LocationAuthorizationStatus parseLocationAuthorizationStatus(String? result) {
  switch (result) {
    case 'notDetermined':
      return LocationAuthorizationStatus.notDetermined;

    case 'restricted':
      return LocationAuthorizationStatus.restricted;

    case 'denied':
      return LocationAuthorizationStatus.denied;

    case 'authorizedAlways':
      return LocationAuthorizationStatus.authorizedAlways;

    case 'authorizedWhenInUse':
      return LocationAuthorizationStatus.authorizedWhenInUse;

    default:
      return LocationAuthorizationStatus.unknown;
  }
}

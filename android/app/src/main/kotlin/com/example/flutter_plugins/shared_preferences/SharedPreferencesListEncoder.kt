package com.example.flutter_plugins.shared_preferences

interface SharedPreferencesListEncoder {
  /** Converts list to String for storing in shared preferences.  */
  fun encode(list: List<String?>?): String?

  /** Converts stored String representing List<String> to List. </String> */
  fun decode(listString: String?): List<String?>?
}
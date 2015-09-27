package com.shekhar.olaalarma.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by ShekharKG on 9/27/2015.
 */
public class StorageHelper {

  public static final String CATEGORY = "category";
  public static final String TIME = "time";
  public static final String WEEK_DAYS = "week_days";

  public static void storePreference(Context context, String key, String value) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = preferences.edit();
    editor.putString(key, value);
    editor.apply();
  }

  public static String getPreference(Context context, String key) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    return preferences.getString(key, "");
  }

  public static void clearPreferences(Context context) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = preferences.edit();
    editor.clear();
    editor.apply();
  }

}

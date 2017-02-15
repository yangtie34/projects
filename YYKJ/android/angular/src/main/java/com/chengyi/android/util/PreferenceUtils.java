package com.chengyi.android.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.chengyi.android.angular.core.Scope;

public class PreferenceUtils {
    public static String getPrefString(String key,
                                       final String defaultValue) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(Scope.activity);
        return settings.getString(key, defaultValue);
    }

    public static void setPrefString(final String key,
                                     final String value) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(Scope.activity);
        settings.edit().putString(key, value).commit();
    }

    public static boolean getPrefBoolean(final String key,
                                         final boolean defaultValue) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(Scope.activity);
        return settings.getBoolean(key, defaultValue);
    }

    public static boolean hasKey(final String key) {
        return PreferenceManager.getDefaultSharedPreferences(Scope.activity).contains(
                key);
    }

    public static void setPrefBoolean(final String key,
                                      final boolean value) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(Scope.activity);
        settings.edit().putBoolean(key, value).commit();
    }

    public static void setPrefInt(final String key,
                                  final int value) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(Scope.activity);
        settings.edit().putInt(key, value).commit();
    }

    public static int getPrefInt(final String key,
                                 final int defaultValue) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(Scope.activity);
        return settings.getInt(key, defaultValue);
    }

    public static void setPrefFloat(final String key,
                                    final float value) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(Scope.activity);
        settings.edit().putFloat(key, value).commit();
    }

    public static float getPrefFloat(final String key,
                                     final float defaultValue) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(Scope.activity);
        return settings.getFloat(key, defaultValue);
    }

    public static void setSettingLong(final String key,
                                      final long value) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(Scope.activity);
        settings.edit().putLong(key, value).commit();
    }

    public static long getPrefLong(final String key,
                                   final long defaultValue) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(Scope.activity);
        return settings.getLong(key, defaultValue);
    }

    public static void clearPreference() {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(Scope.activity);
        final SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
    }
}

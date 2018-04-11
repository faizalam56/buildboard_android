package com.buildboard.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreference {

    private static final String APP_SHARED_PREFERENCE = "App_Preference";
    private static SharedPreferences.Editor editor;

    public static AppPreference create(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        AppPreference userSharedPreferences = new AppPreference();
        userSharedPreferences.editor = sharedPreferences.edit();

        return userSharedPreferences;
    }

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    public static void setBoolean(boolean loggedin, String key) {
        editor.putBoolean(key, loggedin);
        editor.apply();
    }

    public static String getString(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

    public static void setString(String sessionId, String key) {
        editor.putString(key, sessionId).apply();
    }

    public static void setInteger(int activeJobPosition, String key) {
        editor.putInt(key, activeJobPosition).apply();
    }

    public static int getInteger(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }
}

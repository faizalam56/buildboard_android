package com.buildboard.preferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class AppPreference {

    private static final String APP_SHARED_PREFERENCE = "App_Preference";
    private static SharedPreferences sSharedPreferences;
    private static AppPreference sAppPreference;
    private SharedPreferences.Editor mEditor;

    @SuppressLint("CommitPrefEdits")
    private AppPreference(Context mContext) {
        sSharedPreferences = mContext.getSharedPreferences(APP_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        mEditor = sSharedPreferences.edit();
    }

    public static AppPreference getAppPreference(Context mContext) {
        if (sAppPreference == null) sAppPreference = new AppPreference(mContext);

        return sAppPreference;
    }
    
    public boolean getBoolean(String key) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        return sSharedPreferences.getBoolean(key, false);
    }

    public void setBoolean(boolean loggedin, String key) {
        mEditor.putBoolean(key, loggedin).apply();
    }

    public String getString(String key) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        return sSharedPreferences.getString(key, "");
    }

    public void setString(String sessionId, String key) {
        mEditor.putString(key, sessionId);
        mEditor.apply();
    }

    public void setInteger(int activeJobPosition, String key) {
        mEditor.putInt(key, activeJobPosition).apply();
    }

    public int getInteger(String key) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        return sSharedPreferences.getInt(key, 0);
    }
}

package com.buildboard.app;

import android.app.Application;

import com.buildboard.BuildConfig;

public class MyApplication extends Application {

    private static MyApplication instance;
    private String baseUrl;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        baseUrl = BuildConfig.BASE_URL;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}

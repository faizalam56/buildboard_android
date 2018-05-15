package com.buildboard.modules.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.modules.home.HomeActivity;
import com.buildboard.modules.login.LoginActivity;
import com.buildboard.preferences.AppPreference;

public class SplashActivity extends AppCompatActivity implements AppConstant {

    private static final int SPLASH_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (AppPreference.getAppPreference(SplashActivity.this).getBoolean(IS_LOGIN))
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                else
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                SplashActivity.this.finish();
            }
        }, SPLASH_TIME);
    }
}

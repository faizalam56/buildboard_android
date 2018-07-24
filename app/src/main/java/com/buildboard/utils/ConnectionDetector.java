package com.buildboard.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class ConnectionDetector {
    public static boolean isNetworkConnected(Context mContext) {
        if (mContext == null) {
            return false;
        }
        ConnectivityManager cm = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return (ni != null && ni.isConnected());
    }
    public static void showMessage(Context context){
        Toast.makeText(context, "No Internet Connection , Please try again", Toast.LENGTH_SHORT).show();
    }
}

package com.buildboard.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import com.buildboard.R;

public class ConnectionDetector {
    public static boolean isNetworkConnected(Context mContext) {
        if (mContext == null) {
            return false;
        }
        ConnectivityManager cm = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = null;
        if (cm != null) {
            ni = cm.getActiveNetworkInfo();
        }
        return (ni != null && ni.isConnected());
    }

    public static Snackbar createSnackBar(Context context, View view) {
        Snackbar snackbar = Snackbar.make(view, context.getString(R.string.internet_connection_check), Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
        snackbar.show();
        return snackbar;
    }
}

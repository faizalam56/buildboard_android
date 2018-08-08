package com.buildboard.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

public class ProgressHelper {

    private static ProgressDialog dialog;
    private static ProgressBar sProgressBar;

    public static void start(Context context, String message) {
        dialog = new ProgressDialog(context);
        dialog.setMessage(message);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void stop() {
        if (dialog.isShowing())
            dialog.dismiss();
    }

    public static void showProgressBar(Activity activity, ProgressBar progressBar) {
        sProgressBar = progressBar;
        sProgressBar.setVisibility(View.VISIBLE);
        Utils.showProgressColor(activity, sProgressBar);
    }

    public static void hideProgressBar() {
        if (sProgressBar != null)
            sProgressBar.setVisibility(View.GONE);
    }
}

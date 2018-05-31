package com.buildboard.http;

import android.app.Activity;
import android.view.View;

import com.buildboard.models.ErrorResponse;
import com.buildboard.view.SnackBarFactory;

public class ErrorManager {
    private Activity mActivity;
    private View mView;
    private Object mObject;

    public ErrorManager(Activity activity, View view, Object object) {
        mActivity = activity;
        mView = view;
        mObject = object;
    }

    public void handleErrorResponse() {
        if (mObject == null) {
            return;
        }

        if (mObject instanceof ErrorResponse) {
            ErrorResponse response = (ErrorResponse) mObject;
            if (response.getMessage() != null && response.getMessage() != null && response.getMessage().get(0) != null)
                SnackBarFactory.createSnackBar(mActivity, mView, response.getMessage().get(0));
        }

        if (mObject instanceof Throwable)
            SnackBarFactory.createSnackBar(mActivity, mView, ((Throwable) mObject).getLocalizedMessage());
    }
}

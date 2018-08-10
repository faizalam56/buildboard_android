package com.buildboard.modules.home.modules.profile.consumer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.buildboard.R;
import com.buildboard.customviews.BuildBoardButton;
import com.buildboard.customviews.BuildBoardEditText;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.http.DataManager;
import com.buildboard.models.ErrorResponse;
import com.buildboard.modules.home.modules.profile.consumer.models.ChangePasswordRequest;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.Utils;
import com.buildboard.view.SnackBarFactory;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePasswordActivity extends Activity {

    @BindView(R.id.text_old_password)
    BuildBoardTextView textOldPassword;
    @BindView(R.id.text_new_password)
    BuildBoardTextView textNewPassword;
    @BindView(R.id.text_confirm_password)
    BuildBoardTextView textConfirmPassword;
    @BindView(R.id.button_next)
    BuildBoardButton buildBoardButton;
    @BindView(R.id.container)
    ConstraintLayout constraintLayout;
    @BindView(R.id.edit_old_password)
    BuildBoardEditText editOldPassword;
    @BindView(R.id.edit_new_password)
    BuildBoardEditText editNewPassword;
    @BindView(R.id.edit_confirm_password)
    BuildBoardEditText editConfirmPassword;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.title)
    TextView title;

    @BindString(R.string.error_old_password)
    String stringErrorOldPasswordEmptyMsg;
    @BindString(R.string.error_new_password)
    String stringErrorNewPasswordEmptyMsg;
    @BindString(R.string.error_confirm_password)
    String stringErrorConfirmPasswordEmptyMsg;
    @BindString(R.string.error_incorrect_password_length)
    String stringErrorPasswordLength;
    @BindString(R.string.password_alert_message)
    String stringErrorPasswordNotMatched;
    @BindString(R.string.change_password)
    String stringChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        title.setText(stringChangePassword);
        setAsteriskToText();
    }

    private void setAsteriskToText() {
        textOldPassword.setText(Utils.setStarToLabel(getString(R.string.old_password)));
        textNewPassword.setText(Utils.setStarToLabel(getString(R.string.new_password)));
        textConfirmPassword.setText(Utils.setStarToLabel(getString(R.string.confirm_password)));
    }

    @OnClick(R.id.button_next)
    public void changePasswordTapped(){
        String oldPassword = editOldPassword.getText().toString();
        String newPassword = editNewPassword.getText().toString();
        String confirmPassword = editConfirmPassword.getText().toString();

        if(ConnectionDetector.isNetworkConnected(this)) {
            if (validateFields(oldPassword, newPassword, confirmPassword)) {
                if (newPassword.equals(confirmPassword)) {
                    changePasswordCall(oldPassword, newPassword);
                } else {
                    SnackBarFactory.createSnackBar(this, constraintLayout, stringErrorPasswordNotMatched).show();
                }
            }
        } else {
            ConnectionDetector.createSnackBar(this, constraintLayout);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private boolean validateFields(String oldPassword, String newPassword, String confirmPassword) {

        if (TextUtils.isEmpty(oldPassword)) {
            SnackBarFactory.createSnackBar(this, constraintLayout, stringErrorOldPasswordEmptyMsg).show();
            return false;
        } else if (oldPassword.length() < 6) {
            SnackBarFactory.createSnackBar(this, constraintLayout, stringErrorPasswordLength).show();
            return false;
        }

        if (TextUtils.isEmpty(newPassword)) {
            SnackBarFactory.createSnackBar(this, constraintLayout, stringErrorNewPasswordEmptyMsg).show();
            return false;
        } else if (newPassword.length() < 6) {
            SnackBarFactory.createSnackBar(this, constraintLayout, stringErrorPasswordLength).show();
            return false;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            SnackBarFactory.createSnackBar(this, constraintLayout, stringErrorConfirmPasswordEmptyMsg).show();
            return false;
        } else if (confirmPassword.length() < 6) {
            SnackBarFactory.createSnackBar(this, constraintLayout, stringErrorPasswordLength).show();
            return false;
        }

        return true;
    }

    private void changePasswordCall(String oldPassword, String newPassword) {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setOldPassword(oldPassword);
        changePasswordRequest.setNewPassword(newPassword);

        ProgressHelper.showProgressBar(ChangePasswordActivity.this,progressBar);
        DataManager.getInstance().changePasswordCall(ChangePasswordActivity.this, changePasswordRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
               ProgressHelper.hideProgressBar();
                if (response == null) return;
                String data = (String) response;
                Toast.makeText(ChangePasswordActivity.this, data, Toast.LENGTH_SHORT).show();
                finish();
            }
            @Override
            public void onError(Object error) {
                ProgressHelper.hideProgressBar();
                ArrayList<String> errorResponse = (ArrayList<String>) error;
                Toast.makeText(ChangePasswordActivity.this, errorResponse.get(0), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

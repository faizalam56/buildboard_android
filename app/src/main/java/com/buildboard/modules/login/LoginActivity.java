package com.buildboard.modules.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.modules.selection.SelectionActivity;
import com.buildboard.modules.signup.SignUpActivity;
import com.buildboard.constants.AppConstant;
import com.buildboard.fonts.FontHelper;
import com.buildboard.view.SnackBarFactory;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindArray;
import butterknife.BindFont;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.edit_username)
    EditText editUserName;
    @BindView(R.id.edit_password)
    EditText editPassword;

    @BindView(R.id.text_user_type)
    TextView textUserType;
    @BindView(R.id.text_forgot_password)
    TextView textForgotPassword;
    @BindView(R.id.text_signup)
    TextView textSignUp;

    @BindView(R.id.button_signin)
    Button buttonSignIn;
    @BindView(R.id.button_login_facebook)
    Button buttonLoginFacebook;
    @BindView(R.id.button_login_google)
    Button buttonLoginGoogle;

    @BindString(R.string.contractor)
    String stringContractor;
    @BindString(R.string.consumer)
    String stringConsumer;
    @BindString(R.string.error_password)
    String stringPasswordEmptyMsg;
    @BindString(R.string.error_username)
    String stringUsernameEmptyMsg;
    @BindString(R.string.user_type)
    String stringUserType;

    @BindArray(R.array.user_type_array)
    String[] arrayUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setFont();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (data == null) return;

            if (requestCode == USER_TYPE_RESULT_CODE) {
                    textUserType.setText(data.getStringExtra(INTENT_SELECTED_ITEM));
            }
        }
    }

    @OnClick(R.id.text_signup)
    public void openSignUpScreen(View view) {
        openActivity(SignUpActivity.class, false);
    }

    @OnClick(R.id.text_user_type)
    public void openUserTypeSelection(View view) {
        openActivity(SelectionActivity.class, true);
    }

    @OnClick(R.id.button_signin)
    public void signinTapped(View view) {
        isFieldsValid();
    }

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, editPassword, editUserName, textForgotPassword, textSignUp,
                buttonLoginFacebook, buttonLoginGoogle, buttonSignIn);
    }

    private boolean isFieldsValid() {
        if (TextUtils.isEmpty(editUserName.getText()) || editPassword.getText().length() < 3) {
            SnackBarFactory.createSnackBar(this, editPassword.getRootView(), stringUsernameEmptyMsg);
            return false;
        }
        if (TextUtils.isEmpty(editPassword.getText()) || editPassword.getText().length() < 8) {
            SnackBarFactory.createSnackBar(this, editPassword.getRootView(), stringPasswordEmptyMsg);
            return false;
        }

        return true;
    }

    private void openActivity(Class classToReplace, boolean isStartForResult) {
        Intent intent = new Intent(LoginActivity.this, classToReplace);
        if (isStartForResult) {
            intent.putExtra(DATA, new ArrayList<>(Arrays.asList(arrayUserType)));
            intent.putExtra(INTENT_TITLE, stringUserType);
            startActivityForResult(intent, USER_TYPE_RESULT_CODE);
        } else startActivity(intent);
    }
}

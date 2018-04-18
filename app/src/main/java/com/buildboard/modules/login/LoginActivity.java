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
import com.buildboard.modules.selection.UserTypeLoginActivity;
import com.buildboard.modules.signup.SignUpActivity;
import com.buildboard.modules.signup.SignUpContractorActivity;
import com.buildboard.utils.AppConstant;
import com.buildboard.utils.FontHelper;
import com.buildboard.utils.SnackBarFactory;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.edit_username)
    EditText editUserName;
    @BindView(R.id.edit_password)
    EditText editPassword;

    @BindView(R.id.text_forgot_password)
    TextView textForgotPassword;
    @BindView(R.id.text_signup)
    TextView textSignUp;
    @BindView(R.id.text_user_type)
    EditText textUserType;

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
    @BindString(R.string.error_password_msg)
    String stringPasswordEmptyMsg;
    @BindString(R.string.error_username_msg)
    String stringUsernameEmptyMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setFont();
    }

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, editPassword, editUserName, textForgotPassword, textSignUp,
                buttonLoginFacebook, buttonLoginGoogle, buttonSignIn);
    }

    private boolean isFieldsValid() {
        if (TextUtils.isEmpty(editUserName.getText())) {
            SnackBarFactory.createSnackBar(this, editPassword.getRootView(), stringUsernameEmptyMsg);
            return false;
        }
        if (TextUtils.isEmpty(editPassword.getText())) {
            SnackBarFactory.createSnackBar(this, editPassword.getRootView(), stringPasswordEmptyMsg);
            return false;
        }

        return true;
    }

    public void gotoSignUpScreen(View view) {
        if (textUserType.getText().toString().equalsIgnoreCase(stringContractor))
            openActivity(SignUpContractorActivity.class, false);
        else
            openActivity(SignUpActivity.class, false);
    }

    private void openActivity(Class classToReplace, boolean isStartForResult) {
        Intent intent = new Intent(LoginActivity.this, classToReplace);
        if (isStartForResult)
            startActivityForResult(intent, ACTIVITY_RESULT_CODE);
        else startActivity(intent);
    }

    public void openUserTypeSelection(View view) {
        openActivity(UserTypeLoginActivity.class, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;

        if (requestCode == ACTIVITY_RESULT_CODE) {
            if (data.hasExtra(INTENT_SELECTION))
                textUserType.setText(data.getStringExtra(INTENT_SELECTION));
        }
    }
}

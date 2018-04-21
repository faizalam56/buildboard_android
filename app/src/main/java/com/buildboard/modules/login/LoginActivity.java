package com.buildboard.modules.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.fonts.FontHelper;
import com.buildboard.modules.forgotpassword.ForgotPasswordActivity;
import com.buildboard.modules.selection.SelectionActivity;
import com.buildboard.modules.signup.SignUpActivity;
import com.buildboard.view.SnackBarFactory;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindArray;
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
    @BindView(R.id.text_sign_up)
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
    @BindString(R.string.error_incorrect_password_length)
    String stringIncorrectPassword;
    @BindString(R.string.error_select_user_type)
    String stringSelectUserType;
    @BindString(R.string.error_username_short)
    String stringUsernameTooShort;

    @BindArray(R.array.user_type_array)
    String[] arrayUserType;

    @BindView(R.id.constraint_root)
    ConstraintLayout constraintRoot;

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

        if (data == null) return;

        if (resultCode == RESULT_OK && requestCode == USER_TYPE_REQUEST_CODE) {
            textUserType.setText(data.getStringExtra(INTENT_SELECTED_ITEM));
        }
    }

    @OnClick(R.id.text_sign_up)
    void signUpTapped() {
        openActivity(SignUpActivity.class, false);
    }

    @OnClick(R.id.text_user_type)
    void userTypeTapped() {
        openActivity(SelectionActivity.class, true);
    }

    @OnClick(R.id.button_signin)
    void signInTapped() {
        String userName = editUserName.getText().toString();
        String password = editPassword.getText().toString();
        String userType = textUserType.getText().toString();

        if (validateFields(userName, password, userType)) {
            // TODO: 4/21/18
        }
    }

    @OnClick(R.id.text_forgot_password)
    void forgotPasswordTapped() {
        openActivity(ForgotPasswordActivity.class, false);
    }

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, editPassword, editUserName, textForgotPassword, textSignUp,
                buttonLoginFacebook, buttonLoginGoogle, buttonSignIn);
    }

    private boolean validateFields(String userName, String password, String userType) {
        if (userType.equals(stringUserType)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringSelectUserType);
            return false;
        }
        if (TextUtils.isEmpty(userName)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringUsernameEmptyMsg);
            return false;
        } else if (userName.length() < 3) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringUsernameTooShort);
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringPasswordEmptyMsg);
            return false;
        } else if (password.length() < 8) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringIncorrectPassword);
            return false;
        }

        return true;
    }

    private void openActivity(Class classToReplace, boolean isStartForResult) {
        Intent intent = new Intent(LoginActivity.this, classToReplace);
        if (isStartForResult) {
            intent.putExtra(DATA, new ArrayList<>(Arrays.asList(arrayUserType)));
            intent.putExtra(INTENT_TITLE, stringUserType);
            startActivityForResult(intent, USER_TYPE_REQUEST_CODE);
        } else startActivity(intent);
    }
}

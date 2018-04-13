package com.buildboard.modules.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.utils.FontHelper;
import com.buildboard.utils.SnackBarFactory;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.spinner_usertype)
    Spinner spinnerUserType;

    @BindView(R.id.edit_username)
    EditText editUserName;
    @BindView(R.id.edit_password)
    EditText editPassword;

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
        setSpinnerAdapter();
    }

    private void setSpinnerAdapter() {
        ArrayList<String> userTypeList = new ArrayList<>();
        userTypeList.add(stringContractor);
        userTypeList.add(stringConsumer);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spinner_usertype_item, userTypeList);
        spinnerUserType.setAdapter(arrayAdapter);
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
}

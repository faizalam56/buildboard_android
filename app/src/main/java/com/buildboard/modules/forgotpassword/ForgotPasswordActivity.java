package com.buildboard.modules.forgotpassword;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.fonts.FontHelper;
import com.buildboard.utils.StringUtils;
import com.buildboard.view.SnackBarFactory;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswordActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.text_reset_password_msg)
    TextView textResetPasswordMsg;
    @BindView(R.id.edit_email)
    EditText editEmail;
    @BindView(R.id.button_send_mail)
    Button buttonSendMail;
    @BindView(R.id.constraint_root)
    ConstraintLayout constraintRoot;

    @BindString(R.string.error_enter_email)
    String stringErrorEnterEmail;
    @BindString(R.string.error_invalid_email)
    String stringErrorInvalidEmail;
    @BindString(R.string.title_forgot_password)
    String stringFrogotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);

        toolbar.setTitle(stringFrogotPassword);
        setFont();
    }

    @OnClick(R.id.button_send_mail)
    void sendEmailTapped() {
        String email = editEmail.getText().toString();
        if (validateFields(email)) forgotPassword();
    }

    private boolean validateFields(String email) {
        if (TextUtils.isEmpty(email)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorEnterEmail);
            return false;
        } else if (!StringUtils.isValidEmailId(email)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorInvalidEmail);
            return false;
        }

        return true;
    }

    private void forgotPassword() {
        // TODO: 4/21/18
    }

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_LIGHT, textResetPasswordMsg, editEmail, buttonSendMail);
    }
}
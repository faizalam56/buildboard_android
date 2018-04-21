package com.buildboard.modules.forgotpassword;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
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

    @BindView(R.id.text_reset_password_msg)
    TextView textResetPasswordMsg;
    @BindView(R.id.edit_email)
    EditText editEmail;
    @BindView(R.id.button_send_mail)
    Button buttonSendMail;

    @BindString(R.string.error_enter_email)
    String stringEnterEmail;
    @BindString(R.string.error_invalid_email)
    String stringInvalidEmail;

    @BindView(R.id.constraint_root)
    ConstraintLayout constraintRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);

        setFont();
    }

    @OnClick(R.id.button_send_mail)
    void sendEmailTapped() {
        if (TextUtils.isEmpty(editEmail.getText()))
            SnackBarFactory.createSnackBar(this, constraintRoot, stringEnterEmail);
        else if (!StringUtils.isValidEmailId(editEmail.getText().toString()))
            SnackBarFactory.createSnackBar(this, constraintRoot, stringInvalidEmail);
    }

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, textResetPasswordMsg, editEmail, buttonSendMail);
    }
}

package com.buildboard.modules.login.forgotpassword;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.dialogs.PopUpHelper;
import com.buildboard.fonts.FontHelper;
import com.buildboard.http.DataManager;
import com.buildboard.modules.login.LoginActivity;
import com.buildboard.modules.login.forgotpassword.models.ForgotPasswordRequest;
import com.buildboard.modules.selection.ContractorTypeSelectionActivity;
import com.buildboard.modules.signup.SignUpActivity;
import com.buildboard.modules.signup.models.contractortype.ContractorTypeDetail;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.StringUtils;
import com.buildboard.utils.Utils;
import com.buildboard.view.SnackBarFactory;

import java.util.ArrayList;

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
        if (validateFields(email)) forgotPassword(email);
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

    private void forgotPassword(String email) {
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
        forgotPasswordRequest.setEmail(email);
        ProgressHelper.start(this, getString(R.string.msg_please_wait));
        DataManager.getInstance().forgotPassword(this, forgotPasswordRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                if (response == null) return;

                confirmPopup(response.toString());
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
                Utils.showError(ForgotPasswordActivity.this, constraintRoot, error);
            }
        });
    }

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_LIGHT, textResetPasswordMsg, editEmail, buttonSendMail);
    }

    private void confirmPopup(String message) {
        PopUpHelper.showInfoAlertPopup(this, message, new PopUpHelper.InfoPopupListener() {
            @Override
            public void onConfirm() {
                startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}
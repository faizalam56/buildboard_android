package com.buildboard.modules.login.forgotpassword;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.buildboard.R;
import com.buildboard.customviews.BuildBoardEditText;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.dialogs.PopUpHelper;
import com.buildboard.fonts.FontHelper;
import com.buildboard.http.DataManager;
import com.buildboard.modules.login.LoginActivity;
import com.buildboard.modules.login.forgotpassword.models.ForgotPasswordRequest;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.StringUtils;
import com.buildboard.utils.Utils;
import com.buildboard.view.SnackBarFactory;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.buildboard.utils.Utils.showProgressColor;

public class ForgotPasswordActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.text_reset_password_msg)
    BuildBoardTextView textResetPasswordMsg;
    @BindView(R.id.edit_email)
    BuildBoardEditText editEmail;
    @BindView(R.id.button_send_mail)
    Button buttonSendMail;
    @BindView(R.id.constraint_root)
    ConstraintLayout constraintRoot;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

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

        showProgressColor(this, progressBar);

        editEmail.setFocusableInTouchMode(true);
        editEmail.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        title.setText(stringFrogotPassword);
        setFont();
    }

    @OnClick(R.id.button_send_mail)
    void sendEmailTapped() {
        if(ConnectionDetector.isNetworkConnected(this)) {
            String email = editEmail.getText().toString();
            if (validateFields(email)) forgotPassword(email);
        } else {
            ConnectionDetector.createSnackBar(this,constraintRoot);
        }
    }

    public void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar(){
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
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
        showProgressBar();
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
        forgotPasswordRequest.setEmail(email);
        DataManager.getInstance().forgotPassword(this, forgotPasswordRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                hideProgressBar();
                if (response == null) return;

                confirmPopup(response.toString());
            }

            @Override
            public void onError(Object error) {
                hideProgressBar();
            }
        });
    }

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, textResetPasswordMsg, editEmail);
        FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, buttonSendMail);
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
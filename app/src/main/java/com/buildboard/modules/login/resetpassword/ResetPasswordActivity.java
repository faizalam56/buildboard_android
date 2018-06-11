package com.buildboard.modules.login.resetpassword;

import android.net.Uri;
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
import com.buildboard.http.DataManager;
import com.buildboard.http.ErrorManager;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.view.SnackBarFactory;

import java.util.Objects;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetPasswordActivity extends AppCompatActivity {

    private String schemaSpecificPart;
    private String apiKey;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.edit_enter_password)
    EditText editEnterPassword;
    @BindView(R.id.edit_confirm_password)
    EditText editConfirmPassword;

    @BindView(R.id.button_reset_password)
    Button buttonResetPassword;
    
    @BindView(R.id.constraint_root)
    ConstraintLayout constraintRoot;

    @BindString(R.string.reset_password) String stringResetPassword;
    @BindString(R.string.new_password) String stringNewPassword;
    @BindString(R.string.confirm_new_password) String stringConfirmNewPassword;
    @BindString(R.string.error_network) String stringErrorNetwork;
    @BindString(R.string.please_wait) String stringPleaseWait;
    @BindString(R.string.please_enter) String stringPleaseEnter;
    @BindString(R.string.confirm_password) String stringConfirmPassword;
    @BindString(R.string.password_did_not_match) String stringPasswordDidNotMatch;
    @BindString(R.string.password) String stringPassword;
    @BindString(R.string.eight_char) String stringEightChar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);

        title.setText(stringResetPassword);
        setFont();
    }

    @OnClick(R.id.button_reset_password)
    void resetPasswordTapped() {
        String password = editEnterPassword.getText().toString();
        String confirmPassword = editConfirmPassword.getText().toString();
        if (validate(password, confirmPassword)) {
             resetPassword(password);
        }
    }

    private boolean validate(String password, String confirmPassword) {

        if (TextUtils.isEmpty(password)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringPleaseEnter + " " + stringPassword);
            return false;
        } else if (password.length() < 8) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringPleaseEnter + " " + stringEightChar);
            return false;
        } else if (TextUtils.isEmpty(confirmPassword)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringPleaseEnter + " " + stringConfirmPassword);
            return false;
        } else if (!password.equalsIgnoreCase(confirmPassword)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringPasswordDidNotMatch);
            return false;
        }

        return true;
    }

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_LIGHT, editEnterPassword, editConfirmPassword, buttonResetPassword);
    }

    private void resetPassword(String password) {
        ProgressHelper.start(this, stringPleaseWait);

        DataManager.getInstance().resetPassword(this, password, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
                ErrorManager errorManager = new ErrorManager(ResetPasswordActivity.this, constraintRoot, error);
                errorManager.handleErrorResponse();
            }
        });
    }
}
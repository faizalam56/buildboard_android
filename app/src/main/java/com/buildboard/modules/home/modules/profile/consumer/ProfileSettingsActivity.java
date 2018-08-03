package com.buildboard.modules.home.modules.profile.consumer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.widget.TextView;
import android.widget.Toast;

import com.buildboard.R;
import com.buildboard.http.DataManager;
import com.buildboard.modules.login.LoginActivity;
import com.buildboard.preferences.AppPreference;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.Utils;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.buildboard.constants.AppConstant.IS_LOGIN;

public class ProfileSettingsActivity extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.text_edit_profile)
    TextView textEditProfile;
    @BindView(R.id.text_manage_payment)
    TextView textManagePayment;
    @BindView(R.id.text_payment)
    TextView textPayment;
    @BindView(R.id.text_privacy_policy)
    TextView textPrivacyPolicy;
    @BindView(R.id.text_terms_of_use)
    TextView textTermsOfUse;
    @BindView(R.id.text_faq)
    TextView textFaqs;
    @BindView(R.id.text_contact)
    TextView textContactUs;
    @BindView(R.id.text_logout)
    TextView textLogout;
    @BindView(R.id.card_logout)
    CardView logout;
    @BindView(R.id.constraint_root)
    ConstraintLayout constraintRoot;
    @BindString(R.string.settings)
    String stringSettings;
    @BindString(R.string.successfullyLogout)
    String stringLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        ButterKnife.bind(this);
        title.setText(stringSettings);
    }

    @OnClick(R.id.card_logout)
    public void CardLogout() {
        ProgressHelper.start(this, getString(R.string.msg_please_wait));
        DataManager.getInstance().logout(this, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                if (mGoogleSignInClient != null) {
                    Toast.makeText(ProfileSettingsActivity.this, stringLogout, Toast.LENGTH_SHORT).show();
                    AppPreference.getAppPreference(ProfileSettingsActivity.this).setBoolean(false, IS_LOGIN);
                    mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent googlePlusLogoutIntent = new Intent(ProfileSettingsActivity.this, LoginActivity.class);
                            startActivity(googlePlusLogoutIntent);
                            finish();
                        }
                    });
                } else {
                    Toast.makeText(ProfileSettingsActivity.this, stringLogout, Toast.LENGTH_SHORT).show();
                    AppPreference.getAppPreference(ProfileSettingsActivity.this).setBoolean(false, IS_LOGIN);
                    openActivity(LoginActivity.class, true);
                }
            }
            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
                Utils.showError(ProfileSettingsActivity.this, constraintRoot, error);
            }
        });
    }

    private void openActivity(Class classToReplace, boolean isClearStack) {
        Intent intent = new Intent(ProfileSettingsActivity.this, classToReplace);
        if (isClearStack) {
            Intent homeIntent = new Intent(ProfileSettingsActivity.this, classToReplace);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(homeIntent);
            finish();
        } else startActivity(intent);
    }

    @OnClick(R.id.text_edit_profile)
    public void moveToClass() {
        if (ConnectionDetector.isNetworkConnected(this)) {
            startActivity(new Intent(ProfileSettingsActivity.this, EditProfileActivity.class));
        } else {
            ConnectionDetector.createSnackBar(this,constraintRoot);
        }
    }
}
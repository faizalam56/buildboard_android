package com.buildboard.modules.home.modules.profile.consumer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.dialogs.PopUpHelper;
import com.buildboard.http.DataManager;
import com.buildboard.modules.home.modules.profile.contractor.EditContractorProfileActivity;
import com.buildboard.modules.login.LoginActivity;
import com.buildboard.preferences.AppPreference;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.Utils;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileSettingsActivity extends AppCompatActivity implements AppConstant {

    private GoogleSignInClient mGoogleSignInClient;
    private String mFacebookToken;

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
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindString(R.string.settings)
    String stringSettings;
    @BindString(R.string.successfullyLogout)
    String stringLogout;
    @BindString(R.string.msg_confirm_logout)
    String stringConfirmLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        ButterKnife.bind(this);
        getFacebookToken();
        title.setText(stringSettings);
    }

    private void getFacebookToken() {
        mFacebookToken = AppPreference.getAppPreference(ProfileSettingsActivity.this).getString(FACEBOOK_TOKEN);
    }

    @OnClick(R.id.card_logout)
    public void CardLogout() {
        ProgressHelper.showProgressBar(this, progressBar);
        DataManager.getInstance().logout(this, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                logoutUser();
            }

            @Override
            public void onError(Object error) {

            }
        });
    }

    @OnClick(R.id.text_edit_profile)
    public void moveToClass() {
        if (ConnectionDetector.isNetworkConnected(this))
            startActivity(new Intent(ProfileSettingsActivity.this, AppPreference.getAppPreference(this).getBoolean(IS_CONTRACTOR) ? EditContractorProfileActivity.class : EditProfileActivity.class));
        else ConnectionDetector.createSnackBar(this, constraintRoot);
    }

    private void logoutUser(){
        ProgressHelper.showProgressBar(this, progressBar);
        DataManager.getInstance().logout(ProfileSettingsActivity.this, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.hideProgressBar();
                if (mGoogleSignInClient != null) {
                    Toast.makeText(ProfileSettingsActivity.this, stringLogout, Toast.LENGTH_SHORT).show();
                    AppPreference.getAppPreference(ProfileSettingsActivity.this).setString("", SESSION_ID);
                    AppPreference.getAppPreference(ProfileSettingsActivity.this).setBoolean(false, IS_LOGIN);
                    mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent googlePlusLogoutIntent = new Intent(ProfileSettingsActivity.this, LoginActivity.class);
                            startActivity(googlePlusLogoutIntent);
                            finish();
                        }
                    });
                } else if (mFacebookToken != null) {
                    LoginManager.getInstance().logOut();
                    Toast.makeText(ProfileSettingsActivity.this, stringLogout, Toast.LENGTH_SHORT).show();
                    AppPreference.getAppPreference(ProfileSettingsActivity.this).setString("", SESSION_ID);
                    AppPreference.getAppPreference(ProfileSettingsActivity.this).setBoolean(false, IS_LOGIN);
                    openActivity(LoginActivity.class, true);
                } else {
                    Toast.makeText(ProfileSettingsActivity.this, stringLogout, Toast.LENGTH_SHORT).show();
                    AppPreference.getAppPreference(ProfileSettingsActivity.this).setString("", SESSION_ID);
                    AppPreference.getAppPreference(ProfileSettingsActivity.this).setBoolean(false, IS_LOGIN);
                    ProfileFragment.newInstance().profileData = null;
                    openActivity(LoginActivity.class, true);
                }
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.hideProgressBar();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.card_privacy_policy)
    public void privacyPolicyTapped(){
        openLink(PRIVACY_POLICY_LINK);
    }

    @OnClick(R.id.card_change_password)
    public void changePasswordTapped(){ }

    @OnClick(R.id.card_term_of_use)
    public void termsOfUseTapped(){
        openLink(TERMS_OF_SERVICES_LINK);
    }

    @OnClick(R.id.card_faq)
    public void faqTapped(){
        openLink(FAQ_LINK);
    }

    @OnClick(R.id.card_contact)
    public void contactUsTapped(){
        openLink(CONTACT_US);
    }

    private void openLink(String link) {
        Uri uri = Uri.parse(link);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}
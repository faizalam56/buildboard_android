package com.buildboard.modules.login;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.customviews.BuildBoardButton;
import com.buildboard.customviews.BuildBoardEditText;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.dialogs.PopUpHelper;
import com.buildboard.dialogs.UserTypeDialog;
import com.buildboard.http.DataManager;
import com.buildboard.http.ErrorManager;
import com.buildboard.models.ErrorResponse;
import com.buildboard.modules.home.HomeActivity;
import com.buildboard.modules.home.modules.profile.consumer.ProfileSettingsActivity;
import com.buildboard.modules.login.forgotpassword.ForgotPasswordActivity;
import com.buildboard.modules.login.models.getAccessToken.GetAccessTokenRequest;
import com.buildboard.modules.login.models.getAccessToken.TokenData;
import com.buildboard.modules.login.models.login.LoginData;
import com.buildboard.modules.login.models.login.LoginRequest;
import com.buildboard.modules.login.models.sociallogin.SocialLoginRequest;
import com.buildboard.modules.login.models.sociallogin.SocialLoginResponse;
import com.buildboard.modules.signup.SignUpActivity;
import com.buildboard.modules.signup.contractor.businessinfo.SignUpContractorActivity;
import com.buildboard.modules.signup.models.activateuser.ActivateUserResponse;
import com.buildboard.preferences.AppPreference;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.Utils;
import com.buildboard.utils.Validator;
import com.buildboard.view.SnackBarFactory;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.buildboard.utils.Utils.showProgressColor;

public class LoginActivity extends AppCompatActivity implements AppConstant, GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 9001;
    private CallbackManager mCallbackManager;
    private GoogleSignInClient mGoogleSignInClient;
    private String apiKey;
    private String schemaSpecificPart;

    @BindView(R.id.edit_useremail)
    BuildBoardEditText editUserEmail;
    @BindView(R.id.edit_password)
    BuildBoardEditText editPassword;
    @BindView(R.id.text_forgot_password)
    BuildBoardTextView textForgotPassword;
    @BindView(R.id.text_sign_up)
    BuildBoardTextView textSignUp;
    @BindView(R.id.button_signin)
    BuildBoardButton buttonSignIn;
    @BindView(R.id.button_login_facebook)
    BuildBoardButton buttonLoginFacebook;
    @BindView(R.id.button_login_google)
    BuildBoardButton buttonLoginGoogle;
    @BindView(R.id.constraint_root)
    ConstraintLayout constraintRoot;
    @BindView(R.id.sign_in_button)
    SignInButton signInButton;
    @BindView(R.id.login_button)
    LoginButton loginButton;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindString(R.string.contractor)
    String stringContractor;
    @BindString(R.string.consumer)
    String stringConsumer;
    @BindString(R.string.error_password)
    String stringErrorPasswordEmptyMsg;
    @BindString(R.string.error_username)
    String stringErrorUsernameEmptyMsg;
    @BindString(R.string.user_type)
    String stringUserType;
    @BindString(R.string.error_incorrect_password_length)
    String stringErrorIncorrectPassword;
    @BindString(R.string.error_select_user_type)
    String stringErrorSelectUserType;
    @BindString(R.string.error_enter_valid_email)
    String stringErrorInvalidEmail;
    @BindString(R.string.msg_please_wait)
    String stringPleaseWait;
    @BindArray(R.array.user_type_array)
    String[] arrayUserType;
    @BindArray(R.array.array_user_type)
    String[] arrayUsertype;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        showProgressColor(this, progressBar);

        Uri uri = getIntent().getData();
        if (uri != null && uri.getSchemeSpecificPart() != null) {
            schemaSpecificPart = uri.getSchemeSpecificPart();
            apiKey = splitApiKey();
        }

        if (!TextUtils.isEmpty(apiKey))
            verifyUser(apiKey);

        textSignUp.setPaintFlags(textSignUp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        getAccessToken();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mCallbackManager = CallbackManager.Factory.create();
    }
    private String splitApiKey() {
        Uri uri = getIntent().getData();
        String apiKey = null;
        assert uri != null;
        if (uri.getSchemeSpecificPart() != null) {
            schemaSpecificPart = uri.getSchemeSpecificPart();
            apiKey = schemaSpecificPart.substring(schemaSpecificPart.lastIndexOf("/") + 1);
        }

        return apiKey;
    }

    private void verifyUser(String apiKey) {
        ProgressHelper.showProgressBar(this, progressBar);

        DataManager.getInstance().activateUser(this, apiKey, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.hideProgressBar();
                ActivateUserResponse activateUserResponse = (ActivateUserResponse) response;
                PopUpHelper.showInfoAlertPopup(LoginActivity.this, activateUserResponse.getDatas().get(0), new PopUpHelper.InfoPopupListener() {
                    @Override
                    public void onConfirm() {
                    }
                });
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.hideProgressBar();
                Utils.showError(LoginActivity.this, constraintRoot, error);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) return;

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RC_SIGN_IN:
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    handleGoogleSignInResult(task);
                    break;
                default:
                    mCallbackManager.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @OnClick(R.id.text_sign_up)
    void signUpTapped() {
        UserTypeDialog alert = new UserTypeDialog();
        alert.showDialog(this, new UserTypeDialog.IUserTypeCallback() {
            @Override
            public void onConsumerSelection() {
                openActivity(SignUpActivity.class, false, false, null, null);
            }

            @Override
            public void onContractorSelection() {
                openActivity(SignUpContractorActivity.class, false, false, null, null);
            }
        });
    }

    @OnClick(R.id.button_signin)
    void signInTapped() {
        if(ConnectionDetector.isNetworkConnected(this)) {
            String userEmail = editUserEmail.getText().toString();
            String password = editPassword.getText().toString();

            if (validateFields(userEmail, password)) {
                login(userEmail, password);
            }
        } else {
            ConnectionDetector.createSnackBar(this, constraintRoot);
        }
    }

    @OnClick(R.id.text_forgot_password)
    void forgotPasswordTapped() {
        openActivity(ForgotPasswordActivity.class, false, false, null, null);
    }

    @OnClick({R.id.button_login_facebook, R.id.login_button})
    void userFacebookLoginTapped() {
        if(ConnectionDetector.isNetworkConnected(this)) {
            loginButton.performClick();
            signInFaceBook();
        } else {
            ConnectionDetector.createSnackBar(this, constraintRoot);
        }
    }

    @OnClick({R.id.button_login_google, R.id.sign_in_button})
    void userGoogleLoginTapped() {
        if(ConnectionDetector.isNetworkConnected(this)) {
            signInButton.performClick();
            signInGoogle();
        } else {
            ConnectionDetector.createSnackBar(this, constraintRoot);
        }
    }

    private boolean validateFields(String userEmail, String password) {
        if (TextUtils.isEmpty(userEmail)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorUsernameEmptyMsg);
            return false;
        } else if (!Validator.validateEmail(userEmail)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorInvalidEmail);
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorPasswordEmptyMsg);
            return false;
        } else if (password.length() < 6) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorIncorrectPassword);
            return false;
        }

        return true;
    }

    private void openActivity(Class classToReplace, boolean isClearStack, boolean isSocialLogin, SocialLoginRequest socialLoginRequest, String email) {
        Intent intent = new Intent(LoginActivity.this, classToReplace);
        if (isClearStack) {
            Intent homeIntent = new Intent(LoginActivity.this, classToReplace);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(homeIntent);
            finish();
        } else if (isSocialLogin) {
            intent.putExtra(INTENT_PROVIDER, socialLoginRequest.getProvider());
            intent.putExtra(INTENT_PROVIDER_ID, socialLoginRequest.getProviderId());
            intent.putExtra(INTENT_EMAIL, email);
            intent.putExtra(INTENT_FIRST_NAME, socialLoginRequest.getFirstName());
            intent.putExtra(INTENT_LAST_NAME, socialLoginRequest.getLastName());
            startActivity(intent);
            finish();
        } else startActivity(intent);
    }

    public void signInFaceBook() {
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                requestFBUserProfile(loginResult);
            }

            @Override
            public void onCancel() {}

            @Override
            public void onError(FacebookException exception) {
                exception.printStackTrace();
            }
        });
    }

    private void requestFBUserProfile(final LoginResult loginResult) {

        final String userId = loginResult.getAccessToken().getUserId();

        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                AppPreference.getAppPreference(LoginActivity.this).setString(String.valueOf(loginResult.getAccessToken()), FACEBOOK_TOKEN);
                SocialLoginRequest socialLoginRequest = new SocialLoginRequest();
                socialLoginRequest.setProvider(getString(R.string.facebook));
                socialLoginRequest.setProviderId(userId);
                String email = null;
                try {
                    email = object.getString("email");
                    socialLoginRequest.setFirstName(object.getString("first_name"));
                    socialLoginRequest.setLastName(object.getString("last_name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getSocialLogin(socialLoginRequest, email);
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location, link");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void signInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);
        } catch (ApiException e) {
            updateUI(null);
            e.printStackTrace();
        }
    }

    private void getSocialLogin(final SocialLoginRequest socialLoginRequest, final String email) {
        if (socialLoginRequest == null)
            return;

        ProgressHelper.showProgressBar(this, progressBar);
        DataManager.getInstance().getSocialLogin(this, socialLoginRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.hideProgressBar();
                if (response == null) return;
                SocialLoginResponse socialLoginResponse = (SocialLoginResponse) response;
                LoginData loginData = socialLoginResponse.getDatas().get(0);
                if (loginData.getRole().equalsIgnoreCase(stringContractor)) {
                    AppPreference.getAppPreference(LoginActivity.this).setBoolean(true, IS_CONTRACTOR);
                } else {
                    AppPreference.getAppPreference(LoginActivity.this).setBoolean(false, IS_CONTRACTOR);
                }
                AppPreference.getAppPreference(LoginActivity.this).setBoolean(true, IS_LOGIN);
                AppPreference.getAppPreference(LoginActivity.this).setString(loginData.getSessionId(), SESSION_ID);
                openActivity(HomeActivity.class, false, true, socialLoginRequest, email);
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.hideProgressBar();
                ErrorResponse errorResponse = (ErrorResponse) error;
                if(errorResponse.getCode().equals("200")) {
                    Toast.makeText(LoginActivity.this, getString(R.string.user_not_signed_alert_msg), Toast.LENGTH_LONG).show();
                    redirectToSignUp(socialLoginRequest, email);
                } else {
                    SnackBarFactory.createSnackBar(LoginActivity.this, constraintRoot, String.valueOf(errorResponse.getMessage()));
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //TODO: 3/05/18
    }

    private void getAccessToken() {
        ProgressHelper.showProgressBar(this, progressBar);
        DataManager.getInstance().getAccessToken(new GetAccessTokenRequest(), new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.hideProgressBar();
                if (response == null) return;

                TokenData tokenData = (TokenData) response;
                if (tokenData.getAccessToken() != null)
                    AppPreference.getAppPreference(LoginActivity.this).setString(tokenData.getAccessToken(), ACCESS_TOKEN);
            }

            @Override
            public void onError(Object response) {
                ProgressHelper.hideProgressBar();
                ErrorResponse errorResponse = (ErrorResponse) response;
                SnackBarFactory.createSnackBar(LoginActivity.this, constraintRoot, String.valueOf(errorResponse.getMessage()));
            }
        });
    }

    private void login(String username, String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(username);
        loginRequest.setPassword(password);

        ProgressHelper.showProgressBar(this, progressBar);
        DataManager.getInstance().login(this, loginRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
               ProgressHelper.hideProgressBar();
                if (response == null) return;

                LoginData loginData = (LoginData) response;
                AppPreference.getAppPreference(LoginActivity.this).setBoolean(true, IS_LOGIN);

                if (loginData.getRole().equalsIgnoreCase(stringContractor)) {
                    AppPreference.getAppPreference(LoginActivity.this).setBoolean(true, IS_CONTRACTOR);
                } else {
                    AppPreference.getAppPreference(LoginActivity.this).setBoolean(false, IS_CONTRACTOR);
                }
                AppPreference.getAppPreference(LoginActivity.this).setString(loginData.getSessionId(), SESSION_ID);
                AppPreference.getAppPreference(LoginActivity.this).setString(loginData.getUserId(), USER_ID);
                openActivity(HomeActivity.class, true, false, null, null);
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.hideProgressBar();
                Utils.showError(LoginActivity.this, constraintRoot, error);
            }
        });
    }

    private void redirectToSignUp(SocialLoginRequest socialLoginRequest, String email) {
        showUserTypePopup(true, socialLoginRequest, email);
    }

    private void showUserTypePopup(final boolean isSocialLogin, final SocialLoginRequest socialLoginRequest, final String email) {
        UserTypeDialog alert = new UserTypeDialog();
        alert.showDialog(this, new UserTypeDialog.IUserTypeCallback() {
            @Override
            public void onConsumerSelection() {
                openActivity(SignUpActivity.class, false, isSocialLogin, socialLoginRequest, email);
            }

            @Override
            public void onContractorSelection() {
                openActivity(SignUpContractorActivity.class, false, isSocialLogin, socialLoginRequest, email);
            }
        });
    }

    public void updateUI(@Nullable GoogleSignInAccount account) {
        if (account != null) {
            SocialLoginRequest socialLoginRequest = new SocialLoginRequest();
            socialLoginRequest.setProvider(getString(R.string.google));
            socialLoginRequest.setProviderId(account.getId());
            socialLoginRequest.setFirstName(account.getGivenName());
            socialLoginRequest.setLastName(account.getFamilyName());
            String email = account.getEmail();
            getSocialLogin(socialLoginRequest, email);
        }
    }
}

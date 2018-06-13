package com.buildboard.modules.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.customviews.BuildBoardButton;
import com.buildboard.customviews.BuildBoardEditText;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.http.DataManager;
import com.buildboard.modules.home.HomeActivity;
import com.buildboard.modules.login.forgotpassword.ForgotPasswordActivity;
import com.buildboard.modules.login.models.getAccessToken.GetAccessTokenRequest;
import com.buildboard.modules.login.models.getAccessToken.TokenData;
import com.buildboard.modules.login.models.login.LoginData;
import com.buildboard.modules.login.models.login.LoginRequest;
import com.buildboard.modules.login.models.sociallogin.SocialLoginRequest;
import com.buildboard.modules.login.models.sociallogin.SocialLoginResponse;
import com.buildboard.modules.signup.SignUpActivity;
import com.buildboard.preferences.AppPreference;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.Utils;
import com.buildboard.utils.Validator;
import com.buildboard.view.SnackBarFactory;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements AppConstant, GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 9001;
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
    @BindArray(R.array.user_type_array)
    String[] arrayUserType;
    @BindView(R.id.constraint_root)
    ConstraintLayout constraintRoot;
    private CallbackManager mCallbackManager;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getAccessToken();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) return;

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RC_SIGN_IN:
                    handleGoogleSignInResult(data);
                    break;

                default:
                    mCallbackManager.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @OnClick(R.id.text_sign_up)
    void signUpTapped() {
        openActivity(SignUpActivity.class, false);
    }

    @OnClick(R.id.button_signin)
    void signInTapped() {
        String userEmail = editUserEmail.getText().toString();
        String password = editPassword.getText().toString();

        if (validateFields(userEmail, password)) {
            login(userEmail, password);
        }
    }

    @OnClick(R.id.text_forgot_password)
    void forgotPasswordTapped() {
        openActivity(ForgotPasswordActivity.class, false);
    }

    @OnClick(R.id.button_login_facebook)
    void userFacebookLoginTapped() {
        signInFaceBook();
    }

    @OnClick(R.id.button_login_google)
    void userGoogleLoginTapped() {
        signInGoogle();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.stopAutoManage(LoginActivity.this);
            mGoogleApiClient.disconnect();
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
        } else if (password.length() < 8) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorIncorrectPassword);
            return false;
        }

        return true;
    }

    private void openActivity(Class classToReplace, boolean isClearStack) {
        Intent intent = new Intent(LoginActivity.this, classToReplace);
        if (isClearStack) {
            Intent homeIntent = new Intent(LoginActivity.this, classToReplace);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(homeIntent);
            finish();
        } else startActivity(intent);
    }

    public void signInFaceBook() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "user_photos", "public_profile"));
        mCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("Success", loginResult + "");
                requestFBUserProfile(loginResult);
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException exception) {
            }
        });
    }

    private void requestFBUserProfile(LoginResult loginResult) {
        String accessToken = loginResult.getAccessToken().getToken();

        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                openActivity(HomeActivity.class, true);
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location, link");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void signInGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this, this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleGoogleSignInResult(Intent data) {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        if (result != null && result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();

            SocialLoginRequest socialLoginRequest = new SocialLoginRequest();
            socialLoginRequest.setProvider("google"); // TODO remove hardcoded string
            socialLoginRequest.setProviderId(account.getId());

            getSocialLogin(socialLoginRequest);
        }
    }

    private void getSocialLogin(final SocialLoginRequest socialLoginRequest) {
        if (socialLoginRequest == null)
            return;

        ProgressHelper.start(this, getString(R.string.msg_please_wait));
        DataManager.getInstance().getSocialLogin(this, socialLoginRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
                Toast.makeText(LoginActivity.this, "You haven't signed up yet, please signup", Toast.LENGTH_LONG).show(); //TODO remove hardcoded data
                redirectToSignUp(socialLoginRequest);
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //TODO: 3/05/18
    }

    private void getAccessToken() {
        DataManager.getInstance().getAccessToken(new GetAccessTokenRequest(), new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                if (response == null) return;

                TokenData tokenData = (TokenData) response;
                if (tokenData.getAccessToken() != null)
                    AppPreference.getAppPreference(LoginActivity.this).setString(tokenData.getAccessToken(), ACCESS_TOKEN);
            }

            @Override
            public void onError(Object response) {
                Utils.showError(LoginActivity.this, constraintRoot, response);
            }
        });
    }

    private void login(String username, String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(username);
        loginRequest.setPassword(password);

        ProgressHelper.start(this, getString(R.string.msg_please_wait));
        DataManager.getInstance().login(this, loginRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                if (response == null) return;

                LoginData loginData = (LoginData) response;
                AppPreference.getAppPreference(LoginActivity.this).setBoolean(true, IS_LOGIN);

                if (loginData.getRole().equalsIgnoreCase(stringContractor)) {
                    AppPreference.getAppPreference(LoginActivity.this).setBoolean(true, IS_CONTRACTOR);
                } else {
                    AppPreference.getAppPreference(LoginActivity.this).setBoolean(false, IS_CONTRACTOR);
                }
                AppPreference.getAppPreference(LoginActivity.this).setString(loginData.getSessionId(), SESSION_ID);
                openActivity(HomeActivity.class, true);
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
                Utils.showError(LoginActivity.this, constraintRoot, error);
            }
        });
    }

    private void redirectToSignUp(SocialLoginRequest socialLoginRequest) {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        intent.putExtra(INTENT_PROVIDER, socialLoginRequest.getProvider());
        intent.putExtra(INTENT_PROVIDER_ID, socialLoginRequest.getProviderId());
        startActivity(intent);
    }
}

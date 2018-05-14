package com.buildboard.modules.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.fonts.FontHelper;
import com.buildboard.http.ApiClient;
import com.buildboard.http.ErrorManager;
import com.buildboard.modules.forgotpassword.ForgotPasswordActivity;
import com.buildboard.modules.home.HomeActivity;
import com.buildboard.modules.login.models.getAccessToken.GetAccessTokenRequest;
import com.buildboard.modules.login.models.getAccessToken.GetAccessTokenResponse;
import com.buildboard.modules.login.models.getAccessToken.TokenData;
import com.buildboard.modules.login.models.login.LoginData;
import com.buildboard.modules.login.models.login.LoginRequest;
import com.buildboard.modules.login.models.login.LoginResponse;
import com.buildboard.modules.selection.SelectionActivity;
import com.buildboard.modules.signup.SignUpActivity;
import com.buildboard.preferences.AppPreference;
import com.buildboard.utils.ProgressHelper;
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

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements AppConstant, GoogleApiClient.OnConnectionFailedListener {

    private CallbackManager mCallbackManager;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;

    @BindView(R.id.edit_username)
    EditText editUserName;
    @BindView(R.id.edit_password)
    EditText editPassword;

    @BindView(R.id.text_user_type)
    TextView textUserType;
    @BindView(R.id.text_forgot_password)
    TextView textForgotPassword;
    @BindView(R.id.text_sign_up)
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
    @BindString(R.string.error_username_short)
    String stringErrorUsernameTooShort;

    @BindArray(R.array.user_type_array)
    String[] arrayUserType;

    @BindView(R.id.constraint_root)
    ConstraintLayout constraintRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setFont();
        getAccessToken();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) return;

        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case USER_TYPE_REQUEST_CODE:
                    textUserType.setText(data.getStringExtra(INTENT_SELECTED_ITEM));
                    break;

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
        openActivity(SignUpActivity.class, false, false);
    }

    @OnClick(R.id.text_user_type)
    void userTypeTapped() {
        openActivity(SelectionActivity.class, true, false);
    }

    @OnClick(R.id.button_signin)
    void signInTapped() {
        String userName = editUserName.getText().toString();
        String password = editPassword.getText().toString();
        String userType = textUserType.getText().toString();

        if (validateFields(userName, password, userType)) {
            // TODO: 4/21/18
            loginApi();
        }
    }

    @OnClick(R.id.text_forgot_password)
    void forgotPasswordTapped() {
        openActivity(ForgotPasswordActivity.class, false, false);
    }

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_LIGHT, editPassword, editUserName, textForgotPassword, textSignUp,
                buttonLoginFacebook, buttonLoginGoogle, buttonSignIn, textUserType);
        FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, textForgotPassword);
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

    private boolean validateFields(String userName, String password, String userType) {
        if (userType.equals(stringUserType)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorSelectUserType);
            return false;
        }
        if (TextUtils.isEmpty(userName)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorUsernameEmptyMsg);
            return false;
        } else if (userName.length() < 3) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorUsernameTooShort);
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

    private void openActivity(Class classToReplace, boolean isStartForResult, boolean isClearStack) {
        Intent intent = new Intent(LoginActivity.this, classToReplace);
        if (isStartForResult) {
            intent.putExtra(DATA, new ArrayList<>(Arrays.asList(arrayUserType)));
            intent.putExtra(INTENT_TITLE, stringUserType);
            startActivityForResult(intent, USER_TYPE_REQUEST_CODE);
        } else if (isClearStack) {
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
                openActivity(HomeActivity.class, false, true);
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
            openActivity(HomeActivity.class, false, true);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //TODO: 3/05/18
    }

    private void getAccessToken() {

        ApiClient.getInstance().getAccessToken(new GetAccessTokenRequest(), new ApiClient.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                if (response == null) return;

                TokenData tokenData = (TokenData) response;
                if (tokenData.getAccessToken() != null)
                    AppPreference.getAppPreference(LoginActivity.this).setString(tokenData.getAccessToken(), ACCESS_TOKEN);
            }

            @Override
            public void onError(Object error) {
                ErrorManager errorManager = new ErrorManager(LoginActivity.this, constraintRoot, error);
                errorManager.handleErrorResponse();
            }
        });
    }

    private void loginApi() {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(editUserName.getText().toString());
        loginRequest.setPassword(editPassword.getText().toString());

        ProgressHelper.start(this, "Please wait...");
        ApiClient.getInstance().login(this, loginRequest, new ApiClient.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                if (response == null) return;

                LoginData loginData = (LoginData) response;
                openActivity(HomeActivity.class, false, true);
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
                ErrorManager errorManager = new ErrorManager(LoginActivity.this, constraintRoot, error);
                errorManager.handleErrorResponse();
            }
        });
    }
}

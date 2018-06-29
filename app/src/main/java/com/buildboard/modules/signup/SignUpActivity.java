package com.buildboard.modules.signup;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.customviews.BuildBoardButton;
import com.buildboard.customviews.BuildBoardEditText;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.http.DataManager;
import com.buildboard.http.ErrorManager;
import com.buildboard.modules.login.LoginActivity;
import com.buildboard.modules.selection.ContractorTypeSelectionActivity;
import com.buildboard.modules.selection.SelectionActivity;
import com.buildboard.modules.signup.imageupload.ImageUploadActivity;
import com.buildboard.modules.signup.models.activateuser.ActivateUserResponse;
import com.buildboard.modules.signup.models.contractortype.ContractorTypeDetail;
import com.buildboard.modules.signup.models.createconsumer.CreateConsumerData;
import com.buildboard.modules.signup.models.createconsumer.CreateConsumerRequest;
import com.buildboard.modules.signup.models.createcontractor.CreateContractorRequest;
import com.buildboard.permissions.PermissionHelper;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.StringUtils;
import com.buildboard.utils.Utils;
import com.buildboard.view.SnackBarFactory;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity implements AppConstant {

    private final String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private final int REQUEST_PERMISSION_CODE = 300;
    private ContractorTypeDetail contractorTypeDetail;
    private String apiKey;
    private String schemaSpecificPart;
    private LatLng addressLatLng;
    private String provider;
    private String providerId;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.text_terms_of_service)
    BuildBoardTextView textTermsOfService;
    @BindView(R.id.edit_first_name)
    BuildBoardEditText editFirstName;
    @BindView(R.id.edit_last_name)
    BuildBoardEditText editLastName;
    @BindView(R.id.edit_address)
    BuildBoardTextView editAddress;
    @BindView(R.id.edit_phoneno)
    BuildBoardEditText editPhoneNo;
    @BindView(R.id.edit_contact_mode)
    BuildBoardEditText editContactMode;
    @BindView(R.id.edit_email)
    BuildBoardEditText editEmail;
    @BindView(R.id.edit_password)
    BuildBoardEditText editPassword;
    @BindView(R.id.button_next)
    BuildBoardButton buttonNext;
    @BindView(R.id.constraint_consumer_address_container)
    ConstraintLayout constraintConsumerAddressContainer;
    @BindView(R.id.constraint_root)
    ConstraintLayout constraintRoot;
    @BindString(R.string.gender)
    String stringGender;
    @BindString(R.string.female)
    String stringFemale;
    @BindString(R.string.male)
    String stringMale;
    @BindString(R.string.other)
    String stringOther;
    @BindString(R.string.preferred_contact_mode)
    String stringPreferredContactMode;
    @BindString(R.string.type_of_contractor)
    String stringContractorType;
    @BindString(R.string.working_area)
    String stringWorkingArea;
    @BindString(R.string.user_type)
    String stringUserType;
    @BindString(R.string.contractor)
    String stringContractor;
    @BindString(R.string.consumer)
    String stringConsumer;
    @BindString(R.string.error_enter_first_name)
    String stringErrorFirstName;
    @BindString(R.string.error_enter_last_name)
    String stringErrorLastName;
    @BindString(R.string.error_invalid_email)
    String stringErrorInvalidEmail;
    @BindString(R.string.error_enter_email)
    String stringErrorEmail;
    @BindString(R.string.error_password)
    String stringErrorPasswordEmptyMsg;
    @BindString(R.string.error_enter_business_name)
    String stringErrorBusinessName;
    @BindString(R.string.error_enter_business_address)
    String stringErrorBusinessAddress;
    @BindString(R.string.error_enter_summary)
    String stringSummary;
    @BindString(R.string.error_enter_address)
    String stringErrorAddress;
    @BindString(R.string.error_enter_valid_phone_number)
    String stringErrorValidPhoneNumber;
    @BindString(R.string.error_enter_phone_number)
    String stringErrorPhoneNumber;
    @BindString(R.string.error_select_user_type)
    String stringErrorSelectUserType;
    @BindString(R.string.error_first_name_short)
    String stringErrorFirstnameTooShort;
    @BindString(R.string.error_incorrect_password_length)
    String stringErrorPasswordLength;
    @BindString(R.string.error_user_type)
    String stringErrorUserType;
    @BindString(R.string.error_working_area)
    String stringErrorWorkingArea;
    @BindString(R.string.error_summary)
    String stringErrorSummary;
    @BindString(R.string.sign_up)
    String stringSignUp;
    @BindString(R.string.error_contractor_type)
    String stringErrorContractorType;
    @BindString(R.string.phone)
    String stringPhone;
    @BindString(R.string.email)
    String stringEmail;
    @BindString(R.string.terms_of_service)
    String stringTermsOfService;
    @BindString(R.string.privacy_policy_text)
    String stringPrivacyPolicy;
    @BindArray(R.array.user_type_array)
    String[] arrayUserType;
    @BindString(R.string.please_enter_a_valid_address)
    String stringEnterValidAddress;
    @BindString(R.string.please_wait)
    String stringPleaseWait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        title.setText(stringSignUp);
        setTermsServiceText();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionHelper permission = new PermissionHelper(this);
            if (!permission.checkPermission(permissions))
                requestPermissions(permissions, REQUEST_PERMISSION_CODE);
        }

        getIntentData();

        Uri uri = getIntent().getData();
        if (uri != null && uri.getSchemeSpecificPart() != null) {
            schemaSpecificPart = uri.getSchemeSpecificPart();
            apiKey = splitApiKey();
        }

        if (!TextUtils.isEmpty(apiKey))
            verifyUser(apiKey);
    }

    @OnClick(R.id.edit_contact_mode)
    void contactModeTapped() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(stringPhone);
        arrayList.add(stringEmail);
        openActivity(SelectionActivity.class, arrayList, CONTACT_MODE_REQUEST_CODE, stringPreferredContactMode);
    }

    @OnClick(R.id.edit_address)
    void consumerAddressTapped() {
        try {
            PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
            Intent intent = intentBuilder.build(this);
            startActivityForResult(intent, PLACE_PICKER_REQUEST);

        } catch (GooglePlayServicesRepairableException
                | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.button_next)
    void nextButtonTapped() {

        String firstName = editFirstName.getText().toString();
        String lastName = editLastName.getText().toString();
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();
        String address = editAddress.getText().toString();
        String phoneNo = editPhoneNo.getText().toString();
        String contactMode = editContactMode.getText().toString();
        if (validateFields(firstName, lastName, email, password, address, phoneNo, contactMode)) {
            Intent intent = new Intent(this, ImageUploadActivity.class);
            startActivityForResult(intent, IMAGE_UPLOAD_REQUEST_CODE);
        }
    }

    private void signUpMethod(String firstName, String lastName, String email, String password, String address, String phoneNo, String contactMode, String imageUrl) {
        createConsumer(firstName, lastName, email, password, address, phoneNo, contactMode, imageUrl);
    }

    private void createConsumer(String firstName, String lastName, String email, String password, String address, String phoneNo, String contactMode, String imageUrl) {
        CreateConsumerRequest consumerRequest = getConsumerDetails(firstName, lastName, email, password, address, phoneNo, contactMode, imageUrl);
        if (consumerRequest.getLatitude() == null || consumerRequest.getLongitude() == null) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringEnterValidAddress);
            return;
        }

        ProgressHelper.start(this, getString(R.string.msg_please_wait));
        DataManager.getInstance().createConsumer(SignUpActivity.this, consumerRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                if (response == null) return;

                CreateConsumerData createConsumerData = (CreateConsumerData) response;
                Toast.makeText(SignUpActivity.this, createConsumerData.getMessage(), Toast.LENGTH_LONG).show();
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                finish();
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
                Utils.showError(SignUpActivity.this, constraintRoot, error);
            }
        });
    }

    private CreateConsumerRequest getConsumerDetails(String firstName, String lastName, String email, String password, String address, String phoneNo, String contactMode, String imageUrl) {
        CreateConsumerRequest consumerRequest = new CreateConsumerRequest();
        consumerRequest.setFirstName(firstName);
        consumerRequest.setLastName(lastName);
        consumerRequest.setEmail(email);
        consumerRequest.setPassword(password);
        consumerRequest.setAddress(address);
        consumerRequest.setPhoneNo(phoneNo);
        consumerRequest.setContactMode(contactMode);
        if (!TextUtils.isEmpty(imageUrl))
            consumerRequest.setImage(imageUrl);

        if (addressLatLng != null) {
            consumerRequest.setLatitude(String.valueOf(addressLatLng.latitude));
            consumerRequest.setLongitude(String.valueOf(addressLatLng.longitude));
        }

        if (!TextUtils.isEmpty(provider) && !TextUtils.isEmpty(providerId)) {
            consumerRequest.setProvider(provider);
            consumerRequest.setProviderId(providerId);
        }

        return consumerRequest;
    }

    private CreateContractorRequest getContractorDetails(String userType, String firstName, String lastName, String email, String password, String address, String phoneNo,
                                                         String contactMode, String businessName, String businessAddress, String workingArea,
                                                         String summary) {

        CreateContractorRequest createContractorRequest = new CreateContractorRequest();
        createContractorRequest.setFirstName(firstName);
        if (!TextUtils.isEmpty(lastName)) createContractorRequest.setLastName(lastName);
        createContractorRequest.setEmail(email);
        createContractorRequest.setPassword(password);
        if (!TextUtils.isEmpty(phoneNo)) createContractorRequest.setPhoneNo(phoneNo);
        if (contractorTypeDetail != null && contractorTypeDetail.getIdentifier() != null)
            createContractorRequest.setTypeOfContractorId(contractorTypeDetail.getIdentifier());
        createContractorRequest.setBusinessName(businessName);
        createContractorRequest.setBusinessAddress(businessAddress);
        if (!TextUtils.isEmpty(summary)) createContractorRequest.setSummary(summary);
        createContractorRequest.setWorkingAreaRadius(workingArea);

        return createContractorRequest;
    }

    private void setTermsServiceText() {
        SpannableString styledString = new SpannableString(getString(R.string.privacy_policy_text));
        styledString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorGreen)), 34, 50, 0);
        styledString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorGreen)), 55, 69, 0);
        styledString.setSpan(clickableSpanTermsService, 34, 50, 0);
        styledString.setSpan(clickableSpanPrivacyPolicy, 55, 69, 0);
        textTermsOfService.setText(styledString);
        textTermsOfService.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void openActivity(Class classToReplace, ArrayList<String> arrayList, int requestCode, String title) {
        Intent intent = new Intent(SignUpActivity.this, classToReplace);
        intent.putExtra(DATA, arrayList);
        intent.putExtra(INTENT_TITLE, title);
        startActivityForResult(intent, requestCode);
    }

    private boolean validateFields(String firstName, String lastName, String email, String password, String address, String phoneNo, String contactMode) {

        if (TextUtils.isEmpty(firstName)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorFirstName);
            return false;
        } else if (firstName.length() < 3) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorFirstnameTooShort);
            return false;
        }

        if (TextUtils.isEmpty(email)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorEmail).show();
            return false;
        } else if (!StringUtils.isValidEmailId(email)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorInvalidEmail).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorPasswordEmptyMsg);
            return false;
        } else if (password.length() < 8) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorPasswordLength);
            return false;
        }

        return validateConsumerFields(address, phoneNo, contactMode);
    }

    private boolean validateContractorFields(String businessName, String businessAddress, String workingArea, String summary) {

        if (TextUtils.isEmpty(businessName)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorBusinessName).show();
            return false;
        }

        if (TextUtils.isEmpty(businessAddress)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorBusinessAddress).show();
            return false;
        }

        if (TextUtils.isEmpty(workingArea)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorWorkingArea).show();
            return false;
        }

        return true;
    }

    private boolean validateConsumerFields(String address, String phoneNo, String contactMode) {
        if (TextUtils.isEmpty(address)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorAddress).show();
            return false;
        }

        if (!TextUtils.isEmpty(phoneNo) && phoneNo.length() < 10) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorValidPhoneNumber).show();
            return false;
        }

        return true;
    }

    private void getContractorList() {

        ProgressHelper.start(this, getString(R.string.msg_please_wait));
        DataManager.getInstance().getContractorList(this, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                if (response == null) return;

                ArrayList<ContractorTypeDetail> arrayList = (ArrayList<ContractorTypeDetail>) response;
                Intent intent = new Intent(SignUpActivity.this, ContractorTypeSelectionActivity.class);
                intent.putParcelableArrayListExtra(DATA, arrayList);
                intent.putExtra(INTENT_TITLE, stringContractorType);
                startActivityForResult(intent, CONTRACTOR_TYPE_REQUEST_CODE);
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
                Utils.showError(SignUpActivity.this, constraintRoot, error);
            }
        });
    }

    private void createAccount(String imageUrl) {
        String firstName = editFirstName.getText().toString();
        String lastName = editLastName.getText().toString();
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();
        String address = editAddress.getText().toString();
        String phoneNo = editPhoneNo.getText().toString();
        String contactMode = editContactMode.getText().toString();

        if (validateFields(firstName, lastName, email, password, address, phoneNo, contactMode)) {
            signUpMethod(firstName, lastName, email, password, address, phoneNo, contactMode, imageUrl);
        }
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
        ProgressHelper.start(this, stringPleaseWait);

        DataManager.getInstance().activateUser(this, apiKey, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                ActivateUserResponse activateUserResponse = (ActivateUserResponse) response;
                Toast.makeText(SignUpActivity.this, activateUserResponse.getDatas().get(0), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
                ErrorManager errorManager = new ErrorManager(SignUpActivity.this, constraintRoot, error);
                errorManager.handleErrorResponse();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) return;

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case WORKING_AREA_REQUEST_CODE:
                    break;

                case CONTRACTOR_TYPE_REQUEST_CODE:
                    contractorTypeDetail = data.getParcelableExtra(INTENT_SELECTED_ITEM);
                    break;

                case CONTACT_MODE_REQUEST_CODE:
                    editContactMode.setText(data.getStringExtra(INTENT_SELECTED_ITEM));
                    break;

                case USER_TYPE_REQUEST_CODE:
                    break;

                case IMAGE_UPLOAD_REQUEST_CODE:
                    createAccount(data.getStringExtra(INTENT_IMAGE_URL));
                    break;

                case PLACE_PICKER_REQUEST:
                    getAddressLatLng(PlacePicker.getPlace(this, data));
                    break;
            }
        }
    }

    private void getAddressLatLng(Place place) {
        editAddress.setText(place.getAddress());
        addressLatLng = place.getLatLng();
    }

    public void getIntentData() {
        if (getIntent().hasExtra(INTENT_PROVIDER) && getIntent().hasExtra(INTENT_PROVIDER_ID)) {
            provider = getIntent().getStringExtra(INTENT_PROVIDER);
            providerId = getIntent().getStringExtra(INTENT_PROVIDER_ID);
        }
    }

    ClickableSpan clickableSpanTermsService = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            Toast.makeText(SignUpActivity.this, stringTermsOfService, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(getResources().getColor(R.color.colorGreen));
        }
    };
    ClickableSpan clickableSpanPrivacyPolicy = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            Toast.makeText(SignUpActivity.this, stringPrivacyPolicy, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(getResources().getColor(R.color.colorGreen));
        }
    };
}
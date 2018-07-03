package com.buildboard.modules.signup.contractor;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.customviews.BuildBoardEditText;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.http.DataManager;
import com.buildboard.modules.signup.adapter.WorkTypeAdapter;
import com.buildboard.modules.signup.contractor.models.businessinfo.BusinessInfoData;
import com.buildboard.modules.signup.contractor.models.businessinfo.BusinessInfoRequest;
import com.buildboard.modules.signup.contractor.models.businessinfo.BusinessInfoResponse;
import com.buildboard.modules.signup.imageupload.ImageUploadActivity;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.StringUtils;
import com.buildboard.view.SnackBarFactory;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.oob.SignUp;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpContractorActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.constraint_root)
    ConstraintLayout constraintRoot;
    @BindView(R.id.edit_business_name)
    BuildBoardEditText editBusinessName;
    @BindView(R.id.edit_business_address)
    BuildBoardTextView editBusinessAddress;
    @BindView(R.id.edit_first_name)
    BuildBoardEditText editPrincipalFirstName;
    @BindView(R.id.edit_last_name)
    BuildBoardEditText editPrincipalLastName;
    @BindView(R.id.edit_email)
    BuildBoardEditText editEmail;
    @BindView(R.id.edit_password)
    BuildBoardEditText editPassword;
    @BindView(R.id.edit_summary)
    BuildBoardEditText editSummary;
    @BindView(R.id.spinner_working_area)
    Spinner spinnerWorkingArea;

    @BindString(R.string.sign_up)
    String stringSignUp;
    @BindString(R.string.terms_of_service)
    String stringTermsOfService;
    @BindString(R.string.privacy_policy_text)
    String stringPrivacyPolicy;
    @BindView(R.id.text_terms_of_service)
    BuildBoardTextView textTermsOfService;
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
    @BindString(R.string.please_enter_a_valid_address)
    String stringEnterValidAddress;
    @BindString(R.string.error_working_area)
    String stringErrorWorkingArea;
    @BindString(R.string.please_wait)
    String stringPleaseWait;

    @BindArray(R.array.array_working_area)
    String[] arrayWorkingArea;

    private String mProvider;
    private String mProviderId;
    private String mEmail;
    private LatLng addressLatLng;
    private String workingArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_contractor);
        ButterKnife.bind(this);

        title.setText(stringSignUp);
        setTermsServiceText();
        getIntentData();
    }

    @OnClick(R.id.edit_business_address)
    void contractorAddressTapped() {
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
    void nextTapped() {
        String password;
        String businessName = editBusinessName.getText().toString();
        String businessAddress = editBusinessAddress.getText().toString();
        String principalFirstName = editPrincipalFirstName.getText().toString();
        String principalLastName = editPrincipalLastName.getText().toString();
        String email = editEmail.getText().toString();
        String summary = editSummary.getText().toString();

        if (mProviderId != null && mProvider != null) {
            password = "not_required"; //TODO remove hardcoded string
        } else {
            password = editPassword.getText().toString();
        }

        if (validateContractorFields(businessName, businessAddress, principalFirstName, principalLastName,
                email, password, workingArea, summary)) {
            BusinessInfoRequest businessInfoRequest = getBusinessInfoRequest(businessName, businessAddress, principalFirstName, principalLastName,
                    email, password, workingArea, summary);
            saveBusinessInfo(businessInfoRequest);
        }
        /*Intent intent = new Intent(this, WorkTypeActivity.class);
        startActivity(intent);*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) return;

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PLACE_PICKER_REQUEST:
                    getAddressLatLng(PlacePicker.getPlace(this, data));
            }
        }
    }

    private void getAddressLatLng(Place place) {
        editBusinessAddress.setText(place.getAddress());
        addressLatLng = place.getLatLng();
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

    private void getIntentData() {
        if (getIntent().hasExtra(INTENT_PROVIDER) && getIntent().hasExtra(INTENT_PROVIDER_ID) && getIntent().hasExtra(INTENT_EMAIL)) {
            mProvider = getIntent().getStringExtra(INTENT_PROVIDER);
            mProviderId = getIntent().getStringExtra(INTENT_PROVIDER_ID);
            mEmail = getIntent().getStringExtra(INTENT_EMAIL);
        }

        if (mProvider != null && mProviderId != null) {
            editPassword.setVisibility(View.GONE);
            editEmail.setText(mEmail);
            editEmail.setFocusable(false);
            editEmail.setFocusableInTouchMode(false);
            editEmail.setClickable(false);
            editEmail.setCursorVisible(false);
        } else {
            editPassword.setVisibility(View.VISIBLE);
            editEmail.setFocusable(true);
            editEmail.setFocusableInTouchMode(true);
            editEmail.setClickable(true);
            editEmail.setCursorVisible(true);
        }

        populateWorkingArea();
    }

    private void populateWorkingArea () {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayWorkingArea);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWorkingArea.setAdapter(adapter);

        spinnerWorkingArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                workingArea = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //TODO implement if needed
            }
        });
    }

    private void saveBusinessInfo(BusinessInfoRequest businessInfoRequest) {
        ProgressHelper.start(this, stringPleaseWait);
        DataManager.getInstance().saveBusinessInfo(this, businessInfoRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                BusinessInfoData businessInfoData = (BusinessInfoData) response;

                Intent intent = new Intent(SignUpContractorActivity.this, WorkTypeActivity.class);
                intent.putExtra(INTENT_WORK_TYPE_ID, businessInfoData.getUserId());
                startActivity(intent);
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
            }
        });
    }

    private BusinessInfoRequest getBusinessInfoRequest (String businessName, String businessAddress, String principalFirstName,
                                                        String principalLastName, String email, String password, String workingArea, String summary) {
        BusinessInfoRequest businessInfoRequest = new BusinessInfoRequest();

        businessInfoRequest.setBusinessName(businessName);
        businessInfoRequest.setBusinessAddress(businessAddress);
        businessInfoRequest.setFirstName(principalFirstName);
        businessInfoRequest.setLastName(principalLastName);
        businessInfoRequest.setEmail(email);

        if (!password.equalsIgnoreCase("not_required")) // TODO hardcoded string
            businessInfoRequest.setPassword(password);

        if (!TextUtils.isEmpty(summary))
            businessInfoRequest.setSummary(summary);

        if (addressLatLng != null) {
            businessInfoRequest.setLatitude(String.valueOf(addressLatLng.latitude));
            businessInfoRequest.setLongitude(String.valueOf(addressLatLng.longitude));
        }

        String[] radius = workingArea.split("-");
        int minRadius = Integer.valueOf(radius[0].replaceAll("[\\D]", ""));
        int maxRadius = Integer.parseInt(radius[1].replaceAll("[\\D]", ""));
        businessInfoRequest.setMinAreaRadius(minRadius);
        businessInfoRequest.setMaxAreaRadius(maxRadius);

        if (!TextUtils.isEmpty(mProvider) && !TextUtils.isEmpty(mProviderId)) {
            businessInfoRequest.setProvider(mProvider);
            businessInfoRequest.setProviderId(mProviderId);
        }

        return businessInfoRequest;
    }

    private boolean validateContractorFields(String businessName, String businessAddress, String principalFirstName,
                                             String principalLastName, String email, String password, String workingArea, String summary) {

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

        if (TextUtils.isEmpty(principalFirstName)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorFirstName);
            return false;
        } else if (principalFirstName.length() < 3) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorFirstnameTooShort);
            return false;
        }


        if (TextUtils.isEmpty(principalLastName)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorLastName);
            return false;
        }

        if (TextUtils.isEmpty(email)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorEmail).show();
            return false;
        } else if (!StringUtils.isValidEmailId(email)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorInvalidEmail).show();
            return false;
        }

        if (!password.equalsIgnoreCase("not_required")) { //TODO: hardcoded string
            if (TextUtils.isEmpty(password)) {
                SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorPasswordEmptyMsg);
                return false;
            } else if (password.length() < 8) {
                SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorPasswordLength);
                return false;
            }
        }

        return true;
    }

    ClickableSpan clickableSpanTermsService = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            Toast.makeText(SignUpContractorActivity.this, stringTermsOfService, Toast.LENGTH_SHORT).show();
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
            Toast.makeText(SignUpContractorActivity.this, stringPrivacyPolicy, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(getResources().getColor(R.color.colorGreen));
        }
    };
}

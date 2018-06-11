package com.buildboard.modules.signup;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
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
import com.buildboard.modules.paymentdetails.PaymentDetailsActivity;
import com.buildboard.modules.selection.ContractorTypeSelectionActivity;
import com.buildboard.modules.selection.SelectionActivity;
import com.buildboard.modules.signup.imageupload.ImageUploadActivity;
import com.buildboard.modules.signup.models.contractortype.ContractorTypeDetail;
import com.buildboard.modules.signup.models.createconsumer.CreateConsumerData;
import com.buildboard.modules.signup.models.createconsumer.CreateConsumerRequest;
import com.buildboard.modules.signup.models.createcontractor.CreateContractorRequest;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.StringUtils;
import com.buildboard.utils.Utils;
import com.buildboard.view.SnackBarFactory;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.text_terms_of_service)
    BuildBoardTextView textTermsOfService;
    @BindView(R.id.text_user_type)
    BuildBoardTextView textUserType;
    @BindView(R.id.edit_first_name)
    BuildBoardEditText editFirstName;
    @BindView(R.id.edit_last_name)
    BuildBoardEditText editLastName;
    @BindView(R.id.edit_address)
    BuildBoardEditText editAddress;
    @BindView(R.id.edit_phoneno)
    BuildBoardEditText editPhoneNo;
    @BindView(R.id.edit_contact_mode)
    BuildBoardEditText editContactMode;
    @BindView(R.id.edit_email)
    BuildBoardEditText editEmail;
    @BindView(R.id.edit_password)
    BuildBoardEditText editPassword;
    @BindView(R.id.edit_contractor_type)
    BuildBoardEditText editContractorType;
    @BindView(R.id.edit_working_area)
    BuildBoardEditText editWorkingArea;
    @BindView(R.id.edit_business_name)
    BuildBoardEditText editBusinessName;
    @BindView(R.id.edit_business_address)
    BuildBoardEditText editBusinessAddress;
    @BindView(R.id.edit_summary)
    BuildBoardEditText editSummary;

    @BindView(R.id.button_next)
    BuildBoardButton buttonNext;

    @BindView(R.id.constraint_consumer_address_container)
    ConstraintLayout constraintConsumerAddressContainer;
    @BindView(R.id.constraint_contractor_address_container)
    ConstraintLayout constraintContractorAddressContainer;
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

    private ContractorTypeDetail contractorTypeDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        title.setText(stringSignUp);
        setTermsServiceText();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) return;

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case WORKING_AREA_REQUEST_CODE:
                    editWorkingArea.setText(data.getStringExtra(INTENT_SELECTED_ITEM));
                    break;

                case CONTRACTOR_TYPE_REQUEST_CODE:
                    contractorTypeDetail = data.getParcelableExtra(INTENT_SELECTED_ITEM);
                    editContractorType.setText(contractorTypeDetail.getTitle());
                    break;

                case CONTACT_MODE_REQUEST_CODE:
                    editContactMode.setText(data.getStringExtra(INTENT_SELECTED_ITEM));
                    break;

                case USER_TYPE_REQUEST_CODE:
                    textUserType.setText(data.getStringExtra(INTENT_SELECTED_ITEM));
                    showUserTypeUI(textUserType.getText().toString());
                    break;

                case IMAGE_UPLOAD_REQUEST_CODE:
                    createAccount(data.getStringExtra(INTENT_IMAGE_URL));
            }
        }
    }

    @OnClick(R.id.text_user_type)
    void userTypeTapped() {
        openActivity(SelectionActivity.class, new ArrayList<>(Arrays.asList(arrayUserType)), USER_TYPE_REQUEST_CODE, stringUserType);
    }

    @OnClick(R.id.edit_contact_mode)
    void contactModeTapped() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(stringPhone);
        arrayList.add(stringEmail);
        openActivity(SelectionActivity.class, arrayList, CONTACT_MODE_REQUEST_CODE, stringPreferredContactMode);
    }

    @OnClick(R.id.edit_working_area)
    void workingAreaTapped() {
        List<String> workingAreaList = Arrays.asList(getResources().getStringArray(R.array.array_working_area));
        ArrayList<String> arrayList = new ArrayList<>(workingAreaList);
        openActivity(SelectionActivity.class, arrayList, WORKING_AREA_REQUEST_CODE, stringWorkingArea);
    }

    @OnClick(R.id.edit_contractor_type)
    void contractorTypeTapped() {
        if (contractorTypeDetail == null)
            getContractorList();
        else editContractorType.setText(contractorTypeDetail.getTitle());
    }

    @OnClick(R.id.button_next)
    void nextButtonTapped() {

        String userType = textUserType.getText().toString();
        String firstName = editFirstName.getText().toString();
        String lastName = editLastName.getText().toString();
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();
        String address = editAddress.getText().toString();
        String phoneNo = editPhoneNo.getText().toString();
        String contactMode = editContactMode.getText().toString();
        String typeOfContractor = editContractorType.getText().toString();
        String businessName = editBusinessName.getText().toString();
        String businessAddress = editBusinessAddress.getText().toString();
        String workingArea = editWorkingArea.getText().toString();
        String summary = editSummary.getText().toString();

        if (validateFields(userType, firstName, lastName, email, password, address, phoneNo, contactMode, typeOfContractor,
                businessName, businessAddress, workingArea, summary)) {
            Intent intent = new Intent(this, ImageUploadActivity.class);
            startActivityForResult(intent, IMAGE_UPLOAD_REQUEST_CODE);
        }
    }

    private void signUpMethod(String userType, String firstName, String lastName, String email, String password, String address, String phoneNo, String contactMode, String typeOfContractor, String businessName, String businessAddress, String workingArea, String summary, String imageUrl) {

        if (userType.equals(stringContractor)) {
            Intent intent = new Intent(SignUpActivity.this, PaymentDetailsActivity.class);
            intent.putExtra(DATA, getContractorDetails(userType, firstName, lastName, email, password, address, phoneNo, contactMode, typeOfContractor,
                    businessName, businessAddress, workingArea, summary));
            startActivity(intent);
        } else {
            createConsumer(firstName, lastName, email, password, address, phoneNo, contactMode, imageUrl);
        }
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
                Toast.makeText(SignUpActivity.this, createConsumerData.getMessage(), Toast.LENGTH_SHORT).show();
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

        LatLng latLng = getLocationFromAddress(getApplicationContext(), address);
        if (latLng != null) {
            consumerRequest.setLatitude(String.valueOf(latLng.latitude));
            consumerRequest.setLongitude(String.valueOf(latLng.longitude));
        }

        return consumerRequest;
    }

    private CreateContractorRequest getContractorDetails(String userType, String firstName, String lastName, String email, String password, String address, String phoneNo,
                                                         String contactMode, String typeOfContractor, String businessName, String businessAddress, String workingArea,
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

    private void showUserTypeUI(String userType) {
        constraintContractorAddressContainer.setVisibility(userType.equalsIgnoreCase(stringContractor) ? View.VISIBLE : View.GONE);
        constraintConsumerAddressContainer.setVisibility(userType.equalsIgnoreCase(stringConsumer) ? View.VISIBLE : View.GONE);
    }

    private boolean validateFields(String userType, String firstName, String lastName, String email, String password, String address, String phoneNo, String contactMode, String typeOfContractor, String businessName, String businessAddress, String workingArea, String summary) {
        if (userType.equals(stringUserType)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorSelectUserType);
            return false;
        }

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

        return userType.equals(stringConsumer) ?
                validateConsumerFields(address, phoneNo, contactMode) :
                validateContractorFields(typeOfContractor, businessName, businessAddress, workingArea, summary);
    }

    private boolean validateContractorFields(String typeOfContractor, String businessName, String businessAddress, String workingArea, String summary) {

        if (typeOfContractor.equalsIgnoreCase(stringContractorType)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorContractorType).show();
            return false;
        }

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
        String userType = textUserType.getText().toString();
        String firstName = editFirstName.getText().toString();
        String lastName = editLastName.getText().toString();
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();
        String address = editAddress.getText().toString();
        String phoneNo = editPhoneNo.getText().toString();
        String contactMode = editContactMode.getText().toString();
        String typeOfContractor = editContractorType.getText().toString();
        String businessName = editBusinessName.getText().toString();
        String businessAddress = editBusinessAddress.getText().toString();
        String workingArea = editWorkingArea.getText().toString();
        String summary = editSummary.getText().toString();

        if (validateFields(userType, firstName, lastName, email, password, address, phoneNo, contactMode, typeOfContractor,
                businessName, businessAddress, workingArea, summary)) {
            signUpMethod(userType, firstName, lastName, email, password, address, phoneNo, contactMode, typeOfContractor,
                    businessName, businessAddress, workingArea, summary, imageUrl);
        }
    }

    private LatLng getLocationFromAddress(Context context, String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng addressLatLng = null;
        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null || address.size() <= 0)
                return null;

            Address location = address.get(0);
            addressLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return addressLatLng;
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
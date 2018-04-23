package com.buildboard.modules.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.fonts.FontHelper;
import com.buildboard.modules.paymentdetails.PaymentDetailsActivity;
import com.buildboard.modules.selection.SelectionActivity;
import com.buildboard.utils.StringUtils;
import com.buildboard.view.SnackBarFactory;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.text_terms_of_service)
    TextView textTermsOfService;
    @BindView(R.id.text_user_type)
    TextView textUserType;

    @BindView(R.id.edit_first_name)
    EditText editFirstName;
    @BindView(R.id.edit_last_name)
    EditText editLastName;
    @BindView(R.id.edit_address)
    EditText editAddress;
    @BindView(R.id.edit_phoneno)
    EditText editPhoneNo;
    @BindView(R.id.edit_contact_mode)
    EditText editContactMode;
    @BindView(R.id.edit_email)
    EditText editEmail;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.edit_contractor_type)
    EditText editContractorType;
    @BindView(R.id.edit_working_area)
    EditText editWorkingArea;
    @BindView(R.id.edit_business_name)
    EditText editBusinessName;
    @BindView(R.id.edit_business_address)
    EditText editBusinessAddress;
    @BindView(R.id.edit_summary)
    EditText editSummary;

    @BindView(R.id.button_next)
    Button buttonNext;

    @BindView(R.id.constraint_consumer_address_container)
    ConstraintLayout constraintConsumerAddressContainer;
    @BindView(R.id.constraint_contractor_address_container)
    ConstraintLayout constraintContractorAddressContainer;

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

    @BindArray(R.array.user_type_array)
    String[] arrayUserType;

    @BindView(R.id.constraint_root)
    ConstraintLayout constraintRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        setFont();
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
                    editContractorType.setText(data.getStringExtra(INTENT_SELECTED_ITEM));
                    break;

                case CONTACT_MODE_REQUEST_CODE:
                    editContactMode.setText(data.getStringExtra(INTENT_SELECTED_ITEM));
                    break;

                case USER_TYPE_REQUEST_CODE:
                    textUserType.setText(data.getStringExtra(INTENT_SELECTED_ITEM));
                    showUserTypeUI(textUserType.getText().toString());
                    break;
            }
        }
    }

    ClickableSpan clickableSpanTermsService = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            Toast.makeText(SignUpActivity.this, "Terms of Service", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(SignUpActivity.this, "Privacy Policy", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(getResources().getColor(R.color.colorGreen));
        }
    };

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_LIGHT, editPassword, editAddress, editPhoneNo, editEmail, editFirstName, editLastName, editContactMode,
                editContractorType, editWorkingArea, editBusinessName, editBusinessAddress, editSummary, textTermsOfService, textUserType, buttonNext);
    }

    @OnClick(R.id.text_user_type)
    void userTypeTapped() {
        openActivity(SelectionActivity.class, new ArrayList<>(Arrays.asList(arrayUserType)), USER_TYPE_REQUEST_CODE, stringUserType);
    }

    @OnClick(R.id.edit_contact_mode)
    void contactModeTapped() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(stringPreferredContactMode);
        openActivity(SelectionActivity.class, arrayList, CONTACT_MODE_REQUEST_CODE, stringPreferredContactMode);
    }

    @OnClick(R.id.edit_working_area)
    void workingAreaTapped() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(stringWorkingArea);
        openActivity(SelectionActivity.class, arrayList, WORKING_AREA_REQUEST_CODE, stringWorkingArea);
    }

    @OnClick(R.id.edit_contractor_type)
    void contractorTypeTapped() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(stringContractorType);
        openActivity(SelectionActivity.class, arrayList, CONTRACTOR_TYPE_REQUEST_CODE, stringContractorType);
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
                businessName, businessAddress, workingArea, summary))
            startActivity(new Intent(this, PaymentDetailsActivity.class));
    }

    private void setTermsServiceText() {
        SpannableString styledString = new SpannableString(getString(R.string.privacy_policy));
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

        if (TextUtils.isEmpty(lastName)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorLastName).show();
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
//        if (typeOfContractor.equals(stringContractorType)) {
//            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorUserType).show();
//            return false;
//        }

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

        if (TextUtils.isEmpty(summary)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorSummary).show();
            return false;
        }

        return true;
    }

    private boolean validateConsumerFields(String address, String phoneNo, String contactMode) {
        if (TextUtils.isEmpty(address)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorAddress).show();
            return false;
        }

        if (TextUtils.isEmpty(phoneNo)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorPhoneNumber).show();
            return false;
        } else if (phoneNo.length() < 10) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorValidPhoneNumber).show();
            return false;
        }

        return true;
    }
}
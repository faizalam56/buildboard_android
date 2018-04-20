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
    String stringFirstName;
    @BindString(R.string.error_enter_last_name)
    String stringLastName;
    @BindString(R.string.error_invalid_email)
    String stringInvalidEmail;
    @BindString(R.string.error_enter_email)
    String stringEmail;
    @BindString(R.string.error_password)
    String stringPassword;
    @BindString(R.string.error_enter_business_name)
    String stringBusinessName;
    @BindString(R.string.error_enter_business_address)
    String stringBusinessAddress;
    @BindString(R.string.error_enter_summary)
    String stringSummary;
    @BindString(R.string.error_enter_address)
    String stringAddress;
    @BindString(R.string.error_enter_valid_phone_number)
    String stringValidPhoneNumber;
    @BindString(R.string.error_enter_phone_number)
    String stringPhoneNumber;

    @BindArray(R.array.user_type_array)
    String[] arrayUserType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        setFont();
        setTermsServiceText();
        checkUserType();
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
                    checkUserType();
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
        FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, editPassword, editAddress, editPhoneNo, editEmail, editFirstName, editLastName);
    }

    @OnClick(R.id.text_user_type)
    void userTypeTapped() {
        openActivity(SelectionActivity.class, true, new ArrayList<>(Arrays.asList(arrayUserType)), USER_TYPE_REQUEST_CODE, stringUserType);
    }

    @OnClick(R.id.edit_contact_mode)
    void contactModeTapped() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(stringPreferredContactMode);
        openActivity(SelectionActivity.class, true, arrayList, CONTACT_MODE_REQUEST_CODE, stringPreferredContactMode);
    }

    @OnClick(R.id.edit_working_area)
    void workingAreaTapped() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(stringWorkingArea);
        openActivity(SelectionActivity.class, true, arrayList, WORKING_AREA_REQUEST_CODE, stringWorkingArea);
    }

    @OnClick(R.id.edit_contractor_type)
    void contractorTypeTapped() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(stringContractorType);
        openActivity(SelectionActivity.class, true, arrayList, CONTRACTOR_TYPE_REQUEST_CODE, stringContractorType);
    }

    @OnClick(R.id.button_next)
    void nextButtonTapped(View view) {
        if (checkUserType()) {
            if (validateFields(view))
                openActivity(PaymentDetailsActivity.class, false, null, 0, null);
        } else
            SnackBarFactory.createSnackBar(this, view.getRootView(), getString(R.string.error_select_user_type)).show();
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

    private void openActivity(Class classToReplace, boolean isStartForResult, ArrayList<String> arrayList, int requestCode, String title) {
        Intent intent = new Intent(SignUpActivity.this, classToReplace);

        if (isStartForResult) {
            intent.putExtra(DATA, arrayList);
            intent.putExtra(INTENT_TITLE, title);
            startActivityForResult(intent, requestCode);
        } else startActivity(intent);
    }

    private boolean checkUserType() {
        if (textUserType.getText().toString().equalsIgnoreCase(stringContractor)) {
            constraintContractorAddressContainer.setVisibility(View.VISIBLE);
            constraintConsumerAddressContainer.setVisibility(View.GONE);
            return true;
        } else if (textUserType.getText().toString().equalsIgnoreCase(stringConsumer)) {
            constraintConsumerAddressContainer.setVisibility(View.VISIBLE);
            constraintContractorAddressContainer.setVisibility(View.GONE);
            return true;
        } else {
            constraintContractorAddressContainer.setVisibility(View.GONE);
            constraintConsumerAddressContainer.setVisibility(View.GONE);
        }

        return false;
    }

    private boolean validateFields(View view) {
        if (TextUtils.isEmpty(editFirstName.getText())) {
            SnackBarFactory.createSnackBar(this, view.getRootView(), stringFirstName).show();
            return false;
        } else if (TextUtils.isEmpty(editLastName.getText())) {
            SnackBarFactory.createSnackBar(this, view.getRootView(), stringLastName).show();
            return false;
        } else if (TextUtils.isEmpty(editEmail.getText())) {
            if (!StringUtils.isValidEmailId(editEmail.getText().toString()))
                SnackBarFactory.createSnackBar(this, view.getRootView(), stringInvalidEmail).show();
            else
                SnackBarFactory.createSnackBar(this, view.getRootView(), stringEmail).show();
            return false;
        } else if (TextUtils.isEmpty(editPassword.getText())) {
            SnackBarFactory.createSnackBar(this, view.getRootView(), stringPassword).show();
            return false;
        } else if (textUserType.getText().toString().equalsIgnoreCase(stringContractor)) {
            if (!validateContractorFields(view))
                return false;
        } else if (textUserType.getText().toString().equalsIgnoreCase(stringConsumer)) {
            if (!validateConsumerFields(view))
                return false;
        }

        return true;
    }

    private boolean validateContractorFields(View view) {
        if (TextUtils.isEmpty(editBusinessName.getText())) {
            SnackBarFactory.createSnackBar(this, view.getRootView(), stringBusinessName).show();
            return false;
        } else if (TextUtils.isEmpty(editBusinessAddress.getText())) {
            SnackBarFactory.createSnackBar(this, view.getRootView(), stringBusinessAddress).show();
            return false;
        } else if (TextUtils.isEmpty(editSummary.getText())) {
            SnackBarFactory.createSnackBar(this, view.getRootView(), stringSummary).show();
            return false;
        }

        return true;
    }

    private boolean validateConsumerFields(View view) {
        if (TextUtils.isEmpty(editAddress.getText())) {
            SnackBarFactory.createSnackBar(this, view.getRootView(), stringAddress).show();
            return false;
        } else if (TextUtils.isEmpty(editPhoneNo.getText())) {
            if (StringUtils.isValidNumber(editPhoneNo.getText().toString()))
                SnackBarFactory.createSnackBar(this, view.getRootView(), stringValidPhoneNumber).show();
            else
                SnackBarFactory.createSnackBar(this, view.getRootView(), stringPhoneNumber).show();
            return false;
        }

        return true;
    }
}
package com.buildboard.modules.signup;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;

import java.util.ArrayList;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;

public class SignUpBaseActivity extends AppCompatActivity implements AppConstant {

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

    @BindArray(R.array.user_type_array)
    String[] arrayUserType;

    ClickableSpan clickableSpanTermsService = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            Toast.makeText(SignUpBaseActivity.this, "Terms of Service", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(SignUpBaseActivity.this, "Privacy Policy", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(getResources().getColor(R.color.colorGreen));
        }
    };

    protected void setTermsServiceText() {
        SpannableString styledString = new SpannableString(getString(R.string.privacy_policy));
        styledString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorGreen)), 34, 50, 0);
        styledString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorGreen)), 55, 69, 0);
        styledString.setSpan(clickableSpanTermsService, 34, 50, 0);
        styledString.setSpan(clickableSpanPrivacyPolicy, 55, 69, 0);
        textTermsOfService.setText(styledString);
        textTermsOfService.setMovementMethod(LinkMovementMethod.getInstance());
    }

    protected void openActivity(Class classToReplace, boolean isStartForResult, ArrayList<String> arrayList, int requestCode, String title) {
        Intent intent = new Intent(SignUpBaseActivity.this, classToReplace);
        intent.putExtra(DATA, arrayList);
        intent.putExtra(INTENT_TITLE, title);

        if (isStartForResult)
            startActivityForResult(intent, requestCode);
        else startActivity(intent);
    }

    protected void checkUserType() {
        if (textUserType.getText().toString().equalsIgnoreCase(stringContractor)) {
            constraintContractorAddressContainer.setVisibility(View.VISIBLE);
            constraintConsumerAddressContainer.setVisibility(View.GONE);
        } else if (textUserType.getText().toString().equalsIgnoreCase(stringConsumer)) {
            constraintConsumerAddressContainer.setVisibility(View.VISIBLE);
            constraintContractorAddressContainer.setVisibility(View.GONE);
        } else {
            constraintContractorAddressContainer.setVisibility(View.GONE);
            constraintConsumerAddressContainer.setVisibility(View.GONE);
        }
    }
}

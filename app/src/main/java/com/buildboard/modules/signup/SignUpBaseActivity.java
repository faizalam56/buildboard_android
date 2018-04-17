package com.buildboard.modules.signup;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.buildboard.R;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;

@SuppressLint("Registered")
public class SignUpBaseActivity extends AppCompatActivity {
    @BindView(R.id.text_terms_of_service)
    TextView textTermsOfService;

    @BindView(R.id.edit_first_name)
    EditText editFirstName;
    @BindView(R.id.edit_last_name)
    EditText editLastName;

    @BindView(R.id.edit_email)
    EditText editEmail;
    @BindView(R.id.edit_password)
    EditText editPassword;

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

    @BindView(R.id.spinner_gender)
    Spinner spinnerGender;

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

    protected void setSpinnerGenderAdapter() {
        ArrayList<String> userTypeList = new ArrayList<>();
        userTypeList.add(stringGender);
        userTypeList.add(stringFemale);
        userTypeList.add(stringMale);
        userTypeList.add(stringOther);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spinner_usertype_item, userTypeList);
        spinnerGender.setAdapter(arrayAdapter);
    }

    protected void setTermsServiceText() {
        SpannableString styledString = new SpannableString(getString(R.string.privacy_policy));
        styledString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorGreen)), 34, 50, 0);
        styledString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorGreen)), 55, 69, 0);
        styledString.setSpan(clickableSpanTermsService, 34, 50, 0);
        styledString.setSpan(clickableSpanPrivacyPolicy, 55, 69, 0);
        textTermsOfService.setText(styledString);
        textTermsOfService.setMovementMethod(LinkMovementMethod.getInstance());
    }
}

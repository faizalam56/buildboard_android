package com.buildboard.modules.signup;

import android.os.Bundle;
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
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.edit_first_name)
    EditText editFirstName;
    @BindView(R.id.edit_last_name)
    EditText editLastName;
    @BindView(R.id.spinner_gender)
    Spinner spinnerGender;
    @BindView(R.id.edit_email)
    EditText editEmail;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.edit_address)
    EditText editAddress;
    @BindView(R.id.edit_phoneno)
    EditText editPhoneNo;
    @BindView(R.id.spinner_contact_mode)
    Spinner spinnerContactMode;
    @BindView(R.id.text_terms_of_service)
    TextView textTermsOfService;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        setSpinnerGenderAdapter();
        setSpinnerContactModeAdapter();

        setTermsServiceText();
    }

    private void setTermsServiceText() {
        SpannableString styledString = new SpannableString(getString(R.string.privacy_policy));
        styledString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorGreen)), 34, 50, 0);
        styledString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorGreen)), 55, 69, 0);
        styledString.setSpan(clickableSpan, 34, 50, 0);
        styledString.setSpan(clickableSpan1, 55, 69, 0);
        textTermsOfService.setText(styledString);
        textTermsOfService.setMovementMethod(LinkMovementMethod.getInstance());
    }

    ClickableSpan clickableSpan = new ClickableSpan() {
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

    ClickableSpan clickableSpan1 = new ClickableSpan() {
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

    private void setSpinnerGenderAdapter() {
        ArrayList<String> userTypeList = new ArrayList<>();
        userTypeList.add(stringGender);
        userTypeList.add(stringFemale);
        userTypeList.add(stringMale);
        userTypeList.add(stringOther);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spinner_usertype_item, userTypeList);
        spinnerGender.setAdapter(arrayAdapter);
    }

    private void setSpinnerContactModeAdapter() {
        ArrayList<String> userTypeList = new ArrayList<>();
        userTypeList.add(stringPreferredContactMode);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spinner_usertype_item, userTypeList);
        spinnerContactMode.setAdapter(arrayAdapter);
    }
}
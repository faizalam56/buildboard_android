package com.buildboard.modules.signup;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.buildboard.R;
import com.buildboard.utils.FontHelper;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends SignUpBaseActivity {

    @BindView(R.id.edit_address)
    EditText editAddress;
    @BindView(R.id.edit_phoneno)
    EditText editPhoneNo;
    @BindView(R.id.spinner_contact_mode)
    Spinner spinnerContactMode;

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

        setFont();
        setSpinnerGenderAdapter();
        setSpinnerContactModeAdapter();

        setTermsServiceText();
    }

    protected void setSpinnerContactModeAdapter() {
        ArrayList<String> userTypeList = new ArrayList<>();
        userTypeList.add(stringPreferredContactMode);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spinner_usertype_item, userTypeList);
        spinnerContactMode.setAdapter(arrayAdapter);
    }

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, editPassword, editAddress, editPhoneNo, editEmail, editFirstName, editLastName);
    }
}
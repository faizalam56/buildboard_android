package com.buildboard.modules.signup;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.buildboard.R;
import com.buildboard.utils.FontHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpContractorActivity extends SignUpBaseActivity {

    @BindView(R.id.spinner_contractor_type)
    Spinner spinnerContractorType;
    @BindView(R.id.spinner_working_area)
    Spinner spinnerWorkingArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_contractor);
        ButterKnife.bind(this);

        setFont();
        setSpinnerGenderAdapter();
        setSpinnerContractorTypeAdapter();
        setSpinnerWorkingAreaAdapter();

        setTermsServiceText();
    }

    protected void setSpinnerContractorTypeAdapter() {
        ArrayList<String> userTypeList = new ArrayList<>();
        userTypeList.add(stringContractorType);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spinner_usertype_item, userTypeList);
        spinnerContractorType.setAdapter(arrayAdapter);
    }

    protected void setSpinnerWorkingAreaAdapter() {
        ArrayList<String> userTypeList = new ArrayList<>();
        userTypeList.add(stringWorkingArea);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spinner_usertype_item, userTypeList);
        spinnerWorkingArea.setAdapter(arrayAdapter);
    }

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, editPassword, editEmail, editFirstName, editLastName);
    }
}
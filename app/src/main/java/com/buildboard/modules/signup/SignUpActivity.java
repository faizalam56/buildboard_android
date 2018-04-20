package com.buildboard.modules.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.fonts.FontHelper;
import com.buildboard.modules.selection.SelectionActivity;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.ButterKnife;

public class SignUpActivity extends SignUpBaseActivity implements AppConstant {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        setFont();
        setTermsServiceText();
        checkUserType();
    }

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, editPassword, editAddress, editPhoneNo, editEmail, editFirstName, editLastName);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (data == null) return;

            switch (requestCode) {

                case WORKING_AREA_RESULT_CODE:
                    if (data.hasExtra(INTENT_SELECTED_ITEM))
                        editWorkingArea.setText(data.getStringExtra(INTENT_SELECTED_ITEM));
                    break;

                case CONTRACTOR_TYPE_RESULT_CODE:
                    if (data.hasExtra(INTENT_SELECTED_ITEM))
                        editContractorType.setText(data.getStringExtra(INTENT_SELECTED_ITEM));
                    break;

                case CONTACT_MODE_RESULT_CODE:
                    if (data.hasExtra(INTENT_SELECTED_ITEM))
                        editContactMode.setText(data.getStringExtra(INTENT_SELECTED_ITEM));
                    break;

                case USER_TYPE_RESULT_CODE:
                    if (data.hasExtra(INTENT_SELECTED_ITEM)) {
                        textUserType.setText(data.getStringExtra(INTENT_SELECTED_ITEM));
                        checkUserType();
                    }
                    break;
            }
        }
    }

    public void openUserTypeSelection(View view) {
        openActivity(SelectionActivity.class, true, new ArrayList<>(Arrays.asList(arrayUserType)), USER_TYPE_RESULT_CODE, stringUserType);
    }

    public void openContactModeSelection(View view) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(stringPreferredContactMode);
        openActivity(SelectionActivity.class, true, arrayList, CONTACT_MODE_RESULT_CODE, stringPreferredContactMode);
    }

    public void openWorkingAreaSelection(View view) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(stringWorkingArea);
        openActivity(SelectionActivity.class, true, arrayList, WORKING_AREA_RESULT_CODE, stringWorkingArea);
    }

    public void openContractorTypeSelection(View view) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(stringContractorType);
        openActivity(SelectionActivity.class, true, arrayList, CONTRACTOR_TYPE_RESULT_CODE, stringContractorType);
    }
}
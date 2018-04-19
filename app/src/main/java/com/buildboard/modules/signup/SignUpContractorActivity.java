package com.buildboard.modules.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.buildboard.R;
import com.buildboard.modules.selection.SelectionActivity;
import com.buildboard.constants.AppConstant;
import com.buildboard.fonts.FontHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpContractorActivity extends SignUpBaseActivity implements AppConstant {

    @BindView(R.id.edit_contractor_type)
    EditText editContractorType;
    @BindView(R.id.edit_working_area)
    EditText editWorkingArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_contractor);
        ButterKnife.bind(this);

        setFont();
        setSpinnerGenderAdapter();

        setTermsServiceText();
    }

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, editPassword, editEmail, editFirstName, editLastName);
    }

    private void openActivity(Class classToReplace, boolean isStartForResult, ArrayList<String> selectionList, int resultCode, String title) {
        Intent intent = new Intent(SignUpContractorActivity.this, classToReplace);
        intent.putExtra(DATA, selectionList);
        intent.putExtra(INTENT_TITLE, title);

        if (isStartForResult)
            startActivityForResult(intent, resultCode);
        else startActivity(intent);
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
            }
        }
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
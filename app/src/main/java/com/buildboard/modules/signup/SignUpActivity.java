package com.buildboard.modules.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.buildboard.R;
import com.buildboard.utils.AppConstant;
import com.buildboard.utils.FontHelper;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends SignUpBaseActivity implements AppConstant {

    @BindView(R.id.edit_address)
    EditText editAddress;
    @BindView(R.id.edit_phoneno)
    EditText editPhoneNo;

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
    @BindView(R.id.edit_contact_mode)
    EditText editContactMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        setFont();
        setSpinnerGenderAdapter();

        setTermsServiceText();
    }

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, editPassword, editAddress, editPhoneNo, editEmail, editFirstName, editLastName);
    }

    private void openActivity(Class classToReplace, boolean isStartForResult, String title) {
        Intent intent = new Intent(SignUpActivity.this, classToReplace);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(stringPreferredContactMode);
        intent.putExtra(DATA, arrayList);
        intent.putExtra(INTENT_TITLE, title);

        if (isStartForResult)
            startActivityForResult(intent, ACTIVITY_RESULT_CODE);
        else startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;

        if (requestCode == ACTIVITY_RESULT_CODE) {
            if (data.hasExtra(INTENT_SELECTION))
                editContactMode.setText(data.getStringExtra(INTENT_SELECTION));
        }
    }

    public void openContactModeSelection(View view) {
        openActivity(SignUpSelectionActivity.class, true, stringPreferredContactMode);
    }
}
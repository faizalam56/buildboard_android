package com.buildboard.modules.paymentdetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.modules.login.LoginActivity;
import com.buildboard.modules.selection.SelectionActivity;
import com.buildboard.view.SnackBarFactory;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentDetailsActivity extends AppCompatActivity implements AppConstant {

    @BindString(R.string.payment_details)
    String stringPaymentDetails;
    @BindString(R.string.card_type)
    String stringCardType;

    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_address)
    EditText editAddress;
    @BindView(R.id.edit_city)
    EditText editCity;
    @BindView(R.id.edit_card_type)
    EditText editCardType;
    @BindView(R.id.edit_card_number)
    EditText editCardNumber;
    @BindView(R.id.edit_expire)
    EditText editExpire;
    @BindView(R.id.edit_cvv)
    EditText editCvv;
    @BindView(R.id.edit_name_on_card)
    EditText editNameOnCard;
    @BindView(R.id.button_next)
    Button buttonNext;
    @BindView(R.id.text_skip)
    TextView textSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.edit_card_type)
    void openCardTypeSelection() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(stringCardType);
        openActivity(SelectionActivity.class, true, arrayList, CONTACT_MODE_RESULT_CODE, stringCardType);
    }

    @OnClick(R.id.button_next)
    void nextButtonTapped(View view) {
        if (validateFields(view))
            finish();
    }

    private boolean validateFields(View view) {
        if (TextUtils.isEmpty(editName.getText())) {
            SnackBarFactory.createSnackBar(this, view.getRootView(), getString(R.string.error_enter_name)).show();
            return false;
        } else if (TextUtils.isEmpty(editAddress.getText())) {
            SnackBarFactory.createSnackBar(this, view.getRootView(), getString(R.string.error_enter_address)).show();
            return false;
        } else if (TextUtils.isEmpty(editCity.getText())) {
            SnackBarFactory.createSnackBar(this, view.getRootView(), getString(R.string.error_enter_city)).show();
            return false;
        } else if (TextUtils.isEmpty(editCardNumber.getText())) {
            SnackBarFactory.createSnackBar(this, view.getRootView(), getString(R.string.error_enter_card_number)).show();
            return false;
        } else if (TextUtils.isEmpty(editExpire.getText())) {
            SnackBarFactory.createSnackBar(this, view.getRootView(), getString(R.string.error_enter_expire_date)).show();
            return false;
        } else if (TextUtils.isEmpty(editCvv.getText())) {
            SnackBarFactory.createSnackBar(this, view.getRootView(), getString(R.string.error_enter_cvv)).show();
            return false;
        } else if (TextUtils.isEmpty(editNameOnCard.getText())) {
            SnackBarFactory.createSnackBar(this, view.getRootView(), getString(R.string.error_enter_card_name)).show();
            return false;
        }

        return true;
    }

    @OnClick(R.id.text_skip)
    void skipTextTapped() {
        openActivity(LoginActivity.class, false, null, 0, null);
    }

    private void openActivity(Class classToReplace, boolean isStartForResult, ArrayList<String> arrayList, int requestCode, String title) {
        Intent intent = new Intent(PaymentDetailsActivity.this, classToReplace);

        if (isStartForResult) {
            intent.putExtra(DATA, arrayList);
            intent.putExtra(INTENT_TITLE, title);
            startActivityForResult(intent, requestCode);
        } else startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (data == null) return;

            switch (requestCode) {

                case WORKING_AREA_RESULT_CODE:
                        editCardType.setText(data.getStringExtra(INTENT_SELECTED_ITEM));
                    break;
            }
        }
    }
}
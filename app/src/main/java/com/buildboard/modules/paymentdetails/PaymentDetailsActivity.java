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
    @BindString(R.string.error_enter_name)
    String stringName;
    @BindString(R.string.error_enter_address)
    String stringAddress;
    @BindString(R.string.error_enter_city)
    String stringCity;
    @BindString(R.string.error_enter_card_number)
    String stringCardNumber;
    @BindString(R.string.error_enter_expire_date)
    String stringExpireDate;
    @BindString(R.string.error_enter_cvv)
    String stringCvv;
    @BindString(R.string.error_enter_card_name)
    String stringCarsName;

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
    void cardTypeTapped() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(stringCardType);
        openActivity(SelectionActivity.class, true, arrayList, CARD_TYPE_REQUEST_CODE, stringCardType);
    }

    @OnClick(R.id.button_next)
    void nextButtonTapped(View view) {
        if (validateFields(view))
            finish();
    }

    private boolean validateFields(View view) {
        if (TextUtils.isEmpty(editName.getText())) {
            SnackBarFactory.createSnackBar(this, view.getRootView(), stringName).show();
            return false;
        } else if (TextUtils.isEmpty(editAddress.getText())) {
            SnackBarFactory.createSnackBar(this, view.getRootView(), stringAddress).show();
            return false;
        } else if (TextUtils.isEmpty(editCity.getText())) {
            SnackBarFactory.createSnackBar(this, view.getRootView(), stringCity).show();
            return false;
        } else if (TextUtils.isEmpty(editCardNumber.getText())) {
            SnackBarFactory.createSnackBar(this, view.getRootView(), stringCardNumber).show();
            return false;
        } else if (TextUtils.isEmpty(editExpire.getText())) {
            SnackBarFactory.createSnackBar(this, view.getRootView(), stringExpireDate).show();
            return false;
        } else if (TextUtils.isEmpty(editCvv.getText())) {
            SnackBarFactory.createSnackBar(this, view.getRootView(), stringCvv).show();
            return false;
        } else if (TextUtils.isEmpty(editNameOnCard.getText())) {
            SnackBarFactory.createSnackBar(this, view.getRootView(), stringCarsName).show();
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

        if (data == null) return;

        if (resultCode == RESULT_OK && requestCode == CARD_TYPE_REQUEST_CODE) {
            editCardType.setText(data.getStringExtra(INTENT_SELECTED_ITEM));
        }
    }
}
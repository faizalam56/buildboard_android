package com.buildboard.modules.paymentdetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.fonts.FontHelper;
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

    @BindView(R.id.constraint_root)
    ConstraintLayout constraintRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);
        ButterKnife.bind(this);

        setFont();
    }

    @OnClick(R.id.edit_card_type)
    void cardTypeTapped() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(stringCardType);
        openActivity(SelectionActivity.class, true, arrayList, CARD_TYPE_REQUEST_CODE, stringCardType);
    }

    @OnClick(R.id.button_next)
    void nextButtonTapped(View view) {
        String name = editName.getText().toString();
        String address = editAddress.getText().toString();
        String city = editCity.getText().toString();
        String cardNumber = editCardNumber.getText().toString();
        String expire = editExpire.getText().toString();
        String cvv = editCvv.getText().toString();
        String nameOnCard = editNameOnCard.getText().toString();

        if (validateFields(name, address, city, cardNumber, expire, cvv, nameOnCard))
            finish();
    }

    private boolean validateFields(String name, String address, String city, String cardNumber, String expire, String cvv, String nameOnCard) {
        if (TextUtils.isEmpty(name)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringName).show();
            return false;
        }

        if (TextUtils.isEmpty(address)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringAddress).show();
            return false;
        }

        if (TextUtils.isEmpty(city)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringCity).show();
            return false;
        }

        if (TextUtils.isEmpty(cardNumber)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringCardNumber).show();
            return false;
        }

        if (TextUtils.isEmpty(expire)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringExpireDate).show();
            return false;
        }

        if (TextUtils.isEmpty(cvv)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringCvv).show();
            return false;
        }

        if (TextUtils.isEmpty(nameOnCard)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringCarsName).show();
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

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, editName, editAddress, editCity, editCardType, editCardNumber,
                editExpire, editCvv, editNameOnCard, buttonNext, textSkip);
    }
}
package com.buildboard.modules.paymentdetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.fonts.FontHelper;
import com.buildboard.http.DataManager;
import com.buildboard.http.ErrorManager;
import com.buildboard.modules.login.LoginActivity;
import com.buildboard.modules.selection.SelectionActivity;
import com.buildboard.modules.signup.models.createcontractor.CreateContractorRequest;
import com.buildboard.utils.ProgressHelper;
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
    String stringErrorName;
    @BindString(R.string.error_enter_address)
    String stringErrorAddress;
    @BindString(R.string.error_enter_city)
    String stringErrorCity;
    @BindString(R.string.error_enter_card_number)
    String stringErrorCardNumber;
    @BindString(R.string.error_enter_expire_date)
    String stringErrorExpireDate;
    @BindString(R.string.error_enter_cvv)
    String stringErrorCvv;
    @BindString(R.string.error_enter_card_name)
    String stringErrorCarsName;

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

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private CreateContractorRequest createContractorRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);
        ButterKnife.bind(this);

        toolbar.setTitle(stringPaymentDetails);
        setFont();
        getIntentData();
    }

    @OnClick(R.id.edit_card_type)
    void cardTypeTapped() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(stringCardType);
        openActivity(SelectionActivity.class, arrayList, CARD_TYPE_REQUEST_CODE, stringCardType);
    }

    @OnClick(R.id.button_next)
    void nextButtonTapped() {
        /*String name = editName.getText().toString();
        String address = editAddress.getText().toString();
        String city = editCity.getText().toString();
        String cardNumber = editCardNumber.getText().toString();
        String expire = editExpire.getText().toString();
        String cvv = editCvv.getText().toString();
        String nameOnCard = editNameOnCard.getText().toString();

        if (validateFields(name, address, city, cardNumber, expire, cvv, nameOnCard)) {
            openLoginActivity();
        }*/
        createContractor();
    }

    private boolean validateFields(String name, String address, String city, String cardNumber, String expire, String cvv, String nameOnCard) {
        if (TextUtils.isEmpty(name)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorName).show();
            return false;
        }

        if (TextUtils.isEmpty(address)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorAddress).show();
            return false;
        }

        if (TextUtils.isEmpty(city)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorCity).show();
            return false;
        }

        if (TextUtils.isEmpty(cardNumber)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorCardNumber).show();
            return false;
        }

        if (TextUtils.isEmpty(expire)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorExpireDate).show();
            return false;
        }

        if (TextUtils.isEmpty(cvv)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorCvv).show();
            return false;
        }

        if (TextUtils.isEmpty(nameOnCard)) {
            SnackBarFactory.createSnackBar(this, constraintRoot, stringErrorCarsName).show();
            return false;
        }

        return true;
    }

    @OnClick(R.id.text_skip)
    void skipTextTapped() {
        createContractor();
    }

    private void openActivity(Class classToReplace, ArrayList<String> arrayList, int requestCode, String title) {
        Intent intent = new Intent(PaymentDetailsActivity.this, classToReplace);
        intent.putExtra(DATA, arrayList);
        intent.putExtra(INTENT_TITLE, title);
        startActivityForResult(intent, requestCode);
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
        FontHelper.setFontFace(FontHelper.FontType.FONT_LIGHT, editName, editAddress, editCity, editCardType, editCardNumber,
                editExpire, editCvv, editNameOnCard, buttonNext, textSkip);
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void createContractor() {
        ProgressHelper.start(this, getString(R.string.msg_please_wait));
        DataManager.getInstance().createContractor(PaymentDetailsActivity.this, createContractorRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                if (response == null) return;

                openLoginActivity();
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
                ErrorManager errorManager = new ErrorManager(PaymentDetailsActivity.this, constraintRoot, error);
                errorManager.handleErrorResponse();
            }
        });
    }

    private void getIntentData() {

        if (getIntent().hasExtra(DATA)) {
            createContractorRequest = getIntent().getParcelableExtra(DATA);
        }
    }
}
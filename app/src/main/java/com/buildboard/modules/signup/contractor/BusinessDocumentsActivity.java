package com.buildboard.modules.signup.contractor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.http.DataManager;
import com.buildboard.modules.signup.contractor.models.businessdocument.BusinessDocumentsRequest;
import com.buildboard.modules.signup.contractor.models.businessdocument.DocumentData;
import com.buildboard.modules.signup.contractor.models.businessdocument.BusinessDocuments;
import com.buildboard.utils.ProgressHelper;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BusinessDocumentsActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.title)
    TextView title;

    @BindString(R.string.documents)
    String stringDocuments;
    @BindString(R.string.terms_of_service)
    String stringTermsOfService;
    @BindString(R.string.privacy_policy_text)
    String stringPrivacyPolicy;
    @BindView(R.id.text_terms_of_service)
    BuildBoardTextView textTermsOfService;
    @BindString(R.string.please_wait)
    String stringPleaseWait;

    private String mUserId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_documents);
        ButterKnife.bind(this);

        title.setText(stringDocuments);
        getIntentData();
        setTermsServiceText();
    }

    @OnClick(R.id.button_next)
    void nextTapped() {
        storeContractorDocuments();
    }

    private void getIntentData() {
        if (getIntent().hasExtra(INTENT_USER_ID))
            mUserId = getIntent().getStringExtra(INTENT_USER_ID);
    }

    private void setTermsServiceText() {
        SpannableString styledString = new SpannableString(getString(R.string.privacy_policy_text));
        styledString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorGreen)), 34, 50, 0);
        styledString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorGreen)), 55, 69, 0);
        styledString.setSpan(clickableSpanTermsService, 34, 50, 0);
        styledString.setSpan(clickableSpanPrivacyPolicy, 55, 69, 0);
        textTermsOfService.setText(styledString);
        textTermsOfService.setMovementMethod(LinkMovementMethod.getInstance());
    }

    ClickableSpan clickableSpanTermsService = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            Toast.makeText(BusinessDocumentsActivity.this, stringTermsOfService, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(getResources().getColor(R.color.colorGreen));
        }
    };

    ClickableSpan clickableSpanPrivacyPolicy = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            Toast.makeText(BusinessDocumentsActivity.this, stringPrivacyPolicy, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(getResources().getColor(R.color.colorGreen));
        }
    };

    private void storeContractorDocuments() {
        ProgressHelper.start(this, stringPleaseWait);
        BusinessDocumentsRequest businessDocumentsRequest = getBusinessRequest();
        DataManager.getInstance().storeContractorDocuments(this, businessDocumentsRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                Intent intent = new Intent(BusinessDocumentsActivity.this, PreviousWorkActivity.class);
                intent.putExtra(INTENT_USER_ID, mUserId);
                startActivity(intent);
//                ArrayList<ProjectsData> data = (ArrayList<ProjectsData>) response;
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
            }
        });
    }

    private BusinessDocumentsRequest getBusinessRequest() {
        BusinessDocuments businessDocuments = new BusinessDocuments();
        businessDocuments.setWorkmanCampInsurance(getWorkmanInsurance());
        businessDocuments.setInsurance(getInsurance());
        businessDocuments.setCertification(getCertification());
        businessDocuments.setBonding(getBonding());
        businessDocuments.setBusinessLicensing(getBusinessLicensing());

        BusinessDocumentsRequest businessDocumentsRequest = new BusinessDocumentsRequest();
        businessDocumentsRequest.setBusinessDocuments(businessDocuments);
        businessDocumentsRequest.setId(mUserId);
//        businessDocumentsRequest.setId("38c0c020-8f01-11e8-b310-6778951ca517");

        return businessDocumentsRequest;
    }

    private HashMap<Integer, ArrayList<DocumentData>> getWorkmanInsurance() {
        HashMap<Integer, ArrayList<DocumentData>> workmanInsurances = new HashMap<>();

        for (int i = 1; i <= 2; i++) {
            ArrayList<DocumentData> workmanInsuranceDetails = new ArrayList<>();
            DocumentData insuranceProvider = new DocumentData();
            insuranceProvider.setKey(KEY_INSURANCE_PROVIDER);
            insuranceProvider.setType(TYPE_TEXT);
            insuranceProvider.setValue("");
            workmanInsuranceDetails.add(insuranceProvider);

            DocumentData insuranceDollarAmount = new DocumentData();
            insuranceDollarAmount.setKey(KEY_INSURANCE_DOLLAR_AMOUNT);
            insuranceDollarAmount.setType(TYPE_TEXT);
            insuranceDollarAmount.setValue("");
            workmanInsuranceDetails.add(insuranceDollarAmount);

            DocumentData insuranceAttachment = new DocumentData();
            insuranceAttachment.setKey(KEY_ATTACHMENT_INSURANCE);
            insuranceAttachment.setType(TYPE_ATTACHMENT);
            insuranceAttachment.setValue("");
            workmanInsuranceDetails.add(insuranceAttachment);

            workmanInsurances.put(i, workmanInsuranceDetails);
        }

        return workmanInsurances;
    }

    private HashMap<Integer, ArrayList<DocumentData>> getInsurance() {
        HashMap<Integer, ArrayList<DocumentData>> insurances = new HashMap<>();

        for (int i = 1; i <= 2; i++) {
            ArrayList<DocumentData> insuranceDetails = new ArrayList<>();
            DocumentData insuranceLiability = new DocumentData();
            insuranceLiability.setKey(KEY_LIABILITY);
            insuranceLiability.setType(TYPE_TEXT);
            insuranceLiability.setValue("");
            insuranceDetails.add(insuranceLiability);

            DocumentData insuranceProvider = new DocumentData();
            insuranceProvider.setKey(KEY_INSURANCE_PROVIDER);
            insuranceProvider.setType(TYPE_TEXT);
            insuranceProvider.setValue("");
            insuranceDetails.add(insuranceProvider);

            DocumentData insuranceDollarAmount = new DocumentData();
            insuranceDollarAmount.setKey(KEY_INSURANCE_DOLLAR_AMOUNT);
            insuranceDollarAmount.setType(TYPE_TEXT);
            insuranceDollarAmount.setValue("");
            insuranceDetails.add(insuranceDollarAmount);

            DocumentData insuranceAttachment = new DocumentData();
            insuranceAttachment.setKey(KEY_ATTACHMENT_INSURANCE);
            insuranceAttachment.setType(TYPE_ATTACHMENT);
            insuranceAttachment.setValue("");
            insuranceDetails.add(insuranceAttachment);

            insurances.put(i, insuranceDetails);
        }

        return insurances;
    }

    private HashMap<Integer, ArrayList<DocumentData>> getCertification() {
        HashMap<Integer, ArrayList<DocumentData>> certifications = new HashMap<>();

        for (int i = 1; i <= 2; i++) {
            ArrayList<DocumentData> certificationDetails = new ArrayList<>();
            DocumentData certifying = new DocumentData();
            certifying.setKey(KEY_CERTIFYING);
            certifying.setType(TYPE_TEXT);
            certifying.setValue("");
            certificationDetails.add(certifying);

            DocumentData certificationNumber = new DocumentData();
            certificationNumber.setKey(KEY_CERTFICATION_NUMBER);
            certificationNumber.setType(TYPE_TEXT);
            certificationNumber.setValue("");
            certificationDetails.add(certificationNumber);

            DocumentData certificationDescript = new DocumentData();
            certificationDescript.setKey(KEY_CERTFICATION_DESCRIPTION);
            certificationDescript.setType(TYPE_TEXT);
            certificationDescript.setValue("");
            certificationDetails.add(certificationDescript);

            DocumentData certificationAttachment = new DocumentData();
            certificationAttachment.setKey(KEY_ATTACHMENT_CERTIFICATION);
            certificationAttachment.setType(TYPE_ATTACHMENT);
            certificationAttachment.setValue("");
            certificationDetails.add(certificationAttachment);

            certifications.put(i, certificationDetails);
        }

        return certifications;
    }

    private HashMap<Integer, ArrayList<DocumentData>> getBonding() {
        HashMap<Integer, ArrayList<DocumentData>> bondings = new HashMap<>();

        for (int i = 1; i <= 2; i++) {
            ArrayList<DocumentData> bondingDetails = new ArrayList<>();
            DocumentData bondState = new DocumentData();
            bondState.setKey(KEY_STATE);
            bondState.setType(TYPE_DROPDOWN);
            bondState.setValue("");
            bondingDetails.add(bondState);

            DocumentData bondCity = new DocumentData();
            bondCity.setKey(KEY_CITY);
            bondCity.setType(TYPE_DROPDOWN);
            bondCity.setValue("");
            bondingDetails.add(bondCity);

            DocumentData bondNumber = new DocumentData();
            bondNumber.setKey(KEY_BOND_NUMBER);
            bondNumber.setType(TYPE_TEXT);
            bondNumber.setValue("");
            bondingDetails.add(bondNumber);

            DocumentData bondingDollarAmount = new DocumentData();
            bondingDollarAmount.setKey(KEY_BOND_DOLLAR_AMOUNT);
            bondingDollarAmount.setType(TYPE_TEXT);
            bondingDollarAmount.setValue("");
            bondingDetails.add(bondingDollarAmount);

            DocumentData bondAttachment = new DocumentData();
            bondAttachment.setKey(KEY_ATTACHMENT_BOND);
            bondAttachment.setType(TYPE_ATTACHMENT);
            bondAttachment.setValue("");
            bondingDetails.add(bondAttachment);

            bondings.put(i, bondingDetails);
        }

        return bondings;
    }

    private HashMap<Integer, ArrayList<DocumentData>> getBusinessLicensing() {
        HashMap<Integer, ArrayList<DocumentData>> businessLicensings = new HashMap<>();

        for (int i = 1; i <= 2; i++) {
            ArrayList<DocumentData> businessLicensingDetails = new ArrayList<>();
            DocumentData businessState = new DocumentData();
            businessState.setKey(KEY_STATE);
            businessState.setType(TYPE_DROPDOWN);
            businessState.setValue("");
            businessLicensingDetails.add(businessState);

            DocumentData businessLicenceNo = new DocumentData();
            businessLicenceNo.setKey(KEY_LICENSE_NUMBER);
            businessLicenceNo.setType(TYPE_TEXT);
            businessLicenceNo.setValue("");
            businessLicensingDetails.add(businessLicenceNo);

            DocumentData businessAttachment = new DocumentData();
            businessAttachment.setKey(KEY_ATTACHMENT_BUSINESS);
            businessAttachment.setType(TYPE_ATTACHMENT);
            businessAttachment.setValue("");
            businessLicensingDetails.add(businessAttachment);

            businessLicensings.put(i, businessLicensingDetails);
        }

        return businessLicensings;
    }
}

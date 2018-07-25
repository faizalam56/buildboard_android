package com.buildboard.modules.signup.contractor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.buildboard.dialogs.AddProfilePhotoDialog;
import com.buildboard.dialogs.UserTypeDialog;
import com.buildboard.http.DataManager;
import com.buildboard.modules.signup.SignUpActivity;
import com.buildboard.modules.signup.contractor.models.businessdocument.BusinessDocumentsRequest;
import com.buildboard.modules.signup.contractor.models.previouswork.PreviousWorkData;
import com.buildboard.modules.signup.contractor.models.previouswork.PreviousWorkRequest;
import com.buildboard.modules.signup.contractor.models.previouswork.PreviousWorks;
import com.buildboard.modules.signup.contractor.models.previouswork.TestimonialData;
import com.buildboard.utils.ProgressHelper;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PreviousWorkActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.title)
    TextView title;

    @BindString(R.string.previous_work)
    String stringPreviousWork;
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
        setContentView(R.layout.activity_previous_work);
        ButterKnife.bind(this);

        title.setText(stringPreviousWork);
        setTermsServiceText();
        getIntentData();
//        storePrevWork();
    }

    @OnClick(R.id.button_next)
    void nextTapped() {
        AddProfilePhotoDialog addProfilePhotoDialog = new AddProfilePhotoDialog();
        addProfilePhotoDialog.showDialog(this, new AddProfilePhotoDialog.IAddProfileCallback() {
            @Override
            public void onImageSelection() {

            }

            @Override
            public void onSaveImage() {

            }
        });
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
            Toast.makeText(PreviousWorkActivity.this, stringTermsOfService, Toast.LENGTH_SHORT).show();
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
            Toast.makeText(PreviousWorkActivity.this, stringPrivacyPolicy, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(getResources().getColor(R.color.colorGreen));
        }
    };

    private void storePrevWork() {
        ProgressHelper.start(this, stringPleaseWait);
        DataManager.getInstance().storePrevWork(this, getPreviousWorkRequest(), new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
            }
        });
    }

    private PreviousWorkRequest getPreviousWorkRequest() {

        PreviousWorks previousWorks = new PreviousWorks();
        previousWorks.setPreviousWork(getPreviousWorkData());
        previousWorks.setTestimonial(getTestimonialData());

        PreviousWorkRequest previousWorkRequest = new PreviousWorkRequest();
        previousWorkRequest.setPreviousWorks(previousWorks);
        previousWorkRequest.setId(mUserId);
//        previousWorkRequest.setId("38c0c020-8f01-11e8-b310-6778951ca517");

        return previousWorkRequest;
    }

    private HashMap<Integer, ArrayList<TestimonialData>> getTestimonialData() {
        HashMap<Integer, ArrayList<TestimonialData>> testimonials = new HashMap<>();

        for (int i = 1; i <= 2; i++) {
            ArrayList<TestimonialData> testimonialDetails = new ArrayList<>();
            TestimonialData testimonialData = new TestimonialData();
            testimonialData.setKey(KEY_NAME);
            testimonialData.setType(TYPE_TEXT);
            testimonialData.setValue("");
            testimonialDetails.add(testimonialData);

            testimonials.put(i, testimonialDetails);
        }

        return testimonials;
    }

    private HashMap<Integer, ArrayList<PreviousWorkData>> getPreviousWorkData() {
        HashMap<Integer, ArrayList<PreviousWorkData>> previousWorks = new HashMap<>();

        for (int i = 1; i <= 2; i++) {
            ArrayList<PreviousWorkData> previousWorkDetails = new ArrayList<>();
            PreviousWorkData testimonialData = new PreviousWorkData();
            testimonialData.setKey(KEY_NAME);
            testimonialData.setType(TYPE_TEXT);
            testimonialData.setValue(new ArrayList<String>());
            previousWorkDetails.add(testimonialData);

            previousWorks.put(i, previousWorkDetails);
        }

        return previousWorks;
    }
}

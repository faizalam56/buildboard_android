package com.buildboard.modules.signup.contractor.previouswork;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.buildboard.http.DataManager;
import com.buildboard.modules.signup.contractor.businessdocuments.models.DocumentData;
import com.buildboard.modules.signup.contractor.interfaces.IAddMoreCallback;
import com.buildboard.modules.signup.contractor.previouswork.adapters.PreviousWorkAdapter;
import com.buildboard.modules.signup.contractor.previouswork.adapters.TestimonialAdapter;
import com.buildboard.modules.signup.contractor.previouswork.models.PreviousWorkData;
import com.buildboard.modules.signup.contractor.previouswork.models.PreviousWorkRequest;
import com.buildboard.modules.signup.contractor.previouswork.models.PreviousWorks;
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
    @BindString(R.string.please_wait)
    String stringPleaseWait;

    @BindView(R.id.text_terms_of_service)
    BuildBoardTextView textTermsOfService;

    @BindView(R.id.recycler_previous_work)
    RecyclerView recyclerPreviousWork;
    @BindView(R.id.recycler_testimonial)
    RecyclerView recyclerTestimonial;

    private String mUserId = "";
    private PreviousWorkAdapter mPreviousWorkAdapter;
    private TestimonialAdapter mTestimonialAdapter;
    private HashMap<Integer, ArrayList<PreviousWorkData>> mPreviousWorks = new HashMap<>();
    private HashMap<Integer, ArrayList<DocumentData>> mTestimonials = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_work);
        ButterKnife.bind(this);

        title.setText(stringPreviousWork);
        setTermsServiceText();
        getIntentData();

        addTestimonialData();
        addPreviousWorkData();
        setTestimonialAdapter();
        setPreviousWorkAdapter();
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
        previousWorks.setPreviousWork(mPreviousWorks);
        previousWorks.setTestimonial(mTestimonials);

        PreviousWorkRequest previousWorkRequest = new PreviousWorkRequest();
        previousWorkRequest.setPreviousWorks(previousWorks);
        previousWorkRequest.setId(mUserId);
//        previousWorkRequest.setId("38c0c020-8f01-11e8-b310-6778951ca517");

        return previousWorkRequest;
    }

    private void addTestimonialData() {

        ArrayList<DocumentData> testimonialDetails = new ArrayList<>();
        DocumentData nameInfo = new DocumentData();
        nameInfo.setKey(KEY_NAME);
        nameInfo.setType(TYPE_TEXT);
        nameInfo.setValue("");
        testimonialDetails.add(nameInfo);

        DocumentData descriptionInfo = new DocumentData();
        descriptionInfo.setKey(KEY_DESCRIPTION);
        descriptionInfo.setType(TYPE_TEXT);
        descriptionInfo.setValue("");
        testimonialDetails.add(descriptionInfo);

        DocumentData workPerformed = new DocumentData();
        workPerformed.setKey(KEY_WORK_PERFORMED);
        workPerformed.setType(TYPE_TEXT);
        workPerformed.setValue("");
        testimonialDetails.add(workPerformed);

        mTestimonials.put(mTestimonials.size() + 1, testimonialDetails);
    }

    private void addPreviousWorkData() {

        ArrayList<PreviousWorkData> previousWorkDetails = new ArrayList<>();
        PreviousWorkData descriptionInfo = new PreviousWorkData();
        descriptionInfo.setKey(KEY_DESCRIPTION);
        descriptionInfo.setType(TYPE_TEXT);
        descriptionInfo.setValue(new ArrayList<String>());
        previousWorkDetails.add(descriptionInfo);

        PreviousWorkData attachmentInfo = new PreviousWorkData();
        attachmentInfo.setKey(KEY_ATTACHMENT);
        attachmentInfo.setType(TYPE_MULTIPLE_ATTACHMENT);
        attachmentInfo.setValue(new ArrayList<String>());
        previousWorkDetails.add(attachmentInfo);

        mPreviousWorks.put(mPreviousWorks.size() + 1, previousWorkDetails);
    }

    private void setPreviousWorkAdapter() {
        mPreviousWorkAdapter = new PreviousWorkAdapter(this, mPreviousWorks, new IAddMoreCallback() {
            @Override
            public void addMore() {
                addPreviousWorkData();
                mPreviousWorkAdapter.notifyDataSetChanged();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerPreviousWork.setLayoutManager(linearLayoutManager);
        recyclerPreviousWork.setAdapter(mPreviousWorkAdapter);
    }

    private void setTestimonialAdapter() {
        mTestimonialAdapter = new TestimonialAdapter(this, mTestimonials, new IAddMoreCallback() {
            @Override
            public void addMore() {
                addTestimonialData();
                mTestimonialAdapter.notifyDataSetChanged();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerTestimonial.setLayoutManager(linearLayoutManager);
        recyclerTestimonial.setAdapter(mTestimonialAdapter);
    }
}

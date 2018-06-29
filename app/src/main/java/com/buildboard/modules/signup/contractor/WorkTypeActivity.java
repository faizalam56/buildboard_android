package com.buildboard.modules.signup.contractor;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
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
import com.buildboard.http.DataManager;
import com.buildboard.modules.signup.adapter.WorkTypeAdapter;
import com.buildboard.modules.signup.models.contractortype.ContractorTypeDetail;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.Utils;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkTypeActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.recycler_work_type)
    RecyclerView recyclerWorkType;
    @BindString(R.string.sign_up)
    String stringSignUp;
    @BindString(R.string.terms_of_service)
    String stringTermsOfService;
    @BindString(R.string.privacy_policy_text)
    String stringPrivacyPolicy;
    @BindView(R.id.text_terms_of_service)
    BuildBoardTextView textTermsOfService;
    @BindString(R.string.type_of_contractor)
    String stringContractorType;
    @BindView(R.id.constraint_root)
    ConstraintLayout constraintRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_type);
        ButterKnife.bind(this);

        title.setText(stringSignUp);
        setTermsServiceText();
        getContractorList();
    }

    @OnClick(R.id.button_next)
    void nextTapped() {
        Intent intent = new Intent(this, BusinessDocumentsActivity.class);
        startActivity(intent);
    }

    public void setRecycler(ArrayList<ContractorTypeDetail> workTypeList) {
        WorkTypeAdapter workTypeAdapter = new WorkTypeAdapter(this, workTypeList);
        recyclerWorkType.setLayoutManager(new LinearLayoutManager(this));
        recyclerWorkType.setAdapter(workTypeAdapter);
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

    private void getContractorList() {

        ProgressHelper.start(this, getString(R.string.msg_please_wait));
        DataManager.getInstance().getContractorList(this, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                if (response == null) return;

                ArrayList<ContractorTypeDetail> workTypeList = (ArrayList<ContractorTypeDetail>) response;
                setRecycler(workTypeList);
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
                Utils.showError(WorkTypeActivity.this, constraintRoot, error);
            }
        });
    }

    ClickableSpan clickableSpanTermsService = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            Toast.makeText(WorkTypeActivity.this, stringTermsOfService, Toast.LENGTH_SHORT).show();
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
            Toast.makeText(WorkTypeActivity.this, stringPrivacyPolicy, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(getResources().getColor(R.color.colorGreen));
        }
    };
}

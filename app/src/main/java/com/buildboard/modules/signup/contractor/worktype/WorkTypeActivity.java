package com.buildboard.modules.signup.contractor.worktype;

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
import com.buildboard.customviews.BuildBoardButton;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.http.DataManager;
import com.buildboard.modules.signup.adapter.WorkTypeAdapter;
import com.buildboard.modules.signup.contractor.businessdocuments.BusinessDocumentsActivity;
import com.buildboard.modules.signup.models.contractortype.ContractorListResponse;
import com.buildboard.modules.signup.models.contractortype.ContractorTypeDetail;
import com.buildboard.modules.signup.models.contractortype.WorkTypeRequest;
import com.buildboard.preferences.AppPreference;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.Utils;
import com.buildboard.view.SnackBarFactory;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkTypeActivity extends AppCompatActivity implements AppConstant {

    private String mWorkTypeId = "";
    private ArrayList<String> selectedWorkType = new ArrayList<>();
    private boolean isContractor;
    private ArrayList<ContractorTypeDetail> workTypeList;
    private WorkTypeAdapter mWorkTypeAdapter;

    @BindView(R.id.recycler_work_type)
    RecyclerView recyclerWorkType;

    @BindString(R.string.work_type)
    String stringWorkType;
    @BindString(R.string.terms_of_service)
    String stringTermsOfService;
    @BindString(R.string.privacy_policy_text)
    String stringPrivacyPolicy;
    @BindString(R.string.type_of_contractor)
    String stringContractorType;
    @BindString(R.string.save)
    String stringSave;

    @BindView(R.id.text_terms_of_service)
    BuildBoardTextView textTermsOfService;
    @BindView(R.id.title)
    BuildBoardTextView title;

    @BindView(R.id.constraint_root)
    ConstraintLayout constraintRoot;

    @BindView(R.id.button_next)
    BuildBoardButton buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_type);
        ButterKnife.bind(this);

        title.setText(stringWorkType);
        getIntentData();
        setTermsServiceText();
        getContractorList();

        isContractor = AppPreference.getAppPreference(this).getBoolean(IS_CONTRACTOR);
        if (isContractor) {
            textTermsOfService.setVisibility(View.GONE);
            buttonNext.setText(stringSave);
        }
    }

    @OnClick(R.id.button_next)
    void nextTapped() {
        if (selectedWorkType != null && !selectedWorkType.isEmpty()) {
            WorkTypeRequest workTypeRequest = getWorkTypeRequest(selectedWorkType);
            saveWorkType(workTypeRequest);
        } else {
            SnackBarFactory.createSnackBar(this, constraintRoot, getString(R.string.error_work_type_not_selected));
        }
    }

    public void setRecycler(ArrayList<ContractorTypeDetail> workTypeList) {
        mWorkTypeAdapter = new WorkTypeAdapter(this, workTypeList, new WorkTypeAdapter.OnItemCheckListener() {
            @Override
            public void onItemChecked(String selectWorkId) {
                selectedWorkType.add(selectWorkId);
            }

            @Override
            public void onItemUnChecked(String selectWorkId) {
                selectedWorkType.remove(selectWorkId);
            }
        });

        recyclerWorkType.setLayoutManager(new LinearLayoutManager(this));
        recyclerWorkType.setAdapter(mWorkTypeAdapter);
    }

    private void getIntentData() {
        if (getIntent().hasExtra(INTENT_WORK_TYPE_ID))
            mWorkTypeId = getIntent().getStringExtra(INTENT_WORK_TYPE_ID);
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

                workTypeList = (ArrayList<ContractorTypeDetail>) response;
                setRecycler(workTypeList);
                if (isContractor)
                    getContractorWorkType();
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
                Utils.showError(WorkTypeActivity.this, constraintRoot, error);
            }
        });
    }

    private void saveWorkType(WorkTypeRequest workTypeRequest) {

        ProgressHelper.start(this, getString(R.string.msg_please_wait));
        DataManager.getInstance().saveWorkType(this, workTypeRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                if (response == null) return;

                Intent intent = new Intent(WorkTypeActivity.this, BusinessDocumentsActivity.class);
                intent.putExtra(INTENT_USER_ID, mWorkTypeId);
                startActivity(intent);
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
                Utils.showError(WorkTypeActivity.this, constraintRoot, error);
            }
        });
    }

    private WorkTypeRequest getWorkTypeRequest(ArrayList<String> selectedType) {
        WorkTypeRequest workTypeRequest = new WorkTypeRequest();
        workTypeRequest.setId(mWorkTypeId);
        workTypeRequest.setProjectTypeId(selectedType);

        return workTypeRequest;
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

    private void getContractorWorkType() {
        ProgressHelper.start(this, getString(R.string.msg_please_wait));
        DataManager.getInstance().getContractorWorkType(this, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                if (response == null) return;
                ContractorListResponse contractorListResponse = (ContractorListResponse) response;
                setWorkTypeData(contractorListResponse.getDatas());
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
                Utils.showError(WorkTypeActivity.this, constraintRoot, error);
            }
        });
    }

    private void setWorkTypeData(ArrayList<ContractorTypeDetail> contractorTypeDetails) {
        for (ContractorTypeDetail contractorTypeDetail : workTypeList){
            for (ContractorTypeDetail selectedContractorType : contractorTypeDetails){
                if(contractorTypeDetail.getId().equalsIgnoreCase(selectedContractorType.getId()))
                    contractorTypeDetail.setSelected(true);
            }
        }
        mWorkTypeAdapter.notifyDataSetChanged();
    }
}

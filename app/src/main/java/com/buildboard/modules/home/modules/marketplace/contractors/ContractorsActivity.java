package com.buildboard.modules.home.modules.marketplace.contractors;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.fonts.FontHelper;
import com.buildboard.http.DataManager;
import com.buildboard.modules.home.modules.marketplace.contractor_projecttype.models.ContractorByProjectTypeData;
import com.buildboard.modules.home.modules.marketplace.contractor_projecttype.models.ContractorByProjectTypeListData;
import com.buildboard.modules.home.modules.marketplace.contractors.adapters.ContractorsAdapter;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.Utils;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.buildboard.utils.Utils.showProgressColor;

public class ContractorsActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_contractors)
    RecyclerView recyclerContractors;
    @BindView(R.id.text_project_type)
    BuildBoardTextView textProjectType;
    @BindView(R.id.text_nodata)
    TextView textNodata;
    @BindView(R.id.constraint_root)
    ConstraintLayout constraintRoot;
    @BindView(R.id.title)
    BuildBoardTextView textTitle;

    @BindString(R.string.contractors)
    String stringContractors;
    @BindView(R.id.progress_bar_service)
    ProgressBar progressBar;

    private ArrayList<ContractorByProjectTypeListData> mContractorList = new ArrayList<>();
    private ContractorsAdapter mContractorsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractors);
        ButterKnife.bind(this);

        showProgressColor(this, progressBar);
        textTitle.setText(stringContractors);
        setFont();
        getIntentData();
    }

    private void getIntentData() {
        if (getIntent().hasExtra(DATA)) {
            getContractorByProjectType(getIntent().getStringExtra(DATA), ROLE_CONTRACTOR);
        }

        if (getIntent().hasExtra(INTENT_TITLE)) {
            textProjectType.setText(getIntent().getStringExtra(INTENT_TITLE));
        }
    }

    private void setContractorsAdapter() {
        mContractorsAdapter = new ContractorsAdapter(this, mContractorList);
        recyclerContractors.setLayoutManager(new LinearLayoutManager(this));
        recyclerContractors.setAdapter(mContractorsAdapter);
    }

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, textProjectType);
    }

    private void getContractorByProjectType(String contractorTypeId, String role) {
        ProgressHelper.showProgressBar(this, progressBar);
        DataManager.getInstance().getContractorByProjectType(this, contractorTypeId, role,  new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.hideProgressBar();
                handleSuccessResponse(response);
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.hideProgressBar();
                Utils.showError(ContractorsActivity.this, constraintRoot, error);
            }
        });
    }

    private void handleSuccessResponse(Object response) {
        if (response == null) return;
        ContractorByProjectTypeData contractorByProjectTypeData = (ContractorByProjectTypeData) response;
        if (contractorByProjectTypeData.getData().size() > 0) {
            mContractorList.addAll(contractorByProjectTypeData.getData());
            setContractorsAdapter();
        } else {
            textNodata.setVisibility(View.VISIBLE);
        }
    }
}
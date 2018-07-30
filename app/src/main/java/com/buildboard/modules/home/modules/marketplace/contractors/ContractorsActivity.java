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
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
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

public class ContractorsActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycler_contractors)
    RecyclerView recyclerContractors;

    @BindView(R.id.text_project_type)
    TextView textProjectType;
    @BindView(R.id.text_nodata)
    TextView textNodata;

    @BindView(R.id.edit_search_by_name)
    EditText editSearchByName;

    @BindView(R.id.constraint_root)
    ConstraintLayout constraintRoot;

    @BindString(R.string.contractors)
    String stringContractors;

    private ArrayList<ContractorByProjectTypeListData> mContractorList = new ArrayList<>();
    private ArrayList<ContractorByProjectTypeListData> mSearchContractorList = new ArrayList<>();
    private ContractorsAdapter mContractorsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractors);
        ButterKnife.bind(this);
        toolbar.setTitle(stringContractors);
        setFont();
        getIntentData();
        editSearchByName.addTextChangedListener(searchTextWatcher);
    }

    private void getIntentData() {
        if (getIntent().hasExtra(DATA)) {
            getContractorByProjectType(getIntent().getStringExtra(DATA));
        }

        if (getIntent().hasExtra(INTENT_TITLE)) {
            textProjectType.setText(getIntent().getStringExtra(INTENT_TITLE));
        }
    }

    TextWatcher searchTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mSearchContractorList.clear();
            if (s.length() == 0) {
                mSearchContractorList.addAll(mContractorList);
                mContractorsAdapter.notifyDataSetChanged();
                return;
            }

            for (ContractorByProjectTypeListData contractors : mContractorList) {
                if (contractors.getFirstName().contains(s))
                    mSearchContractorList.add(contractors);
            }
            mContractorsAdapter.notifyDataSetChanged();
        }
    };

    private void setContractorsAdapter() {
        mContractorsAdapter = new ContractorsAdapter(this, mSearchContractorList);
        recyclerContractors.setLayoutManager(new LinearLayoutManager(this));
        recyclerContractors.setAdapter(mContractorsAdapter);
    }

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, textProjectType);
        FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, editSearchByName);
    }

    private void getContractorByProjectType(String contractorTypeId) {
        ProgressHelper.start(this, getString(R.string.msg_please_wait));
        DataManager.getInstance().getContractorByProjectType(this, contractorTypeId, 1, 2, 30, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                handleSuccessResponse(response);
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
                Utils.showError(ContractorsActivity.this, constraintRoot, error);
            }
        });
    }

    private void handleSuccessResponse(Object response) {
        if (response == null) return;

        ContractorByProjectTypeData contractorByProjectTypeData = (ContractorByProjectTypeData) response;
        if (contractorByProjectTypeData.getDatas().size() > 0) {
            mContractorList.addAll(contractorByProjectTypeData.getDatas());
            mSearchContractorList.addAll(contractorByProjectTypeData.getDatas());
            setContractorsAdapter();
        } else {
            textNodata.setVisibility(View.VISIBLE);
            editSearchByName.setVisibility(View.GONE);
        }
    }
}
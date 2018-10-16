package com.buildboard.modules.home.modules.marketplace.contractors;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.modules.home.modules.marketplace.contractors.adapters.RvAdapterContractorProjectDetailRequirement;
import com.buildboard.modules.home.modules.marketplace.contractors.models.ProjectsDetailData;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContractorProjectsRequirementsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    BuildBoardTextView toolbarTitle;
    @BindView(R.id.rv_project_reqirment_data)
    RecyclerView rvProjectRequirement;
    private ProjectsDetailData mNearByProjectDetailsData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirements);
        ButterKnife.bind(this);
        toolbarTitle.setText(AppConstant.TEXT_REQUIREMENTS);
        mNearByProjectDetailsData = (ProjectsDetailData) getIntent().getParcelableExtra("projectDetailsData");

        RvAdapterContractorProjectDetailRequirement adapter = new RvAdapterContractorProjectDetailRequirement(mNearByProjectDetailsData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvProjectRequirement.setLayoutManager(layoutManager);
        rvProjectRequirement.setAdapter(adapter);
    }
}

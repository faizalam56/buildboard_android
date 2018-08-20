package com.buildboard.modules.home.modules.projects;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.fonts.FontHelper;
import com.buildboard.http.DataManager;
import com.buildboard.modules.home.modules.marketplace.contractor_projecttype.models.ContractorByProjectTypeData;
import com.buildboard.modules.home.modules.marketplace.contractor_projecttype.models.ContractorByProjectTypeListData;
import com.buildboard.modules.home.modules.marketplace.contractors.ContractorsActivity;
import com.buildboard.modules.home.modules.marketplace.contractors.adapters.ContractorsAdapter;
import com.buildboard.modules.home.modules.marketplace.models.projectbyprojecttype.ProjectByProjectTypeData;
import com.buildboard.modules.home.modules.marketplace.models.projectbyprojecttype.ProjectData;
import com.buildboard.modules.home.modules.profile.consumer.models.reviews.Project;
import com.buildboard.modules.home.modules.projects.adapters.ProjectsAdapter;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.Utils;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.buildboard.utils.Utils.showProgressColor;

public class ProjectsActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_projects)
    RecyclerView recyclerProjects;
    @BindView(R.id.text_no_data)
    TextView textNoData;
    @BindView(R.id.constraint_root)
    ConstraintLayout constraintRoot;
    @BindView(R.id.title)
    BuildBoardTextView textTitle;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    
    @BindString(R.string.title_projects)
    String stringProjects;

    private ArrayList<ProjectData> mProjectsData = new ArrayList<>();
    private ProjectsAdapter mProjectsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        ButterKnife.bind(this);

        showProgressColor(this, progressBar);
        textTitle.setText(stringProjects);
        getIntentData();
    }

    private void getIntentData() {
        if (getIntent().hasExtra(DATA)) {
            getProjectsByProjectType(getIntent().getStringExtra(DATA));
        } 
    }

    private void setContractorsAdapter() {
        mProjectsAdapter = new ProjectsAdapter(this, mProjectsData);
        recyclerProjects.setLayoutManager(new LinearLayoutManager(this));
        recyclerProjects.setAdapter(mProjectsAdapter);
    }

    private void getProjectsByProjectType(String projectTypeId) {
        ProgressHelper.showProgressBar(this, progressBar);
        DataManager.getInstance().getProjectByProjectType(this, projectTypeId,  new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.hideProgressBar();
                handleSuccessResponse(response);
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.hideProgressBar();
                Utils.showError(ProjectsActivity.this, constraintRoot, error);
            }
        });
    }

    private void handleSuccessResponse(Object response) {
        if (response == null) return;

        ProjectByProjectTypeData projectByProjectTypeData = (ProjectByProjectTypeData) response;
        if (projectByProjectTypeData.getData().size() > 0) {
            mProjectsData.addAll(projectByProjectTypeData.getData());
            setContractorsAdapter();
        } else {
            textNoData.setVisibility(View.VISIBLE);
        }
    }
}

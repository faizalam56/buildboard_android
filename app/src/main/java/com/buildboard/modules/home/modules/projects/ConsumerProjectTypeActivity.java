package com.buildboard.modules.home.modules.projects;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.buildboard.R;
import com.buildboard.http.DataManager;
import com.buildboard.interfaces.IRecyclerItemClickListener;
import com.buildboard.modules.home.modules.projects.adapters.ConsumerProjectTypeAdapter;
import com.buildboard.modules.home.modules.projects.models.ProjectAllType;
import com.buildboard.modules.home.modules.projects.models.ProjectFormDetails;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.view.SnackBarFactory;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.buildboard.constants.AppConstant.INTENT_PROJECT_TYPE_DATA;

public class ConsumerProjectTypeActivity extends AppCompatActivity
        implements IRecyclerItemClickListener{

    private ArrayList<ProjectAllType> projectsData;

    @BindView(R.id.recycler_all_project_type)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.container)
    ConstraintLayout containers;

    @BindString(R.string.create_new_project)
    String stringCreateNewProjectText;
    @BindString(R.string.no_task)
    String stringNoTaskAvailable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_select_all_type_project);
        ButterKnife.bind(this);

        title.setText(stringCreateNewProjectText);

        if (ConnectionDetector.isNetworkConnected(this)) {
            getAllTypeOfProject();
        } else {
            ConnectionDetector.createSnackBar(this, containers);
        }
    }

    private void getAllTypeOfProject() {
        ProgressHelper.showProgressBar(this, progressBar);
        DataManager.getInstance().getAllTypeOfProjectList(this, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.hideProgressBar();
                projectsData = (ArrayList<ProjectAllType>) response;
                setProjectsRecycler(projectsData);
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
            }
        });
    }

    private void setProjectsRecycler(ArrayList<ProjectAllType> projectsData) {
        ConsumerProjectTypeAdapter mConsumerProjectTypeAdapter = new ConsumerProjectTypeAdapter(this, projectsData,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mConsumerProjectTypeAdapter);
    }

    @Override
    public void onItemClick(View view, int position, Object data) {
        if(ConnectionDetector.isNetworkConnected(this)) {
            ProjectAllType projectType = (ProjectAllType) data;
            getAllProjectDetails(projectType.getId());
        } else {
            ConnectionDetector.createSnackBar(this, containers);
        }
    }

   private void getAllProjectDetails(final String selectedProjectId) {
        ProgressHelper.showProgressBar(this, progressBar);
        DataManager.getInstance().getProjectDetails(this, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.hideProgressBar();
                if(response!=null){
                   getProjectById(selectedProjectId);
                }
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.hideProgressBar();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getProjectById(String id) {
        ProgressHelper.showProgressBar(this, progressBar);
        DataManager.getInstance().getSelectedProjectById(this, id, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.hideProgressBar();
                if (response != null) {
                    ProjectFormDetails projectFormDetails = (ProjectFormDetails) response;
                    if(projectFormDetails.getForm() != null) {
                        for(int i =0; i< projectFormDetails.getForm().size() ; i++) {
                            if(projectFormDetails.getForm().get(i).getTasks() != null) {
                                if (projectFormDetails.getType().equals(getString(R.string.other))) {
                                    openActivity(projectFormDetails, ConsumerProjectTypeDetailsActivity.class);
                                } else {
                                    openActivity(projectFormDetails, ConsumerCreateProjectActivity.class);
                                }
                            } else {
                                SnackBarFactory.createSnackBar(ConsumerProjectTypeActivity.this,containers,stringNoTaskAvailable);
                            }
                        }
                    } else {
                        SnackBarFactory.createSnackBar(ConsumerProjectTypeActivity.this,containers,stringNoTaskAvailable);
                    }
                }
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.hideProgressBar();
            }
        });
    }

    public void openActivity(ProjectFormDetails projectFormDetails, Class classToReplace){
        Intent intent = new Intent(ConsumerProjectTypeActivity.this, classToReplace);
        intent.putExtra(INTENT_PROJECT_TYPE_DATA, projectFormDetails);
        startActivity(intent);
    }
}

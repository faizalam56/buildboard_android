package com.buildboard.modules.home.modules.projects;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.interfaces.IRecyclerItemClickListener;
import com.buildboard.modules.home.modules.projects.adapters.ConsumerSelectedTaskAdapter;
import com.buildboard.modules.home.modules.projects.models.ProjectFormDetails;
import com.buildboard.modules.home.modules.projects.models.Task;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.view.SimpleDividerItemDecoration;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.buildboard.constants.AppConstant.EXTERIOR;
import static com.buildboard.constants.AppConstant.INTENT_PROJECT_TYPE_DATA;
import static com.buildboard.constants.AppConstant.INTENT_SELECTED_CATEGORY;
import static com.buildboard.constants.AppConstant.INTERIOR;
import static com.buildboard.constants.AppConstant.TASK;

public class ConsumerCreateProjectActivity extends AppCompatActivity implements IRecyclerItemClickListener {

    @BindView(R.id.recycler_project_task)
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
        setContentView(R.layout.fragment_consumer_create_project);
        ButterKnife.bind(this);

        title.setText(stringCreateNewProjectText);

        if (ConnectionDetector.isNetworkConnected(this)) {
            ProgressHelper.showProgressBar(this, progressBar);
            ProjectFormDetails mProjectAllTypesData = getIntent().getParcelableExtra(INTENT_PROJECT_TYPE_DATA);
            String mSelectedCategory = getIntent().getStringExtra(INTENT_SELECTED_CATEGORY);
            showTask(mProjectAllTypesData, mSelectedCategory);
        } else {
            ConnectionDetector.createSnackBar(this, containers);
        }
    }

    private void showTask(ProjectFormDetails mProjectAllTypesData, String mSelectedCategory) {
        if (mSelectedCategory != null) {
            for (int i = 0; i < mProjectAllTypesData.getForm().size(); i++) {
                if (mProjectAllTypesData.getForm().get(i).getCategory().equalsIgnoreCase(mSelectedCategory)) {
                    if(mProjectAllTypesData.getForm().get(i).getTasks() !=null) {
                        getAllTask(mProjectAllTypesData.getForm().get(i).getTasks());
                        break;
                    }
                }
            }
        } else {
            for (int i = 0; i < mProjectAllTypesData.getForm().size(); i++) {
                if (mProjectAllTypesData.getForm().get(i).getCategory().equalsIgnoreCase(INTERIOR)) {
                   if(mProjectAllTypesData.getForm().get(i).getTasks() !=null) {
                       getAllTask(mProjectAllTypesData.getForm().get(i).getTasks());
                   }
                } else if (mProjectAllTypesData.getForm().get(i).getCategory().equalsIgnoreCase(EXTERIOR)) {
                    if(mProjectAllTypesData.getForm().get(i).getTasks() !=null) {
                        getAllTask(mProjectAllTypesData.getForm().get(i).getTasks());
                    }
                }
            }
        }
    }

    private void getAllTask(List<Task> tasks) {
        if(tasks != null){
            setProjectsRecycler(tasks);
        }
    }

    private void setProjectsRecycler(List<Task> tasks) {
        ProgressHelper.hideProgressBar();
        ConsumerSelectedTaskAdapter consumerSelectedTaskAdapter = new ConsumerSelectedTaskAdapter(this, tasks, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        recyclerView.setAdapter(consumerSelectedTaskAdapter);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    public void onItemClick(View view, int position, Object data) {
        if (ConnectionDetector.isNetworkConnected(this)) {
            Task task = (Task) data;
            Intent intent = new Intent(this, ConsumerWindowActivity.class);
            intent.putExtra(TASK, task);
            startActivity(intent);
        } else {
            ConnectionDetector.createSnackBar(this, containers);
        }
    }
}

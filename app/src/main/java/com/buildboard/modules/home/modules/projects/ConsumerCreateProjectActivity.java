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

import com.buildboard.R;
import com.buildboard.interfaces.IRecyclerItemClickListener;
import com.buildboard.modules.home.modules.projects.adapters.ConsumerSelectedTaskAdapter;
import com.buildboard.modules.home.modules.projects.models.ProjectFormDetails;
import com.buildboard.modules.home.modules.projects.models.Task;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.ProgressHelper;

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

    private ProjectFormDetails mProjectAllTypesData;
    private String mSelectedCategory;
    private ConstraintLayout containers;

    @BindView(R.id.recycler_project_task)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.title)
    TextView title;

    @BindString(R.string.create_new_project)
    String stringCreateNewProjectText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_consumer_create_project);
        ButterKnife.bind(this);

        title.setText(stringCreateNewProjectText);

        if (ConnectionDetector.isNetworkConnected(this)) {
            ProgressHelper.showProgressBar(this, progressBar);
            mProjectAllTypesData = getIntent().getParcelableExtra(INTENT_PROJECT_TYPE_DATA);
            mSelectedCategory = getIntent().getStringExtra(INTENT_SELECTED_CATEGORY);
            if (mSelectedCategory != null) {
                for (int i = 0; i < mProjectAllTypesData.getForm().size(); i++) {
                    if (mProjectAllTypesData.getForm().get(i).getCategory().equalsIgnoreCase(mSelectedCategory)) {
                        if (mProjectAllTypesData.getForm().get(i).getTasks().get(i).getTask() == null) {
                            Intent intent = new Intent(this, ConsumerWindowActivity.class);
                            startActivity(intent);
                        } else {
                            getAllTask(mProjectAllTypesData.getForm().get(i).getTasks());
                            break;
                        }
                    }
                }

            } else {
                for (int i = 0; i < mProjectAllTypesData.getForm().size(); i++) {
                    if (mProjectAllTypesData.getForm().get(i).getCategory().equalsIgnoreCase(INTERIOR)) {
                        if (mProjectAllTypesData.getForm().get(i).getTasks().get(i).getTask() == null) {
                            Intent intent = new Intent(ConsumerCreateProjectActivity.this, ConsumerWindowActivity.class);
                            startActivity(intent);
                        } else {
                            getAllTask(mProjectAllTypesData.getForm().get(i).getTasks());
                            break;
                        }
                    } else if (mProjectAllTypesData.getForm().get(i).getCategory().equalsIgnoreCase(EXTERIOR)) {
                        if (mProjectAllTypesData.getForm().get(i).getTasks().get(i).getTask() == null) {
                            Intent intent = new Intent(ConsumerCreateProjectActivity.this, ConsumerWindowActivity.class);
                            startActivity(intent);
                        } else {
                            getAllTask(mProjectAllTypesData.getForm().get(i).getTasks());
                            break;
                        }
                    }
                }
            }
        } else {
            ConnectionDetector.createSnackBar(this, containers);
        }
    }

    private void getAllTask(List<Task> tasks) {
        if(tasks !=null){
            setProjectsRecycler(tasks);
        }
    }

    private void setProjectsRecycler(List<Task> tasks) {
        ProgressHelper.hideProgressBar();
        ConsumerSelectedTaskAdapter consumerSelectedTaskAdapter = new ConsumerSelectedTaskAdapter(this, tasks, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(consumerSelectedTaskAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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

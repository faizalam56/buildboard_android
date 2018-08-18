package com.buildboard.modules.home.modules.projects;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.buildboard.R;
import com.buildboard.modules.home.modules.projects.adapters.QuestionAdapter;
import com.buildboard.modules.home.modules.projects.models.ProjectFormResponse;
import com.buildboard.modules.home.modules.projects.models.ProjectTypeQuestion;
import com.buildboard.modules.home.modules.projects.models.Task;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.ProgressHelper;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.buildboard.constants.AppConstant.QUESTION_LOCAL_JSON;
import static com.buildboard.utils.Utils.loadJsonFromAsset;


public class CreateProjectScheduleLocationFragment extends Fragment {
    private Unbinder unbinder;
    private Task task;
    private List<ProjectTypeQuestion> mQuestionList;
    private QuestionAdapter mQuestionAdapter;

    @BindView(R.id.container)
    ConstraintLayout container;
    @BindView(R.id.recycler_question)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    public static CreateProjectScheduleLocationFragment newInstance() {
        return new CreateProjectScheduleLocationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_project_schedule_location, container, false);
        unbinder = ButterKnife.bind(this, view);

        if(ConnectionDetector.isNetworkConnected(getActivity())){
            ProgressHelper.showProgressBar(getActivity(), progressBar);
            Gson gson = new Gson();
            String json =  loadJsonFromAsset(getActivity(), QUESTION_LOCAL_JSON);
            ProjectFormResponse mProjectFromResponse = gson.fromJson(json, ProjectFormResponse.class);
            List<Task> tasks =mProjectFromResponse.getData().get(0).getForm().get(0).getTasks();
            if (tasks.get(0).getTask() !=null && tasks.get(0).getQuestions() != null) {
                ProgressHelper.hideProgressBar();
                mQuestionList = tasks.get(0).getQuestions();
                setProjectsRecycler(mQuestionList);
            }
        } else {
            ConnectionDetector.createSnackBar(getActivity(), container);
        }

        return view;
    }

    @OnClick(R.id.button_next)
    public void nextButtonTapped(){

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setProjectsRecycler(List<ProjectTypeQuestion> questionList) {
        ProgressHelper.hideProgressBar();
        mQuestionAdapter = new QuestionAdapter(getActivity(), questionList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mQuestionAdapter);
    }
}

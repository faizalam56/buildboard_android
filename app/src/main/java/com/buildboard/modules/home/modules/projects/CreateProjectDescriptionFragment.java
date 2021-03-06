package com.buildboard.modules.home.modules.projects;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.buildboard.R;
import com.buildboard.dialogs.PopUpHelper;
import com.buildboard.modules.home.modules.projects.adapters.QuestionAdapter;
import com.buildboard.modules.home.modules.projects.models.ProjectTypeQuestion;
import com.buildboard.modules.home.modules.projects.models.Task;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.ProgressHelper;

import java.util.List;
import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.buildboard.constants.AppConstant.TASK;

public class CreateProjectDescriptionFragment extends Fragment{

    private Unbinder unbinder;
    private List<ProjectTypeQuestion> mQuestionList;
    private QuestionAdapter mQuestionAdapter;

    @BindView(R.id.recycler_question)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.container)
    ConstraintLayout constraintLayout;

    @BindString(R.string.msg_creat_project_alert)
    String showAlertMsg;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_project_description, container, false);
        unbinder = ButterKnife.bind(this, view);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Task task = bundle.getParcelable(TASK);
            if (task !=null && task.getQuestions() != null) {
                mQuestionList = task.getQuestions();
                setProjectsRecycler(mQuestionList);
            }
        }

        return view;
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
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mQuestionAdapter);
    }

    @OnClick(R.id.button_next)
    public void nextButtonTapped() {
        if (ConnectionDetector.isNetworkConnected(getActivity())){
            Map<String, List<String>> answerListMap = mQuestionAdapter.getAnswer();
            if (!validateAnswerList(mQuestionAdapter.getAnswer())) {
                for (Map.Entry<String,List<String>> entry : answerListMap.entrySet())
                Toast.makeText(getActivity(), "Key = " + entry.getKey() +
                        ", Value = " + entry.getValue(), Toast.LENGTH_SHORT).show();
            } else {
                PopUpHelper.showInfoAlertPopup(getActivity(), showAlertMsg, new PopUpHelper.InfoPopupListener() {
                    @Override
                    public void onConfirm() {
                    }
                });
            }
        } else {
            ConnectionDetector.createSnackBar(getActivity(), constraintLayout);
        }
    }

    public boolean isEmptyStringArray(String array[]){
        for (String anArray : array) {
            if (anArray != null) {
                return false;
            }
        }

        return true;
    }

    public boolean validateAnswerList(final Map<String, List<String>> answerListMap) {
        if (answerListMap.keySet() != null && answerListMap.values() != null) {
            if (answerListMap.keySet().size() == answerListMap.values().size()) {
                return true;
            }
        }
        return false;
    }
}

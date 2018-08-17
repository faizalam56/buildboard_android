package com.buildboard.modules.home.modules.projects.adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buildboard.R;
import com.buildboard.customviews.BuildBoardEditText;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.modules.home.modules.projects.models.ProjectTypeQuestion;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.buildboard.utils.Utils.setStarToLabel;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private List<ProjectTypeQuestion> questionList;
    private Context mContext;
    private String[] stringArrayAnswer;

    public QuestionAdapter(FragmentActivity activity, List<ProjectTypeQuestion> questionList) {
        this.mContext = activity;
        this.questionList = questionList;
        this.stringArrayAnswer = new String[questionList.size()];
    }

    public String[] getAnswer() {
        return stringArrayAnswer;
    }

    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_questions, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionAdapter.ViewHolder holder, int position) {
        holder.setData(questionList.get(position));
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_question)
        BuildBoardTextView textQuestion;
        @BindView(R.id.edit_answer)
        BuildBoardEditText editAnswer;
        @BindView(R.id.container_root)
        ConstraintLayout constraintLayout;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }

        public void setData(ProjectTypeQuestion question) {
            if(!TextUtils.isEmpty(question.getQuestion())) {
                textQuestion.setText(setStarToLabel(question.getQuestion()));
            }

            if(!TextUtils.isEmpty(question.getQuestionType())) {
                editAnswer.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        stringArrayAnswer[getAdapterPosition()] = charSequence.toString();
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                });
            }
        }
    }
}
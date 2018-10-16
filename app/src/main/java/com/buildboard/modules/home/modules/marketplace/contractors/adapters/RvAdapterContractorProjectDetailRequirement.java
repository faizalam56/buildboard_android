package com.buildboard.modules.home.modules.marketplace.contractors.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.modules.home.modules.marketplace.contractors.models.ProjectForm;
import com.buildboard.modules.home.modules.marketplace.contractors.models.ProjectsDetailData;
import com.buildboard.modules.home.modules.marketplace.contractors.models.Question;
import com.buildboard.modules.home.modules.marketplace.contractors.models.Task;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by faiz on 23/8/18.
 */

public class RvAdapterContractorProjectDetailRequirement extends RecyclerView.Adapter<RvAdapterContractorProjectDetailRequirement.ViewHolderRequirement> {

    private ProjectsDetailData mProjectDetailsData;
    public RvAdapterContractorProjectDetailRequirement(ProjectsDetailData mProjectDetailsData){
        this.mProjectDetailsData = mProjectDetailsData;
    }
    @Override
    public ViewHolderRequirement onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_requirement,parent,false);
        return new ViewHolderRequirement(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolderRequirement holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {

        return 1;
    }

    class ViewHolderRequirement extends RecyclerView.ViewHolder{

        @BindView(R.id.text_question)
        TextView tvQuestion;
        @BindView(R.id.text_answer)
        TextView tvAnswer;
        public ViewHolderRequirement(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        private void setData(int position){
            List<ProjectForm> projectFormList=mProjectDetailsData.getProjectForm();
            List<Task> taskList=projectFormList.get(position).getTasks();
            List<Question> questionList=taskList.get(position).getQuestions();
            String question=questionList.get(position).getQuestion();
            tvQuestion.setText(question);

           /* List<String> answer=questionList.get(position).getAnswer();
            tvAnswer.setText(answer.get(position));*/
        }
    }
}

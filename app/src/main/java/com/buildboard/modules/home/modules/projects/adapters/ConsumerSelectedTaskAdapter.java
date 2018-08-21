package com.buildboard.modules.home.modules.projects.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buildboard.R;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.interfaces.IRecyclerItemClickListener;
import com.buildboard.modules.home.modules.projects.models.Task;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConsumerSelectedTaskAdapter extends RecyclerView.Adapter<ConsumerSelectedTaskAdapter.ViewHolder> {

    private List<Task> taskList;
    private Context mContext;
    private IRecyclerItemClickListener mClickListener;

    public ConsumerSelectedTaskAdapter(Activity activity, List<Task> taskList, IRecyclerItemClickListener clickListener) {
        this.mContext = activity;
        this.taskList = taskList;
        mClickListener = clickListener;
    }

    @Override
    public ConsumerSelectedTaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_select_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ConsumerSelectedTaskAdapter.ViewHolder holder, int position) {
        holder.setData(taskList.get(position));
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_task)
        BuildBoardTextView textTask;
        @BindView(R.id.container_root)
        ConstraintLayout constraintLayout;

        @BindString(R.string.repair)
        String stringRepairText;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }

        public void setData(Task task) {
            if (task != null) {
                textTask.setText(!TextUtils.isEmpty(task.getTask()) ? task.getTask() : stringRepairText);
            }
        }

        @OnClick(R.id.container_root)
        void rowTapped(View view) {
            mClickListener.onItemClick(view, getAdapterPosition(), taskList.get(getAdapterPosition()));
        }
    }
}
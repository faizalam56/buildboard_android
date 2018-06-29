package com.buildboard.modules.signup.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.buildboard.R;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.modules.signup.models.contractortype.ContractorTypeDetail;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkTypeAdapter extends RecyclerView.Adapter<WorkTypeAdapter.WorkTypeViewHolder> {

    private Activity mActivity;
    private ArrayList<ContractorTypeDetail> mWorkTypeList;
    private LayoutInflater mLayoutInflater;

    public WorkTypeAdapter(Activity activity, ArrayList<ContractorTypeDetail> workTypeList) {
        mActivity = activity;
        mWorkTypeList = workTypeList;
        mLayoutInflater = LayoutInflater.from(mActivity);
    }

    @Override
    public WorkTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_work_type, parent, false);
        return new WorkTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WorkTypeViewHolder holder, int position) {
        holder.bindData(mWorkTypeList.get(position));
    }

    @Override
    public int getItemCount() {
        return mWorkTypeList.size();
    }

    class WorkTypeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.checkbox_work_type)
        CheckBox checkBox;
        @BindView(R.id.text_work_name)
        BuildBoardTextView textWorkName;

        public WorkTypeViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindData(ContractorTypeDetail contractorTypeDetail) {
            textWorkName.setText(contractorTypeDetail.getTitle());
        }
    }
}

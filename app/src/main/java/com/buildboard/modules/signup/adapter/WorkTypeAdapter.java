package com.buildboard.modules.signup.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
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
import butterknife.OnClick;

public class WorkTypeAdapter extends RecyclerView.Adapter<WorkTypeAdapter.WorkTypeViewHolder> {

    private final SparseBooleanArray itemPositionArray = new SparseBooleanArray();
    private Activity mActivity;
    private ArrayList<ContractorTypeDetail> mWorkTypeList;
    private LayoutInflater mLayoutInflater;
    private OnItemCheckListener mOnItemCheckListener;

    public WorkTypeAdapter(Activity activity, ArrayList<ContractorTypeDetail> workTypeList, OnItemCheckListener onItemCheckListener) {
        mActivity = activity;
        mWorkTypeList = workTypeList;
        mOnItemCheckListener = onItemCheckListener;
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

        private ContractorTypeDetail contractorTypeDetail;

        @BindView(R.id.checkbox_work_type)
        CheckBox checkBox;
        @BindView(R.id.text_work_name)
        BuildBoardTextView textWorkName;

        public WorkTypeViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindData(ContractorTypeDetail contractorTypeDetail) {
            this.contractorTypeDetail = contractorTypeDetail;

            textWorkName.setText(contractorTypeDetail.getTitle());
            checkBox.setChecked(contractorTypeDetail.isSelected());
            itemPositionArray.put(getAdapterPosition(), contractorTypeDetail.isSelected());
        }

        @OnClick(R.id.constraint_root)
        void onRowTapped() {
            checkBox.setChecked(!checkBox.isChecked());
            itemPositionArray.put(getAdapterPosition(), true);

            if (checkBox.isChecked()) {
                itemPositionArray.put(getAdapterPosition(), true);
                mOnItemCheckListener.onItemChecked(contractorTypeDetail.getId());
            } else {
                itemPositionArray.put(getAdapterPosition(), false);
                mOnItemCheckListener.onItemUnChecked(contractorTypeDetail.getId());
            }

            notifyDataSetChanged();
        }

        @OnClick(R.id.checkbox_work_type)
        void checkBoxTapped() {
            if (checkBox.isChecked()) {
                itemPositionArray.put(getAdapterPosition(), true);
                mOnItemCheckListener.onItemChecked(contractorTypeDetail.getId());
            } else {
                itemPositionArray.put(getAdapterPosition(), false);
                mOnItemCheckListener.onItemUnChecked(contractorTypeDetail.getId());
            }
        }
    }

    public interface OnItemCheckListener {
        void onItemChecked(String selectWorkId);

        void onItemUnChecked(String selectWorkId);
    }
}

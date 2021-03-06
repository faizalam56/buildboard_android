package com.buildboard.modules.signup.contractor.businessdocuments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.buildboard.R;
import com.buildboard.customviews.BuildBoardEditText;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.modules.signup.contractor.businessdocuments.GenericTextWatcher;
import com.buildboard.modules.signup.contractor.interfaces.IAddMoreCallback;
import com.buildboard.modules.signup.contractor.interfaces.ISelectAttachment;
import com.buildboard.modules.signup.contractor.interfaces.ITextWatcherCallback;
import com.buildboard.modules.signup.contractor.businessdocuments.models.DocumentData;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkmanInsuranceAdapter extends RecyclerView.Adapter<WorkmanInsuranceAdapter.ViewHolder> {

    private Context mContext;
    private HashMap<Integer, ArrayList<DocumentData>> mWorkmanInsurances;
    private LayoutInflater mLayoutInflater;
    private IAddMoreCallback iAddMoreCallback;
    private ISelectAttachment iSelectAttachment;

    public WorkmanInsuranceAdapter(Context context, HashMap<Integer, ArrayList<DocumentData>> workmanInsurances, IAddMoreCallback iAddMoreCallback, ISelectAttachment iSelectAttachment) {
        mContext = context;
        mWorkmanInsurances = workmanInsurances;
        this.iAddMoreCallback = iAddMoreCallback;
        this.iSelectAttachment = iSelectAttachment;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public WorkmanInsuranceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_workman_insurance, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WorkmanInsuranceAdapter.ViewHolder holder, int position) {
        holder.textAddMore.setVisibility(position < mWorkmanInsurances.size() - 1 ? View.GONE : View.VISIBLE);
        holder.bindData();
    }

    @Override
    public int getItemCount() {
        return mWorkmanInsurances.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_add_more)
        BuildBoardTextView textAddMore;

        @BindView(R.id.edit_insurance_provider_workman)
        BuildBoardEditText editProvider;
        @BindView(R.id.edit_insurance_amount_workman)
        BuildBoardEditText editAmount;
        @BindView(R.id.edit_attachment_workman_insurance)
        BuildBoardEditText editAttachment;

        @BindView(R.id.image_delete_row)
        ImageView imageDeleteRow;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            editProvider.addTextChangedListener(new GenericTextWatcher(editProvider, new ITextWatcherCallback() {

                @Override
                public void getValue(String value) {
                    mWorkmanInsurances.get(getAdapterPosition() + 1).get(0).setValue(value);
                }
            }));
            editAmount.addTextChangedListener(new GenericTextWatcher(editAmount, new ITextWatcherCallback() {
                @Override
                public void getValue(String value) {
                    mWorkmanInsurances.get(getAdapterPosition() + 1).get(1).setValue(value);
                }
            }));
        }

        private void bindData(){
            ArrayList<DocumentData> bondingDetail = mWorkmanInsurances.get(getAdapterPosition()+1);
            editProvider.setText(bondingDetail.get(0).getValue());
            editAmount.setText(bondingDetail.get(1).getValue());
            editAttachment.setText(bondingDetail.get(2).getValue());
            imageDeleteRow.setVisibility(mWorkmanInsurances.size() > 1 ? View.VISIBLE : View.GONE);
        }

        @OnClick(R.id.text_add_more)
        void addmoreTapped() {
            iAddMoreCallback.addMore();
        }

        @OnClick(R.id.image_attachment)
        void attachmentTapped() {
            iSelectAttachment.selectAttachment(getAdapterPosition() + 1);
        }

        @OnClick(R.id.image_delete_row)
        void deleteRowTapped() {
            for (int i = getAdapterPosition() + 1; i <= mWorkmanInsurances.size(); i++) {
                if (i == mWorkmanInsurances.size())
                    mWorkmanInsurances.remove(mWorkmanInsurances.size());
                else
                    mWorkmanInsurances.put(i, mWorkmanInsurances.get(i + 1));
            }
            notifyDataSetChanged();
        }
    }
}
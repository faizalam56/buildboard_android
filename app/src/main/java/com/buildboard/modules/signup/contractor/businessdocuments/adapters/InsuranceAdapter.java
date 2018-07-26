package com.buildboard.modules.signup.contractor.businessdocuments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buildboard.R;
import com.buildboard.customviews.BuildBoardEditText;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.modules.signup.contractor.businessdocuments.GenericTextWatcher;
import com.buildboard.modules.signup.contractor.interfaces.IAddMoreCallback;
import com.buildboard.modules.signup.contractor.interfaces.ITextWatcherCallback;
import com.buildboard.modules.signup.contractor.businessdocuments.models.DocumentData;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InsuranceAdapter extends RecyclerView.Adapter<InsuranceAdapter.ViewHolder> {

    private Context mContext;
    private HashMap<Integer, ArrayList<DocumentData>> mInsurances;
    private LayoutInflater mLayoutInflater;
    private IAddMoreCallback iAddMoreCallback;

    public InsuranceAdapter(Context context, HashMap<Integer, ArrayList<DocumentData>> insurances, IAddMoreCallback iAddMoreCallback) {
        mContext = context;
        mInsurances = insurances;
        this.iAddMoreCallback = iAddMoreCallback;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public InsuranceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_insurance, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InsuranceAdapter.ViewHolder holder, int position) {
        holder.textAddMore.setVisibility(position < mInsurances.size()-1 ? View.GONE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return mInsurances.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_add_more)
        BuildBoardTextView textAddMore;

        @BindView(R.id.edit_liability_insurance)
        BuildBoardEditText editLiability;
        @BindView(R.id.edit_insurance_provider)
        BuildBoardEditText editProvider;
        @BindView(R.id.edit_insurance_amount)
        BuildBoardEditText editAmount;
        @BindView(R.id.edit_attachment_insurance)
        BuildBoardEditText editAttachment;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            editLiability.addTextChangedListener(new GenericTextWatcher(editLiability, new ITextWatcherCallback(){

                @Override
                public void getValue(String value) {
                    mInsurances.get(getAdapterPosition() + 1).get(0).setValue(value);
                }
            }));
            editProvider.addTextChangedListener(new GenericTextWatcher(editProvider, new ITextWatcherCallback() {
                @Override
                public void getValue(String value) {
                    mInsurances.get(getAdapterPosition() + 1).get(1).setValue(value);
                }
            }));
            editAmount.addTextChangedListener(new GenericTextWatcher(editAmount, new ITextWatcherCallback() {
                @Override
                public void getValue(String value) {
                    mInsurances.get(getAdapterPosition() + 1).get(2).setValue(value);
                }
            }));
        }

        @OnClick(R.id.text_add_more)
        void addmoreTapped() {
            iAddMoreCallback.addMore();
        }
    }
}
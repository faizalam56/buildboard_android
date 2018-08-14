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
import com.buildboard.modules.signup.contractor.interfaces.ISelectAttachment;
import com.buildboard.modules.signup.contractor.interfaces.ITextWatcherCallback;
import com.buildboard.modules.signup.contractor.businessdocuments.models.DocumentData;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BondingAdapter extends RecyclerView.Adapter<BondingAdapter.ViewHolder> {

    private Context mContext;
    private HashMap<Integer, ArrayList<DocumentData>> mBondinds;
    private LayoutInflater mLayoutInflater;
    private IAddMoreCallback iAddMoreCallback;
    private ISelectAttachment iSelectAttachment;

    public BondingAdapter(Context context, HashMap<Integer, ArrayList<DocumentData>> bondinds, IAddMoreCallback iAddMoreCallback,
                          ISelectAttachment iSelectAttachment) {
        mContext = context;
        this.mBondinds = bondinds;
        this.iAddMoreCallback = iAddMoreCallback;
        this.iSelectAttachment = iSelectAttachment;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public BondingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_bonding, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BondingAdapter.ViewHolder holder, int position) {
        holder.textAddMore.setVisibility(position < mBondinds.size() - 1 ? View.GONE : View.VISIBLE);
        holder.bindData();
    }

    @Override
    public int getItemCount() {
        return mBondinds.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_add_more)
        BuildBoardTextView textAddMore;

        @BindView(R.id.edit_city)
        BuildBoardEditText editCity;
        @BindView(R.id.edit_bond_number)
        BuildBoardEditText editBondNumber;
        @BindView(R.id.edit_bond_amount)
        BuildBoardEditText editAmount;
        @BindView(R.id.edit_attachment_bonding)
        BuildBoardEditText editAttachment;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            editCity.addTextChangedListener(new GenericTextWatcher(editCity, new ITextWatcherCallback() {

                @Override
                public void getValue(String value) {
                    mBondinds.get(getAdapterPosition() + 1).get(0).setValue(value);
                }
            }));
            editBondNumber.addTextChangedListener(new GenericTextWatcher(editBondNumber, new ITextWatcherCallback() {
                @Override
                public void getValue(String value) {
                    mBondinds.get(getAdapterPosition() + 1).get(1).setValue(value);
                }
            }));
            editAmount.addTextChangedListener(new GenericTextWatcher(editAmount, new ITextWatcherCallback() {
                @Override
                public void getValue(String value) {
                    mBondinds.get(getAdapterPosition() + 1).get(2).setValue(value);
                }
            }));
        }

        private void bindData(){
            ArrayList<DocumentData> bondingDetail = mBondinds.get(getAdapterPosition()+1);
            editCity.setText(bondingDetail.get(0).getValue());
            editBondNumber.setText(bondingDetail.get(1).getValue());
            editAmount.setText(bondingDetail.get(2).getValue());
        }

        @OnClick(R.id.text_add_more)
        void addmoreTapped() {
            iAddMoreCallback.addMore();
        }

        @OnClick(R.id.image_attachment)
        void attachmentTapped() {
            iSelectAttachment.selectAttachment(getAdapterPosition() + 1);
        }
    }
}
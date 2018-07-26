package com.buildboard.modules.signup.contractor.businessdocuments.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.customviews.BuildBoardEditText;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.fonts.FontHelper;
import com.buildboard.modules.home.modules.mailbox.draft.drafts_reply.DraftsReplyActivity;
import com.buildboard.modules.signup.contractor.businessdocuments.GenericTextWatcher;
import com.buildboard.modules.signup.contractor.businessdocuments.interfaces.IBusinessDocumentsAddMoreCallback;
import com.buildboard.modules.signup.contractor.businessdocuments.interfaces.ITextWatcherCallback;
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
    private IBusinessDocumentsAddMoreCallback iAddMoreCallback;

    public InsuranceAdapter(Context context, HashMap<Integer, ArrayList<DocumentData>> insurances, IBusinessDocumentsAddMoreCallback iAddMoreCallback) {
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
        if (position < mInsurances.size() - 1)
            holder.textAddMore.setVisibility(View.GONE);
        else
            holder.textAddMore.setVisibility(View.VISIBLE);
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
            setFont();

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

            /*editLiability.addTextChangedListener(new GenericTextWatcher(editLiability));
            editProvider.addTextChangedListener(new GenericTextWatcher(editProvider));
            editAmount.addTextChangedListener(new GenericTextWatcher(editAmount));*/
        }

        @OnClick(R.id.text_add_more)
        void addmoreTapped() {
            iAddMoreCallback.addLayout();
        }

        private void setFont() {
//            FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, textName, textTitle, textSubject);
//            FontHelper.setFontFace(FontHelper.FontType.FONT_LIGHT, textMsg);
        }

        /*private class GenericTextWatcher implements TextWatcher {

            private View view;

            private GenericTextWatcher(View view) {
                this.view = view;
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                switch (view.getId()) {
                    case R.id.edit_liability_insurance:
                        mInsurances.get(getAdapterPosition() + 1).get(0).setValue(text);
                        break;
                    case R.id.edit_insurance_provider:
                        mInsurances.get(getAdapterPosition() + 1).get(1).setValue(text);
                        break;
                    case R.id.edit_insurance_amount:
                        mInsurances.get(getAdapterPosition() + 1).get(2).setValue(text);
                        break;
                }
            }
        }*/
    }
}
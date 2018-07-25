package com.buildboard.modules.signup.contractor.businessdocuments.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.fonts.FontHelper;
import com.buildboard.modules.home.modules.mailbox.draft.drafts_reply.DraftsReplyActivity;
import com.buildboard.modules.signup.contractor.businessdocuments.interfaces.IBusinessDocumentsAddMoreCallback;
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
        if(position < mInsurances.size()-1)
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

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
        }

        @OnClick(R.id.text_add_more)
        void addmoreTapped(){
            iAddMoreCallback.addLayout();
        }

        private void setFont() {
//            FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, textName, textTitle, textSubject);
//            FontHelper.setFontFace(FontHelper.FontType.FONT_LIGHT, textMsg);
        }
    }
}
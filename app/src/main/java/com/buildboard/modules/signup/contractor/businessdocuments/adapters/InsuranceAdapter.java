package com.buildboard.modules.signup.contractor.businessdocuments.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.fonts.FontHelper;
import com.buildboard.modules.home.modules.mailbox.draft.drafts_reply.DraftsReplyActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InsuranceAdapter extends RecyclerView.Adapter<InsuranceAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<String> mArrayList;
    private LayoutInflater mLayoutInflater;

    public InsuranceAdapter(Context context, ArrayList<String> arrayList) {
        mContext = context;
        mArrayList = arrayList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public InsuranceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_insurance, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InsuranceAdapter.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
        }

        private void setFont() {
//            FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, textName, textTitle, textSubject);
//            FontHelper.setFontFace(FontHelper.FontType.FONT_LIGHT, textMsg);
        }
    }
}
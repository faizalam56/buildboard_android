package com.buildboard.modules.signup.contractor.businessdocuments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buildboard.R;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class WorkmanInsuranceAdapter extends RecyclerView.Adapter<WorkmanInsuranceAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<String> mArrayList;
    private LayoutInflater mLayoutInflater;

    public WorkmanInsuranceAdapter(Context context, ArrayList<String> arrayList) {
        mContext = context;
        mArrayList = arrayList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public WorkmanInsuranceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_workman_insurance, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WorkmanInsuranceAdapter.ViewHolder holder, int position) {
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
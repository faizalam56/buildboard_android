package com.buildboard.modules.home.modules.marketplace.contractor_projecttype.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.fonts.FontHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContractorByProjectTypeDetailAdapter extends RecyclerView.Adapter<ContractorByProjectTypeDetailAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public ContractorByProjectTypeDetailAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ContractorByProjectTypeDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_contractor_by_project_detail, parent, false);
        return new ContractorByProjectTypeDetailAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContractorByProjectTypeDetailAdapter.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_project_type)
        TextView textProjectType;

        @BindView(R.id.recycler_contractors)
        RecyclerView recyclerContractors;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
            setContractorsRecycler();
        }

        private void setContractorsRecycler() {
            ContractorsAdapter contractorsAdapter = new ContractorsAdapter(mContext);
            recyclerContractors.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            recyclerContractors.setAdapter(contractorsAdapter);
        }

        private void setFont() {
            FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, textProjectType);
        }
    }
}

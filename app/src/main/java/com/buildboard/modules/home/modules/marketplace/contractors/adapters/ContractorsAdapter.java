package com.buildboard.modules.home.modules.marketplace.contractors.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.fonts.FontHelper;
import com.buildboard.modules.home.modules.marketplace.contractor_projecttype.models.ContractorByProjectTypeListData;
import com.buildboard.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContractorsAdapter extends RecyclerView.Adapter<ContractorsAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<ContractorByProjectTypeListData> mContractorList;

    public ContractorsAdapter(Context context, ArrayList<ContractorByProjectTypeListData> contractorList) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mContractorList = contractorList;
    }

    @Override
    public ContractorsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_contractors, parent, false);
        return new ContractorsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContractorsAdapter.ViewHolder holder, int position) {
        holder.setData();
    }

    @Override
    public int getItemCount() {
        return mContractorList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_contractor_name)
        TextView textContractorName;
        @BindView(R.id.text_company_name)
        TextView textCompanyName;

        @BindView(R.id.image_contractor)
        ImageView imageContractor;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
        }

        private void setFont() {
            FontHelper.setFontFace(FontHelper.FontType.FONT_LIGHT, textContractorName, textCompanyName);
        }

        private void setData() {
            ContractorByProjectTypeListData contractorData = mContractorList.get(getAdapterPosition());
            textContractorName.setText(contractorData.getFirstName());
            textCompanyName.setText(contractorData.getBusinessName());
            Utils.display(mContext, contractorData.getImage(), imageContractor, R.mipmap.ic_launcher);
        }
    }
}

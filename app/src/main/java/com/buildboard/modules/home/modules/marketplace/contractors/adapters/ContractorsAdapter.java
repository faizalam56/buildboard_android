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

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContractorsAdapter extends RecyclerView.Adapter<ContractorsAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public ContractorsAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ContractorsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_contractors, parent, false);
        return new ContractorsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContractorsAdapter.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 5;
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
    }
}

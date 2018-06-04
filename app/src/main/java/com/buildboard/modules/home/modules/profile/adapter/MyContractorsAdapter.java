package com.buildboard.modules.home.modules.profile.adapter;

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
import com.buildboard.modules.home.modules.marketplace.contractors.adapters.ContractorsAdapter;
import com.buildboard.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyConstractorsAdapter extends RecyclerView.Adapter<MyConstractorsAdapter.ViewHolder>{

    private Context mContext;

    public MyConstractorsAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MyConstractorsAdapter.ViewHolder holder, int position) {
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

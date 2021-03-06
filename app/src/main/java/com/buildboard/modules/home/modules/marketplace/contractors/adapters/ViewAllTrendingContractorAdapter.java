package com.buildboard.modules.home.modules.marketplace.contractors.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.fonts.FontHelper;
import com.buildboard.modules.home.modules.marketplace.ContractorProfile;
import com.buildboard.modules.home.modules.marketplace.contractor_projecttype.models.ContractorByProjectTypeListData;
import com.buildboard.modules.home.modules.marketplace.models.NearByContractor;
import com.buildboard.modules.home.modules.marketplace.models.TrendingService;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewAllTrendingContractorAdapter extends RecyclerView.Adapter<ViewAllTrendingContractorAdapter.ViewHolder> implements AppConstant{

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<TrendingService> mContractorList;

    public ViewAllTrendingContractorAdapter(Context context, ArrayList<TrendingService> contractorList) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mContractorList = contractorList;
    }

    @Override
    public ViewAllTrendingContractorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_contractors, parent, false);
        return new ViewAllTrendingContractorAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewAllTrendingContractorAdapter.ViewHolder holder, int position) {
        holder.setData();
    }

    @Override
    public int getItemCount() {
        return mContractorList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_contractor_name)
        TextView textContractorName;
        @BindView(R.id.text_company_address)
        TextView textCompanyAddress;
        @BindView(R.id.rating_contractor)
        AppCompatRatingBar ratingBar;
        @BindView(R.id.image_contractor)
        ImageView imageContractor;
        @BindView(R.id.linear_root)
        LinearLayout constraintLayout;
        @BindView(R.id.image_verified)
        ImageView imageVerified;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setFont() {
            FontHelper.setFontFace(FontHelper.FontType.FONT_LIGHT, textContractorName, textCompanyAddress);
        }

        private void setData() {
            TrendingService contractorData = mContractorList.get(getAdapterPosition());
            textContractorName.setText(String.format("%s%s", contractorData.getBusinessName().substring(0, 1).toUpperCase(), contractorData.getBusinessName().substring(1)));
            textCompanyAddress.setText(contractorData.getBusinessAddress());
            Utils.display(mContext, contractorData.getImage(), imageContractor, R.mipmap.no_image_available);
            ratingBar.setVisibility(View.GONE);
            if(contractorData.getVerified() == 1){
                imageVerified.setVisibility(View.VISIBLE);
            }
        }

        @OnClick(R.id.linear_root)
        public void onClick() {
            if (ConnectionDetector.isNetworkConnected(mContext)) {
                Intent intent = new Intent(mContext, ContractorProfile.class);
                intent.putExtra(INTENT_TRENDING_USER_ID, mContractorList.get(getAdapterPosition()).getUserId());
                mContext.startActivity(intent);
            } else {
                ConnectionDetector.createSnackBar(mContext, constraintLayout);
            }
        }
    }
}

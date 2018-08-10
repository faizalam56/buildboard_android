package com.buildboard.modules.home.modules.marketplace.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.fonts.FontHelper;
import com.buildboard.modules.home.modules.marketplace.ContractorProfile;
import com.buildboard.modules.home.modules.marketplace.models.NearByContractor;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NearByContractorAdapter extends RecyclerView.Adapter<NearByContractorAdapter.ViewHolder>
        implements AppConstant {

    private Context mContext;
    private List<NearByContractor> mNearByContractors;
    private LayoutInflater mLayoutInflater;

    public NearByContractorAdapter(Context context, List<NearByContractor> nearByContractors) {
        mContext = context;
        mNearByContractors = nearByContractors;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public NearByContractorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_nearby_contractor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NearByContractorAdapter.ViewHolder holder, int position) {
        holder.setData();
    }

    @Override
    public int getItemCount() {
        return mNearByContractors.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private NearByContractor nearByContractor;

        @BindView(R.id.text_name)
        BuildBoardTextView textName;
        @BindView(R.id.image_service)
        ImageView imageService;
        @BindView(R.id.textRatingBar)
        BuildBoardTextView textRatingBar;

        @BindString(R.string.not_available)
        String stringNotAvailable;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
        }

        private void setFont() {
            FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, textName);
        }

        private void setData() {
            nearByContractor = mNearByContractors.get(getAdapterPosition());
            if (nearByContractor == null) return;

            if(nearByContractor.getRatingCount() != null) {
                textRatingBar.setVisibility(View.VISIBLE);
                textRatingBar.setText(nearByContractor.getRatingCount());
            } else {
                textRatingBar.setVisibility(View.INVISIBLE);
            }

            textName.setText(nearByContractor.getBusinessName() != null ? nearByContractor.getBusinessName() : stringNotAvailable);
            Picasso.get().load(nearByContractor.getImage()).placeholder(R.mipmap.no_image_available).into(imageService);//TODO change placeholder
        }

        @OnClick(R.id.card_service)
        void contractorTapped() {
            Intent intent = new Intent(mContext, ContractorProfile.class);
            intent.putExtra(INTENT_TRENDING_USER_ID, nearByContractor.getUserId());
            mContext.startActivity(intent);
        }
    }
}
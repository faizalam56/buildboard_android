package com.buildboard.modules.home.modules.marketplace.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.fonts.FontHelper;
import com.buildboard.modules.home.modules.marketplace.models.NearByContractor;
import com.buildboard.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NearByContractorAdapter extends RecyclerView.Adapter<NearByContractorAdapter.ViewHolder> {

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

        @BindView(R.id.text_name)
        TextView textName;

        @BindView(R.id.image_service)
        ImageView imageService;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
        }

        private void setFont() {
            FontHelper.setFontFace(FontHelper.FontType.FONT_LIGHT, textName);
        }

        private void setData() {
            NearByContractor nearByContractor = mNearByContractors.get(getAdapterPosition());
            if (nearByContractor == null) return;

            textName.setText(nearByContractor.getRole() != null ? nearByContractor.getRole() : "N/A");
            Utils.display(mContext, nearByContractor.getContractorInfo().getImage(), imageService, 0);
        }
    }
}
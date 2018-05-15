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
import com.buildboard.modules.home.modules.marketplace.models.TrendingService;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {

    private Context mContext;
    private List<TrendingService> mTrendingServices;
    private LayoutInflater mLayoutInflater;

    public ServicesAdapter(Context context, List<TrendingService> trendingServices) {
        mContext = context;
        mTrendingServices = trendingServices;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ServicesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_services, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ServicesAdapter.ViewHolder holder, int position) {
        holder.setData();
    }

    @Override
    public int getItemCount() {
        return mTrendingServices.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_service_name)
        TextView textServiceName;
        @BindView(R.id.text_service_provider_name)
        TextView textServiceProviderName;

        @BindView(R.id.image_service)
        ImageView imageService;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
        }

        private void setFont() {
            FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, textServiceName);
            FontHelper.setFontFace(FontHelper.FontType.FONT_LIGHT, textServiceProviderName);
        }

        private void setData() {
            TrendingService trendingService = mTrendingServices.get(getAdapterPosition());
            textServiceName.setText(trendingService.getTitle());
            textServiceProviderName.setText(trendingService.getDescription() != null ? trendingService.getDescription().toString() : "N/A");
            Picasso.get().load(trendingService.getImage()).into(imageService);
        }
    }
}
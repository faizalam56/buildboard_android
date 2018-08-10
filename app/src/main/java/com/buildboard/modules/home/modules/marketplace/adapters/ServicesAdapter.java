package com.buildboard.modules.home.modules.marketplace.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.customviews.RoundedCornersTransform;
import com.buildboard.fonts.FontHelper;
import com.buildboard.modules.home.modules.marketplace.models.TrendingService;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.buildboard.utils.Utils.dottedAfterCertainLength;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> implements AppConstant {

    private Context mContext;
    private List<TrendingService> mTrendingServices;
    private LayoutInflater mLayoutInflater;

    public ServicesAdapter(Activity context, List<TrendingService> trendingServices) {
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

        @BindView(R.id.text_name)
        TextView textServiceName;
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
            FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, textServiceName);
        }

        private void setData() {
            TrendingService trendingService = mTrendingServices.get(getAdapterPosition());
            if (trendingService == null)
                return;

            if(trendingService.getRatingCount() != null) {
                textRatingBar.setVisibility(View.VISIBLE);
                textRatingBar.setText(trendingService.getRatingCount());
            } else {
                textRatingBar.setVisibility(View.INVISIBLE);
            }
            textServiceName.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS |InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
            textServiceName.setText(trendingService.getBusinessName() != null ? dottedAfterCertainLength(trendingService.getBusinessName(),mContext,56) : stringNotAvailable);
            Picasso.get().load(trendingService.getImage()).transform(new RoundedCornersTransform()).placeholder(R.mipmap.no_image_available).into(imageService);
        }
    }
}
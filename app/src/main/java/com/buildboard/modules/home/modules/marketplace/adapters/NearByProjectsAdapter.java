package com.buildboard.modules.home.modules.marketplace.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.buildboard.R;
import com.buildboard.fonts.FontHelper;
import com.buildboard.modules.home.modules.marketplace.models.NearByContractor;
import com.buildboard.modules.home.modules.marketplace.models.NearByProjects;
import com.squareup.picasso.Picasso;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NearByProjectsAdapter extends RecyclerView.Adapter<NearByProjectsAdapter.ViewHolder> {

    private Context mContext;
    private List<NearByProjects> mNearByContractors;
    private LayoutInflater mLayoutInflater;

    public NearByProjectsAdapter(Context context, List<NearByProjects> nearByContractors) {
        mContext = context;
        mNearByContractors = nearByContractors;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public NearByProjectsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_nearby_contractor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NearByProjectsAdapter.ViewHolder holder, int position) {
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
            NearByProjects nearByProjects = mNearByContractors.get(getAdapterPosition());
            if (nearByProjects == null) return;
            Picasso.get().load(nearByProjects.getImage()).placeholder(R.mipmap.ic_launcher).into(imageService);
            textName.setText(nearByProjects.getTitle());
        }
    }
}
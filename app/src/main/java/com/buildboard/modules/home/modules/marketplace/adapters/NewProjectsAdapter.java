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
import com.buildboard.fonts.FontHelper;
import com.buildboard.modules.home.modules.marketplace.contractors.NearByProjectsActivity;
import com.buildboard.modules.home.modules.marketplace.contractors.models.NewProject;
import com.buildboard.modules.home.modules.marketplace.models.NearByProjects;
import com.buildboard.utils.ConnectionDetector;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.buildboard.constants.AppConstant.DATA;

public class NewProjectsAdapter extends RecyclerView.Adapter<NewProjectsAdapter.ViewHolder> {

    private Context mContext;
    private List<NewProject> mNewProjects;
    private LayoutInflater mLayoutInflater;

    public NewProjectsAdapter(Context context, List<NewProject> nearByProjects) {
        mContext = context;
        mNewProjects = nearByProjects;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public NewProjectsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_nearby_contractor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewProjectsAdapter.ViewHolder holder, int position) {
        holder.setData();
    }

    @Override
    public int getItemCount() {
        return mNewProjects.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_name)
        TextView textName;
        @BindView(R.id.image_service)
        ImageView imageService;
        @BindView(R.id.textRatingBar)
        TextView textRatingbar;
        @BindView(R.id.card_service)
        CardView mCoordinatorLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
        }

        private void setFont() {
            FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, textName);
        }

        private void setData() {
            NewProject nearByProjects = mNewProjects.get(getAdapterPosition());
            if (nearByProjects == null) return;
            Picasso.get().load(nearByProjects.getImage()).placeholder(R.mipmap.no_image_available).into(imageService);
            textName.setText(nearByProjects.getTitle().substring(0,1).toUpperCase() + nearByProjects.getTitle().substring(1));
            textRatingbar.setVisibility(View.INVISIBLE);
        }

        @OnClick(R.id.card_service)
        public void rowTapped() {

            if (ConnectionDetector.isNetworkConnected(mContext)) {
                NewProject nearByProjects = mNewProjects.get(getAdapterPosition());
                Intent intent = new Intent(mContext, NearByProjectsActivity.class);
                intent.putExtra(DATA, nearByProjects.getId());
                mContext.startActivity(intent);
            } else {
                ConnectionDetector.createSnackBar(mContext, mCoordinatorLayout);
            }
        }
    }
}
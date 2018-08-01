package com.buildboard.modules.home.modules.marketplace.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.buildboard.R;
import com.buildboard.fonts.FontHelper;
import com.buildboard.interfaces.IRecyclerItemClickListener;
import com.buildboard.modules.home.modules.marketplace.contractors.ContractorsActivity;
import com.buildboard.modules.home.modules.marketplace.contractors.NearByProjectsActivity;
import com.buildboard.modules.home.modules.marketplace.models.NearByProjects;
import com.squareup.picasso.Picasso;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.buildboard.constants.AppConstant.DATA;
import static com.buildboard.constants.AppConstant.INTENT_TITLE;

public class NearByProjectsAdapter extends RecyclerView.Adapter<NearByProjectsAdapter.ViewHolder> {

    private Context mContext;
    private List<NearByProjects> mNearByProjects;
    private LayoutInflater mLayoutInflater;

    public NearByProjectsAdapter(Context context, List<NearByProjects> nearByProjects) {
        mContext = context;
        mNearByProjects = nearByProjects;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public NearByProjectsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_nearby_projects, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NearByProjectsAdapter.ViewHolder holder, int position) {
        holder.setData();
    }

    @Override
    public int getItemCount() {
        return mNearByProjects.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_service_name)
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
            NearByProjects nearByProjects = mNearByProjects.get(getAdapterPosition());
            if (nearByProjects == null) return;
            Picasso.get().load(nearByProjects.getImage()).placeholder(R.mipmap.ic_launcher).into(imageService);
            textName.setText(nearByProjects.getTitle());
        }

        @OnClick(R.id.container)
        public void rowTapped() {
            NearByProjects nearByProjects = mNearByProjects.get(getAdapterPosition());
            Intent intent = new Intent(mContext, NearByProjectsActivity.class);
            intent.putExtra(DATA, nearByProjects.getId());
            mContext.startActivity(intent);
        }
    }
 }
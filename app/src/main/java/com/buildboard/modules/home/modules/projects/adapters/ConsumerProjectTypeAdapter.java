package com.buildboard.modules.home.modules.projects.adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.buildboard.R;
import com.buildboard.interfaces.IRecyclerItemClickListener;
import com.buildboard.modules.home.modules.projects.models.ProjectAllType;
import com.buildboard.utils.Utils;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConsumerProjectTypeAdapter extends RecyclerView.Adapter<ConsumerProjectTypeAdapter.ViewHolder> {

    private ArrayList<ProjectAllType> projectAllTypesList;
    private Context mContext;
    private IRecyclerItemClickListener mClickListener;

    public ConsumerProjectTypeAdapter(FragmentActivity activity, ArrayList<ProjectAllType> projectAllTypesList, IRecyclerItemClickListener clickListener) {
        this.mContext = activity;
        this.projectAllTypesList = projectAllTypesList;
        mClickListener = clickListener;
    }

    @Override
    public ConsumerProjectTypeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_projects, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ConsumerProjectTypeAdapter.ViewHolder holder, int position) {
        holder.setData();
    }

    @Override
    public int getItemCount() {
        return projectAllTypesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_service)
        ImageView imageService;
        @BindView(R.id.text_service_type)
        TextView textServiceType;
        @BindView(R.id.text_service_type_name)
        TextView textServiceTypeName;
        @BindView(R.id.container)
        CardView mRootContainer;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
            textServiceTypeName.setVisibility(View.GONE);
        }

        private void setData() {
            ProjectAllType projectType = projectAllTypesList.get(getAdapterPosition());
            if (projectType == null) return;
            textServiceType.setText(projectType.getTitle());
            Utils.display(mContext, projectType.getImage(), imageService, R.mipmap.ic_launcher);
        }

        @OnClick(R.id.container)
        void rowTapped(View view) {
            mClickListener.onItemClick(view, getAdapterPosition(), projectAllTypesList.get(getAdapterPosition()));
        }
        
    }
}
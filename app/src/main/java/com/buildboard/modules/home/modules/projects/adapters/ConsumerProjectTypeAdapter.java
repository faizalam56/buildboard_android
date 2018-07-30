package com.buildboard.modules.home.modules.projects.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.buildboard.R;
import com.buildboard.modules.home.modules.projects.models.ProjectAllType;
import com.buildboard.utils.Utils;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ConsumerProjectTypeAdapter extends RecyclerView.Adapter<ConsumerProjectTypeAdapter.ViewHolder> {

    private ArrayList<ProjectAllType> projectAllTypesList;
    private Context mContext;

    public ConsumerProjectTypeAdapter(FragmentActivity activity, ArrayList<ProjectAllType> projectAllTypesList) {
        this.mContext = activity;
        this.projectAllTypesList = projectAllTypesList;
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

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }

        private void setData() {
            ProjectAllType projectType = projectAllTypesList.get(getAdapterPosition());
            if (projectType == null) return;
            textServiceType.setText(projectType.getTitle());
            textServiceTypeName.setText(projectType.getDescription());
            Utils.display(mContext, projectType.getImage(), imageService, R.mipmap.ic_launcher);
        }
    }
}
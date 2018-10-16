package com.buildboard.modules.home.modules.projects.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.buildboard.modules.home.modules.marketplace.contractors.ContractorsActivity;
import com.buildboard.modules.home.modules.marketplace.contractors.ProjectsDetailActivity;
import com.buildboard.modules.home.modules.marketplace.models.projectbyprojecttype.ProjectData;
import com.buildboard.modules.home.modules.projects.ProjectsActivity;
import com.buildboard.preferences.AppPreference;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ViewHolder> implements AppConstant {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<ProjectData> mProjectList;

    public ProjectsAdapter(Context context, ArrayList<ProjectData> projectData) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mProjectList = projectData;
    }

    @Override
    public ProjectsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_project_by_type, parent, false);
        return new ProjectsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProjectsAdapter.ViewHolder holder, int position) {
        holder.setData(mProjectList.get(position));
    }

    @Override
    public int getItemCount() {
        return mProjectList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_service_name)
        TextView textServiceName;
        @BindView(R.id.text_service_type)
        TextView textServiceType;
        @BindView(R.id.text_project_name)
        TextView textProjectName;
        @BindView(R.id.text_started_date)
        TextView textStartDate;
        @BindView(R.id.text_end_date)
        TextView textEndDate;
        @BindView(R.id.image_project_type)
        ImageView imageProjectType;
        @BindView(R.id.row_item_project_type)
        LinearLayout container;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setFont() {
            FontHelper.setFontFace(FontHelper.FontType.FONT_LIGHT, textServiceName, textServiceType, textProjectName);
        }

        private void setData(ProjectData projectData) {
            String completeName = projectData.getConsumer().getFirstName() + " " + projectData.getConsumer().getLastName();
            textServiceName.setText(completeName);
            textServiceType.setText(projectData.getProjectType().getTitle());
            textProjectName.setText(projectData.getTitle());
            textServiceType.setText(projectData.getProjectType().getTitle());
            textStartDate.setText(Utils.getFormattedDate(projectData.getStartDate()));
            textEndDate.setText(Utils.getFormattedDate(projectData.getEndDate()));
            Utils.display(mContext, projectData.getProjectType().getImage(), imageProjectType, R.mipmap.no_image_available);
        }

       @OnClick(R.id.row_item_project_type)
        public void rowTapped(){

           if (ConnectionDetector.isNetworkConnected(mContext)) {
               Intent intent = new Intent(mContext, ProjectsDetailActivity.class);
               intent.putExtra(DATA, mProjectList.get(getAdapterPosition()).getId());
               mContext.startActivity(intent);
           } else {
               ConnectionDetector.createSnackBar(mContext, container);
           }
        }
    }
}


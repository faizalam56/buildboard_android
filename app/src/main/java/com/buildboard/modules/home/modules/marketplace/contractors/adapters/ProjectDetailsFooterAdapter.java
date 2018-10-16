package com.buildboard.modules.home.modules.marketplace.contractors.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.fonts.FontHelper;
import com.buildboard.modules.home.modules.marketplace.contractors.ContractorProjectsAttachmentActivity;
import com.buildboard.modules.home.modules.marketplace.contractors.ContractorProjectsRequirementsActivity;
import com.buildboard.modules.home.modules.marketplace.contractors.models.ProjectForm;
import com.buildboard.modules.home.modules.marketplace.contractors.models.ProjectsDetailData;
import com.buildboard.modules.home.modules.marketplace.contractors.models.Task;
import com.buildboard.utils.ConnectionDetector;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.buildboard.constants.AppConstant.TEXT_ATTACHMENT;
import static com.buildboard.constants.AppConstant.TEXT_REQUIREMENTS;

public class ProjectDetailsFooterAdapter extends RecyclerView.Adapter<ProjectDetailsFooterAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<String> mArrayList;
    private LayoutInflater mLayoutInflater;
    private ProjectsDetailData mNearByProjectDetailsData;
    private ArrayList<String> mAttachmentsArray;

    public ProjectDetailsFooterAdapter(Context context, ArrayList<String> arrayList, ProjectsDetailData projectsDetailData) {
        mContext = context;
        mArrayList = arrayList;
        mLayoutInflater = LayoutInflater.from(mContext);
        mNearByProjectDetailsData = projectsDetailData;
    }

    @Override
    public ProjectDetailsFooterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_projects_details_footer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProjectDetailsFooterAdapter.ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_title)
        TextView textTitle;
        @BindView(R.id.constraint_drafts_row_footer)
        LinearLayout linearRootLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setData(int position) {
            textTitle.setText(mArrayList.get(position));
        }

        private ArrayList<String> getAttachmentArray(int position) {
            mAttachmentsArray = new ArrayList<>();

            for (int index = 0; index < mNearByProjectDetailsData.getAdditionalAttachment().size(); index++) {
                mAttachmentsArray.add(mNearByProjectDetailsData.getAdditionalAttachment().get(index));
            }

            for (int index = 0; index < mNearByProjectDetailsData.getPrefferedMaterialAttachment().size(); index++) {
                mAttachmentsArray.add(mNearByProjectDetailsData.getPrefferedMaterialAttachment().get(index));
            }

            return mAttachmentsArray;
        }

        @OnClick(R.id.constraint_drafts_row_footer)
        public void rowTapped() {

            if (ConnectionDetector.isNetworkConnected(mContext)) {
                String Text = mArrayList.get(getAdapterPosition());

                switch (Text) {
                    case TEXT_ATTACHMENT:
                        Intent intent = new Intent(mContext, ContractorProjectsAttachmentActivity.class);
                        intent.putExtra(AppConstant.DATA, getAttachmentArray(getAdapterPosition()));
                        mContext.startActivity(intent);
                        break;
                    case TEXT_REQUIREMENTS:
                        gotoRequirements();
                        break;
                }
            } else {
                ConnectionDetector.createSnackBar(mContext, linearRootLayout);
            }
        }

        private void gotoRequirements(){
            /*List<ProjectForm> data =mNearByProjectDetailsData.getProjectForm();
            List<Task> taskList = data.get(getAdapterPosition()).getTasks();*/

            Intent intentRequirement = new Intent(mContext, ContractorProjectsRequirementsActivity.class);
            intentRequirement.putExtra("projectDetailsData",mNearByProjectDetailsData);
            mContext.startActivity(intentRequirement);


        }
    }
}
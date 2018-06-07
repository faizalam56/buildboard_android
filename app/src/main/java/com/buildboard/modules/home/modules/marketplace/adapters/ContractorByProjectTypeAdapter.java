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
import com.buildboard.modules.home.modules.marketplace.contractors.ContractorsActivity;
import com.buildboard.modules.home.modules.marketplace.models.ProjectType;
import com.buildboard.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.buildboard.constants.AppConstant.DATA;
import static com.buildboard.constants.AppConstant.INTENT_TITLE;

public class ContractorByProjectTypeAdapter extends RecyclerView.Adapter<ContractorByProjectTypeAdapter.ViewHolder> {

    private Context mContext;
    private List<ProjectType> mProjectTypes;
    private LayoutInflater mLayoutInflater;

    public ContractorByProjectTypeAdapter(Context context, List<ProjectType> projectTypes) {
        mContext = context;
        mProjectTypes = projectTypes;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ContractorByProjectTypeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_contractor_by_project, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContractorByProjectTypeAdapter.ViewHolder holder, int position) {
        holder.setData();
    }

    @Override
    public int getItemCount() {
        return mProjectTypes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_name)
        TextView textName;

        @BindView(R.id.image_service)
        ImageView imageService;

        @BindView(R.id.card_service)
        CardView cardRow;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
        }

        @OnClick(R.id.card_service)
        public void rowTapped() {
            Intent intent = new Intent(mContext, ContractorsActivity.class);
            intent.putExtra(INTENT_TITLE, mProjectTypes.get(getAdapterPosition()).getTitle());
            intent.putExtra(DATA, mProjectTypes.get(getAdapterPosition()).getId());
            mContext.startActivity(intent);
        }

        private void setFont() {
            FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, textName);
        }

        private void setData() {
            ProjectType projectType = mProjectTypes.get(getAdapterPosition());
            if (projectType == null) return;

            textName.setText(projectType.getTitle() != null ? projectType.getTitle() : "N/A");
            Utils.display(mContext, projectType.getImage(), imageService, R.mipmap.ic_launcher);
        }
    }
}
package com.buildboard.modules.home.modules.marketplace.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.buildboard.R;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.customviews.RoundedCornersTransform;
import com.buildboard.fonts.FontHelper;
import com.buildboard.modules.home.modules.marketplace.contractors.ContractorsActivity;
import com.buildboard.modules.home.modules.marketplace.models.ProjectType;
import com.buildboard.preferences.AppPreference;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.buildboard.constants.AppConstant.DATA;
import static com.buildboard.constants.AppConstant.INTENT_TITLE;
import static com.buildboard.constants.AppConstant.IS_CONTRACTOR;

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
        BuildBoardTextView textName;
        @BindView(R.id.image_service)
        ImageView imageService;
        @BindView(R.id.container)
        CardView container;

        @BindString(R.string.not_available)
        String stringNotAvailable;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
        }

        @OnClick(R.id.container)
        public void rowTapped() {
            if (ConnectionDetector.isNetworkConnected(mContext)) {
                if (!AppPreference.getAppPreference(mContext).getBoolean(IS_CONTRACTOR)) {
                    Intent intent = new Intent(mContext, ContractorsActivity.class);
                    intent.putExtra(INTENT_TITLE, mProjectTypes.get(getAdapterPosition()).getTitle());
                    intent.putExtra(DATA, mProjectTypes.get(getAdapterPosition()).getId());
                    mContext.startActivity(intent);
                }else{
                    // TODO: 8/14/2018
                }
            } else {
                ConnectionDetector.createSnackBar(mContext,container);
            }
        }

        private void setFont() {
            FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, textName);
            FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD);
        }

        private void setData() {
            ProjectType projectType = mProjectTypes.get(getAdapterPosition());
            if (projectType == null) return;
            textName.setText(projectType.getTitle() != null ? projectType.getTitle() : stringNotAvailable);
            Picasso.get().load(projectType.getImage()).transform(new RoundedCornersTransform()).placeholder(R.mipmap.no_image_available).into(imageService);//TODO change placeholder
        }
    }
}
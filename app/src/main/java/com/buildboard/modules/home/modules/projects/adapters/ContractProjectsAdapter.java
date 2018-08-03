package com.buildboard.modules.home.modules.projects.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.fonts.FontHelper;
import com.buildboard.modules.home.modules.projects.models.ProjectDetail;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContractProjectsAdapter extends RecyclerView.Adapter {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    public boolean isLoading = false;
    private Context mContext;
    private Activity mActivity;
    private ArrayList<ProjectDetail> mProjectDetails;
    private OnLoadMoreListener onLoadMoreListener;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean isLastPage = false;
    private LayoutInflater mLayoutInflater;
    private String actualEndTime;

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = mLinearLayoutManager.getChildCount();
            int totalItemCount = mLinearLayoutManager.getItemCount();
            int firstVisibleItemPosition = mLinearLayoutManager.findFirstVisibleItemPosition();

            if (!isLoading && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                    setLoading(true);
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                }
            }
        }
    };

    public ContractProjectsAdapter(Activity activity, ArrayList<ProjectDetail> projectDetails, RecyclerView recyclerView) {
        mActivity = activity;
        mProjectDetails = projectDetails;
        mLinearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(onScrollListener);
        mLayoutInflater = LayoutInflater.from(mActivity);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        if (viewType == VIEW_TYPE_ITEM) {
            View view = mLayoutInflater.inflate(R.layout.item_contractor__projects, parent, false);
            return new ViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = mLayoutInflater.inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).bindData(position);
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            if (isLastPage)
                loadingViewHolder.progressBar.setVisibility(View.GONE);
            else {
                loadingViewHolder.progressBar.setVisibility(View.VISIBLE);
                loadingViewHolder.progressBar.setIndeterminate(true);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mProjectDetails.size()) ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return (mProjectDetails.size() != 0 && !isLastPage) ? mProjectDetails.size() + 1 : mProjectDetails.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    private String ConvertTime(String strDate) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = format1.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (format2.format(date));
    }

    public void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public void setLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_service)
        ImageView imageService;
        @BindView(R.id.card_service)
        CardView cardService;
        @BindView(R.id.text_service_name)
        TextView textServiceName;
        @BindView(R.id.text_service_projecttype_text)
        TextView textServiceProjectType;
        @BindView(R.id.text_service_projectname_text)
        TextView textServiceProjectName;
        @BindView(R.id.text_service_projectvalue_text)
        TextView textServiceContractValue;
        @BindView(R.id.text_service_project_completiondate_text)
        TextView textServiceCompletionDate;
        Button buttonView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
        }

        private void bindData(int position) {
            Picasso.get().load(mProjectDetails.get(position).getImage()).placeholder(R.mipmap.ic_launcher).into(imageService);
            textServiceName.setText(mProjectDetails.get(position).getConsumerInfo().getFirstName());
            textServiceProjectType.setText(mProjectDetails.get(position).getProjectType().getTitle());
            textServiceProjectName.setText(mProjectDetails.get(position).getTitle());
            textServiceContractValue.setText(mProjectDetails.get(position).getCategory());
            actualEndTime = mProjectDetails.get(position).getEndDate().split("\\s+")[0];
            textServiceCompletionDate.setText(ConvertTime(actualEndTime.replaceAll("-", "/")));
        }

        private void setFont() {
            FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, textServiceCompletionDate, textServiceProjectType, textServiceContractValue, buttonView, textServiceProjectName);
            FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, textServiceName);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;
        private LoadingViewHolder(View view) {
            super(view);
            progressBar = view.findViewById(R.id.progressBar_loading);
        }
    }
}
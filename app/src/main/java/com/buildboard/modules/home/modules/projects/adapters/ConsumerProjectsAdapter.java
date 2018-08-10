package com.buildboard.modules.home.modules.projects.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.buildboard.R;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.fonts.FontHelper;
import com.buildboard.modules.home.modules.projects.models.ProjectDetail;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.buildboard.constants.AppConstant.INPUT_PATTERN;
import static com.buildboard.constants.AppConstant.OUTPUT_PATTERN;
import static com.buildboard.constants.AppConstant.STATUS_COMPLETED;
import static com.buildboard.constants.AppConstant.STATUS_CURRENT;
import static com.buildboard.constants.AppConstant.STATUS_OPEN;
import static com.buildboard.constants.AppConstant.STATUS_SAVED;
import static com.buildboard.utils.Utils.parseDateFormat;

public class ConsumerProjectsAdapter extends RecyclerView.Adapter {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    public boolean isLoading = false;
    private ArrayList<ProjectDetail> mProjectDetails;
    private OnLoadMoreListener onLoadMoreListener;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean isLastPage = false;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
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

    public ConsumerProjectsAdapter(Activity activity, ArrayList<ProjectDetail> projectDetails, RecyclerView recyclerView) {
        mContext = activity;
        mProjectDetails = projectDetails;
        mLinearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(onScrollListener);
        mLayoutInflater = LayoutInflater.from(activity);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = mLayoutInflater.inflate(R.layout.item_projects, parent, false);

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
        @BindView(R.id.text_service_type)
        BuildBoardTextView textServiceType;
        @BindView(R.id.text_service_type_name)
        BuildBoardTextView textServiceTypeName;
        @BindView(R.id.text_started_date)
        BuildBoardTextView textStartedDate;
        @BindView(R.id.text_bid_count)
        BuildBoardTextView textBidCount;
        @BindView(R.id.text_number_of_bid)
        BuildBoardTextView textNumberOfBid;
        @BindView(R.id.text_view_count)
        BuildBoardTextView textViewCount;
        @BindView(R.id.text_view)
        BuildBoardTextView textView;
        @BindView(R.id.relative_bid_related)
        RelativeLayout bidRelatedRelativeLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
        }

        private void setFont() {
            FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD,textServiceType);
            FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, textServiceTypeName);
            FontHelper.setFontFace(FontHelper.FontType.FONT_LIGHT, textStartedDate, textViewCount, textView, textBidCount, textNumberOfBid);
        }

        private void bindData(int position) {
            String data=null;
            switch (mProjectDetails.get(position).getStatus()){
                case STATUS_OPEN:
                    if (mProjectDetails.get(position).getQuotesCount() != null && mProjectDetails.get(position).getViewsCount() != null) {
                        bidRelatedRelativeLayout.setVisibility(View.VISIBLE);
                        textBidCount.setText(mProjectDetails.get(position).getQuotesCount());
                        textViewCount.setText(mProjectDetails.get(position).getViewsCount());
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) textStartedDate.getLayoutParams();
                        layoutParams.setMargins(0, 0, 0, 15);
                    } else {
                        bidRelatedRelativeLayout.setVisibility(View.GONE);
                    }

                    data = mContext.getString(R.string.posted_on)
                            +parseDateFormat(mProjectDetails.get(position).getStartDate().substring(0, 10));
                    break;
                case STATUS_COMPLETED:
                    bidRelatedRelativeLayout.setVisibility(View.GONE);
                    data = mContext.getString(R.string.completed_on)
                            +parseDateFormat(mProjectDetails.get(position).getStartDate().substring(0, 10));
                    break;
                case STATUS_CURRENT:
                    bidRelatedRelativeLayout.setVisibility(View.GONE);
                    data = mContext.getString(R.string.completed_on)
                            +parseDateFormat(mProjectDetails.get(position).getStartDate().substring(0, 10));
                    break;
                case STATUS_SAVED:
                    bidRelatedRelativeLayout.setVisibility(View.GONE);
                    data = mContext.getString(R.string.completed_on)
                            +parseDateFormat(mProjectDetails.get(position).getStartDate().substring(0, 10));
                    break;
            }

            textServiceType.setText(mProjectDetails.get(position).getProjectType().getTitle());
            textServiceTypeName.setText(capitalizeFirstLetter(mProjectDetails.get(position).getTitle()));
            textStartedDate.setText(data);
            Picasso.get().load(mProjectDetails.get(position).getImage())
                    .placeholder(R.drawable.supplies).into(imageService);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;
        private LoadingViewHolder(View view) {
            super(view);
            progressBar = view.findViewById(R.id.progressBar_loading);
        }
    }

    private String capitalizeFirstLetter(String original) {
        return original.length() == 0 ? original : original.substring(0, 1).toUpperCase() + original.substring(1);
    }
}
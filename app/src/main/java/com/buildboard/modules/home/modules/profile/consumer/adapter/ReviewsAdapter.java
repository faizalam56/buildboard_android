package com.buildboard.modules.home.modules.profile.consumer.adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.modules.home.modules.profile.consumer.models.reviews.ReviewData;
import com.buildboard.preferences.AppPreference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewsAdapter extends RecyclerView.Adapter implements AppConstant {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    public boolean isLoading = false;
    private Activity mActivity;
    private ArrayList<ReviewData> mReviewsList;
    private OnLoadMoreListener onLoadMoreListener;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean isLastPage = false;
    private LayoutInflater mLayoutInflater;

    public ReviewsAdapter(Activity activity, ArrayList<ReviewData> reviewsList, RecyclerView recyclerView) {
        mActivity = activity;
        mReviewsList = reviewsList;
        mLinearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        mLayoutInflater = LayoutInflater.from(mActivity);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = mLayoutInflater.inflate(R.layout.item_review, parent, false);

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
                loadingViewHolder.mProgressBar.setVisibility(View.GONE);
            else {
                loadingViewHolder.mProgressBar.setVisibility(View.VISIBLE);
                loadingViewHolder.mProgressBar.setIndeterminate(true);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mReviewsList.size();
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

        @BindView(R.id.image_contractor)
        ImageView imageContractor;
        @BindView(R.id.text_business_name)
        TextView textBusinessName;
        @BindView(R.id.text_review)
        TextView textReview;
        @BindView(R.id.rating_contractor)
        RatingBar ratingBar;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bindData(int position) {

            if (AppPreference.getAppPreference(mActivity).getBoolean(IS_CONTRACTOR)) {
                setReviewData(mReviewsList.get(position).getConsumer().getImage(),
                        mReviewsList.get(position).getConsumer().getFirstName() + " " + mReviewsList.get(position).getConsumer().getLastName(),
                        mReviewsList.get(position).getReview(),
                        5);
            } else {
                setReviewData(mReviewsList.get(position).getContractor().getImage(),
                        mReviewsList.get(position).getContractor().getBusinessName(),
                        mReviewsList.get(position).getReview(),
                        5);
            }
        }

        private void setReviewData(String image, String name, String review, int rating) {

            Picasso.get()
                    .load(image)
                    .into(imageContractor);
            textBusinessName.setText(name);
            textReview.setText(review);
            ratingBar.setRating(rating > 5 ? 5 : rating);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        private ProgressBar mProgressBar;

        private LoadingViewHolder(View view) {
            super(view);
            mProgressBar = view.findViewById(R.id.progressBar_loading);
        }

    }
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
}
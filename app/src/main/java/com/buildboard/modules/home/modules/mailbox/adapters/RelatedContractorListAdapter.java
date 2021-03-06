package com.buildboard.modules.home.modules.mailbox.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.modules.home.modules.mailbox.inbox.InboxActivity;
import com.buildboard.modules.home.modules.mailbox.modules.models.ConsumerRelatedData;
import com.buildboard.modules.home.modules.mailbox.modules.models.ContractorRelatedData;
import com.buildboard.modules.home.modules.marketplace.ContractorProfile;
import com.buildboard.utils.ConnectionDetector;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.buildboard.constants.AppConstant.DATA;

public class RelatedContractorListAdapter extends RecyclerView.Adapter implements AppConstant {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    public boolean isLoading = false;
    private Activity mActivity;
    private ArrayList<ContractorRelatedData> mMessageList;
    private MessagesAdapter.OnLoadMoreListener onLoadMoreListener;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean isLastPage = false;
    private LayoutInflater mLayoutInflater;

    public RelatedContractorListAdapter(Activity activity, ArrayList<ContractorRelatedData> messageDataList, RecyclerView recyclerView) {
        mActivity = activity;
        mMessageList = messageDataList;
        mLinearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        mLayoutInflater = LayoutInflater.from(mActivity);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = mLayoutInflater.inflate(R.layout.item_related_users, parent, false);
            return new RelatedContractorListAdapter.ViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = mLayoutInflater.inflate(R.layout.item_loading, parent, false);
            return new RelatedContractorListAdapter.LoadingViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RelatedContractorListAdapter.ViewHolder) {
            ((RelatedContractorListAdapter.ViewHolder) holder).bindData(position);
        } else if (holder instanceof RelatedContractorListAdapter.LoadingViewHolder) {
            RelatedContractorListAdapter.LoadingViewHolder loadingViewHolder = (RelatedContractorListAdapter.LoadingViewHolder) holder;
            if (isLastPage)
                loadingViewHolder.progressBar.setVisibility(View.GONE);
            else {
                loadingViewHolder.progressBar.setVisibility(View.VISIBLE);
                loadingViewHolder.progressBar.setIndeterminate(true);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    public void setOnLoadMoreListener(MessagesAdapter.OnLoadMoreListener mOnLoadMoreListener) {
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

        @BindView(R.id.image_receiver)
        ImageView imageReciever;
        @BindView(R.id.text_receiver_name)
        TextView textReceiverName;
        @BindView(R.id.constraint_root)
        ConstraintLayout constraintLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bindData(int position) {
            Picasso.get()
                    .load(mMessageList.get(position).getImage())
                    .into(imageReciever);
            textReceiverName.setText(mMessageList.get(position).getFirstName()+" "+mMessageList.get(position).getLastName());
        }

        @OnClick(R.id.constraint_root)
        public void rowTapped() {
            if (ConnectionDetector.isNetworkConnected(mActivity)) {
                Intent intent = new Intent(mActivity, ContractorProfile.class);
                intent.putExtra(INTENT_TRENDING_USER_ID,  mMessageList.get(getAdapterPosition()).getUserId());
                mActivity.startActivity(intent);
            } else {
                ConnectionDetector.createSnackBar(mActivity, constraintLayout);
            }
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        private ProgressBar progressBar;
        private LoadingViewHolder(View view) {
            super(view);
            progressBar = view.findViewById(R.id.progressBar_loading);
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

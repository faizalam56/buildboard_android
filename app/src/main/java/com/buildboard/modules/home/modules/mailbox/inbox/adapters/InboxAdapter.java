package com.buildboard.modules.home.modules.mailbox.inbox.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.fonts.FontHelper;
import com.buildboard.modules.home.modules.mailbox.adapters.MessagesAdapter;
import com.buildboard.modules.home.modules.mailbox.inbox.InboxActivity;
import com.buildboard.modules.home.modules.mailbox.inbox.models.Data;
import com.buildboard.preferences.AppPreference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InboxAdapter extends RecyclerView.Adapter {

    private Activity mActivity;
    private ArrayList<Data> mArrayList;
    private LayoutInflater mLayoutInflater;
    private InboxAdapter.OnLoadMoreListener onLoadMoreListener;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    public boolean isLoading = false;
    private boolean isLastPage = false;
    private LinearLayoutManager mLinearLayoutManager;

    public InboxAdapter(Activity activity, ArrayList<Data> arrayList, RecyclerView recyclerView) {
        mActivity = activity;
        mArrayList = arrayList;
        mLinearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(onScrollListener);
        mLayoutInflater = LayoutInflater.from(mActivity);
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
                if (firstVisibleItemPosition== 0) {
                    setLoading(true);
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                }
            }
        }
    };

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View view = mLayoutInflater.inflate(R.layout.item_inbox_sender, parent, false);
            return new InboxAdapter.ViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = mLayoutInflater.inflate(R.layout.item_loading, parent, false);
            return new InboxAdapter.LoadingViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof InboxAdapter.ViewHolder) {
            ((InboxAdapter.ViewHolder) holder).bindData(position);
        } else if (holder instanceof InboxAdapter.LoadingViewHolder) {
            InboxAdapter.LoadingViewHolder loadingViewHolder = (InboxAdapter.LoadingViewHolder) holder;
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
        return (position == mArrayList.size()) ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public void setOnLoadMoreListener(InboxAdapter.OnLoadMoreListener mOnLoadMoreListener) {
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

        @BindView(R.id.text_msg_sent)
        TextView textSentMsg;
        @BindView(R.id.text_msg_receive)
        TextView textReceiveMsg;
        @BindView(R.id.text_msg_receive_date)
        TextView textMessageReceiveDate;
        @BindView(R.id.text_message_receive_view)
        LinearLayout layoutMessageReceive;
        @BindView(R.id.text_message_sent_view)
        LinearLayout layoutMessageSent;
        @BindView(R.id.text_msg_sent_date)
        TextView textMessageSentDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            setFont();
        }

        private void bindData(int position) {
            String User_Id = AppPreference.getAppPreference(mActivity).getString(AppConstant.USER_ID);
            Data messageModel = mArrayList.get(getAdapterPosition());
            layoutMessageSent.setVisibility(!messageModel.getRecipientId().equals(User_Id) ? View.VISIBLE : View.GONE);
            layoutMessageReceive.setVisibility(!messageModel.getRecipientId().equals(User_Id) ? View.GONE : View.VISIBLE);

            if (!messageModel.getRecipientId().equals(User_Id)) {
                textSentMsg.setText(messageModel.getBody());
                textMessageSentDate.setText(ConvertTime(messageModel.getCreatedAt()
                        .replaceAll("-", "/")));

            } else {
                textReceiveMsg.setText(messageModel.getBody());
                textMessageReceiveDate.setText(ConvertTime(messageModel.getCreatedAt()
                        .replaceAll("-", "/")));
            }
        }

        private void setFont() {
            FontHelper.setFontFace(FontHelper.FontType.FONT_LIGHT, textSentMsg, textReceiveMsg);
        }
    }

    private String ConvertTime(String strDate) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("MMM d, h:mm a");
        Date date = null;
        try {
            date = format1.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (format2.format(date));
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {

        private ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = view.findViewById(R.id.progressBar_loading);
        }
    }
}
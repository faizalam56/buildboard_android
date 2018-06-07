package com.buildboard.modules.home.modules.mailbox.inbox.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.fonts.FontHelper;
import com.buildboard.modules.home.modules.mailbox.draft.drafts_reply.DraftsReplyActivity;
import com.buildboard.modules.home.modules.mailbox.inbox.InboxActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InboxListAdapter extends RecyclerView.Adapter<InboxListAdapter.ViewHolder> {

    private Activity mActivity;
    private ArrayList<String> mInboxMessages;
    private LayoutInflater mLayoutInflater;

    public InboxListAdapter(Activity activity, ArrayList<String> inboxMessages) {
        mActivity = activity;
        mInboxMessages = inboxMessages;
        mLayoutInflater = LayoutInflater.from(mActivity);
    }

    @Override
    public InboxListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_drafts, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return mInboxMessages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_name)
        TextView textName;
        @BindView(R.id.text_title)
        TextView textTitle;
        @BindView(R.id.text_subject)
        TextView textSubject;
        @BindView(R.id.text_msg)
        TextView textMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
        }

        @OnClick(R.id.constraint_drafts_row)
        void rowTapped() {
            mActivity.startActivity(new Intent(mActivity, InboxActivity.class));
        }

        private void setFont() {
            FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, textName, textTitle, textSubject);
            FontHelper.setFontFace(FontHelper.FontType.FONT_LIGHT, textMessage);
        }
    }
}

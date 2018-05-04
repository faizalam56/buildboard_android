package com.buildboard.modules.home.modules.mailbox.inbox.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.fonts.FontHelper;
import com.buildboard.modules.home.modules.mailbox.inbox.InboxActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<InboxActivity.MessageModel> mArrayList;
    private LayoutInflater mLayoutInflater;

    public InboxAdapter(Context context, ArrayList<InboxActivity.MessageModel> arrayList) {
        mContext = context;
        mArrayList = arrayList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public InboxAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_inbox_sender, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InboxAdapter.ViewHolder holder, int position) {
        holder.setData();
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_msg_sent)
        TextView textSentMsg;
        @BindView(R.id.text_msg_receive)
        TextView textReceiveMsg;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
        }

        private void setData() {
            InboxActivity.MessageModel messageModel = mArrayList.get(getAdapterPosition());

            textSentMsg.setVisibility(messageModel.isSentMsg() ? View.VISIBLE : View.GONE);
            textReceiveMsg.setVisibility(messageModel.isSentMsg() ? View.GONE : View.VISIBLE);

            if (messageModel.isSentMsg())
                textSentMsg.setText(messageModel.getMsg());
            else
                textReceiveMsg.setText(messageModel.getMsg());
        }

        private void setFont() {
            FontHelper.setFontFace(FontHelper.FontType.FONT_LIGHT, textSentMsg, textReceiveMsg);
        }
    }
}
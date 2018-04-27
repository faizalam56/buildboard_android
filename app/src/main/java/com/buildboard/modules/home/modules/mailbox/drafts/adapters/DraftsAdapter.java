package com.buildboard.modules.home.modules.mailbox.drafts.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.fonts.FontHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DraftsAdapter extends RecyclerView.Adapter<DraftsAdapter.ViewHolder> {

    private Context mContext;
    private List<String> mArrayList;
    private LayoutInflater mLayoutInflater;

    public DraftsAdapter(Context context, List<String> arrayList) {
        mContext = context;
        mArrayList = arrayList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public DraftsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_drafts, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DraftsAdapter.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_name)
        TextView textName;
        @BindView(R.id.text_msg)
        TextView textMsg;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
        }

        private void setFont() {
            FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, textName);
            FontHelper.setFontFace(FontHelper.FontType.FONT_LIGHT, textMsg);
        }
    }
}
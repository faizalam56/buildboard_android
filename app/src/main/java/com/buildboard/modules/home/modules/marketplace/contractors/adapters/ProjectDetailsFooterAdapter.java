package com.buildboard.modules.home.modules.marketplace.contractors.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.fonts.FontHelper;
import com.buildboard.modules.home.modules.mailbox.inbox.InboxActivity;
import com.buildboard.modules.home.modules.marketplace.contractors.NearByProjectsActivity;
import com.buildboard.modules.home.modules.marketplace.models.NearByProjects;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.buildboard.constants.AppConstant.DATA;

public class ProjectDetailsFooterAdapter extends RecyclerView.Adapter<ProjectDetailsFooterAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<String> mArrayList;
    private LayoutInflater mLayoutInflater;

    public ProjectDetailsFooterAdapter(Context context, ArrayList<String> arrayList) {
        mContext = context;
        mArrayList = arrayList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ProjectDetailsFooterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_projects_details_footer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProjectDetailsFooterAdapter.ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_title)
        TextView textTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
        }

        private void setData(int position) {
            textTitle.setText(mArrayList.get(position));
        }

        private void setFont() {
            FontHelper.setFontFace(FontHelper.FontType.FONT_LIGHT, textTitle);
        }

        @OnClick(R.id.constraint_drafts_row_footer)
        public void rowTapped() {
            String Text = mArrayList.get(getAdapterPosition());

        }
    }
}
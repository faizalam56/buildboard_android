package com.buildboard.modules.home.modules.projects.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.fonts.FontHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ViewHolder> {

    private Context mContext;
    private List<String> mArrayList;
    private LayoutInflater mLayoutInflater;

    public ProjectsAdapter(Context context, List<String> arrayList) {
        mContext = context;
        mArrayList = arrayList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_projects, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_service)
        ImageView imageService;
        @BindView(R.id.card_service)
        CardView cardService;
        @BindView(R.id.text_service_type)
        TextView textServiceType;
        @BindView(R.id.button_view)
        Button buttonView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
        }

        private void setFont() {
            FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, textServiceType, buttonView);
        }
    }
}
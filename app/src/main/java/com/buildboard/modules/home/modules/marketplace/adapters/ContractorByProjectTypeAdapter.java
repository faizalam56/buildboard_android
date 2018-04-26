package com.buildboard.modules.home.modules.marketplace.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.fonts.FontHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContractorByProjectTypeAdapter extends RecyclerView.Adapter<ContractorByProjectTypeAdapter.ViewHolder> {

    private Context mContext;
    private List<String> mArrayList;
    private LayoutInflater mLayoutInflater;

    public ContractorByProjectTypeAdapter(Context context, List<String> arrayList) {
        mContext = context;
        mArrayList = arrayList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ContractorByProjectTypeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_contractor_by_project, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContractorByProjectTypeAdapter.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_name)
        TextView textName;

        @BindView(R.id.image_service)
        ImageView imageService;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
        }

        private void setFont() {
            FontHelper.setFontFace(FontHelper.FontType.FONT_LIGHT, textName);
        }
    }
}
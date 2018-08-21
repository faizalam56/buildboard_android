package com.buildboard.modules.home.modules.marketplace.contractors.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.buildboard.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContractorProjectAttachmetsAdapter extends RecyclerView.Adapter<ContractorProjectAttachmetsAdapter.ViewHolder> {

    private ArrayList<String> mAttachmentArray;
    private LayoutInflater mLayoutInflater;

    public ContractorProjectAttachmetsAdapter(Context context, ArrayList<String> attachmentArray) {
        mAttachmentArray = attachmentArray;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ContractorProjectAttachmetsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_contractor_projects_attachment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContractorProjectAttachmetsAdapter.ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return mAttachmentArray.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_service)
        ImageView imageService;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setData(int position) {
          Picasso.get().load(mAttachmentArray.get(position)).placeholder(R.mipmap.no_image_available).into(imageService);
        }
    }
}
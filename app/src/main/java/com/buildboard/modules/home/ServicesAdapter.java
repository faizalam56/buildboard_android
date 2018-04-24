package com.buildboard.modules.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.fonts.FontHelper;
import com.buildboard.interfaces.IRecyclerItemClickListener;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {

    private Context mContext;
    private List<String> mArrayList;
    private LayoutInflater layoutInflater;
    private IRecyclerItemClickListener mClickListener;

    public ServicesAdapter(Context context, List<String> arrayList, IRecyclerItemClickListener clickListener) {
        mContext = context;
        mArrayList = arrayList;
        mClickListener = clickListener;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ServicesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_services, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ServicesAdapter.ViewHolder holder, int position) {
//        holder.textSelection.setText(mArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_service_name)
        TextView textServiceName;
        @BindView(R.id.text_service_provider_name)
        TextView textServiceProviderName;

        @BindView(R.id.image_service)
        ImageView imageService;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
            /*Glide.with(mContext)
                    .load(R.drawable.supplies)
                    .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(5)))
                    .into(imageService);*/
//            Picasso.get().load(R.drawable.supplies).transform(new CircleTransform(15, 2)).into(imageService);
        }

        /*@OnClick(R.id.text_selection_item)
        void rowTapped(View view) {
            mClickListener.onItemClick(view, getAdapterPosition(), mArrayList.get(getAdapterPosition()));
        }*/

        private void setFont() {
            FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, textServiceName);
            FontHelper.setFontFace(FontHelper.FontType.FONT_LIGHT, textServiceProviderName);
        }
    }
}
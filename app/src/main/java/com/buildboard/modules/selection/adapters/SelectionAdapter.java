package com.buildboard.modules.selection.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.fonts.FontHelper;
import com.buildboard.interfaces.IRecyclerItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectionAdapter extends RecyclerView.Adapter<SelectionAdapter.ViewHolder> {

    private Context mContext;
    private List<String> mArrayList;
    private LayoutInflater layoutInflater;
    private IRecyclerItemClickListener mClickListener;

    public SelectionAdapter(Context context, List<String> arrayList, IRecyclerItemClickListener clickListener) {
        mContext = context;
        mArrayList = arrayList;
        mClickListener = clickListener;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public SelectionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_usertype, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SelectionAdapter.ViewHolder holder, int position) {
        holder.textSelection.setText(mArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_selection_item)
        TextView textSelection;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();
        }

        @OnClick(R.id.text_selection_item)
        void rowTapped(View view) {
            mClickListener.onItemClick(view, getAdapterPosition(), mArrayList.get(getAdapterPosition()));
        }

        private void setFont() {
            FontHelper.setFontFace(FontHelper.FontType.FONT_LIGHT, textSelection);
        }
    }
}
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
import com.buildboard.modules.signup.models.contractortype.ContractorTypeDetail;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContractorTypeSelectionAdapter extends RecyclerView.Adapter<ContractorTypeSelectionAdapter.ViewHolder> {

    private Context mContext;
    private List<ContractorTypeDetail> mArrayList;
    private LayoutInflater layoutInflater;
    private IRecyclerItemClickListener mClickListener;

    public ContractorTypeSelectionAdapter(Context context, List<ContractorTypeDetail> arrayList, IRecyclerItemClickListener clickListener) {
        mContext = context;
        mArrayList = arrayList;
        mClickListener = clickListener;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ContractorTypeSelectionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_usertype, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContractorTypeSelectionAdapter.ViewHolder holder, int position) {
        holder.setData();
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

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

        private void setData(){
            ContractorTypeDetail datum = mArrayList.get(getAdapterPosition());
            textSelection.setText(datum.getTitle());
        }

        private void setFont() {
            FontHelper.setFontFace(FontHelper.FontType.FONT_LIGHT, textSelection);
        }
    }
}
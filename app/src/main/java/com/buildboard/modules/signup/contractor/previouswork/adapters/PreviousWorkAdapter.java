package com.buildboard.modules.signup.contractor.previouswork.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.buildboard.R;
import com.buildboard.customviews.BuildBoardEditText;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.modules.signup.contractor.businessdocuments.GenericTextWatcher;
import com.buildboard.modules.signup.contractor.interfaces.IAddMoreCallback;
import com.buildboard.modules.signup.contractor.interfaces.ITextWatcherCallback;
import com.buildboard.modules.signup.contractor.previouswork.models.PreviousWorkData;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PreviousWorkAdapter extends RecyclerView.Adapter<PreviousWorkAdapter.ViewHolder> {

    private Context mContext;
    private HashMap<Integer, ArrayList<PreviousWorkData>> mPreviousWorks;
    private LayoutInflater mLayoutInflater;
    private IAddMoreCallback iAddMoreCallback;

    public PreviousWorkAdapter(Context context, HashMap<Integer, ArrayList<PreviousWorkData>> previousWorks, IAddMoreCallback iAddMoreCallback) {
        mContext = context;
        this.mPreviousWorks = previousWorks;
        this.iAddMoreCallback = iAddMoreCallback;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public PreviousWorkAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_previous_work, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PreviousWorkAdapter.ViewHolder holder, int position) {
        holder.textAddMore.setVisibility(position < mPreviousWorks.size()-1 ? View.GONE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return mPreviousWorks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_add_more)
        BuildBoardTextView textAddMore;

        @BindView(R.id.image_attachment)
        ImageView imageAttachment;

        @BindView(R.id.edit_description)
        BuildBoardEditText editDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            editDescription.addTextChangedListener(new GenericTextWatcher(editDescription, new ITextWatcherCallback(){

                @Override
                public void getValue(String value) {
//                    mPreviousWorks.get(getAdapterPosition() + 1).get(0).setValue(value);
                }
            }));
        }

        @OnClick(R.id.text_add_more)
        void addmoreTapped(){
            iAddMoreCallback.addMore();
        }
    }
}
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
import com.buildboard.modules.signup.contractor.interfaces.ISelectAttachment;
import com.buildboard.modules.signup.contractor.interfaces.ITextWatcherCallback;
import com.buildboard.modules.signup.contractor.previouswork.models.PreviousWorkData;
import com.squareup.picasso.Picasso;

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
    private ISelectAttachment iSelectAttachment;

    public PreviousWorkAdapter(Context context, HashMap<Integer, ArrayList<PreviousWorkData>> previousWorks, IAddMoreCallback iAddMoreCallback, ISelectAttachment iSelectAttachment) {
        mContext = context;
        this.mPreviousWorks = previousWorks;
        this.iAddMoreCallback = iAddMoreCallback;
        this.iSelectAttachment = iSelectAttachment;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public PreviousWorkAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_previous_work, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PreviousWorkAdapter.ViewHolder holder, int position) {
        holder.textAddMore.setVisibility(position < mPreviousWorks.size() - 1 ? View.GONE : View.VISIBLE);
        holder.setData();
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
        @BindView(R.id.image_attachment1)
        ImageView imageAttachment1;
        @BindView(R.id.image_attachment2)
        ImageView imageAttachment2;
        @BindView(R.id.image_attachment3)
        ImageView imageAttachment3;
        @BindView(R.id.image_attachment4)
        ImageView imageAttachment4;
        @BindView(R.id.image_close_attachment1)
        ImageView imageCloseAttachment1;
        @BindView(R.id.image_close_attachment2)
        ImageView imageCloseAttachment2;
        @BindView(R.id.image_close_attachment3)
        ImageView imageCloseAttachment3;
        @BindView(R.id.image_close_attachment4)
        ImageView imageCloseAttachment4;

        @BindView(R.id.edit_title)
        BuildBoardEditText editTitle;
        @BindView(R.id.edit_description)
        BuildBoardEditText editDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            editTitle.addTextChangedListener(new GenericTextWatcher(editTitle, new ITextWatcherCallback() {

                @Override
                public void getValue(String value) {
                    if (mPreviousWorks.get(getAdapterPosition() + 1).get(0).getValue().size() > 0)
                        mPreviousWorks.get(getAdapterPosition() + 1).get(0).getValue().set(0, value);
                    else
                        mPreviousWorks.get(getAdapterPosition() + 1).get(0).getValue().add(value);
                }
            }));

            editDescription.addTextChangedListener(new GenericTextWatcher(editDescription, new ITextWatcherCallback() {

                @Override
                public void getValue(String value) {
                    if (mPreviousWorks.get(getAdapterPosition() + 1).get(1).getValue().size() > 0)
                        mPreviousWorks.get(getAdapterPosition() + 1).get(1).getValue().set(0, value);
                    else
                        mPreviousWorks.get(getAdapterPosition() + 1).get(1).getValue().add(value);
                }
            }));
        }

        private void setData() {
            if (mPreviousWorks.get(getAdapterPosition() + 1).get(0).getValue().size() > 0)
                editTitle.setText(mPreviousWorks.get(getAdapterPosition() + 1).get(0).getValue().get(0));
            if (mPreviousWorks.get(getAdapterPosition() + 1).get(1).getValue().size() > 0)
                editDescription.setText(mPreviousWorks.get(getAdapterPosition() + 1).get(1).getValue().get(0));

            int size = mPreviousWorks.get(getAdapterPosition() + 1).get(2).getValue().size();
            switch (size) {
                case 1:
                    Picasso.get().load(mPreviousWorks.get(getAdapterPosition() + 1).get(2).getValue().get(0)).into(imageAttachment1);
                    break;
                case 2:
                    Picasso.get().load(mPreviousWorks.get(getAdapterPosition() + 1).get(2).getValue().get(0)).into(imageAttachment1);
                    Picasso.get().load(mPreviousWorks.get(getAdapterPosition() + 1).get(2).getValue().get(1)).into(imageAttachment2);
                    break;
                case 3:
                    Picasso.get().load(mPreviousWorks.get(getAdapterPosition() + 1).get(2).getValue().get(0)).into(imageAttachment1);
                    Picasso.get().load(mPreviousWorks.get(getAdapterPosition() + 1).get(2).getValue().get(1)).into(imageAttachment2);
                    Picasso.get().load(mPreviousWorks.get(getAdapterPosition() + 1).get(2).getValue().get(2)).into(imageAttachment3);
                    break;
                case 4:
                    Picasso.get().load(mPreviousWorks.get(getAdapterPosition() + 1).get(2).getValue().get(0)).into(imageAttachment1);
                    Picasso.get().load(mPreviousWorks.get(getAdapterPosition() + 1).get(2).getValue().get(1)).into(imageAttachment2);
                    Picasso.get().load(mPreviousWorks.get(getAdapterPosition() + 1).get(2).getValue().get(2)).into(imageAttachment3);
                    Picasso.get().load(mPreviousWorks.get(getAdapterPosition() + 1).get(2).getValue().get(3)).into(imageAttachment4);
                    break;
            }
            setVisibility(size);
        }

        private void setVisibility(int size) {
            imageAttachment1.setVisibility(size > 0 ? View.VISIBLE : View.GONE);
            imageCloseAttachment1.setVisibility(size > 0 ? View.VISIBLE : View.GONE);
            imageAttachment2.setVisibility(size > 1 ? View.VISIBLE : View.GONE);
            imageCloseAttachment2.setVisibility(size > 1 ? View.VISIBLE : View.GONE);
            imageAttachment3.setVisibility(size > 2 ? View.VISIBLE : View.GONE);
            imageCloseAttachment3.setVisibility(size > 2 ? View.VISIBLE : View.GONE);
            imageAttachment4.setVisibility(size > 3 ? View.VISIBLE : View.GONE);
            imageCloseAttachment4.setVisibility(size > 3 ? View.VISIBLE : View.GONE);
            imageAttachment.setVisibility(size == 4 ? View.GONE : View.VISIBLE);
        }

        @OnClick(R.id.text_add_more)
        void addmoreTapped() {
            iAddMoreCallback.addMore();
        }

        @OnClick(R.id.image_attachment)
        void attachmentTapped() {
            iSelectAttachment.selectAttachment(getAdapterPosition() + 1);
        }

        @OnClick(R.id.image_close_attachment1)
        void attachment1CloseTapped() {
            mPreviousWorks.get(getAdapterPosition() + 1).get(2).getValue().remove(0);
            notifyDataSetChanged();
        }

        @OnClick(R.id.image_close_attachment2)
        void attachment2CloseTapped() {
            mPreviousWorks.get(getAdapterPosition() + 1).get(2).getValue().remove(1);
            notifyDataSetChanged();
        }

        @OnClick(R.id.image_close_attachment3)
        void attachment3CloseTapped() {
            mPreviousWorks.get(getAdapterPosition() + 1).get(2).getValue().remove(2);
            notifyDataSetChanged();
        }

        @OnClick(R.id.image_close_attachment4)
        void attachment4CloseTapped() {
            mPreviousWorks.get(getAdapterPosition() + 1).get(2).getValue().remove(3);
            notifyDataSetChanged();
        }
    }
}
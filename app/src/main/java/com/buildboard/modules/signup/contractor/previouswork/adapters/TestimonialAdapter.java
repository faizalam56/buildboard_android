package com.buildboard.modules.signup.contractor.previouswork.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buildboard.R;
import com.buildboard.customviews.BuildBoardEditText;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.modules.signup.contractor.businessdocuments.GenericTextWatcher;
import com.buildboard.modules.signup.contractor.interfaces.IAddMoreCallback;
import com.buildboard.modules.signup.contractor.businessdocuments.models.DocumentData;
import com.buildboard.modules.signup.contractor.interfaces.ITextWatcherCallback;
import com.buildboard.modules.signup.contractor.previouswork.models.PreviousWorkData;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestimonialAdapter extends RecyclerView.Adapter<TestimonialAdapter.ViewHolder> {

    private Context mContext;
    private HashMap<Integer, ArrayList<PreviousWorkData>> mTestimonials;
    private LayoutInflater mLayoutInflater;
    private IAddMoreCallback iAddMoreCallback;

    public TestimonialAdapter(Context context, HashMap<Integer, ArrayList<PreviousWorkData>> testimonials, IAddMoreCallback iAddMoreCallback) {
        mContext = context;
        this.mTestimonials = testimonials;
        this.iAddMoreCallback = iAddMoreCallback;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public TestimonialAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_testimonial, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TestimonialAdapter.ViewHolder holder, int position) {
        holder.textAddMore.setVisibility(position < mTestimonials.size()-1 ? View.GONE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return mTestimonials.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_add_more)
        BuildBoardTextView textAddMore;

        @BindView(R.id.edit_name)
        BuildBoardEditText editName;
        @BindView(R.id.edit_work_performed)
        BuildBoardEditText editWorkPerformed;
        @BindView(R.id.edit_testimonial)
        BuildBoardEditText editTestimonial;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            editName.addTextChangedListener(new GenericTextWatcher(editName, new ITextWatcherCallback(){

                @Override
                public void getValue(String value) {
                    if (mTestimonials.get(getAdapterPosition()+1).get(0).getValue().size() > 0)
                        mTestimonials.get(getAdapterPosition()+1).get(0).getValue().set(0, value);
                    else
                        mTestimonials.get(getAdapterPosition()+1).get(0).getValue().add(value);
                }
            }));
            editWorkPerformed.addTextChangedListener(new GenericTextWatcher(editWorkPerformed, new ITextWatcherCallback() {
                @Override
                public void getValue(String value) {
                    if (mTestimonials.get(getAdapterPosition()+1).get(1).getValue().size() > 0)
                        mTestimonials.get(getAdapterPosition()+1).get(1).getValue().set(0, value);
                    else
                        mTestimonials.get(getAdapterPosition()+1).get(1).getValue().add(value);
                }
            }));
            editTestimonial.addTextChangedListener(new GenericTextWatcher(editTestimonial, new ITextWatcherCallback() {
                @Override
                public void getValue(String value) {
                    if (mTestimonials.get(getAdapterPosition()+1).get(2).getValue().size() > 0)
                        mTestimonials.get(getAdapterPosition()+1).get(2).getValue().set(0, value);
                    else
                        mTestimonials.get(getAdapterPosition()+1).get(2).getValue().add(value);
                }
            }));
        }

        @OnClick(R.id.text_add_more)
        void addmoreTapped(){
            iAddMoreCallback.addMore();
        }
    }
}
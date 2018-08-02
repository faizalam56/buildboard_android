package com.buildboard.modules.signup.contractor.businessdocuments.adapters;

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
import com.buildboard.modules.signup.contractor.interfaces.ISelectAttachment;
import com.buildboard.modules.signup.contractor.interfaces.ITextWatcherCallback;
import com.buildboard.modules.signup.contractor.businessdocuments.models.DocumentData;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CertificationAdapter extends RecyclerView.Adapter<CertificationAdapter.ViewHolder> {

    private Context mContext;
    private HashMap<Integer, ArrayList<DocumentData>> mCertifications;
    private LayoutInflater mLayoutInflater;
    private IAddMoreCallback iAddMoreCallback;
    private ISelectAttachment iSelectAttachment;

    public CertificationAdapter(Context context, HashMap<Integer, ArrayList<DocumentData>> certifications, IAddMoreCallback iAddMoreCallback, ISelectAttachment iSelectAttachment) {
        mContext = context;
        mCertifications = certifications;
        this.iAddMoreCallback = iAddMoreCallback;
        this.iSelectAttachment = iSelectAttachment;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public CertificationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_certification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CertificationAdapter.ViewHolder holder, int position) {
        holder.textAddMore.setVisibility(position < mCertifications.size() - 1 ? View.GONE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return mCertifications.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_add_more)
        BuildBoardTextView textAddMore;

        @BindView(R.id.edit_certifying_body)
        BuildBoardEditText editCertBody;
        @BindView(R.id.edit_certification_number)
        BuildBoardEditText editCertNumber;
        @BindView(R.id.edit_certification_desc)
        BuildBoardEditText editCertDesc;
        @BindView(R.id.edit_attachment_certification)
        BuildBoardEditText editAttachment;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            editCertBody.addTextChangedListener(new GenericTextWatcher(editCertBody, new ITextWatcherCallback() {

                @Override
                public void getValue(String value) {
                    mCertifications.get(getAdapterPosition() + 1).get(0).setValue(value);
                }
            }));
            editCertNumber.addTextChangedListener(new GenericTextWatcher(editCertNumber, new ITextWatcherCallback() {
                @Override
                public void getValue(String value) {
                    mCertifications.get(getAdapterPosition() + 1).get(1).setValue(value);
                }
            }));
            editCertDesc.addTextChangedListener(new GenericTextWatcher(editCertDesc, new ITextWatcherCallback() {
                @Override
                public void getValue(String value) {
                    mCertifications.get(getAdapterPosition() + 1).get(2).setValue(value);
                }
            }));
        }

        @OnClick(R.id.text_add_more)
        void addmoreTapped() {
            iAddMoreCallback.addMore();
        }

        @OnClick(R.id.image_attachment)
        void attachmentTapped() {
            iSelectAttachment.selectAttachment(getAdapterPosition() + 1);
        }
    }
}
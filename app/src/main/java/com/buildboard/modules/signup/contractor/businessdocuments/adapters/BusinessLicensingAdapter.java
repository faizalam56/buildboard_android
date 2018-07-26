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
import com.buildboard.modules.signup.contractor.businessdocuments.interfaces.IBusinessDocumentsAddMoreCallback;
import com.buildboard.modules.signup.contractor.businessdocuments.interfaces.ITextWatcherCallback;
import com.buildboard.modules.signup.contractor.businessdocuments.models.DocumentData;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BusinessLicensingAdapter extends RecyclerView.Adapter<BusinessLicensingAdapter.ViewHolder> {

    private Context mContext;
    private HashMap<Integer, ArrayList<DocumentData>> mBusinessLicensings;
    private LayoutInflater mLayoutInflater;
    private IBusinessDocumentsAddMoreCallback iBusinessDocumentsAddMoreCallback;
    private int size;

    public BusinessLicensingAdapter(Context context, HashMap<Integer, ArrayList<DocumentData>> businessLicensings, IBusinessDocumentsAddMoreCallback iBusinessDocumentsAddMoreCallback) {
        mContext = context;
        this.mBusinessLicensings = businessLicensings;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.iBusinessDocumentsAddMoreCallback = iBusinessDocumentsAddMoreCallback;
    }

    @Override
    public BusinessLicensingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_business_licensing, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BusinessLicensingAdapter.ViewHolder holder, int position) {
        if(position < mBusinessLicensings.size()-1)
            holder.textAddMore.setVisibility(View.GONE);
        else
            holder.textAddMore.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return mBusinessLicensings.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_add_more)
        BuildBoardTextView textAddMore;

        @BindView(R.id.edit_state)
        BuildBoardEditText editState;
        @BindView(R.id.edit_license_number)
        BuildBoardEditText editLicenceNumber;
        @BindView(R.id.edit_attachment)
        BuildBoardEditText editAttachment;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setFont();

            editState.addTextChangedListener(new GenericTextWatcher(editState, new ITextWatcherCallback(){

                @Override
                public void getValue(String value) {
                    mBusinessLicensings.get(getAdapterPosition() + 1).get(0).setValue(value);
                }
            }));
            editLicenceNumber.addTextChangedListener(new GenericTextWatcher(editLicenceNumber, new ITextWatcherCallback() {
                @Override
                public void getValue(String value) {
                    mBusinessLicensings.get(getAdapterPosition() + 1).get(1).setValue(value);
                }
            }));
        }

        @OnClick(R.id.text_add_more)
        void addmoreTapped(){
            iBusinessDocumentsAddMoreCallback.addLayout();
        }

        private void setFont() {
//            FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, textName, textTitle, textSubject);
//            FontHelper.setFontFace(FontHelper.FontType.FONT_LIGHT, textMsg);
        }
    }
}
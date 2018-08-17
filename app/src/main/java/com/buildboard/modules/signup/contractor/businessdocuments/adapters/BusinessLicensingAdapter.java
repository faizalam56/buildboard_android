package com.buildboard.modules.signup.contractor.businessdocuments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.buildboard.R;
import com.buildboard.customviews.BuildBoardEditText;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.modules.signup.contractor.businessdocuments.GenericTextWatcher;
import com.buildboard.modules.signup.contractor.interfaces.IAddMoreCallback;
import com.buildboard.modules.signup.contractor.interfaces.ISelectAttachment;
import com.buildboard.modules.signup.contractor.interfaces.ITextWatcherCallback;
import com.buildboard.modules.signup.contractor.businessdocuments.models.DocumentData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BusinessLicensingAdapter extends RecyclerView.Adapter<BusinessLicensingAdapter.ViewHolder> {

    private Context mContext;
    private HashMap<Integer, ArrayList<DocumentData>> mBusinessLicensings;
    private LayoutInflater mLayoutInflater;
    private IAddMoreCallback iBusinessDocumentsAddMoreCallback;
    private int size;
    private ISelectAttachment iSelectAttachment;
    private HashMap<String, ArrayList<String>> mStates;

    public BusinessLicensingAdapter(Context context, HashMap<Integer, ArrayList<DocumentData>> businessLicensings, IAddMoreCallback iBusinessDocumentsAddMoreCallback,
                                    ISelectAttachment iSelectAttachment) {
        mContext = context;
        this.mBusinessLicensings = businessLicensings;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.iBusinessDocumentsAddMoreCallback = iBusinessDocumentsAddMoreCallback;
        this.iSelectAttachment = iSelectAttachment;
        getStates();
    }

    @Override
    public BusinessLicensingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_business_licensing, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BusinessLicensingAdapter.ViewHolder holder, int position) {
        holder.textAddMore.setVisibility(position < mBusinessLicensings.size() - 1 ? View.GONE : View.VISIBLE);
        holder.bindData();
    }

    @Override
    public int getItemCount() {
        return mBusinessLicensings.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_add_more)
        BuildBoardTextView textAddMore;

        //        @BindView(R.id.edit_state)
//        BuildBoardEditText editState;
        @BindView(R.id.edit_license_number)
        BuildBoardEditText editLicenceNumber;
        @BindView(R.id.edit_attachment)
        BuildBoardEditText editAttachment;

        @BindView(R.id.image_delete_row)
        ImageView imageDeleteRow;

        @BindView(R.id.spinner_states)
        Spinner spinnerStates;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            /*editState.addTextChangedListener(new GenericTextWatcher(editState, new ITextWatcherCallback() {

                @Override
                public void getValue(String value) {
                    mBusinessLicensings.get(getAdapterPosition() + 1).get(0).setValue(value);
                }
            }));*/
            editLicenceNumber.addTextChangedListener(new GenericTextWatcher(editLicenceNumber, new ITextWatcherCallback() {
                @Override
                public void getValue(String value) {
                    mBusinessLicensings.get(getAdapterPosition() + 1).get(1).setValue(value);
                }
            }));
        }

        private void bindData() {
            ArrayList<DocumentData> bondingDetail = mBusinessLicensings.get(getAdapterPosition() + 1);
//            editState.setText(bondingDetail.get(0).getValue());
            editLicenceNumber.setText(bondingDetail.get(1).getValue());
            editAttachment.setText(bondingDetail.get(2).getValue());
            imageDeleteRow.setVisibility(mBusinessLicensings.size() > 1 ? View.VISIBLE : View.GONE);
        }

        @OnClick(R.id.text_add_more)
        void addmoreTapped() {
            iBusinessDocumentsAddMoreCallback.addMore();
        }

        @OnClick(R.id.image_attachment)
        void attachmentTapped() {
            iSelectAttachment.selectAttachment(getAdapterPosition() + 1);
        }

        @OnClick(R.id.image_delete_row)
        void deleteRowTapped() {
            for (int i = getAdapterPosition() + 1; i <= mBusinessLicensings.size(); i++) {
                if (i == mBusinessLicensings.size())
                    mBusinessLicensings.remove(mBusinessLicensings.size());
                else
                    mBusinessLicensings.put(i, mBusinessLicensings.get(i + 1));
            }
            notifyDataSetChanged();
        }
    }

    private void getStates() {
        try {
            JSONObject statesJson = new JSONObject(readJSONFromAsset()).getJSONObject("data").getJSONObject("states");
            mStates = new Gson().fromJson(
                    statesJson.toString(), new TypeToken<HashMap<String, Object>>() {
                    }.getType()
            );
            mStates.keySet();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String readJSONFromAsset() {
        String json = null;
        try {
            InputStream is = mContext.getAssets().open("State.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
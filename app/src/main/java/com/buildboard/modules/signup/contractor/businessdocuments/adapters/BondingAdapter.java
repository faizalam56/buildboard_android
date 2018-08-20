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

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BondingAdapter extends RecyclerView.Adapter<BondingAdapter.ViewHolder> {

    private Context mContext;
    private HashMap<Integer, ArrayList<DocumentData>> mBondings;
    private LayoutInflater mLayoutInflater;
    private IAddMoreCallback iAddMoreCallback;
    private ISelectAttachment iSelectAttachment;
    private HashMap<String, ArrayList<String>> mStateCitiesDatas;

    public BondingAdapter(Context context, HashMap<Integer, ArrayList<DocumentData>> bondings, HashMap<String, ArrayList<String>> stateCityValues,
                          IAddMoreCallback iAddMoreCallback, ISelectAttachment iSelectAttachment) {
        mContext = context;
        this.mBondings = bondings;
        this.iAddMoreCallback = iAddMoreCallback;
        this.iSelectAttachment = iSelectAttachment;
        this.mStateCitiesDatas = stateCityValues;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public BondingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_bonding, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BondingAdapter.ViewHolder holder, int position) {
        holder.textAddMore.setVisibility(position < mBondings.size() - 1 ? View.GONE : View.VISIBLE);
        holder.bindData();
    }

    @Override
    public int getItemCount() {
        return mBondings.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ArrayAdapter<String> citiesAdapter;
        private ArrayList<String> cities = new ArrayList<>();

        @BindView(R.id.text_add_more)
        BuildBoardTextView textAddMore;

        @BindView(R.id.edit_bond_number)
        BuildBoardEditText editBondNumber;
        @BindView(R.id.edit_bond_amount)
        BuildBoardEditText editAmount;
        @BindView(R.id.edit_attachment_bonding)
        BuildBoardEditText editAttachment;

        @BindView(R.id.image_delete_row)
        ImageView imageDeleteRow;

        @BindView(R.id.spinner_states)
        Spinner spinnerStates;
        @BindView(R.id.spinner_city)
        Spinner spinnerCities;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            populateStatesData();
            cities.addAll(mStateCitiesDatas.get(spinnerStates.getSelectedItem().toString()));
            populateCitiesData();

            editBondNumber.addTextChangedListener(new GenericTextWatcher(editBondNumber, new ITextWatcherCallback() {
                @Override
                public void getValue(String value) {
                    mBondings.get(getAdapterPosition() + 1).get(2).setValue(value);
                }
            }));
            editAmount.addTextChangedListener(new GenericTextWatcher(editAmount, new ITextWatcherCallback() {
                @Override
                public void getValue(String value) {
                    mBondings.get(getAdapterPosition() + 1).get(3).setValue(value);
                }
            }));
        }

        private void bindData() {
            ArrayList<DocumentData> bondingDetail = mBondings.get(getAdapterPosition() + 1);
            editBondNumber.setText(bondingDetail.get(2).getValue());
            editAmount.setText(bondingDetail.get(3).getValue());
            editAttachment.setText(bondingDetail.get(4).getValue());
            imageDeleteRow.setVisibility(mBondings.size() > 1 ? View.VISIBLE : View.GONE);
            for (int i = 0; i < spinnerStates.getCount(); i++) {
                if (spinnerStates.getItemAtPosition(i).toString().contains(bondingDetail.get(0).getValue())) {
                    spinnerStates.setSelection(i);
                }
            }

            for (int i = 0; i < spinnerCities.getCount(); i++) {
                if (spinnerCities.getItemAtPosition(i).toString().contains(bondingDetail.get(1).getValue())) {
                    spinnerCities.setSelection(i);
                }
            }
        }

        @OnClick(R.id.text_add_more)
        void addmoreTapped() {
            iAddMoreCallback.addMore();
        }

        @OnClick(R.id.image_attachment)
        void attachmentTapped() {
            iSelectAttachment.selectAttachment(getAdapterPosition() + 1);
        }

        @OnClick(R.id.image_delete_row)
        void deleteRowTapped() {
            for (int i = getAdapterPosition() + 1; i <= mBondings.size(); i++) {
                if (i == mBondings.size())
                    mBondings.remove(mBondings.size());
                else
                    mBondings.put(i, mBondings.get(i + 1));
            }
            notifyDataSetChanged();
        }

        private void populateStatesData() {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, new ArrayList<>(mStateCitiesDatas.keySet()));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerStates.setAdapter(adapter);

            spinnerStates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    mBondings.get(getAdapterPosition() + 1).get(0).setValue(adapterView.getSelectedItem().toString());
                    if (citiesAdapter == null) return;

                    cities.clear();
                    cities.addAll(mStateCitiesDatas.get(spinnerStates.getSelectedItem().toString()));
                    citiesAdapter.notifyDataSetChanged();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    //TODO implement if needed
                }
            });
        }

        private void populateCitiesData() {
            citiesAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, cities);
            citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCities.setAdapter(citiesAdapter);

            spinnerCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    mBondings.get(getAdapterPosition() + 1).get(1).setValue(adapterView.getSelectedItem().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    //TODO implement if needed
                }
            });
        }
    }
}
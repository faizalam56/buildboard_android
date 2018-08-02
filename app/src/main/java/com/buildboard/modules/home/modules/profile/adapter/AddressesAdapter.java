package com.buildboard.modules.home.modules.profile.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buildboard.R;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.modules.home.modules.profile.models.addresses.getaddress.AddressListData;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.ViewHolder> {
    private Activity mActivity;
    private ArrayList<AddressListData> mAddressList;
    private AddressListData mPrimaryAddress;

    public AddressesAdapter(Activity activity, ArrayList<AddressListData> addressList) {
        mActivity = activity;
        mAddressList = addressList;
        sortPrimaryTop();
    }

    @Override
    public AddressesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_addresses, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddressesAdapter.ViewHolder holder, int position) {
        holder.bindData(mAddressList.get(position));
    }

    @Override
    public int getItemCount() {
        return mAddressList.size();
    }

    private void sortPrimaryTop() {
        Collections.sort(mAddressList, new Comparator<AddressListData>() {
            @Override
            public int compare(AddressListData item1, AddressListData item2) {

                return item2.getIsDefault().compareTo(item1.getIsDefault());
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_location)
        BuildBoardTextView textAddress;

        private AddressListData address;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bindData(AddressListData address) {
            if (address == null) return;
            this.address = address;
            textAddress.setText(address.getAddress());
        }

        @OnClick(R.id.image_location)
        void locationIconTapped() {
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            openAddressInMap(latLng);
        }

        private void openAddressInMap(LatLng latLng) {
            Uri mapUri = Uri.parse("geo:0,0?q=" + latLng.latitude + "," + latLng.longitude + address.getAddress());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            mActivity.startActivity(mapIntent);
        }
    }
}

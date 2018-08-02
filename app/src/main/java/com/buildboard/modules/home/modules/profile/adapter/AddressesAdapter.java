package com.buildboard.modules.home.modules.profile.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.buildboard.R;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.dialogs.PopUpHelper;
import com.buildboard.http.DataManager;
import com.buildboard.modules.home.modules.profile.LocationAddressActivity;
import com.buildboard.modules.home.modules.profile.models.addresses.addaddress.AddAddressResponse;
import com.buildboard.modules.home.modules.profile.models.addresses.getaddress.AddressListData;
import com.buildboard.modules.home.modules.profile.models.addresses.primaryaddress.PrimaryAddressResponse;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.Utils;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.ViewHolder> {

    private Activity mActivity;
    private ArrayList<AddressListData> mAddressList;

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

        @BindString(R.string.confirm_primary_address)
        String stringPrimaryAddress;
        @BindString(R.string.msg_please_wait)
        String stringPleaseWait;
        @BindString(R.string.success_primary_address_changed)
        String stringAddChanged;

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

        @OnClick(R.id.constraint_root)
        void addressRowTapped() {
            PopUpHelper.showConfirmPopup(mActivity, stringPrimaryAddress, new PopUpHelper.ConfirmPopUp() {
                @Override
                public void onConfirm(boolean isConfirm) {
                    makePrimaryAddress(address.getId());
                }

                @Override
                public void onDismiss(boolean isDismiss) {

                }
            });
        }

        //TODO extract it in utils
        private void openAddressInMap(LatLng latLng) {
            Uri mapUri = Uri.parse("geo:0,0?q=" + latLng.latitude + "," + latLng.longitude + address.getAddress());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            mActivity.startActivity(mapIntent);
        }

        private void makePrimaryAddress(String addressId) {
            ProgressHelper.start(mActivity, stringPleaseWait);
            DataManager.getInstance().makePrimaryAddress(mActivity, addressId, new DataManager.DataManagerListener() {
                @Override
                public void onSuccess(Object response) {
                    ProgressHelper.stop();
                    if (response == null) return;

                    Toast.makeText(mActivity, response.toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Object error) {
                    ProgressHelper.stop();
                }
            });
        }
    }
}

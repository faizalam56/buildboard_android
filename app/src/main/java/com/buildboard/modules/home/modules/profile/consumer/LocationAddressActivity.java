package com.buildboard.modules.home.modules.profile.consumer;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.http.DataManager;
import com.buildboard.modules.home.modules.profile.consumer.adapter.AddressesAdapter;
import com.buildboard.modules.home.modules.profile.consumer.models.addresses.addaddress.AddAddressRequest;
import com.buildboard.modules.home.modules.profile.consumer.models.addresses.getaddress.AddressListData;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.Utils;
import com.buildboard.view.SimpleDividerItemDecoration;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LocationAddressActivity extends AppCompatActivity implements AppConstant {

    @BindView(R.id.title)
    BuildBoardTextView textTitle;
    @BindView(R.id.fab)
    FloatingActionButton fabAddMoreAddresses;
    @BindView(R.id.recycler_addresses)
    RecyclerView recyclerAddresses;
    @BindView(R.id.constraint_root)
    ConstraintLayout constraintLayout;
    @BindString(R.string.my_location_address)
    String stringTitle;
    @BindString(R.string.msg_please_wait)
    String stringPleaseWait;

    private AddressesAdapter addressesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_address);
        ButterKnife.bind(this);

        textTitle.setText(stringTitle);

        if (!ConnectionDetector.isNetworkConnected(this)) return;

        getAddresses();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) return;

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PLACE_PICKER_REQUEST:
                    getAddressLatLng(PlacePicker.getPlace(this, data));
                    break;
            }
        }
    }

    @OnClick(R.id.fab)
    void addMoreAddressesTapped() {
        try {
            PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
            Intent intent = intentBuilder.build(this);
            startActivityForResult(intent, PLACE_PICKER_REQUEST);

        } catch (GooglePlayServicesRepairableException
                | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    private void setRecycler(ArrayList<AddressListData> addressListData) {
        addressesAdapter = new AddressesAdapter(this, addressListData);
        recyclerAddresses.setLayoutManager(new LinearLayoutManager(this));
        recyclerAddresses.addItemDecoration(new SimpleDividerItemDecoration(this));
        recyclerAddresses.setAdapter(addressesAdapter);
    }

    private void getAddresses() {
        ProgressHelper.start(this, stringPleaseWait);
        DataManager.getInstance().getAddresses(this, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                if (response == null) return;

                ArrayList<AddressListData> addressListData = (ArrayList<AddressListData>) response;
                setRecycler(addressListData);
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
                Utils.showError(LocationAddressActivity.this, constraintLayout, error);
            }
        });
    }

    private void getAddressLatLng(final Place place) {

        AddAddressRequest addAddressRequest = new AddAddressRequest();
        addAddressRequest.setAddress(place.getAddress().toString());
        addAddressRequest.setLatitude(String.valueOf(place.getLatLng().latitude));
        addAddressRequest.setLongitude(String.valueOf(place.getLatLng().longitude));

        ProgressHelper.start(this, stringPleaseWait);
        DataManager.getInstance().addAddress(this, addAddressRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                if (response == null) return;

                getAddresses();
                addressesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
                Utils.showError(LocationAddressActivity.this, constraintLayout, error);
            }
        });
    }
}

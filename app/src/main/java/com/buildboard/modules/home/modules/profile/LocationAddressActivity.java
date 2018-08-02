package com.buildboard.modules.home.modules.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.http.DataManager;
import com.buildboard.modules.home.modules.profile.adapter.AddressesAdapter;
import com.buildboard.modules.home.modules.profile.models.addresses.AddressListData;
import com.buildboard.modules.signup.contractor.previouswork.PreviousWorkActivity;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.Utils;
import com.buildboard.view.SimpleDividerItemDecoration;
import com.buildboard.view.SnackBarFactory;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_address);
        ButterKnife.bind(this);

        textTitle.setText(stringTitle);

        if (!ConnectionDetector.isNetworkConnected(this)) return;

        getAddresses();
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
        AddressesAdapter addressesAdapter = new AddressesAdapter(this, addressListData);
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
}

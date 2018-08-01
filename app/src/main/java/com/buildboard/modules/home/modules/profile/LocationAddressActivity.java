package com.buildboard.modules.home.modules.profile;

import android.content.Intent;
import android.location.Address;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.modules.home.modules.mailbox.draft.drafts.adapters.DraftsAdapter;
import com.buildboard.modules.home.modules.profile.adapter.AddressesAdapter;
import com.buildboard.view.SimpleDividerItemDecoration;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;

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

    @BindString(R.string.my_location_address)
    String stringTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_address);
        ButterKnife.bind(this);

        textTitle.setText(stringTitle);
        setServicesRecycler();
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

    private void setServicesRecycler() {
        AddressesAdapter addressesAdapter = new AddressesAdapter(this);
        recyclerAddresses.setLayoutManager(new LinearLayoutManager(this));
        recyclerAddresses.addItemDecoration(new SimpleDividerItemDecoration(this));
        recyclerAddresses.setAdapter(addressesAdapter);
    }
}

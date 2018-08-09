package com.buildboard.modules.home.modules.profile.consumer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ProgressBar;

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

public class LocationAddressActivity extends AppCompatActivity
        implements AppConstant, AddressesAdapter.IChangePrimaryAddressListener {

    @BindView(R.id.title)
    BuildBoardTextView textTitle;
    @BindView(R.id.fab)
    FloatingActionButton fabAddMoreAddresses;
    @BindView(R.id.recycler_addresses)
    RecyclerView recyclerAddresses;
    @BindView(R.id.constraint_root)
    ConstraintLayout constraintLayout;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindString(R.string.my_location_address)
    String stringTitle;
    @BindString(R.string.msg_please_wait)
    String stringPleaseWait;
    @BindString(R.string.undo)
    String stringUndo;

    private AddressesAdapter mAddressesAdapter;
    private ArrayList<AddressListData> mAddressListData;
    private Paint p = new Paint();

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

    @Override
    public void onPrimaryAddressChanged() {
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
        mAddressesAdapter = new AddressesAdapter(this, addressListData, progressBar);
        recyclerAddresses.setLayoutManager(new LinearLayoutManager(this));
        recyclerAddresses.addItemDecoration(new SimpleDividerItemDecoration(this));
        recyclerAddresses.setAdapter(mAddressesAdapter);
        enableSwipe();
    }

    private void getAddresses() {
        ProgressHelper.showProgressBar(this, progressBar);
        DataManager.getInstance().getAddresses(this, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.hideProgressBar();
                if (response == null) return;

                mAddressListData = (ArrayList<AddressListData>) response;
                setRecycler(mAddressListData);
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.hideProgressBar();
                Utils.showError(LocationAddressActivity.this, constraintLayout, error);
            }
        });
    }

    private void getAddressLatLng(final Place place) {

        AddAddressRequest addAddressRequest = new AddAddressRequest();
        addAddressRequest.setAddress(place.getAddress().toString());
        addAddressRequest.setLatitude(String.valueOf(place.getLatLng().latitude));
        addAddressRequest.setLongitude(String.valueOf(place.getLatLng().longitude));

        ProgressHelper.showProgressBar(this, progressBar);
        DataManager.getInstance().addAddress(this, addAddressRequest, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.hideProgressBar();
                if (response == null) return;

                getAddresses();
                mAddressesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.hideProgressBar();
                Utils.showError(LocationAddressActivity.this, constraintLayout, error);
            }
        });
    }

    private void enableSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    final AddressListData deletedAddress = mAddressListData.get(position);
                    final int deletedPosition = position;
                    mAddressesAdapter.removeItem(position);
                } else {
                    final AddressListData deletedAddress = mAddressListData.get(position);
                    final int deletedPosition = position;
                    mAddressesAdapter.removeItem(position);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX > 0) {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_trash);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    } else if (dX < 0) {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_trash);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
                int dragFlags = 0;
                int swipeFlags = position == 0 ? 0 : ItemTouchHelper.START | ItemTouchHelper.END;

                return makeMovementFlags(dragFlags, swipeFlags);
            }
        };


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerAddresses);
    }
}

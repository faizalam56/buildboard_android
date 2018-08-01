package com.buildboard.modules.home.modules.profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.buildboard.R;
import com.buildboard.customviews.BuildBoardTextView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationAddressActivity extends AppCompatActivity {

    @BindView(R.id.title)
    BuildBoardTextView textTitle;
    @BindView(R.id.text_address)
    BuildBoardTextView textAddress;
    @BindView(R.id.text_add_more)
    BuildBoardTextView textAddMore;

    @BindString(R.string.my_location_address)
    String stringTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_address);
        ButterKnife.bind(this);

        textTitle.setText(stringTitle);
    }
}

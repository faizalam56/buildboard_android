package com.buildboard.modules.home.modules.marketplace;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.buildboard.R;
import com.buildboard.customviews.BuildBoardTextView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ContractorProfile extends AppCompatActivity {

    @BindView(R.id.title)
    BuildBoardTextView textTitle;

    @BindString(R.string.trending_services)
    String stringTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_profile);
        ButterKnife.bind(this);

        textTitle.setText(stringTitle);
    }
}

package com.buildboard.modules.home.modules.profile.consumer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.buildboard.R;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.modules.home.modules.profile.consumer.adapter.PreferredContractorAdapter;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PreferredContractorActivity extends AppCompatActivity {

    @BindView(R.id.title)
    BuildBoardTextView textTitle;
    @BindView(R.id.recycler_preferred_contractors)
    RecyclerView recyclerPrefContractors;

    @BindString(R.string.my_preferred_contractor)
    String stringTitle;

    private PreferredContractorAdapter mPreferredContractorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferred_contractor);
        ButterKnife.bind(this);

        textTitle.setText(stringTitle);
        setRecycler();
    }

    private void setRecycler() {
        mPreferredContractorAdapter = new PreferredContractorAdapter(this, recyclerPrefContractors);
        recyclerPrefContractors.setLayoutManager(new LinearLayoutManager(this));
        recyclerPrefContractors.setAdapter(mPreferredContractorAdapter);
    }
}

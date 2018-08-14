package com.buildboard.modules.home.modules.marketplace.contractors;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.fonts.FontHelper;
import com.buildboard.modules.home.modules.marketplace.contractor_projecttype.models.ContractorByProjectTypeListData;
import com.buildboard.modules.home.modules.marketplace.contractors.adapters.ViewAllNearByContractorAdapter;
import com.buildboard.modules.home.modules.marketplace.contractors.adapters.ViewAllTrendingContractorAdapter;
import com.buildboard.modules.home.modules.marketplace.models.NearByContractor;
import com.buildboard.modules.home.modules.marketplace.models.TrendingService;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.buildboard.utils.Utils.showProgressColor;

public class ViewAllContractorsActivity extends AppCompatActivity implements AppConstant {


    private ViewAllNearByContractorAdapter mViewAllContractorsAdapter;
    private ViewAllTrendingContractorAdapter mViewAllTrendingContractorsAdapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_contractors)
    RecyclerView recyclerContractors;
    @BindView(R.id.text_project_type)
    BuildBoardTextView textProjectType;
    @BindView(R.id.text_nodata)
    TextView textNodata;
    @BindView(R.id.constraint_root)
    ConstraintLayout constraintRoot;
    @BindView(R.id.title)
    BuildBoardTextView textTitle;
    @BindView(R.id.progress_bar_service)
    ProgressBar progressBar;

    @BindString(R.string.contractors)
    String stringContractors;
    @BindString(R.string.near_by_contractors)
    String stringNearByContractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractors);
        ButterKnife.bind(this);

        showProgressColor(this, progressBar);
        textTitle.setText(stringContractors);
        setFont();
        getIntentData();
    }

    private void getIntentData() {
        if (getIntent().hasExtra(DATA)) {

            if (getIntent().getStringExtra(INTENT_TITLE).equals(stringNearByContractor)) {
                ArrayList<NearByContractor> contractorArrayList= getIntent().getExtras().getParcelableArrayList(DATA);
                setAdapterForNearByContractor(contractorArrayList);
            } else {
                ArrayList<TrendingService> contractorArrayList= getIntent().getExtras().getParcelableArrayList(DATA);
                setAdapterForTrendingContractor(contractorArrayList);
            }
        }

        if (getIntent().hasExtra(INTENT_TITLE)) {
            textProjectType.setText(getIntent().getStringExtra(INTENT_TITLE));
        }
    }

    private void setAdapterForNearByContractor(ArrayList<NearByContractor> mList) {
        if (mList != null) {
            mViewAllContractorsAdapter = new ViewAllNearByContractorAdapter(this, mList);
            recyclerContractors.setLayoutManager(new LinearLayoutManager(this));
            recyclerContractors.setAdapter(mViewAllContractorsAdapter);
        } else {
            showNoDataText();
        }
    }

    private void setAdapterForTrendingContractor(ArrayList<TrendingService> mList) {
        if (mList != null) {
            mViewAllTrendingContractorsAdapter = new ViewAllTrendingContractorAdapter(this, mList);
            recyclerContractors.setLayoutManager(new LinearLayoutManager(this));
            recyclerContractors.setAdapter(mViewAllTrendingContractorsAdapter);
        } else {
            showNoDataText();
        }
    }

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, textProjectType);
    }

    private void showNoDataText() {
        textNodata.setVisibility(View.VISIBLE);
    }
}
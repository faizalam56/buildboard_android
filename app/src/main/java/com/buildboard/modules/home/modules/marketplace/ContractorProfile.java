package com.buildboard.modules.home.modules.marketplace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.http.DataManager;
import com.buildboard.modules.home.modules.marketplace.models.contractorprofile.ContractorInfo;
import com.buildboard.modules.home.modules.profile.consumer.ReviewActivity;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.Utils;
import com.squareup.picasso.Picasso;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContractorProfile extends AppCompatActivity implements AppConstant {

    @BindView(R.id.title)
    BuildBoardTextView textTitle;
    @BindView(R.id.linear_root)
    LinearLayout linearLayout;
    @BindView(R.id.progress_bar_profile)
    ProgressBar progressBar;
    @BindView(R.id.text_summary_company)
    BuildBoardTextView textCompanySummary;
    @BindView(R.id.text_address)
    BuildBoardTextView textAddress;
    @BindView(R.id.text_working_radius)
    BuildBoardTextView textWorkingRadius;
    @BindView(R.id.text_type_of_work)
    BuildBoardTextView textTypeOfWork;
    @BindView(R.id.text_verified)
    BuildBoardTextView textVerified;
    @BindView(R.id.text_name)
    BuildBoardTextView textName;
    @BindView(R.id.text_email)
    BuildBoardTextView textEmail;
    @BindView(R.id.text_phone)
    BuildBoardTextView textPhone;
    @BindView(R.id.image_profile)
    ImageView imageProfile;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.image_verified)
    ImageView imageVerified;

    @BindString(R.string.trending_services)
    String stringTitle;

    @BindColor(R.color.colorPrimary)
    int colorPrimary;
    @BindColor(R.color.colorWhite)
    int colorWhite;

    private String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_profile);
        ButterKnife.bind(this);

        textTitle.setText(stringTitle);
        changeToolbarColor(colorPrimary, colorWhite);

        if (!ConnectionDetector.isNetworkConnected(this)) {
            ConnectionDetector.createSnackBar(this, linearLayout);
            return;
        }

        getIntentData();
    }

    private void getIntentData() {
        if (getIntent().hasExtra(INTENT_TRENDING_USER_ID)) {
            mUserId = getIntent().getStringExtra(INTENT_TRENDING_USER_ID);
            getContractorProfile();
        }
    }

    private void getContractorProfile() {
        ProgressHelper.showProgressBar(this, progressBar);
        DataManager.getInstance().getContractorProfile(this, mUserId, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.hideProgressBar();
                if (response == null) return;

                ContractorInfo contractorInfo = (ContractorInfo) response;
                setProfileData(contractorInfo);
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.hideProgressBar();
                Utils.showError(ContractorProfile.this, linearLayout, error);
            }
        });
    }

    private void setProfileData(ContractorInfo userProfile) {
        textName.setText(userProfile.getBusinessName());
        textEmail.setText(userProfile.getEmail());
        textPhone.setText(userProfile.getPhoneNo());

        imageVerified.setVisibility(userProfile.getVerified() == 1 ? View.VISIBLE : View.GONE);
        textVerified.setVisibility(userProfile.getVerified() == 1 ? View.VISIBLE : View.GONE);

        Picasso.get().load(userProfile.getImage()).into(imageProfile);
        textCompanySummary.setText(!TextUtils.isEmpty(userProfile.getSummary()) ? userProfile.getSummary() : "-");
        textAddress.setText(!TextUtils.isEmpty(userProfile.getBusinessAddress()) ? userProfile.getBusinessAddress() : "-");

        StringBuilder typeOfProject = new StringBuilder();
        for (int i = 0; i < userProfile.getProjectType().size(); i++) {
            typeOfProject = typeOfProject.append(userProfile.getProjectType().get(i).getTitle());

            if (i != userProfile.getProjectType().size() - 1)
                typeOfProject.append(", ");

        }
        textTypeOfWork.setText(typeOfProject != null ? typeOfProject : "-");

        String radius = userProfile.getMinAreaRadius() + " - " + userProfile.getMaxAreaRadius();
        textWorkingRadius.setText(!TextUtils.isEmpty(radius) ? radius : "-");
    }

    @OnClick(R.id.row_reviews)
    void rowReviewTapped() {
        if (!ConnectionDetector.isNetworkConnected(this)) {
            ConnectionDetector.createSnackBar(this, linearLayout);
            return;
        }

        Intent intent = new Intent(this, ReviewActivity.class);
        startActivity(intent);
    }
    private void changeToolbarColor(int background, int text) {
        toolbar.setBackgroundColor(background);
        textTitle.setTextColor(text);
    }
}

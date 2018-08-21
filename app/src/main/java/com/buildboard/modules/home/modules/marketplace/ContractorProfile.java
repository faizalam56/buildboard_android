package com.buildboard.modules.home.modules.marketplace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.http.DataManager;
import com.buildboard.modules.home.modules.mailbox.inbox.InboxActivity;
import com.buildboard.modules.home.modules.marketplace.models.contractorprofile.ContractorInfo;
import com.buildboard.modules.home.modules.profile.consumer.ReviewActivity;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.Utils;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContractorProfile extends AppCompatActivity implements AppConstant {

    @BindView(R.id.title)
    BuildBoardTextView textTitle;
    @BindView(R.id.relative_root)
    RelativeLayout relativeLayout;
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
    @BindView(R.id.text_error_message)
    BuildBoardTextView textErrorMessage;
    @BindView(R.id.scrollBar)
    ScrollView scrollView;
    @BindView(R.id.rating)
    RatingBar ratingBar;

    @BindString(R.string.contractor_details)
    String stringTitle;
    @BindString(R.string.miles)
    String stringMiles;

    @BindColor(R.color.colorPrimary)
    int colorPrimary;
    @BindColor(R.color.colorWhite)
    int colorWhite;

    private String mUserId;
    private LatLng mLatLng;
    private ContractorInfo mContractorInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_profile);
        ButterKnife.bind(this);

        textTitle.setText(stringTitle);
        changeToolbarColor(colorPrimary, colorWhite);

        boolean isNetworkConnected = ConnectionDetector.isNetworkConnected(this);

        textErrorMessage.setVisibility(isNetworkConnected ? View.GONE : View.VISIBLE);
        scrollView.setVisibility(isNetworkConnected ? View.VISIBLE : View.GONE);

        if (!isNetworkConnected) {
            ConnectionDetector.createSnackBar(this, relativeLayout);
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
        scrollView.setVisibility(View.GONE);
        DataManager.getInstance().getContractorProfile(this, mUserId, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.hideProgressBar();
                scrollView.setVisibility(View.VISIBLE);
                if (response == null) return;

                ContractorInfo contractorInfo = (ContractorInfo) response;
                mContractorInfo = contractorInfo;
                setProfileData(contractorInfo);
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.hideProgressBar();
                Utils.showError(ContractorProfile.this, relativeLayout, error);
            }
        });
    }

    private void setProfileData(ContractorInfo userProfile) {
        Picasso.get().load(userProfile.getImage()).into(imageProfile);
        textName.setText(userProfile.getBusinessName());
        textEmail.setText(userProfile.getEmail());
        textPhone.setText(userProfile.getPhoneNo());
        imageVerified.setVisibility(userProfile.getVerified() == 1 ? View.VISIBLE : View.GONE);
        textVerified.setVisibility(userProfile.getVerified() == 1 ? View.VISIBLE : View.GONE);

        textCompanySummary.setText(!TextUtils.isEmpty(userProfile.getSummary()) ? userProfile.getSummary() : "-");
        textAddress.setText(!TextUtils.isEmpty(userProfile.getBusinessAddress()) ? userProfile.getBusinessAddress() : "-");

        StringBuilder typeOfProject = new StringBuilder();
        for (int i = 0; i < userProfile.getProjectType().size(); i++) {
            typeOfProject = typeOfProject.append(userProfile.getProjectType().get(i).getTitle());

            if (i != userProfile.getProjectType().size() - 1)
                typeOfProject.append(", ");

        }

        textTypeOfWork.setText(typeOfProject != null ? typeOfProject : "-");

        String radius = userProfile.getMinAreaRadius() + " - " + userProfile.getMaxAreaRadius()
                + " " + stringMiles;
        textWorkingRadius.setText(!TextUtils.isEmpty(radius) ? radius : "-");

        if (userProfile.getRatingCount() != null)
            ratingBar.setRating(Float.valueOf(userProfile.getRatingCount()));

        if (userProfile.getLatitude() != null && userProfile.getLongitude() != null)
            mLatLng = new LatLng(userProfile.getLatitude(), userProfile.getLongitude());
    }

    @OnClick(R.id.row_reviews)
    void rowReviewTapped() {
        if (!ConnectionDetector.isNetworkConnected(this)) {
            ConnectionDetector.createSnackBar(this, relativeLayout);
            return;
        }

        Intent intent = new Intent(this, ReviewActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.text_documents)
    public void documentsTapped(){ }

    @OnClick(R.id.text_previous_work)
    public void previousWorkTapped(){ }


    @OnClick(R.id.image_location)
    void locationIconTapped() {
        Utils.openAddressInMap(ContractorProfile.this, mLatLng, "");
    }

    @OnClick(R.id.fab_chat)
    void fabChatTapped() {
        if (!ConnectionDetector.isNetworkConnected(this)) {
            ConnectionDetector.createSnackBar(this, relativeLayout);
            return;
        }

        Intent intent = new Intent(this, InboxActivity.class);
        intent.putExtra(DATA, mContractorInfo.getUserId());
        startActivity(intent);
    }

    private void changeToolbarColor(int background, int text) {
        toolbar.setBackgroundColor(background);
        textTitle.setTextColor(text);
    }

    private void showHideProgress(boolean status) {
        progressBar.setVisibility(status ? View.GONE : View.VISIBLE);
        scrollView.setVisibility(status ? View.VISIBLE : View.GONE);
    }
}

package com.buildboard.modules.home.modules.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.customviews.RoundedCornersTransform;
import com.buildboard.http.DataManager;
import com.buildboard.modules.home.modules.profile.consumer.EditProfileActivity;
import com.buildboard.modules.home.modules.profile.consumer.LocationAddressActivity;
import com.buildboard.modules.home.modules.profile.consumer.PreferredContractorActivity;
import com.buildboard.modules.home.modules.profile.consumer.ReviewActivity;
import com.buildboard.modules.home.modules.profile.consumer.models.ProfileData;
import com.buildboard.modules.signup.contractor.previouswork.PreviousWorkActivity;
import com.buildboard.preferences.AppPreference;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.buildboard.utils.Utils.showProgressColor;

public class ProfileFragment extends Fragment
        implements EditProfileActivity.UpdateProfileListener, AppConstant {

    private static ProfileFragment sFragment;
    private Unbinder unbinder;
    public ProfileData profileData;

    @BindView(R.id.image_profile)
    ImageView imageProfile;
    @BindView(R.id.text_name)
    BuildBoardTextView textName;
    @BindView(R.id.text_email)
    BuildBoardTextView textEmail;
    @BindView(R.id.text_phone)
    BuildBoardTextView textPhone;
    @BindView(R.id.container_root)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.row_my_preferred_contractor)
    RelativeLayout relativeLayoutPrefContractor;
    @BindView(R.id.row_my_location)
    RelativeLayout relativeLayoutLocation;
    @BindView(R.id.divider_my_pref_contractor)
    View dividerContractor;
    @BindView(R.id.divider_my_location_addresses)
    View dividerLocation;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.swipe_root)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.constraint_root)
    ConstraintLayout constraintRoot;

    public static ProfileFragment newInstance() {
        if (sFragment == null)
            sFragment = new ProfileFragment();

        return sFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        showProgressColor(getActivity(), progressBar);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getProfile();
            }
        });

        if (AppPreference.getAppPreference(getActivity()).getBoolean(IS_CONTRACTOR)) {
            relativeLayoutPrefContractor.setVisibility(View.GONE);
            relativeLayoutLocation.setVisibility(View.GONE);
            dividerContractor.setVisibility(View.GONE);
            dividerLocation.setVisibility(View.GONE);
        }

        if (profileData != null) setProfileData(profileData);
        else getProfile();

        return view;
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setProfileData(ProfileData profileData) {
        textName.setText(profileData.getFirstName() + " " + profileData.getLastName());
        textEmail.setText(profileData.getEmail());
        textPhone.setText(profileData.getPhoneNo());
        Picasso.get().load(profileData.getImage()).transform(new RoundedCornersTransform()).into(imageProfile);
    }

    @OnClick(R.id.row_my_preferred_contractor)
    void rowPreferredContractorTapped() {
        if (!ConnectionDetector.isNetworkConnected(getActivity())) {
            ConnectionDetector.createSnackBar(getActivity(), mCoordinatorLayout);
            return;
        }

        Intent intent = new Intent(getActivity(), PreferredContractorActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.row_my_location)
    void rowLocationTapped() {
        if (!ConnectionDetector.isNetworkConnected(getActivity())) {
            ConnectionDetector.createSnackBar(getActivity(), mCoordinatorLayout);
            return;
        }

        Intent intent = new Intent(getActivity(), LocationAddressActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.row_reviews)
    void rowReviewTapped() {
        if (!ConnectionDetector.isNetworkConnected(getActivity())) {
            ConnectionDetector.createSnackBar(getActivity(), mCoordinatorLayout);
            return;
        }

        Intent intent = new Intent(getActivity(), ReviewActivity.class);
        startActivity(intent);
    }

    private void getProfile() {
        if (!ConnectionDetector.isNetworkConnected(getActivity())) {
            ConnectionDetector.createSnackBar(getActivity(), mCoordinatorLayout);

            return;
        }

        showProgressBar();
        DataManager.getInstance().getProfile(getActivity(), new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                hideProgressBar();
                profileData = (ProfileData) response;
                setProfileData(profileData);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Object error) {
                hideProgressBar();
                Utils.showError(getActivity(), constraintRoot, error);
            }
        });
    }

    @Override
    public void updateProfile() {
        if (ConnectionDetector.isNetworkConnected(getActivity()))
            getProfile();
        else ConnectionDetector.createSnackBar(getActivity(), mCoordinatorLayout);
    }
}

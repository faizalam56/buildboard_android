package com.buildboard.modules.home.modules.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.buildboard.R;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.customviews.RoundedCornersTransform;
import com.buildboard.http.DataManager;
import com.buildboard.modules.home.modules.profile.models.ProfileData;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.ProgressHelper;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ProfileFragment extends Fragment implements EditProfileActivity.UpdateProfileListener {

    private static ProfileFragment sFragment;

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

    private Unbinder unbinder;
    private ProfileData profileData;

    public static ProfileFragment newInstance() {
        if (sFragment == null)
            sFragment = new ProfileFragment();

        return sFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);

        if (ConnectionDetector.isNetworkConnected(getActivity())) {
            if (profileData != null) setProfileData(profileData);
            else getProfile();
        } else {
            ConnectionDetector.createSnackBar(getActivity(), mCoordinatorLayout);
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setProfileData(ProfileData profileData) {
        textName.setText(profileData.getFirstName());
        textEmail.setText(profileData.getEmail());
        textPhone.setText(profileData.getPhoneNo());
        Picasso.get().load(profileData.getImage()).transform(new RoundedCornersTransform()).into(imageProfile);
    }

    @OnClick(R.id.row_my_preferred_contractor)
    void rowPreferredContractorTapped() {
        Intent intent = new Intent(getActivity(), PreferredContractorActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.row_my_location)
    void rowLocationTapped() {
        Intent intent = new Intent(getActivity(), LocationAddressActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.row_reviews)
    void rowReviewTapped() {
        Intent intent = new Intent(getActivity(), ReviewActivity.class);
        startActivity(intent);
    }

    private void getProfile() {
        ProgressHelper.start(getActivity(), getString(R.string.msg_please_wait));
        DataManager.getInstance().getProfile(getActivity(), new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                profileData = (ProfileData) response;
                setProfileData(profileData);
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
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

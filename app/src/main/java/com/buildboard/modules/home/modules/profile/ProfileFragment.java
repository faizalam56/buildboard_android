package com.buildboard.modules.home.modules.profile;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.buildboard.R;
import com.buildboard.customviews.BuildBoardButton;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.http.DataManager;
import com.buildboard.modules.home.modules.profile.adapter.MyContractorsAdapter;
import com.buildboard.modules.home.modules.profile.models.ProfileData;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.ProgressHelper;
import com.squareup.picasso.Picasso;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ProfileFragment extends Fragment {

    private static ProfileFragment sFragment;

    @BindView(R.id.image_profile)
    ImageView imageProfile;
    @BindView(R.id.text_name)
    BuildBoardTextView textName;
    @BindView(R.id.text_email)
    BuildBoardTextView textEmail;
    @BindView(R.id.text_phone)
    BuildBoardTextView textPhone;
    @BindView(R.id.button_out_for_bid)
    BuildBoardButton buttonOutForBid;
    @BindView(R.id.button_current_project)
    BuildBoardButton buttonCurrentProject;
    @BindView(R.id.button_completed_projects)
    BuildBoardButton buttonCompletedProjects;
    @BindView(R.id.button_my_contractors)
    BuildBoardButton buttonMyContractors;
    @BindView(R.id.recycler_my_contactor)
    RecyclerView recyclerMyContractors;
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
        setProjectsRecycler();

        if(ConnectionDetector.isNetworkConnected(getActivity())) {
            if (profileData != null) setProfileData(profileData);
        }
        else ConnectionDetector.createSnackBar(getActivity(),mCoordinatorLayout);

        return view;
    }

    private void setProjectsRecycler() {
        MyContractorsAdapter myContractorsAdapter = new MyContractorsAdapter(getActivity());
        recyclerMyContractors.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerMyContractors.setAdapter(myContractorsAdapter);
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

    private void setProfileData(ProfileData profileData) {
        textName.setText(profileData.getFirstName());
        textEmail.setText(profileData.getEmail());
        textPhone.setText(profileData.getPhoneNo());
        Picasso.get().load(profileData.getImage()).into(imageProfile);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(ConnectionDetector.isNetworkConnected(getActivity()))
        getProfile();
        else ConnectionDetector.createSnackBar(getActivity(),mCoordinatorLayout);
    }
}

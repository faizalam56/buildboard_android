package com.buildboard.modules.home.modules.projects;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buildboard.R;
import com.buildboard.modules.home.modules.projects.models.ProjectFormDetails;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CreateProjectDescriptionFragment extends Fragment{

    private ProjectFormDetails mProjectAllTypesData;
    private String mSelectedCategory;
    private Unbinder unbinder;

    public static CreateProjectDescriptionFragment newInstance() {
        return new CreateProjectDescriptionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_project_description, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void navigateFragment(Fragment fragment) {
        if (getActivity() != null) {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_home_container, fragment).commit();
        }
    }
}

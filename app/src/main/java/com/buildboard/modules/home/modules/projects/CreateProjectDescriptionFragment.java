package com.buildboard.modules.home.modules.projects;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.buildboard.R;
import com.buildboard.modules.home.modules.profile.consumer.models.reviews.Project;
import com.buildboard.modules.home.modules.projects.models.ProjectFormDetails;
import com.buildboard.modules.home.modules.projects.models.Task;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.buildboard.constants.AppConstant.INTENT_PROJECT_TYPE_DATA;
import static com.buildboard.constants.AppConstant.TASK;

public class CreateProjectDescriptionFragment extends Fragment{

    private ProjectFormDetails mProjectAllTypesData;
    private String mSelectedCategory;
    private Unbinder unbinder;
    private Task task;

    public static CreateProjectDescriptionFragment newInstance() {
        return new CreateProjectDescriptionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_project_description, container, false);
        unbinder = ButterKnife.bind(this, view);
        getDataFromBundle();
        return view;
    }

    private void getDataFromBundle() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            task = bundle.getParcelable(TASK);
            Toast.makeText(getActivity(), ""+task.getQuestions(), Toast.LENGTH_SHORT).show();
        }
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

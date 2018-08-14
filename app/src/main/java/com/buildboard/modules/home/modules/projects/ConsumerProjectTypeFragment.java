package com.buildboard.modules.home.modules.projects;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.buildboard.R;
import com.buildboard.http.DataManager;
import com.buildboard.interfaces.IRecyclerItemClickListener;
import com.buildboard.modules.home.HomeActivity;
import com.buildboard.modules.home.modules.projects.adapters.ConsumerProjectTypeAdapter;
import com.buildboard.modules.home.modules.projects.models.ProjectAllType;
import com.buildboard.modules.home.modules.projects.models.ProjectFormDetails;
import com.buildboard.modules.login.LoginActivity;
import com.buildboard.modules.login.models.sociallogin.SocialLoginRequest;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.ProgressHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.buildboard.constants.AppConstant.INTENT_PROJECT_TYPE_DATA;

public class ConsumerProjectTypeFragment extends Fragment
        implements IRecyclerItemClickListener, HomeActivity.OnBackPressedListener {

    private Unbinder unbinder;
    private ConstraintLayout containers;
    private ArrayList<ProjectAllType> projectsData;

    @BindView(R.id.recycler_all_project_type)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    public static ConsumerProjectTypeFragment newInstance() {
        return new ConsumerProjectTypeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_all_type_project, container, false);
        unbinder = ButterKnife.bind(this, view);

        if (getActivity() != null) ((HomeActivity) getActivity()).setOnBackPressedListener(this);

        if (ConnectionDetector.isNetworkConnected(getActivity())) {
            getAllTypeOfProject();
        } else {
            ConnectionDetector.createSnackBar(getActivity(), containers);
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity) Objects.requireNonNull(getActivity())).setTitle(getString(R.string.create_new_project));
    }

    private void getAllTypeOfProject() {
        ProgressHelper.showProgressBar(getActivity(), progressBar);
        DataManager.getInstance().getAllTypeOfProjectList(getActivity(), new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.hideProgressBar();
                projectsData = (ArrayList<ProjectAllType>) response;
                setProjectsRecycler(projectsData);
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
            }
        });
    }

    private void setProjectsRecycler(ArrayList<ProjectAllType> projectsData) {
        ConsumerProjectTypeAdapter mConsumerProjectTypeAdapter = new ConsumerProjectTypeAdapter(getActivity(), projectsData,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mConsumerProjectTypeAdapter);
    }

    @Override
    public void onItemClick(View view, int position, Object data) {
        if(ConnectionDetector.isNetworkConnected(getActivity())) {
            ProjectAllType projectType = (ProjectAllType) data;
            getAllProjectDetails(projectType.getId());
        } else {
            ConnectionDetector.createSnackBar(getActivity(), containers);
        }
    }

   private void getAllProjectDetails(final String selectedProjectId) {
        ProgressHelper.showProgressBar(getActivity(), progressBar);
        DataManager.getInstance().getProjectDetails(getActivity(), new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.hideProgressBar();
                if(isAdded() && response!=null){
                    getProjectById(selectedProjectId);
                }
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.hideProgressBar();
            }
        });
    }

    private void getProjectById(String id){
        ProgressHelper.showProgressBar(getActivity(), progressBar);
        DataManager.getInstance().getSelectedProjectById(getActivity(),id, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.hideProgressBar();
                if(isAdded() && response!=null){
                    ProjectFormDetails projectFormDetails =(ProjectFormDetails) response;

                    ConsumerProjectTypeDetailsFragment consumerProjectTypeDetailsFragment = new ConsumerProjectTypeDetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(INTENT_PROJECT_TYPE_DATA,projectFormDetails);
                    consumerProjectTypeDetailsFragment.setArguments(bundle);
                    navigateFragment(consumerProjectTypeDetailsFragment);

                    /*if(projectFormDetails.getType().equalsIgnoreCase(getString(R.string.other))) {
                        ConsumerProjectTypeDetailsFragment consumerProjectTypeDetailsFragment = new ConsumerProjectTypeDetailsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(INTENT_PROJECT_TYPE_DATA,projectFormDetails);
                        consumerProjectTypeDetailsFragment.setArguments(bundle);
                        navigateFragment(consumerProjectTypeDetailsFragment);
                    }*/ /*else{
                        ConsumerCreateProjectFragment consumerCreateProjectFragment = new ConsumerCreateProjectFragment();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(INTENT_PROJECT_TYPE_DATA,projectFormDetails);
                        consumerCreateProjectFragment.setArguments(bundle);
                        navigateFragment(consumerCreateProjectFragment);
                    }*/
                }
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.hideProgressBar();
            }
        });

    }


    private void navigateFragment(Fragment fragment) {
        if (getActivity() != null) {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_home_container, fragment).commit();
        }
    }
    @Override
    public void doBack() {
        navigateFragment(ConsumerProjectsFragment.newInstance());
    }
}

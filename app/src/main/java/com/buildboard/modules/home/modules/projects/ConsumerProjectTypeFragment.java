package com.buildboard.modules.home.modules.projects;


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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.buildboard.R;
import com.buildboard.http.DataManager;
import com.buildboard.interfaces.IRecyclerItemClickListener;
import com.buildboard.modules.home.HomeActivity;
import com.buildboard.modules.home.modules.projects.adapters.ConsumerProjectTypeAdapter;
import com.buildboard.modules.home.modules.projects.models.ProjectAllType;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.ProgressHelper;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ConsumerProjectTypeFragment extends Fragment
        implements IRecyclerItemClickListener, HomeActivity.OnBackPressedListener {

    @BindView(R.id.recycler_all_project_type)
    RecyclerView recyclerView;
    private Unbinder unbinder;
    private ConstraintLayout containers;
    private ArrayList<ProjectAllType> projectsData;

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
        ProgressHelper.start(getActivity(), getString(R.string.msg_please_wait));
        DataManager.getInstance().getAllTypeOfProjectList(getActivity(), new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
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
        ProjectAllType projectType = (ProjectAllType) data;
        getAllProjectDetails();
    }

    private void getAllProjectDetails() {
        ProgressHelper.start(getActivity(), getString(R.string.msg_please_wait));
        DataManager.getInstance().getProjectDetails(getActivity(), new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                navigateFragment(ConsumerProjectTypeDetailsFragment.newInstance());
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
            }
        });
    }

    private void navigateFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_home_container, fragment).commit();
    }

    @Override
    public void doBack() {
        navigateFragment(ConsumerProjectsFragment.newInstance());
    }
}

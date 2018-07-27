package com.buildboard.modules.home.modules.projects;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.buildboard.R;
import com.buildboard.http.DataManager;
import com.buildboard.modules.home.modules.projects.adapters.SelectAllTypeProjectsAdapter;
import com.buildboard.modules.home.modules.projects.models.ProjectAllType;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.ProgressHelper;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SelectAllTypeProjectFragment extends Fragment {

    private Unbinder unbinder;
    private ConstraintLayout containers;
    private ArrayList<ProjectAllType> projectsData;
    private SelectAllTypeProjectsAdapter mSelectAllTypeProjectsAdapter;

    @BindView(R.id.recycler_all_project_type)
    RecyclerView recyclerView;

    public static SelectAllTypeProjectFragment newInstance() {
        SelectAllTypeProjectFragment fragment = new SelectAllTypeProjectFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_select_all_type_project, container, false);
        unbinder = ButterKnife.bind(this, view);

        if(ConnectionDetector.isNetworkConnected(getActivity())){
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

    private void getAllTypeOfProject() {
        ProgressHelper.start(getActivity(), getString(R.string.msg_please_wait));
        DataManager.getInstance().getAllTypeOfProjectList(getActivity(), new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                projectsData = (ArrayList<ProjectAllType>)response;
                setProjectsRecycler(projectsData);
            }
            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
            }
        });
    }

    private void setProjectsRecycler(ArrayList<ProjectAllType> projectsData) {
        mSelectAllTypeProjectsAdapter = new SelectAllTypeProjectsAdapter(getActivity(),projectsData);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mSelectAllTypeProjectsAdapter);
    }
}

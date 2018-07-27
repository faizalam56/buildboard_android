package com.buildboard.modules.home.modules.projects;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.fonts.FontHelper;
import com.buildboard.http.DataManager;
import com.buildboard.modules.home.modules.profile.ProfileFragment;
import com.buildboard.modules.home.modules.projects.adapters.ProjectsAdapter;
import com.buildboard.modules.home.modules.projects.models.ProjectDetail;
import com.buildboard.modules.home.modules.projects.models.ProjectsData;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.ProgressHelper;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ProjectsFragment extends Fragment implements AppConstant {

    private  ArrayList<ProjectsData> projectsData;
    private ConstraintLayout container;
    private Unbinder unbinder;
    private int mCurrentPage = 1;
    ProjectsAdapter mProjectsAdapter;
    ArrayList<ProjectDetail> mProjectDetails = new ArrayList<>();
    private String mCurrentStatus = STATUS_OPEN;

    @BindView(R.id.recycler_projects)
    RecyclerView recyclerProjects;
    @BindView(R.id.button_create_new_projects)
    Button buttonCreateNewProjects;
    @BindView(R.id.button_completed_projects)
    Button buttonCompletedProjects;
    @BindView(R.id.button_open_projects)
    Button buttonOpenProjects;
    @BindView(R.id.button_saved_projects)
    Button buttonSavedProjects;
    @BindView(R.id.button_current_projects)
    Button buttonCurrentProjects;
    @BindView(R.id.text_projects)
    BuildBoardTextView buildBoardTextProjectType;
    @BindView(R.id.textProjectDetails)
    BuildBoardTextView textProjectDetail;

    public static ProjectsFragment newInstance() {
        ProjectsFragment fragment = new ProjectsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects, container, false);
        unbinder = ButterKnife.bind(this, view);

        setFonts();

        if(ConnectionDetector.isNetworkConnected(getActivity())) {
            ProgressHelper.start(getActivity(), getString(R.string.msg_loading));
            getProjectsList();
        } else {
            ProgressHelper.stop();
            ConnectionDetector.createSnackBar(getActivity(),container);
        }

        return view;
    }

    private void setProjectsRecycler(ArrayList<ProjectDetail> projectDetails, int lastPage) {
        ProgressHelper.start(getActivity(),getString(R.string.msg_loading));
        mProjectDetails.addAll(projectDetails);

        if (mProjectsAdapter == null) {
            ProgressHelper.stop();
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerProjects.setLayoutManager(mLinearLayoutManager);
            mProjectsAdapter = new ProjectsAdapter(getActivity(), mProjectDetails, recyclerProjects);
            recyclerProjects.setAdapter(mProjectsAdapter);
            mProjectsAdapter.setOnLoadMoreListener(new ProjectsAdapter.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    mCurrentPage++;
                    if (mProjectsAdapter != null && mProjectsAdapter.isLoading)
                        getProjectsList();
                }
            });
        } else {
            ProgressHelper.stop();
            recyclerProjects.getAdapter().notifyItemInserted((mProjectDetails.size()));
        }
        if (mProjectsAdapter != null) {
            mProjectsAdapter.setLoading(false);
            if (mCurrentPage == lastPage)
                mProjectsAdapter.setLastPage(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setFonts() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD,buttonCreateNewProjects, buttonCompletedProjects, buttonCurrentProjects, buttonOpenProjects, buttonSavedProjects);
        FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, buildBoardTextProjectType);
    }

    private void navigateFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_home_container, fragment).commit();
    }

    @OnClick(R.id.button_create_new_projects)
    void navigateToFragment(){
        navigateFragment(SelectAllTypeProjectFragment.newInstance());
    }

    @OnClick(R.id.button_completed_projects)
    void completedProjectsTapped() {
        mProjectDetails.clear();
        mProjectsAdapter = null;
        mCurrentStatus = STATUS_COMPLETED;
        buildBoardTextProjectType.setText(getString(R.string.completed_project));
        textProjectDetail.setText(getString(R.string.complete_project_description));
        getProjectsList();
    }

    @OnClick(R.id.button_open_projects)
    void openProjectsTapped() {
        mProjectDetails.clear();
        mProjectsAdapter = null;
        mCurrentStatus = STATUS_OPEN;
        buildBoardTextProjectType.setText(getString(R.string.open_project));
        textProjectDetail.setText(getString(R.string.open_project_description));
        getProjectsList();
    }

    @OnClick(R.id.button_saved_projects)
    void savedProjectsTapped() {
        mProjectDetails.clear();
        mProjectsAdapter = null;
        mCurrentStatus = STATUS_SAVED;
        getProjectsList();
        buildBoardTextProjectType.setText(getString(R.string.saved_project));
        textProjectDetail.setText(getString(R.string.save_project_description));
    }

    @OnClick(R.id.button_current_projects)
    void currentProjectsTapped() {
        mProjectDetails.clear();
        mProjectsAdapter = null;
        mCurrentStatus = STATUS_CURRENT;
        getProjectsList();
        buildBoardTextProjectType.setText(R.string.current_project);
        textProjectDetail.setText(getString(R.string.current_project_description));
    }

    private void getProjectsList() {
        DataManager.getInstance().getProjectsList(getActivity(), mCurrentStatus, mCurrentPage, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                projectsData = (ArrayList<ProjectsData>)response;
                if(mCurrentStatus.equals(STATUS_OPEN)){
                    buildBoardTextProjectType.setText(getString(R.string.open_project));
                    textProjectDetail.setText(getString(R.string.open_project_description));
                }
                ArrayList<ProjectDetail> projectDetails = projectsData.get(0).getDatas();
                setProjectsRecycler(projectDetails, projectsData.get(0).getLastPage());
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
            }
        });
    }
}

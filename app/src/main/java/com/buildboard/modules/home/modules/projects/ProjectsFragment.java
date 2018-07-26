package com.buildboard.modules.home.modules.projects;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.buildboard.modules.home.modules.projects.adapters.ProjectsAdapter;
import com.buildboard.modules.home.modules.projects.models.ProjectDetail;
import com.buildboard.modules.home.modules.projects.models.ProjectsData;
import com.buildboard.utils.ProgressHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ProjectsFragment extends Fragment implements AppConstant {

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
    TextView buildBoardTextProjectType;
    @BindView(R.id.textProjectDetails)
    BuildBoardTextView textProjectDetail;


    private Unbinder unbinder;
    private int mCurrentPage = 1;
    ProjectsAdapter mProjectsAdapter;
    ArrayList<ProjectDetail> mProjectDetails = new ArrayList<>();
    private String mCurrentStatus = STATUS_OPEN;

    public static ProjectsFragment newInstance() {
        ProjectsFragment fragment = new ProjectsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects, container, false);
        unbinder = ButterKnife.bind(this, view);

        setFonts();
        getProjectsList(false);
        return view;
    }

    private void setProjectsRecycler(ArrayList<ProjectDetail> projectDetails, int lastPage) {
        mProjectDetails.addAll(projectDetails);

        if (mProjectsAdapter == null) {
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerProjects.setLayoutManager(mLinearLayoutManager);
            mProjectsAdapter = new ProjectsAdapter(getActivity(), mProjectDetails, recyclerProjects);
            recyclerProjects.setAdapter(mProjectsAdapter);
            mProjectsAdapter.setOnLoadMoreListener(new ProjectsAdapter.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    mCurrentPage++;
                    if (mProjectsAdapter != null && mProjectsAdapter.isLoading)
                        getProjectsList(false);
                }
            });
        } else {
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
        FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, buttonCompletedProjects, buttonCreateNewProjects, buttonCurrentProjects, buttonOpenProjects, buttonSavedProjects);
        FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, buildBoardTextProjectType);
    }

    @OnClick(R.id.button_completed_projects)
    void completedProjectsTapped() {
        mProjectDetails.clear();
        mProjectsAdapter = null;
        mCurrentStatus = STATUS_COMPLETED;
        textProjectDetail.setVisibility(View.VISIBLE);
        buildBoardTextProjectType.setText(getString(R.string.completed_projects));
        textProjectDetail.setText(getString(R.string.complete_project_description));
        getProjectsList(true);
    }

    @OnClick(R.id.button_open_projects)
    void openProjectsTapped() {
        mProjectDetails.clear();
        mProjectsAdapter = null;
        mCurrentStatus = STATUS_OPEN;
        textProjectDetail.setVisibility(View.VISIBLE);
        buildBoardTextProjectType.setText(getString(R.string.open_projects));
        textProjectDetail.setText(getString(R.string.open_project_description));
        getProjectsList(true);
    }

    @OnClick(R.id.button_saved_projects)
    void savedProjectsTapped() {
        mProjectDetails.clear();
        mProjectsAdapter = null;
        textProjectDetail.setVisibility(View.VISIBLE);
        mCurrentStatus = STATUS_SAVED;
        getProjectsList(true);
        buildBoardTextProjectType.setText(getString(R.string.saved_projects));
        textProjectDetail.setText(getString(R.string.save_project_description));
    }

    @OnClick(R.id.button_current_projects)
    void currentProjectsTapped() {
        mProjectDetails.clear();
        mProjectsAdapter = null;
        mCurrentStatus = STATUS_CURRENT;
        textProjectDetail.setVisibility(View.VISIBLE);
        getProjectsList(true);
        buildBoardTextProjectType.setText(getString(R.string.current_projects));
        textProjectDetail.setText(getString(R.string.current_project_description));
    }

    private void getProjectsList(final boolean showProgress) {
        if (showProgress) ProgressHelper.start(getActivity(), getString(R.string.msg_please_wait));
        DataManager.getInstance().getProjectsList(getActivity(), mCurrentStatus, mCurrentPage, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                if (showProgress) ProgressHelper.stop();
                ArrayList<ProjectsData> projectsData = (ArrayList<ProjectsData>)response;
                ArrayList<ProjectDetail> projectDetails = projectsData.get(0).getDatas();
                setProjectsRecycler(projectDetails, projectsData.get(0).getLastPage());
            }

            @Override
            public void onError(Object error) {
                if (showProgress) ProgressHelper.stop();
            }
        });
    }
}

package com.buildboard.modules.home.modules.projects;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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
import com.buildboard.fonts.FontHelper;
import com.buildboard.http.DataManager;
import com.buildboard.modules.home.modules.projects.adapters.ContractProjectsAdapter;
import com.buildboard.modules.home.modules.projects.models.ProjectDetail;
import com.buildboard.modules.home.modules.projects.models.ProjectsData;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.ProgressHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ContractorProjectsFragment extends Fragment implements AppConstant {

    private Unbinder unbinder;
    private int mCurrentPage = 1;
    ContractProjectsAdapter mProjectsAdapter;
    ArrayList<ProjectDetail> mProjectDetails = new ArrayList<>();
    private String mCurrentStatus = STATUS_OPEN;
    @BindView(R.id.recycler_projects)
    RecyclerView recyclerProjects;
    @BindView(R.id.button_current_projects)
    Button buttonCurrentProjects;
    @BindView(R.id.button_completed_projects)
    Button buttonCompletedProjects;
    @BindView(R.id.button_open_projects)
    Button buttonOpenProjects;
    @BindView(R.id.button_saved_projects)
    Button buttonSavedProjects;
    @BindView(R.id.button_lost_projects)
    Button buttonLostProjects;
    @BindView(R.id.text_projects)
    TextView textProjects;
    @BindView(R.id.text_projects_details)
    TextView textProjectsDetails;
    @BindView(R.id.container_root)
    ConstraintLayout mCoordinatorLayout;
    @BindView(R.id.text_no_internet)
    TextView noInternetText;


    public static ContractorProjectsFragment newInstance() {
        ContractorProjectsFragment fragment = new ContractorProjectsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contractor_projects, container, false);
        unbinder = ButterKnife.bind(this, view);
        noInternetText.setVisibility(View.GONE);
        setFonts();

        return view;
    }

    private void setProjectsRecycler(ArrayList<ProjectDetail> projectDetails, int lastPage) {
        mProjectDetails.addAll(projectDetails);

        if (mProjectsAdapter == null) {
            ProgressHelper.stop();
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerProjects.setLayoutManager(mLinearLayoutManager);
            mProjectsAdapter = new ContractProjectsAdapter(getActivity(), mProjectDetails, recyclerProjects);
            recyclerProjects.setAdapter(mProjectsAdapter);
            mProjectsAdapter.setOnLoadMoreListener(new ContractProjectsAdapter.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    mCurrentPage++;
                    if (mProjectsAdapter != null && mProjectsAdapter.isLoading)
                        getProjectsList(false);
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
    public void onResume() {
        super.onResume();
        getProjectsList(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setFonts() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, buttonCompletedProjects, buttonLostProjects, buttonCurrentProjects, buttonOpenProjects, buttonSavedProjects);
        FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, textProjects);
    }

    @OnClick(R.id.button_current_projects)
    void lostProjectsTapped() {
        mCurrentPage = 1;
        mProjectDetails.clear();
        mProjectsAdapter = null;
        mCurrentStatus = STATUS_CURRENT;
        getProjectsList(true);
    }

    @OnClick(R.id.button_completed_projects)
    void completedProjectsTapped() {
        mCurrentPage = 1;
        mProjectDetails.clear();
        mProjectsAdapter = null;
        mCurrentStatus = STATUS_COMPLETED;
        getProjectsList(true);
    }

    @OnClick(R.id.button_open_projects)
    void openProjectsTapped() {
        mCurrentPage = 1;
        mProjectDetails.clear();
        mProjectsAdapter = null;
        mCurrentStatus = STATUS_OPEN;
        getProjectsList(true);
    }

    @OnClick(R.id.button_saved_projects)
    void savedProjectsTapped() {
        mCurrentPage = 1;
        mProjectDetails.clear();
        mProjectsAdapter = null;
        mCurrentStatus = STATUS_SAVED;
        getProjectsList(true);
    }

    @OnClick(R.id.button_lost_projects)
    void currentProjectsTapped() {
        mCurrentPage = 1;
        mProjectDetails.clear();
        mProjectsAdapter = null;
        mCurrentStatus = STATUS_LOST;
        getProjectsList(true);
    }

    private void getProjectsList(final boolean showProgress) {

        if (ConnectionDetector.isNetworkConnected(getActivity())) {
            noInternetText.setVisibility(View.GONE);
            recyclerProjects.setVisibility(View.VISIBLE);
            if (showProgress)
                ProgressHelper.start(getActivity(), getString(R.string.msg_please_wait));
            DataManager.getInstance().getProjectsList(getActivity(), mCurrentStatus, mCurrentPage, new DataManager.DataManagerListener() {
                @Override
                public void onSuccess(Object response) {
                    if (showProgress) ProgressHelper.stop();
                    ArrayList<ProjectsData> projectsData = (ArrayList<ProjectsData>) response;
                    ArrayList<ProjectDetail> projectDetails = projectsData.get(0).getDatas();
                    if (!projectDetails.isEmpty()) {
                        setProjectsRecycler(projectDetails, projectsData.get(0).getLastPage());
                        setProjectsSubTitle(mProjectsAdapter.getItemCount());
                    } else {
                        textProjectsDetails.setText(getText(R.string.no_projects));
                        textProjects.setText(mCurrentStatus.substring(0, 1).toUpperCase() + mCurrentStatus.substring(1).toLowerCase() + " Projects");
                    }
                }

                @Override
                public void onError(Object error) {
                    if (showProgress) ProgressHelper.stop();
                }
            });
        } else {
            ConnectionDetector.createSnackBar(getActivity(), mCoordinatorLayout);
            if (mProjectsAdapter != null) {
                if (mProjectsAdapter.getItemCount() == 0) {
                    noInternetText.setVisibility(View.VISIBLE);
                    recyclerProjects.setVisibility(View.GONE);
                }
            } else {
                noInternetText.setVisibility(View.VISIBLE);
                recyclerProjects.setVisibility(View.GONE);
            }
        }
    }

    private void setProjectsSubTitle(int count) {

        switch (mCurrentStatus) {
            case AppConstant.STATUS_OPEN:
                textProjectsDetails.setText(R.string.open_projects_subtitle);
                textProjects.setText(getString(R.string.open_project) + "(" + count + ")");
                break;
            case AppConstant.STATUS_COMPLETED:
                textProjectsDetails.setText(R.string.completed_projects_subtitle);
                textProjects.setText(getString(R.string.completed_project) + "(" + count + ")");
                break;
            case AppConstant.STATUS_CURRENT:
                textProjectsDetails.setText(R.string.current_projects_subtitle);
                textProjects.setText(getString(R.string.current_project) + "(" + count + ")");
                break;
            case AppConstant.STATUS_SAVED:
                textProjectsDetails.setText(R.string.saved_projects_subtitle);
                textProjects.setText(getString(R.string.saved_project) + "(" + count + ")");
                break;
            case AppConstant.STATUS_LOST:
                textProjectsDetails.setText(R.string.lost_projects_subtitle);
                textProjects.setText(getString(R.string.lost_project) + "(" + count + ")");
                break;
        }


    }
}

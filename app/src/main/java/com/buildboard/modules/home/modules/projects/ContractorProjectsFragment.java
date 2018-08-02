package com.buildboard.modules.home.modules.projects;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
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
import com.buildboard.view.SimpleDividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.buildboard.utils.Utils.showProgressColor;

public class ContractorProjectsFragment extends Fragment implements AppConstant {

    private Unbinder unbinder;
    private int mCurrentPage = 1;
    private ContractProjectsAdapter mProjectsAdapter;
    private ArrayList<ProjectDetail> mProjectDetails = new ArrayList<>();
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
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    public static ContractorProjectsFragment newInstance() {
        return new ContractorProjectsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contractor_projects, container, false);
        unbinder = ButterKnife.bind(this, view);
        noInternetText.setVisibility(View.GONE);
        setFonts();

        return view;
    }

    private void setProjectsRecycler(ArrayList<ProjectDetail> projectDetails, int lastPage) {
        mProjectDetails.addAll(projectDetails);
        showProgressBar();
        if (mProjectsAdapter == null) {
            hideProgressBar();
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerProjects.setLayoutManager(mLinearLayoutManager);
            recyclerProjects.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
            mProjectsAdapter = new ContractProjectsAdapter(getActivity(), mProjectDetails, recyclerProjects);
            recyclerProjects.setAdapter(mProjectsAdapter);
            mProjectsAdapter.setOnLoadMoreListener(new ContractProjectsAdapter.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    mCurrentPage++;
                    if (mProjectsAdapter != null && mProjectsAdapter.isLoading)
                        getProjectsList();
                }
            });
        } else {
            hideProgressBar();
            recyclerProjects.getAdapter().notifyItemInserted((mProjectDetails.size()));
        }

        if (mProjectsAdapter != null) {
            mProjectsAdapter.setLoading(false);
            if (mCurrentPage == lastPage)
                hideProgressBar();
            mProjectsAdapter.setLastPage(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (isAdded())
            showProgressColor(getActivity(), progressBar);
        getProjectsList();

        if (ConnectionDetector.isNetworkConnected(getActivity())) {
            noInternetText.setVisibility(View.GONE);
            recyclerProjects.setVisibility(View.VISIBLE);
            showProgressBar();
            getProjectsList();
        } else {
            hideProgressBar();
            ConnectionDetector.createSnackBar(getActivity(), mCoordinatorLayout);
        }

    }

    public void hideProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public void showProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
        getProjectsList();
    }

    @OnClick(R.id.button_completed_projects)
    void completedProjectsTapped() {
        mCurrentPage = 1;
        mProjectDetails.clear();
        mProjectsAdapter = null;
        mCurrentStatus = STATUS_COMPLETED;
        getProjectsList();
    }

    @OnClick(R.id.button_open_projects)
    void openProjectsTapped() {
        mCurrentPage = 1;
        mProjectDetails.clear();
        mProjectsAdapter = null;
        mCurrentStatus = STATUS_OPEN;
        getProjectsList();
    }

    @OnClick(R.id.button_saved_projects)
    void savedProjectsTapped() {
        mCurrentPage = 1;
        mProjectDetails.clear();
        mProjectsAdapter = null;
        mCurrentStatus = STATUS_SAVED;
        getProjectsList();
    }

    @OnClick(R.id.button_lost_projects)
    void currentProjectsTapped() {
        mCurrentPage = 1;
        mProjectDetails.clear();
        mProjectsAdapter = null;
        mCurrentStatus = STATUS_LOST;
        getProjectsList();
    }

    private void getProjectsList() {

        if (ConnectionDetector.isNetworkConnected(getActivity())) {
            noInternetText.setVisibility(View.GONE);
            recyclerProjects.setVisibility(View.VISIBLE);
            recyclerProjects.setEnabled(false);
            recyclerProjects.setAdapter(null);
            showProgressBar();
            DataManager.getInstance().getProjectsList(getActivity(), mCurrentStatus, mCurrentPage, new DataManager.DataManagerListener() {
                @Override
                public void onSuccess(Object response) {

                    hideProgressBar();
                    recyclerProjects.setEnabled(true);
                    ArrayList<ProjectsData> projectsData = (ArrayList<ProjectsData>) response;
                    ArrayList<ProjectDetail> projectDetails = projectsData.get(0).getDatas();

                    if (isAdded() && projectsData.size() > 0) {

                        if (!projectDetails.isEmpty()) {
                            setProjectsRecycler(projectDetails, projectsData.get(0).getLastPage());
                            setProjectsSubTitle(mProjectsAdapter.getItemCount());
                        } else {
                            textProjectsDetails.setText(getText(R.string.no_projects));

                            if (mCurrentStatus.equals(AppConstant.STATUS_LOST) || mCurrentStatus.equals(AppConstant.STATUS_OPEN) || mCurrentStatus.equals(AppConstant.STATUS_SAVED)) {
                                textProjects.setText(mCurrentStatus.substring(0, 1).toUpperCase() + mCurrentStatus.substring(1).toLowerCase() + getString(R.string.quotes));
                            } else {
                                textProjects.setText(mCurrentStatus.substring(0, 1).toUpperCase() + mCurrentStatus.substring(1).toLowerCase() + getString(R.string.title_projects));
                            }
                        }
                    }
                }

                @Override
                public void onError(Object error) {
                    hideProgressBar();
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
                textProjectsDetails.setText(R.string.open_quotes_subtitle);
                textProjects.setText(getString(R.string.open_quotes) + "(" + count + ")");
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
                textProjectsDetails.setText(R.string.saved_quotes_subtitle);
                textProjects.setText(getString(R.string.saved_quotes) + "(" + count + ")");
                break;
            case AppConstant.STATUS_LOST:
                textProjectsDetails.setText(R.string.lost_quotes_subtitle);
                textProjects.setText(getString(R.string.lost_quotes) + "(" + count + ")");
                break;
        }
    }
}

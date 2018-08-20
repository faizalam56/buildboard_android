package com.buildboard.modules.home.modules.projects;

import android.content.Intent;
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

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.fonts.FontHelper;
import com.buildboard.http.DataManager;
import com.buildboard.modules.home.modules.projects.adapters.ConsumerProjectsAdapter;
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

import static com.buildboard.utils.Utils.showProgressColor;

public class ConsumerProjectsFragment extends Fragment implements AppConstant {

    private ArrayList<ProjectsData> projectsData;
    private Unbinder unbinder;
    private int mCurrentPage = 1;
    private ConsumerProjectsAdapter mProjectsAdapter;
    private ArrayList<ProjectDetail> mProjectDetails = new ArrayList<>();
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
    @BindView(R.id.text_projects_details)
    BuildBoardTextView textProjectDetail;
    @BindView(R.id.text_projects)
    BuildBoardTextView buildBoardTextProjectType;
    @BindView(R.id.text_no_internet)
    BuildBoardTextView noInternetText;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.container)
    ConstraintLayout container;

    public static ConsumerProjectsFragment newInstance() {
        return new ConsumerProjectsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consumer_projects, container, false);
        unbinder = ButterKnife.bind(this, view);

        setFonts();
        showProgressColor(getActivity(), progressBar);

        if (ConnectionDetector.isNetworkConnected(getActivity())) {
            noInternetText.setVisibility(View.GONE);
            recyclerProjects.setVisibility(View.VISIBLE);
            getProjectsList();
        } else {
            ProgressHelper.hideProgressBar();
            ConnectionDetector.createSnackBar(getActivity(), container);
        }

        return view;
    }

    private void setProjectsRecycler(ArrayList<ProjectDetail> projectDetails, int lastPage) {
        ProgressHelper.showProgressBar(getActivity(), progressBar);
        mProjectDetails.addAll(projectDetails);

        if (mProjectsAdapter == null) {
           ProgressHelper.hideProgressBar();
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerProjects.setLayoutManager(mLinearLayoutManager);
            mProjectsAdapter = new ConsumerProjectsAdapter(getActivity(), mProjectDetails, recyclerProjects);
            recyclerProjects.setAdapter(mProjectsAdapter);
            mProjectsAdapter.setOnLoadMoreListener(new ConsumerProjectsAdapter.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    mCurrentPage++;
                    if (mProjectsAdapter != null && mProjectsAdapter.isLoading)
                        getProjectsList();
                }
            });
        } else {
            ProgressHelper.hideProgressBar();
            recyclerProjects.getAdapter().notifyItemInserted((mProjectDetails.size()));
        }

        if (mProjectsAdapter != null) {
            mProjectsAdapter.setLoading(false);
            if (mCurrentPage == lastPage)
             ProgressHelper.hideProgressBar();
             mProjectsAdapter.setLastPage(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setFonts() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, buttonCreateNewProjects, buttonCompletedProjects, buttonCurrentProjects, buttonOpenProjects, buttonSavedProjects);
        FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, buildBoardTextProjectType);
    }


    @OnClick(R.id.button_create_new_projects)
    void navigateToActivity() {
       startActivity(new Intent(getActivity(), ConsumerProjectTypeActivity.class));
    }

    @OnClick(R.id.button_completed_projects)
    void completedProjectsTapped() {
        mProjectDetails.clear();
        mProjectsAdapter = null;
        mCurrentStatus = STATUS_COMPLETED;
        getProjectsList();
    }

    @OnClick(R.id.button_open_projects)
    void openProjectsTapped() {
        mProjectDetails.clear();
        mProjectsAdapter = null;
        mCurrentStatus = STATUS_OPEN;
        getProjectsList();
    }

    @OnClick(R.id.button_saved_projects)
    void savedProjectsTapped() {
        mProjectDetails.clear();
        mProjectsAdapter = null;
        mCurrentStatus = STATUS_SAVED;
        getProjectsList();
    }

    @OnClick(R.id.button_current_projects)
    void currentProjectsTapped() {
        mProjectDetails.clear();
        mProjectsAdapter = null;
        mCurrentStatus = STATUS_CURRENT;
        getProjectsList();
    }

    private void getProjectsList() {
        ProgressHelper.showProgressBar(getActivity(), progressBar);
        recyclerProjects.setEnabled(false);
        DataManager.getInstance().getProjectsList(getActivity(), mCurrentStatus, mCurrentPage, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.hideProgressBar();
                ArrayList<ProjectsData> projectsData = (ArrayList<ProjectsData>) response;
                ArrayList<ProjectDetail> projectDetails = projectsData.get(0).getDatas();

                if (isAdded() && projectsData.size() > 0) {
                    if (!projectDetails.isEmpty()) {
                        setProjectsRecycler(projectDetails, projectsData.get(0).getLastPage());
                        setProjectsSubTitle(projectDetails.size());

                    } else {
                        textProjectDetail.setText("");
                        buildBoardTextProjectType.setText(String.format("%s%s Projects", mCurrentStatus.substring(0, 1).toUpperCase(), mCurrentStatus.substring(1).toLowerCase()));
                        if (mProjectsAdapter != null) {
                            mProjectsAdapter.notifyDataSetChanged();
                        }
                     }
                }
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.hideProgressBar();
            }
        });
    }

    private void setProjectsSubTitle(int count){
        switch (mCurrentStatus) {
            case STATUS_OPEN:
                textProjectDetail.setText(R.string.open_projects_subtitle);
                buildBoardTextProjectType.setText(String.format(Locale.getDefault(),"%s (%d)", getString(R.string.open_project), count));
                break;
            case STATUS_COMPLETED:
                textProjectDetail.setText(R.string.completed_projects_subtitle);
                buildBoardTextProjectType.setText(String.format(Locale.getDefault(),"%s (%d)", getString(R.string.completed_project), count));
                break;
            case STATUS_CURRENT:
                textProjectDetail.setText(R.string.current_projects_subtitle);
                buildBoardTextProjectType.setText(String.format(Locale.getDefault(),"%s (%d)", getString(R.string.current_project), count));
                break;
            case STATUS_SAVED:
                textProjectDetail.setText(R.string.saved_projects_subtitle);
                buildBoardTextProjectType.setText(String.format(Locale.getDefault(),"%s (%d)", getString(R.string.saved_project), count));
                break;
        }
    }
}

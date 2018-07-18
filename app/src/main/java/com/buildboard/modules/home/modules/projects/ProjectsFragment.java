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
import com.buildboard.fonts.FontHelper;
import com.buildboard.http.DataManager;
import com.buildboard.modules.home.modules.profile.models.ProfileData;
import com.buildboard.modules.home.modules.projects.adapters.ProjectsAdapter;
import com.buildboard.modules.home.modules.projects.models.ProjectDetail;
import com.buildboard.modules.home.modules.projects.models.ProjectsData;
import com.buildboard.utils.ProgressHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ProjectsFragment extends Fragment {

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
    TextView textProjects;

    private Unbinder unbinder;

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
        setProjectsRecycler();
        getProjectsList("open",1);
        return view;
    }

    private void setProjectsRecycler() {
        ProjectsAdapter selectionAdapter = new ProjectsAdapter(getActivity(), getDatas());
        recyclerProjects.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerProjects.setAdapter(selectionAdapter);
    }

    private ArrayList<String> getDatas() {
        ArrayList<String> datas = new ArrayList<>();
        datas.add("Service 1");
        datas.add("Service 2");
        datas.add("Service 3");
        datas.add("Service 4");
        datas.add("Service 5");

        return datas;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setFonts() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_LIGHT, buttonCompletedProjects, buttonCreateNewProjects, buttonCurrentProjects, buttonOpenProjects, buttonSavedProjects);
        FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, textProjects);
    }

    @OnClick(R.id.button_completed_projects)
    void completedProjectsTapped() {
        getProjectsList("completed",1);
    }

    @OnClick(R.id.button_open_projects)
    void openProjectsTapped() {
        getProjectsList("open",1);
    }

    @OnClick(R.id.button_saved_projects)
    void savedProjectsTapped() {
        getProjectsList("saved",1);
    }

    @OnClick(R.id.button_current_projects)
    void currentProjectsTapped() {
        getProjectsList("current",1);
    }

    private void getProjectsList(String status, int page) {
        ProgressHelper.start(getActivity(), getString(R.string.msg_please_wait));
        DataManager.getInstance().getProjectsList(getActivity(), status, page, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                ArrayList<ProjectsData> projectsData = (ArrayList<ProjectsData>)response;
                ArrayList<ProjectDetail> projectDetails = projectsData.get(0).getDatas();
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
            }
        });
    }
}

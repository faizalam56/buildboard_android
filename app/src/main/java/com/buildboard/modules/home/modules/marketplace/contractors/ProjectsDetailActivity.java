package com.buildboard.modules.home.modules.marketplace.contractors;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.fonts.FontHelper;
import com.buildboard.http.DataManager;
import com.buildboard.modules.home.modules.marketplace.contractors.adapters.ProjectDetailsFooterAdapter;
import com.buildboard.modules.home.modules.marketplace.contractors.models.ProjectsDetailData;
import com.buildboard.utils.Utils;
import com.buildboard.view.SimpleDividerItemDecoration;
import com.google.android.gms.maps.model.LatLng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.buildboard.constants.AppConstant.DATA;
import static com.buildboard.utils.Utils.showProgressColor;

public class ProjectsDetailActivity extends AppCompatActivity implements AppConstant {

    private ArrayList<String> mMenuArray = new ArrayList<>();
    private ProjectsDetailData mProjectsDetailData;

    @BindView(R.id.constraint_root)
    LinearLayout constraintRoot;
    @BindView(R.id.image_service)
    ImageView projectImage;
    @BindView(R.id.text_title)
    BuildBoardTextView textTitle;
    @BindView(R.id.description_text)
    BuildBoardTextView textDescription;
    @BindView(R.id.description_title)
    BuildBoardTextView textDescriptionTitle;
    @BindView(R.id.address_title)
    BuildBoardTextView textAddressTitle;
    @BindView(R.id.address_text)
    BuildBoardTextView textAddressText;
    @BindView(R.id.startdate_text)
    BuildBoardTextView textStartDate;
    @BindView(R.id.startdate_title)
    BuildBoardTextView textStartDateTitle;
    @BindView(R.id.enddate_text)
    BuildBoardTextView textEndDate;
    @BindView(R.id.enddate_title)
    BuildBoardTextView textEndDateTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    BuildBoardTextView toolbarTitle;
    @BindView(R.id.recycler_footer)
    RecyclerView recyclerFooter;
    @BindView(R.id.progress_bar_service)
    ProgressBar progressBar;
    @BindView(R.id.scrollBar)
    ScrollView scrollView;

    @BindString(R.string.title_projects_desc)
    String titleProjectDesc;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_projects);
        ButterKnife.bind(this);

        toolbarTitle.setText(titleProjectDesc);
        showProgressColor(this, progressBar);
        setFont();
        getIntentData();
    }

    private void getIntentData() {
        if (getIntent().hasExtra(DATA)) {
            getNearByProjectsByProjectId(getIntent().getStringExtra(DATA));
        }
    }

    private void setProgressBar(Boolean visiblity) {
        progressBar.setVisibility(visiblity ? View.VISIBLE : View.GONE);
        scrollView.setVisibility(visiblity ? View.GONE : View.VISIBLE);
        toolbar.setVisibility(visiblity ? View.GONE : View.VISIBLE);
    }

    private void getNearByProjectsByProjectId(String projectId) {
        setProgressBar(true);
        DataManager.getInstance().getNearByProjects(this, projectId, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                setProgressBar(false);
                handleSuccessResponse(response);
            }

            @Override
            public void onError(Object error) {
                setProgressBar(false);
                Utils.showError(ProjectsDetailActivity.this, constraintRoot, error);
            }
        });
    }

    private void handleSuccessResponse(Object response) {

        if (response == null) return;

        mProjectsDetailData = (ProjectsDetailData) response;
        Utils.display(ProjectsDetailActivity.this, mProjectsDetailData.getImage(), projectImage, R.mipmap.no_image_available);
        textTitle.setText(mProjectsDetailData.getTitle());
        textEndDate.setText(convertTime(mProjectsDetailData.getEndDate().split("\\s+")[0].replaceAll("-", "/")));
        textStartDate.setText(convertTime(mProjectsDetailData.getStartDate().split("\\s+")[0].replaceAll("-", "/")));
        textDescription.setText(mProjectsDetailData.getDescription());
        textAddressText.setText(mProjectsDetailData.getAddress());
        setFooter(mProjectsDetailData);
    }

    private void setFooter(ProjectsDetailData contractorByProjectTypeData) {

        mMenuArray.add(TEXT_ATTACHMENT);
        mMenuArray.add(TEXT_REQUIREMENTS);
        ProjectDetailsFooterAdapter projectDetailsFooterAdapter = new ProjectDetailsFooterAdapter(this, mMenuArray, contractorByProjectTypeData);
        recyclerFooter.setLayoutManager(new LinearLayoutManager(this));
        recyclerFooter.addItemDecoration(new SimpleDividerItemDecoration(this));
        recyclerFooter.setAdapter(projectDetailsFooterAdapter);
    }

    private String convertTime(String strDate) {
        String converted_time = "";
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat format2 = new SimpleDateFormat("MMM d, yyyy"); // TODO: 8/13/2018  
        Date date = null;
        try {
            date = format1.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return converted_time;
        }
        converted_time = format2.format(date);
        return converted_time;
    }

    @OnClick(R.id.button_showonmap)
    public void redirectToMap() {

        Utils.openAddressInMap(ProjectsDetailActivity.this,
                new LatLng(mProjectsDetailData.getLatitude(),
                        mProjectsDetailData.getLongitude()),
                mProjectsDetailData.getAddress());
    }

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, textAddressTitle, textDescriptionTitle, textStartDateTitle, textEndDateTitle);
        FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, textAddressText, textDescription, textEndDate, textStartDate);
    }
}
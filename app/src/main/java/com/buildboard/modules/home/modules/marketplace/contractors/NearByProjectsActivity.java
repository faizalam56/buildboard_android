package com.buildboard.modules.home.modules.marketplace.contractors;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.buildboard.R;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.fonts.FontHelper;
import com.buildboard.http.DataManager;
import com.buildboard.modules.home.modules.mailbox.inbox.adapters.InboxAdapter;
import com.buildboard.modules.home.modules.marketplace.adapters.NearByProjectsAdapter;
import com.buildboard.modules.home.modules.marketplace.contractors.adapters.ProjectDetailsFooterAdapter;
import com.buildboard.modules.home.modules.marketplace.contractors.models.NearByProjectData;
import com.buildboard.modules.home.modules.marketplace.contractors.models.NearByProjectsResponse;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.Utils;
import com.buildboard.view.SimpleDividerItemDecoration;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import static com.buildboard.constants.AppConstant.DATA;
import static com.buildboard.constants.AppConstant.INTENT_TITLE;

public class NearByProjectsActivity extends AppCompatActivity{

    private ArrayList<String> mMenuArray=new ArrayList<>();
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
    @BindString(R.string.title_projects_desc)
    String titleProjectDesc;
    @BindView(R.id.title)
    BuildBoardTextView toolbarTitle;
    @BindView(R.id.recycler_footer)
    RecyclerView recyclerFooter;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_projects);
        ButterKnife.bind(this);
        toolbarTitle.setText(titleProjectDesc);
        setFont();
        getIntentData();
    }

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, textAddressTitle, textDescriptionTitle, textStartDateTitle, textEndDateTitle);
        FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, textAddressText, textDescription, textEndDate, textStartDate);
    }

    private void getIntentData() {
        if (getIntent().hasExtra(DATA)) {
            getNearByProjectsByProjectId(getIntent().getStringExtra(DATA));
        }
    }

    private void getNearByProjectsByProjectId(String projectId) {
        ProgressHelper.start(this, getString(R.string.msg_please_wait));
        DataManager.getInstance().getNearByProjects(this, projectId, new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                handleSuccessResponse(response);
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
                Utils.showError(NearByProjectsActivity.this, constraintRoot, error);
            }
        });
    }

    private void handleSuccessResponse(Object response) {
        if (response == null) return;
        NearByProjectData contractorByProjectTypeData = (NearByProjectData) response;
        Utils.display(NearByProjectsActivity.this, contractorByProjectTypeData.getImage(), projectImage, R.mipmap.ic_launcher);
        textTitle.setText(contractorByProjectTypeData.getTitle());
        textEndDate.setText(convertTime(contractorByProjectTypeData.getEndDate().split("\\s+")[0].replaceAll("-", "/")));
        textStartDate.setText(convertTime(contractorByProjectTypeData.getStartDate().split("\\s+")[0].replaceAll("-", "/")));
        textDescription.setText(contractorByProjectTypeData.getDescription());
        textAddressText.setText(contractorByProjectTypeData.getAddress());
        //todo refctor: text allignmenmts
        setFooter();

    }

    private void setFooter() {

        mMenuArray.add("Attachments");
        mMenuArray.add("Requirements");
        ProjectDetailsFooterAdapter projectDetailsFooterAdapter = new ProjectDetailsFooterAdapter(this, mMenuArray);
        recyclerFooter.setLayoutManager(new LinearLayoutManager(this));
        recyclerFooter.addItemDecoration(new SimpleDividerItemDecoration(this));
        recyclerFooter.setAdapter(projectDetailsFooterAdapter);

    }

    private String convertTime(String strDate) {
        String converted_time = "";
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
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
}
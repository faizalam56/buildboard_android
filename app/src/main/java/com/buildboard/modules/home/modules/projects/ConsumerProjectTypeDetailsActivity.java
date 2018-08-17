package com.buildboard.modules.home.modules.projects;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.customviews.BuildBoardButton;
import com.buildboard.dialogs.PopUpHelper;
import com.buildboard.modules.home.modules.projects.models.ProjectFormDetails;
import com.buildboard.modules.home.modules.projects.models.ProjectTypeQuestion;
import com.buildboard.utils.ConnectionDetector;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.buildboard.constants.AppConstant.INTENT_PROJECT_TYPE_DATA;
import static com.buildboard.constants.AppConstant.INTENT_SELECTED_CATEGORY;
import static com.buildboard.constants.AppConstant.QUESTION;

public class ConsumerProjectTypeDetailsActivity extends AppCompatActivity {

    private ProjectFormDetails mProjectAllTypesData;
    private String mSelectedMode;

    @BindView(R.id.radio_group_contact_mode)
    RadioGroup radioGroup;
    @BindView(R.id.radio_phone)
    RadioButton radioPhone;
    @BindView(R.id.radio_email)
    RadioButton radioEmail;
    @BindView(R.id.buttonNext)
    BuildBoardButton buildBoardButton;
    @BindView(R.id.container_root)
    ConstraintLayout constraintLayout;

    @BindView(R.id.title)
    TextView title;

    @BindString(R.string.create_new_project)
    String stringCreateNewProjectText;

    @BindString(R.string.select_alert_message)
    String showAlertMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_consumer_project_type_details);
        ButterKnife.bind(this);

        title.setText(stringCreateNewProjectText);

        mProjectAllTypesData= (ProjectFormDetails) getIntent().getParcelableExtra(INTENT_PROJECT_TYPE_DATA);
        radioGroup.setOnCheckedChangeListener(checkedChangeListener);
    }

    RadioGroup.OnCheckedChangeListener checkedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            radioGroup.getCheckedRadioButtonId();
            RadioButton radioButton = radioGroup.findViewById(checkedId);
            mSelectedMode = (String) radioButton.getText();
        }
    };

    @OnClick(R.id.buttonNext)
    public void nextButtonTapped(){
        if(ConnectionDetector.isNetworkConnected(this)) {
            if (radioGroup.getCheckedRadioButtonId() != -1) {
                for (int i = 0; i < mProjectAllTypesData.getForm().size(); i++) {
                    if (mProjectAllTypesData.getForm().get(i).getCategory().equalsIgnoreCase(mSelectedMode)) {
                        openActivity(ConsumerCreateProjectActivity.class, mProjectAllTypesData);
                    }
                }
            } else {
                PopUpHelper.showInfoAlertPopup(this, showAlertMessage, new PopUpHelper.InfoPopupListener() {
                    @Override
                    public void onConfirm() {
                    }
                });
            }
        } else {
            ConnectionDetector.createSnackBar(this,constraintLayout);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void openActivity(Class classToReplace,ProjectFormDetails projectFormDetails){
        Intent intent = new Intent(ConsumerProjectTypeDetailsActivity.this, classToReplace);
        intent.putExtra(INTENT_PROJECT_TYPE_DATA,projectFormDetails);
        intent.putExtra(INTENT_SELECTED_CATEGORY, mSelectedMode);
        startActivity(intent);
    }
}

package com.buildboard.modules.home.modules.projects;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.buildboard.R;
import com.buildboard.customviews.BuildBoardEditText;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.modules.home.modules.profile.consumer.LocationAddressActivity;
import com.buildboard.modules.home.modules.projects.adapters.QuestionAdapter;
import com.buildboard.modules.home.modules.projects.models.ProjectScheduleLocation;
import com.buildboard.modules.home.modules.projects.models.ProjectTypeQuestion;
import com.buildboard.modules.home.modules.projects.models.Task;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.utils.Utils;
import com.buildboard.view.SnackBarFactory;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.buildboard.constants.AppConstant.INTENT_ADDRESS;
import static com.buildboard.constants.AppConstant.PROJECT_SCHEDULE_LOCATION;
import static com.buildboard.constants.AppConstant.SCHEDULE_REQUEST_CODE;


public class CreateProjectScheduleLocationFragment extends Fragment {

    private Unbinder unbinder;
    private Task task;
    private List<ProjectTypeQuestion> mQuestionList;
    private QuestionAdapter mQuestionAdapter;

    @BindView(R.id.container)
    ConstraintLayout container;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.text_project_title)
    BuildBoardTextView textProjectTitle;
    @BindView(R.id.text_project_description)
    BuildBoardTextView textProjectDescription;
    @BindView(R.id.text_preferred_start_date)
    BuildBoardTextView textPreferredStartDate;
    @BindView(R.id.text_preferred_end_date)
    BuildBoardTextView textPreferredEndDate;
    @BindView(R.id.text_address)
    BuildBoardTextView textAddress;
    @BindView(R.id.edit_project_title)
    BuildBoardEditText editProjectTitle;
    @BindView(R.id.edit_project_description)
    BuildBoardEditText editProjectDescription;
    @BindView(R.id.edit_preferred_start_date)
    BuildBoardEditText editPreferredStartDate;
    @BindView(R.id.edit_preferred_end_date)
    BuildBoardEditText editPreferredEndDate;
    @BindView(R.id.edit_project_address)
    BuildBoardEditText editProjectAddress;
    @BindView(R.id.edit_select_preferred_contractor)
    BuildBoardEditText editSelectPreferredContractor;
    @BindView(R.id.checkbox_contractor_quote)
    CheckBox checkBoxContractorQuote;
    @BindView(R.id.checkbox_preferred_contractor_quote)
    CheckBox checkBoxPreferredContractorQuote;

    @BindString(R.string.project_title)
    String stringProjectTitle;
    @BindString(R.string.project_description)
    String stringProjectDescription;
    @BindString(R.string.preferred_start_date)
    String stringPreferredStartDate;
    @BindString(R.string.preferred_end_date)
    String stringPreferredEndDate;
    @BindString(R.string.project_address)
    String stringProjectAddress;
    @BindString(R.string.contractor_quote)
    String stringContractorQuote;
    @BindString(R.string.preferred_contractor_quote)
    String stringPreferredContractorQuote;
    @BindString(R.string.project_title_empty_error)
    String stringProjectTitleErrorMsg;
    @BindString(R.string.project_description_empty_error)
    String stringProjectDescriptionErrorMsg;
    @BindString(R.string.project_start_date_empty_error)
    String stringProjectStartDateErrorMsg;
    @BindString(R.string.project_end_date_empty_error)
    String stringProjectEndDateErrorMsg;
    @BindString(R.string.project_address_empty_error)
    String stringProjectAddressErrorMsg;

    public static CreateProjectScheduleLocationFragment newInstance() {
        return new CreateProjectScheduleLocationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_project_schedule_location, container, false);
        unbinder = ButterKnife.bind(this, view);

        setAsteriskToText();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCHEDULE_REQUEST_CODE) {
            String address = data.getStringExtra(INTENT_ADDRESS);
            editProjectAddress.setText(address);
        }
    }

    @OnClick(R.id.image_start_date)
    public void imageStartDateTapped(){
        getDateAndTime(getActivity(),editPreferredStartDate);
    }

    @OnClick(R.id.image_end_date)
    public void imageEndDateTapped(){
        getDateAndTime(getActivity(),editPreferredEndDate);
    }

    @OnClick(R.id.edit_project_address)
    public void imageProjectAddressTapped(){
        Intent intent = new Intent(getActivity(), LocationAddressActivity.class);
        startActivityForResult(intent, SCHEDULE_REQUEST_CODE);
    }

    @OnClick(R.id.button_next)
    public void nextButtonTapped(){
        String projectTitle = editProjectTitle.getText().toString();
        String projectDescription = editProjectDescription.getText().toString();
        String preferredStartDate = editPreferredStartDate.getText().toString();
        String preferredEndDate = editPreferredEndDate.getText().toString().trim();
        String projectAddress = editProjectAddress.getText().toString();
        String preferredContractor = editSelectPreferredContractor.getText().toString();

        if (ConnectionDetector.isNetworkConnected(getActivity())) {
            if (validateFields(projectTitle, projectDescription, preferredStartDate, preferredEndDate, projectAddress)) {
                String contractorPriorityValue = getCheckedBoxValue();
                storeValue(projectTitle, projectDescription, preferredStartDate, preferredEndDate, projectAddress ,contractorPriorityValue ,preferredContractor);
            }
        } else {
            ConnectionDetector.createSnackBar(getActivity(), container);
        }
    }

    private void storeValue(String projectTitle, String projectDescription, String preferredStartDate, String preferredEndDate, String projectAddress, String contractorPriorityValue , String preferredContractor) {
        ProjectScheduleLocation projectScheduleLocation = new ProjectScheduleLocation();
        projectScheduleLocation.setProjectTitle(projectTitle);
        projectScheduleLocation.setProjectDescription(projectDescription);
        projectScheduleLocation.setPreferredStartDate(preferredStartDate);
        projectScheduleLocation.setPreferredEndDate(preferredEndDate);
        projectScheduleLocation.setProjectAddress(projectAddress);
        projectScheduleLocation.setProjectContractorPriority(contractorPriorityValue);
        projectScheduleLocation.setPreferredContractor(preferredContractor);

        CreateProjectAttachmentFragment createProjectAttachmentFragment = new CreateProjectAttachmentFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(PROJECT_SCHEDULE_LOCATION, projectScheduleLocation);
        createProjectAttachmentFragment.setArguments(bundle);
    }

    private boolean validateFields(String projectTitle, String projectDescription, String preferredStartDate, String preferredEndDate, String projectAddress) {

        if (TextUtils.isEmpty(projectTitle)) {
            SnackBarFactory.createSnackBar(getActivity(), container, stringProjectTitleErrorMsg);
            return false;
        }

        if (TextUtils.isEmpty(projectDescription)) {
            SnackBarFactory.createSnackBar(getActivity(), container, stringProjectDescriptionErrorMsg);
            return false;
        }

        if (TextUtils.isEmpty(preferredStartDate)) {
            SnackBarFactory.createSnackBar(getActivity(), container, stringProjectStartDateErrorMsg).show();
            return false;
        }

        if (TextUtils.isEmpty(preferredEndDate)) {
            SnackBarFactory.createSnackBar(getActivity(), container, stringProjectEndDateErrorMsg).show();
            return false;
        }

        if (TextUtils.isEmpty(projectAddress)) {
            SnackBarFactory.createSnackBar(getActivity(), container, stringProjectAddressErrorMsg).show();
            return false;
        }

        return true;
    }

    private void setAsteriskToText() {
        textProjectTitle.setText(Utils.setStarToLabel(stringProjectTitle));
        textProjectDescription.setText(Utils.setStarToLabel(stringProjectDescription));
        textPreferredStartDate.setText(Utils.setStarToLabel(stringPreferredStartDate));
        textPreferredEndDate.setText(Utils.setStarToLabel(stringPreferredEndDate));
        textAddress.setText(Utils.setStarToLabel(stringProjectAddress));
    }

    private String getCheckedBoxValue(){
        String contractorPriorityDetails = "";
        if(checkBoxContractorQuote.isChecked()){
            contractorPriorityDetails+= stringContractorQuote;
        }

        if(checkBoxPreferredContractorQuote.isChecked()){
            contractorPriorityDetails+= stringPreferredContractorQuote;
        }

        return contractorPriorityDetails;
    }

    public static void getDateAndTime(Activity activity,final EditText editText) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(activity,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        editText.setText(String.format(Locale.getDefault(),"%d-%d-%d", dayOfMonth, monthOfYear + 1, year));
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}

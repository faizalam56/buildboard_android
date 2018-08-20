package com.buildboard.modules.home.modules.projects;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.buildboard.R;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.modules.signup.contractor.helper.ImageUploadHelper;
import com.buildboard.utils.Utils;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CreateProjectAttachmentFragment extends Fragment {
    private final String[] permissions = {android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private final int REQUEST_CODE = 2001;
    private ImageUploadHelper mImageUploadHelper;
    private Unbinder unbinder;
    private BottomSheetBehavior mBehavior;
    private String mCurrentPhotoPath;


    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.container)
    ConstraintLayout constraintLayout;
    @BindView(R.id.text_attach_project_photos_heading)
    BuildBoardTextView textAttachProjectHeading;
    @BindView(R.id.text_measurement_heading)
    BuildBoardTextView textMeasurementHeading;
    @BindView(R.id.text_additional_attachment)
    BuildBoardTextView textAdditionalAttachment;
    @BindView(R.id.text_preferred_attachment_msg)
    BuildBoardTextView textPreferredAttachment;
    @BindView(R.id.bottom_sheet)
    LinearLayout bottomSheet;

    @BindString(R.string.attach_project_photos)
    String stringAttachProjectPhotos;
    @BindString(R.string.measurement_heading)
    String stringMeasurementHeading;
    @BindString(R.string.additional_attachment)
    String stringAdditionalAttachment;
    @BindString(R.string.preferred_attachment)
    String stringPreferredAttachment;

    public static CreateProjectAttachmentFragment newInstance() {
        return new CreateProjectAttachmentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_project_attachment, container, false);
        unbinder = ButterKnife.bind(this, view);
        setAsteriskToText();

        return view;
    }

    private void setAsteriskToText() {
        textAttachProjectHeading.setText(Utils.setStarToLabel(stringAttachProjectPhotos));
        textMeasurementHeading.setText(Utils.setStarToLabel(stringMeasurementHeading));
        textAdditionalAttachment.setText(Utils.setStarToLabel(stringAdditionalAttachment));
        textPreferredAttachment.setText(Utils.setStarToLabel(stringPreferredAttachment));
    }

    @OnClick(R.id.text_save)
    public void saveProjectTapped(){
        Toast.makeText(getActivity(), "save tapped", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.button_post)
    public void postProjectTapped(){
        Toast.makeText(getActivity(), "post tapped", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

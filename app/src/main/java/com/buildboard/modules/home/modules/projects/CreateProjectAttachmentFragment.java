package com.buildboard.modules.home.modules.projects;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.buildboard.R;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.utils.Utils;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CreateProjectAttachmentFragment extends Fragment {

    private Unbinder unbinder;

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

    @OnClick(R.id.card_attachment_btn)
    public void attachmentFirstTapped(){
        Toast.makeText(getActivity(), "attach tapped", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.card_attachment_btn_two)
    public void attachmentSecondTapped(){
        Toast.makeText(getActivity(), "attach tapped", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.card_attachment_btn_three)
    public void attachmentThirdTapped(){
        Toast.makeText(getActivity(), "attach tapped", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

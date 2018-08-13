package com.buildboard.modules.home.modules.projects;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.buildboard.R;
import com.buildboard.customviews.BuildBoardButton;
import com.buildboard.dialogs.PopUpHelper;
import com.buildboard.modules.home.HomeActivity;
import com.buildboard.modules.home.modules.projects.models.ProjectAllType;

import java.util.Objects;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.buildboard.constants.AppConstant.INTENT_PROJECT_TYPE_DATA;

public class ConsumerProjectTypeDetailsFragment extends Fragment implements HomeActivity.OnBackPressedListener{

    private Unbinder unbinder;
    private  ProjectAllType mProjectAllTypesData;

    @BindView(R.id.radio_group_contact_mode)
    RadioGroup radioGroup;
    @BindView(R.id.radio_phone)
    RadioButton radioPhone;
    @BindView(R.id.radio_email)
    RadioButton radioEmail;
    @BindView(R.id.buttonNext)
    BuildBoardButton buildBoardButton;

    @BindString(R.string.select_alert_message)
    String showAlertMessage;

    public static ConsumerProjectTypeDetailsFragment newInstance() {
        return new ConsumerProjectTypeDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view =inflater.inflate(R.layout.fragment_consumer_project_type_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (getActivity() != null) ((HomeActivity) getActivity()).setOnBackPressedListener(this);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mProjectAllTypesData = bundle.getParcelable(INTENT_PROJECT_TYPE_DATA);
        }


        return  view;
    }

    @OnClick(R.id.buttonNext)
    public void nextButtonTapped(){
        if(radioGroup.getCheckedRadioButtonId()!=-1){
            navigateFragment(ConsumerCreateProjectFragment.newInstance());
       } else {
            PopUpHelper.showInfoAlertPopup(getActivity(), showAlertMessage, new PopUpHelper.InfoPopupListener() {
                @Override
                public void onConfirm() { }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity) Objects.requireNonNull(getActivity())).setTitle(getString(R.string.select_location));
    }

    @Override
    public void doBack() {
        navigateFragment(ConsumerProjectTypeFragment.newInstance());
    }

    private void navigateFragment(Fragment fragment) {
        if(getActivity()!=null) {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_home_container, fragment).commit();
        }
    }
}

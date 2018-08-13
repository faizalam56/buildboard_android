package com.buildboard.modules.home.modules.projects;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.buildboard.R;
import com.buildboard.modules.home.HomeActivity;
import com.buildboard.modules.home.modules.projects.models.ProjectAllType;
import com.buildboard.modules.home.modules.projects.models.ProjectFormDetails;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.buildboard.constants.AppConstant.INTENT_PROJECT_TYPE_DATA;

public class ConsumerCreateProjectFragment extends Fragment implements HomeActivity.OnBackPressedListener {

    private Unbinder unbinder;
    private ProjectFormDetails mProjectAllTypesData;

    @BindView(R.id.row_repair_window)
    RelativeLayout relativeRepairWindowLayout;
    @BindView(R.id.row_replace_window)
    RelativeLayout relativeReplaceWindowLayout;

    public static ConsumerCreateProjectFragment newInstance() {
        return new ConsumerCreateProjectFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_consumer_create_project, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (getActivity() != null) ((HomeActivity) getActivity()).setOnBackPressedListener(this);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mProjectAllTypesData = bundle.getParcelable(INTENT_PROJECT_TYPE_DATA);
        }
        return view;
    }

    @OnClick(R.id.row_repair_window)
    public void repairWidowTapped(){
      startActivity(new Intent(getActivity(), ConsumerWindowActivity.class));
    }

    @OnClick(R.id.row_replace_window)
    public void replaceWidowTapped(){
        startActivity(new Intent(getActivity(), ConsumerWindowActivity.class));
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
        if (getActivity() != null) {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_home_container, fragment).commit();
        }
    }
}

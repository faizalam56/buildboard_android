package com.buildboard.modules.home.modules.projects;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buildboard.R;
import com.buildboard.modules.home.HomeActivity;

import java.util.Objects;

public class ConsumerCreateProjectFragment extends Fragment implements HomeActivity.OnBackPressedListener {

    public static ConsumerCreateProjectFragment newInstance() {
        return new ConsumerCreateProjectFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_consumer_create_project, container, false);
        if (getActivity() != null) ((HomeActivity) getActivity()).setOnBackPressedListener(this);
        return view;
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
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_home_container, fragment).commit();
    }
}

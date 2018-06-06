package com.buildboard.modules.home.modules.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buildboard.R;
import com.buildboard.modules.home.modules.profile.adapter.MyContractorsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProfileFragment extends Fragment {

    private static ProfileFragment sFragment;
    private Unbinder unbinder;

    @BindView(R.id.recycler_my_contactor)
    RecyclerView recyclerMyContractors;

    public static ProfileFragment newInstance() {
        if (sFragment == null)
            sFragment = new ProfileFragment();

        return sFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        setProjectsRecycler();

        return view;
    }

    private void setProjectsRecycler() {
        MyContractorsAdapter myContractorsAdapter = new MyContractorsAdapter(getActivity());
        recyclerMyContractors.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerMyContractors.setAdapter(myContractorsAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

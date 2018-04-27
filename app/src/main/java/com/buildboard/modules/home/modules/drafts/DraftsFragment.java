package com.buildboard.modules.home.modules.drafts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buildboard.R;
import com.buildboard.modules.home.modules.drafts.adapters.DraftsAdapter;
import com.buildboard.view.SimpleDividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DraftsFragment extends Fragment {

    private Unbinder mUnbinder;

    @BindView(R.id.recycler_drafts)
    RecyclerView recyclerDrafts;

    public static DraftsFragment newInstance() {
        DraftsFragment fragment = new DraftsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drafts, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        setServicesRecycler();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    private void setServicesRecycler() {
        DraftsAdapter selectionAdapter = new DraftsAdapter(getActivity(), getDatas());
        recyclerDrafts.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerDrafts.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerDrafts.setAdapter(selectionAdapter);
    }

    private ArrayList<String> getDatas() {
        ArrayList<String> datas = new ArrayList<>();
        datas.add("Service 1");
        datas.add("Service 2");
        datas.add("Service 3");
        datas.add("Service 4");
        datas.add("Service 5");

        return datas;
    }
}

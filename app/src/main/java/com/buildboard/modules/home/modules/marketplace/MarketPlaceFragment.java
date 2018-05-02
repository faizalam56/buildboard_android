package com.buildboard.modules.home.modules.marketplace;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.fonts.FontHelper;
import com.buildboard.modules.home.modules.marketplace.adapters.ContractorByProjectTypeAdapter;
import com.buildboard.modules.home.modules.marketplace.adapters.NearByContractorAdapter;
import com.buildboard.modules.home.modules.marketplace.adapters.ServicesAdapter;
import com.buildboard.view.SimpleDividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MarketPlaceFragment extends Fragment {

    private String mTitle;
    private Unbinder mUnbinder;

    @BindView(R.id.recycler_services)
    RecyclerView recyclerServices;
    @BindView(R.id.recycler_nearby_contractors)
    RecyclerView recyclerNearbyContractors;
    @BindView(R.id.recycler_contractors_by_projecttype)
    RecyclerView recyclerContractorsByProjecttype;

    @BindView(R.id.text_trending_service)
    TextView textTrendingService;
    @BindView(R.id.text_nearby_contractors)
    TextView textNearbyContractors;
    @BindView(R.id.text_contractors_by_projecttype)
    TextView textContractorsByProjecttype;

    public static MarketPlaceFragment newInstance() {
        MarketPlaceFragment fragment = new MarketPlaceFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market_place, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        setFont();
        setServicesRecycler();
        setNearbyContractorsRecycler();
        setContractorByProjectRecycler();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    private void setServicesRecycler() {
        ServicesAdapter selectionAdapter = new ServicesAdapter(getActivity(), getDatas());
        recyclerServices.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerServices.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerServices.setAdapter(selectionAdapter);
    }

    private void setNearbyContractorsRecycler() {
        NearByContractorAdapter selectionAdapter = new NearByContractorAdapter(getActivity(), getDatas());
        recyclerNearbyContractors.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerNearbyContractors.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerNearbyContractors.setAdapter(selectionAdapter);
    }

    private void setContractorByProjectRecycler() {
        ContractorByProjectTypeAdapter selectionAdapter = new ContractorByProjectTypeAdapter(getActivity(), getDatas());
        recyclerContractorsByProjecttype.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerContractorsByProjecttype.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerContractorsByProjecttype.setAdapter(selectionAdapter);
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

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, textContractorsByProjecttype, textNearbyContractors, textTrendingService);
    }
}
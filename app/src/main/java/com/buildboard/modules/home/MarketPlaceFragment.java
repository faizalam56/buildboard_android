package com.buildboard.modules.home;

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
import com.buildboard.interfaces.IRecyclerItemClickListener;
import com.buildboard.view.SimpleDividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MarketPlaceFragment extends Fragment implements IRecyclerItemClickListener {

    private static final String ARG_PARAM1 = "param1";

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

    private String mTitle;
    Unbinder unbinder;

    public MarketPlaceFragment() {
    }

    public static MarketPlaceFragment newInstance(String param1) {
        MarketPlaceFragment fragment = new MarketPlaceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) mTitle = getArguments().getString(ARG_PARAM1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market_place, container, false);
        unbinder = ButterKnife.bind(this, view);

        setFont();
        setServicesRecycler();
        setNearbyContractorsRecycler();
        setContractorByProjectRecycler();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(View view, int position, Object data) {

    }

    private void setServicesRecycler() {
        ArrayList<String> datas = new ArrayList<>();
        datas.add("Service 1");
        datas.add("Service 2");
        datas.add("Service 3");
        datas.add("Service 4");
        datas.add("Service 5");

        ServicesAdapter selectionAdapter = new ServicesAdapter(getActivity(), datas, this);
        recyclerServices.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerServices.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerServices.setAdapter(selectionAdapter);
    }

    private void setNearbyContractorsRecycler() {
        ArrayList<String> datas = new ArrayList<>();
        datas.add("Service 1");
        datas.add("Service 2");
        datas.add("Service 3");
        datas.add("Service 4");
        datas.add("Service 5");

        NearByContractorAdapter selectionAdapter = new NearByContractorAdapter(getActivity(), datas, this);
        recyclerNearbyContractors.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerNearbyContractors.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerNearbyContractors.setAdapter(selectionAdapter);
    }

    private void setContractorByProjectRecycler() {
        ArrayList<String> datas = new ArrayList<>();
        datas.add("Service 1");
        datas.add("Service 2");
        datas.add("Service 3");
        datas.add("Service 4");
        datas.add("Service 5");

        ContractorByProjectTypeAdapter selectionAdapter = new ContractorByProjectTypeAdapter(getActivity(), datas, this);
        recyclerContractorsByProjecttype.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerContractorsByProjecttype.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerContractorsByProjecttype.setAdapter(selectionAdapter);
    }

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_REGULAR, textContractorsByProjecttype, textNearbyContractors, textTrendingService);
    }
}
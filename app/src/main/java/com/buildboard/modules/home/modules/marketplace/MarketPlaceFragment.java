package com.buildboard.modules.home.modules.marketplace;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.fonts.FontHelper;
import com.buildboard.http.DataManager;
import com.buildboard.http.ErrorManager;
import com.buildboard.modules.home.modules.marketplace.adapters.ContractorByProjectTypeAdapter;
import com.buildboard.modules.home.modules.marketplace.adapters.NearByContractorAdapter;
import com.buildboard.modules.home.modules.marketplace.adapters.ServicesAdapter;
import com.buildboard.modules.home.modules.marketplace.models.MarketplaceConsumerData;
import com.buildboard.modules.home.modules.marketplace.models.NearByContractor;
import com.buildboard.modules.home.modules.marketplace.models.ProjectType;
import com.buildboard.modules.home.modules.marketplace.models.TrendingService;
import com.buildboard.preferences.AppPreference;
import com.buildboard.utils.ProgressHelper;
import com.buildboard.utils.Utils;
import com.buildboard.view.SimpleDividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MarketPlaceFragment extends Fragment implements AppConstant {

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

    @BindView(R.id.constraint_root)
    ConstraintLayout constraintRoot;

    @BindView(R.id.view_services)
    View viewServices;
    @BindView(R.id.view_nearby_contractor)
    View viewNearbyContractor;

    @BindString(R.string.trending_services)
    String stringTrendingServices;
    @BindString(R.string.near_by_contractors)
    String stringNearByContractor;
    @BindString(R.string.contractors_by_project_type)
    String stringContractorByProjectType;
    @BindString(R.string.trending_projects)
    String stringTrendingProjects;
    @BindString(R.string.near_by_projects)
    String stringNearByProjects;
    @BindString(R.string.projects_on_marketplace)
    String stringProjectsOnMarketplace;

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
        updateUi(false);
        //TODO: call contractor marketplace api
        if (AppPreference.getAppPreference(getActivity()).getBoolean(IS_CONTRACTOR)) {
            textTrendingService.setText(stringTrendingProjects);
            textNearbyContractors.setText(stringNearByProjects);
            textContractorsByProjecttype.setText(stringProjectsOnMarketplace);
            getMarketplaceConsumer();
        } else {
            textTrendingService.setText(stringTrendingServices);
            textNearbyContractors.setText(stringNearByContractor);
            textContractorsByProjecttype.setText(stringContractorByProjectType);
            getMarketplaceConsumer();
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    private void setServicesRecycler(ArrayList<TrendingService> trendingServices) {
        ServicesAdapter selectionAdapter = new ServicesAdapter(getActivity(), trendingServices);
        recyclerServices.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerServices.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerServices.setAdapter(selectionAdapter);
    }

    private void setNearbyContractorsRecycler(ArrayList<NearByContractor> nearByContractorArrayList) {
        NearByContractorAdapter selectionAdapter = new NearByContractorAdapter(getActivity(), nearByContractorArrayList);
        recyclerNearbyContractors.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerNearbyContractors.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerNearbyContractors.setAdapter(selectionAdapter);
    }

    private void setContractorByProjectRecycler(ArrayList<ProjectType> projectTypes) {
        ContractorByProjectTypeAdapter selectionAdapter = new ContractorByProjectTypeAdapter(getActivity(), projectTypes);
        recyclerContractorsByProjecttype.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerContractorsByProjecttype.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerContractorsByProjecttype.setAdapter(selectionAdapter);
    }

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, textContractorsByProjecttype, textNearbyContractors, textTrendingService);
    }

    private void getMarketplaceConsumer() {
        ProgressHelper.start(getActivity(), getString(R.string.msg_please_wait));
        DataManager.getInstance().getMarketplaceConsumer(getActivity(), new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                ProgressHelper.stop();
                if (response == null) return;

                MarketplaceConsumerData marketplaceConsumerData = (MarketplaceConsumerData) response;

                updateUi(true);
                setServicesRecycler(marketplaceConsumerData.getTrendingServices());
                setNearbyContractorsRecycler(marketplaceConsumerData.getNearByContractor());
                setContractorByProjectRecycler(marketplaceConsumerData.getProjectTypes());
            }

            @Override
            public void onError(Object error) {
                ProgressHelper.stop();
                Utils.showError(getActivity(), constraintRoot, error);
            }
        });
    }

    private void updateUi(boolean visibility) {
        textTrendingService.setVisibility(visibility ? View.VISIBLE : View.GONE);
        textContractorsByProjecttype.setVisibility(visibility ? View.VISIBLE : View.GONE);
        textNearbyContractors.setVisibility(visibility ? View.VISIBLE : View.GONE);
        viewServices.setVisibility(visibility ? View.VISIBLE : View.GONE);
        viewNearbyContractor.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }
}
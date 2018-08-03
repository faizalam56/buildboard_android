package com.buildboard.modules.home.modules.marketplace;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.buildboard.R;
import com.buildboard.constants.AppConstant;
import com.buildboard.customviews.BuildBoardTextView;
import com.buildboard.fonts.FontHelper;
import com.buildboard.http.DataManager;
import com.buildboard.modules.home.modules.marketplace.adapters.ContractorByProjectTypeAdapter;
import com.buildboard.modules.home.modules.marketplace.adapters.NearByContractorAdapter;
import com.buildboard.modules.home.modules.marketplace.adapters.NearByProjectsAdapter;
import com.buildboard.modules.home.modules.marketplace.adapters.ServicesAdapter;
import com.buildboard.modules.home.modules.marketplace.contractor_projecttype.ContractorByProjectTypeActivity;
import com.buildboard.modules.home.modules.marketplace.models.MarketplaceConsumerData;
import com.buildboard.modules.home.modules.marketplace.models.MarketplaceContractorData;
import com.buildboard.modules.home.modules.marketplace.models.NearByContractor;
import com.buildboard.modules.home.modules.marketplace.models.NearByProjects;
import com.buildboard.modules.home.modules.marketplace.models.ProjectType;
import com.buildboard.modules.home.modules.marketplace.models.TrendingService;
import com.buildboard.preferences.AppPreference;
import com.buildboard.utils.ConnectionDetector;
import com.buildboard.view.SimpleDividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.buildboard.utils.Utils.showProgressColor;

public class MarketPlaceFragment extends Fragment implements AppConstant {

    @BindView(R.id.recycler_services)
    RecyclerView recyclerServices;
    @BindView(R.id.recycler_services_no_record)
    BuildBoardTextView textServicesNoRecord;
    @BindView(R.id.recycler_nearby_contractors)
    RecyclerView recyclerNearbyContractors;
    @BindView(R.id.recycler_nearby_contractors_norecords)
    BuildBoardTextView textNearbyContractorsNorecord;
    @BindView(R.id.recycler_contractors_by_projecttype)
    RecyclerView recyclerContractorsByProjecttype;
    @BindView(R.id.recycler_contractors_by_projecttype_norecords)
    BuildBoardTextView textContractorsByProjecttypeNorecords;
    @BindView(R.id.text_trending_service)
    BuildBoardTextView textTrendingService;
    @BindView(R.id.text_nearby_contractors)
    BuildBoardTextView textNearbyContractors;
    @BindView(R.id.text_contractors_by_projecttype)
    BuildBoardTextView textContractorsByProjecttype;
    @BindView(R.id.text_view_all_nearby)
    BuildBoardTextView textViewAllNearby;
    @BindView(R.id.text_view_all_byproject)
    BuildBoardTextView textViewAllByproject;
    @BindView(R.id.view_services)
    View viewServices;
    @BindView(R.id.view_nearby_contractor)
    View viewNearbyContractor;
    @BindView(R.id.text_no_internet)
    BuildBoardTextView textNoInternet;
    @BindView(R.id.constraint_root)
    ConstraintLayout constraintLayout;
    @BindView(R.id.scroll)
    ScrollView scrollView;
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
    @BindString(R.string.browse_projects_by_projectstype)
    String stringBrowseProjects;
    @BindView(R.id.progress_bar_service)
    ProgressBar progressService;
    @BindView(R.id.progress_bar_nearby)
    ProgressBar progressNearby;
    @BindView(R.id.progress_bar_projecttype)
    ProgressBar progressProjecttype;
    private String mTitle;
    private Unbinder mUnbinder;

    public static MarketPlaceFragment newInstance() {
        return new MarketPlaceFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market_place, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        textServicesNoRecord.setVisibility(View.INVISIBLE);
        textNearbyContractorsNorecord.setVisibility(View.INVISIBLE);
        textContractorsByProjecttypeNorecords.setVisibility(View.INVISIBLE);
        showProgressColor(getActivity(), progressNearby);
        showProgressColor(getActivity(), progressProjecttype);
        showProgressColor(getActivity(), progressService);

        setFont();
        updateUi(false);

        if (ConnectionDetector.isNetworkConnected(getActivity())) {
            textNoInternet.setVisibility(View.GONE);
            if (AppPreference.getAppPreference(getActivity()).getBoolean(IS_CONTRACTOR)) {
                textTrendingService.setText(stringTrendingProjects);
                textNearbyContractors.setText(stringNearByProjects);
                textContractorsByProjecttype.setText(stringBrowseProjects);
                updateUi(true);
                getMarketplaceContractor();
            } else {
                textTrendingService.setText(stringTrendingServices);
                textNearbyContractors.setText(stringNearByContractor);
                textContractorsByProjecttype.setText(stringContractorByProjectType);
                updateUi(true);
                getMarketplaceConsumer();
            }
        } else {
            textNoInternet.setVisibility(View.VISIBLE);
            View rootView = getActivity().getWindow().getDecorView().getRootView();
            ConnectionDetector.createSnackBar(getActivity(), rootView);
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.text_view_all_nearby)
    void viewAllNearbyTapped() {
        startActivity(new Intent(getActivity(), ContractorByProjectTypeActivity.class));
    }

    @OnClick(R.id.text_view_all_byproject)
    void viewByProjectTapped() {
        startActivity(new Intent(getActivity(), ContractorByProjectTypeActivity.class));
    }

    private void setServicesRecycler(ArrayList<TrendingService> trendingServices) {

        textServicesNoRecord.setVisibility(!trendingServices.isEmpty() ? View.INVISIBLE : View.VISIBLE);
        ServicesAdapter selectionAdapter = new ServicesAdapter(getActivity(), trendingServices);
        recyclerServices.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerServices.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerServices.setAdapter(selectionAdapter);
    }

    private void setNearbyContractorsRecycler(ArrayList<NearByContractor> nearByContractorArrayList) {

        textNearbyContractorsNorecord.setVisibility(!nearByContractorArrayList.isEmpty() ? View.INVISIBLE : View.VISIBLE);
        NearByContractorAdapter selectionAdapter = new NearByContractorAdapter(getActivity(), nearByContractorArrayList);
        recyclerNearbyContractors.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerNearbyContractors.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerNearbyContractors.setAdapter(selectionAdapter);
    }

    private void setNearbyProjectsRecycler(ArrayList<NearByProjects> nearByProjectsArrayList) {

        textNearbyContractorsNorecord.setVisibility(!nearByProjectsArrayList.isEmpty() ? View.INVISIBLE : View.VISIBLE);
        NearByProjectsAdapter selectionAdapter = new NearByProjectsAdapter(getActivity(), nearByProjectsArrayList);
        recyclerNearbyContractors.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerNearbyContractors.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerNearbyContractors.setAdapter(selectionAdapter);
    }

    private void setContractorByProjectRecycler(ArrayList<ProjectType> projectTypes) {

        textContractorsByProjecttypeNorecords.setVisibility(!projectTypes.isEmpty() ? View.INVISIBLE : View.VISIBLE);
        ContractorByProjectTypeAdapter selectionAdapter = new ContractorByProjectTypeAdapter(getActivity(), projectTypes);
        recyclerContractorsByProjecttype.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerContractorsByProjecttype.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerContractorsByProjecttype.setAdapter(selectionAdapter);
    }

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, textContractorsByProjecttype, textNearbyContractors, textTrendingService);
    }

    private void getMarketplaceConsumer() {
        if (isAdded())

            setProgressBar(true);
        DataManager.getInstance().getMarketplaceConsumer(getActivity(), new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                setProgressBar(false);
                if (response == null) return;

                if (isAdded()) {
                    MarketplaceConsumerData marketplaceConsumerData = (MarketplaceConsumerData) response;
                    updateUi(true);
                    setServicesRecycler(marketplaceConsumerData.getTrendingServices());
                    setNearbyContractorsRecycler(marketplaceConsumerData.getNearByContractor());
                    setContractorByProjectRecycler(marketplaceConsumerData.getProjectTypes());
                }
            }

            @Override
            public void onError(Object error) {
                setProgressBar(false);
            }
        });
    }

    private void getMarketplaceContractor() {
        setProgressBar(true);
        DataManager.getInstance().getMarketplaceContractor(getActivity(), new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {

                setProgressBar(false);

                if (response == null) return;

                if (isAdded()) {
                    MarketplaceContractorData marketplaceContractorData = (MarketplaceContractorData) response;
                    updateUi(true);
                    setServicesRecycler(marketplaceContractorData.getTrendingServices());
                    setNearbyProjectsRecycler(marketplaceContractorData.getNearByProjects());
                    setContractorByProjectRecycler(marketplaceContractorData.getProjectTypes());
                }
            }

            @Override
            public void onError(Object error) {
                setProgressBar(false);
            }
        });
    }

    @Override
    public void onCreate(Bundle saveinstancestate) {
        super.onCreate(saveinstancestate);
    }

    private void setProgressBar(Boolean visiblity) {
        progressNearby.setVisibility(visiblity ? View.VISIBLE : View.GONE);
        progressProjecttype.setVisibility(visiblity ? View.VISIBLE : View.GONE);
        progressService.setVisibility(visiblity ? View.VISIBLE : View.GONE);
    }

    private void updateUi(boolean visibility) {
        textViewAllNearby.setVisibility(visibility ? View.VISIBLE : View.GONE);
        textViewAllByproject.setVisibility(visibility ? View.VISIBLE : View.GONE);
        textTrendingService.setVisibility(visibility ? View.VISIBLE : View.GONE);
        textContractorsByProjecttype.setVisibility(visibility ? View.VISIBLE : View.GONE);
        textNearbyContractors.setVisibility(visibility ? View.VISIBLE : View.GONE);
        viewServices.setVisibility(visibility ? View.VISIBLE : View.GONE);
        viewNearbyContractor.setVisibility(visibility ? View.VISIBLE : View.GONE);
        scrollView.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }
}
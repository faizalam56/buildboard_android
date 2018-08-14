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
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.buildboard.modules.home.modules.marketplace.adapters.NewProjectsAdapter;
import com.buildboard.modules.home.modules.marketplace.adapters.ServicesAdapter;
import com.buildboard.modules.home.modules.marketplace.contractor_projecttype.ContractorByProjectTypeActivity;
import com.buildboard.modules.home.modules.marketplace.contractors.ContractorsActivity;
import com.buildboard.modules.home.modules.marketplace.contractors.ViewAllContractorsActivity;
import com.buildboard.modules.home.modules.marketplace.contractors.models.NewProject;
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

    private String mTitle;
    private Unbinder mUnbinder;
    private  MarketplaceConsumerData mMarketplaceConsumerData;

    @BindView(R.id.recycler_services)
    RecyclerView recyclerServices;
    @BindView(R.id.recycler_services_no_record)
    BuildBoardTextView textServicesNoRecord;
    @BindView(R.id.recycler_nearby_contractors)
    RecyclerView recyclerNearbyContractors;
    @BindView(R.id.recycler_newservices)
    RecyclerView recyclerNewProjects;
    @BindView(R.id.recycler_nearby_contractors_norecords)
    BuildBoardTextView textNearbyContractorsNorecord;
    @BindView(R.id.recycler_contractors_by_projecttype)
    RecyclerView recyclerContractorsByProjectType;
    @BindView(R.id.recycler_contractors_by_projecttype_norecords)
    BuildBoardTextView textContractorsByProjecttypeNorecords;
    @BindView(R.id.recycler_newservices_no_record)
    BuildBoardTextView textNewProjectsNoRecords;
    @BindView(R.id.text_trending_service)
    BuildBoardTextView textTrendingService;
    @BindView(R.id.text_nearby_contractors)
    BuildBoardTextView textNearbyContractors;
    @BindView(R.id.text_contractors_by_projecttype)
    BuildBoardTextView textContractorsByProjectType;
    @BindView(R.id.text_view_all_nearby)
    BuildBoardTextView textViewAllNearby;
    @BindView(R.id.text_view_all_byproject)
    BuildBoardTextView textViewAllByproject;
    @BindView(R.id.text_view_all_newprojects)
    BuildBoardTextView textViewAllNewProjects;
    @BindView(R.id.text_view_all_trending)
    BuildBoardTextView textViewAllTrendingrojects;
    @BindView(R.id.text_no_internet)
    BuildBoardTextView textNoInternet;
    @BindView(R.id.constraint_root)
    ConstraintLayout constraintLayout;
    @BindView(R.id.scroll)
    ScrollView scrollView;
    @BindView(R.id.progress_bar_service)
    ProgressBar progressService;
    @BindView(R.id.progress_bar_nearby)
    ProgressBar progressNearby;
    @BindView(R.id.progress_bar_projecttype)
    ProgressBar progressProjectType;
    @BindView(R.id.progress_bar_newservice)
    ProgressBar progressNewProject;
    @BindView(R.id.text_new_service)
    BuildBoardTextView textNewProjectsTitle;
    @BindView(R.id.linear_root)
    LinearLayout linearLayout;

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

    public static MarketPlaceFragment newInstance() {
        return new MarketPlaceFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market_place, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        showProgressColor(getActivity(), progressNearby);
        showProgressColor(getActivity(), progressProjectType);
        showProgressColor(getActivity(), progressService);
        showProgressColor(getActivity(), progressNewProject);
        setFont();
        updateUi(false);
        setNoRecordFoundTextVisiblity(false, AppPreference.getAppPreference(getActivity()).getBoolean(IS_CONTRACTOR));

        if (ConnectionDetector.isNetworkConnected(getActivity())) {
            textNoInternet.setVisibility(View.GONE);
            if (AppPreference.getAppPreference(getActivity()).getBoolean(IS_CONTRACTOR)) {
                textTrendingService.setText(stringTrendingProjects);
                textNearbyContractors.setText(stringNearByProjects);
                textContractorsByProjectType.setText(stringBrowseProjects);
                updateUi(true);
                getMarketplaceContractor();
            } else {
                textTrendingService.setText(stringTrendingServices);
                textNearbyContractors.setText(stringNearByContractor);
                textContractorsByProjectType.setText(stringContractorByProjectType);
                textViewAllByproject.setVisibility(View.INVISIBLE);
                updateUi(true);
                hideNewProjectsView(false);
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
        if (ConnectionDetector.isNetworkConnected(getActivity())) {
            if (!AppPreference.getAppPreference(getActivity()).getBoolean(IS_CONTRACTOR)) {
                Intent intent = new Intent(getActivity(), ViewAllContractorsActivity.class);
                intent.putExtra(INTENT_TITLE, stringNearByContractor);
                intent.putParcelableArrayListExtra(DATA, mMarketplaceConsumerData.getNearByContractor());
                if (mMarketplaceConsumerData != null)
                    getActivity().startActivity(intent);
            } else {
                // TODO: 8/14/2018
            }
        } else {
            ConnectionDetector.createSnackBar(getActivity(), linearLayout);
        }
    }

    @OnClick(R.id.text_view_all_byproject)
    void viewByProjectTapped() {
        startActivity(new Intent(getActivity(), ContractorByProjectTypeActivity.class));
    }

    @OnClick(R.id.text_view_all_trending)
    void viewByTrendingTapped() {
        if (ConnectionDetector.isNetworkConnected(getActivity())) {
            if (!AppPreference.getAppPreference(getActivity()).getBoolean(IS_CONTRACTOR)) {
                Intent intent = new Intent(getActivity(), ViewAllContractorsActivity.class);
                intent.putExtra(INTENT_TITLE, stringTrendingServices);
                intent.putParcelableArrayListExtra(DATA, mMarketplaceConsumerData.getTrendingServices());
                if (mMarketplaceConsumerData != null)
                    getActivity().startActivity(intent);
            } else {
                // TODO: 8/14/2018
            }
        } else {
            ConnectionDetector.createSnackBar(getActivity(),linearLayout);
        }
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
        recyclerContractorsByProjectType.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerContractorsByProjectType.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerContractorsByProjectType.setAdapter(selectionAdapter);
    }

    private void setNewProjectsRecyclerForContractor(ArrayList<NewProject> newProjects) {
        textNewProjectsNoRecords.setVisibility(!newProjects.isEmpty() ? View.INVISIBLE : View.VISIBLE);
        NewProjectsAdapter selectionAdapter = new NewProjectsAdapter(getActivity(), newProjects);
        recyclerNewProjects.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerNewProjects.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerNewProjects.setAdapter(selectionAdapter);
    }

    private void setFont() {
        FontHelper.setFontFace(FontHelper.FontType.FONT_BOLD, textNewProjectsTitle, textContractorsByProjectType, textNearbyContractors, textTrendingService);
    }

    private void getMarketplaceConsumer() {
        if (isAdded())
            setProgressBar(true, AppPreference.getAppPreference(getActivity()).getBoolean(IS_CONTRACTOR));
        DataManager.getInstance().getMarketplaceConsumer(getActivity(), new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                setProgressBar(false, AppPreference.getAppPreference(getActivity()).getBoolean(IS_CONTRACTOR));
                if (response == null) return;
                if (isAdded()) {
                    mMarketplaceConsumerData = (MarketplaceConsumerData) response;
                    updateUi(true);
                    setServicesRecycler(mMarketplaceConsumerData.getTrendingServices());
                    setNearbyContractorsRecycler(mMarketplaceConsumerData.getNearByContractor());
                    setContractorByProjectRecycler(mMarketplaceConsumerData.getProjectTypes());
                }
            }
            @Override
            public void onError(Object error) {
                setProgressBar(false, AppPreference.getAppPreference(getActivity()).getBoolean(IS_CONTRACTOR));
                setNoRecordFoundTextVisiblity(true, AppPreference.getAppPreference(getActivity()).getBoolean(IS_CONTRACTOR));
            }
        });
    }

    private void getMarketplaceContractor() {
        setProgressBar(true, AppPreference.getAppPreference(getActivity()).getBoolean(IS_CONTRACTOR));
        DataManager.getInstance().getMarketplaceContractor(getActivity(), new DataManager.DataManagerListener() {
            @Override
            public void onSuccess(Object response) {
                setProgressBar(false, AppPreference.getAppPreference(getActivity()).getBoolean(IS_CONTRACTOR));
                if (response == null) return;
                if (isAdded()) {
                    MarketplaceContractorData marketplaceContractorData = (MarketplaceContractorData) response;
                    updateUi(true);
                    setServicesRecycler(marketplaceContractorData.getTrendingServices());
                    setNearbyProjectsRecycler(marketplaceContractorData.getNearByProjects());
                    setContractorByProjectRecycler(marketplaceContractorData.getProjectTypes());
                    setNewProjectsRecyclerForContractor(marketplaceContractorData.getNewProjects());
                }
            }
            @Override
            public void onError(Object error) {
                setProgressBar(false, AppPreference.getAppPreference(getActivity()).getBoolean(IS_CONTRACTOR));
                setNoRecordFoundTextVisiblity(true, AppPreference.getAppPreference(getActivity()).getBoolean(IS_CONTRACTOR));
            }
        });
    }

    private void hideNewProjectsView(boolean visiblity) {
        textNewProjectsNoRecords.setVisibility(visiblity ? View.VISIBLE : View.GONE);
        progressNewProject.setVisibility(visiblity ? View.VISIBLE : View.GONE);
        recyclerNewProjects.setVisibility(visiblity ? View.VISIBLE : View.GONE);
        textNewProjectsTitle.setVisibility(visiblity ? View.VISIBLE : View.GONE);
        textViewAllNewProjects.setVisibility(visiblity ? View.VISIBLE : View.GONE);
    }

    private void setProgressBar(Boolean visiblity, boolean isContractor) {
        progressNearby.setVisibility(visiblity ? View.VISIBLE : View.GONE);
        progressProjectType.setVisibility(visiblity ? View.VISIBLE : View.GONE);
        progressService.setVisibility(visiblity ? View.VISIBLE : View.GONE);
        progressNewProject.setVisibility(visiblity && isContractor ? View.VISIBLE : View.GONE);
    }

    private void updateUi(boolean visibility) {
        textViewAllNearby.setVisibility(visibility ? View.VISIBLE : View.GONE);
        if (AppPreference.getAppPreference(getActivity()).getBoolean(IS_CONTRACTOR)) {
            textViewAllByproject.setVisibility(visibility ? View.VISIBLE : View.GONE);
        }
        textViewAllTrendingrojects.setVisibility(visibility ? View.VISIBLE : View.GONE);
        textTrendingService.setVisibility(visibility ? View.VISIBLE : View.GONE);
        textContractorsByProjectType.setVisibility(visibility ? View.VISIBLE : View.GONE);
        textNearbyContractors.setVisibility(visibility ? View.VISIBLE : View.GONE);
        scrollView.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    private void setNoRecordFoundTextVisiblity(boolean visiblity , boolean isContractor) {
        textServicesNoRecord.setVisibility(visiblity ? View.VISIBLE : View.INVISIBLE);
        textNearbyContractorsNorecord.setVisibility(visiblity ? View.VISIBLE : View.INVISIBLE);
        textContractorsByProjecttypeNorecords.setVisibility(visiblity ? View.VISIBLE : View.INVISIBLE);
        if (isContractor) {
            textNewProjectsNoRecords.setVisibility(visiblity ? View.VISIBLE : View.INVISIBLE);
        } else {
            textNewProjectsNoRecords.setVisibility(visiblity && isContractor ? View.VISIBLE : View.GONE);
        }
    }
}

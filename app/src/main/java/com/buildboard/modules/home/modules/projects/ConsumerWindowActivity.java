package com.buildboard.modules.home.modules.projects;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buildboard.R;
import com.buildboard.modules.home.modules.projects.models.ProjectFormDetails;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.buildboard.constants.AppConstant.INTENT_PROJECT_TYPE_DATA;
import static com.buildboard.constants.AppConstant.INTENT_SELECTED_PROJECT_SUB_CATEGORY;

public class ConsumerWindowFragment extends  Fragment {

    private Unbinder unbinder;
    private ProjectFormDetails mProjectAllTypesData;
    private String mSelectedCategory;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    public static ConsumerCreateProjectFragment newInstance() {
        return new ConsumerCreateProjectFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.activity_consumer_repair_window, container, false);

        unbinder = ButterKnife.bind(this, view);

        getIntentData();

      /*  if(getActivity()!=null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }*/
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    private void getIntentData() {
       // mProjectAllTypesData = getIntent().getExtras().getParcelable(INTENT_PROJECT_TYPE_DATA);
        //mSelectedCategory = intent.getStringExtra(INTENT_SELECTED_PROJECT_SUB_CATEGORY);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

     /*   CreateProjectDescriptionFragment createProjectDescriptionFragment = new CreateProjectDescriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(INTENT_PROJECT_TYPE_DATA,mProjectAllTypesData);
       // bundle.putString(INTENT_SELECTED_PROJECT_SUB_CATEGORY,mSelectedCategory);
        createProjectDescriptionFragment.setArguments(bundle);*/


        adapter.addFragment(new CreateProjectDescriptionFragment(), getString(R.string.description));
        adapter.addFragment(new CreateProjectScheduleLocationFragment(), getString(R.string.schedule_location));
        adapter.addFragment(new CreateProjectAttachmentFragment(), getString(R.string.attachements));
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

  /*  @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }*/
}

package com.buildboard.modules.home.modules.projects;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.buildboard.R;
import com.buildboard.modules.home.modules.projects.models.ProjectFormDetails;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.buildboard.constants.AppConstant.INTENT_PROJECT_TYPE_DATA;

public class ConsumerWindowActivity extends  AppCompatActivity {

    private ProjectFormDetails mProjectAllTypesData;
    private String mSelectedCategory;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_repair_window);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void getIntentData() {
       mProjectAllTypesData = getIntent().getExtras().getParcelable(INTENT_PROJECT_TYPE_DATA);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        CreateProjectDescriptionFragment createProjectDescriptionFragment = new CreateProjectDescriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(INTENT_PROJECT_TYPE_DATA, mProjectAllTypesData);
        createProjectDescriptionFragment.setArguments(bundle);

        adapter.addFragment(createProjectDescriptionFragment, getString(R.string.description));
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

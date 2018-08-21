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

import com.buildboard.modules.home.modules.projects.models.ProjectTypeQuestion;
import com.buildboard.modules.home.modules.projects.models.Task;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.buildboard.R;
import static com.buildboard.constants.AppConstant.TASK;

public class ConsumerWindowActivity extends  AppCompatActivity {

    private Task task;
    private String mSelectedCategory;
    List<ProjectTypeQuestion> questions;

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

        task = getIntent().getParcelableExtra(TASK);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        CreateProjectDescriptionFragment createProjectDescriptionFragment = new CreateProjectDescriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(TASK, task);
        createProjectDescriptionFragment.setArguments(bundle);

        adapter.addFragment(createProjectDescriptionFragment, getString(R.string.description));
        adapter.addFragment(new CreateProjectScheduleLocationFragment(), getString(R.string.schedule_location));
        adapter.addFragment(new CreateProjectAttachmentFragment(), getString(R.string.attachments));
        viewPager.setOffscreenPageLimit(3);
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
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

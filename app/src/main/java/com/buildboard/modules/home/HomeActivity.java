package com.buildboard.modules.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.buildboard.R;
import com.buildboard.modules.home.modules.marketplace.MarketPlaceFragment;
import com.buildboard.modules.home.modules.projects.ProjectsFragment;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.frame_home_container)
    FrameLayout frameHomeContainer;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindString(R.string.title_marketplace)
    String stringMarketPlace;
    @BindString(R.string.title_projects)
    String stringProjects;
    @BindString(R.string.title_mailbox)
    String stringMailbox;
    @BindString(R.string.title_profile)
    String stringProfile;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {

                case R.id.navigation_marketplace:
                    setTitle(stringMarketPlace);
                    navigateFragment(MarketPlaceFragment.newInstance());
                    return true;

                case R.id.navigation_projects:
                    setTitle(stringProjects);
                    navigateFragment(ProjectsFragment.newInstance());
                    return true;

                case R.id.navigation_mailbox:
                    setTitle(stringMailbox);
                    return true;

                case R.id.navigation_profile:
                    setTitle(stringProfile);
                    return true;
            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.removeShiftMode(navigation);
        setTitle(stringMarketPlace);
        navigateFragment(MarketPlaceFragment.newInstance());
    }

    private void navigateFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_home_container, fragment).commit();
    }

    private void setTitle(String title) {
        toolbar.setTitle(title);
    }
}

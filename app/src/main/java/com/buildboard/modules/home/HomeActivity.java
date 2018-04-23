package com.buildboard.modules.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.buildboard.R;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.frame_home_container)
    FrameLayout frameHomeContainer;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

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
                    navigateFragment(stringMarketPlace);
                    return true;
                case R.id.navigation_projects:
                    navigateFragment(stringProjects);
                    return true;
                case R.id.navigation_mailbox:
                    navigateFragment(stringMailbox);
                    return true;
                case R.id.navigation_profile:
                    navigateFragment(stringProfile);
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

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.removeShiftMode(navigation);
    }

    private void navigateFragment(String title) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_home_container, MarketPlaceFragment.newInstance(title)).commit();
    }
}

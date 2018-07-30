package com.buildboard.modules.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.buildboard.R;
import com.buildboard.modules.home.modules.mailbox.MailboxFragment;
import com.buildboard.modules.home.modules.marketplace.MarketPlaceFragment;
import com.buildboard.modules.home.modules.profile.ProfileFragment;
import com.buildboard.modules.home.modules.profile.ProfileSettingsActivity;
import com.buildboard.modules.home.modules.projects.ConsumerProjectsFragment;
import com.buildboard.modules.home.modules.projects.ContractorProjectsFragment;
import com.buildboard.preferences.AppPreference;
import com.buildboard.view.BottomNavigationViewHelper;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.buildboard.constants.AppConstant.IS_CONTRACTOR;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.frame_home_container)
    FrameLayout frameHomeContainer;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView textViewTitle;
    @BindView(R.id.image_setting)
    ImageView imageProfileSetting;
    @BindString(R.string.title_marketplace)
    String stringMarketPlace;
    @BindString(R.string.title_projects)
    String stringProjects;
    @BindString(R.string.title_mailbox)
    String stringMailbox;
    @BindString(R.string.title_profile)
    String stringProfile;
    @BindColor(R.color.colorPrimary)
    int colorPrimary;
    @BindColor(R.color.colorWhite)
    int colorWhite;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {

                case R.id.navigation_marketplace:
                    setTitle(stringMarketPlace);
                    changeToolbarColor(colorWhite, colorPrimary, false);
                    navigateFragment(MarketPlaceFragment.newInstance());
                    return true;

                case R.id.navigation_projects:
                    setTitle(stringProjects);
                    changeToolbarColor(colorWhite, colorPrimary, false);
                    if (AppPreference.getAppPreference(getApplicationContext()).getBoolean(IS_CONTRACTOR)) {
                        navigateFragment(ContractorProjectsFragment.newInstance());
                    } else {
                        navigateFragment(ConsumerProjectsFragment.newInstance());
                    }

                    return true;

                case R.id.navigation_mailbox:
                    setTitle(stringMailbox);
                    changeToolbarColor(colorWhite, colorPrimary, false);
                    navigateFragment(MailboxFragment.newInstance());
                    return true;

                case R.id.navigation_profile:
                    setTitle(stringProfile);
                    changeToolbarColor(colorPrimary, colorWhite, true);
                    navigateFragment(ProfileFragment.newInstance());
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
        textViewTitle.setText(title);
    }

    private void changeToolbarColor(int background, int text, boolean imageSettingVisibility) {
        imageProfileSetting.setVisibility(imageSettingVisibility ? View.VISIBLE : View.GONE);
        toolbar.setBackgroundColor(background);
        textViewTitle.setTextColor(text);
    }

    @OnClick(R.id.image_setting)
    void profileSettingTapped() {
        startActivity(new Intent(this, ProfileSettingsActivity.class));
    }
}

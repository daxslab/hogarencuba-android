package com.f4adaxs.apps.hogarencuba.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import com.f4adaxs.apps.hogarencuba.R;
import com.f4adaxs.apps.hogarencuba.config.AppConfig;
import com.f4adaxs.apps.hogarencuba.fragments.HomeFragment;
import com.f4adaxs.apps.hogarencuba.fragments.ListPublicacionesFragment;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

public class MainActivity extends BaseActivity {

    private final int[] colors = {R.color.bottomtab_0, R.color.bottomtab_1, R.color.bottomtab_2};

    private AHBottomNavigation bottomNavigation;
    private Fragment currentFragment = null;

    private int activeButtomTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activeButtomTab = AppConfig.TAB_VARIABLES;
        if (savedInstanceState != null && savedInstanceState.containsKey(AppConfig.ID_ACTIVE_BUTTOM_TAB)) {
            activeButtomTab = savedInstanceState.getInt(AppConfig.ID_ACTIVE_BUTTOM_TAB);
        }

        this.initToolbar();

        this.loadView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(AppConfig.ID_ACTIVE_BUTTOM_TAB, this.activeButtomTab);
        super.onSaveInstanceState(outState);
    }

    private void loadView() {
        this.setFramegmentItem(this.activeButtomTab);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        setupBottomNav();

        bottomNavigation.setOnTabSelectedListener((position, wasSelected) -> {

            if (!wasSelected)
                setFramegmentItem(position);

            activeButtomTab = position;

            return true;
        });

    }

    private void setFramegmentItem(int position) {
        Fragment fragment = null;
        String TAG = null;
        int color = 0;
        if (position == AppConfig.TAB_VARIABLES) {
            fragment = new ListPublicacionesFragment();
            TAG = ListPublicacionesFragment.class.getSimpleName();
            color = R.color.bottomtab_0;
        } else if (position == AppConfig.TAB_LISTADO) {
            fragment = new HomeFragment();
            TAG = HomeFragment.class.getSimpleName();
            color = R.color.bottomtab_1;
        } else if (position == AppConfig.TAB_CONF) {
            fragment = new HomeFragment();
            TAG = HomeFragment.class.getSimpleName();
            color = R.color.bottomtab_2;
        }
        if (currentFragment != null) {
            currentFragment.onPause();
        }
        boolean poped = getSupportFragmentManager().popBackStackImmediate();
        while (poped) {
            poped = getSupportFragmentManager().popBackStackImmediate();
        }
        fragment.setArguments(passFragmentArguments(fetchColor(color)));
        currentFragment = fragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.detail_container, currentFragment, TAG).commit();
    }


    @NonNull
    private Bundle passFragmentArguments(int color) {
        Bundle bundle = new Bundle();
        bundle.putInt("color", color);
        return bundle;
    }

    private void setupBottomNav() {
        bottomNavigation.setTranslucentNavigationEnabled(false);

        /*
        Set Bottom Navigation colors. Accent color for active item,
        Inactive color when its view is disabled.

        Will not be visible if setColored(true) and default current item is set.
         */
        bottomNavigation.setDefaultBackgroundColor(Color.WHITE);
        bottomNavigation.setAccentColor(fetchColor(R.color.bottomtab_0));
        bottomNavigation.setInactiveColor(fetchColor(R.color.bottomtab_item_resting));

        // Colors for selected (active) and non-selected items.
        bottomNavigation.setColoredModeColors(Color.WHITE, fetchColor(R.color.bottomtab_item_resting));

        //  Enables Reveal effect
        bottomNavigation.setColored(true);

        //  Displays item Title always (for selected and non-selected items)
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

//        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.ic_list_24dp, colors[0]);
//        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.ic_brush_blue, colors[1]);
//        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_3, R.drawable.ic_search_24dp, colors[2]);
//
//        bottomNavigation.addItem(item1);
//        bottomNavigation.addItem(item2);
//        bottomNavigation.addItem(item3);
    }

    private int fetchColor(@ColorRes int color) {
        return ContextCompat.getColor(this, color);
    }

    private void initToolbar() {
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
    }

}

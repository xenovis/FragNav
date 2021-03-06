package com.ncapdevi.sample;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ncapdevi.fragnav.FragNavController;

import java.util.ArrayList;
import java.util.List;

public class NavDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BaseFragment.FragmentNavigation {
    private FragNavController mNavController;

    //Better convention to properly name the indices what they are in your app
    private final int INDEX_RECENTS = FragNavController.TAB1;
    private final int INDEX_FAVORITES = FragNavController.TAB2;
    private final int INDEX_NEARBY = FragNavController.TAB3;
    private final int INDEX_FRIENDS = FragNavController.TAB4;
    private final int INDEX_FOOD = FragNavController.TAB5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        List<Fragment> fragments = new ArrayList<>(5);

        fragments.add(RecentsFragment.newInstance(0));
        fragments.add(FavoritesFragment.newInstance(0));
        fragments.add(NearbyFragment.newInstance(0));
        fragments.add(FriendsFragment.newInstance(0));
        fragments.add(FoodFragment.newInstance(0));

        mNavController = new FragNavController(getSupportFragmentManager(), R.id.container, fragments);
        mNavController.switchTab(INDEX_RECENTS);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if (mNavController.getCurrentStack().size() > 1) {
            mNavController.pop();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bb_menu_recents:
                mNavController.switchTab(INDEX_RECENTS);
                break;
            case R.id.bb_menu_favorites:
                mNavController.switchTab(INDEX_FAVORITES);
                break;
            case R.id.bb_menu_nearby:
                mNavController.switchTab(INDEX_NEARBY);
                break;
            case R.id.bb_menu_friends:
                mNavController.switchTab(INDEX_FRIENDS);
                break;
            case R.id.bb_menu_food:
                mNavController.switchTab(INDEX_FOOD);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void pushFragment(Fragment fragment) {
        mNavController.push(fragment);
    }
}

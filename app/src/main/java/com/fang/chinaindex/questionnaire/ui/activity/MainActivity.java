package com.fang.chinaindex.questionnaire.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import com.fang.chinaindex.questionnaire.R;
import com.fang.chinaindex.questionnaire.ui.fragment.DrawerFragment;
import com.fang.chinaindex.questionnaire.ui.fragment.NewSurveysFragment;
import com.fang.chinaindex.questionnaire.ui.fragment.SubmitFragment;
import com.fang.chinaindex.questionnaire.ui.fragment.UnFinishedFragment;


public class MainActivity extends BaseActivity implements DrawerFragment.NavigationDrawerCallbacks{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private DrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private int lastPosition = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNavigationDrawerFragment = (DrawerFragment)
                                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        if(savedInstanceState == null){
            mNavigationDrawerFragment.selectItem(0);
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Fragment lastFragment = fm.findFragmentByTag(getTag(lastPosition));
        if (lastFragment != null) {
            ft.detach(lastFragment);
        }

        Fragment fragment = fm.findFragmentByTag(getTag(position));
        if (fragment == null) {
            fragment = getFragmentItem(position);
            ft.add(R.id.container, fragment, getTag(position));
        } else {
            ft.attach(fragment);
        }
        ft.commit();
        lastPosition = position;
        mTitle = mNavigationDrawerFragment.getTitle(position);
        restoreActionBar();
    }

    private Fragment getFragmentItem(int position){
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new NewSurveysFragment();
                break;
            case 1:
                fragment = new UnFinishedFragment();
                break;
            case 2:
                fragment = new SubmitFragment();
                break;
        }
        return fragment;
    }

    private String getTag(int position){
        String tag = null;
        switch (position){
            case 0:
                tag = NewSurveysFragment.class.getSimpleName();
                break;
            case 1:
                tag = UnFinishedFragment.class.getSimpleName();
                break;
            case 2:
                tag = SubmitFragment.class.getSimpleName();
                break;
        }
        return tag;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}

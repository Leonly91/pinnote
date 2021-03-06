package com.example.pinnote;//test for git

import java.util.ArrayList;
import java.util.List;

import com.example.pinnote.comm.ViewInlineFragment;
import com.example.pinnote.db.*;

import android.os.Bundle;
import android.os.Handler;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
    private ViewPager viewPager;
    private ActionBar actionBar;
    private TabsPagerAdapter mAdapter;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter navDrawerListadapter;
    private ListView mTodoListView;
    
    private boolean doubleBackToExitPressedOnce;

    //private NavDrawerListAdapter adapter;
    private String tabs[] = {"To Do", "Doing", "Done"};
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initActionBarView();

        //Add DrawerLayout
        initDrawerView();

        if (savedInstanceState == null){
            //displayView(0);
        }
        
    }
    
    private void initActionBarView(){
    	viewPager = (ViewPager)findViewById(R.id.viewPager);

        /* Add ActionBar Tab Activity */
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);

        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for (String tab_name : tabs){
            actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(new MyTabListener()));
        }

        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
                
                //refresh list data
                ViewInlineFragment fragment = (ViewInlineFragment)getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.viewPager+":"+position);
                if (fragment != null){
                	fragment.refreshListData();
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }
    
    private void initDrawerView(){
    	mTitle = getString(R.string.app_name);
    	navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerList = (ListView)findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0,-1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(3, -1), true, "22"));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(4, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(5, -1), true, "50+"));

        navMenuIcons.recycle();

        navDrawerListadapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
        mDrawerList.setAdapter(navDrawerListadapter);
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.app_name,
                R.string.app_name){

            public void onDrawerClosed(View view){
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }
            public void onDrawerOpened(View drawerView){
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }
        };
        
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }
    
    public boolean onCreateOptionsMenu(Menu menu){
        //super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        switch(item.getItemId()){
            case R.id.AddEventMenu:
            	Intent intent = new Intent();
            	intent.setClass(MainActivity.this, AddNoteActivity.class);
            	startActivity(intent);
            	overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                break;
            case R.id.SeeAllMenu:
                Toast.makeText(this, "SeeAllMenu", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settingMenu:
            	Intent settingIntent = new Intent();
            	settingIntent.setClass(MainActivity.this, SettingsActivity.class);
            	startActivity(settingIntent);
            	overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            	break;
            case R.id.login_tst:
            	Intent LoginItent = new Intent();
            	LoginItent.setClass(MainActivity.this, LoginActivity.class);
            	startActivity(LoginItent);
            	overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            	break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }
        return true;
    }

    public boolean onPrepareOptionMenu(Menu menu){
        //boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    
    class MyTabListener implements ActionBar.TabListener{
        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            //To change body of implemented methods use File | Settings | File Templates.
            viewPager.setCurrentItem(tab.getPosition());
        }

    	@Override
    	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
    		// TODO Auto-generated method stub
    		
    	}
    }
    
    public void onBackPressed(){
    	if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.exit_alarm, Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
             doubleBackToExitPressedOnce=false;   

            }
        }, 2000);
    }
}

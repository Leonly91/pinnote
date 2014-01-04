package com.example.pinnote;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: Terry
 * Date: 13-12-20
 * Time: 下午11:07
 * To change this template use File | Settings | File Templates.
 */
public class MyTabListener implements ActionBar.TabListener {
    public Fragment fragment;

    public MyTabListener(Fragment fragment){
            this.fragment = fragment;
    }
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        //To change body of implemented methods use File | Settings | File Templates.
        //ft.replace(R.id.fragment_container, fragment);
        Log.v("liny:", "OnTabSelected,"+ fragment.getClass().getName());
        //ft.add(R.id.fragment_container, fragment, null);

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        //To change body of implemented methods use File | Settings | File Templates.
        ft.remove(fragment);
    }
}

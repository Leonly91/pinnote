package com.example.pinnote;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: Terry
 * Date: 13-12-22
 * Time: 下午2:24
 * To change this template use File | Settings | File Templates.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override    
    public Fragment getItem(int i) {
        switch(i){
            case 0:
                return new AFragment();
            case 1:
                return new BFragment();
            case 2:
                return new CFragment();
        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getCount() {
        return 3;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

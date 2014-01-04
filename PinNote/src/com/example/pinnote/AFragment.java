package com.example.pinnote;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created with IntelliJ IDEA. 
 * User: Terry
 * Date: 13-12-20
 * Time: 下午10:57
 * To change this template use File | Settings | File Templates.
 */
public class AFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)  {
        return inflater.inflate(R.layout.first, container, false);
    }
}

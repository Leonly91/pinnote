package com.example.pinnote;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created with IntelliJ IDEA. 
 * User: Terry
 * Date: 13-12-20
 * Time: 下午10:57
 * To change this template use File | Settings | File Templates.
 */
public class AFragment extends Fragment {
	private ListView todoList;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)  {
    	View view = inflater.inflate(R.layout.first, container, false);
    	todoList = (ListView)view.findViewById(R.id.todoList);
        return view;
    }
    
    public AFragment(){
    	super();
    }
}

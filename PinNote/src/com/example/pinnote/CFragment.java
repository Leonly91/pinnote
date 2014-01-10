package com.example.pinnote;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
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
 * Time: 下午10:58
 * To change this template use File | Settings | File Templates.
 */
public class CFragment extends Fragment {
	private ListView todoList;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)  {
    	View view = inflater.inflate(R.layout.third, container, false);
    	todoList = (ListView)view.findViewById(R.id.doneList);
    	if (todoList != null){
    		List<String> data = new ArrayList<String>();
        	for (int i = 0; i < 20; i++) {
        		data.add("data2:" + i);
        	}
    		todoList.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_expandable_list_item_1, data));
    	}
        return view;
    }
}

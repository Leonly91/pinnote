package com.example.pinnote;

import java.util.ArrayList;
import java.util.List;

import com.example.pinnote.comm.ListViewItemLongClickIListener;
import com.example.pinnote.db.DBUtil;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created with IntelliJ IDEA. 
 * User: Terry
 * Date: 13-12-20
 * Time: ‰∏ãÂçà10:57
 * To change this template use File | Settings | File Templates.
 */
public class AFragment extends Fragment {
	private ListView todoList;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)  {
    	View view = inflater.inflate(R.layout.first, container, false);
    	fillListData(view);
    	
        return view;
    }
    
    private void fillListData(View view){
    	todoList = (ListView)view.findViewById(R.id.todoList);
    	todoList.setOnItemLongClickListener(new ListViewItemLongClickIListener(todoList, getActivity()));
    	
    	if (todoList != null){
    		List<String> data = new ArrayList<String>();
        	for (int i = 0; i < 20; i++) {
        		data.add("data0:" + i);
        	}
        	
//        	Note note_data[] = new Note[]{
//        			new Note("Hello"),
//        			new Note("Goodbye"),
//        			new Note("Huawei"),
//        			new Note("How Could you fall in love with him"),
//        			new Note("TerryGuy1001@gmail.com"),
//        			new Note("√˜ÃÏ«¿ª≥µ∆±"),
//        			new Note("ThinkPad T430u")
//        			};
        	Note note_data[] = DBUtil.getTodoNoteArray(getActivity());
        	if (null != note_data)
        	{
        		NoteAdapter noteAdapter = new NoteAdapter(getActivity(), R.layout.mainlist_item_row, note_data);
        		noteAdapter.setNotifyOnChange(true);
        		todoList.setAdapter(noteAdapter);
        	}
//    		todoList.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_expandable_list_item_1, data));
    	}
    }
}

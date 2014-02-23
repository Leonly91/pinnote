package com.example.pinnote;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.pinnote.comm.AListViewItemLCListener;
import com.example.pinnote.comm.ListViewItemClickBaseListener;
import com.example.pinnote.comm.NoteType;
import com.example.pinnote.comm.ViewInlineFragment;
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
 * Time: 下午10:57
 * To change this template use File | Settings | File Templates.
 */
public class AFragment extends Fragment implements ViewInlineFragment{
	private ListView todoList;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)  {
    	View view = inflater.inflate(R.layout.first, container, false);
    	fillListData(view);
    	
        return view;
    }
    
    public void refreshListData(){
    	if (todoList == null){
    		Log.e("liny:", "AFragment Call refreshList but todolist is null");
    		return;
    	}
    	
    	Note note_data[] = DBUtil.getTodoNoteArray(getActivity());
    	if (null != note_data)
    	{
    		NoteAdapter noteAdapter = new NoteAdapter(getActivity(), R.layout.mainlist_item_row, note_data);
    		noteAdapter.setNotifyOnChange(true);
    		todoList.setAdapter(noteAdapter);
    		
    	}
    }
    
    private void fillListData(View view){
    	todoList = (ListView)view.findViewById(R.id.todoList);
    	todoList.setOnItemLongClickListener(new AListViewItemLCListener(todoList, NoteType.TODO, getActivity()));
    	todoList.setOnItemClickListener(new ListViewItemClickBaseListener(getActivity()));
    	
    	if (todoList != null){
//    		List<String> data = new ArrayList<String>();
//        	for (int i = 0; i < 20; i++) {
//        		data.add("data0:" + i);
//        	}
        	
//        	Note note_data[] = new Note[]{
//        			new Note("Hello"),
//        			new Note("Goodbye"),
//        			new Note("Huawei"),
//        			new Note("How Could you fall in love with him"),
//        			new Note("TerryGuy1001@gmail.com"),
//        			new Note("��������Ʊ"),
//        			new Note("ThinkPad T430u")
//        			};
        	Note note_data[] = DBUtil.getTodoNoteArray(getActivity());
        	if (null != note_data)
        	{
        		NoteAdapter noteAdapter = new NoteAdapter(getActivity(), R.layout.mainlist_item_row, note_data);
        		noteAdapter.setNotifyOnChange(true);
        		todoList.setAdapter(noteAdapter);
        		
        		Log.v("liny:", "AFragment Call fillListData!");
        	}
    	}
    }
}

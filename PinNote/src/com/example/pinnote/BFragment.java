package com.example.pinnote;

import java.util.ArrayList;
import java.util.List;

import com.example.pinnote.comm.BListViewItemLCListener;
import com.example.pinnote.comm.NoteType;
import com.example.pinnote.db.DBUtil;

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
 * Time: 下午10:57
 * To change this template use File | Settings | File Templates.
 */
public class BFragment extends Fragment {
	private ListView doingList;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)  {
    	View view = inflater.inflate(R.layout.second, container, false);
    	fillListData(view);
        return view;
    }
    
    private void fillListData(View view){
    	doingList = (ListView)view.findViewById(R.id.doingList);
    	doingList.setOnItemLongClickListener(new BListViewItemLCListener(doingList, NoteType.DOING, getActivity()));
    	
    	if (doingList != null){
    		Note note_data[] = DBUtil.getDoingNoteArray(getActivity());
        	if (null != note_data)
        	{
        		NoteAdapter noteAdapter = new NoteAdapter(getActivity(), R.layout.mainlist_item_row, note_data);
        		noteAdapter.setNotifyOnChange(true);
        		doingList.setAdapter(noteAdapter);
        	}
    	}
    }
}

package com.example.pinnote;

import com.example.pinnote.comm.CListViewItemLCListener;
import com.example.pinnote.comm.NoteType;
import com.example.pinnote.comm.ViewInlineFragment;
import com.example.pinnote.db.DBUtil;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created with IntelliJ IDEA.
 * User: Terry
 * Date: 13-12-20
 * Time: 下午10:58
 * To change this template use File | Settings | File Templates.
 */
public class CFragment extends Fragment implements ViewInlineFragment{
	private ListView doneList;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)  {
    	View view = inflater.inflate(R.layout.third, container, false);
    	fillListData(view);
        return view;
    }
    
    private void fillListData(View view){
    	doneList = (ListView)view.findViewById(R.id.doneList);
    	doneList.setOnItemLongClickListener(new CListViewItemLCListener(doneList, NoteType.DONE, getActivity()));
    	
    	if (doneList != null){
    		Note note_data[] = DBUtil.getDoneNoteArray(getActivity());
        	if (null != note_data)
        	{
        		NoteAdapter noteAdapter = new NoteAdapter(getActivity(), R.layout.mainlist_item_row, note_data);
        		noteAdapter.setNotifyOnChange(true);
        		doneList.setAdapter(noteAdapter);
        	}
    	}
    }

	@Override
	public void refreshListData() {
		// TODO Auto-generated method stub
		if (doneList == null){
    		Log.e("liny:", "CFragment Call refreshList but doneList is null");
    		return;
    	}
    	
    	Note note_data[] = DBUtil.getDoneNoteArray(getActivity());
    	if (null != note_data)
    	{
    		NoteAdapter noteAdapter = new NoteAdapter(getActivity(), R.layout.mainlist_item_row, note_data);
    		noteAdapter.setNotifyOnChange(true);
    		doneList.setAdapter(noteAdapter);
    		
    	}
	}
}

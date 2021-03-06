package com.example.pinnote.comm;

import java.util.Arrays;

import com.example.pinnote.Note;
import com.example.pinnote.NoteAdapter;
import com.example.pinnote.R;
import com.example.pinnote.db.DBUtil;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class BListViewItemLCListener extends ListViewItemLongClickBaseListener{
	
	private ListView listView;
	private Context context;
	private NoteType noteType;

	public BListViewItemLCListener(ListView listView, NoteType noteType,
			Context context) {
		super(listView, noteType, context);
		// TODO Auto-generated constructor stub
		
		this.listView = listView;
		this.noteType = noteType;
		this.context = context;
	}

	public int getLayoutResID(){
		return R.layout.longclick_dialog;
	}

	@Override
	public void regDialogEvent(Dialog dialog, Note note) {
		// TODO Auto-generated method stub
		Button pinBtn = (Button)dialog.findViewById(R.id.pinBtn);
		Button doBtn = (Button)dialog.findViewById(R.id.doBtn);
		Button viewBtn = (Button)dialog.findViewById(R.id.delBtn);
		Button cancelBtn = (Button)dialog.findViewById(R.id.cancelBtn);
		Button doneBtn = (Button)dialog.findViewById(R.id.doneBtn);
		Button fileBtn = (Button)dialog.findViewById(R.id.fileNoteBtn);
		OnClickListener itemBtnOnClickListener = new BItemBtnClickListener(dialog, note);
		pinBtn.setOnClickListener(itemBtnOnClickListener);
		doneBtn.setOnClickListener(itemBtnOnClickListener);
		viewBtn.setOnClickListener(itemBtnOnClickListener);
		cancelBtn.setOnClickListener(itemBtnOnClickListener);
		doBtn.setVisibility(View.GONE);
		fileBtn.setVisibility(View.GONE);
	}
	
	class BItemBtnClickListener implements OnClickListener
	{
		private Dialog dialog;
		private Note note;
		
		public BItemBtnClickListener(Dialog dialog, Note note){
			this.dialog = dialog;
			this.note = note;
		}
		
		@Override
		public void onClick(View v) {
			if (null == dialog || null == note){
				return;
			}
			Note note_data[] = null;
					
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.pinBtn:
				//pinNote(noteId)
				break;
			case R.id.doneBtn:
				DBUtil.updateNoteType(context, note, NoteType.DONE);
				
				//switch to doing listview tab
				note_data = DBUtil.getDoingNoteArray(context);
	        	if (null != note_data)
				{
	        		NoteAdapter noteAdapter = new NoteAdapter(context, R.layout.mainlist_item_row, note_data);
	        		noteAdapter.setNotifyOnChange(true);
	        		listView.setAdapter(noteAdapter);
	        		Toast.makeText(context, "Mark it done", Toast.LENGTH_SHORT).show();
				}
	        	
				dialog.dismiss();
				break;
			case R.id.delBtn:
				DBUtil.delNote(context, note.getmId(), note.getType());
				
				//refresh listview data
				note_data = DBUtil.getDoingNoteArray(context);
	        	if (null != note_data)
				{
	        		NoteAdapter noteAdapter = new NoteAdapter(context, R.layout.mainlist_item_row, note_data);
	        		noteAdapter.setNotifyOnChange(true);
	        		listView.setAdapter(noteAdapter);
	        		Toast.makeText(dialog.getContext(), "ɾ���ɹ�", Toast.LENGTH_SHORT).show();
				}
	        	
				dialog.dismiss();
				break;
			case R.id.cancelBtn:
				dialog.dismiss();
				break;
			default:
				Log.e("pinnote:BItemBtnClickListener", "err btn id:"+v.getId());
				break;
			}
		}
		
	}
}

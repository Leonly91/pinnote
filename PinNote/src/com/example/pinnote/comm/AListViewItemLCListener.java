package com.example.pinnote.comm;

import com.example.pinnote.Note;
import com.example.pinnote.NoteAdapter;
import com.example.pinnote.R;
import com.example.pinnote.db.DBUtil;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class AListViewItemLCListener extends ListViewItemLongClickBaseListener{
	
	private ListView listView;
	private Context context;
	private NoteType noteType;

	public AListViewItemLCListener(ListView listView, NoteType noteType,
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
		OnClickListener itemBtnOnClickListener = new AItemBtnClickListener(dialog, note);
		pinBtn.setOnClickListener(itemBtnOnClickListener);
		doBtn.setOnClickListener(itemBtnOnClickListener);
		viewBtn.setOnClickListener(itemBtnOnClickListener);
		cancelBtn.setOnClickListener(itemBtnOnClickListener);
		doneBtn.setVisibility(View.GONE);
	}
	
	class AItemBtnClickListener implements OnClickListener
	{
		private Dialog dialog;
		private Note note;
		
		public AItemBtnClickListener(Dialog dialog, Note note){
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
			case R.id.delBtn:
				DBUtil.delNote(context, note.getmId(), note.getType());
				
				//refresh listview data
				note_data = DBUtil.getTodoNoteArray(context);
	        	if (null != note_data)
				{
	        		NoteAdapter noteAdapter = new NoteAdapter(context, R.layout.mainlist_item_row, note_data);
	        		noteAdapter.setNotifyOnChange(true);
	        		listView.setAdapter(noteAdapter);
	        		Toast.makeText(dialog.getContext(), "É¾³ý³É¹¦", Toast.LENGTH_SHORT).show();
				}
	        	
				dialog.dismiss();
				break;
			case R.id.doBtn:
				DBUtil.updateNoteType(context, note, NoteType.DOING);
				
				//switch to doing listview tab
				note_data = DBUtil.getTodoNoteArray(context);
	        	if (null != note_data)
				{
	        		NoteAdapter noteAdapter = new NoteAdapter(context, R.layout.mainlist_item_row, note_data);
	        		noteAdapter.setNotifyOnChange(true);
	        		listView.setAdapter(noteAdapter);
				}
	        	
				dialog.dismiss();
				break;
			case R.id.cancelBtn:
				dialog.dismiss();
				break;
			default:
				break;
			}
		}
		
	}
}

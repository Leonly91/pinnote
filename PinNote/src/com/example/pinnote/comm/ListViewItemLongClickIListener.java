package com.example.pinnote.comm;

import java.util.List;
import java.util.Map;

import com.example.pinnote.Note;
import com.example.pinnote.NoteAdapter;
import com.example.pinnote.R;
import com.example.pinnote.db.DBUtil;

import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class ListViewItemLongClickIListener implements OnItemLongClickListener{
	private ListView listView;
	private LinearLayout dropLayout;
	private Context context;
	private List<Note> noteList;
	
	public ListViewItemLongClickIListener(){
		super();
	}
	
	public ListViewItemLongClickIListener(ListView listView, Context context){
		super();
		this.listView = listView;
		this.context = context;
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
		//show the dropdownlist, hide other dropdownlist
		/*
		dropLayout = (LinearLayout)view.findViewById(R.id.dropDownList1);
		Animation fade_in = new TranslateAnimation(
		          Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
		          Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f
		      );
		fade_in.setDuration(500);
		
		dropLayout.startAnimation(fade_in);// the animation doesn't work
		dropLayout.setVisibility(View.VISIBLE);
		
	    int totalCount =  parent.getCount();
	    for (int i = 0; i< totalCount; i++)
	    {
	    	if (i == position)
	    	{
	    		continue;
	    	}
	    	if (null == listView){
	    		break;
	    	}
	    	
	    	View otherView = (View)parent.getAdapter().getView(position, null, listView);
	    	LinearLayout tmpdropLayout = (LinearLayout)otherView.findViewById(R.id.dropDownList1);
	    	String tmp = "";
	    	if (null != tmpdropLayout){
	    		tmpdropLayout.setVisibility(View.GONE);
	    	}else{
	    		tmp = "null";
	    	}
		Toast.makeText(parent.getContext(), "otherVIew:"+otherView.getClass()+","+tmp, Toast.LENGTH_SHORT).show();
	    }
	    */
		
		//Show Dialog
		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		dialog.setContentView(R.layout.longclick_dialog);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		Button pinBtn = (Button)dialog.findViewById(R.id.pinBtn);
		Button shareBtn = (Button)dialog.findViewById(R.id.doBtn);
		Button viewBtn = (Button)dialog.findViewById(R.id.delBtn);
		Button cancelBtn = (Button)dialog.findViewById(R.id.cancelBtn);
		OnClickListener itemBtnOnClickListener = new ItemBtnClickListener(dialog, (Note)parent.getItemAtPosition(position));
		pinBtn.setOnClickListener(itemBtnOnClickListener);
		shareBtn.setOnClickListener(itemBtnOnClickListener);
		viewBtn.setOnClickListener(itemBtnOnClickListener);
		cancelBtn.setOnClickListener(itemBtnOnClickListener);

		dialog.show();
		Window window = dialog.getWindow();
		window.setLayout(400, LayoutParams.WRAP_CONTENT);
		
		Vibrator vib = (Vibrator)context.getSystemService(Service.VIBRATOR_SERVICE); 
		vib.vibrate(30);
		
		return false;
	}
	
	class ItemBtnClickListener implements OnClickListener
	{
		private Dialog dialog;
		private Note note;
		
		public ItemBtnClickListener(Dialog dialog, Note note){
			this.dialog = dialog;
			this.note = note;
		}
		
		@Override
		public void onClick(View v) {
			if (null == dialog || null == note){
				return;
			}
			
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.pinBtn:
				//pinNote(noteId)
				break;
			case R.id.delBtn:
				DBUtil.delNote(dialog.getContext(), note.getmId(), note.getType());
				
				//refresh listview data
				Note note_data[] = DBUtil.getTodoNoteArray(context);
	        	if (null != note_data)
				{
	        		NoteAdapter noteAdapter = new NoteAdapter(context, R.layout.mainlist_item_row, note_data);
	        		noteAdapter.setNotifyOnChange(true);
	        		listView.setAdapter(noteAdapter);
				}
	        	
				dialog.dismiss();
				break;
			case R.id.doBtn:
				//shareNote(noteId);
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

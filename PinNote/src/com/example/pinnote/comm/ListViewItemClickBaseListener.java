package com.example.pinnote.comm;

import com.example.pinnote.AddNoteActivity;
import com.example.pinnote.MainActivity;
import com.example.pinnote.Note;
import com.example.pinnote.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class ListViewItemClickBaseListener implements OnItemClickListener {
	
	private Context context;
	
	public ListViewItemClickBaseListener(Context context){
		this.context = context;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Note note = (Note)parent.getItemAtPosition(position);
		
		//jump to AddNoteActivity
		Intent intent = new Intent();
    	intent.setClass(context, AddNoteActivity.class);
    	intent.putExtra("editNoteId", note.getmId());
    	intent.putExtra("editNoteType", NoteType.TODO.ordinal());
    	context.startActivity(intent);
    	((FragmentActivity)context).overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		
//		Toast.makeText(context, note.getmTitle(), Toast.LENGTH_SHORT).show();
	}

}

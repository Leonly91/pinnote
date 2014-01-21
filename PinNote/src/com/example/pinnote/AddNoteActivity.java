package com.example.pinnote;

import com.example.pinnote.comm.NoteType;
import com.example.pinnote.db.DBUtil;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class AddNoteActivity extends Activity{
	
	private String m_titleStr;
	private String m_contentStr;
	private EditText m_noteTitleEdit;
	private EditText m_noteContentEdit;
	private ActionBar actionBar;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_note);
		
		actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		//set cursor focus on title textview
		m_noteTitleEdit = (EditText)findViewById(R.id.new_event_title);
		m_noteTitleEdit.setFocusable(true);
		m_noteTitleEdit.requestFocus();
		
		m_noteContentEdit = (EditText)findViewById(R.id.new_event_content);
		
//		Note[] note_data = DBUtil.TestRetrieveObject(this);
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if (keyCode == KeyEvent.KEYCODE_BACK){
			this.finish();
			overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	public boolean onMenuItemSelected(int featureId, MenuItem item){
		int itemId = item.getItemId();
		switch(itemId){
		case android.R.id.home:
			super.onMenuItemSelected(featureId, item);
			this.finish();
			overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
			break;
		case R.id.newOKBtn:
			
			Note note = new Note(m_noteTitleEdit.getText().toString());
			note.setType(NoteType.TODO);
			if (DBUtil.addNote(this, note, NoteType.TODO))
			{
				Log.v("liny:", "add note succesds");
			}else{
				Log.v("liny:", "add note fail");
			}
		    Intent intent = new Intent(this, MainActivity.class);
		    AddNoteActivity.this.startActivity(intent);
			this.finish();
			overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
			break;
		default:
			break;
		}
		
		return true;
	}
	
	public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.new_note, menu);
        return true;
    }
}

package com.example.pinnote;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;

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

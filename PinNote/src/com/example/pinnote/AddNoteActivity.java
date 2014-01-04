package com.example.pinnote;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;

public class AddNoteActivity extends Activity{
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_note);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
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
}

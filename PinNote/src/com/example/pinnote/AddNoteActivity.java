package com.example.pinnote;

import java.util.Calendar;

import com.example.pinnote.comm.NoteType;
import com.example.pinnote.comm.TimeAlarm;
import com.example.pinnote.comm.Util;
import com.example.pinnote.comm.ViewInlineFragment;
import com.example.pinnote.db.DBUtil;
import com.example.pinnote.widget.AppWidgetActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

public class AddNoteActivity extends Activity implements OnClickListener{
	
	private String endTime;
	private EditText m_noteTitleEdit;
	private EditText m_noteContentEdit;
	private ActionBar actionBar;
	private Note crntNote;
	
	private DatePicker datePicker;
	private TimePicker timePicker;
	private Button alarmMeBtn;
	
	private Dialog timeDialog;
	private boolean editMode;
	
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private int alarmMode;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_note);
		
		Log.v("widget_pinnote","add note activity");
		
		editMode = false;
		crntNote = new Note("");
		crntNote.setAlarmFlag(0);
		
		actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		//set cursor focus on title textview
		m_noteTitleEdit = (EditText)findViewById(R.id.new_event_title);
		m_noteTitleEdit.setFocusable(true);
		m_noteTitleEdit.requestFocus();
		
		m_noteContentEdit = (EditText)findViewById(R.id.new_event_content);
		
        TextView timeView = (TextView)findViewById(R.id.endTimeText);
        timeView.setVisibility(View.GONE);
        alarmMeBtn = (Button)findViewById(R.id.alarmMeBtn);
		
		Intent intent = this.getIntent();
		if (intent != null){
			String noteId = intent.getStringExtra("editNoteId");
			NoteType noteType = NoteType.values()[intent.getIntExtra("editNoteType",0)];
			
			if (noteId != null){
				editMode = true;
				Note note = DBUtil.getNote(this, noteId, noteType);
				if (note != null){
					m_noteTitleEdit.setText(note.getmTitle());
					m_noteContentEdit.setText(note.getmContent());
					
					if (note.getmDeadLine() != ""){
						timeView.setText(note.getmDeadLine());
						timeView.setVisibility(View.VISIBLE);
					}
					if (note.getAlarmFlag() == 1){
						crntNote.setAlarmFlag(1);
						alarmMeBtn.setText(R.string.cancelAlarmMe);
					}
					
					crntNote = note.getCopyObject();
				}
			}
		}
		
		Button showTimePickerBtn = (Button)findViewById(R.id.showTimePickerBtn);
		showTimePickerBtn.setOnClickListener(this);
		alarmMeBtn.setOnClickListener(this);
		
		Calendar calendar = Calendar.getInstance();  
        year = calendar.get(Calendar.YEAR);  
        month = calendar.get(Calendar.MONTH);  
        day = calendar.get(Calendar.DAY_OF_MONTH);  
        hour = calendar.get(Calendar.HOUR);  
        minute = calendar.get(Calendar.MINUTE);

	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if (keyCode == KeyEvent.KEYCODE_BACK){
			this.finish();
			overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void sendNewBroadcast(){
		Intent intent = new Intent();
		intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
		int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), AppWidgetActivity.class));
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
		this.sendBroadcast(intent);
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
			crntNote.setmTitle(m_noteTitleEdit.getText().toString());
			crntNote.setmContent(m_noteContentEdit.getText().toString());
			if (endTime != null && endTime != ""){
				crntNote.setmDeadLine(endTime+":00");
			}
			if (editMode == true){
				if (!DBUtil.updateNote(this, crntNote)){
					Log.e("liny:", "update note fail");
				}
			}
			else {
				crntNote.setType(NoteType.TODO);
				if (!DBUtil.addNote(this, crntNote, NoteType.TODO)){
					Log.e("liny:", "add note fail");
				}
			}
			if (crntNote.getAlarmFlag() == 1)
			{
				Util.sendNotification(this, crntNote);
			}
			
			sendNewBroadcast();
			
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.showTimePickerBtn:
			timeDialog = new Dialog(this);
			timeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
			timeDialog.setContentView(R.layout.time_picker);
//			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			timeDialog.show();
			
			Button timeOkBtn = (Button)timeDialog.findViewById(R.id.timePickerOKBtn);
			timeOkBtn.setOnClickListener(this);
			
			datePicker = (DatePicker)timeDialog.findViewById(R.id.dpPicker);
			timePicker = (TimePicker)timeDialog.findViewById(R.id.tpPicker);
			
			if (datePicker != null)
			{
				datePicker.init(year, month, day, new OnDateChangedListener(){
					
					@Override
					public void onDateChanged(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						// TODO Auto-generated method stub
						AddNoteActivity.this.year = year;
						AddNoteActivity.this.month = monthOfYear;
						AddNoteActivity.this.day = dayOfMonth;
					}
					
				});
			}
			
			if (timePicker != null)
			{
				timePicker.setOnTimeChangedListener(new OnTimeChangedListener(){
					
					@Override
					public void onTimeChanged(TimePicker view, int hour, int minute) {
						// TODO Auto-generated method stub
						AddNoteActivity.this.hour = hour;
						AddNoteActivity.this.minute = minute;
					}
					
				});
			}
			break;
		case R.id.timePickerOKBtn:
			String time = year + "-"+(month+1)+"-"+day+" "+hour+":"+minute+"";
			TextView timeView = (TextView)findViewById(R.id.endTimeText);
			timeView.setText(time);
			timeView.setVisibility(View.VISIBLE);
			endTime = time;
			timeDialog.dismiss();
			
			break;
		case R.id.alarmMeBtn:
			int alarmMeBtnText;
			
			if (crntNote.getAlarmFlag() == 0 ){
				alarmMeBtnText = R.string.cancelAlarmMe;
				crntNote.setAlarmFlag(1);
			}
			else{
				alarmMeBtnText = R.string.alarmMe;
				crntNote.setAlarmFlag(0);
			}
			alarmMeBtn.setText(alarmMeBtnText);
			
			//show notification
//			String str = "Notification";
//			int icon = R.drawable.ic_launcher;
//			long when = System.currentTimeMillis() + 1000;
//			Notification noti =new Notification.Builder(this)
//					.setContentTitle(str)
//					.setSmallIcon(icon)
//					.setContentText("notification text").build();
//			noti.flags = Notification.FLAG_ONLY_ALERT_ONCE | Notification.FLAG_SHOW_LIGHTS;
//			noti.defaults = Notification.DEFAULT_SOUND;
//			noti.when = when;
//			
//			NotificationManager mnotiManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE); 
//			mnotiManager.notify(0, noti);
			
			
//			Calendar calendar = Calendar.getInstance();
//			
//			Intent intent  = new Intent(AddNoteActivity.this, TimeAlarm.class);
//			intent.putExtra("note_title", "NoteTitle");
//			intent.putExtra("note_content", "NoteContent");
//			
//			PendingIntent pendingIntent = PendingIntent.getBroadcast(AddNoteActivity.this, 0, intent, 0);
//			
//			AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//			int alarmType = AlarmManager.RTC;//ELAPSED_REALTIME_WAKEUP;
//			alarmManager.set(alarmType, calendar.getTimeInMillis()+3000, pendingIntent);//after one second
			
			break;
		default:
			break;
		}
	}
}

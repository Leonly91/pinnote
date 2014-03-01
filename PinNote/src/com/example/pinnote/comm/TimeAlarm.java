package com.example.pinnote.comm;

import com.example.pinnote.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TimeAlarm extends BroadcastReceiver {
	
	private Context context;
	
	private void showNotification(Intent intent){
		// TODO Auto-generated method stub
		NotificationManager manger = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		
		String titleStr = intent.getStringExtra("note_title");
		String contentStr = intent.getStringExtra("note_content");
		int icon = R.drawable.ic_launcher;
		
		Notification noti = new Notification.Builder(context)
			.setContentTitle(titleStr)
			.setSmallIcon(icon)
			.setContentText(contentStr).build();
        noti.flags = Notification.FLAG_ONLY_ALERT_ONCE | Notification.FLAG_SHOW_LIGHTS;
        noti.defaults = Notification.DEFAULT_SOUND;
        
//        PendingIntent contentIntent = PendingIntent.getActivity(context,
//                1, new Intent(context, TimeAlarm.class),0);
        
        manger.notify(1, noti);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		
		this.context = context;
		
		Log.v("liny:", "TimeAlarm receives a boardcast :"+intent.getStringExtra("note_title"));
		
        showNotification(intent);
	}

}

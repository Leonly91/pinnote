package com.example.pinnote.comm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.pinnote.AddNoteActivity;
import com.example.pinnote.Note;
import com.example.pinnote.R;

public class Util {
	
	public static Date parsetoDate(String string){
		SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date date = null;
		try {
			date = format.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	public static long getMinutes(Date date){
		long minutes = 0;
		
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(date);
		minutes += calendar.getTimeInMillis();
		return minutes;
	}
	
	public static int getColorRscByTime(Note note){
		int retColor = R.color.green_normal;
		
		Calendar calendar = Calendar.getInstance();  
        int year = calendar.get(Calendar.YEAR);  
        int month = calendar.get(Calendar.MONTH);  
        int day = calendar.get(Calendar.DAY_OF_MONTH);  
        int hour = calendar.get(Calendar.HOUR);  
        int minute = calendar.get(Calendar.MINUTE);
        int second = 0;
        
        /* 1. 计算从创建到截止时间差
         * 2. 计算当前时间在该时间差内百分比
         * 3. 根据百分比来设置颜色
         * 4. 以分钟为单位 */
        long millisNow = calendar.getTimeInMillis();
        
        Date endDate = Util.parsetoDate(note.getmDeadLine());
        calendar.setTime(endDate);
		long millisEnd = calendar.getTimeInMillis();
		
        Date createDate = Util.parsetoDate(note.getCreateTime());
        calendar.setTime(createDate);
        long millisCreate =  calendar.getTimeInMillis();
        
        long percent = 1;
        if (millisNow > millisCreate)
        {
        	percent = (millisEnd - millisCreate)/(millisNow - millisCreate);
        }
        percent *= 100;
        if (percent < 20){
        	retColor = R.color.green_normal;
        }else if (percent < 40){
        	retColor = R.color.green_yellow;
        }else if(percent < 60){
        	retColor = R.color.yellow_alarm;
        }else if(percent < 80){
        	retColor = R.color.yellow_red;
        }else{
        	retColor = R.color.red_fatal;
        }
        
                
		return retColor;
	}
	
	public static void sendNotification(Context context, Note note){
		Calendar calendar = Calendar.getInstance();
		Date date  = parsetoDate(note.getmDeadLine());
		calendar.setTime(date);
		long alarmWhen = calendar.getTimeInMillis() - 60*1000;//alarm right that
		
		Intent intent  = new Intent(context, TimeAlarm.class);
		intent.putExtra("note_title", note.getmTitle());
		intent.putExtra("note_content", note.getmContent());
		
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
		
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
		int alarmType = AlarmManager.RTC;//ELAPSED_REALTIME_WAKEUP;
		alarmManager.set(alarmType, alarmWhen, pendingIntent);
	}
}

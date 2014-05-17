package com.example.pinnote.widget;

import com.example.pinnote.AddNoteActivity;
import com.example.pinnote.MainActivity;
import com.example.pinnote.R;
import com.example.pinnote.R.layout;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.Toast;

public class AppWidgetActivity extends AppWidgetProvider{
	//private Context context;
	
	public static final String BTN_ADD_ACTION = "com.example.pinnote.widget.BTN_ADD_ACTION";
	public static final String COLLECTION_VIEW_REFRESH = "com.example.pinnote.widget.COLLECTION_REFRESH_DATA";
	public static final String COLLECTION_VIEW_ACTION = "com.example.pinnote.widget.COLLECTION_VIEW_ACTION";
    public static final String COLLECTION_VIEW_EXTRA = "com.example.pinnote.widget.COLLECTION_VIEW_EXTRA";
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		//this.context = context;
		
		Intent intent = new Intent(context, ListWidgetService.class);
		
		Log.v("widget_pinnote", "onUpdate. appWidgetIds.length = "+appWidgetIds.length + "; id[0] = "+appWidgetIds[0]);
		
		for (int i = 0; i <  appWidgetIds.length; i++)
		{
			if (appWidgetIds[i] == AppWidgetManager.INVALID_APPWIDGET_ID){
				continue;
			}
			
			RemoteViews myRemoteView = new RemoteViews(context.getPackageName(), R.layout.app_widget_layout);
			myRemoteView.setRemoteAdapter(R.id.widget_listview, intent);
			
			Intent addIntent = new Intent();
			addIntent.setAction(BTN_ADD_ACTION);
			PendingIntent addPendingIntent =  PendingIntent.getBroadcast(context, 0, addIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			myRemoteView.setOnClickPendingIntent(R.id.app_widget_add_btn, addPendingIntent);
			
			Intent listIntent = new Intent();
			listIntent.setAction(COLLECTION_VIEW_ACTION);
			listIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, listIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			myRemoteView.setPendingIntentTemplate(R.id.widget_listview, pendingIntent);
			
			appWidgetManager.updateAppWidget(appWidgetIds[i], myRemoteView);
		}
		
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
	
	@Override
    public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		
		String action = intent.getAction();
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		
		Log.v("widget_pinnote", "OnReceive:"+action);
		
		if (action.equals(COLLECTION_VIEW_ACTION)){
			//view note detail
			int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
			int viewIndex  = intent.getIntExtra(COLLECTION_VIEW_EXTRA, 0);
			
			//跳转到详细界面
			Log.v("widget_pinnote", "ListView Ite Click:"+viewIndex+", appWidgetId:"+appWidgetId);
		}
		else if (action.equals(BTN_ADD_ACTION)){
			//add new note
			Intent addIntent = new Intent();
			addIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			addIntent.setClass(context, AddNoteActivity.class);
			context.startActivity(addIntent);
		}
		else if (action.equals(COLLECTION_VIEW_REFRESH)){
			//refresh listveiw data
			Log.v("widget_pinnote", "receive a COLLECTION_VIEW_REFRESH");
			
			updateListViewData(context);
			
			return;
		}
	}
	
	private void updateListViewData(Context context){
		if (context == null){
			Log.e("widget_pinnote", "AppWidgetActivity call updateListViewData fail. context is null");
			return;
		}
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		int appWidgetIds[] = appWidgetManager.getAppWidgetIds(new ComponentName(context, AppWidgetActivity.class));
		appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_listview);
	}
	
	@Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        
    }
}

package com.example.pinnote.widget;

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

public class AppWidgetActivity extends AppWidgetProvider{
	private int[] appWidgetIds;
	private Context context;
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		this.appWidgetIds = appWidgetIds;
		this.context = context;
		
		Intent intent = new Intent(context, ListWidgetService.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
		
		for (int i = 0; i <  appWidgetIds.length; i++)
		{
			RemoteViews myRemoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget_layout);
			myRemoteViews.setRemoteAdapter( R.id.widget_listview, intent);
			appWidgetManager.updateAppWidget(appWidgetIds[i], myRemoteViews);
		}
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		Log.v("widget:", "AppWidgetActivity.onUpdate");
	}
	
	private void updateList(Context context){
		RemoteViews listRemoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget_layout);
//		listRemoteViews.
	}
	
	@Override
    public void onEnabled(Context context) {
        Log.v("liny:","AppWidgetActivity onEnabled called");
        super.onEnabled(context);
        
    }
}

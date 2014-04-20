package com.example.pinnote.widget;

import java.util.ArrayList;
import java.util.List;

import com.example.pinnote.Note;
import com.example.pinnote.R;
import com.example.pinnote.comm.Util;
import com.example.pinnote.db.DBUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

public class ListRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory{

	private Context context;
	private List<Note> data = new ArrayList<Note>();
	
	public ListRemoteViewFactory(Context context, Intent intent){
		Log.v("widget_pinnote", "ListRemoteViewFactory");
		this.context = context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public RemoteViews getLoadingView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RemoteViews getViewAt(int position) {
		// TODO Auto-generated method stub
		int color = context.getResources().getColor(Util.getColorRscByTime(data.get(position)));
		int drawableId;
		RemoteViews rv = new RemoteViews (context.getPackageName(), R.layout.app_widget_item);
		rv.setTextViewText(R.id.app_widget_item_text, data.get(position).getmTitle());
		String shortTime = Util.getShortTimeStr(data.get(position).getmDeadLine());
		if (null != shortTime){	
			rv.setTextViewText(R.id.app_widget_item_time, shortTime);
		}
		
		Drawable drawable = context.getResources().getDrawable(R.drawable.circle);
		
		GradientDrawable  gd1 = new GradientDrawable();
		gd1.setColor(color);
		gd1.setShape(GradientDrawable.OVAL);
//		drawableId = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
		
		Intent viewIntent = new Intent();
		viewIntent.putExtra(AppWidgetActivity.COLLECTION_VIEW_EXTRA, position);
		rv.setOnClickFillInIntent(R.id.app_widget_item_layout, viewIntent);
//		Log.v("widget_pinnote", "ListRemoteViewFactory:getViewAt:"+data.get(position).getmTitle());
				
		rv.setInt(R.id.app_widget_imgIcon, "setBackgroundResource", R.drawable.circle);
		return rv;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		if (null != context){
			Note note_data[] = DBUtil.getTodoNoteArray(context);
			for (Note note: note_data){
				data.add(note);
			}
		}
	}

	@Override
	public void onDataSetChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		data.clear();
	}

}

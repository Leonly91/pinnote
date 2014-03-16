package com.example.pinnote;


import com.example.pinnote.comm.Util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.transition.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NoteAdapter extends ArrayAdapter<Note>{
	Context context;
	int layoutResourceId;
	Note data[] = null;

	public NoteAdapter(Context context, int resource, Note[] objects) {
		super(context, resource, objects);
		this.context = context;
		this.layoutResourceId = resource;
		this.data = objects;
		// TODO Auto-generated constructor stub
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		View row = convertView;
		
		NoteHolder holder = null;
		
		if (row == null)
		{
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, null);
			
			holder = new NoteHolder();
			holder.imgIcon   = (ImageView)row.findViewById(R.id.imgIcon);
			holder.txtTitle  = (TextView)row.findViewById(R.id.noteText);
			holder.endTime   = (TextView)row.findViewById(R.id.endTime);
			holder.alarmIcon = (ImageView)row.findViewById(R.id.smlAlarmIcon);
			
			row.setTag(holder);
		}
		else{
			holder = (NoteHolder)row.getTag();
		}
		
		Note note = data[position];
		holder.txtTitle.setText(note.getmTitle());
		GradientDrawable backgroundGradient = (GradientDrawable)holder.imgIcon.getBackground();
		backgroundGradient.setColor(context.getResources().getColor(Util.getColorRscByTime(note)));
//		backgroundGradient.setColor(context.getResources().getColor(R.color.green_normal));
		if (note.getAlarmFlag() == 1){
			holder.alarmIcon.setVisibility(View.VISIBLE);
		}else{
			holder.alarmIcon.setVisibility(View.GONE);
		}
		String shortTime = Util.getShortTimeStr(note.getmDeadLine());
		if (null != shortTime){			
			holder.endTime.setText(shortTime);
		}
		
		return row;
	}
	
	static class NoteHolder
    {
		ImageView imgIcon;
        TextView txtTitle;
        TextView endTime;
        ImageView alarmIcon;
    }
}

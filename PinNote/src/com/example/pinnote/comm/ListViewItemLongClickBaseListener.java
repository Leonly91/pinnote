package com.example.pinnote.comm;

import java.util.List;
import java.util.Map;

import com.example.pinnote.Note;
import com.example.pinnote.NoteAdapter;
import com.example.pinnote.R;
import com.example.pinnote.db.DBUtil;
import com.example.pinnote.widget.AppWidgetActivity;

import android.app.Dialog;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public abstract class ListViewItemLongClickBaseListener implements OnItemLongClickListener{
	private ListView listView;
	private Context context;
	
	public ListViewItemLongClickBaseListener(ListView listView, NoteType noteType, Context context){
		super();
		this.listView = listView;
		this.context = context;
	}
	
	public abstract int getLayoutResID();
	
	public abstract void regDialogEvent(Dialog dialog, Note note);
	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
			long id) {
//		Toast.makeText(parent.getContext(), "otherVIew:"+otherView.getClass()+","+tmp, Toast.LENGTH_SHORT).show();
		
		//Show Dialog
		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		dialog.setContentView(getLayoutResID());
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		
		regDialogEvent(dialog, (Note)parent.getItemAtPosition(position));

		dialog.show();
		Window window = dialog.getWindow();
		window.setLayout(400, LayoutParams.WRAP_CONTENT);
		
		Vibrator vib = (Vibrator)context.getSystemService(Service.VIBRATOR_SERVICE); 
		vib.vibrate(30);
		
		return false;
	}
	
	protected void sendRefreshWidgetBoardcast(Context context){
		if (context == null){
			return;
		}
		Intent intent = new Intent();
		intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
		int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, AppWidgetActivity.class));
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
		context.sendBroadcast(intent);
	}
	
}

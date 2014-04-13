package com.example.pinnote.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class ListWidgetService extends RemoteViewsService {

	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
		// TODO Auto-generated method stub
		return new ListRemoteViewFactory(this, intent);
	}

}

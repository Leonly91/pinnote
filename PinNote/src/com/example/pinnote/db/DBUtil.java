package com.example.pinnote.db;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.example.pinnote.Note;
import com.example.pinnote.R;
import com.google.gson.Gson;

public class DBUtil {
	private static SharedPreferences sharedPref = null;
	
	public static SharedPreferences getSharedPref(Context context){
		if (null == sharedPref){
			sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
		}
		return sharedPref;
	}
	
	public static boolean saveNote(Note note){
		boolean ret = true;
		try{
			
		}catch(Exception ex){
			Log.e("DBUtil call saveNote failed:", ex.getMessage());
			ret = false;
		}
		return ret;
	}
	
	public static boolean saveNoteList(Context context, String key, List<Note> list){
		boolean ret = true;
		try{
			Editor prefsEditor = getSharedPref(context).edit();
			Gson note = new Gson();
		}catch(Exception ex){
			Log.e("DBUtil call saveList failed:", ex.getMessage());
			ret = false;
		}
		return ret;
	}
	
	public static List<Note> getNoteList(String key){
		return null;
	}
	
	public static void TestSaveObject(Context context){
		Editor prefsEditor = getSharedPref(context).edit();
		Gson note = new Gson();
		Note o = new Note(0, "Note1");
		String jsonStr = note.toJson(o);
		Log.v("liny:", "jsonStr = "+jsonStr);
		prefsEditor.putString("testNote", jsonStr);
		prefsEditor.commit();
		
		//get store data
		String saveJson = sharedPref.getString("testNote","");
		Note no = note.fromJson(saveJson, Note.class);
		Log.v("liny:", "note : " + no.getmTitle());
	}
}

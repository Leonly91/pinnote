package com.example.pinnote.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.example.pinnote.Note;
import com.example.pinnote.R;
import com.example.pinnote.comm.NoteType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DBUtil {
	
	public static int keyId = 0;
	
	public static String generalId(){
		keyId++;
		Date date = new Date();
		
		String key = String.valueOf(date.getTime()) + "_" + keyId;
		
		return key;
	}
	
	public static SharedPreferences getPinNoteSharedPref(Context context){
			return context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
	}
	
	public static Note getNote(Context context, String noteId, NoteType type){
		Note note = null;
		String noteListStoreName = "";
		switch(type){
		case TODO:
			noteListStoreName = "todolist";
			break;
		case DOING:
			noteListStoreName = "doinglist";
			break;
		case DONE:
			noteListStoreName = "donelist";
			break;
		default:
			return null;
		}
		
		List<Note> note_data = DBUtil.getNoteList(context, noteListStoreName);
		if (note_data != null){
			for(Note nt : note_data){
				if (nt.getmId().equals(noteId)){
					note = nt;
					break;
				}
			}
		}
		
		return note;
	}
	
	public static boolean addNote(Context context, Note note, NoteType type){
		boolean ret = true;
		try{
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String createTime = sf.format(new Date());
			note.setCreateTime(createTime);
			
			Editor prefsEditor = getPinNoteSharedPref(context).edit();
			Gson gson = new Gson();
			String noteListStoreName = "";
			note.setType(type);
			
			switch(type){
			case TODO:
				note.setmId(generalId());
				noteListStoreName = "todolist";
				break;
			case DOING:
				noteListStoreName = "doinglist";
				break;
			case DONE:
				noteListStoreName = "donelist";
				break;
			default:
				ret = false;
				break;
			}
			
			List<Note> note_data = DBUtil.getNoteList(context, noteListStoreName);
			if (note_data == null){
				note_data = new ArrayList<Note>();
				note_data.add(note);
			}else{
				note_data.add(note);
			}
			
			String jsonStr = gson.toJson(note_data);
			prefsEditor.putString(noteListStoreName, jsonStr);
			prefsEditor.commit();
		}catch(Exception ex){
			Log.e("DBUtil call saveNote failed:", ex.getMessage());
			ret = false;
		}
		return ret;
	}
	
	public static boolean delNote(Context context, String noteId, NoteType type){
		try{
			Editor prefsEditor = getPinNoteSharedPref(context).edit();
			Gson gson = new Gson();
			List<Note> note_data = null;
			String noteListStoreName = "";
			
			switch (type){
			case TODO:
				noteListStoreName = "todolist";
				break;
			case DOING:
				noteListStoreName = "doinglist";
				break;
			case DONE:
				noteListStoreName = "donelist";
				break;
			default:
				break;
			}
			
			note_data = DBUtil.getNoteList(context, noteListStoreName);
			if (note_data != null){
				for (int i = 0; i < note_data.size(); i++){
					if (note_data.get(i).getmId().equals(noteId)){
						note_data.remove(i);
						break;
					}
				}
			}
			
			String jsonStr = gson.toJson(note_data);
			prefsEditor.putString(noteListStoreName, jsonStr);
			prefsEditor.commit();
		}catch(Exception ex){
			Log.e("DBUtil call saveNote failed:", ex.getMessage());
			return false;
		}
		return true;
	}
	
	public static boolean updateNote(Context context, Note note){
		boolean ret = true;
		try{
			Note n;
			Editor prefsEditor = getPinNoteSharedPref(context).edit();
			Gson gson = new Gson();
			List<Note> note_data = null;
			String noteListStoreName = "";
			
			switch (note.getType()){
			case TODO:
				noteListStoreName = "todolist";
				break;
			case DOING:
				noteListStoreName = "doinglist";
				break;
			case DONE:
				noteListStoreName = "donelist";
				break;
			default:
				break;
			}
			
			note_data = DBUtil.getNoteList(context, noteListStoreName);
			if (note_data != null){
				for (int i = 0; i < note_data.size(); i++){
					if (note_data.get(i).getmId().equals(note.getmId())){
						n = note_data.get(i);
						break;
					}
				}
			}
			
			//upate note data
			
			
			String jsonStr = gson.toJson(note_data);
			prefsEditor.putString(noteListStoreName, jsonStr);
			prefsEditor.commit();
		}catch(Exception ex){
			Log.e("DBUtil call saveNote failed:", ex.getMessage());
			return false;
		}
		return ret;
	}
	public static boolean updateNoteType(Context context, Note note, NoteType type){
		boolean ret = true;
		try{
			DBUtil.delNote(context, note.getmId(), note.getType());
			DBUtil.addNote(context, note, type);
		}catch(Exception ex){
			Log.e("DBUtil call updateNote failed:", ex.getMessage());
			ret = false;
		}
		return ret;
	}
	
	public static boolean saveNoteArray(Context context, String key, Note[] note_data){
		boolean ret = true;
		try{
			Editor prefsEditor = getPinNoteSharedPref(context).edit();
			Gson note = new Gson();
			
			String jsonStr = note.toJson(note_data);
			prefsEditor.putString(R.string.store_string + key, jsonStr);
			prefsEditor.commit();
		}catch(Exception ex){
			Log.e("DBUtil call saveList failed:", ex.getMessage());
			ret = false;
		}
		return ret;
	}
	
	public static List<Note> getNoteList(Context context, String key){
		List<Note> noteList = null;
		try{
			Gson note = new Gson();
			String saveJson = getPinNoteSharedPref(context).getString(key,"");
			noteList = note.fromJson(saveJson, new TypeToken<List<Note>>(){}.getType());
			return noteList;
		}catch(Exception ex){
			Log.e("DBUtil call getNoteArray failed:", ex.getMessage());
		}
		return noteList;
	}
	
	public static Note[] getDoneNoteArray(Context context){
		return DBUtil.getNoteArray(context, "donelist");
	}
	
	public static Note[] getDoingNoteArray(Context context){
		return DBUtil.getNoteArray(context, "doinglist");
	}
	
	public static Note[] getTodoNoteArray(Context context){
		return DBUtil.getNoteArray(context, "todolist");
	}
	
	public static Note[] getNoteArray(Context context, String key){
		Note[] ret = null;
		try{
			Gson note = new Gson();
			String saveJson = getPinNoteSharedPref(context).getString(key,"");
			Note note_data[] = note.fromJson(saveJson, Note[].class);
			if (note_data != null){
				Arrays.sort(note_data);
			}
			return note_data;
		}catch(Exception ex){
			Log.e("DBUtil call getNoteArray failed:", ex.getMessage());
		}
		return ret;
	}
	
	public static void TestSaveObject(Context context){
		Editor prefsEditor = getPinNoteSharedPref(context).edit();
		Gson note = new Gson();
		
		Note note_data[] = new Note[]{
    			new Note("Hello"),
    			new Note("Goodbye"),
    			new Note("Huawei"),
    			new Note("How Could you fall in love with him"),
    			new Note("TerryGuy1001@gmail.com"),
    			new Note("Ã÷ÌìÇÀ»ð³µÆ±"),
    			};
		String jsonStr = note.toJson(note_data);
		Log.v("liny:", "jsonStr = "+jsonStr);
		prefsEditor.putString("testNote", jsonStr);
		prefsEditor.commit();
		
		
	}
	
	public static Note[] TestRetrieveObject(Context context){
		//get store data
		Gson note = new Gson();
		String saveJson = getPinNoteSharedPref(context).getString("testNote","");
		Note note_data2[] = note.fromJson(saveJson, Note[].class);
		Log.v("liny:", "note : " + note_data2[0].getmTitle());
		
		return note_data2;
	}
}

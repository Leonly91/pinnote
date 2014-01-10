package com.example.pinnote;

public class Note {
	private int mLevel;
	private int mImageIcon;
	private String mTitle;
	private String mContent;
	private String mId;
	private String mAlarm;
	private String mDeadLine;
	
	public Note(int mImageIcon, String mTitle){
		this.mTitle = mTitle;
		this.mImageIcon = mImageIcon;
	}
	
	public Note(String mTitle){
		this.mTitle = mTitle;
	}
	
	public Note(int mLevel, String mTitle, String mContent, String mId,
			String mAlarm, String mDeadLine, int mImageIcon) {
		super();
		this.mLevel = mLevel;
		this.mTitle = mTitle;
		this.mContent = mContent;
		this.mId = mId;
		this.mAlarm = mAlarm;
		this.mDeadLine = mDeadLine;
		this.mImageIcon = mImageIcon;
	}
	
	public int getmLevel() {
		return mLevel;
	}
	public void setmLevel(int mLevel) {
		this.mLevel = mLevel;
	}
	public String getmTitle() {
		return mTitle;
	}
	public void setmTitle(String mTitle) {
		this.mTitle = mTitle;
	}
	public String getmContent() {
		return mContent;
	}
	public void setmContent(String mContent) {
		this.mContent = mContent;
	}
	public String getmId() {
		return mId;
	}
	public void setmId(String mId) {
		this.mId = mId;
	}

	public String getmAlarm() {
		return mAlarm;
	}
	public void setmAlarm(String mAlarm) {
		this.mAlarm = mAlarm;
	}
	public String getmDeadLine() {
		return mDeadLine;
	}
	public void setmDeadLine(String mDeadLine) {
		this.mDeadLine = mDeadLine;
	}

	public int getmImageIcon() {
		return mImageIcon;
	}

	public void setmImageIcon(int mImageIcon) {
		this.mImageIcon = mImageIcon;
	}
	
	
}
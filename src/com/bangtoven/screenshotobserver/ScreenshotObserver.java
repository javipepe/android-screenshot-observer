package com.bangtoven.screenshotobserver;

import java.io.File;

import android.net.Uri;
import android.os.Environment;
import android.os.FileObserver;
import android.util.Log;

public class ScreenshotObserver extends FileObserver {
	private static final String TAG = "ScreenshotObserver";
	private static final boolean ALL_PHOTOS = false;
	private static final String PATH = Environment.getExternalStorageDirectory().toString() + "/Pictures/" + (ALL_PHOTOS ? "" : "Screenshots/");
	
	private OnScreenshotTakenListener mListener;
	private String mLastTakenPath;
	
	public ScreenshotObserver(OnScreenshotTakenListener listener) {
		super(PATH, FileObserver.CLOSE_WRITE);
		mListener = listener;
	}

	@Override
	public void onEvent(int event, String path) {
		Log.i(TAG, "Event:"+event+"\t"+path);
		
		if (path==null || event!=FileObserver.CLOSE_WRITE)
			//don't care
		else if (mLastTakenPath!=null && path.equalsIgnoreCase(mLastTakenPath))
			//event observed before
		else {
			mLastTakenPath = path;
			File file = new File(PATH+path);
			mListener.onScreenshotTaken(Uri.fromFile(file));
			//send instance of event to listener
		}
	}
	
	public void start() {
		super.startWatching();
	}
	
	public void stop() {
		super.stopWatching();
	}
	

	
//	private Uri getLastScreenshotUri() {
//		File[] files = mScreenshotFolder.listFiles(new FileFilter() {			
//			public boolean accept(File file) {
//				return file.isFile();
//			}
//		});
//		long modifiedTime = Long.MIN_VALUE;
//		File lastScreenshot = null;
//		for (File file : files) {
//			if (file.lastModified() > modifiedTime) {
//				lastScreenshot = file;
//				modifiedTime = file.lastModified();
//			}
//		}
//		
//		return Uri.fromFile(lastScreenshot);
//	}
}

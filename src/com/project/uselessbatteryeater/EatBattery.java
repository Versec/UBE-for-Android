package com.project.uselessbatteryeater;

import java.io.FileOutputStream;
import java.io.IOException;
import android.content.Context;
import android.os.AsyncTask;
import android.text.format.Time;
import android.util.Log;

public class EatBattery extends AsyncTask <Void, Void, Void>{
	
	private static final String TAG = MainActivity.class.getName();
	
	String FILENAME;
	String batteryLevel;
	//FileOutputStream fos = MainActivity.this.openFileOutput(FILENAME, Context.MODE_PRIVATE);
	Context mcontext;
	
	Time time = new Time();
	
	public EatBattery(Context filecontext){
	    this.mcontext = filecontext;
	}

	
	protected void onPreExecute(Void thing){
	
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		Log.i(TAG, "asynkTask EatBattery has started");
		time.setToNow();
   	 	FILENAME = "UBE_" + time.format3339(false)+ ".txt";        
        try {
        	FileOutputStream fos = mcontext.openFileOutput(FILENAME, Context.MODE_PRIVATE);
        	fos.write(("Starting test at " + time.format3339(false)).getBytes());
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	
		return null;
	}
	
	protected void onPostExecute(Void result) {
		
	}
	

}

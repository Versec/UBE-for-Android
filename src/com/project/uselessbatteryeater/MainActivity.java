package com.project.uselessbatteryeater;

import java.io.FileOutputStream;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.format.Time;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	private Button mainButton;
	
	String filename;
	String batteryLevel;
	FileOutputStream outputStream;
	Context context;
	Time time = new Time();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mainButton = (Button) findViewById(R.id.mainButton);
		 
		mainButton.setOnClickListener(new OnClickListener() {
			
			  @Override
			  public void onClick(View arg0) {
			     Toast.makeText(getApplicationContext(), "Button is clicked", Toast.LENGTH_LONG).show();
			     
			     try {
			    	 time.setToNow();
			    	 filename = "UBE_" + time.format3339(false);
			    	 
			    	  outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
			    	  outputStream.write(("Starting test at " + time.format3339(false)).getBytes());
			    	  
			    	 batteryLevel();
			    	  outputStream.close();
			    	} catch (Exception e) {
			    	  e.printStackTrace();
			    	}
			     
			  }
			  
			  
			  private void batteryLevel() {
				  BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver() {
				  public void onReceive(Context context, Intent intent) {
				  context.unregisterReceiver(this);
				  int rawlevel = intent.getIntExtra("level", -1);
				  int scale = intent.getIntExtra("scale", -1);
				  int level = -1;
				  if (rawlevel >= 0 && scale > 0) {
				  level = (rawlevel * 100) / scale;
				  }
				  batteryLevel = "Battery Level Remaining: " + level + "%";
				  System.out.println(batteryLevel);
				  }
				  };
				  IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
				  registerReceiver(batteryLevelReceiver, batteryLevelFilter);
				  }
			  
			  
			  
		});
	}
		
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

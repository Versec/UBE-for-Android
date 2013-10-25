package com.project.uselessbatteryeater;

import java.io.FileOutputStream;

import android.os.BatteryManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	private Button mainButton;
	public static final String SETTINGS_FILE = "Settings";
	SharedPreferences settings;
	
	String filename;
	String batteryLevel;
	public FileOutputStream outputStream;
	Context context;
	Time time = new Time();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		mainButton = (Button) findViewById(R.id.mainButton);
		setup();
		mainButton.setOnClickListener(new OnClickListener() {
		
			  @Override
			  public void onClick(View arg0) {
			     Toast.makeText(getApplicationContext(), "Button is clicked", Toast.LENGTH_LONG).show();
			     
			     new EatBattery(getApplicationContext()).execute();			     
			  }
			  
	
			  /**
			   * Checks the battery level of the phone
			   */
	private void batteryLevel(boolean getTime) {
		BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver() {
			public void onReceive(Context context, Intent intent) {
				context.unregisterReceiver(this);
				int rawlevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
				int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
				int level = -1;
				if (rawlevel >= 0 && scale > 0) {
					level = (rawlevel * 100) / scale;
				}
				batteryLevel = time.format3339(false).getBytes() + " | Current battery level" + level + "%"; } };
				IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
				registerReceiver(batteryLevelReceiver, batteryLevelFilter);
	}});
	}
	
	/**
	 * Initial setup if a Settings file is not found.
	 * If a SharedPreferences file called "Settings" does not exist, the method will create one with some default values.
	 */
	public void setup (){
		settings = getSharedPreferences(SETTINGS_FILE, 0);
		if(settings.contains("use_All") == true){
			Log.d("SETUP", "settings found");
		}
		
		else {
			
			Editor editor = settings.edit();
			editor.putBoolean("USE_All", true);
			editor.putBoolean("USE_WIFI", true);
			editor.putInt("MODE_WIFI", 1);
			editor.putBoolean("USE_BLUE", true);
			editor.commit();
		}
	}
		
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
        case R.id.action_settings:
        	startActivity(new Intent(this, Settings.class));
        	return true;
        default:
        return super.onOptionsItemSelected(item);
        }
	}

}

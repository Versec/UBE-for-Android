package com.project.uselessbatteryeater;

import java.util.Map;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.Toast;
import android.provider.Settings.System;
import android.provider.Settings.SettingNotFoundException;

public class Settings extends Activity {

	public static final String SETTINGS_FILE = "Settings";
	
	public Spinner wifi_Spinner;
	public Spinner cpu_Spinner;
	
	public CheckBox wifi_CheckBox;
	public CheckBox bluetooth_CheckBox;
	public CheckBox radio_CheckBox;
	public SeekBar brightness_SeekBar;
	public CheckBox cpu_CheckBox;
	int brightness;
	ContentResolver resolver;
	private Window window;
	
	OnClickListener checkBoxListener;
	Map <String, ?> settingsList;
	
	SharedPreferences settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		settings = getSharedPreferences(SETTINGS_FILE, 0);
		final Editor editor = settings.edit();
		
		wifi_CheckBox = (CheckBox) findViewById(R.id.checkBox_Wifi);
		wifi_Spinner = (Spinner) findViewById(R.id.spinner_Wifi);
		bluetooth_CheckBox = (CheckBox) findViewById(R.id.checkBox_Bluetooth);
		radio_CheckBox = (CheckBox) findViewById(R.id.checkBox_Radio);
		brightness_SeekBar = (SeekBar) findViewById(R.id.seekBar_Brigthness);
		
		cpu_CheckBox = (CheckBox) findViewById(R.id.checkBox_CPU);
		cpu_Spinner = (Spinner) findViewById(R.id.spinner_CPU);
		
		resolver = getContentResolver();
		window = getWindow();
		brightness_SeekBar.setMax(255);
		brightness_SeekBar.setKeyProgressIncrement(1);
		
		 try
	        {
	            //Get the current system brightness
	            brightness = System.getInt(resolver, System.SCREEN_BRIGHTNESS);
	        }
	        catch (SettingNotFoundException SNT){
	        }
		 
		 brightness_SeekBar.setProgress(brightness);
		 
		 
		 brightness_SeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
			 public void onStopTrackingTouch(SeekBar seekBar){
		 		System.putInt(resolver, System.SCREEN_BRIGHTNESS, brightness);
                android.view.WindowManager.LayoutParams layoutpars = window.getAttributes();
                layoutpars.screenBrightness = brightness / (float)255;
                window.setAttributes(layoutpars);
                editor.putInt("LEVEL_BRIGHTNESS", brightness);
                editor.commit();
			}
			 
			 public void onStartTrackingTouch(SeekBar seekBar) {
				 //Do nothing
			 }
			 public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
	                    brightness = progress;
	            }

	        });
	 
		
		checkBoxListener =new OnClickListener() {
			 
			 public void onClick(View v) {
			 if (wifi_CheckBox.isChecked() == true) {
				 editor.putBoolean("USE_WIFI", true);
				 wifi_Spinner.setEnabled(true);
				 editor.commit();
			 }
			 else {
				 editor.putBoolean("USE_WIFI", false);
				 wifi_Spinner.setEnabled(false);
				 editor.commit();
			 }
			 
			 if(bluetooth_CheckBox.isChecked()== true){
				 editor.putBoolean("USE_BLUE", true);
				 editor.commit();
			 }
			 else {
				 editor.putBoolean("USE_BLUE", false);
			 }
			 
			 if(radio_CheckBox.isChecked()== true){
				 editor.putBoolean("USE_RADIO", true);
				 editor.commit();
			 }
			 else {
				 editor.putBoolean("USE_RADIO", false);
			 }
			 
			 if(cpu_CheckBox.isChecked()==true){
				 editor.putBoolean("USE_CPU", true);
				 cpu_Spinner.setEnabled(true);
				 editor.commit();
			 }
			 else {
				 editor.putBoolean("USE_CPU", false);
				 cpu_Spinner.setEnabled(false);
				 editor.commit();
			 }
			 
			 
			 
			 
			 
			 }};
		
		wifi_CheckBox.setOnClickListener(checkBoxListener);
		bluetooth_CheckBox.setOnClickListener(checkBoxListener);
		radio_CheckBox.setOnClickListener(checkBoxListener);
		cpu_CheckBox.setOnClickListener(checkBoxListener);
		
		
		
		
		ArrayAdapter wifiAdapter = ArrayAdapter.createFromResource(this, R.array.wifi_array, android.R.layout.simple_spinner_item);
		wifiAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
		wifi_Spinner.setAdapter(wifiAdapter);
	
		wifi_Spinner.setOnItemSelectedListener((new OnItemSelectedListener(){
			
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				int index = arg0.getSelectedItemPosition();
				editor.putInt("MODE_WIFI", index);
				editor.commit();
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				//Do nothing.
			}}));
		
		
		ArrayAdapter cpuAdapter = ArrayAdapter.createFromResource(this, R.array.CPU_array, android.R.layout.simple_spinner_item);
		cpuAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
		cpu_Spinner.setAdapter(cpuAdapter);
	
		cpu_Spinner.setOnItemSelectedListener((new OnItemSelectedListener(){
			
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				int index = arg0.getSelectedItemPosition();
				editor.putInt("MODE_CPU", index);
				editor.commit();
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				//Do nothing.
			}}));
	}
	@Override
	protected void onResume(){
		super.onResume();
		settings = getSharedPreferences(SETTINGS_FILE, 0);
		wifi_CheckBox.setChecked(settings.getBoolean("USE_WIFI", false));
		wifi_Spinner.setEnabled(settings.getBoolean("USE_WIFI", false));
		wifi_Spinner.setSelection(settings.getInt("MODE_WIFI", -1));
		
		bluetooth_CheckBox.setChecked(settings.getBoolean("USE_BLUE", false));
		radio_CheckBox.setChecked(settings.getBoolean("USE_RADIO", false));
		cpu_CheckBox.setChecked(settings.getBoolean("USE_CPU", false));
		cpu_Spinner.setEnabled(settings.getBoolean("USE_CPU", false));
		cpu_Spinner.setSelection(settings.getInt("MODE_CPU", -1));
		
	}
}

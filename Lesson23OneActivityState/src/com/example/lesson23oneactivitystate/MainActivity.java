package com.example.lesson23oneactivitystate;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	final String TAG = "States";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		Log.d (TAG,"MAinActivity: onCreate()");		
	}
	
	public void onStart() {
		super.onStart();
		Log.d (TAG,"MAinActivity: onStart()");
		
	}
	
	public void onResume() {
		super.onResume();
		Log.d (TAG,"MainActivity: onResume()"); 
	}
	
	public void onPause() {
		super.onPause();
		Log.d (TAG,"MainActivity: onPause()");
	}
	
	public void onStop() {
		super.onStop();
		Log.d (TAG,"MainActivity: onStop()");
	}
	
	public void onDestroy() {
		super.onDestroy();
		Log.d (TAG,"MainActivity: onDestroy()");
	}
		
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

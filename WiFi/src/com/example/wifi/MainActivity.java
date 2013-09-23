package com.example.wifi;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiInfo;


public class MainActivity extends Activity implements OnClickListener {

	TextView WifiState;
	Button WifiOn,WifiOff;
	
	int extraWifiState ;
	int newRssi;

	  /** Called when the activity is first created. */
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.activity_main);
	      WifiOn = (Button)findViewById(R.id.wifi_on);
	      WifiOff = (Button)findViewById(R.id.wifi_off);
	      WifiState = (TextView)findViewById(R.id.wifiState);	    
	      this.registerReceiver(this.WifiStateChangedReceiver,
	              new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));
	      WifiOn.setOnClickListener(this);
	      WifiOff.setOnClickListener(this);;
	  }

	  @Override  
		public void onClick(View v) {
			Log.d("MY TAG ","SWITCH on ACTION");		
			Log.d("MY TAG ", "State = " + extraWifiState);
			
			 WifiManager wifiManager = (WifiManager)getBaseContext().getSystemService(Context.WIFI_SERVICE); 
						
			    
			switch (v.getId()) {
			case R.id.wifi_on:
			WifiState.setText("WiFi ON");
			 wifiManager.setWifiEnabled(true);
			 Log.d("MY TAG ", "After Enable State = " + extraWifiState);
			//Toast.makeText(getApplicationContext(), "PRESSED OK", Toast.LENGTH_LONG).show();
			break;					
			
			case R.id.wifi_off:
				WifiState.setText("WiFi OFF");
				wifiManager.setWifiEnabled(false);	
				Log.d("MY TAG ", "After Disable State = " + extraWifiState);
			break;	
			}
		    
			
		}
		
	  
	  private BroadcastReceiver WifiStateChangedReceiver
	  = new BroadcastReceiver(){
		  

	@Override
	 public void onReceive(Context context, Intent intent) {
	  // TODO Auto-generated method stub
	 
		//WifiManager myWifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
		 // WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();

	  //WifiState.setText(myWifiInfo.getSSID());

		
	 extraWifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE ,
	    WifiManager.WIFI_STATE_UNKNOWN);
	 
	 newRssi = intent.getIntExtra(WifiManager.EXTRA_NEW_RSSI, 0);
	 
	  switch(extraWifiState){
	  case WifiManager.WIFI_STATE_DISABLED:
	   WifiState.setText("WIFI STATE DISABLED");
	   break;
	  case WifiManager.WIFI_STATE_DISABLING:
	   WifiState.setText("WIFI STATE DISABLING");
	   break;
	  case WifiManager.WIFI_STATE_ENABLED:
	   WifiState.setText("WIFI STATE ENABLED");
	   break;
	  case WifiManager.WIFI_STATE_ENABLING:
	   WifiState.setText("WIFI STATE ENABLING");
	   break;
	  case WifiManager.WIFI_STATE_UNKNOWN:
	   WifiState.setText("WIFI STATE UNKNOWN");
	   break;
	  }
	 
	 }
	 
	  };
	  
		
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

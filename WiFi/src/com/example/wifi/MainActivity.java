package com.example.wifi;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener {	

	TextView WifiState,WifiInfo;
	Button WifiOn,WifiOff,WifiParam;
	TextView rowtablename,rowssid_1,rowssid_2,rowssid_3;
	List<ScanResult> results;
	ListView lvAP;
	WifiManager wifiManager;
	int extraWifiState ;
	boolean scanState ;
	String scanState_string;
	int scanState_integer;
	List<ScanResult> results_new_intent;
	CustomScanListAdapter adapterlist;	
		
	final int DIALOG_EXIT = 1;	
	
	ArrayList<ScanItem> scanDetails;
	//final ArrayList<String> apnames = new ArrayList<String>() ;	//OLD CODE
	Intent intent_1 = new Intent("my.action.bat.SCHEDULE_ACT");
		
	IntentFilter wifiStateIntent;
	IntentFilter wifiScanAvailIntent;
	
	Timer timerScanUpdate;
	TimerTask tasktimer;	
	
	CustomDialogWindow cdd;
	MyProgressDialog waiting_icon, temp_waiting_icon;
		 
	  /** Called when the activity is first created. */
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.activity_main);	      
	      WifiOn = (Button)findViewById(R.id.wifi_on);
	      WifiOff = (Button)findViewById(R.id.wifi_off);
	      WifiParam = (Button)findViewById(R.id.wifiParam);
	      WifiState = (TextView)findViewById(R.id.wifiState);
	      WifiInfo = (TextView)findViewById(R.id.wifiInfo);
	      lvAP = (ListView)findViewById(R.id.listAP);
	      scanDetails = new ArrayList<ScanItem>();
      
	      /**Create timer*/
	    //  timerScanUpdate = new Timer();
	      
	      /** Intents for Broadcast receivers */
	  	  wifiStateIntent  = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
	  	  wifiScanAvailIntent =  new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
	  	  /** Register Receivers */
	      this.registerReceiver(this.WifiStateChangedReceiver,wifiStateIntent);	     
	      this.registerReceiver(this.WifiScanResultReceiver, wifiScanAvailIntent);
	      waiting_icon = new MyProgressDialog(this);
	      temp_waiting_icon = new MyProgressDialog(this);
		  
	      /**Debug Buttons */
	      WifiOn.setOnClickListener(this);
	      WifiOff.setOnClickListener(this);
	      WifiParam.setOnClickListener(this);
	     
	      Log.d("MY ON_CREATE ", "Call onCreate " );
	      wifiManager = (WifiManager)getBaseContext().getSystemService(Context.WIFI_SERVICE);	    
	   
	      if (wifiManager.getWifiState() ==  wifiManager.WIFI_STATE_DISABLED)
	    	  Log.d("MY ON_CREATE ", "WIFI_STATE_DISABLED" );
	     
	      /* Create dialog window for WIFI state */
	      cdd = new CustomDialogWindow(MainActivity.this,this);	      
	      if (wifiManager.getWifiState() ==  wifiManager.WIFI_STATE_DISABLED)
    	  {    	     
    	     cdd.setCancelable(false);
//    	     cdd.show();  
    	  }
	      
	      Log.d("MY ON_CREATE ", "BACK TO MAIN_ACTIVITY");	    
	      Log.d("MY ON_CREATE STATE = ", Integer.toString(wifiManager.getWifiState() )); 	     
      	      
		  results = wifiManager.getScanResults();//Get result for first run
  		  
		 if (wifiManager.getWifiState() ==  wifiManager.WIFI_STATE_ENABLED)
		 {			 		 
			  adapterlist = new CustomScanListAdapter(this,results); 	 
		      //lvAP.setAdapter(new CustomScanListAdapter(this,results));
			  lvAP.setAdapter(adapterlist);
		      Log.d("MY TAG ", "WIFI STATE:" + Integer.toString(wifiManager.getWifiState()));
		 }	 		 
			  //Switch to old version
	      //final ListView lvAP = (ListView) findViewById(R.id.listAP);	     
	      //lvAP.setAdapter(new CustomListAdapter(this, scanDetails));		 
	  }

	  @Override  
	public void onClick(View v) {
		   
		Log.d("MY TAG ","SWITCH on ACTION");		
		Log.d("MY TAG ", "State = " + extraWifiState);

		//Intent intent_1 = new Intent(this, ActivityTwo.class);			
		//WifiManager wifiManager = (WifiManager)getBaseContext().getSystemService(Context.WIFI_SERVICE);
		//wifiManager = (WifiManager)getBaseContext().getSystemService(Context.WIFI_SERVICE);
		
		WifiInfo myWifiInfo = wifiManager.getConnectionInfo();
	  						    
		switch (v.getId()) {
		case R.id.wifi_on:
			WifiState.setText("WiFi ON");
			wifiManager.setWifiEnabled(true);				
			Log.d("MY TAG ", "After Enable State = " + extraWifiState);		
		break;					
		case R.id.wifi_off:			
			WifiState.setText("WiFi OFF");
			wifiManager.setWifiEnabled(false);	
			
		break;
		case R.id.wifiParam:
			Log.d("MY TAG ", "After PUSH wifiParam");			
			int ip = myWifiInfo.getIpAddress();		
			Log.d("MY TAG ", "After PUSH wifiParam ip = " + Integer.toString(ip));
			  String ipString = String.format("%d.%d.%d.%d",
					   (ip & 0xff),
					   (ip >> 8 & 0xff),
					   (ip >> 16 & 0xff),
					   (ip >> 24 & 0xff));
			
			WifiInfo.setText("BSSID:"     + myWifiInfo.getSSID() + "\n"
							+ "RSSI:"     + myWifiInfo.getRssi() + "\n"
						//	+ "MAC: "     + myWifiInfo.getMacAddress() + "\n"
							+ "LINK SPEED: "     + myWifiInfo.getLinkSpeed() + "\n"
							+ "SUPPLICANT:" + myWifiInfo.getSupplicantState() + "\n"
							+ "IP ADDRESS:" + ipString
								);
	      
	        // loop that goes through list
	        for (ScanResult result : results) {        	
	        	
	        	ScanItem scan_element = new ScanItem();	        	   	 
	         //   Toast.makeText(this, result.SSID + " " + result.level + " " + result.frequency + " MHz",
	           //         Toast.LENGTH_SHORT).show();
	        	 Log.d("MY TAG ", "***********************");
	            Log.d("MY TAG ", result.SSID + " " + result.level + " " + result.frequency + " MHz");	
	            scan_element.setSSID(result.SSID);
	            scan_element.setChannelFreq(result.frequency);
	            scan_element.setRSSIlevel(result.level);
	            scan_element.setBSSID(result.BSSID);
	            scan_element.setCipherType(result.capabilities);		            
	            
	            if (result.BSSID.matches(myWifiInfo.getBSSID()))
	            {
	            	scan_element.setConnectFlag(true);
	            	Log.d("MY TAG ", "CONNECTED SSID = " + myWifiInfo.getSSID());
	            	Log.d("MY TAG ", "CONNECTED BSSID = " + myWifiInfo.getBSSID());
	            }
	            Log.d("MY TAG ", "flag = " + scan_element.getConnectFlag());
	            Log.d("MY TAG ", "CAPABILITIES = " + scan_element.getCipherType());
	          	    		            		      	            
	            scanDetails.add(scan_element);		                     
	        }		        
			
	        break;			
		}		    
		
	};
			  	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public boolean  onOptionsItemSelected(MenuItem item) {
		    // TODO Auto-generated method stub
		    switch (item.getItemId()) {		    
		    case R.id.switch_screen:
		    	Log.d("MY TAG ", "Choose item = ");
		    	Intent intent = new Intent(this, ActivityTwo.class);
		    	startActivity(intent);	    
		      break;	
		    }
		    return super.onOptionsItemSelected(item);
	  }
 	  
	  private BroadcastReceiver WifiStateChangedReceiver
	  = new BroadcastReceiver(){		  
	  public void onReceive(Context context, Intent intent) {
		  // TODO Auto-generated method stub
	
		 extraWifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE ,
		    WifiManager.WIFI_STATE_UNKNOWN);	
		 
		  switch(extraWifiState){
		  case WifiManager.WIFI_STATE_DISABLED:
		   WifiState.setText("WIFI STATE DISABLED");
		   break;
		  case WifiManager.WIFI_STATE_DISABLING:
		   WifiState.setText("WIFI STATE DISABLING");
		   break;
		  case WifiManager.WIFI_STATE_ENABLED:
		   WifiState.setText("WIFI STATE ENABLED");
		   Log.d("MY BroadcastReceiver !!!  ", "WIFI STATE ENABLED" );  
		   //Toast.makeText(getApplicationContext(), "WIFI_STATE_ENABLED", Toast.LENGTH_LONG).show(); 
		   break;
		  case WifiManager.WIFI_STATE_ENABLING:
		   WifiState.setText("WIFI STATE ENABLING");
		   /** Enable waiting icon */
		   if(cdd.status_icon == true)
			temp_waiting_icon = waiting_icon.show(context,"Please Wait ...",true,false);	
		   break;
		  case WifiManager.WIFI_STATE_UNKNOWN:
		   WifiState.setText("WIFI STATE UNKNOWN");
		   break;	   
		  }   

		 }	 	  
		  };
		  
		  protected void onStart() {
			  this.registerReceiver(this.WifiStateChangedReceiver,wifiStateIntent);
		      this.registerReceiver(this.WifiScanResultReceiver,wifiScanAvailIntent);
		      /** Run timer*/
		      timerMethod();//Need investigate: no need to run timer when
			  super.onStart();// WIFI state is DISABLED !!!!!!!!!!
	
			//  Log.d("MY ON_STATE ", "onStart Timer Stop ActivityTwo.activity = " + ActivityTwo.activity );
			  
			  Log.d("MY 1 ON_STATE ", "onStart" );
		  }
		  protected void onPause() {
			//  unregisterReceiver(WifiScanResultReceiver);
			 // unregisterReceiver(WifiStateChangedReceiver);
			  Log.d("MY 1 ON_STATE ", "onPause" );			  
			  //timerScanUpdate.cancel();//Timer stop
		      super.onPause();
		    }
		  @Override
		  protected void onResume() {
			  super.onResume();
			  //timerMethod();
			  Log.d("MY 1 ON_STATE ", "onResume" );
		  }
		  protected void onStop()
		  {
		      unregisterReceiver(WifiScanResultReceiver);
		      unregisterReceiver(WifiStateChangedReceiver);
		      Log.d("MY 1 ON_STATE ", "onStop" );		    
		      if(timerScanUpdate != null)
		    	  timerScanUpdate.cancel();//Timer stop	
		    //  if(ActivityTwo.activity == false)
		    	//  timerScanUpdate.cancel();
		      super.onStop();
		  }
		  protected void onDestroy()
		  {
		      Log.d("MY 1 ON_STATE ", "onDestroy" );
		      //timerScanUpdate.cancel();//Timer stop
		      super.onDestroy();
		  }
		  
	  //private BroadcastReceiver WifiScanResultReceiver
	  public BroadcastReceiver WifiScanResultReceiver
	  = new BroadcastReceiver(){
		  public void onReceive(Context context, Intent intent) {
			 // Log.d("MY WifiScanResultReceiver !!!  ", "INSIDE" );				  
			  //Toast.makeText(getApplicationContext(), "MY WifiScanResultReceiver INSIDE !!!", Toast.LENGTH_LONG).show();		  			   
				   
		  results_new_intent = wifiManager.getScanResults();
			  
			//  Log.d("MY WifiScanResultReceiver !!!  ", results_new_intent.get(1).SSID + ": " +Integer.toString(results_new_intent.get(1).level));			  			  				  
			  //if(results_new_intent != null)
				  //Toast.makeText(getApplicationContext(), "WifiScanResultReceiver results_new_intent = " + results_new_intent, Toast.LENGTH_LONG).show();
			  			  
			  if(results_new_intent == null) {
				  //Toast.makeText(getApplicationContext(), "WifiScanResultReceiver results_new_intent == NULL", Toast.LENGTH_LONG).show();
			  } else {
				  /** Disable waiting icon */
				  if(cdd.status_icon == true)
				  {					  
					  temp_waiting_icon.cancel();
					  cdd.status_icon = false;				
				  }
				  //Toast.makeText(getApplicationContext(), "WifiScanResultReceiver results_new_intent = " + results_new_intent, Toast.LENGTH_LONG).show();
				 // if(lvAP != null)
					//  Toast.makeText(getApplicationContext(), "WifiScanResultReceiver lvAP EXIST", Toast.LENGTH_LONG).show();
			
			     if (adapterlist == null)
			     {
			    	 adapterlist = new CustomScanListAdapter(getApplicationContext(),results_new_intent);	   	 
			    	 lvAP.setAdapter(adapterlist);
			     }
			     	//Refresh adapter list
			     	adapterlist.setData(results_new_intent);
			     	adapterlist.notifyDataSetChanged();	
			     	
			  	 }			 
		}	 	 		  
	  };
	  void timerMethod()
	  {
		  timerScanUpdate = new Timer();
		  tasktimer = new TimerTask() {
			  public void run() {
				 // Log.d("MY Timer", "run code in Timer");
				  wifiManager.startScan();
			  }
  		  };
  		  timerScanUpdate.schedule(tasktimer, 5000, 5000);
	  }		  
}

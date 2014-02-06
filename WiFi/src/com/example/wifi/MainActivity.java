package com.example.wifi;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
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
	
	int flag = 0;	
	final int DIALOG_EXIT = 1;
	ListView test;
	
	ArrayList<ScanItem> scan_details = new ArrayList<ScanItem>();
	// final ListView lvAP = (ListView) findViewById(R.id.listAP);
	final ArrayList<String> apnames = new ArrayList<String>() ;	
	Intent intent_1 = new Intent("my.action.bat.SCHEDULE_ACT");
		
	Timer timer_scan_update = new Timer();
	
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
	      rowtablename = (TextView)findViewById(R.id.textTableName);	    
	      rowssid_1 = (TextView)findViewById(R.id.textView31);
	      rowssid_2 = (TextView)findViewById(R.id.textView41);
	      rowssid_3 = (TextView)findViewById(R.id.textView47);
	      //final ListView lvAP = (ListView)findViewById(R.id.listAP);	 
	      lvAP = (ListView)findViewById(R.id.listAP);	 
	//      this.registerReceiver(this.WifiStateChangedReceiver,
	  //            new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));
	      
	      //MY TEST !!!
	    //  this.registerReceiver(this.WifiScanResultReceiver,
	      //        new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
	      
	      WifiOn.setOnClickListener(this);
	      WifiOff.setOnClickListener(this);
	      WifiParam.setOnClickListener(this);      
	      
	      Log.d("MY ON_CREATE ", "Call onCreate " );
	      wifiManager = (WifiManager)getBaseContext().getSystemService(Context.WIFI_SERVICE);	    
	   
	      if (wifiManager.getWifiState() ==  wifiManager.WIFI_STATE_DISABLED)
	    	  Log.d("MY ON_CREATE ", "WIFI_STATE_DISABLED" );
	      
	      if (wifiManager.getWifiState() ==  wifiManager.WIFI_STATE_DISABLED)
    	  {
    	     CustomDialogWindow cdd = new CustomDialogWindow(MainActivity.this,this);
    	     unregisterReceiver(WifiStateChangedReceiver);
    	     unregisterReceiver(WifiScanResultReceiver);    	     
    	     cdd.show();  
    	  }
	      Log.d("MY ON_CREATE ", "BACK TO MAIN_ACTIVITY");

	      //try {
	    	  //rowssid_1.setText("MAIN");  
	    //  } 
	     // catch (Exception e) {
	    	//  Log.e("ERROR", "ERROR IN CODE" + e.toString());
	    	 // e.printStackTrace();
	      //}	    
	      Log.d("MY ON_CREATE STATE = ", Integer.toString(wifiManager.getWifiState() ));     
 
	      
	      if (wifiManager.getWifiState() ==  wifiManager.WIFI_STATE_ENABLED)
    	  {	 
	    	  Log.d("MY ON_CREATE results !!!  ", "WIFI_STATE_ENABLED");
	    	
	    	// for (ScanResult result : results) {
		      // 	Log.d("MY ON_CREATE results !!!  ", result.SSID);     	
		       //}  	 
        
    	  } 
	        // get list of the results in object format ( like an array )
		
	     // Log.d("MY ON_CREATE ", "BEFORE RESULT ");
	      Toast.makeText(getApplicationContext(), "IN CREATE", Toast.LENGTH_LONG).show();	    
	      
		  results = wifiManager.getScanResults();	
		  
		  //Run timer for scanning
		  timerMethod();
		  
		 if (wifiManager.getWifiState() ==  wifiManager.WIFI_STATE_ENABLED)
		 {	     
		  adapterlist = new CustomScanListAdapter(this,results); 	 
	      //lvAP.setAdapter(new CustomScanListAdapter(this,results));
		  lvAP.setAdapter(adapterlist);
	      Log.d("MY TAG ", "WIFI STATE:" + Integer.toString(wifiManager.getWifiState()));
		 }	 		 
			  //Switch to old version
	      //final ListView lvAP = (ListView) findViewById(R.id.listAP);	     
	      //lvAP.setAdapter(new CustomListAdapter(this, scan_details));		 
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
			Log.d("MY TAG ", "After PUSH wifiParam = ");
			
			WifiInfo.setText("BSSID:"     + myWifiInfo.getSSID() + "\n"
							+ "RSSI:"     + myWifiInfo.getRssi() + "\n"
						//	+ "MAC: "     + myWifiInfo.getMacAddress() + "\n"
							+ "LINK SPEED: "     + myWifiInfo.getLinkSpeed() + "\n"
							+ "SUPPLICANT:" + myWifiInfo.getSupplicantState()
								);
			
		//	wifiManager.startScan(); 
	        // get list of the results in object format ( like an array )
		
			//  results = wifiManager.getScanResults();
			  Log.d("MY TAG ", "Point 1");   
	         //results = wifiManager.getScanResults();      
	         //lvAP.setAdapter(new CustomScanListAdapter(this,results));
	      
	        // loop that goes through list
	        for (ScanResult result : results) {
	        	Log.d("MY TAG ", "Point 2"); 
	        	
	        	ScanItem scan_element = new ScanItem();
	        	   	 
	         //   Toast.makeText(this, result.SSID + " " + result.level + " " + result.frequency + " MHz",
	           //         Toast.LENGTH_SHORT).show();
	        	 Log.d("MY TAG ", "***********************");
	            Log.d("MY TAG ", result.SSID + " " + result.level + " " + result.frequency + " MHz");	
	            scan_element.setSsid(result.SSID);
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
	          	    		            		      	            
	            scan_details.add(scan_element);		                     
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
		    	intent_1.putStringArrayListExtra("AP_NAMES", apnames);
		    	
			    startActivity(intent);
			    startActivity(intent_1);		     
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
		   Toast.makeText(getApplicationContext(), "WIFI_STATE_ENABLED", Toast.LENGTH_LONG).show();		  
          
		   if (wifiManager.getScanResults() == null)
			   Toast.makeText(getApplicationContext(), "getScanResults == NULL", Toast.LENGTH_LONG).show();

		   List<ScanResult> result_TEST;
		   result_TEST = wifiManager.getScanResults();  
		   if (result_TEST != null)
		   { 				 		   
			   // 	Log.d("MY MAIN ACTIVITY BroadcastReceiver :", "WIFI_STATE_ENABLED");
			   for (ScanResult result : result_TEST) {
				   //Log.d("MY BroadcastReceiver !!!  ", result.SSID);     	
		       }
		   } 
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
		  
		  protected void onStart() {
			  this.registerReceiver(this.WifiStateChangedReceiver,
		              new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));
		      this.registerReceiver(this.WifiScanResultReceiver,
		              new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
			  super.onStart();
			  Log.d("MY ON_CREATE ", "onStart" );
		  }
		  protected void onPause() {
			//  unregisterReceiver(WifiScanResultReceiver);
			 // unregisterReceiver(WifiStateChangedReceiver);
			  Log.d("MY ON_CREATE ", "onPause" );			  
			  timer_scan_update.cancel();//Timer stop
		      super.onPause();
		    }
		  @Override
		  protected void onResume() {
			  super.onResume();
			  
			  Log.d("MY ON_CREATE ", "onResume" );
		  }
		  protected void onStop()
		  {
		      unregisterReceiver(WifiScanResultReceiver);
		      unregisterReceiver(WifiStateChangedReceiver);
		      Log.d("MY ON_CREATE ", "onStop" );
		      super.onStop();
		  }
		 		 
		//  protected void onRestart() {
			//  Log.d("MY ON_CREATE ", "onRestart" );
			  //super.onRestart();		    
		 // }
		  
		  protected void onDestroy()
		  {
		     // unregisterReceiver(WifiScanResultReceiver);
		      //unregisterReceiver(WifiStateChangedReceiver);
		      Log.d("MY ON_CREATE ", "onDestroy" );
		      timer_scan_update.cancel();//Timer stop
		      super.onDestroy();
		  }
		  
	  private BroadcastReceiver WifiScanResultReceiver
	  = new BroadcastReceiver(){
		  public void onReceive(Context context, Intent intent) {
			 // Log.d("MY WifiScanResultReceiver !!!  ", "INSIDE" );				  
			  Toast.makeText(getApplicationContext(), "MY WifiScanResultReceiver INSIDE !!!", Toast.LENGTH_LONG).show();
			  			   
			  //if (wifiManager.getWifiState() ==  wifiManager.WIFI_STATE_ENABLED)		   		   
			   //wifiManager.WIFI_MODE_SCAN_ONLY 
				   
			  results_new_intent = wifiManager.getScanResults();
			  
			//  Log.d("MY WifiScanResultReceiver !!!  ", results_new_intent.get(1).SSID + ": " +Integer.toString(results_new_intent.get(1).level));
			  			  				  
			  //if(results_new_intent != null)
				  //Toast.makeText(getApplicationContext(), "WifiScanResultReceiver results_new_intent = " + results_new_intent, Toast.LENGTH_LONG).show();
			  			  
			  if(results_new_intent == null) {
				  Toast.makeText(getApplicationContext(), "WifiScanResultReceiver results_new_intent == NULL", Toast.LENGTH_LONG).show();
			  } else {		  
				  //Toast.makeText(getApplicationContext(), "WifiScanResultReceiver results_new_intent = " + results_new_intent, Toast.LENGTH_LONG).show();
				  if(lvAP != null)
					  Toast.makeText(getApplicationContext(), "WifiScanResultReceiver lvAP EXIST", Toast.LENGTH_LONG).show();
			
			     if (adapterlist == null)
			     {
			    	 adapterlist = new CustomScanListAdapter(getApplicationContext(),results_new_intent);	   	 
			    	 lvAP.setAdapter(adapterlist);
			    	 flag++;
			     }
			     	//Refresh adapter list
			     	adapterlist.setData(results_new_intent);
			     	adapterlist.notifyDataSetChanged();				  
			 }			 
		}	 	 		  
	  };
	  
	  	void timerMethod()
	  	{
	  		timer_scan_update.schedule(new TimerTask() {
	  			public void run() {	  				
	  				Log.d("MY Timer", "run code");
	  				wifiManager.startScan(); 				
	  			}
	  		}, 5000, 5000);
	  	}		  
}

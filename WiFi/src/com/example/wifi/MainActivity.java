package com.example.wifi;
import java.util.ArrayList;
import java.util.List;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends Activity implements OnClickListener {

	TextView WifiState,WifiInfo;
	Button WifiOn,WifiOff,WifiParam;
	TextView rowtablename,rowssid_1,rowssid_2,rowssid_3;
	List<ScanResult> results;
	ListView lvAP;
	int extraWifiState ;
	
	ArrayList<ScanItem> scan_details = new ArrayList<ScanItem>();
	
	final ArrayList<String> apnames = new ArrayList<String>() ;	
	Intent intent_1 = new Intent("my.action.bat.SCHEDULE_ACT");
	
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
	      this.registerReceiver(this.WifiStateChangedReceiver,
	              new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));
	      WifiOn.setOnClickListener(this);
	      WifiOff.setOnClickListener(this);
	      WifiParam.setOnClickListener(this);      
	     	     
	      //try {
	    	  //rowssid_1.setText("MAIN");  
	    //  } 
	     // catch (Exception e) {
	    	//  Log.e("ERROR", "ERROR IN CODE" + e.toString());
	    	 // e.printStackTrace();
	      //}	      
	     // Intent myIntent = new Intent(MainActivity.this, ActivityTwo.class);	      
     //     myIntent.putExtra("FirstRow",rowssid_1.getText());      
	      
	  	  //lvAP = (ListView)findViewById(R.id.listAP);
		  //Create adapter
		  //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.my_listview, apnames);
		  //Put adapter into list
		  //lvAP.setAdapter(adapter);	
		 
	      //scan_details = getListData();
		 
	      final ListView lvAP = (ListView) findViewById(R.id.listAP);
	      lvAP.setAdapter(new CustomListAdapter(this, scan_details));
	    }
		  
	  @Override  
		public void onClick(View v) {
			Log.d("MY TAG ","SWITCH on ACTION");		
			Log.d("MY TAG ", "State = " + extraWifiState);

			//Intent intent_1 = new Intent(this, ActivityTwo.class);
			
			WifiManager wifiManager = (WifiManager)getBaseContext().getSystemService(Context.WIFI_SERVICE); 
			//WifiManager myWifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
			WifiInfo myWifiInfo = wifiManager.getConnectionInfo();			
					
		  //WifiState.setText(myWifiInfo.getSSID());						    
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
				
				wifiManager.startScan(); 
		        // get list of the results in object format ( like an array )
		         results = wifiManager.getScanResults();
		         
		       //In Android 4.0.x, getConnectionInfo() calls 
	        
		        // loop that goes through list
		        for (ScanResult result : results) {	
		        	 ScanItem scan_element = new ScanItem();
		         //   Toast.makeText(this, result.SSID + " " + result.level + " " + result.frequency + " MHz",
		           //         Toast.LENGTH_SHORT).show();
		        	 Log.d("MY TAG ", "***********************");
		            Log.d("MY TAG ", result.SSID + " " + result.level + " " + result.frequency + " MHz");	
		            
		            //result.capabilities
		            //[WPA2-EAP-TKIP+CCMP] - cipher; [ESS] - no cipher
		            //[WPA-PSK-TKIP+CCMP][WPA2-PSK-TKIP-CCMP][WPS][ESS]		            
		                     	            
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
		          
		    
		            if(result.capabilities.contains("WPA2"))
		            	scan_element.setCipherType("WPA2");
		            else if (result.capabilities.contains("WPA"))
		            	scan_element.setCipherType("WPA");
		            else if (result.capabilities.contains("WPS"))
		            	scan_element.setCipherType("WPS");	
		            else if (result.capabilities.contains("WEP"))
		            	scan_element.setCipherType("WEP");	
		            else 
		            	scan_element.setCipherType("ESS");          
		            		      	            
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
}

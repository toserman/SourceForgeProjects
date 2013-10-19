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

//import ActivityTwo;

public class MainActivity extends Activity implements OnClickListener {

	TextView WifiState,WifiInfo;
	Button WifiOn,WifiOff,WifiParam;
	TextView rowtablename,rowssid_1,rowssid_2,rowssid_3;
	List<ScanResult> results;
	ListView lvAP;
	int extraWifiState ;
	
	class wifiparam {
		public int a;
	};
	final ArrayList<wifiparam> myparam = new ArrayList<MainActivity.wifiparam>();
	
	
	
	final ArrayList<String> apnames = new ArrayList<String>() ;	
	Intent intent_1 = new Intent("my.action.bat.SCHEDULE_ACT");
	String myString;
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
	      
	      
	      myString = "HELLO WORLD";
	      //try {
	    	  //rowssid_1.setText("MAIN");  
	    //  } 
	     // catch (Exception e) {
	    	//  Log.e("ERROR", "ERROR IN CODE" + e.toString());
	    	 // e.printStackTrace();
	      //}	      
	     // Intent myIntent = new Intent(MainActivity.this, ActivityTwo.class);	      
     //     myIntent.putExtra("FirstRow",rowssid_1.getText());      
	      
	  	  lvAP = (ListView)findViewById(R.id.listAP);
		  //Create adapter
		  ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.my_listview, apnames);
		  //Put adapter into list
		  lvAP.setAdapter(adapter);	
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
				Log.d("MY TAG ", "After Disable State = " + extraWifiState);
			break;
			case R.id.wifiParam:
				WifiInfo.setText("BSSID:"     + myWifiInfo.getSSID() + "\n"
								+ "RSSI:"     + myWifiInfo.getRssi() + "\n"
							//	+ "MAC: "     + myWifiInfo.getMacAddress() + "\n"
								+ "LINK SPEED: "     + myWifiInfo.getLinkSpeed() + "\n"
								+ "SUPPLICANT:" + myWifiInfo.getSupplicantState()
									);
				wifiManager.startScan(); 
		        // get list of the results in object format ( like an array )
		         results = wifiManager.getScanResults();

		         
		        int count = 0;
		        // loop that goes through list
		        for (ScanResult result : results) {	
		        	
		         //   Toast.makeText(this, result.SSID + " " + result.level + " " + result.frequency + " MHz",
		           //         Toast.LENGTH_SHORT).show();
		            Log.d("MY TAG ", result.SSID + " " + result.level + " " + result.frequency + " MHz");
		            Log.d("MY TAG count = ", "" + count);
		            switch(count) {
		            case 0:
		            	intent_1.putExtra("FirstRow", myString.toString() );
		            	
		            	//intent_1.putExtra("FirstRow", result.SSID.toString() );
		            	//intent_1.putExtra("FirstRow", rowssid_1.getText().toString() );
		            	  //RowSSID1
		            //	startActivity(intent_1);
		            	//rowssid_1.setText(result.SSID);
		            	//intent.putExtra("FirstRow", rowssid_1.getText().toString());
		            	//startActivity(intent);
		            	
		            	//rowssid_1.setText(result.SSID);
		          //      ActivityTwo.rowssid_1.setText("DDDDD");
		       	     
		            	break;
		            case 1:
		            	intent_1.putExtra("SecondRow", myString.toString());
		            	//rowssid_2.setText(result.SSID);
		            //	break;
		            case 2:
		            	//rowssid_3.setText(result.SSID);
		            	//break;
		            }
		            count++;
		            apnames.add(result.SSID);   
		            
		        }
		        
				Log.d("MY TAG ", "Get BSSID = " + myWifiInfo.getSSID());
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
		      //tvColor.setTextColor(Color.RED);
		      //tvColor.setText("Text color = red");
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

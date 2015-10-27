/**
 * 
 */
package com.example.wifi;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

/**
 * @author anton
 *
 */
public class ActitvityFilter extends Activity {
	
	List<ScanResult> results;
	WifiManager wifiManager;

	CustomScanListAdapter testadapterlist;
	ListView testlvAP;
	ArrayList<TestScanResult> test_list_filter = new ArrayList<TestScanResult>(); //JUST FOR TEST
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {		
	    super.onCreate(savedInstanceState);
	    wifiManager = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);
	    setContentView(R.layout.activity_filter);	
	    
	    testlvAP = (ListView)findViewById(R.id.listApFilter);
	    
	    results = wifiManager.getScanResults();//Get result for first run
	    
	    Bundle b = getIntent().getExtras();
	    int receiv_id = b.getInt("chosen_id");
	    if( receiv_id == ActivityTwo.AP_FILTER)
	    	Toast.makeText(getApplicationContext(),"ActivityFilter onCreate() receiv_id AP_FILTER = " + Integer.toString(receiv_id),Toast.LENGTH_SHORT).show();
	    
	    if (wifiManager.getWifiState() ==  wifiManager.WIFI_STATE_ENABLED)
		 {	
	    	if(results != null) Log.d("MY TAG ", "NOT EMPTY results" );
	    	  
	    	testadapterlist = new CustomScanListAdapter(this,results); 	 
	    	if(testadapterlist != null) Log.d("MY TAG ", "NOT EMPTY testadapterlist" );
		      //lvAP.setAdapter(new CustomScanListAdapter(this,results));
			  testlvAP.setAdapter(testadapterlist);
		      Log.d("MY TAG ", "WIFI STATE:" + Integer.toString(wifiManager.getWifiState()));
		     
		 }	 	
	    
//	    if(results != null) { 
//	    	testadapterlist = new CustomScanListAdapter(this,results);
	
//	    	testlvAP.setAdapter(new CustomScanListAdapter(this,results));
//	    testlvAP.setAdapter(testadapterlist);
//	      Log.d("MY TAG ", "WIFI STATE:" + Integer.toString(wifiManager.getWifiState()));
//	    }
	    // TODO Auto-generated method stub
	}

}

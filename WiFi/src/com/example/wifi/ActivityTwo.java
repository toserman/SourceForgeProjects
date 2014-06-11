package com.example.wifi;



import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActivityTwo extends Activity {
	
	IntentFilter wifiScanAvailIntentSecond;
	Timer timerChart;
	TimerTask tasktimerChart;
	List<ScanResult> results_scan_intent;
	WifiManager wifiManager;
	
	 Button btnGenerate;
	 DrawChart barChart;
	 static int test  = 1;
	 public BroadcastReceiver WifiScanResultReceiver =
			  new BroadcastReceiver(){
		  public void onReceive(Context context, Intent intent) {
			  Log.d("MY ActivityTwo ", "WifiScanResultReceiver !!! INSIDE" );				  
			//  Toast.makeText(getApplicationContext(), "MY ActivityTwo WifiScanResultReceiver !!!", Toast.LENGTH_LONG).show();
			  results_scan_intent = wifiManager.getScanResults(); 
			  
			  Log.d("MY ActivityTwo ", "WifiScanResultReceiver freq = "
			  + Integer.toString(CustomScanListAdapter.convertFreqtoChannelNum(2412,CustomScanListAdapter.arr_freq)));
		  }
	 };  
	 protected void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    Log.d("MY ActivityTwo ", "onCreate()" );	
		    //setContentView(new DrawView(this));
		   // wifiManager = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);
		   //wifiScanAvailIntentSecond =  new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
		    
		    setContentView(R.layout.activity_two);
		    barChart = (DrawChart) findViewById(R.id.barchart);		    
		    btnGenerate = (Button)findViewById(R.id.test_button_generate);
		    
		    OnClickListener btnGen = new OnClickListener() {
		        @Override
		        public void onClick(View v) {
		          // TODO Auto-generated method stub
		        	 Log.d("MY ActivityTwo ", "Click button" );
		        	 barChart.forbuttonDraw(5);
		        	 ChartEngine.frame_ready = 1;
		        	 //barChart.first = 1;
		        	 
		       }
		   };
		      btnGenerate.setOnClickListener(btnGen);
		  }
		  
	 
		  void timerMethod()
		  {
			  timerChart = new Timer();
			  tasktimerChart = new TimerTask() {
				  public void run() {
				//	  Log.d("MY timerChart", "run code in timerChart");
	
					 // wifiManager.startScan();
					 
				  }
	  		  };
	  		  timerChart.schedule(tasktimerChart, 5000, 5000);
		  }	
		  protected void onStart() {
		//	  this.registerReceiver(this.WifiScanResultReceiver, wifiScanAvailIntentSecond);
			  /** Run timer*/
			  timerMethod();
			  super.onStart();
		  }
		  protected void onStop() {
			  super.onStop();	
		    //  unregisterReceiver(WifiScanResultReceiver);
		      
		      if(timerChart != null)
		    	  timerChart.cancel();//Timer stop		      
		  }

}




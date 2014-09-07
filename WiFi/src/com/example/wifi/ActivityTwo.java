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
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActivityTwo extends Activity {

	IntentFilter wifiScanAvailIntentSecond;
	Timer timerChart;
	TimerTask tasktimerChart;
	List<ScanResult> results_scan_intent;
	WifiManager wifiManager;
	List<ScanResult> results; //JUST FOR TEST
	 Button btnGenerate;
	 DrawChart barChart;
	 ChartSurfaceView draw_barChart; 
	 static int test  = 1;
	 ArrayList<TestScanResult> list_one = new ArrayList<TestScanResult>(); //JUST FOR TEST
	 ArrayList<TestScanResult> list_two = new ArrayList<TestScanResult>(); //JUST FOR TEST
	 ArrayList<TestScanResult> list_third = new ArrayList<TestScanResult>(); //JUST FOR TEST
	 
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

		    //NEW VERSION
		    draw_barChart = new ChartSurfaceView(this);
		    setContentView(draw_barChart);
		    //OLD VERSION
//		   setContentView(R.layout.activity_two);
//		    barChart = (DrawChart) findViewById(R.id.barchart);		    
		    TestScanResult.FillListOne(list_one);
		    TestScanResult.FillListSecond(list_two);
		    TestScanResult.FillListThird(list_third);
		   
		    // btnGenerate = (Button)findllViewById(R.id.test_button_generate);		    
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
		      //btnGenerate.setOnClickListener(btnGen);
		  }
		  
	 
		  void timerMethodSecondAct()
		  {
			  timerChart = new Timer();
			  tasktimerChart = new TimerTask() {
				  public void run() {
					  Log.d("MY timerMethodSecondAct", "run code in timerChart each 5 sec");
					 // wifiManager.startScan();
//					  results = wifiManager.getScanResults();
//					  ScanResult obj1 = new ScanResult() ;					  
//					  results.add(arg0)
				  }
	  		  };
	  		  timerChart.schedule(tasktimerChart, 5000, 5000);	 
//			   1	2	3	4    5     6    7    8    9    10   11  12   13    14
//public static int[] arr_freq = {0,2412,2417,2422,2427,2432,2437,2442,2447,2452,2457,2462,2467,2472,2484}; 
	  		  
		  }	
		  protected void onStart() {
//			  this.registerReceiver(this.WifiScanResultReceiver, wifiScanAvailIntentSecond);
			  /** Run timer*/
			  timerMethodSecondAct();
			  super.onStart();
		  }
		  protected void onStop() {
			  super.onStop();	
		    //  unregisterReceiver(WifiScanResultReceiver);
		      
		      if(timerChart != null)
		    	  timerChart.cancel();//Timer stop		      
		  }
		  
		  public boolean onTouchEvent(MotionEvent event)
		  {
		  	Log.d("MY onTouchEvent", "onTouchEvent");
		  	
			    if(event.getAction() == MotionEvent.ACTION_DOWN)
			    {
			    	DrawThread.test_flag = true;
			    	DrawThread.new_data_flag = true; //New data received
			    	//draw_barChart = new ChartSurfaceView(this);
			    	
				    setContentView(draw_barChart);
			    }
			    return true;
		  }
		  
		  
}




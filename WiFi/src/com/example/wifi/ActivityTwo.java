package com.example.wifi;



import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.chart.PieChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

public class ActivityTwo extends Activity {
	
	IntentFilter wifiScanAvailIntentSecond;
	Timer timerChart;
	TimerTask tasktimerChart;
	List<ScanResult> results_scan_intent;
	WifiManager wifiManager;
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
		//    wifiScanAvailIntentSecond =  new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
		     DrawChart barChart = (DrawChart)findViewById(R.id.barchart);
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




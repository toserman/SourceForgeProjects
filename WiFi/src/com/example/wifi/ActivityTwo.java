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
	 public BroadcastReceiver WifiScanResultReceiver;
	 protected void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    setContentView(new DrawView(this));
		    wifiManager = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);
		    wifiScanAvailIntentSecond =  new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
		    
		    
		    
		  }

		  class DrawView extends View {	
			  Paint p;
			  ChartEngine chart;
			  float touchX = 0; // FOR DEBUG
			  float touchY = 0; // FOR DEBUG
		    public DrawView(Context context) {		    	
		      super(context);
		      p = new Paint();
		      chart = new ChartEngine(context);
		      ChartEngine.getDisplaySize(ActivityTwo.this,getApplicationContext());		
		      WifiScanResultReceiver = new BroadcastReceiver(){
				  public void onReceive(Context context, Intent intent) {
					  Log.d("MY ActivityTwo ", "WifiScanResultReceiver !!! INSIDE" );				  
					  Toast.makeText(getApplicationContext(), "MY ActivityTwo WifiScanResultReceiver !!!", Toast.LENGTH_LONG).show();
					  results_scan_intent = wifiManager.getScanResults(); 
					  
					  Log.d("MY ActivityTwo ", "WifiScanResultReceiver freq = "
					  + Integer.toString(CustomScanListAdapter.convertFreqtoChannelNum(2412,CustomScanListAdapter.arr_freq)));
					  
					  
					  for (ScanResult result : results_scan_intent)    		
						 	Log.d("MY results_scan_intent CHECK: SSID: ", result.SSID);
					//if(results_scan_intent != null)
					 // Toast.makeText(getApplicationContext(), "WifiScanResultReceiver results_scan_intent = " + results_scan_intent, Toast.LENGTH_LONG).show();
				  		
					  invalidate();
				  }
			  };
		    }
		    
		    @Override
		    protected void onDraw(Canvas canvas) {
		    	 Log.d("MY ActivityTwo ", "onDraw !!!");
		    	 Log.d("MY OnDraw ", "canvas.getHeight() = " + canvas.getHeight() 
		    			 			+ "canvas.getWidth()= " + canvas.getWidth());

		    	 /*
			    	 Display display = getWindowManager().getDefaultDisplay();
			    	 Point size = new Point();
			    	 display.getSize(size);
			    	 int width = size.x;
			    	 int height = size.y;
			    	 //width = 480 height = 800
			    	 Log.d("MY ActivityTwo: Display: ", "width = " + Integer.toString(width) + " height = "
			    			 		+ Integer.toString(height)); 
		    	*/ 

		    	 chart.drawAxisXY(canvas);	 
		    	 chart.testDraw(canvas,touchX,touchY);
		    	 touchX+=5;
		    	 touchY+=5;
		    	// if (touchX < canvas.getWidth())
		    		// invalidate();
		    	 if(touchY < 200)
		    		 invalidate();
		
		    	//  canvas.drawColor(Color.rgb(0x1c,0x1c,0x1c));
		    	 // brush preferences
		          //  p.setColor(Color.WHITE);
		            // толщина линии = 10
		           // p.setStrokeWidth(10);
		            //canvas.drawLine(2 ,788,2,2,p);		           
		            //invalidate();
		    }
		    protected void reDraw() {
		    	 Log.d("MY ActivityTwo ", "reDraw !!!");
		         this.invalidate();
		    }
		    
		    
		    public boolean onTouchEvent(MotionEvent event)
		    {
		    	Log.d("MY onTouchEvent", "onTouchEvent");
			    if(event.getAction() == MotionEvent.ACTION_DOWN)
			    {
			    	//touchX = event.getX();
			    	//touchY = event.getY();
		    		invalidate();		
			    }
			    return true;
		    }
		    
		//	  public BroadcastReceiver
		    
		    
		  }

		  
		  void timerMethod()
		  {
			  timerChart = new Timer();
			  tasktimerChart = new TimerTask() {
				  public void run() {
					  Log.d("MY timerChart", "run code in timerChart");
					  wifiManager.startScan();
					  //chart.testDraw(canvas);
				  }
	  		  };
	  		  timerChart.schedule(tasktimerChart, 5000, 5000);
		  }	
		  protected void onStart() {
			  this.registerReceiver(this.WifiScanResultReceiver, wifiScanAvailIntentSecond);
			  /** Run timer*/
			  timerMethod();
			  super.onStart();
		  }
		  protected void onStop() {
			  super.onStop();	
		      unregisterReceiver(WifiScanResultReceiver);
		      
		      if(timerChart != null)
		    	  timerChart.cancel();//Timer stop		      
		  }

}




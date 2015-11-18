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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;

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
	 static int count_list = 0; //JUST FOR TEST
	 public static final boolean PHONE = false; //true = PHONE , false = AVD
	 public static final int AP_FILTER = 1;
	 public static final int CH_NUM_FILTER = 2;
	 
	 ArrayList<TestScanResult> list_one = new ArrayList<TestScanResult>(); //JUST FOR TEST
	 ArrayList<TestScanResult> list_two = new ArrayList<TestScanResult>(); //JUST FOR TEST
	 ArrayList<TestScanResult> list_third = new ArrayList<TestScanResult>(); //JUST FOR TEST

	 ArrayList<ScanItem> list_wifi_ready = new ArrayList<ScanItem>();//READY LIST from WIFI

	 public BroadcastReceiver WifiScanResultReceiver =
			  new BroadcastReceiver(){
		  public void onReceive(Context context, Intent intent) {
						  
			  Toast.makeText(getApplicationContext(), "MY ActivityTwo WIFI results.size = " + Integer.toString(results.size()),
					  								Toast.LENGTH_LONG).show();
			  results.clear();
			  results = wifiManager.getScanResults();
//			  Log.e("MY ActivityTwo ", "WifiScanResultReceiver !!! results.size = " + Integer.toString(results.size()) );			  draw_barChart.wifi_results = results;
			  draw_barChart.destroyDrawingCache();
			  draw_barChart.surfaceCreated(draw_barChart.getHolder());			  
			  	 
		  }
	 };  
	 protected void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    Log.d("MY ActivityTwo ", "onCreate()" );		    
		    // FOR PHONE
		    if (ActivityTwo.PHONE)
		    {
			    wifiManager = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);
			    wifiScanAvailIntentSecond =  new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
			    /* Register Receiver */		    	     
			    this.registerReceiver(this.WifiScanResultReceiver, wifiScanAvailIntentSecond);	
			    results = wifiManager.getScanResults();//Get result for first run	
			    //TestScanResult.FillListFromWIFI(results,list_two);
			    draw_barChart = new ChartSurfaceView(this,results); //FOR PHONE
		    }		    
		    // FOR AVD 
		    if (!ActivityTwo.PHONE)
			{		
//		    	TestScanResult.FillListOne(list_one);
//		    	TestScanResult.FillListSecond(list_two);
//		    	TestScanResult.FillListThird(list_third);
		    	draw_barChart = new ChartSurfaceView(this,list_two); // FOR AVD

			}		    		    	
		    
		    setContentView(draw_barChart);		   
		}

	 	void timerMethodSecondAct()
		  {
			  timerChart = new Timer();
			  tasktimerChart = new TimerTask() {
				  
				  public void run() {
					  Log.e("MY timerMethodSecondAct", "run code in timerChart each 15 sec count_list: " + Integer.toString(count_list));					  
					
					  if (!ActivityTwo.PHONE)
					  {						
						  count_list++;
						
						  if (count_list == 1)
						  {						  
							  draw_barChart.test_scan_list = list_two;						  
						  }  
						  if (count_list == 2)
						  {
							  list_two.clear();
							  TestScanResult.FillListSecondUpdated_1(list_two);
							  draw_barChart.test_scan_list = list_two;						  
						  }
						  if (count_list == 3)
						  {
							  list_two.clear();
							  TestScanResult.FillListSecondUpdated_2(list_two);
							  draw_barChart.test_scan_list = list_two;						  
						  }
						  if (count_list == 4)
						  {
							  list_two.clear();
							  TestScanResult.FillListThird(list_two);
							  draw_barChart.test_scan_list = list_two;
							  
							  count_list = 0;
						  }
						  
						  /* SECTION FOR UPGRADE on 1channel*/ 
//						  if (count_list == 1)
//						  {						  
//							  draw_barChart.test_scan_list = list_two;						  
//						  }  
//						  if (count_list == 2)
//						  {
//							  list_two.clear();
//							  TestScanResult. FillList_On1ch(list_two);
//							  draw_barChart.test_scan_list = list_two;						  
//						  }
//						  if (count_list == 3)
//						  {
//							  list_two.clear();
//							  TestScanResult. FillListSecondUpdated_On1ch(list_two);
//							  draw_barChart.test_scan_list = list_two;	
//							  count_list = 0;
//						  }				
						  
						    draw_barChart.surfaceCreated(draw_barChart.getHolder());  
					  }				  
															
					 if (ActivityTwo.PHONE)
					 {
						 // Request for WIFI scan results
						 wifiManager.startScan();
					 }
				  }
	  		  };
	  		  timerChart.schedule(tasktimerChart, 5000, 10000);	 
//			   1	2	3	4    5     6    7    8    9    10   11  12   13    14
//public static int[] arr_freq = {0,2412,2417,2422,2427,2432,2437,2442,2447,2452,2457,2462,2467,2472,2484}; 
		  }	

	 	@Override
		public boolean onCreateOptionsMenu(Menu sec_act_menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
	 		getMenuInflater().inflate(R.menu.menu_second_act, sec_act_menu);
			return true;
		}
	 	
	 	public void showSortPopupMenu(){
	 	    View menuItemView = findViewById(R.id.menu_sort_ap);
	 	    PopupMenu popupMenu = new PopupMenu(this, menuItemView);
	 	    MenuInflater inflate = popupMenu.getMenuInflater();
	 	    inflate.inflate(R.menu.filter_pop_menu, popupMenu.getMenu());

	    	popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {  
	             public boolean onMenuItemClick(MenuItem item) {  
	            	 switch (item.getItemId()) {
	 			    	case R.id.filterAPname:
	 			    		//Toast.makeText(getApplicationContext(),"You Clicked :  filterAPname",Toast.LENGTH_SHORT).show();
	 			    		draw_barChart.surfaceDestroyed(draw_barChart.getHolder());//Need to destroy surface for prevent Thread crash

//	 			    		ArrayList<String> list = new ArrayList<String>();
//	 			    		Intent intent = new Intent(ActivityTwo.this,ActitvityFilter.class);
//	 			    		intent.putExtra("arraylist", list);
//	 			    		startActivity(intent);
//	 			    		ArrayList<String> list = getIntent().getSerializableExtra("arraylist");
//	 			    		
//	 			    		Bundle bundle = new Bundle();
//	 			    		bundle.putParcelable("data", results);
//	 			    		intent.putExtras(bundle);
	 			    		
	 			    		Intent intentAPfilter = new Intent(ActivityTwo.this,ActitvityFilter.class);
	 			    		intentAPfilter.putExtra("chosen_id",AP_FILTER);
	 			    		startActivity(intentAPfilter);
	 
	 			    		break;
	 			    	case R.id.filterChannel:
	 			    		Toast.makeText(getApplicationContext(),"You Clicked :  filterChannel",Toast.LENGTH_SHORT).show();
	 			    		break;	 			    		
	            	 }
	            	 //Toast.makeText(getApplicationContext(),"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();  
	              return true;  
	             }
	        }); 
	 	    popupMenu.show();
	 	}

	 	
		public boolean onOptionsItemSelected(MenuItem item) {
			    // TODO Auto-generated method stub	        
			    switch (item.getItemId()) {
			    case R.id.menu_sort_ap:
			    	showSortPopupMenu();
			    	break;
			    }
			    return super.onOptionsItemSelected(item);
		  }
	 	
	 	  protected void onStart() {
//			  this.registerReceiver(this.WifiScanResultReceiver, wifiScanAvailIntentSecond);
			  /** Run timer*/
//			  timerMethodSecondAct();
			  super.onStart();
		  }
		  protected void onStop() {
			  super.onStop();	
//		      unregisterReceiver(WifiScanResultReceiver);
		      
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
				    setContentView(draw_barChart);				    
			    }
			    return true;
		  }
}




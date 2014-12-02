package com.example.wifi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.R.bool;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.widget.Toast;

public class DrawThread extends Thread {
	private boolean runFlag = false;
    private SurfaceHolder surfaceHolder;
    private Bitmap myBitmap;
    ChartEngine chart;
    
    int temp;
    int draw_marker;
    int high_rssi;
	
    static boolean test_flag = true;
    static boolean new_data_flag = false;
    
    ArrayList<TestScanResult> test_inp_wifi_list; //FOR AVD
    List<ScanResult> wifi_list_draw; // FOR PHONE 
    
	ArrayList<ScanItem> list_ap_new = new ArrayList<ScanItem>();
	public static ArrayList<ScanItem> list_ap_old = new ArrayList<ScanItem>(); 
	ArrayList<ScanItem> list_ap_res = new ArrayList<ScanItem>(); 
    
    Paint p;    
    Canvas canvas;
	
    //ScanResult
    public DrawThread(SurfaceHolder surfaceHolder,
			   Resources resources,
			   Context context,
			   List<ScanResult> input_wifi_list,
			   ChartEngine inp_chart)
    {    
		Log.d("MY DrawThread:", "PHONE Constructor create !!!" );
		this.surfaceHolder = surfaceHolder;
		p = new Paint();
		chart = inp_chart;
		wifi_list_draw = input_wifi_list;    	
		myBitmap =  Bitmap.createBitmap(ChartEngine.width,ChartEngine.height,Bitmap.Config.ARGB_8888);    	
		canvas = new Canvas(myBitmap);
		chart.drawAxisXY(canvas);
		// Save current time
		temp = 0;
		
//		Log.d("MY DrawThread:", "PHONE Constructor create  2 !!!" );
			//Received new data fill ap_new list
			for (int i = 0; i < input_wifi_list.size(); i++)
			{
		//		chart.testAddScanItem(SSID, BSSID, inpRSSI, inpFreq, index_color, inp_list_ap);
				/* */
				if (i >= chart.NUMBER_OF_COLORS)
					break;
		//		if (ActivityTwo.PHONE)
		//		{      			
		//			if(i == 1)//Limit AP for DEBUG
		//				break;
		//		} 
//				Log.d("MY DrawThread:", "PHONE Constructor create  3 !!!" );
				
				//COPY last DATA to AP_NEW
				chart.testAddScanItem(wifi_list_draw.get(i).SSID,
						wifi_list_draw.get(i).BSSID,
						wifi_list_draw.get(i).level,
						wifi_list_draw.get(i).frequency,
									  i,list_ap_new);
				
//				Log.d("MY DrawThread:", "PHONEConstructor create  4 !!!" );
//				
			}        

}
    
  public DrawThread(SurfaceHolder surfaceHolder,
    				   Resources resources,
    				   Context context,
    				   ArrayList<TestScanResult> inp_wifi_list,
    				   ChartEngine inp_chart)
    {    
    	Log.d("MY DrawThread:", "AVD Constructor create  1 !!!" );
        this.surfaceHolder = surfaceHolder;
    	p = new Paint();
    	chart = inp_chart;
    	test_inp_wifi_list = inp_wifi_list;    	
    	myBitmap =  Bitmap.createBitmap(ChartEngine.width,ChartEngine.height,Bitmap.Config.ARGB_8888);    	
    	canvas = new Canvas(myBitmap);
    	chart.drawAxisXY(canvas);
        // Save current time
        temp = 0;

        	//Received new data fill ap_new list
        	for (int i = 0; i < test_inp_wifi_list.size(); i++)
        	{
//        		chart.testAddScanItem(SSID, BSSID, inpRSSI, inpFreq, index_color, inp_list_ap);
        		/* */
        		if (i >= chart.NUMBER_OF_COLORS)
        			break;
//        		if (ActivityTwo.PHONE)
//        		{      			
//        			if(i == 1)//Limit AP for DEBUG
//        				break;
//        		} 
   
        		
        		//COPY last DATA to AP_NEW
        		chart.testAddScanItem(test_inp_wifi_list.get(i).SSID,
        							  test_inp_wifi_list.get(i).BSSID,
        							  test_inp_wifi_list.get(i).rssi,
        							  test_inp_wifi_list.get(i).freq,
        							  i,list_ap_new);  
        		
        	}        
      
    }

    public void setRunning(boolean run) {
//    	Log.d("MY DrawThread:", "setRunning() set runFlag : " + Boolean.toString(run) );
        runFlag = run;
//    	 DrawThread.test_flag = run;    	
    }

    @Override
    public void run() {
//    	Log.d("MY DrawThread:", "run() draw picture !!!");    	
    					//  		   1	2	3	4    5     6    7    8    9    10   11  12   13    14
//public static int[] arr_freq = {0,2412,2417,2422,2427,2432,2437,2442,2447,2452,2457,2462,2467,2472,2484}; 
    	

//    	chart.testPrintList(list_ap_new,"list_ap_new");
    	chart.compareListData(list_ap_old, list_ap_new,list_ap_res);
//    	chart.testPrintList(list_ap_old,"list_ap_old");

		/* find the biggest RSSI and set limitation for draw */
		high_rssi = chart.findHighestRSSI(list_ap_res);
	    draw_marker = chart.getCoordRSSILevel(high_rssi);
   
//   Log.e("MY TAG ", "draw_marker = " + Integer.toString(draw_marker));
//   Log.e("MY TAG ", "temp =  " + Integer.toString(temp));
        while (runFlag) {
            canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas(null);
                synchronized(surfaceHolder) {
                	if (temp >= draw_marker)
                	{                		
                		setRunning(false);
                		//chart.testPrintList(list_ap_new, "list_ap_new"); //FOR DEBUG                		                		
                		Collections.copy(list_ap_old, list_ap_new);
                		list_ap_new.clear();
                		//chart.testPrintList(list_ap_old, "AFTER COPY list_ap_old"); 
                		
                	}
            		// TO DO : NO NEED TO REDRAW SCREEN WHEN NOTHING CHANGED temp value incremented !!!!
                    canvas.drawBitmap(myBitmap, 0, 0,null );                    
                    chart.startDraw(canvas,temp,list_ap_old,list_ap_res);
              		temp+= 2; //temp = 2 
//              		 Log.e("MY TAG ", "INSIDE THREAD temp =  " + Integer.toString(temp));
              		if (temp == draw_marker)
                	    break;
                }
            } 
            finally {
                if (canvas != null) {
             
                	// Drawing finish and display on the screenhjghj
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        } //end while
    } //end run()
	
}



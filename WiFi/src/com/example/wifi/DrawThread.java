package com.example.wifi;

import java.util.ArrayList;
import java.util.Collections;
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
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

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
    
    ArrayList<TestScanResult> test_inp_wifi_list; //JUST FOR TEST
    
	ArrayList<ScanItem> list_ap_new = new ArrayList<ScanItem>();
	public static ArrayList<ScanItem> list_ap_old = new ArrayList<ScanItem>(); 
	ArrayList<ScanItem> list_ap_res = new ArrayList<ScanItem>(); 
    
    Paint p;    
    Canvas canvas;
	    	
    public DrawThread(SurfaceHolder surfaceHolder, Resources resources,Context context, ArrayList<TestScanResult> inp_wifi_list,ChartEngine inp_chart){
    
        this.surfaceHolder = surfaceHolder;
//    	Log.d("MY DrawThread:", "Constructor create !!!" );
    	p = new Paint();
    	chart = inp_chart;
    	test_inp_wifi_list = inp_wifi_list;    	
    	myBitmap =  Bitmap.createBitmap(ChartEngine.width,ChartEngine.height,Bitmap.Config.ARGB_8888);    	
    	canvas = new Canvas(myBitmap);
    	chart.drawAxisXY(canvas);
        // Save current time
        temp = 0;
//      chart.testAddScanItem("MARADONA","01:11:22:33:41:55",-95,2472,2,list_ap_old);
//    	chart.testAddScanItem("TEST","01:02:03:04:05:06",-70,2412,0,list_ap_old);
//      chart.testAddScanItem("ACTION","01:11:22:33:44:55",-80,2437,1,list_ap_old);    	
////    testAddScanItem("MAPROAD","77:11:22:33:44:55",-95,2457,2,list_ap_old);    	
//    	chart.testAddScanItem("MARCH","01:11:22:33:31:55",-95,2452,4,list_ap_new);
//		chart.testAddScanItem("TEST","01:02:03:04:05:06",-70,2412,0,list_ap_new);
//    	chart.testAddScanItem("ACTION","01:11:22:33:44:55",-70,2437,1,list_ap_new);
//    	chart.testAddScanItem("MARADONA","01:11:22:33:41:55",-95,2472,2,list_ap_new);        	
//
//        	//Received new data fill ap_new list
        	for (int i = 0; i < test_inp_wifi_list.size(); i++)
        	{
//        		chart.testAddScanItem(SSID, BSSID, inpRSSI, inpFreq, index_color, inp_list_ap);
        		/* */
       // 		if (i >= chart.NUMBER_OF_COLORS)
        			//break;
        		if (ActivityTwo.PHONE)
        		{      			
        			if(i == 1)//Limit AP for DEBUG
        				break;
        		} 
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
    	
    	//if (DrawThread.new_data_flag == true)
    	//{

//    	chart.testPrintList(list_ap_new,"list_ap_new");
    	chart.compareListData(list_ap_old, list_ap_new,list_ap_res);
//    	chart.testPrintList(list_ap_old,"list_ap_old");

		/* find the biggest RSSI and set limitation for draw */
		high_rssi = chart.findHighestRSSI(list_ap_res);
	    draw_marker = chart.getCoordRSSILevel(high_rssi);
   
   Log.e("MY TAG ", "draw_marker = " + Integer.toString(draw_marker));
   Log.e("MY TAG ", "temp =  " + Integer.toString(temp));
        while (runFlag) {
//	    while (DrawThread.test_flag) {
            canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas(null);
                synchronized(surfaceHolder) {
                	if (temp >= draw_marker)
                	{                		
                		setRunning(false);
                		chart.testPrintList(list_ap_new, "list_ap_new");                		                		
                		Collections.copy(list_ap_old, list_ap_new);
                		list_ap_new.clear();
                		
                		chart.testPrintList(list_ap_old, "AFTER COPY list_ap_old"); 
                		
                	}
            		
                    canvas.drawBitmap(myBitmap, 0, 0,null );                    
                    chart.startDraw(canvas,temp,list_ap_old,list_ap_res);
              		temp+= 2; //temp = 2 
              		 Log.e("MY TAG ", "INSIDE THREAD temp =  " + Integer.toString(temp));
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



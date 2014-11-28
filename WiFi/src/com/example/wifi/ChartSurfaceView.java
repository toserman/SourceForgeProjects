package com.example.wifi;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class ChartSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
	private DrawThread drawThread;
	public int just_test = 100;

	ChartEngine inp_chart;
	public ArrayList<TestScanResult> test_scan_list; //JUST FOR TEST
	List<ScanResult> wifi_results;
	ArrayList<ScanItem> list_ready = new ArrayList<ScanItem>();
	
	 public ChartSurfaceView(Context context,ArrayList<TestScanResult> wifi_scan_list) {
	        super(context);
	        Log.d("MY ChartSurfaceView:", "CONSTRUCTOR AVD" );
	        test_scan_list = wifi_scan_list;
	        inp_chart = new ChartEngine(context);
	        getHolder().addCallback(this);
	    }
	 public ChartSurfaceView(Context context,List<ScanResult> results) {
	        super(context);
	        Log.d("MY ChartSurfaceView:", "CONSTRUCTOR WIFI 1" );
	        wifi_results = results;//Get received results
	        Log.d("MY ChartSurfaceView:", "CONSTRUCTOR WIFI 2" );
	        inp_chart = new ChartEngine(context);
	      //  MoveFromWifiToList(wifi_results,list_ready); // FOR PHONE
	        //TestScanResult.FillListFromWIFI(results,test_scan_list);
	        getHolder().addCallback(this);
	    }
	    @Override
	    public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {
	    	Log.d("MY ChartSurfaceView:", "surfaceChanged() " );
	    	
	    }

	    @Override
	    public void surfaceCreated(SurfaceHolder holder) {
	    	Log.d("MY ChartSurfaceView:", "surfaceCreated() create and run Draw stream" );
//	    	drawThread = new DrawThread(getHolder(), getResources(),getContext(),test_scan_list,inp_chart);	    	
	    	//drawThread = new DrawThread(getHolder(),getResources(),getContext(),test_scan_list,inp_chart); // FOR AVD
	    	 if (ActivityTwo.PHONE)
	    		 drawThread = new DrawThread(getHolder(),getResources(),getContext(),wifi_results,inp_chart); // FOR PHONE
	    	 else
	    		drawThread = new DrawThread(getHolder(),getResources(),getContext(),test_scan_list,inp_chart); // FOR AVD
	    	
	    	drawThread.setRunning(true);
	        drawThread.start();	        
	    }

	    @Override
	    public void surfaceDestroyed(SurfaceHolder holder) {
	    	boolean retry = true;
	    	Log.d("MY ChartSurfaceView:", "surfaceDestroyed() kill Draw stream" );
	        // завершаем работу потока
	        drawThread.setRunning(false);
	        while (retry) {
	            try {
	                drawThread.join();
	                retry = false;
	            } catch (InterruptedException e) {
	                // если не получилось, то будем пытаться еще и еще
	            }
	        }
	    }
	    
	    public void MoveFromWifiToList (List<ScanResult> results,ArrayList<ScanItem> inp_list_ap)
	    {
//        	//Received new data fill ap_new list
        	for (int i = 0; i < results.size(); i++)
        	{
//        		chart.testAddScanItem(SSID, BSSID, inpRSSI, inpFreq, index_color, inp_list_ap);
        		inp_chart.testAddScanItem(results.get(i).SSID,
        									results.get(i).BSSID,
        									results.get(i).level,
        									results.get(i).frequency,
        										i,inp_list_ap);        				
        	}      
	    }
	    

}

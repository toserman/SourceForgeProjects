package com.example.wifi;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class ChartSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
	private DrawThread drawThread;
	public int just_test = 100;

	ChartEngine inp_chart;
	public ArrayList<TestScanResult> test_scan_list; //JUST FOR TEST	
	
	 public ChartSurfaceView(Context context,ArrayList<TestScanResult> wifi_scan_list) {
	        super(context);
	        Log.d("MY ChartSurfaceView:", "CONSTRUCTOR" );
	        test_scan_list = wifi_scan_list;
	        inp_chart = new ChartEngine(context);
	        getHolder().addCallback(this);
	    }

	    @Override
	    public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {
	    	Log.d("MY ChartSurfaceView:", "surfaceChanged() " );
	    	
	    }

	    @Override
	    public void surfaceCreated(SurfaceHolder holder) {
	    	Log.d("MY ChartSurfaceView:", "surfaceCreated() create and run Draw stream" );
	    	drawThread = new DrawThread(getHolder(), getResources(),getContext(),test_scan_list,inp_chart);	    	
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
	    

}

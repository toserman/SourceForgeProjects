package com.example.wifi;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ChartSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
	private DrawThread drawThread;
	 public ChartSurfaceView(Context context) {
	        super(context);
	        getHolder().addCallback(this);
	    }

	    @Override
	    public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {
	    	Log.d("MY ChartSurfaceView:", "surfaceChanged() " );
	    	
	    }

	    @Override
	    public void surfaceCreated(SurfaceHolder holder) {
	    	drawThread = new DrawThread(getHolder(), getResources(),getContext());
	    	Log.d("MY ChartSurfaceView:", "surfaceCreated() create and run Draw stream" );
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

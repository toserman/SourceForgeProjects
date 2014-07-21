package com.example.wifi;

import android.content.Context;
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
	    	
	    }

	    @Override
	    public void surfaceCreated(SurfaceHolder holder) {
	    	drawThread = new DrawThread(getHolder(), getResources());
	        drawThread.setRunning(true);
	        drawThread.start();
	    }

	    @Override
	    public void surfaceDestroyed(SurfaceHolder holder) {
	    	boolean retry = true;
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

package com.example.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;

public class DrawChart extends View {
	
	  Paint p = new Paint();
	  ChartEngine chart;
	  float touchX = 0; // FOR DEBUG
	  float touchY = 0; // FOR DEBUG
  
	  public static int temp;
	  int flag = 0; 
	  static int first = 0;

   public DrawChart(Context context, AttributeSet attrs) {	   
      super(context, attrs);      
      Log.e("MY DrawChart ", "Constructor !!!");
//      bitmap =  Bitmap.createBitmap(50,50,Bitmap.Config.ARGB_8888);
        

      chart = new ChartEngine(context);

  }
  
   @Override
  protected void onDraw(Canvas canvas) {
 	 Log.d("MY DrawChart ", "onDraw !!!");
	   
  //	 Log.d("MY OnDraw ", "canvas.getHeight() = " + canvas.getHeight() 
  	//		 			+ "canvas.getWidth()= " + canvas.getWidth());
 	// canvas.drawBitmap(bitmap, 0, 0, paint);

  	chart.drawAxisXY(canvas);
//  	 if (ChartEngine.frame_ready == 1)
//  	 {  
//  		chart.startDraw(canvas, temp);
//  		if (ChartEngine.frame_ready == 1)
////	  		 invalidate(60/*OFFSET_Y_AXISX LEFT POS*/,(canvas.getHeight() - 600 )/*TOP POS*/,
////	  				 	60 + 285/*RIGHT POS*/,canvas.getHeight() - 15/*BOTTOM POS*/);
//  			invalidate();
//  			//postInvalidate();
//		temp+=1;		
//  	 }  	 
  	
  }

   public void forbuttonDraw (int a)
   {
	   Log.d("MY DrawChart ","forbuttonDraw befor invalidate");
	Log.e("MY ChartEngine: forbuttonDraw: ", "getWidth() = " + Integer.toString(getWidth()) +
			" getHeight() = " + Integer.toString(getHeight()));
	
	Log.e("MY ChartEngine: forbuttonDraw: ", "getPaddingBottom() = " + Integer.toString(getPaddingBottom())
			+ " getPaddingTop() = " + Integer.toString(getPaddingTop()));
	   temp = a;	  
	   invalidate();
	   first = 0;
   }
   
	public void buttonDraw (Canvas canvas)
	{
		final int START_X_AXISX = 50;//pixel
		final int START_X_AXISY = 60;
		final int START_Y_AXISXY = 71;
		int height = 800;
		Paint p = new Paint();		
		p.setARGB(0xA9,0xF7,0xE8,0x36);
		Log.d("MY DrawChart ","buttonDraw");			
		//for(int y = temp/*global*/; y < 10;y++)
		//{
			canvas.drawRect(START_X_AXISY + 100,(height - 70),START_X_AXISY + 150,(height - 70 + 40 - temp++),p);
			temp+=5;
			if ((flag++) < 100 )
				invalidate();
		//}
	}
	
  protected void reDraw() {
  	 Log.d("MY ActivityTwo ", "reDraw !!!");
      // this.invalidate();
  }
  
 
  /*
  public boolean onTouchEvent(MotionEvent event)
  {
  	Log.d("MY onTouchEvent", "onTouchEvent");
	    if(event.getAction() == MotionEvent.ACTION_DOWN)
	    {
	    	//touchX = event.getX();
	    	//touchY = event.getY();
  		//invalidate();		
	    }
	    return true;
  }
  */
//	  public BroadcastReceiver    
  


}

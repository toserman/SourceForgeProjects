package com.example.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;

public class DrawChart extends View {
	
	  Paint p = new Paint();
	  ChartEngine chart = new ChartEngine();
	  float touchX = 0; // FOR DEBUG
	  float touchY = 0; // FOR DEBUG
  
		public int temp;
		 int flag = 0; 
		 static int first = 0;

	  
	  //public DrawChart(Context context) {		    	
    //super(context);
	  //  p = new Paint();
	  //  chart = new ChartEngine(context);
	 //  chart.getDisplaySize(ActivityTwo.this,getApplicationContext());
	//invalidate();	  
	  
  	//}
   public DrawChart(Context context, AttributeSet attrs) {	   
      super(context, attrs);      
      Log.e("MY DrawChart ", "Constructor !!!");
      ChartEngine.getDisplaySize(context);
  }
  
   @Override
  protected void onDraw(Canvas canvas) {
  	 Log.d("MY DrawChart ", "onDraw !!!");
  //	 Log.d("MY OnDraw ", "canvas.getHeight() = " + canvas.getHeight() 
  	//		 			+ "canvas.getWidth()= " + canvas.getWidth());

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
  	 if(first !=0)
  	 {
  		 buttonDraw(canvas);
  	 }
  	 
  	 
  	 //chart.testDraw(canvas,touchX,touchY);
  	 //touchX+=5;
  	 //touchY+=5;
  	 
  	 
  	// for(int i = 60;i > 30; i-- )
  	// {
  	//	 chart.channel2rectDraw(canvas,1,i);
  	//	 Log.d("MY ActivityTwo: ","i = " + Integer.toString(i)); 
  	//	 invalidate();
  	// }
  	
  	 //if(touchY < 200)
  		// invalidate();
  	 

  	//  canvas.drawColor(Color.rgb(0x1c,0x1c,0x1c));
  	 // brush preferences
        //  p.setColor(Color.WHITE);
          // ������� ����� = 10
         // p.setStrokeWidth(10);
          //canvas.drawLine(2 ,788,2,2,p);		           
          //invalidate();
  }

   public void forbuttonDraw (int a)
   {
	   Log.d("MY DrawChart ","forbuttonDraw befor invalidate");
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
  
//	  public BroadcastReceiver    
  


}

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

public class DrawChart extends View {
	
	  Paint p = new Paint();
	  ChartEngine chart = new ChartEngine();
	  float touchX = 0; // FOR DEBUG
	  float touchY = 0; // FOR DEBUG
  
	  //public DrawChart(Context context) {		    	
    //super(context);
	  //  p = new Paint();
	  //  chart = new ChartEngine(context);
	    //ChartEngine.getDisplaySize(ActivityTwo.this);
	  //  chart.getDisplaySize(ActivityTwo.this,getApplicationContext());
	    	
	//invalidate();	  
	  
  	//}
   public DrawChart(Context context, AttributeSet attrs) {	   
      super(context, attrs);      
      Log.e("MY DrawChart ", "Constructor !!!");   
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
  	 chart.testDraw(canvas,touchX,touchY);
  	 touchX+=5;
  	 touchY+=5;
  	// if (touchX < canvas.getWidth())
  		// invalidate();
  	 
  	// for(int i = 60;i > 30; i-- )
  	// {
  	//	 chart.channel2rectDraw(canvas,1,i);
  	//	 Log.d("MY ActivityTwo: ","i = " + Integer.toString(i)); 
  	//	 invalidate();
  	// }
  	 if(touchY < 200)
  		 invalidate();
  	 

  	//  canvas.drawColor(Color.rgb(0x1c,0x1c,0x1c));
  	 // brush preferences
        //  p.setColor(Color.WHITE);
          // ������� ����� = 10
         // p.setStrokeWidth(10);
          //canvas.drawLine(2 ,788,2,2,p);		           
          //invalidate();
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
  		invalidate();		
	    }
	    return true;
  }
  
//	  public BroadcastReceiver    
  


}

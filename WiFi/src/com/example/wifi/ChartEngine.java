package com.example.wifi;

import java.sql.Struct;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Toast;



public class ChartEngine extends View {
	public static int width;//Display size values
	public static int height; //width = 480 height = 800//690 end // MY HTC 480/690
	final int START_X_AXISX = 50;//pixel
	final int START_X_AXISY = 60;
	int evalX_px = 0; //Size of segment X axis in pixels
	
	public class ChannelCoord {
		int x,y,num;	
	}
	
	ChannelCoord [] ch_coord ;
	
	
	Paint p;
	
	public ChartEngine(Context context)
	{
		super(context);
		p = new Paint();
		ch_coord = new ChannelCoord[15];

	//	ch_coord[0].x = START_X_AXISX + 25;
		//ch_coord[0].y = START_X_AXISY;
		//ch_coord[0].num = 1;
	}
	
	public static void getDisplaySize(Activity activity,Context context) {
		Display display = activity.getWindowManager().getDefaultDisplay();
	   	Point size = new Point();
	   	display.getSize(size);
	   	width = size.x;
	   	height = size.y - 110;	   	    
	   	Log.d("MY ChartEngine: Display: ", "width = " + Integer.toString(width)
	   			+ " height = " + Integer.toString(height));
	   	Toast.makeText(context, "Display size width = " + Integer.toString(width) +
	   			" height = " + Integer.toString(height),	Toast.LENGTH_LONG).show();
	} 
	
	private void drawSegmentsAxisX(Canvas canvas)
	{
		
		int count = 1;
		int firstch = 1;
		int lastch = 15;
				
	    /*Calculate number of segments for axis*/		
        while (firstch < lastch )
        {
        	firstch++;
        	count++;
        }
    	Log.d("MY ChartEngine: drawSegmentsAxisX: ", "count = " + count);
		      
        evalX_px = ((width-20) - START_X_AXISX) / lastch;//Evaluate size of segment in pixels    
    	//Log.d("MY ChartEngine: drawSegmentsAxisX: ", "eval_px = " + evalX_px);
        //evalX_px = 25
        /*Draw segments on axis X*/
       
        int k = 1;
        //canvas.drawText(Integer.toString(k),START_X_AXISX + evalX_px*2 - ((k >9)?5:3),
		//		(height - 70) + 18,p);		

 		//canvas.drawLine(START_X_AXISX + evalX_px*2 , (height - 70) + 5, 
			//	START_X_AXISX + evalX_px*2, (height - 70),p);
 		k++;
 		ch_coord[0].x = START_X_AXISX + 25;
		ch_coord[0].y = START_X_AXISY;
		ch_coord[0].num = 1;
   //   canvas.drawLine(ch_coord[0].x ,ch_coord[0].y + 5,ch_coord[0].x, ch_coord[0].y,p);
 	 	
 		count = 15;
 		while(k < (count+1))
 		{
 			canvas.drawLine(START_X_AXISX + evalX_px*(k + 1) , (height - 70) + 5, 
					START_X_AXISX + evalX_px*(k + 1), (height - 70),p); 				
 	
 			canvas.drawText(Integer.toString(k),START_X_AXISX + evalX_px*(k + 1) - ((k >9)?5:3),
					(height - 70) + 18,p);
 			k++;
 		}
        
        /*
        for(int k = 1; k < (count+1); k++)
        {  	
        	if (k == 1)
        	{
        		canvas.drawLine(START_X_AXISX + evalX_px*2 , (height - 70) + 5, 
        					START_X_AXISX + evalX_px*2, (height - 70),p);
        
        		canvas.drawText(Integer.toString(k),START_X_AXISX + evalX_px*2 - ((k >9)?5:3),
        						(height - 70) + 18,p);
        		k++;
        	} else {
         		canvas.drawLine(START_X_AXISX + evalX_px*k , (height - 70) + 5, 
    					START_X_AXISX + evalX_px*k, (height - 70),p);
    
    		canvas.drawText(Integer.toString(k),START_X_AXISX + evalX_px*k - ((k >9)?5:3),
    						(height - 70) + 18,p); 		
        	}
        		
        //	chnum++;     
        }*/
        
	}
	private void drawSegmentsAxisY(Canvas canvas,int startval,int endval,int step)
	{	
	    int rssilevel = startval;
	    int count = 1;
	    int evalY_px = 0;
	    
	    /*Calculate number of segments for axis*/
	    while (startval < endval )
	    {
	    	startval+=step;
	    	count++;
	    }
	    
		evalY_px = ((height - 70) - START_X_AXISY) / count;//Evaluate size of segment in pixels      
		for(int m = 1; m < (count+1); m++)
	    {
	    	canvas.drawLine(START_X_AXISY,(height - 70) - evalY_px*m , 
	    					START_X_AXISY - 5 ,(height - 70) - evalY_px*m,p);        	
	    	if(rssilevel%10 != 0) p.setTextSize(10);
	    	canvas.drawText(Integer.toString(rssilevel),START_X_AXISX - 17, (height - 70) - evalY_px*m + 4,p);
	    	p.setTextSize(12);
	    	rssilevel+=step;
	    	/*Draw Y GRID*/
	    	p.setColor(Color.DKGRAY);
	    	canvas.drawLine(START_X_AXISY,(height - 70) - evalY_px*m , 
	    					(width-20),(height - 70)-evalY_px*m,p);
	    	p.setColor(Color.WHITE);
	    }   	
	}
	private void setName(Canvas canvas,int textsize,int rotate_angle, int x,int y,String axis_name)
	{	
		Rect rect = new Rect();
		//int x = START_X_AXISY - 50;
	    //int y = ((height - 60) - 30)/4;	    
        
		p.setTextSize(textsize);
		canvas.translate(x, y);
    	    canvas.rotate(rotate_angle, x + rect.exactCenterX(),y + rect.exactCenterY());
	    //inp_paint.setStyle(Paint.Style.FILL);
	    canvas.drawText(axis_name, x, y,p);	   
	    canvas.restore();
		p.setTextSize(12);
	}
	public void drawAxisXY(Canvas canvas) {
		canvas.drawColor(Color.rgb(0x1c,0x1c,0x1c));//Set canvas color
		p.setColor(Color.WHITE);//Set brush color
        
        canvas.drawLine(START_X_AXISX,(height - 70),(width-20),(height - 70),p);//Draw axis X
        canvas.drawLine(START_X_AXISY,(height - 60),START_X_AXISY,30,p);//Draw axis Y
        
        drawSegmentsAxisX(canvas);
        drawSegmentsAxisY(canvas,-90,-30,5);
       // setnameAxisX(canvas,"WIFI channels");
        setName(canvas,15,-90,START_X_AXISY - 50,(height - 90)/4,"RSSI Levels");
        setName(canvas,15,0,100,330,"WIFI Channels");
        setName(canvas,20,0,100,70,"Channel graph");        
        
       // setnameAxisY(canvas,15,0,40,100,"WIFI CHANNELS");
	}
	
	public void testDraw (Canvas canvas,float x,float y)
	{
				
		//Log.d("MY ChartEngine: testDraw: ", "incr = " + Integer.toString(incr));			
		canvas.drawRect(START_X_AXISY + 100,(height - 70),START_X_AXISY + 150,(height - 70 + 40 - y),p);
		Paint p = new Paint();
		p.setARGB(0xA9,0xF7,0xE8,0x36);
		
		channel2rectDraw(canvas,1);
		
/*
		Rect rect = new Rect(START_X_AXISY, (height - 70), START_X_AXISY + 75,(height - 300));
		
	 	// fill
    	p.setStyle(Paint.Style.FILL);        	
    	p.setColor(Color.parseColor("#99F7E836"));
        canvas.drawRect(rect, p);
        // border
	    p.setStyle(Paint.Style.STROKE);
		//p.setColor(Color.MAGENTA); 
		p.setColor(Color.parseColor("#F7E8FF"));
		p.setStrokeWidth(2);
		canvas.drawRect(rect, p);
	*/	
		Rect rect1 = new Rect(START_X_AXISY + 30, (height - 70), START_X_AXISY + 90,(height - 200));
		
	 	// fill
    	p.setStyle(Paint.Style.FILL);        	
    	p.setColor(Color.parseColor("#99F7FFF6"));
        canvas.drawRect(rect1, p);
        // border
	    p.setStyle(Paint.Style.STROKE);
		//p.setColor(Color.MAGENTA); 
		p.setColor(Color.parseColor("#F7E8FF"));
		p.setStrokeWidth(2);
		canvas.drawRect(rect1, p);
		
		p.reset();
	}
	protected void channel2rectDraw(Canvas canvas,int channel)
	{
		Paint p = new Paint();
		p.setARGB(0xA9,0xF7,0xE8,0x36);
		//Channel 1(2,3 channel)
		Rect rect = new Rect(START_X_AXISY, (height - 70), START_X_AXISY + 3*evalX_px,(height - 300));
		
	 	// fill
    	p.setStyle(Paint.Style.FILL);        	
    	p.setColor(Color.parseColor("#99F7E836"));
        canvas.drawRect(rect, p);
        // border
	    p.setStyle(Paint.Style.STROKE);
		//p.setColor(Color.MAGENTA); 
		p.setColor(Color.parseColor("#F7E8FF"));
		p.setStrokeWidth(2);
		canvas.drawRect(rect, p);
	}
}

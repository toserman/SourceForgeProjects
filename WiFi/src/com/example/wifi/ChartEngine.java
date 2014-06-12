package com.example.wifi;


import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class ChartEngine {
	public static int width;//Display size values
	public static int height; //width = 480 height = 800//690 end // MY HTC 480/690
	final int START_X_AXISX = 50;//pixel
	final int START_X_AXISY = 60;
	final int START_Y_AXISXY = 71;
	final int RSSI_START_AXISXY = -90;
	final int RSSI_END_AXISXY = -30;
	int evalX_px = 0; //Size of segment X axis in pixels
	int evalY_px = 0; //Size of segment Y axis in pixels
	int evalRSSI_px = 0; //Size of value RSSI in pixels
	public class ChannelCoord {
		public int x;
		public int y;
		public int num ;
	}		
	
	static int frame_ready = 1;
	
	
	ChannelCoord [] ch_coord = new ChannelCoord[15];
	Paint p;
	
	public int y;

	public ChartEngine()
	{
		//super(context);
		p = new Paint();
		//ch_coord = new ChannelCoord[15];
	
		for(int i = 0; i < ch_coord.length ;i++)
		{
			ch_coord[i] = new ChannelCoord();
		}
	}
	
	public static void getDisplaySize(Context context) {
		DisplayMetrics metrics = new DisplayMetrics();
		((WindowManager)context.getSystemService("window")).getDefaultDisplay().getMetrics(metrics);
		Log.d("MY ChartEngine: metrics: ", " metrics.heightPixels = " + Integer.toString( metrics.heightPixels)
	   			+ " metrics.widthPixels = " + Integer.toString(metrics.widthPixels));
		ChartEngine.width = metrics.widthPixels;
		ChartEngine.height = metrics.heightPixels - 110;
		//Display display = activity.getWindowManager().getDefaultDisplay();
		//Display display = ActivityTwo.getWindowManager().getDefaultDisplay();
	   	//Point size = new Point();
	   	//display.getSize(size);
	   //	width = size.x;
	   	//height = size.y - 110;	   	    
	   	//Log.d("MY ChartEngine: Display: ", "width = " + Integer.toString(width)
	   		//	+ " height = " + Integer.toString(height));
	   	
	   	//Toast.makeText(context, "Display size width = " + Integer.toString(width) +
	   		//	" height = " + Integer.toString(height),	Toast.LENGTH_LONG).show();
	} 
	
	private void drawSegmentsAxisX(Canvas canvas)
	{
		int k = 1;
		int lastch = 18;//for 14 WIFI channels
		int chnum = 13;
	
		/*Evaluate size of segment in pixels*/
		evalX_px = ((width-20) - START_X_AXISX) / lastch;    
		
        /*Draw segments on axis X*/
        k++;//1st channel start from second position
 		ch_coord[1].x = START_X_AXISX + 2*evalX_px;
		ch_coord[1].y = (height - START_Y_AXISXY);
		ch_coord[1].num = 1;
        canvas.drawLine(ch_coord[1].x ,ch_coord[1].y + 5,ch_coord[1].x, ch_coord[1].y,p);
        canvas.drawText(Integer.toString(ch_coord[1].num),ch_coord[1].x - ((ch_coord[1].num >9)?5:3),
						ch_coord[1].y + 18,p);
 	 	
        chnum = 13;
 		while(k <= chnum)
 		{
 	 		ch_coord[k].x = START_X_AXISX + evalX_px*(k+1);
 			ch_coord[k].y = (height - START_Y_AXISXY);
 			ch_coord[k].num = k;
 			canvas.drawLine(ch_coord[k].x ,ch_coord[k].y + 5,ch_coord[k].x, ch_coord[k].y,p);
 			
 			canvas.drawText(Integer.toString(ch_coord[k].num),ch_coord[k].x - ((ch_coord[k].num >9)?5:3),
					ch_coord[k].y + 18,p);
 			//canvas.drawLine(START_X_AXISX + evalX_px*(k + 1) , (height - 70) + 5, 
				//	START_X_AXISX + evalX_px*(k + 1), (height - 70),p); 		 	
 			//canvas.drawText(Integer.toString(k),START_X_AXISX + evalX_px*(k + 1) - ((k >9)?5:3),
				//	(height - 70) + 18,p);
 			k++;
 		}
 		k++;//Just for skip 1 space
 		ch_coord[14].x = START_X_AXISX + evalX_px*(k + 1);
		ch_coord[14].y = (height - START_Y_AXISXY);
		ch_coord[14].num = 14;
        canvas.drawLine(ch_coord[14].x ,ch_coord[14].y + 5,ch_coord[14].x, ch_coord[14].y,p);
        canvas.drawText(Integer.toString(ch_coord[14].num),ch_coord[14].x - ((ch_coord[14].num >9)?5:3),
						ch_coord[14].y + 18,p);               
        
	}
	private void drawSegmentsAxisY(Canvas canvas)
	{	
		int startval = RSSI_START_AXISXY ;
		int endval = RSSI_END_AXISXY;
		int step = 5;
	    int rssilevel = startval;
	    int count = 1;	    
	    
	    /*Calculate number of segments for axis*/
	    while (startval < endval )
	    {
	    	startval+=step;
	    	count++;
	    }
	    
		evalY_px = ((height - 70) - (START_X_AXISY - 30)) / count;//Evaluate size of segment in pixels
		evalRSSI_px = evalY_px/step;//Just for RSSI values
		//Log.e("MY drawSegmentsAxisY", "evalY_px = " + Integer.toString(evalY_px));
		for(int m = 1; m < (count+1); m++)
	    {
	    	canvas.drawLine(START_X_AXISY,(height - 70) - evalY_px*m , 
	    					START_X_AXISY - 5 ,(height - 70) - evalY_px*m,p);        	
	    	if(rssilevel%10 != 0)
	    		p.setTextSize(10);
	    	canvas.drawText(Integer.toString(rssilevel),START_X_AXISX - 17, (height - 70) - evalY_px*m + 4,p);
	    	p.setTextSize(12);
	    	rssilevel+=step;
	    	/*Draw Y GRID*/
	    	p.setColor(Color.DKGRAY);
	    	canvas.drawLine(START_X_AXISY,(height - 70) - evalY_px*m ,(width-20),(height - 70)-evalY_px*m,p);
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
       	Log.e("MY ChartEngine: drawAxisXY: ", "width = " + Integer.toString(width)	+ " height = " + Integer.toString(height));
        
        drawSegmentsAxisX(canvas);
        drawSegmentsAxisY(canvas);
       	
       // setnameAxisX(canvas,"WIFI channels");
        setName(canvas,15,-90,START_X_AXISY - 50,(height - 90)/4,"RSSI Levels");
        setName(canvas,15,0,100,330,"WIFI Channels");
        setName(canvas,20,0,100,70,"Channel graph");
       // setnameAxisY(canvas,15,0,40,100,"WIFI CHANNELS");
	}
		
	public void startDraw (Canvas canvas,int test)
	{
		Log.d("MY ChartEngine " + "startDraw temp = ", Integer.toString(test));
	//	final int START_X_AXISX = 50;//pixel
		//final int START_X_AXISY = 60;
		//final int START_Y_AXISXY = 71;
		
		//int height = 800;
		//Paint p = new Paint();		
		//p.setARGB(0xA9,0xF7,0xE8,0x36);					
		//	canvas.drawRect(START_X_AXISY + 100,(height - 70),START_X_AXISY + 150,(height - 70 - test),p);
		channel2rectDraw(canvas,1,67,test);
			if (test > 40)
				frame_ready = 0;//Stop to draw
			
	}
	public void testDraw (Canvas canvas,float x,float y)
	{
				
		//Log.d("MY ChartEngine: ","testDraw");			
		//canvas.drawRect(START_X_AXISY + 100,(height - 70),START_X_AXISY + 150,(height - 70 + 40 - y),p);
		//Paint p = new Paint();
		//p.setARGB(0xA9,0xF7,0xE8,0x36);
		
		//channel2rectDraw(canvas,1,67);
		
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
		/*
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
		*/
		//p.reset();
	}
	protected void channel2rectDraw(Canvas canvas,int channel,int rssi , int test)
	{
		Log.d("MY ChartEngine ","channel2rectDraw");			
		//canvas.drawRect(START_X_AXISY + 100,(height - 70),START_X_AXISY + 150,(height - 70 + 40 - y),p);

	//	Rect rect = new Rect(START_X_AXISY,(height - START_Y_AXISXY - 300),
		//					START_X_AXISY + 3*evalX_px,(height - START_Y_AXISXY));
	
		// -90 = -10 // 91 = -9 // 92 = -8 // 93 = -7
		// -94 = -1 // -93 = -2 // -92 = -3 //-91 = -4 // -90 = -5
		//evalY_px = 45 / 5 = 9
		//95 - 80 = 
	
		//Channel 1
	//	Random r = new Random();
		//Rect rect = new Rect(START_X_AXISY,height -100 ,START_X_AXISY + 150,(height));		
		//rectPaint(canvas, rect);
//
//		Rect rect = new Rect(START_X_AXISY+1,(height - START_Y_AXISXY + (rssi + RSSI_START_AXISXY -5)*(evalRSSI_px)),
//								ch_coord[1].x,(height - START_Y_AXISXY));		
//		rectPaint(canvas, rect);
//		
		//Paint p = new Paint();		
		//p.setARGB(0xA9,0xF7,0xE8,0x36);
			
		
	   	Log.e("MY ChartEngine: channel2rectDraw: ", "width = " + Integer.toString(width)	+ " height = " + Integer.toString(height));
		
	   	canvas.drawRect(START_X_AXISY + 100,(height - 70),START_X_AXISY + 150,(height - 70 - test),p);
	    //canvas.drawLine(START_X_AXISX,(height - 70),(width-20),(height - 70),p);//Draw axis X
	    
	   	canvas.drawLine(START_X_AXISX,(height - 70),(START_X_AXISX + test ),(height - 70),p);//Draw axis X
	   // canvas.drawLine(START_X_AXISX,(ChartEngine.height),(START_X_AXISX + test ),(ChartEngine.height),p);//Draw axis X
	    
		//canvas.drawRect(START_X_AXISY ,height ,START_X_AXISY + 150,height -10 ,p);
		
	//	ch_coord[1].x = 150;
//		canvas.drawRect(START_X_AXISY + 1,(height  + (rssi + RSSI_START_AXISXY -5)*(evalRSSI_px) ),ch_coord[1].x,(height - START_Y_AXISXY - test),p);
			
//			Rect rect1 = new Rect(ch_coord[4].x,(height - START_Y_AXISXY + (r.nextInt(70) + RSSI_START_AXISXY -5)*(evalRSSI_px)),
//					ch_coord[8].x,(height - START_Y_AXISXY));		
//		 	rectPaint(canvas, rect1);

	}
	protected void rectPaint(Canvas canvas,Rect rect)
	{
				// fill
				Paint p = new Paint();
				Random r = new Random();
				//p.setARGB(0xA9,0xF7,0xE8,0x36);
				p.setStyle(Paint.Style.FILL);        	
		    	//p.setColor(Color.parseColor("#99F7E836"));
		    	p.setColor(Color.argb(169/*0xA9*/,r.nextInt(255),r.nextInt(255),r.nextInt(255)));		    	    
		        canvas.drawRect(rect, p);
		        
		        // border
			    p.setStyle(Paint.Style.STROKE);				 
				//p.setColor(Color.parseColor("#F7E8FF"));
			    p.setColor(p.getColor());
				p.setStrokeWidth(2);
				canvas.drawRect(rect, p);
				p.reset();
				
	}

}

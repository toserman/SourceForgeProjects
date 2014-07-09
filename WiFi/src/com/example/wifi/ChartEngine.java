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
	final int OFFSET_X_AXISX = 50;//pixel
	final int OFFSET_Y_AXISX = 70;
	
	final int OFFSET_X_AXISY = 60;	
	final int OFFSET_Y_AXISY = 60;
	
	final int RSSI_START_AXISXY = -90;
	final int RSSI_END_AXISXY = -30;
	final int RSSI_STEP = 5;
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

	public ChartEngine(Context context)
	{
		//super(context);
		p = new Paint();
		//ch_coord = new ChannelCoord[15];
		getDisplaySize(context);
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
		width = metrics.widthPixels;
		height = metrics.heightPixels - 110;
   	    
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
		evalX_px = ((width-20) - OFFSET_X_AXISX) / lastch;    
		
        /*Draw segments on axis X*/
        k++;//1st channel start from second position
 		ch_coord[1].x = OFFSET_X_AXISX + 2*evalX_px;
		ch_coord[1].y = (height - OFFSET_Y_AXISX);
		ch_coord[1].num = 1;
        canvas.drawLine(ch_coord[1].x ,ch_coord[1].y + 5,ch_coord[1].x, ch_coord[1].y,p);
        canvas.drawText(Integer.toString(ch_coord[1].num),ch_coord[1].x - ((ch_coord[1].num >9)?5:3),
						ch_coord[1].y + 18,p);
 	 	
        chnum = 13;
 		while(k <= chnum)
 		{
 	 		ch_coord[k].x = OFFSET_X_AXISX + evalX_px*(k+1);
 			ch_coord[k].y = (height - OFFSET_Y_AXISX);
 			ch_coord[k].num = k;
 			canvas.drawLine(ch_coord[k].x ,ch_coord[k].y + 5,ch_coord[k].x, ch_coord[k].y,p);
 			
 			canvas.drawText(Integer.toString(ch_coord[k].num),ch_coord[k].x - ((ch_coord[k].num >9)?5:3),
					ch_coord[k].y + 18,p);
 			//canvas.drawLine(OFFSET_X_AXISX + evalX_px*(k + 1) , (height - 70) + 5, 
				//	OFFSET_X_AXISX + evalX_px*(k + 1), (height - 70),p); 		 	
 			//canvas.drawText(Integer.toString(k),OFFSET_X_AXISX + evalX_px*(k + 1) - ((k >9)?5:3),
				//	(height - 70) + 18,p);
 			k++;
 		}
 		k++;//Just for skip 1 space
 		ch_coord[14].x = OFFSET_X_AXISX + evalX_px*(k + 1);
		ch_coord[14].y = (height - OFFSET_Y_AXISX);
		ch_coord[14].num = 14;
        canvas.drawLine(ch_coord[14].x ,ch_coord[14].y + 5,ch_coord[14].x, ch_coord[14].y,p);
        canvas.drawText(Integer.toString(ch_coord[14].num),ch_coord[14].x - ((ch_coord[14].num >9)?5:3),
						ch_coord[14].y + 18,p);               
        
	}
	private void drawSegmentsAxisY(Canvas canvas)
	{	
		int startval = RSSI_START_AXISXY ;
		int endval = RSSI_END_AXISXY;
		int step = RSSI_STEP;
	    int rssilevel = startval;
	    int count = 1;	    
	    
	    /*Calculate number of segments for axis*/
	    while (startval < endval )
	    {
	    	startval+=step;
	    	count++;
	    }
	    		
		evalY_px = ((height - 70) - (OFFSET_X_AXISY - 30)) / count;//Evaluate size of segment in pixels
		evalRSSI_px = evalY_px/step;//Just for RSSI values
		//Log.e("MY drawSegmentsAxisY", "evalY_px = " + Integer.toString(evalY_px));
		for(int m = 1; m < (count+1); m++)
	    {
	    	canvas.drawLine(OFFSET_X_AXISY,(height - 70) - evalY_px*m , 
	    					OFFSET_X_AXISY - 5 ,(height - 70) - evalY_px*m,p);        	
	    	if(rssilevel%10 != 0)
	    		p.setTextSize(10);
	    	canvas.drawText(Integer.toString(rssilevel),OFFSET_X_AXISX - 17, (height - 70) - evalY_px*m + 4,p);
	    	p.setTextSize(12);
	    	rssilevel+=step;
	    	/*Draw Y GRID*/
	    	p.setColor(Color.DKGRAY);
	    	canvas.drawLine(OFFSET_X_AXISY,(height - 70) - evalY_px*m ,(width-20),(height - 70)-evalY_px*m,p);
	    	p.setColor(Color.WHITE);
	    }   	
	}
	private void setName(Canvas canvas,int textsize,int rotate_angle, int x,int y,String axis_name)
	{	
		Rect rect = new Rect();

		p.setTextSize(textsize);
		canvas.translate(x, y);
    	    canvas.rotate(rotate_angle, x + rect.exactCenterX(),y + rect.exactCenterY());
	    canvas.drawText(axis_name, x, y,p);	   
	    canvas.restore();
		p.setTextSize(12);
	}
	public void drawAxisXY(Canvas canvas) {
		canvas.drawColor(Color.rgb(0x1c,0x1c,0x1c));//Set canvas color
		p.setColor(Color.WHITE);//Set brush color

		canvas.drawLine(OFFSET_X_AXISX,(height - OFFSET_Y_AXISX),(width-20),(height - OFFSET_Y_AXISX),p);//Draw axis X
        canvas.drawLine(OFFSET_X_AXISY,(height - OFFSET_Y_AXISY),OFFSET_X_AXISY,30,p);//Draw axis Y
       	Log.e("MY ChartEngine: drawAxisXY: ", "width = " + Integer.toString(width) + 
       									" height = " + Integer.toString(height));
        
        drawSegmentsAxisX(canvas);
        drawSegmentsAxisY(canvas);
       	
       // setnameAxisX(canvas,"WIFI channels");
        setName(canvas,15,-90,OFFSET_X_AXISY - 50,(height - 90)/4,"RSSI Levels");
        setName(canvas,15,0,100,330,"WIFI Channels");
        setName(canvas,20,0,100,70,"Channel graph");
       // setnameAxisY(canvas,15,0,40,100,"WIFI CHANNELS");
	}
		
	public void startDraw (Canvas canvas,int test)
	{
		Log.d("MY ChartEngine " + "startDraw temp = ", Integer.toString(test));
	//	final int OFFSET_X_AXISX = 50;//pixel
		//final int OFFSET_X_AXISY = 60;
		//final int OFFSET_Y_AXISX = 71;
		
		//int height = 800;
		//Paint p = new Paint();		
		//p.setARGB(0xA9,0xF7,0xE8,0x36);					
		//	canvas.drawRect(OFFSET_X_AXISY + 100,(height - 70),OFFSET_X_AXISY + 150,(height - 70 - test),p);
		
		channel2rectDraw(canvas,2412,-67,test);
		channel2rectDraw(canvas,2417,-67,test);
			if (test >= getCoordRSSILevel(-67))
				frame_ready = 0;//Stop to draw			
	}
	public void testDraw (Canvas canvas,float x,float y)
	{
				
		//Log.d("MY ChartEngine: ","testDraw");			
		//canvas.drawRect(OFFSET_X_AXISY + 100,(height - 70),OFFSET_X_AXISY + 150,(height - 70 + 40 - y),p);
		//Paint p = new Paint();
		//p.setARGB(0xA9,0xF7,0xE8,0x36);
		
		//channel2rectDraw(canvas,1,67);
		
/*
		Rect rect = new Rect(OFFSET_X_AXISY, (height - 70), OFFSET_X_AXISY + 75,(height - 300));
		
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
		Rect rect1 = new Rect(OFFSET_X_AXISY + 30, (height - 70), OFFSET_X_AXISY + 90,(height - 200));
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
		//public static int[] arr_freq = {0,2412,2417,2422,2427,2432,2437,2442,2447,2452,2457,2462,2467,2472,2484}; 
		
		int coord1 = 0,coord2 = 0;
		int coord3 = 0,coord4 = 0;
		
	//	Rect rect = new Rect(OFFSET_X_AXISY,(height - OFFSET_Y_AXISX - 300),
		//					OFFSET_X_AXISY + 3*evalX_px,(height - OFFSET_Y_AXISX));
	
		// -90 = -10 // 91 = -9 // 92 = -8 // 93 = -7
		// -94 = -1 // -93 = -2 // -92 = -3 //-91 = -4 // -90 = -5
		//evalY_px = 45 / 5 = 9
		//95 - 80 = 
	
		//Channel 1
	//	Random r = new Random();
		//Rect rect = new Rect(OFFSET_X_AXISY,height -100 ,OFFSET_X_AXISY + 150,(height));		
		//rectPaint(canvas, rect);
//
//		Rect rect = new Rect(OFFSET_X_AXISY+1,(height - OFFSET_Y_AXISX + (rssi + RSSI_START_AXISXY -5)*(evalRSSI_px)),
//								ch_coord[1].x,(height - OFFSET_Y_AXISX));		
//		rectPaint(canvas, rect);
//		
		//Paint p = new Paint();		
		
	   	Log.e("MY ChartEngine: channel2rectDraw: ", "evalY_px = " + evalY_px + " evalRSSI_px = " + evalRSSI_px);
//		Log.e("MY ChartEngine: channel2rectDraw: ", "canvas.getWidth() = " + Integer.toString(canvas.getWidth())
//				+ " canvas.getHeight() = " + Integer.toString(canvas.getHeight()));
		
//	   	canvas.drawRect(OFFSET_X_AXISY + 100,(canvas.getHeight() - 70 ),
//	   					OFFSET_X_AXISY + 150,(canvas.getHeight() - 70 - test),p);
		if (channel == CustomScanListAdapter.arr_freq[1])
		{	
			p.setARGB(0xA9,0xF7,0xE8,0x36);//Yellow			

			coord1 = OFFSET_X_AXISY;
			coord2 = OFFSET_X_AXISY + 85;		
			canvas.drawRect(coord1,(canvas.getHeight() - 70 ),
					coord2,(canvas.getHeight() - 70 - test),p);
		}
	   	   	
	   	if (channel == CustomScanListAdapter.arr_freq[2])
		{	
	   		p.setARGB(0xA9,0xCC,0x00,0x00);			

			coord3 = OFFSET_X_AXISY + 10;
			coord4 = OFFSET_X_AXISY + 20 + 85;		
			canvas.drawRect(coord3,(canvas.getHeight() - 70 ),
					coord4,(canvas.getHeight() - 70 - test),p);
		}
	   	canvas.drawLine(coord1,(canvas.getHeight() - OFFSET_Y_AXISX - getCoordRSSILevel(rssi)),
			coord2,(canvas.getHeight() - OFFSET_Y_AXISX - getCoordRSSILevel(rssi)),p);
	}
	protected int getCoordRSSILevel(int rssi_level) {
		int coord_rssi = RSSI_START_AXISXY - RSSI_STEP - rssi_level;
//		Log.e("MY ChartEngine: getRangeRSSI: ","coord_rssi = " + Integer.toString(coord_rssi));
		return Math.abs((evalY_px/RSSI_STEP)*coord_rssi);
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

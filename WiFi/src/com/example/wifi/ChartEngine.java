package com.example.wifi;


import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
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

public class ChartEngine  {
	public static int width;//Display size values
	public static int height; //width = 480 height = 800//690 end // MY HTC 480/690
	final int OFFSET_X_AXISX = 50;//pixel
	final int OFFSET_Y_AXISX = 70;
	
	final int OFFSET_X_AXISY = 60;	
	final int OFFSET_Y_AXISY = 60;
	
	final int RSSI_START_AXISXY = -90;
	final int RSSI_END_AXISXY = -30;
	final int RSSI_STEP = 5;
	final int NUMBERS_OF_CHANNEL = 15;
	int evalX_px = 0; //Size of segment X axis in pixels
	int evalY_px = 0; //Size of segment Y axis in pixels
	int evalRSSI_px = 0; //Size of value RSSI in pixels
	int staRectWidth = 0; //Width for STA rectangle on axis X
	public class ChannelCoord {
		public int x;
		public int y;
		public int num ;
	}		
	public class RectSTACoord {
		public int x1;
		public int x2;
	}		
	
	static int frame_ready = 1;
	
	ChannelCoord [] ch_coord = new ChannelCoord[NUMBERS_OF_CHANNEL];
	RectSTACoord [] rect_ch_coord = new RectSTACoord[NUMBERS_OF_CHANNEL];
	
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
			rect_ch_coord[i] = new RectSTACoord();
		}
		
//		myBitmap = Bitmap.createBitmap( (int)canvas.getWidth(), (int)canvas.getHeight(), Config.RGB_565);
//		canvas = new Canvas(myBitmap);
//		canvas.drawBitmap(myBitmap, 0, 0, p);
		
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
			
		// myBitmap = Bitmap.createBitmap( (int)canvas.getWidth(), (int)canvas.getHeight(), Config.RGB_565);
		 //canvas = new Canvas(myBitmap);
		 //canvas.drawBitmap(myBitmap, 0, 0, p);
		
		
		/*Evaluate size of segment in pixels*/
		evalX_px = ((width-20) - OFFSET_X_AXISX) / lastch;  
		/*Calculate width size value for indicate STA rectangle*/
		staRectWidth = calcSTArectWidth(evalX_px);
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
 			//Save coord values on X axis for display STA rectangle
 			rect_ch_coord[k].x1 = ch_coord[k].x -staRectWidth/2;
 			rect_ch_coord[k].x2 = rect_ch_coord[k].x1 + staRectWidth;
 				
 			canvas.drawLine(ch_coord[k].x ,ch_coord[k].y + 5,ch_coord[k].x, ch_coord[k].y,p); 			
 			canvas.drawText(Integer.toString(ch_coord[k].num),ch_coord[k].x - ((ch_coord[k].num >9)?5:3),
					ch_coord[k].y + 18,p);
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
		//canvas.translate(x, y);		
    	 //   canvas.rotate(rotate_angle, x + rect.exactCenterX(),y + rect.exactCenterY());
    	    //canvas.rotate(rotate_angle, x + canvas.getWidth(),canvas.getHeight() -y);
		   	       	    
	    canvas.drawText(axis_name,x,y,p);
	    canvas.save();
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

        setName(canvas,20,0,canvas.getWidth()/2 - 40,canvas.getHeight() - (canvas.getHeight()-25),"Channel graph");   
        setName(canvas,15,0,canvas.getWidth()/2 - 40,canvas.getHeight() - 30,"WIFI Channels");
//        setName(canvas,15,-90,OFFSET_X_AXISY - 50,(height - 90)/4,"RSSI Levels");
     
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
		
//		channel2rectDraw(canvas,2412,-30,test); //1st ch
//		channel2rectDraw(canvas,2417,-30,test);	//2nd ch
//		channel2rectDraw(canvas,2422,-30,test); //3rd ch
		channel2rectDraw(canvas,2427,-30,test); //4rd ch
//		channel2rectDraw(canvas,2432,-30,test); //5th ch
//		channel2rectDraw(canvas,2437,-30,test); //6th ch
//		channel2rectDraw(canvas,2442,-30,test); //7th ch
		
		
//		if (test >= 65)
			if (test >= getCoordRSSILevel(-30))
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
		//									   1	2	3	4    5     6    7    8    9    10   11  12   13    14
		//public static int[] arr_freq = {0,2412,2417,2422,2427,2432,2437,2442,2447,2452,2457,2462,2467,2472,2484}; 
		
		int coord1 = 0,coord2 = 0;// 1st channel
		int coord3 = 0,coord4 = 0;// 2nd channel
		int coord5 = 0,coord6 = 0;// 3nd channel
		int coord7 = 0,coord8 = 0;// 4th channel
		int coord9 = 0,coord10 = 0;// 5th channel
		int coord11 = 0,coord12 = 0;// 6th channel
		int coord13 = 0,coord14 = 0;// 7th channel
		
		if (channel == CustomScanListAdapter.arr_freq[1])
		{	
			p.setARGB(0x64,0xF7,0xE8,0x36);//Yellow			

			coord1 = OFFSET_X_AXISY;
//			coord2 = OFFSET_X_AXISY + 84;
			coord2 = coord1 + evalX_px*4;
			canvas.drawRect(coord1,(canvas.getHeight() - 70 ),
					coord2,(canvas.getHeight() - 70 - test),p);
			setName(canvas,15,0,(coord2 - 10 - (coord2 - coord1)/2),canvas.getHeight() - 70 - test,"Ch 1");   
		}
	   	   	
	   	if (channel == CustomScanListAdapter.arr_freq[2])
		{	
	   		p.setARGB(0x64,0xCC,0x00,0x00);			

//			coord3 = OFFSET_X_AXISY + 13;
//			coord4 = OFFSET_X_AXISY + 20 + 84;

	   		int temp_ch = CustomScanListAdapter.convertFreqtoChannelNum(channel,CustomScanListAdapter.arr_freq);
		//	coord3 = OFFSET_X_AXISY + evalX_px/2;
	   		coord3 = ch_coord[2].x - (evalX_px*4 +3)/2 ;
			coord4 = coord3 + evalX_px*4 +3  ;
			Log.e("MY ChartEngine: channel2rectDraw: ","evalX_px = " + Integer.toString(evalX_px) +
	   				" temp_ch = " + Integer.toString(temp_ch) + " coord 3 = " + Integer.toString(coord3) +
	   				" coord 4 = " + Integer.toString(coord4));
			Log.e("MY ChartEngine: channel2rectDraw: ", "ch_coord[2].x = " + Integer.toString(ch_coord[2].x)
					+ "ch_coord[2].y = " + Integer.toString(ch_coord[2].y) + "ch_coord[1].num = " + Integer.toString(ch_coord[2].num));
			
			canvas.drawRect(coord3,(canvas.getHeight() - 70 ),
					coord4,(canvas.getHeight() - 70 - test),p);
		       setName(canvas,15,0,(coord4 - 10 - (coord4 - coord3)/2),canvas.getHeight() - 70 - test,"Ch 2");   
		}
	   	
	   	if (channel == CustomScanListAdapter.arr_freq[3])
		{	
	   		p.setARGB(0x64,0xCC,0xBB,0xDD);			
//			coord5 = OFFSET_X_AXISY + 10 + 20;
//			coord6 = OFFSET_X_AXISY + 20*2 + 84 ;
	   		int temp_ch = CustomScanListAdapter.convertFreqtoChannelNum(channel,CustomScanListAdapter.arr_freq);
	   		Log.e("MY ChartEngine: channel2rectDraw: ","evalX_px = " + Integer.toString(evalX_px) +
	   				" temp_ch = " + Integer.toString(temp_ch));
//	   		coord5 = OFFSET_X_AXISY + evalX_px*(temp_ch)/2;
	   		coord5 = ch_coord[3].x - (evalX_px*4 +3)/2 ;
			coord6 = coord5 + evalX_px*4 +3  ;	
			
	   		
			canvas.drawRect(coord5,(canvas.getHeight() - 70 ),
					coord6,(canvas.getHeight() - 70 - test),p);
		       setName(canvas,15,0, (coord6 - 10 - (coord6 - coord5)/2) ,canvas.getHeight() - 70 - test,"Ch 3");   
		}
		if (channel == CustomScanListAdapter.arr_freq[4])
		{	
	   		p.setARGB(0x64,0x64,0xE8,0x0C);	//0ce822	64e80c	

//			coord7 = OFFSET_X_AXISY + 10 + 20 + 22;
//			//coord8 = OFFSET_X_AXISY + 20 + 84 + 20 + 22;
//			coord8 = OFFSET_X_AXISY + 20*3 + 84 ;
//			
			int temp_ch = CustomScanListAdapter.convertFreqtoChannelNum(channel,CustomScanListAdapter.arr_freq);
	   		
			//coord7 = OFFSET_X_AXISY + evalX_px*(temp_ch +1 )/2;
			//coord7 = ch_coord[4].x - staRectWidth/2 ;
//			coord8 = coord7 + staRectWidth  ;
			coord7 = rect_ch_coord[4].x1;
			coord8 = rect_ch_coord[4].x2;	
			Log.e("MY ChartEngine: channel2rectDraw: ","evalX_px = " + Integer.toString(evalX_px) +
	   				" temp_ch = " + Integer.toString(temp_ch) + " coord 7 = " + Integer.toString(coord7) +
	   				" coord 8 = " + Integer.toString(coord8));
			
//			canvas.drawRect(coord7,(canvas.getHeight() - 70 ),coord8,(canvas.getHeight() - 70 - test),p);
			canvas.drawRect(rect_ch_coord[4].x1,(canvas.getHeight() - 70 ),rect_ch_coord[4].x2,(canvas.getHeight() - 70 - test),p);
//		       setName(canvas,15,0, (coord8 - 10 - (coord8 - coord7)/2) ,canvas.getHeight() - 70 - test,"Ch 4");
		       setName(canvas,15,0, ch_coord[4].x ,canvas.getHeight() - 70 - test,"Ch 4");
		}
		if (channel == CustomScanListAdapter.arr_freq[5])
		{	
	   		p.setARGB(0x64,0xE8,0x0C,0x64);	//e80c64

//			coord9 = OFFSET_X_AXISY + 10 + 20 + 20 + 24;
//			coord10 = OFFSET_X_AXISY + 20 + 85 + 20 + 22 + 22 ;	
			int temp_ch = CustomScanListAdapter.convertFreqtoChannelNum(channel,CustomScanListAdapter.arr_freq);

	   		coord9 = OFFSET_X_AXISY + evalX_px*(temp_ch + 2)/2;
			coord10 = coord9 + evalX_px*4 + 3  ;	
			Log.e("MY ChartEngine: channel2rectDraw: ","evalX_px = " + Integer.toString(evalX_px) +
	   				" temp_ch = " + Integer.toString(temp_ch) + " coord 9 = " + Integer.toString(coord9) +
	   				" coord 10 = " + Integer.toString(coord10));
			canvas.drawRect(coord9,(canvas.getHeight() - 70 ),
					coord10,(canvas.getHeight() - 70 - test),p);
		       setName(canvas,15,0, (coord10 - 10 - (coord10 - coord9)/2) ,canvas.getHeight() - 70 - test,"Ch 5");   
		}
		if (channel == CustomScanListAdapter.arr_freq[6])
		{	
	   		p.setARGB(0x64,0xE8,0x01,0x00);	//e80c64
//			coord11 = OFFSET_X_AXISY + 10 + 20 + 20 + 24 + 24;
//			coord12 = OFFSET_X_AXISY + 20 + 85 + 20 + 22 + 22 + 22 ;
	   		int temp_ch = CustomScanListAdapter.convertFreqtoChannelNum(channel,CustomScanListAdapter.arr_freq);
	   		coord11 = OFFSET_X_AXISY + evalX_px*(temp_ch + 3)/2;
			coord12 = coord11 + evalX_px*4 + 3  ;
			
			canvas.drawRect(coord11,(canvas.getHeight() - 70 ),
					coord12,(canvas.getHeight() - 70 - test),p);
		       setName(canvas,15,0, (coord12 - 10 - (coord12 - coord11)/2) ,canvas.getHeight() - 70 - test,"Ch 6");   
		}
		if (channel == CustomScanListAdapter.arr_freq[7])
		{	
	   		p.setARGB(0x64,0x90,0x0C,0xe8);	//#900ce8
//
//			coord13 = OFFSET_X_AXISY + 10 + 20 + 20 + 24 + 24 + 22;
//			coord14 = OFFSET_X_AXISY + 20 + 85 + 20 + 22 + 22 + 22 +22 ;		
	   		int temp_ch = CustomScanListAdapter.convertFreqtoChannelNum(channel,CustomScanListAdapter.arr_freq);
	   		coord13 = OFFSET_X_AXISY + evalX_px*(temp_ch + 4)/2;
			coord14 = coord13 + evalX_px*4 + 3  ;
			canvas.drawRect(coord13,(canvas.getHeight() - 70 ),
					coord14,(canvas.getHeight() - 70 - test),p);
		       setName(canvas,15,0, (coord14 - 10 - (coord14 - coord13)/2) ,canvas.getHeight() - 70 - test,"Ch 7");   
		}
	   	canvas.drawLine(coord1,(canvas.getHeight() - OFFSET_Y_AXISX - getCoordRSSILevel(rssi)),
			coord2,(canvas.getHeight() - OFFSET_Y_AXISX - getCoordRSSILevel(rssi)),p);
//		canvas.drawLine(coord1,(canvas.getHeight() - OFFSET_Y_AXISX - 65),
//				coord2,(canvas.getHeight() - OFFSET_Y_AXISX - 65),p);
	}
	protected int getCoordRSSILevel(int rssi_level){
		int coord_rssi = RSSI_START_AXISXY - RSSI_STEP - rssi_level;
		Log.e("MY ChartEngine: getRangeRSSI: ","coord_rssi = " + Integer.toString(coord_rssi));
		return Math.abs((evalY_px/RSSI_STEP)*coord_rssi);
	}
	protected int calcSTArectWidth(int x_pixels) {
		return (x_pixels*4 +3);
	}
	/*NOT USED*/
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

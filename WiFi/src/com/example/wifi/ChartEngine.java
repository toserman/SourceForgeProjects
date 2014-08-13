package com.example.wifi;


import java.util.ArrayList;
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
	
	final int RSSI_START_AXISXY = -100;
	final int RSSI_END_AXISXY = -40;
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
		 setcalcAPrectWidth(evalX_px);
		 /*Save for Channel 1 coord values on X axis for display STA rectangle*/
		if ( k == 1)
		{
			rect_ch_coord[k].x1 = OFFSET_X_AXISY;
			rect_ch_coord[k].x2 = rect_ch_coord[k].x1 + staRectWidth - 9;
		}
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

 		 /*Save for Channel 14 values on X axis for display STA rectangle*/
		if ( k == 14)
		{
			rect_ch_coord[k].x1 = OFFSET_X_AXISY + evalX_px*16 - 8 - staRectWidth/2;
			rect_ch_coord[k].x2 = rect_ch_coord[k].x1 + staRectWidth;
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
	    	canvas.drawText(Integer.toString(rssilevel),OFFSET_X_AXISX - 23, (height - 70) - evalY_px*m + 4,p);
	    	p.setTextSize(12);
	    	rssilevel+=step;
	    	/*Draw Y GRID*/
	    	p.setColor(Color.DKGRAY);
	    	canvas.drawLine(OFFSET_X_AXISY,(height - 70) - evalY_px*m ,(width-20),(height - 70)-evalY_px*m,p);
	    	p.setColor(Color.WHITE);
	    }   	
	}
	private void setName(Canvas canvas,int textsize,int rotate_angle, int x,int y,String displ_str)
	{	
		Rect rect = new Rect();		
		p.setTextSize(textsize);
		int width_pix = (int)Math.round(p.measureText(displ_str));
	
//		Log.d("MY setName: ", displ_str + " axis_name.length() = " + Integer.toString(displ_str.length()) + 
//							" width_pix = " + Float.toString(width_pix));
		//canvas.translate(x, y);		
    	 //   canvas.rotate(rotate_angle, x + rect.exactCenterX(),y + rect.exactCenterY());
    	    //canvas.rotate(rotate_angle, x + canvas.getWidth(),canvas.getHeight() -y);
		x = x + staRectWidth/2 - width_pix/2; 
	    canvas.drawText(displ_str,x,y,p);
	    
	    canvas.save();
	    canvas.restore();
		p.setTextSize(12);
	}
	public void drawAxisXY(Canvas canvas) {
		canvas.drawColor(Color.rgb(0x1c,0x1c,0x1c));//Set canvas color
		p.setColor(Color.WHITE);//Set brush color

		canvas.drawLine(OFFSET_X_AXISX,(height - OFFSET_Y_AXISX),(width-20),(height - OFFSET_Y_AXISX),p);//Draw axis X
        canvas.drawLine(OFFSET_X_AXISY,(height - OFFSET_Y_AXISY),OFFSET_X_AXISY,30,p);//Draw axis Y
       	Log.e("MY ChartEngine: drawAxisXY: ", "width = " + Integer.toString(width) + " height = " + Integer.toString(height));
        
        drawSegmentsAxisX(canvas);
        drawSegmentsAxisY(canvas);

        setName(canvas,20,0,canvas.getWidth()/2 - 40,canvas.getHeight() - (canvas.getHeight()-25),"Channel graph");   
        setName(canvas,15,0,canvas.getWidth()/2 - 40,canvas.getHeight() - 30,"WIFI Channels");
     
	}
		
	public void startDraw (Canvas canvas,int test,ArrayList<ScanItem> list_ap)
	{
//		Log.d("MY ChartEngine " + "startDraw temp = ", Integer.toString(test));
		//Display list
//    	for (int i=0; i < list_ap.size();i++)
//    	{
//  		  Log.d("MY TAG ", "i = " + Integer.toString(i) + " " + list_ap.get(i).getBSSID());
  		//drawAP(canvas,list_ap[list_ap.get(i)], test);  		
//    	}
    	
    	  for (ScanItem list_test : list_ap) {
    		  //Log.d("MY TAG ", "BSSID = " + list_test.getBSSID() + " RSSI= " + list_test.getRSSIlevel());
    		  drawAP(canvas,list_test, test);
    	  }
    	
	
////		CustomScanListAdapter.convertFreqtoChannelNum(channel,CustomScanListAdapter.arr_freq);
//		channel2rectDraw(canvas,2412,-20,test); //1st ch
////		channel2rectDraw(canvas,2417,-30,test);	//2nd ch
////		channel2rectDraw(canvas,2422,-30,test); //3rd ch
////		channel2rectDraw(canvas,2427,-30,test); //4rd ch
////		channel2rectDraw(canvas,2432,-30,test); //5th ch
//		channel2rectDraw(canvas,2437,-30,test); //6th ch
//		channel2rectDraw(canvas,2442,-30,test); //7th ch
//		channel2rectDraw(canvas,2462,-30,test); //11th ch
//		channel2rectDraw(canvas,2484,-30,test); //14th ch
			
//		if (test >= 65)
//			if (test >= getCoordRSSILevel(-30))
//				frame_ready = 0;//Stop to draw	

	}

	protected void channel2rectDraw(Canvas canvas,int channel,int rssi , int test)
	{
		Log.d("MY ChartEngine ","channel2rectDraw");
		//									   1	2	3	4    5     6    7    8    9    10   11  12   13    14
		//public static int[] arr_freq = {0,2412,2417,2422,2427,2432,2437,2442,2447,2452,2457,2462,2467,2472,2484}; 
		
		int ch = CustomScanListAdapter.convertFreqtoChannelNum(channel,CustomScanListAdapter.arr_freq);
		switch(ch)
		{
			case 1://need fill
				if(Math.abs(rssi) >= test)
				{
					drawAPrect(canvas,ch,test,"Antonio ",0x30F7E836);
				} else {
					drawAPrect(canvas,ch,Math.abs(rssi),"Antonio ",0x30F7E836);
				}
				break;
			case 2:
				drawAPrect(canvas,ch,test,"Cambium ",0x64CC0000);
				break;
			case 3:
				drawAPrect(canvas,ch,test,"Mario ",0x64CCBBDD);
				break;
			case 4:
				drawAPrect(canvas,ch,test,"Barcelona ",0x6464E80C);
				break;
			case 5:
				drawAPrect(canvas,ch,test,"Fabregas ",0x64E80C64);
				break;
			case 6:
				drawAPrect(canvas,ch,test,"Watch out!!! ",0x64E80100);
//				drawSTArect(canvas,ch,test,"Attention!!! ",0x10FFC703);
				break;
			case 7:
				drawAPrect(canvas,ch,test,"Hello ",0x6490CE80);
				break;
			case 11:
				drawAPrect(canvas,ch,test,"Wireless ",0x643EB489);
				break;			
			case 14:
				drawAPrect(canvas,ch,test,"Dublin ",0x64FF0C3E);
				break;	
		}
	}
	protected void drawAP(Canvas canvas,ScanItem ap_draw, int draw_limit)
	{		
		//									   1	2	3	4    5     6    7    8    9    10   11  12   13    14
		//public static int[] arr_freq = {0,2412,2417,2422,2427,2432,2437,2442,2447,2452,2457,2462,2467,2472,2484}; 
	
//		Log.d("MY ChartEngine ","drawAP" + " channel = " + Integer.toString(ap_draw.channel));
	
//		if(getCoordRSSILevel(ap_draw.rssi) > draw_limit)
//			Log.e("MY ChartEngine TRUE","ap_draw.rssi = " + Integer.toString(getCoordRSSILevel(ap_draw.rssi))+" draw_limit = " + Integer.toString(draw_limit));
//		else
//			Log.e("MY ChartEngine FALSE ","ap_draw.rssi = " + Integer.toString(getCoordRSSILevel(ap_draw.rssi))+" draw_limit = " + Integer.toString(draw_limit));
		
		/*Draw AP rectangle*/
		if(getCoordRSSILevel(ap_draw.rssi) > draw_limit)
		{
			drawAPrect(canvas,ap_draw.channel,draw_limit,ap_draw.ssid,ap_draw.apcolor);
		} else {
			/*No need to increase current bar. Just draw last state*/
			drawAPrect(canvas,ap_draw.channel,getCoordRSSILevel(ap_draw.rssi),ap_draw.ssid,ap_draw.apcolor);
		}
	

	}
	protected int getCoordRSSILevel(int rssi_level){
		int coord_rssi = RSSI_START_AXISXY - RSSI_STEP - rssi_level;
		return Math.abs((evalY_px/RSSI_STEP)*coord_rssi);
	}
	protected void setcalcAPrectWidth(int x_pixels) {
		staRectWidth = (x_pixels*4 +3);
	}
	private void drawAPrect(Canvas canvas, int channel,int draw_step,String ssid_name,int color)
	{	
		p.setColor(color);
		canvas.drawRect(rect_ch_coord[channel].x1,(canvas.getHeight() - 70 ),rect_ch_coord[channel].x2,(canvas.getHeight() - 70 - draw_step),p);
//	       setName(canvas,15,0, (coord8 - 10 - (coord8 - coord7)/2) ,canvas.getHeight() - 70 - test,"Ch 4");
	       setName(canvas,12,0, rect_ch_coord[channel].x1 ,canvas.getHeight() - 4 - 70 - draw_step,ssid_name + " " + channel);		
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
	public int findHighestRSSI (ArrayList<ScanItem> list_ap)
	{
		int high_rssi = 0;
		int tmp_rssi = -999;//fake value
    	for (int i=0; i < list_ap.size();i++)
    	{
    		if (tmp_rssi < list_ap.get(i).rssi)
    		{
    			Log.d("MY findHighestRSSI ", "i = " + Integer.toString(i) + " " + list_ap.get(i).rssi);    			
    			tmp_rssi = list_ap.get(i).rssi;
    			high_rssi = tmp_rssi;
    		}
    	}
		
    	Log.d("MY findHighestRSSI ", "high_rssi =  " + high_rssi);
    	    	
		return high_rssi;
	}
	
}

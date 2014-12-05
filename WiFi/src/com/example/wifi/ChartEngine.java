package com.example.wifi;

import java.util.ArrayList;
import java.util.Random;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
public class ChartEngine {
	public static int width;//Display size values
	public static int height; //width = 480 height = 800//690 end // MY HTC 480/690
	final int OFFSET_X_AXISX = 50;//pixel
	final int OFFSET_Y_AXISX = 70;
	
	final int OFFSET_X_AXISY = 60;	
	final int OFFSET_Y_AXISY = 60;
	
	final int RSSI_START_AXISXY = -95;
	final int RSSI_END_AXISXY = -35;
	final int RSSI_STEP = 5;
	final int NUMBER_OF_CHANNELS = 15;
	final int NUMBER_OF_COLORS = 20;
	final int USE = 1;
	final int NOT_USE = 0;
	final int [] arr_const_color = new int[]{
			0x6409e9a3 /*green*/, // 0
			0x64FF00CC /*raspberry*/,
			0x64CC0000 /*red*/,
			0x64FFD700 /* gold */,		
			0x643C3C7E /*black purple*/,
			0x644B0082 /* indigo */,
			0x6400FFFF /*aqua*/,
			0x64696969 /* dim gray */,
			0x64da6e17 /*orange*/,
			0x64db311e /*pink*/,
			0x6464E80C /*green*/,
			0x64238be6 /*blue*/,
			0x64CCBBDD /*grey*/,
			0x64e5cb47 /*yellow*/,			
			0x64b741cc /*purple*/,
			0x64FF00FF /* magenta */,
			0x64ed1b79 /*raspberry*/,
			0x64DAA520 /*goldenRod*/,		
			0x64008000 /*green*/,
			0x64006666 /*cyan black*/,//19
			
			0x64AA9BEB /*purple*/,
			0x64688266 /*green black*/,
			0x64808000 /* olive */,		
			0x64FF8509 /* orange lighter */,
			0x64E0FFFF /* cyan light */,
			0x64F2AAA2 /* pink light */,
			0x64FFFF00 /* yellow */,
			0x64008B8B /* darkCyan */,
			0x64800000 /* red (maroon)*/,			
			0x643E8C2A /*green*/, //29			
			
			//http://www.w3schools.com/html/html_colornames.asp
	};
	
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
	public class APcolors
	{
		public int state;
		public int ap_color;
		
		public APcolors(int color)
		{
			this.ap_color = color;			
		}
	}
	
	static int frame_ready = 1; //??????	
	ChannelCoord [] ch_coord = new ChannelCoord[NUMBER_OF_CHANNELS];
	RectSTACoord [] rect_ch_coord = new RectSTACoord[NUMBER_OF_CHANNELS];
	APcolors	 [] arr_ap_colors = new APcolors[NUMBER_OF_COLORS];	
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
		/* Set default AP colors */
		fillColors(arr_ap_colors, arr_const_color);
		
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
//       	Log.e("MY ChartEngine: drawAxisXY: ", "width = " + Integer.toString(width) + " height = " + Integer.toString(height));
        drawSegmentsAxisX(canvas);
        drawSegmentsAxisY(canvas);
        setName(canvas,20,0,canvas.getWidth()/2 - 40,canvas.getHeight() - (canvas.getHeight()-25),"Channel graph");   
        setName(canvas,15,0,canvas.getWidth()/2 - 40,canvas.getHeight() - 30,"WIFI Channels");
     
	}
		
	public void startDraw (Canvas canvas,int test,ArrayList<ScanItem> list_ap_old,ArrayList<ScanItem> list_ap_new)
	{	
		for (ScanItem list_test : list_ap_new) {
  		  //Log.d("MY TAG ", "BSSID = " + list_test.getBSSID() + " RSSI= " + list_test.getRSSIlevel());
  		  drawAP(canvas,list_test, test);
  	  	}
	}

	public void compareListData	(ArrayList<ScanItem> list_ap_old,ArrayList<ScanItem> list_ap_new,ArrayList<ScanItem> list_ap_res)
	{
		
		/* Find existing objects,what present on the display */		
		for(int i = 0; i < list_ap_old.size(); i++ )
		{		
			for (int k = 0; k < list_ap_new.size(); k++)
			{					
				if (list_ap_old.get(i).getBSSID().equals(list_ap_new.get(k).getBSSID()))
				{
					ScanItem res_obj;
				
					res_obj = list_ap_new.get(k);
					res_obj.setBSSID(list_ap_new.get(k).getBSSID());
					res_obj.apcolor = list_ap_old.get(i).getAPcolor();
					res_obj.old_rssi = list_ap_old.get(i).rssi ;
					res_obj.rssi = list_ap_new.get(k).rssi ;
					res_obj.diff_rssi = list_ap_new.get(k).rssi - list_ap_old.get(i).rssi;
					list_ap_res.add(res_obj);					
					list_ap_new.remove(k);//Remove from list_ap_new after copy
//					testPrintList(list_ap_res, "--- FIRST ELEMENT list_ap_res --");
					
//					if (res_obj.diff_rssi != 0)
//					{
//						Log.e("MY ChartEngine ", "compareListData: i= [ " + Integer.toString(i) + " ] " + " obj.diff_rssi = " 
//								+ Integer.toString(res_obj.diff_rssi)+ " " + res_obj.getSSID());
//					}
					
				} //END OF EQUAL()
				
//				/* OLD VERSION */
//				if (list_ap_old.get(i).getBSSID().equals(list_ap_new.get(k).getBSSID()))
//				{
//					Log.e("MY ChartEngine ", "compareListData: add i = " + Integer.toString(i) + " get k = " + Integer.toString(k));
//					Log.e("MY ChartEngine ", "compareListData: BEFORE get value list_ap_old.size()  " + Integer.toString(list_ap_old.size()));
//					Log.e("MY ChartEngine ", "compareListData: BEFORE get value list_ap_new.size  " + Integer.toString(list_ap_new.size()));					
//					Log.e("MY ChartEngine ", "compareListData: BEFORE get value list_ap_res.size()  " + Integer.toString(list_ap_res.size()));
//					testPrintList(list_ap_new, "---BEFORE get value list_ap_new --");
//					if(list_ap_res.size() != 0)
//					{
//						list_ap_res.add(i,list_ap_new.get(k));
//					} else {
//						list_ap_res.add(list_ap_new.get(k));
//						Log.e("MY ChartEngine ", "compareListData: FIRST ELEMENT add i = " + Integer.toString(i) + " get k = " + Integer.toString(k));
//						testPrintList(list_ap_res, "--- FIRST ELEMENT list_ap_res --");
//					}
//					
//					list_ap_res.get(i).setBSSID(list_ap_new.get(k).getBSSID());
//					list_ap_res.get(i).apcolor = list_ap_old.get(i).getAPcolor();
//					list_ap_res.get(i).old_rssi = list_ap_old.get(i).rssi ;
//					list_ap_res.get(i).rssi = list_ap_new.get(k).rssi ;
//					list_ap_res.get(i).diff_rssi = list_ap_new.get(k).rssi - list_ap_old.get(i).rssi;					
//					list_ap_new.remove(k);//Remove from list_ap_new after copy
//					Log.e("MY ChartEngine ", "compareListData: AFTER REMOVE list_ap_new.size  " + Integer.toString(list_ap_new.size()));
//					testPrintList(list_ap_new, "--- AFTER REMOVE list_ap_new --");
//					
//					if (list_ap_res.get(i).diff_rssi != 0)
//					{
//						Log.e("MY ChartEngine ", "compareListData: i= [ " + Integer.toString(i) + " ] " + " list_ap_res.get(i).diff_rssi = " 
//								+ Integer.toString(list_ap_res.get(i).diff_rssi)+ " " + list_ap_res.get(i).getSSID());
//					}
//					
//				} // end of equal()
				
			}			
			
		}

		
		/* For objects what still exist in received data and were not displayed before*/				
		for (ScanItem getobject: list_ap_new)
		{
			Log.e("MY ChartEngine ", "compareListData: NEW SSID = "	+ getobject.getSSID()
					+ " BSSID:" + getobject.getBSSID());
			list_ap_res.add(getobject);
		}

		list_ap_old.clear();//Clear list with old data
		
		for (ScanItem getobject: list_ap_res)
			list_ap_old.add(getobject);
		 
	}
	
	protected void drawAP(Canvas canvas,ScanItem ap_draw, int draw_limit)
	{		
		//									   1	2	3	4    5     6    7    8    9    10   11  12   13    14
		//public static int[] arr_freq = {0,2412,2417,2422,2427,2432,2437,2442,2447,2452,2457,2462,2467,2472,2484}; 
		
//		Log.e("MY ChartEngine ","------------ " + " ap_draw.rssi = " + Integer.toString(ap_draw.rssi)
//								+ " ap_draw.old_rssi = " + Integer.toString(ap_draw.old_rssi) +
//								 " draw_limit = " + Integer.toString(draw_limit) + " ------------------");
		
		/* New AP appears in list */
		if (ap_draw.old_rssi == 0)
		{	
			if(getCoordRSSILevel(ap_draw.rssi) > draw_limit) {
				drawAPrect(canvas,ap_draw.channel,draw_limit,ap_draw.ssid,ap_draw.apcolor,ap_draw.rssi);
			} else {/*No need to increase current bar. Just draw last state*/
				drawAPrect(canvas,ap_draw.channel,getCoordRSSILevel(ap_draw.rssi),ap_draw.ssid,ap_draw.apcolor,ap_draw.rssi);
			}
		} else { /*For existing AP*/				
			if (Math.abs((evalY_px/RSSI_STEP)*(ap_draw.diff_rssi)) > draw_limit)
			{
				drawAPrect(canvas,ap_draw.channel,
							getCoordRSSILevel(ap_draw.old_rssi) + 
							((ap_draw.old_rssi > ap_draw.rssi) ? ( - draw_limit):draw_limit),
							ap_draw.ssid,ap_draw.apcolor,ap_draw.rssi);
			} else {
				/*No need to increase current bar. Just draw last state*/			
				drawAPrect(canvas,ap_draw.channel,getCoordRSSILevel(ap_draw.rssi),ap_draw.ssid,ap_draw.apcolor,ap_draw.rssi);				
			}
		}
	}
	private void drawAPrect(Canvas canvas, int channel,int draw_step,String ssid_name,int color, int debug_rssi) 
	{	
		p.setColor(color);
//		Log.d("MY ChartEngine "," drawAPrect" + " draw_step = " + Integer.toString(draw_step));

		canvas.drawRect(rect_ch_coord[channel].x1,(canvas.getHeight() - 70 ),rect_ch_coord[channel].x2,(canvas.getHeight() - 70 - draw_step),p);
	    setName(canvas,12,0, rect_ch_coord[channel].x1 ,canvas.getHeight() - 70 - 4 /*just for shift*/ - draw_step,ssid_name + " " + channel + " " + debug_rssi);		
	}
	protected int getCoordRSSILevel(int rssi_level){
		int coord_rssi = RSSI_START_AXISXY - RSSI_STEP - rssi_level;
		return Math.abs((evalY_px/RSSI_STEP)*coord_rssi);
	}
	protected void setcalcAPrectWidth(int x_pixels) {
		staRectWidth = (x_pixels*4 +3);
	}
	
	public int findHighestRSSI (ArrayList<ScanItem> list_ap)
	{
		int high_rssi = 0;
		int tmp_rssi = -999;//fake value
    	for (int i=0; i < list_ap.size();i++)
    	{
    		if (tmp_rssi < list_ap.get(i).rssi)
    		{
    			//Log.d("MY findHighestRSSI ", "i = " + Integer.toString(i) + " " + list_ap.get(i).rssi);    			
    			tmp_rssi = list_ap.get(i).rssi;
    			high_rssi = tmp_rssi;
    		}
    	}		
//    	Log.d("MY findHighestRSSI ", "high_rssi =  " + high_rssi);    	    	
		return high_rssi;
	}
	
	private void fillColors(APcolors [] ap_colors, int [] arr_colors)
	{
		for (int i = 0; i < NUMBER_OF_COLORS;i++)
		{
			ap_colors[i] = new APcolors(arr_colors[i]);
		}
	}
	
	public void testAddScanItem(String SSID,String BSSID,int inpRSSI,int inpFreq,int index_color,
								ArrayList<ScanItem> inp_list_ap)
	{
		ScanItem x = new ScanItem();
    	x.setSSID(SSID);
    	x.setBSSID(BSSID);
    	x.setRSSIlevel(inpRSSI);
//    	first.setOldRSSIlevel(0);
    	x.setChannelFreq(inpFreq);
    	x.setChannelNum(CustomScanListAdapter.convertFreqtoChannelNum(inpFreq,CustomScanListAdapter.arr_freq));
    	x.setAPcolor(arr_ap_colors[index_color].ap_color);
    	//Add in list
    	inp_list_ap.add(x);
//    	Log.d("MY TAG ", SSID + " " + Integer.toHexString(x.getAPcolor()));     	
	}
	public void testPrintList(ArrayList<ScanItem> inp_list_ap, String note)
	{
		//Display list
		Log.d("MY TAG ", "********* " + note + " **********" );
		
		if (inp_list_ap.isEmpty())
		{
			Log.d("MY TAG ", "****** EMPTY " + note + " *******" );
			return ;
		}
    	for (int i=0; i < inp_list_ap.size();i++)
    	{
    		Log.d("MY TAG ", "id[" + Integer.toString(i)+ "] " + "SSID = " + inp_list_ap.get(i).ssid + " BSSID = " + inp_list_ap.get(i).getBSSID());    		
    		//Log.d("MY TAG ", "id[" + Integer.toString(i)+ "] " + "apcolor = " + " 0x" + Integer.toHexString(inp_list_ap.get(i).apcolor));
    		Log.d("MY TAG ", "id[" + Integer.toString(i)+ "] "  + "ch num = " + Integer.toString(inp_list_ap.get(i).getChannelNum()) + 
    						 " ch freq = " + Integer.toString(inp_list_ap.get(i).getChannelFreq()) + " apcolor = " + " 0x" + Integer.toHexString(inp_list_ap.get(i).apcolor));
    		Log.d("MY TAG ", "id[" + Integer.toString(i)+ "] " + "rssi = " + Integer.toString(inp_list_ap.get(i).rssi) +
    						  " old_rssi = " + Integer.toString(inp_list_ap.get(i).old_rssi) +
    						 " diff_rssi = " + Integer.toString(inp_list_ap.get(i).diff_rssi));    		
    	} 
    	Log.d("MY TAG ", "********* END LIST " + note + " *********" );
	}
	 
	/*OLD VERSION*/
	protected void channel2rectDraw(Canvas canvas,int channel,int rssi , int test)
	{
		Log.d("MY ChartEngine ","channel2rectDraw");
		//									   1	2	3	4    5     6    7    8    9    10   11  12   13    14
		//public static int[] arr_freq = {0,2412,2417,2422,2427,2432,2437,2442,2447,2452,2457,2462,2467,2472,2484}; 
		
		int ch = CustomScanListAdapter.convertFreqtoChannelNum(channel,CustomScanListAdapter.arr_freq);
		switch(ch)
		{
//			case 1://need fill
//				if(Math.abs(rssi) >= test)
//				{
//					drawAPrect(canvas,ch,test,"Antonio ",0x30F7E836);
//				} else {
//					drawAPrect(canvas,ch,Math.abs(rssi),"Antonio ",0x30F7E836);
//				}
//				break;
//			case 2:
//				drawAPrect(canvas,ch,test,"Cambium ",0x64CC0000);
//				break;
//			case 3:
//				drawAPrect(canvas,ch,test,"Mario ",0x64CCBBDD);
//				break;
//			case 4:
//				drawAPrect(canvas,ch,test,"Barcelona ",0x6464E80C);
//				break;
//			case 5:
//				drawAPrect(canvas,ch,test,"Fabregas ",0x64E80C64);
//				break;
//			case 6:
//				drawAPrect(canvas,ch,test,"Watch out!!! ",0x64E80100);
////				drawSTArect(canvas,ch,test,"Attention!!! ",0x10FFC703);
//				break;
//			case 7:
//				drawAPrect(canvas,ch,test,"Hello ",0x6490CE80);
//				break;
//			case 11:
//				drawAPrect(canvas,ch,test,"Wireless ",0x643EB489);
//				break;			
//			case 14:
//				drawAPrect(canvas,ch,test,"Dublin ",0x64FF0C3E);
//				break;	
		}
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

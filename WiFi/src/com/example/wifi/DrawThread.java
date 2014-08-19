package com.example.wifi;

import java.util.ArrayList;
import java.util.Random;

import android.R.bool;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class DrawThread extends Thread {
	private boolean runFlag = false;
    private SurfaceHolder surfaceHolder;
    private Bitmap picture;
    private Matrix matrix;
    private long prevTime;
    private Bitmap myBitmap;
    ChartEngine chart;
    
    int temp;
    int draw_marker;
    int high_rssi;
	
    static boolean test_flag = true;
    
    Paint p;
    public DrawThread(SurfaceHolder surfaceHolder, Resources resources,Context context){
    
        this.surfaceHolder = surfaceHolder;
    	Log.d("MY DrawThread:", "Constructor create !!!" );
    	p = new Paint();
    	chart = new ChartEngine(context);
    	myBitmap =  Bitmap.createBitmap(ChartEngine.width,ChartEngine.height,Bitmap.Config.ARGB_8888);
    	
        // Load image for display on the screen
        picture = BitmapFactory.decodeResource(resources, R.drawable.decrypted);
        // формируем матрицу преобразований для картинки
        matrix = new Matrix();
        matrix.postScale(3.0f, 3.0f);
        matrix.postTranslate(100.0f, 100.0f);

        // Save current time
        prevTime = System.currentTimeMillis();
    }

    public void setRunning(boolean run) {
    	Log.d("MY DrawThread:", "setRunning() set runFlag : " + Boolean.toString(run) );
//        runFlag = run;
    	 DrawThread.test_flag = run;
    	
    }

    @Override
    public void run() {
//    	List<ScanResult> debug_scan_result;
    	//WifiManager wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
    	//debug_scan_result = wifiManager.getScanResults();
    	Canvas canvas;
    	Log.d("MY DrawThread:", "run() draw picture !!!");    	
		canvas = new Canvas(myBitmap);
    	ArrayList<ScanItem> list_ap = new ArrayList<ScanItem>();
    	ArrayList<ScanItem> list_ap_old = new ArrayList<ScanItem>();
    	
    	//List <ScanItem> list_sta = new List<ScanItem>();
    	    	
    	
    	Random rnd = new Random();
    	    
    	testAddScanItem("TEST","01:02:03:04:05:06",-90,2412,rnd.nextInt(chart.NUMBER_OF_COLORS),list_ap);
    	testAddScanItem("ACTION","01:11:22:33:44:55",-85,2437,rnd.nextInt(chart.NUMBER_OF_COLORS),list_ap);
    	testAddScanItem("MAPROAD","77:11:22:33:44:55",-95,2457,rnd.nextInt(chart.NUMBER_OF_COLORS),list_ap);

    	
    	//Display list
//    	for (int i=0; i < list_ap.size();i++)
//    	{
//  		  Log.d("MY TAG ", "i = " + Integer.toString(i) + " " + list_ap.get(i).getBSSID());
//    	}
//    	
//    	  for (ScanItem list_test : list_ap) {
//    		  Log.d("MY TAG ", "BSSID = " + list_test.getBSSID() + " RSSI= " + list_test.getRSSIlevel());
//    	  }
    	  
		chart.drawAxisXY(canvas);
		canvas.drawBitmap(myBitmap,0,0,p);
		
		//find the biggest RSSI and set limitation for draw
		high_rssi = chart.findHighestRSSI(list_ap);
	    draw_marker = chart.getCoordRSSILevel(high_rssi);
	    
	    //Copy new to old
	    
	    
        //while (runFlag) {
	    while (DrawThread.test_flag) {
            // получаем текущее время и вычисляем разницу с предыдущим 
            // сохраненным моментом времени
            long now = System.currentTimeMillis();
            long elapsedTime = now - prevTime;
            if (elapsedTime > 30){
                // если прошло больше 30 миллисекунд - сохраним текущее время
                // и повернем картинку на 2 градуса.
                // точка вращения - центр картинки
                prevTime = now;
                matrix.preRotate(2.0f, picture.getWidth() / 2, picture.getHeight() / 2);              
            }
            canvas = null;
            try {
                // получаем объект Canvas и выполняем отрисовку
                canvas = surfaceHolder.lockCanvas(null);
                synchronized(surfaceHolder) {
                	if (temp == draw_marker)
                		setRunning(false);
                	
//                    canvas.drawColor(Color.BLACK);
//                    canvas.drawBitmap(picture, matrix, null);            		
                    canvas.drawBitmap(myBitmap, 0, 0,null );                   
                	chart.startDraw(canvas,temp,list_ap);
              		temp+=1;
                }
            } 
            finally {
                if (canvas != null) {
             
                	// Drawing finish and display on the screen
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        } //end while
    } //end run()
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
    	x.setAPcolor(chart.arr_ap_colors[index_color].ap_color);
    	//Add in list
    	inp_list_ap.add(x);
    	Log.d("MY TAG ", SSID + Integer.toHexString(x.getAPcolor())); 
    	
	}
	  
}



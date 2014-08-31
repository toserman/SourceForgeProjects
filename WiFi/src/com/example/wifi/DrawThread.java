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
    static boolean new_data_flag = false;
    
	ArrayList<ScanItem> list_ap_new = new ArrayList<ScanItem>();
	ArrayList<ScanItem> list_ap_old = new ArrayList<ScanItem>(); 
	ArrayList<ScanItem> list_ap_res = new ArrayList<ScanItem>(); 
    
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
    	Random rnd = new Random(); //Just for debug
    	//Log.d("MY DrawThread:", "run() draw picture !!!");    	
		canvas = new Canvas(myBitmap);
    					//  		   1	2	3	4    5     6    7    8    9    10   11  12   13    14
//public static int[] arr_freq = {0,2412,2417,2422,2427,2432,2437,2442,2447,2452,2457,2462,2467,2472,2484}; 
    	//List <ScanItem> list_sta = new List<ScanItem>();
    	
    	chart.testAddScanItem("MARADONA","01:11:22:33:41:55",-95,2472,2,list_ap_old);
		chart.testAddScanItem("TEST","01:02:03:04:05:06",-70,2412,0,list_ap_old);
    	chart.testAddScanItem("ACTION","01:11:22:33:44:55",-80,2437,1,list_ap_old);    	
//    	testAddScanItem("MAPROAD","77:11:22:33:44:55",-95,2457,2,list_ap_old);    	
    	
    	chart.testAddScanItem("MARCH","01:11:22:33:31:55",-96,2452,4,list_ap_new);
		chart.testAddScanItem("TEST","01:02:03:04:05:06",-80,2412,1,list_ap_new);
    	chart.testAddScanItem("ACTION","01:11:22:33:44:55",-78,2437,2,list_ap_new);
    	chart.testAddScanItem("MARADONA","01:11:22:33:41:55",-85,2472,3,list_ap_new);
    	
    	
    	
    	if (DrawThread.new_data_flag == true)
    	{
    		//Compare here old and new data
    		 Log.e("MY TAG ", "NEW DATA RECEIVED");
        	chart.testAddScanItem("TEST","01:02:03:04:05:06",-90,2412,0,list_ap_new);
//        	testAddScanItem("ACTION","01:11:22:33:44:55",-85,2437,1,list_ap);
//        	testAddScanItem("MAPROAD","77:11:22:33:44:55",-95,2457,2,list_ap);
    	}
    	chart.testPrintList(list_ap_new,"list_ap_new");
    	chart.compareListData(list_ap_old, list_ap_new,list_ap_res);

		chart.drawAxisXY(canvas);
		canvas.drawBitmap(myBitmap,0,0,p);
		
		/* find the biggest RSSI and set limitation for draw */
		high_rssi = chart.findHighestRSSI(list_ap_res);
	    draw_marker = chart.getCoordRSSILevel(high_rssi);
	    //draw_marker = 400; //Just for test
	    Log.e("MY TAG ", "draw_marker " + Integer.toString(draw_marker));
	       
	    
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
                	{
                		setRunning(false);
                		list_ap_new.clear();
                		chart.testPrintList(list_ap_new, "list_ap_new");
                	}
//                    canvas.drawColor(Color.BLACK);
//                    canvas.drawBitmap(picture, matrix, null);            		
                    canvas.drawBitmap(myBitmap, 0, 0,null );                    
                	//chart.startDraw(canvas,temp,list_ap_old,list_ap_new);
                    chart.startDraw(canvas,temp,list_ap_old,list_ap_res);
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
	
}



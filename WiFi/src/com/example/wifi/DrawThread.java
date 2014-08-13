package com.example.wifi;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
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
        runFlag = run;
    }

    @Override
    public void run() {
//    	List<ScanResult> debug_scan_result;
    	//WifiManager wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
    	//debug_scan_result = wifiManager.getScanResults();
    	//ch_coord[i] = new ChannelCoord();
    	ArrayList<ScanItem> list_ap = new ArrayList<ScanItem>();
    	
    	//List <ScanItem> list_sta = new List<ScanItem>();
    	
    	ScanItem first = new ScanItem();
    	first.setSSID("TEST");
    	first.setBSSID("01:02:03:04:05:06");
    	first.setRSSIlevel(-70);
    	first.setOldRSSIlevel(0);
    	first.setChannelFreq(2412);
    	first.setChannelNum(CustomScanListAdapter.convertFreqtoChannelNum(2412,CustomScanListAdapter.arr_freq));
    	first.setAPcolor(0x30F7E836);
    	
    	ScanItem second = new ScanItem();
    	second.setSSID("ACTION");
    	second.setBSSID("01:11:22:33:44:55");
    	second.setRSSIlevel(-85);
    	second.setOldRSSIlevel(0);
    	second.setChannelFreq(2417);
    	second.setChannelNum(CustomScanListAdapter.convertFreqtoChannelNum(2417,CustomScanListAdapter.arr_freq));
    	second.setAPcolor(0x64CC0000);
    	
    	list_ap.add(first);
    	list_ap.add(second);
    	
    	
    	
    	//Display list
//    	for (int i=0; i < list_ap.size();i++)
//    	{
//  		  Log.d("MY TAG ", "i = " + Integer.toString(i) + " " + list_ap.get(i).getBSSID());
//    	}
//    	
//    	  for (ScanItem list_test : list_ap) {
//    		  Log.d("MY TAG ", "BSSID = " + list_test.getBSSID() + " RSSI= " + list_test.getRSSIlevel());
//    	  }
    	  
    	  
    	Canvas canvas;
    	Log.d("MY DrawThread:", "run() draw picture !!!");    	
		canvas = new Canvas(myBitmap);

		chart.drawAxisXY(canvas);
		canvas.drawBitmap(myBitmap,0,0,p);
		
		//find the biggest RSSI and set limitation for draw
	    draw_marker = chart.getCoordRSSILevel(-70);
	    
        while (runFlag) {
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
}



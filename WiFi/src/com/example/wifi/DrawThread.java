package com.example.wifi;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
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
    int high_rssi;
	
    
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
    	Canvas canvas;
    	Log.d("MY DrawThread:", "run() draw picture !!!");    	
		canvas = new Canvas(myBitmap);
    	ArrayList<ScanItem> list_ap = new ArrayList<ScanItem>();
    	
    	//List <ScanItem> list_sta = new List<ScanItem>();
    	
    	Random rnd = new Random();
    	int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));   
    	   	   
    	
//		drawAPrect(canvas,ch,test,"Cambium ",0x64CC0000);
//		drawAPrect(canvas,ch,test,"Mario ",0x64CCBBDD);
//		drawAPrect(canvas,ch,test,"Barcelona ",0x6464E80C);
//    	drawAPrect(canvas,ch,test,"Fabregas ",0x64E80C64);
//		break;
//	case 6:
//		drawAPrect(canvas,ch,test,"Watch out!!! ",0x64E80100);
////		drawSTArect(canvas,ch,test,"Attention!!! ",0x10FFC703);
//		break;
//	case 7:
//		drawAPrect(canvas,ch,test,"Hello ",0x6490CE80);
//		break;
//	case 11:
//		drawAPrect(canvas,ch,test,"Wireless ",0x643EB489);
//		break;			
//	case 14:
//		drawAPrect(canvas,ch,test,"Dublin ",0x64FF0C3E);
//		break;	
    	
    	ScanItem first = new ScanItem();
    	first.setSSID("TEST");
    	first.setBSSID("01:02:03:04:05:06");
    	first.setRSSIlevel(-90);
    	first.setOldRSSIlevel(0);
    	first.setChannelFreq(2412);
    	first.setChannelNum(CustomScanListAdapter.convertFreqtoChannelNum(2412,CustomScanListAdapter.arr_freq));
    	first.setAPcolor(0x6400FFFF);
//    	first.setAPcolor(Color.argb(100, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
    	Log.d("MY TAG ", "TEST = " + Integer.toHexString(first.getAPcolor()));
    	
    	ScanItem second = new ScanItem();
    	second.setSSID("ACTION");
    	second.setBSSID("01:11:22:33:44:55");
    	second.setRSSIlevel(-85);
    	second.setOldRSSIlevel(0);
    	second.setChannelFreq(2437);
    	second.setChannelNum(CustomScanListAdapter.convertFreqtoChannelNum(2437,CustomScanListAdapter.arr_freq));
    	second.setAPcolor(0x64FF00CC);
//    	second.setAPcolor(Color.argb(100, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
    	Log.d("MY TAG ", "ACTION = " + Integer.toHexString(second.getAPcolor()));
    	
    	ScanItem third = new ScanItem();
    	third.setSSID("MAPROAD");
    	third.setBSSID("77:11:22:33:44:55");
    	third.setRSSIlevel(-95);
    	third.setOldRSSIlevel(0);
    	third.setChannelFreq(2457);
    	third.setChannelNum(CustomScanListAdapter.convertFreqtoChannelNum(2457,CustomScanListAdapter.arr_freq));
    	third.setAPcolor(0x64FFFF00);
//    	third.setAPcolor(Color.argb(100, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
    	
  	  	Log.d("MY TAG ", "MAPROAD = " + Integer.toHexString(third.getAPcolor()));
    	
    	list_ap.add(first);
    	list_ap.add(second);
    	list_ap.add(third);
    	
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



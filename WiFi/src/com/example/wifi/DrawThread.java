package com.example.wifi;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
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
        Canvas canvas;
    	Log.d("MY DrawThread:", "run() draw picture !!!" );    	
		canvas = new Canvas(myBitmap);
		
		//canvas.drawBitmap(myBitmap, 0, 0, p);
		chart.drawAxisXY(canvas);
		 canvas.drawBitmap(myBitmap,0,0,p);
		
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
                	if (temp == 300)
                		setRunning(false);
                	
//                    canvas.drawColor(Color.BLACK);
//                    canvas.drawBitmap(picture, matrix, null);            		
                    canvas.drawBitmap(myBitmap, 0, 0,null );                   
                	chart.startDraw(canvas,temp);
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



package com.example.wifi;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Toast;

public class ChartEngine extends View {
	public static int width;//Display size values
	public static int height; //width = 480 height = 800//690 end
	Paint p;	
	public ChartEngine(Context context)
	{
		super(context);
		p = new Paint();
	}
	
	public static void getDisplaySize(Activity activity,Context context) {
		Display display = activity.getWindowManager().getDefaultDisplay();
	   	Point size = new Point();
	   	display.getSize(size);
	   	width = size.x;
	   	height = size.y - 110;	   	    
	   	Log.d("MY ChartEngine: Display: ", "width = " + Integer.toString(width)
	   			+ " height = " + Integer.toString(height));
	   	Toast.makeText(context, "Display size width = " + Integer.toString(width) +
	   			" height = " + Integer.toString(height),	Toast.LENGTH_LONG).show();
	} 

	public void drawAxisXY(Canvas canvas) {
		final int START_X_AXISX = 30;//pixel
		final int START_X_AXISY = 40;
		canvas.drawColor(Color.rgb(0x1c,0x1c,0x1c));
    	// brush preferences
        p.setColor(Color.WHITE);
            // толщина линии = 10
           // p.setStrokeWidth(10);
        
        canvas.drawLine(START_X_AXISX,(height - 70),(width-20),(height - 70),p);//Draw axis X
        canvas.drawLine(START_X_AXISY,(height - 60),START_X_AXISY,30,p);//Draw axis Y
        /*Draw segments on axis X*/
        int evalX_px = ((width-20) - START_X_AXISX) / 12/*channels*/;
    	Log.d("MY ChartEngine: drawAxisXY: ", "eval_px = " + evalX_px);
        for(int k = 1; k < 13; k++)
        {
        	canvas.drawLine(START_X_AXISX + evalX_px*k, (height - 70) + 5, 
        			START_X_AXISX + evalX_px*k, (height - 70), p);
        	canvas.drawText(Integer.toString(k),START_X_AXISX + evalX_px*k - 3, (height - 70) + 18, p);
        }
        /*Draw segments on axis Y*/
        int evalY_px = ((height - 70) - START_X_AXISY) / 10/*channels*/;
        for(int m = 1; m < 11; m++)
        {
        	canvas.drawLine(START_X_AXISY + 5, height - 70 - evalY_px*m , 
        			START_X_AXISY - 5 , height - 70 - evalY_px*m, p);
        	canvas.drawText(Integer.toString(m),START_X_AXISX - 12, height - 70 - evalY_px*m + 3, p);
        }
	}
}

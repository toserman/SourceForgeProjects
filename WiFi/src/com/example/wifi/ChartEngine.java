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
		canvas.drawColor(Color.rgb(0x1c,0x1c,0x1c));
    	// brush preferences
        p.setColor(Color.WHITE);
            // толщина линии = 10
           // p.setStrokeWidth(10);
        
        canvas.drawLine(30,(height - 40),(width-20),(height - 40),p);//Draw axis X
        canvas.drawLine(40,(height - 30),40,30,p);//Draw axis Y
        	
	}
}

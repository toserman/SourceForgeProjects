package com.example.wifi;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.chart.PieChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

public class ActivityTwo extends Activity {
	 protected void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    setContentView(new DrawView(this));
		  }

		  class DrawView extends View {	
			  Paint p;
		    public DrawView(Context context) {		    	
		      super(context);
		      p = new Paint();
		    }
		    
		    @Override
		    protected void onDraw(Canvas canvas) {
		    	 Log.d("MY ActivityTwo ", "Draw !!!");
		    	 Display display = getWindowManager().getDefaultDisplay();
		    	 Point size = new Point();
		    	 display.getSize(size);
		    	 int width = size.x;
		    	 int height = size.y;
		    	 
		    	 Log.d("MY ActivityTwo: Display: ", "width = " + Integer.toString(width) + " height = "
		    			 		+ Integer.toString(height)); 
		    	 
		    	  canvas.drawColor(Color.rgb(0x1c,0x1c,0x1c));
		    	 // brush preferences
		            p.setColor(Color.WHITE);
		            // толщина линии = 10
		           // p.setStrokeWidth(10);
		            canvas.drawLine(2 ,600,100,100,p);
		           
		            ///invalidate();
		    }
		    protected void reDraw() {
		    	 Log.d("MY ActivityTwo ", "reDraw !!!");
		        this.invalidate();
		       }
		    
		  }
}




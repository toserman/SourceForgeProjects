package com.example.wifi;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
	public static TextView rowtablename,rowssid_1,rowssid_2,rowssid_3;
	public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.activity_two);
	      Log.e("MY TAG :", "onCreate = ActivityTwo");
	      
	      Intent intent = buildIntent();	      
	      startActivity(intent);
	      
	      
	 /*     GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {	    	      
	    	      new GraphViewData(1, 6.0d), new GraphViewData(2, 6.0d),
					new GraphViewData(3, 5.0d), new GraphViewData(4,5.0d),new GraphViewData(5, 10.0d), new GraphViewData(6,21.0d)
	    	});*/
	    
	      /* WORKING VERSION
	      GraphViewSeries exampleSeries = new GraphViewSeries(
	    			new GraphViewData[] { new GraphViewData(1, 3),
	    					new GraphViewData(2, 6), new GraphViewData(3, 6),
	    					new GraphViewData(4, 5), new GraphViewData(5, 3)
	    			});
	      
	      GraphView graphView = new BarGraphView(
	    	      this
	    	      , "GraphViewDemo"
	    	);
	      
	      graphView.addSeries(exampleSeries);
	      //graphView.setDrawBackground(true);
	      //graphView.setBackgroundColor(Color.rgb(120, 30, 30));
	      LinearLayout layout = (LinearLayout) findViewById(R.id.graph2);
	      layout.addView(graphView);
	    */		
	  }
	
	public Intent buildIntent() {
        int[] values = new int[] { 5, 15, 25, 50, 75 };        // шаг 2
        String[] bars = new String[] {"Francesca's",  "King of Clubs", 
                                 "Zen Lounge", "Tied House", "Molly Magees"};
        int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.MAGENTA, 
                                   Color.YELLOW, Color.CYAN };
 
        CategorySeries series = new CategorySeries("Pie Chart");  // шаг 3
        DefaultRenderer dr = new DefaultRenderer();   // шаг 4
 
        for (int v=0; v<5; v++){    // шаг 5
            series.add(bars[v], values[v]);
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(colors[v]);
            dr.addSeriesRenderer(r);
        }
        dr.setZoomButtonsVisible(true);
        dr.setZoomEnabled(true);
        dr.setChartTitleTextSize(20);
        return ChartFactory.getPieChartIntent(    // шаг 6
                              this, series, dr, "Pie of bars");    
    }
	
}



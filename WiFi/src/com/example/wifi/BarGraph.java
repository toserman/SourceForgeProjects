package com.example.wifi;

import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.RangeCategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.net.wifi.ScanResult;
import android.util.Log;

public class BarGraph {
	
	public Intent getIntent(Context context,List<ScanResult> scan_results){
	    
		Log.e("MY TAG ", "********* BarGraph **************");
		
        int y[] = {15,10,15,20};
               
        CategorySeries series = new CategorySeries("Bar1");
        for(int i=0; i < y.length; i++){
            series.add("Bar"+(i+1),y[i]);
        }
               
        XYMultipleSeriesDataset dataSet = new XYMultipleSeriesDataset();  // collection of series under one object.,there could any
        dataSet.addSeries(series.toXYSeries());                            // number of series
       //customization of the chart
                   
        XYSeriesRenderer renderer = new XYSeriesRenderer();     // one renderer for one series
        renderer.setColor(Color.RED);
        renderer.setDisplayChartValues(true);
        renderer.setChartValuesSpacing((float) 2.5d);
        renderer.setLineWidth((float) 10.5d);
        
       
        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();   // collection multiple values for one renderer or series
        mRenderer.addSeriesRenderer(renderer);
        mRenderer.setChartTitle("RSSI Level");
//        mRenderer.setXTitle("xValues");
        
        
        mRenderer.setYTitle("Rupee");
        mRenderer.setZoomButtonsVisible(true);
        mRenderer.setShowLegend(true);
        mRenderer.setShowGridX(false);      // this will show the grid in  graph
        mRenderer.setShowGridY(false);             
        mRenderer.setAntialiasing(true);
        mRenderer.setBarSpacing(.1);   // Sets the spacing between bars, in bar charts.
        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(Color.BLACK);
        /*Start values for display*/
        mRenderer.setXAxisMin(0);
        mRenderer.setYAxisMin(0);
        
        mRenderer.setXAxisMax(5);
        mRenderer.setYAxisMax(50);
        mRenderer.setChartValuesTextSize(10);   //for value under bar   
     
        mRenderer.setXLabelsAlign(Align.RIGHT);        
        mRenderer.setXLabelsAngle(270);
        
        mRenderer.setYLabelsAlign(Align.RIGHT);
        mRenderer.addYTextLabel(15, "Very cold");
        mRenderer.addYTextLabel(45,"Hot");
        mRenderer.setMarginsColor(Color.parseColor("#000008"));
        mRenderer.setMargins(new int[] {10,50, 10, 10});
        // mRenderer.setYLabels(0);
        
        mRenderer.setXLabels(0);//disable values on X, just leave "GL_COM" as exmple
    	// loop that goes through list
        int count = 1; //0 - no need
        for (ScanResult result : scan_results) {
       	 	mRenderer.addXTextLabel(count,result.SSID);
        	count++;
        }
       
        /*
        mRenderer.setXLabels(0);
        mRenderer.addXTextLabel(1,"Income");
        mRenderer.addXTextLabel(2,"Saving");
        mRenderer.addXTextLabel(3,"Expenditure");
        mRenderer.addXTextLabel(4,"NetIncome");
        */
        mRenderer.setPanEnabled(true, true);    // will fix the chart position
        //Color.argb(0, 255, 255, 255)
        //http://stackoverflow.com/questions/11309065/my-android-achartengine-is-already-working-but-how-to-make-it-look-good
        //http://stackoverflow.com/questions/10809477/bar-graph-using-achartengine
   /*     
        SimpleSeriesRenderer r = mRenderer.getSeriesRendererAt(0);       
        r.setDisplayChartValues(true);
        r.setChartValuesTextSize(42);        
        r.setChartValuesSpacing(3);
        r.setGradientEnabled(true);
        r.setGradientStart(-20, Color.BLUE);
        r.setGradientStop(20, Color.GREEN);
     */
        Intent intent = ChartFactory.getBarChartIntent(context, dataSet, mRenderer,Type.DEFAULT);
       
        return intent;
	}
	 
}

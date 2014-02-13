package com.example.wifi;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BubbleChart;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class BarGraph {
	
	public Intent getIntent(Context context){
	       
        int y[] = {25,10,15,20};
       
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
        renderer.setChartValuesSpacing((float) 5.5d);
        renderer.setLineWidth((float) 10.5d);
           
       
        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();   // collection multiple values for one renderer or series
        mRenderer.addSeriesRenderer(renderer);
        mRenderer.setChartTitle("Demo Graph");
//        mRenderer.setXTitle("xValues");
        mRenderer.setYTitle("Rupee");
        mRenderer.setZoomButtonsVisible(true);
        mRenderer.setShowLegend(true);
        mRenderer.setShowGridX(false);      // this will show the grid in  graph
        mRenderer.setShowGridY(false);             
        mRenderer.setAntialiasing(true);
        mRenderer.setBarSpacing(.5);   // adding spacing between the line or stacks
        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(Color.BLACK);
        /*Start values for display*/
        mRenderer.setXAxisMin(0);
        mRenderer.setYAxisMin(0);
        
        mRenderer.setXAxisMax(5);
        mRenderer.setYAxisMax(50);
//   
      
        mRenderer.setXLabels(0);
        mRenderer.addXTextLabel(1,"Income");
        mRenderer.addXTextLabel(2,"Saving");
        mRenderer.addXTextLabel(3,"Expenditure");
        mRenderer.addXTextLabel(4,"NetIncome");
        mRenderer.setPanEnabled(true, true);    // will fix the chart position
     
        Intent intent = ChartFactory.getBarChartIntent(context, dataSet, mRenderer,Type.DEFAULT);
       
        return intent;
	}

}

package com.example.wifi;

import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYValueSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.net.wifi.ScanResult;
import android.util.Log;

public class BarGraph {
	
	protected void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle,
		      String yTitle, double xMin, double xMax, double yMin, double yMax) {
		    renderer.setChartTitle(title);
		    renderer.setXTitle(xTitle);
		    renderer.setYTitle(yTitle);
		    renderer.setXAxisMin(xMin);
		    renderer.setXAxisMax(xMax);
		    renderer.setYAxisMin(yMin);
		    renderer.setYAxisMax(yMax);
		   // renderer.setAxesColor(axesColor);
		    //renderer.setLabelsColor(labelsColor);
		  }
	
	public Intent executeBubble(Context context) {
	    XYMultipleSeriesDataset series = new XYMultipleSeriesDataset();
	    XYValueSeries newTicketSeries = new XYValueSeries("New Tickets");
	    newTicketSeries.add(1f, -30, 15);
	    newTicketSeries.add(2f, 2, 12);
	    newTicketSeries.add(3f, 2, 18);
	    newTicketSeries.add(4f, 2, 5);
	    newTicketSeries.add(5f, 2, 1);
	    series.addSeries(newTicketSeries);
	    XYValueSeries fixedTicketSeries = new XYValueSeries("Fixed Tickets");
	    fixedTicketSeries.add(1f, -70, 15);
	    fixedTicketSeries.add(2f, 1, 4);
	    fixedTicketSeries.add(3f, 1, 18);
	    fixedTicketSeries.add(4f, 1, 3);
	    fixedTicketSeries.add(5f, 1, 1);	    
	    series.addSeries(fixedTicketSeries);

	    XYSeriesRenderer newTicketRenderer = new XYSeriesRenderer();
	    newTicketRenderer.setColor(Color.BLUE);
	    newTicketRenderer.setChartValuesTextAlign(Align.LEFT);
	   
	    XYSeriesRenderer fixedTicketRenderer = new XYSeriesRenderer();
	    fixedTicketRenderer.setColor(Color.GREEN);
	    fixedTicketRenderer.setChartValuesTextSize(20);
	    fixedTicketRenderer.setChartValuesTextAlign(Align.CENTER);
	    
	    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
	    renderer.setAxisTitleTextSize(16);
	    renderer.setChartTitleTextSize(20);
	    renderer.setLabelsTextSize(20);
	    renderer.setLegendTextSize(15);
	    renderer.setMargins(new int[] {10,50, 10, 10});

	    renderer.addSeriesRenderer(newTicketRenderer);
	    renderer.addSeriesRenderer(fixedTicketRenderer);
	    
	    renderer.setZoomButtonsVisible(true);
        renderer.setShowLegend(true);
	    renderer.setShowGrid(true);
        renderer.setShowGridX(true);      // this will show the grid in  graph
        renderer.setShowGridY(false);             
        renderer.setAntialiasing(true);
        
        renderer.setXLabelsAlign(Align.CENTER);
        renderer.setYLabelsAlign(Align.RIGHT);
        
        renderer.setApplyBackgroundColor(true);
        renderer.setBackgroundColor(Color.parseColor("#000011"));
        renderer.setDisplayChartValues(true);
	    
        for (int i=1; i<15;i++)
           	renderer.addXTextLabel(i,Integer.toString(i));
        
        
	    
	    renderer.getSeriesRendererAt(0).setGradientEnabled(true);
        renderer.getSeriesRendererAt(0).setGradientStart(-30, Color.BLUE);
        renderer.getSeriesRendererAt(0).setGradientStop(-40,Color.parseColor("#00000000"));
	   
 
	    setChartSettings(renderer, "RSSI Level", "Channels", "Signal strength(dBm)",0,14,-20,-110);


	    return ChartFactory.getBubbleChartIntent(context, series, renderer, "RSSI Level");
	  }


	
	public Intent getIntent(Context context,List<ScanResult> scan_results){
	    
		Log.e("MY TAG ", "********* BarGraph **************");	
		
        int y[] = {-95};
        int y1[] = {-85};
        int y2[] = {-65};
        int y3[] = {-45};
               
        CategorySeries series_0 = new CategorySeries("Bar1");
        for(int i=0; i < y.length; i++){
            series_0.add("Bar",y[i]);
        }
        
        CategorySeries series_1 = new CategorySeries("Toserman");
        for(int i=0; i < y1.length; i++){            
            series_1.add("Toserman",y1[i]);            
        }
        CategorySeries series_2 = new CategorySeries("Peace");
        for(int i=0; i < y2.length; i++){            
            series_2.add("Peace"+(i+1),y2[i]);
        }
        CategorySeries series_3 = new CategorySeries("Hello");
        for(int i=0; i < y3.length; i++){            
            series_3.add("Toserman",y3[i]);            
        }
        
        XYSeriesRenderer renderer = new XYSeriesRenderer();     // one renderer for one series
        renderer.setColor(Color.BLUE);
   //     renderer.setDisplayChartValues(true);
       // renderer.setChartValuesSpacing((float) 2.5d);
       // renderer.setLineWidth((float) 10.5d);
        renderer.setChartValuesTextSize(20);        
       // renderer.setChartValuesSpacing(80);
        renderer.setChartValuesTextAlign(Align.CENTER);
              

        XYSeriesRenderer renderer1 = new XYSeriesRenderer();     // one renderer for one series
        renderer1.setColor(Color.GREEN);
        renderer1.setChartValuesTextAlign(Align.CENTER);
     //   renderer1.setDisplayChartValues(true);
       // renderer1.setChartValuesSpacing((float) 2.5d);
       // renderer1.setLineWidth((float) 10.5d);
        
        XYSeriesRenderer renderer2 = new XYSeriesRenderer();     // one renderer for one series
        renderer2.setColor(Color.MAGENTA);
        renderer2.setChartValuesTextAlign(Align.CENTER);
        
        XYSeriesRenderer renderer3 = new XYSeriesRenderer();     // one renderer for one series
        renderer3.setColor(Color.YELLOW);
        renderer3.setChartValuesTextAlign(Align.CENTER);


                       
        XYMultipleSeriesDataset dataSet = new XYMultipleSeriesDataset();  // collection of series under one object.,there could any
        dataSet.addSeries(series_0.toXYSeries());                            // number of series
        dataSet.addSeries(series_1.toXYSeries());
        dataSet.addSeries(series_2.toXYSeries());// number of series
        dataSet.addSeries(series_3.toXYSeries());// number of series
       //customization of the chart
     
        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();   // collection multiple values for one renderer or series
        mRenderer.addSeriesRenderer(renderer);
        mRenderer.addSeriesRenderer(renderer1);
        mRenderer.addSeriesRenderer(renderer2);
        mRenderer.addSeriesRenderer(renderer3);
        
        mRenderer.setChartTitle("RSSI Level");
        mRenderer.setChartTitleTextSize(20);
//        mRenderer.setXTitle("xValues");
       
       mRenderer.setYTitle("Signal strength(dBm)");
       mRenderer.setXTitle("Channels");
              
        mRenderer.setZoomButtonsVisible(true);
        mRenderer.setShowLegend(true);
        mRenderer.setShowGridX(true);      // this will show the grid in  graph
        mRenderer.setShowGridY(false);             
        mRenderer.setAntialiasing(true);
       // mRenderer.setBarSpacing(-0.3);   // Sets the spacing between bars, in bar charts.
        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(Color.parseColor("#000011"));
        mRenderer.setDisplayChartValues(true);
        
        /*Start values for display*/
        mRenderer.setXAxisMin(0);
        mRenderer.setXAxisMax(14);        
        mRenderer.setYAxisMin(-20);
        mRenderer.setYAxisMax(-110);
        mRenderer.setChartValuesTextSize(20);   //for value under bar   
        
        mRenderer.setXLabelsAlign(Align.CENTER);        
        // mRenderer.setXLabelsAngle(270);
        mRenderer.setYLabelsAlign(Align.RIGHT);
        //mRenderer.setYLabelsAngle(360);
        mRenderer.setLabelsTextSize(20);
        
       // mRenderer.addYTextLabel(-50,"Exnt");
        //mRenderer.addYTextLabel(45,"Hot");
      //  mRenderer.setMarginsColor(Color.BLACK);
        mRenderer.setMargins(new int[] {10,50, 10, 10});
        // mRenderer.setYLabels(0);
        
       // mRenderer.setXLabels(0);//disable values on X, just leave "GL_COM" as exmple
    	// loop that goes through list
        int count = 1; //0 - no need
        mRenderer.addXTextLabel(1,"1");
        mRenderer.addXTextLabel(2,"2");
        mRenderer.addXTextLabel(3,"3");
        //for (ScanResult result : scan_results) {
       	// 	mRenderer.addXTextLabel(count,result.SSID);
       // 	count++;
       // }

/*
        -30 - -60 Excellent
		-60dBm -80 Good
		-80dBm -110 Poor
		-----------------------------
		-30dBm = Awesome
		-60dBm = Good
		-80dBm = Meh
		-90dBm = Bad 
		
        */
        mRenderer.setPanEnabled(true);    // will fix the chart position
       
        //Color.argb(0, 255, 255, 255)
        //http://stackoverflow.com/questions/11309065/my-android-achartengine-is-already-working-but-how-to-make-it-look-good
        //http://stackoverflow.com/questions/10809477/bar-graph-using-achartengine
        
       /* 
        SimpleSeriesRenderer r = mRenderer.getSeriesRendererAt(0);       
        r.setDisplayChartValues(true);
        r.setChartValuesTextSize(42);        
        r.setChartValuesSpacing(3);
        r.setGradientEnabled(true);
        r.setGradientStart(-40, Color.BLUE);
        r.setGradientStop(-120, Color.GREEN); */
        
        mRenderer.getSeriesRendererAt(0).setGradientEnabled(true);
        mRenderer.getSeriesRendererAt(0).setGradientStart(-40, Color.BLUE);
        mRenderer.getSeriesRendererAt(0).setGradientStop(-120,Color.parseColor("#00000000"));
        
        mRenderer.getSeriesRendererAt(1).setGradientEnabled(true);
        mRenderer.getSeriesRendererAt(1).setGradientStart(-40, Color.GREEN);
        mRenderer.getSeriesRendererAt(1).setGradientStop(-120,Color.parseColor("#00000000"));
        
        mRenderer.getSeriesRendererAt(2).setGradientEnabled(true);
        mRenderer.getSeriesRendererAt(2).setGradientStart(-40, Color.MAGENTA);
        mRenderer.getSeriesRendererAt(2).setGradientStop(-120,Color.parseColor("#00000000"));
     
        mRenderer.getSeriesRendererAt(3).setGradientEnabled(true);
        mRenderer.getSeriesRendererAt(3).setGradientStart(-50, Color.YELLOW);
        mRenderer.getSeriesRendererAt(3).setGradientStop(-120,Color.parseColor("#00000000"));
     
     
        Intent intent = ChartFactory.getBarChartIntent(context, dataSet, mRenderer,Type.DEFAULT);
       
        return intent;
	}
	/*
	public Intent BACKUP(Context context) {
	    XYMultipleSeriesDataset series = new XYMultipleSeriesDataset();
	    XYValueSeries newTicketSeries = new XYValueSeries("New Tickets");
	    newTicketSeries.add(1f, 2, 14);
	    newTicketSeries.add(2f, 2, 12);
	    newTicketSeries.add(3f, 2, 18);
	    newTicketSeries.add(4f, 2, 5);
	    newTicketSeries.add(5f, 2, 1);
	    series.addSeries(newTicketSeries);
	    XYValueSeries fixedTicketSeries = new XYValueSeries("Fixed Tickets");
	    fixedTicketSeries.add(1f, 1, 7);
	    fixedTicketSeries.add(2f, 1, 4);
	    fixedTicketSeries.add(3f, 1, 18);
	    fixedTicketSeries.add(4f, 1, 3);
	    fixedTicketSeries.add(5f, 1, 1);
	    series.addSeries(fixedTicketSeries);

	    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
	    renderer.setAxisTitleTextSize(16);
	    renderer.setChartTitleTextSize(20);
	    renderer.setLabelsTextSize(15);
	    renderer.setLegendTextSize(15);
	    renderer.setMargins(new int[] { 20, 30, 15, 0 });
	    XYSeriesRenderer newTicketRenderer = new XYSeriesRenderer();
	    newTicketRenderer.setColor(Color.BLUE);
	    renderer.addSeriesRenderer(newTicketRenderer);
	    XYSeriesRenderer fixedTicketRenderer = new XYSeriesRenderer();
	    fixedTicketRenderer.setColor(Color.GREEN);
	    renderer.addSeriesRenderer(fixedTicketRenderer);
	    
	    renderer.setDisplayChartValues(true);
	    
	   // renderer.getSeriesRendererAt(0).setGradientEnabled(true);
        //renderer.getSeriesRendererAt(0).setGradientStart(1, Color.RED);
        //renderer.getSeriesRendererAt(0).setGradientStop(2,Color.parseColor("#00000000"));

	    setChartSettings(renderer, "Project work status", "Priority", "", 0.5, 5.5, 0, 5, Color.GRAY,
	        Color.LTGRAY);
	    renderer.setXLabels(7);
	    renderer.setYLabels(0);
	    renderer.setShowGrid(false);
	    return ChartFactory.getBubbleChartIntent(context, series, renderer, "Project tickets");
	  }
*/
	
	 
}

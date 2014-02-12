package com.example.wifi;



import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
 


public class ActivityTwo extends Activity {
	public static TextView rowtablename,rowssid_1,rowssid_2,rowssid_3;
	public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.activity_two);
	      Log.e("MY TAG :", "onCreate = ActivityTwo");
	 /*     GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {	    	      
	    	      new GraphViewData(1, 6.0d), new GraphViewData(2, 6.0d),
					new GraphViewData(3, 5.0d), new GraphViewData(4,5.0d),new GraphViewData(5, 10.0d), new GraphViewData(6,21.0d)
	    	});*/
	      
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
	    	
	      
	     /* LineGraphView graphView = new LineGraphView(
	    	      this // context
	    	      , "GraphViewDemo" // heading
	    	);
	    	graphView.addSeries(exampleSeries); // data
	    	 
	    	LinearLayout layout = (LinearLayout) findViewById(R.id.graph1);
	    	layout.addView(graphView);
	    	*/
	    	
	      /*
	      GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] { 
                  new GraphViewData(1, 40) 
                  , new GraphViewData(2, 12) 
                  , new GraphViewData(3, 7)
                  , new GraphViewData(2, 8)
                  , new GraphViewData(2, 10)
                  , new GraphViewData(3, 26)
                  , new GraphViewData(1, 37)
                  , new GraphViewData(1, 53)
                  , new GraphViewData(3, 253)
        }); 
            GraphView graphView = new BarGraphView( 
                  this // context 
                  , "Job Status Graph" // heading 
            ); 
            graphView.addSeries(exampleSeries); // data 
            LinearLayout layout = (LinearLayout) findViewById(R.id.layout); 
            layout.addView(graphView);  
	      */
	      /*
//	      rowtablename = (TextView)findViewById(R.id.textTableName);	    
	     // rowssid_1 = (TextView)findViewById(R.id.textView31);
	      //rowssid_2 = (TextView)findViewById(R.id.textView41);
	      //rowssid_3 = (TextView)findViewById(R.id.textView47);
	      
	     // Intent i = new Intent(this, MainActivity.class);
	     // Intent intent = new Intent(ActivityTwo.this,MainActivity.class).putExtra("myCustomerObj",this);		      
	      
	      Intent intent_1 = getIntent();	      
	      String fName = intent_1.getStringExtra("FirstRow");
	      rowssid_1.setText(fName);
	      Log.d("MY TAG :", "rowssid_1 = " + fName);
	      String sName = intent_1.getStringExtra("SecondRow");
	      rowssid_2.setText(sName);
	      Log.d("MY TAG :", "rowssid_2 = " + sName);  
	   
	      final ArrayList<String> apnames = intent_1.getStringArrayListExtra("AP_NAMES");
	      Log.d("MY TAG :", "apnames = " +  apnames.get(0));
	      Log.d("MY TAG :", "apnames = " +  apnames.get(1));
	      Log.d("MY TAG :", "apnames = " +  apnames.get(2));
	      //rowssid_1.setText(apnames.get(0));
	      //rowssid_2.setText(apnames.get(1));
	      //rowssid_3.setText(apnames.get(1));
	      */
	  }
  
}



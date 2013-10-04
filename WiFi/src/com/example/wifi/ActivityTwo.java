package com.example.wifi;



import java.io.Serializable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


//Deneme dene = new Deneme(4,"Mustafa");
//Intent i = new Intent(this, Y.class);
//i.putExtra("sampleObject", dene);

public class ActivityTwo extends Activity {
	public static TextView rowtablename,rowssid_1,rowssid_2,rowssid_3;
	public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.activity_two);	      
//	      rowtablename = (TextView)findViewById(R.id.textTableName);	    
	      rowssid_1 = (TextView)findViewById(R.id.textView31);
	  //    rowssid_2 = (TextView)findViewById(R.id.textView41);
	    //  rowssid_3 = (TextView)findViewById(R.id.textView47);
	     // Intent i = new Intent(this, MainActivity.class);
	     // Intent intent = new Intent(ActivityTwo.this,MainActivity.class).putExtra("myCustomerObj",this);	      
	      Intent intent_1 = getIntent();
	      
	      String fName = intent_1.getStringExtra("FirstRow");
	      rowssid_1.setText(fName);
	      Log.d("MY TAG :", "rowssid_1 = " + fName);  
	      
	  }
  
}



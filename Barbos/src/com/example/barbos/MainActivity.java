package com.example.barbos;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.content.*;

public class MainActivity extends Activity {
	
	TextView view_status;
	Button button_ok;
	Button button_cancel;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
       setContentView(R.layout.activity_main);
       view_status = (TextView)findViewById(R.id.statusButton);
   	   button_ok = (Button)findViewById(R.id.buttonOk);
   	   button_cancel = (Button)findViewById(R.id.buttonCancel);
   	   //Method with 2 handlers for each button
   /*	   OnClickListener oclBtnCancel = new OnClickListener() {		
		@Override
		public void onClick(View v) {
			view_status.setText("PRESS CANCEL!");			
		}
	};
	button_cancel.setOnClickListener(oclBtnCancel);
	
		OnClickListener oclBtnOk = new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				view_status.setText("PRESS OK !");
			}
		};
	button_ok.setOnClickListener(oclBtnOk);
	*/
   	   OnClickListener oclBtnGeneral = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.buttonOk:
				view_status.setText("BUTTON OK");
				break;
			case R.id.buttonCancel:
				view_status.setText("BUTTON CANCEL");
				break;
			}			
		}
	};
	button_ok.setOnClickListener(oclBtnGeneral);
	button_cancel.setOnClickListener(oclBtnGeneral);
   	   
    }

  //  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
   // @Deprecated
    public void startDiscover(View v)
    { 	
      	CharSequence text = "You have clicked Button 2";
      	
    	int duration = Toast.LENGTH_SHORT;
    	Context context = getBaseContext();
    	//Show pop-up window
    	Toast msg = Toast.makeText(context,text,duration);    	
    	msg.setGravity(2, 10, 50);    	
    	msg.show();
    }    
}




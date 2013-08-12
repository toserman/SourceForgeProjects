package com.example.lesson12log;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	TextView tvOut;
	Button buttOk;
	Button buttCancel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d ("MY TAG","FIND VIEW COMPONENTS");
		tvOut = (TextView)findViewById(R.id.textView2);
		buttOk = (Button)findViewById(R.id.buttonOk);
		buttCancel = (Button)findViewById(R.id.buttonCancel);
		
		buttOk.setOnClickListener(this);
		buttCancel.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		Log.d("MY TAG 1","SWITCH on ACTION");
		switch (v.getId()) {
		case R.id.buttonOk:
			tvOut.setText("PRESSED OK");
			Toast.makeText(getApplicationContext(), "PRESSED OK", Toast.LENGTH_LONG).show();
			break;		
		case R.id.buttonCancel:
			tvOut.setText("PRESSED CANCEL");
		break;
		}		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

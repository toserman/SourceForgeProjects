package com.example.lesson_11_resvalue_folder;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		LinearLayout llBottom = (LinearLayout)findViewById(R.id.llBottom);
		LinearLayout ttBottom = (LinearLayout)findViewById(R.id.llTop);
		TextView tvBottom = (TextView)findViewById(R.id.tvBottom);
		Button btnBottom = (Button)findViewById(R.id.btnBottom);
		
		llBottom.setBackgroundResource(R.color.llBottomColor);
		ttBottom.setBackgroundResource(R.color.llTopColor);
		tvBottom.setText(R.string.tvBottomText);
		btnBottom.setText(R.string.btnBottomText);		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

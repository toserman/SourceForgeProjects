package com.example.lesson13createsimplemenu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	      // TODO Auto-generated method stub
	      
	      menu.add("menu1");
	      menu.add("menu2");
	      menu.add("menu3");
	      menu.add("menu4");
	      
	      return super.onCreateOptionsMenu(menu);
	    }

}



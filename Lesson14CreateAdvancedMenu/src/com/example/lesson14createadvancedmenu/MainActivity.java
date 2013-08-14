package com.example.lesson14createadvancedmenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView tv;
	CheckBox ch;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tv = (TextView)findViewById(R.id.textView);
		ch = (CheckBox)findViewById(R.id.chbExtMenu);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		menu.add(0, 1, 0, "add");
	    menu.add(0, 2, 0, "edit");
	    menu.add(0, 3, 3, "delete");
	    menu.add(1, 4, 1, "copy");
	    menu.add(1, 5, 2, "paste");
	    menu.add(1, 6, 4, "exit");
	      
		return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// пункты меню с ID группы = 1 видны, если в CheckBox стоит галка
	      menu.setGroupVisible(1, ch.isChecked());
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub		
	      StringBuilder sb = new StringBuilder();

	      // Выведем в TextView информацию о нажатом пункте меню 
	      sb.append("Item Menu");
	      sb.append("\r\n groupId: " + String.valueOf(item.getGroupId()));
	      sb.append("\r\n itemId: " + String.valueOf(item.getItemId()));
	      sb.append("\r\n order: " + String.valueOf(item.getOrder()));
	      sb.append("\r\n title: " + item.getTitle());
	      tv.setText(sb.toString());
	      
		return super.onOptionsItemSelected(item);
	}
	

}

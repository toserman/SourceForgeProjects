package com.example.wifi;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class CustomDialogWindow extends Dialog implements
    android.view.View.OnClickListener {

  public Activity c;
  public Dialog d;
  public Button yes, no;
  public Typeface font_roboto;
  public AssetManager mngr; //For fonts
  WifiManager wifi_mngr;
  TextView title,message; 
  
  public CustomDialogWindow(Activity a,Context context) {
    super(a);
    mngr = context.getAssets();
    // TODO Auto-generated constructor stub
    this.c = a;
    wifi_mngr = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);  
   
    Log.d("MY Constructor ", "Constructor CSListAdapter ");
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.dialog_window);
    font_roboto = Typeface.createFromAsset(mngr, "fonts/Roboto-Regular.ttf");
    yes = (Button) findViewById(R.id.btn_yes);
    no = (Button) findViewById(R.id.btn_no);
    title = (TextView)findViewById(R.id.title_dialog);
    message = (TextView)findViewById(R.id.message_dialog);    
    yes.setOnClickListener(this);
    no.setOnClickListener(this);
    
    //set font
    yes.setTypeface(font_roboto);
    no.setTypeface(font_roboto);
    title.setTypeface(font_roboto);
    message.setTypeface(font_roboto);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
    case R.id.btn_yes:    	 
    	  //yes.setBackgroundColor(0x000000);
    	yes.setTextColor(Color.BLACK);
    	Log.d("MY TAG ","PRESS YES"); 	
    	Intent intent = new Intent(this.c, MainActivity.class); 
    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);             
    	wifi_mngr.setWifiEnabled(true);
        this.c.startActivity(intent);
      dismiss();
      break;
    case R.id.btn_no:    	
    	Log.d("MY TAG ","PRESS NO");
    	if (wifi_mngr.getWifiState() ==  wifi_mngr.WIFI_STATE_DISABLED)
    		  Log.d("MY ON_CREATE ", "WIFI_STATE_DISABLED" );
    	  this.c.finish();
      dismiss();
      break;
    default:
      break;
    }
  //  dismiss();
  }
}





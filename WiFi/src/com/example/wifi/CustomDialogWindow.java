package com.example.wifi;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Parcelable;
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
  ProgressDialog dialog;
 // MyProgressDialog myicon;
  MyProgressDialog myicon = new MyProgressDialog(getContext());
  public CustomDialogWindow(Activity a,Context context) {
    super(a);
    mngr = context.getAssets();
    // TODO Auto-generated constructor stub
    this.c = a;
    wifi_mngr = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);  
    dialog = new ProgressDialog(context,R.style.NewDialog);    
    Log.d("MY Constructor ", "Constructor CustomDialogWindow ");
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
    	yes.setTextColor(Color.BLUE);
    	Log.d("MY TAG ","PRESS YES"); 	
    	//Intent intent = new Intent(this.c, MainActivity.class); 
    	//intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);             
    	wifi_mngr.setWifiEnabled(true);
    	 myicon.show(getContext(),"Please Wait ...",true,true);
    	// Intent intent = new Intent(getContext(), MainActivity.class);
    	  //intent.putExtra("PROGRESS_ICON", (Parcelable)myicon);
	    	
		    //startActivity(intent);
    	/*
    	dialog.setMessage("Please wait...");
    	dialog.setIndeterminate(true);
    	dialog.setCancelable(true);//false
    	dialog.show();
    	 */
    	
        //this.c.startActivity(intent);
      dismiss();
      break;
    case R.id.btn_no:    	
    	Log.d("MY TAG ","PRESS NO");
    	no.setTextColor(Color.BLUE);
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





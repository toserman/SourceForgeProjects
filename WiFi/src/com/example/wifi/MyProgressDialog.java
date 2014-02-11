package com.example.wifi;

import android.app.ActionBar.LayoutParams;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.graphics.Typeface;

public class MyProgressDialog extends Dialog {
	
	public Typeface font_roboto;
	
    public MyProgressDialog show(Context context, CharSequence title,
            boolean indeterminate, boolean cancelable) {
    	Log.d("MY MyProgressDialog ", "Constructor MyProgressDialog");
        MyProgressDialog dialog = new MyProgressDialog(context);
       
       // holder.ssidname.setTypeface(font_roboto);
        dialog.setTitle(title);       
        dialog.setCancelable(cancelable);
        
        //((TextView)dialog.findViewById(R.style.NewDialog)).setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/FONT"));
        //For icon
        LayoutParams layoutparam = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);        
      //  layoutparam.height = 80;
        /* Add icon*/
        dialog.addContentView(new ProgressBar(context),layoutparam);        
        dialog.show();

        return dialog;
    }

	public MyProgressDialog(Context context) {	    	
        super(context, R.style.NewDialog);
        //Typeface face=Typeface.createFromAsset(context.getAssets(),"fonts/FONT"); 
        
    }	
		
}

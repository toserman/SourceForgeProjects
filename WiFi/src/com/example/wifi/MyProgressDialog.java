package com.example.wifi;

import android.app.Dialog;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

public class MyProgressDialog extends Dialog {
    public MyProgressDialog show(Context context, CharSequence title,
            boolean indeterminate, boolean cancelable) {
    	Log.d("MY MyProgressDialog ", "Constructor MyProgressDialog");
        MyProgressDialog dialog = new MyProgressDialog(context);
        dialog.setTitle(title);       
        dialog.setCancelable(cancelable);
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
    }
	
}

package com.example.barbos;
import android.app.Activity;
import android.net.nsd.*;
import android.widget.Toast;


public class Networking extends Activity {
	public Networking () {	
	//	Toast msg = Toast.makeText(getBaseContext(), "ALARM", Toast.LENGTH_LONG);
	//	msg.show();
	}
	
	public void registerService(int port) {
	    // Create the NsdServiceInfo object, and populate it.
	   // NsdServiceInfo serviceInfo  = new NsdServiceInfo();
	    // The name is subject to change based on conflicts
	    // with other services advertised on the same network.
	    //serviceInfo.setServiceName("NsdChat");
	    //serviceInfo.setServiceType("_http._tcp.");
	    //serviceInfo.setPort(port);	    
	}

}

package com.example.wifi;

import java.util.List;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomScanListAdapter extends BaseAdapter {
	 
    private List<ScanResult> listData;
    private LayoutInflater layoutInflater;
    public AssetManager mngr; //For fonts
    public WifiManager wifiservice;
    public WifiInfo wifi_info;
    public Typeface font_roboto;
    public int counter,temp_counter;
    static int call_counter;
    
    public CustomScanListAdapter(Context context, List<ScanResult> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
        mngr = context.getAssets();
        font_roboto = Typeface.createFromAsset(mngr, "fonts/Roboto-Regular.ttf");
        wifiservice = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        wifi_info = wifiservice.getConnectionInfo();

        Log.d("MY Constructor ", "Constructor CSListAdapter ");
    }
    
    public void setData(List<ScanResult> listData)
    {
    	this.listData = listData;	
    	        
        Log.d("MY CSListAdapter ", "setData() ");
    }
 
    @Override
    public int getCount() {    	
  //  	Log.d("MY CSListAdapter: ", "getCount() size list = " + Integer.toString(listData.size()));
        return listData.size();
    }
 
    @Override
    public Object getItem(int position) {    	
  //  	Log.d("MY CSListAdapter: ", "getItem() position = " + Integer.toString(position));
        return listData.get(position);
    }
 
    @Override
    public long getItemId(int position) {    	
  //  	Log.d("MY CSListAdapter: ", "getItemId() position = " + Integer.toString(position));
    //	Log.d("MY CSListAdapter: ", "getItemId(): " + listData.get(position).capabilities);
    //  Log.d("MY CSListAdapter: ", "getItemId(): " + listData.get(position).SSID);
    //	Log.d("MY CSListAdapter: ", "getItemId(): " + listData.get(position).BSSID);
        return position;
    }
    
    public void setIcons ( ViewHolder inholder,List<ScanResult> inlistData,int position) {
    	//Find connected AP 
    	if(inlistData.get(position).BSSID.matches(wifi_info.getBSSID()))
    		inholder.wifi_state_icon.setImageResource(R.drawable.internet_radio_new);  
        else
        	inholder.wifi_state_icon.setImageResource(0);        	
              
          
         if(inlistData.get(position).capabilities.contains("WPA2"))
         {
         	inholder.crypted_mode.setText("WPA2");
     		inholder.wifi_capab_icon.setImageResource(R.drawable.encrypted); 	
         } else if (inlistData.get(position).capabilities.contains("WPA")) {        
          	inholder.crypted_mode.setText("WPA");
         	inholder.wifi_capab_icon.setImageResource(R.drawable.encrypted);	
         } else if (inlistData.get(position).capabilities.contains("WPS")) {
          	inholder.crypted_mode.setText("WPS");
         	inholder.wifi_capab_icon.setImageResource(R.drawable.encrypted);        	
         } else if (inlistData.get(position).capabilities.contains("WEP")) {
         	inholder.crypted_mode.setText("WEP");
         	inholder.wifi_capab_icon.setImageResource(R.drawable.encrypted); 	
         } else if (inlistData.get(position).capabilities.contains("ESS")) {
         	inholder.crypted_mode.setText("");
         	inholder.wifi_capab_icon.setImageResource(R.drawable.decrypted);
         } else { 
         	inholder.wifi_capab_icon.setImageResource(0);
         }        
    }

    public View getView(int position, View convertView, ViewGroup parent) {
    //	 Log.d("MY CSListAdapter: ", "Start getView() " + Integer.toString(call_counter++));
    	 	ViewHolder holder; 
        
        	//Log.d("MY CSListAdapter", "getView()");        	
    	 //	Log.d("MY CSListAdapter: position =  ", Integer.toString(position));
        if (convertView == null) {        	
            convertView = layoutInflater.inflate(R.layout.scan_listview, null);            
           // for (ScanResult result : listData)    		
            //	Log.d("MY CSListAdapter CHECK: SSID: ", result.SSID);                
            

            
            holder = new ViewHolder();
            holder.ap_symbol_icon = (ImageView)convertView.findViewById(R.id.icon);         
            holder.ssidname = (TextView)convertView.findViewById(R.id.ap_ssid);
            holder.bssid = (TextView)convertView.findViewById(R.id.ap_bssid);
            holder.channelfreq = (TextView) convertView.findViewById(R.id.ap_freq);
            holder.rssilevel = (TextView) convertView.findViewById(R.id.ap_rssi);
            holder.crypted_mode = (TextView) convertView.findViewById(R.id.crypted_mode);
            holder.wifi_state_icon = (ImageView)convertView.findViewById(R.id.wifi_connect);
            holder.wifi_capab_icon = (ImageView)convertView.findViewById(R.id.wifi_capabilities);          
           
            convertView.setTag(holder);
           
        } else {
            holder = (ViewHolder)convertView.getTag();
        }  
        
        //}
   //   Log.d("MY CSListAdapter: SSID: ", listData.get(position).SSID +
    //		" position = " + Integer.toString(position));      
      //  Log.d("MY getView() !!!  ", Integer.toString(listData.get(1).level) + " dBM");
        
        holder.ssidname.setText(listData.get(position).SSID);
      //  holder.bssid.setText("("+listData.get(position).BSSID +")");
        holder.channelfreq.setText(String.valueOf("Freq: " + listData.get(position).frequency) + " MHz");
        holder.rssilevel.setText(String.valueOf("RSSI: " + listData.get(position).level) + " dBm");     
       
        holder.ssidname.setTypeface(font_roboto);
        holder.bssid.setTypeface(font_roboto);
        holder.channelfreq.setTypeface(font_roboto);
        holder.crypted_mode.setTypeface(font_roboto);
        holder.rssilevel.setTypeface(font_roboto);
        
        /*Recognize capabilities and connection state*/
        setIcons(holder,listData,position);        
        		
        return convertView;
    }
 
    static class ViewHolder {
        TextView ssidname;
        TextView bssid;
        TextView channelfreq;
        TextView rssilevel;
        TextView crypted_mode;
        ImageView ap_symbol_icon;
        ImageView wifi_state_icon;
        ImageView wifi_capab_icon;        
    }
 
}

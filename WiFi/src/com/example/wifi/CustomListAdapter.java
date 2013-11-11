package com.example.wifi;

import java.util.ArrayList;

import android.content.Context;
import android.content.ClipData.Item;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class CustomListAdapter extends BaseAdapter {
	 
    private ArrayList<ScanItem> listData;
    private LayoutInflater layoutInflater;
 
    public CustomListAdapter(Context context, ArrayList<ScanItem> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
        
        Log.d("MY TAG ", "Constructor CustomListAdapter ");
    }

    @Override
    public int getCount() {    	
        return listData.size();
    }
 
    @Override
    public Object getItem(int position) {    	
        return listData.get(position);
    }
 
    @Override
    public long getItemId(int position) {    	
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;     
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.scan_listview, null);
            holder = new ViewHolder();
            holder.ap_symbol_icon = (ImageView)convertView.findViewById(R.id.icon);         
            holder.ssidname = (TextView)convertView.findViewById(R.id.ap_ssid);
            holder.bssid = (TextView)convertView.findViewById(R.id.ap_bssid);
            holder.channelfreq = (TextView) convertView.findViewById(R.id.ap_freq);
            holder.rssilevel = (TextView) convertView.findViewById(R.id.ap_rssi);
            holder.crypted_mode = (TextView) convertView.findViewById(R.id.crypted_mode);
            holder.wifi_state_icon = (ImageView)convertView.findViewById(R.id.wifi_connect);
            holder.wifi_capab_icon = (ImageView)convertView.findViewById(R.id.wifi_capabilities);
            Log.d("MY TAG ", "Get flag = " + listData.get(position).getConnectFlag());
           
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
      //  holder.headlineView.setText(listData.get(position).getHeadline());
        //holder.reporterNameView.setText("By, " + listData.get(position).getReporterName());
    //    holder.reportedDateView.setText(listData.get(position).getDate());
        //if(listData.get(position).getConnectFlag())            	
       
        holder.ssidname.setText(listData.get(position).getSsid());
        holder.bssid.setText("("+listData.get(position).getBSSID()+")");
        holder.channelfreq.setText(String.valueOf("Freq: " + listData.get(position).getChannelFreq()) + " MHz");
        holder.rssilevel.setText(String.valueOf("RSSI: " + listData.get(position).getRSSIlevel()) + " dBm");
       
        if(listData.get(position).getConnectFlag() == true){
        	Log.d("MY TAG ", "Adapter TRUE SSID:" + listData.get(position).getSsid());
        	Log.d("MY TAG ", "Adapter TRUE BSSID:" + listData.get(position).getBSSID());
        	//holder.wifi_state_icon.setImageResource(R.drawable.wifi_connected);  
        	holder.wifi_state_icon.setImageResource(R.drawable.internet_radio_new);  
        } else {
        	holder.wifi_state_icon.setImageResource(0);        	
        }
       
            
        Log.d("MY TAG ", "Adapter: " + listData.get(position).getCipherType());
        if( listData.get(position).getCipherType() == "WPA2" ||
             listData.get(position).getCipherType() == "WPA" ||
             listData.get(position).getCipherType() == "WPS" ){
        	//holder.wifi_capab_icon.setImageResource(R.drawable.padlock_closed);
        	//holder.wifi_capab_icon.setImageResource(R.drawable.wireless_new);
        	holder.crypted_mode.setText(listData.get(position).getCipherType());
        	holder.wifi_capab_icon.setImageResource(R.drawable.encrypted);
        	Log.d("MY TAG ", "WPA2 " + listData.get(position).getCipherType());            
    	} else {    		
    		holder.wifi_capab_icon.setImageResource(0);
    		//holder.wifi_capab_icon.setImageResource(R.drawable.decrypted);
    	}    	
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
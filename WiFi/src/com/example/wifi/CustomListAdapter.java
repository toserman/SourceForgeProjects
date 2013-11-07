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
            Log.d("MY TAG ", "Get flag = " + listData.get(position).getConnectFlag());
           
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
      //  holder.headlineView.setText(listData.get(position).getHeadline());
        //holder.reporterNameView.setText("By, " + listData.get(position).getReporterName());
    //    holder.reportedDateView.setText(listData.get(position).getDate());
        //if(listData.get(position).getConnectFlag())            	
        holder.wifi_state_icon = (ImageView)convertView.findViewById(R.id.wifi_connect);
        holder.ssidname.setText(listData.get(position).getSsid());
        holder.bssid.setText("("+listData.get(position).getBSSID()+")");
        holder.channelfreq.setText(String.valueOf("Freq: " + listData.get(position).getChannelFreq()) + " MHz");
        holder.rssilevel.setText(String.valueOf("RSSI: " + listData.get(position).getRSSIlevel()) + " dBm");
       
        if(listData.get(position).getConnectFlag() == true){
        	Log.d("MY TAG ", "Adapter TRUE SSID:" + listData.get(position).getSsid());
        	Log.d("MY TAG ", "Adapter TRUE BSSID:" + listData.get(position).getBSSID());
        	holder.wifi_state_icon.setImageResource(R.drawable.wifi_connected);       
        } else {
        	holder.wifi_state_icon.setImageResource(0);        	
        }
        return convertView;
    }
 
    static class ViewHolder {
        TextView ssidname;
        TextView bssid;
        TextView channelfreq;
        TextView rssilevel;
        ImageView ap_symbol_icon;
        ImageView wifi_state_icon;
        //TextView reportedDateView;
    }
 
}
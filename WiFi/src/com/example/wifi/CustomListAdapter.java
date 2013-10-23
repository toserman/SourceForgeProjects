package com.example.wifi;

import java.util.ArrayList;

import android.content.Context;
import android.content.ClipData.Item;
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
    	Log.d("MY TAG ", "getCount");
        return listData.size();
    }
 
    @Override
    public Object getItem(int position) {
    	Log.d("MY TAG ", "getItem ");
        return listData.get(position);
    }
 
    @Override
    public long getItemId(int position) {
    	Log.d("MY TAG ", "getItemId ");
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Log.d("MY TAG ", "getView ");
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.scan_listview, null);
            holder = new ViewHolder();
            holder.ap_symbol = (ImageView)convertView.findViewById(R.id.icon);
            holder.ssidname = (TextView) convertView.findViewById(R.id.ap_ssid);
            holder.channelfreq = (TextView) convertView.findViewById(R.id.ap_freq);
            holder.rssilevel = (TextView) convertView.findViewById(R.id.ap_rssi);            
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
      //  holder.headlineView.setText(listData.get(position).getHeadline());
        //holder.reporterNameView.setText("By, " + listData.get(position).getReporterName());
    //    holder.reportedDateView.setText(listData.get(position).getDate());
        holder.ssidname.setText(listData.get(position).getSsid());
        holder.channelfreq.setText(String.valueOf("Freq: " + listData.get(position).getChannelFreq()) + " MHz");
        holder.rssilevel.setText(String.valueOf("RSSI: " + listData.get(position).getRSSIlevel()) + " dBm");       
        
        return convertView;
    }
 
    static class ViewHolder {
        TextView ssidname;
        TextView channelfreq;
        TextView rssilevel;
        ImageView ap_symbol;
        //TextView reportedDateView;
        
    }
 
}
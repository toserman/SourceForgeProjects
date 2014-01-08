package com.example.wifi;

import java.util.ArrayList;

import android.content.Context;
import android.content.ClipData.Item;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
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
    public AssetManager mngr; //For fonts
    public Typeface font_roboto;
    
    public CustomListAdapter(Context context, ArrayList<ScanItem> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
        mngr = context.getAssets();
        font_roboto = Typeface.createFromAsset(mngr, "fonts/Roboto-Regular.ttf");
        Log.d("MY Constructor ", "Constructor CustomListAdapter ");
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
       
        holder.ssidname.setText(listData.get(position).getSsid());
       // holder.bssid.setText("("+listData.get(position).getBSSID()+")");
        holder.channelfreq.setText(String.valueOf("Freq: " + listData.get(position).getChannelFreq()) + " MHz");
        holder.rssilevel.setText(String.valueOf("RSSI: " + listData.get(position).getRSSIlevel()) + " dBm");
       
      
      // Typeface font_roboto = Typeface.createFromAsset(mngr, "fonts/Roboto-Regular.ttf");
     //  Typeface type = Typeface.createFromAsset(mngr, "fonts/Roboto-Medium.ttf");
      // Typeface type = Typeface.createFromAsset(mngr, "fonts/Roboto-MediumItalic.ttf");
        //Typeface type = Typeface.createFromAsset(mngr, "fonts/Roboto-Light.ttf");
        //Typeface type = Typeface.createFromAsset(mngr, "fonts/Roboto-Thin.ttf");
        //Typeface type = Typeface.createFromAsset(mngr, "fonts/Roboto-Condensed.ttf");
        holder.ssidname.setTypeface(font_roboto);
        holder.bssid.setTypeface(font_roboto);
        holder.channelfreq.setTypeface(font_roboto);
        holder.crypted_mode.setTypeface(font_roboto);
        holder.rssilevel.setTypeface(font_roboto);

        if(listData.get(position).getConnectFlag() == true){
        	Log.d("MY TAG ", "Adapter TRUE SSID:" + listData.get(position).getSsid());
        	Log.d("MY TAG ", "Adapter TRUE BSSID:" + listData.get(position).getBSSID());
        	//holder.wifi_state_icon.setImageResource(R.drawable.wifi_connected);  
        	holder.wifi_state_icon.setImageResource(R.drawable.internet_radio_new);  
        } else {
        	holder.wifi_state_icon.setImageResource(0);        	
        }     
                 
        if( listData.get(position).getCipherType() == "WPA2" ||
             listData.get(position).getCipherType() == "WPA" ||
             listData.get(position).getCipherType() == "WPS" ||
             listData.get(position).getCipherType() == "WEP" ){
        	//holder.wifi_capab_icon.setImageResource(R.drawable.padlock_closed);
        	//holder.wifi_capab_icon.setImageResource(R.drawable.wireless_new);
        	holder.crypted_mode.setText(listData.get(position).getCipherType());
        	holder.wifi_capab_icon.setImageResource(R.drawable.encrypted);
        	Log.d("MY TAG ", "ENCRYPTED: " + listData.get(position).getCipherType() + " :"
        		  + listData.get(position).getSsid());            
    	} else {   		
    		holder.wifi_capab_icon.setImageResource(0);    		
    	}
        if( listData.get(position).getCipherType() == "ESS")
        {
        	holder.crypted_mode.setText(listData.get(position).getCipherType());
        	holder.wifi_capab_icon.setImageResource(R.drawable.decrypted);
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
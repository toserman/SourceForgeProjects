package com.example.wifi;

import android.content.Context;
import android.util.Log;

public class ScanItem {
	public String ssid;
	private String bssid;
    private int chanfreq;
    public int channel;
    public int rssi;
    public int old_rssi;
    private boolean connect_flag;
    private String cipher;
    public int apcolor;
    
    //SSID name
    public String getSSID(){
        return ssid;
    } 
    public void setSSID(String ssid){
        this.ssid = ssid;
    }
    
    //BSSID - MAC of AP
    public String getBSSID(){
        return bssid;
    } 
    public void setBSSID(String bssid){
        this.bssid = bssid;
    }
 
    //Channel frequency level
    public int getChannelFreq(){
        return chanfreq;
    }
    public void setChannelFreq(int ch_freq){
        this.chanfreq = ch_freq;
    }
    //Channel number 
    public int getChannelNum(){
        return channel;
    }
    public void setChannelNum(int ch_num){
        this.channel = ch_num;
    }
    //Handle AP color for draw in graph
    public int getAPcolor(){
        return apcolor;
    } 
    public void setAPcolor(int apcolor){
        this.apcolor = apcolor;
    }
     
    //RSSI level for AP
    public int getRSSIlevel(){
        return rssi;
    } 
    public void setRSSIlevel(int rssi){
        this.rssi = rssi;
    }
    public int getOldRSSIlevel(){
        return old_rssi;
    } 
    public void setOldRSSIlevel(int rssi){
        this.old_rssi = rssi;
    }
    
    //Flag for connect status
    public boolean getConnectFlag(){
        return connect_flag;
    } 
    public void setConnectFlag(boolean conflag){
        this.connect_flag = conflag;
    }
     
    //Key management,authentication type (WPA,WPA2,ESS)
    public void setCipherType(String ciphertype){
        this.cipher = ciphertype;
    }    
    public String getCipherType(){
        return cipher;
    }
}

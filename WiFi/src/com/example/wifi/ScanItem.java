package com.example.wifi;

import android.content.Context;
import android.util.Log;

public class ScanItem {
	private String ssid;
	private String bssid;
    private int chanfreq;
    private int rssi;
    private boolean connect_flag;
    private String cipher;
    
    //SSID name
    public String getSsid(){
        return ssid;
    } 
    public void setSsid(String ssid){
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
     
    //RSSI level for AP
    public int getRSSIlevel(){
        return rssi;
    } 
    public void setRSSIlevel(int rssi){
        this.rssi = rssi;
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

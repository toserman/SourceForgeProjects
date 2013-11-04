package com.example.wifi;

public class ScanItem {
	private String ssid;
	private String bssid;
    private int chanfreq;
    private int rssi;
    private boolean connect_flag;
            
    public String getSsid(){
        return ssid;
    }
 
    public void setSsid(String ssid){
        this.ssid = ssid;
    }
    
    public String getBSSID(){
        return bssid;
    }
 
    public void setBSSI(String bssid){
        this.bssid = bssid;
    }
 
    public int getChannelFreq(){
        return chanfreq;
    }
     public void setChannelFreq(int ch_freq){
        this.chanfreq = ch_freq;
    }
    public int getRSSIlevel(){
        return rssi;
    }
 
    public void setRSSIlevel(int rssi){
        this.rssi = rssi;
    }
   
    public boolean getConnectFlag(){
        return connect_flag;
    } 
    public void setConnectFlag(boolean conflag){
        this.connect_flag = conflag;
    }
}

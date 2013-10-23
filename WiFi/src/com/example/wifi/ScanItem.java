package com.example.wifi;

public class ScanItem {
	private String ssid;
    private int chanfreq;
    private int rssi;
        
    public String getSsid(){
        return ssid;
    }
 
    public void setSsid(String ssid){
        this.ssid = ssid;
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
   
  
    //@Override
    //public String toString() {
      //  return "[ headline=" + headline + ", reporter Name=" +
       //         reporterName + " , date=" + date + "]";
    //}
}

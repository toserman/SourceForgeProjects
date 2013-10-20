package com.example.wifi;

public class ScanItem {
	private String ssid;
    private String chanfreq;
     
    public String getSsid() {
        return ssid;
    }
 
    public void setSsid(String ssid) {
        this.ssid = ssid;
    }
 
    public String getReporterName() {
        return chanfreq;
    }
 
    public void setReporterName(String reporterName) {
        this.chanfreq = reporterName;
    }
 
    
    //@Override
    //public String toString() {
      //  return "[ headline=" + headline + ", reporter Name=" +
       //         reporterName + " , date=" + date + "]";
    //}
}

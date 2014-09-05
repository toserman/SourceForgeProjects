package com.example.wifi;

public class TestScanResult {

	public String SSID;
	public String BSSID;
	public int freq;
	public int rssi;
	public int level;
	
	public TestScanResult (String SSID, String BSSID, int freq, int rssi) {
		this.BSSID = BSSID;
		this.freq = freq;
		this.SSID = SSID;
		this.level = rssi;
		// TODO Auto-generated constructor stub
	}
	
}

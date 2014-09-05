package com.example.wifi;

public class TestScanResult {

	public String SSID;
	public String BSSID;
	public int freq;
	public int rssi;
		
	public TestScanResult (String SSID, String BSSID, int freq, int rssi) {
		this.BSSID = BSSID;
		this.freq = freq;
		this.SSID = SSID;
		this.rssi = rssi;
		// TODO Auto-generated constructor stub
	}
	
}

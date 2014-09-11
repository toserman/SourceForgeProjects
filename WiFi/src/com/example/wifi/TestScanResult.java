package com.example.wifi;

import java.util.ArrayList;

public class TestScanResult {

	public String SSID;
	public String BSSID;
	public int freq;
	public int rssi;
//		   1	2	3	4    5     6    7    8    9    10   11  12   13    14
	//{0,2412,2417,2422,2427,2432,2437,2442,2447,2452,2457,2462,2467,2472,2484};
		
	public TestScanResult (String SSID, String BSSID, int freq, int rssi) {
		this.BSSID = BSSID;
		this.freq = freq;
		this.SSID = SSID;
		this.rssi = rssi;
		// TODO Auto-generated constructor stub
	}
	
	static void FillListOne(ArrayList<TestScanResult> list_one) {
		TestScanResult ap1 = new TestScanResult("ANTON","01:11:22:33:31:55",2452,-90);
		TestScanResult ap2 = new TestScanResult("MOTHERLAND","01:11:22:33:41:55",2412,-70);
		TestScanResult ap3 = new TestScanResult("WINK","01:11:22:33:44:50",2457,-80);
		TestScanResult ap4 = new TestScanResult("JAPAN","01:11:22:33:31:55",2484,-67);
		TestScanResult ap5 = new TestScanResult("TOKIO","01:11:22:33:44:51",2437,-77);
		list_one.add(ap1);
		list_one.add(ap2);
		list_one.add(ap3);
		list_one.add(ap4);
		list_one.add(ap5);
	}
	
	static void FillListSecond(ArrayList<TestScanResult> list_second) {
		TestScanResult ap1 = new TestScanResult("ANTON","01:11:22:33:31:55",2452,-70);
		TestScanResult ap2 = new TestScanResult("LAND","01:11:22:33:45:55",2462,-85);
		TestScanResult ap3 = new TestScanResult("SWIFT","01:11:22:33:24:50",2427,-80);
		TestScanResult ap4 = new TestScanResult("SPAIN","01:11:22:33:12:55",2472,-87);
		TestScanResult ap5 = new TestScanResult("FUNNY","01:11:22:33:12:51",2422,-92);
		TestScanResult ap6 = new TestScanResult("BUGS","01:11:22:33:44:59",2447,-72);
		list_second.add(ap1);
		list_second.add(ap2);
		list_second.add(ap3);
		list_second.add(ap4);
		list_second.add(ap5);
		list_second.add(ap6);
	}
	
	static void FillListSecondUpdated(ArrayList<TestScanResult> list_second) {
		TestScanResult ap1 = new TestScanResult("ANTON","01:11:22:33:31:55",2452,-70);
		TestScanResult ap2 = new TestScanResult("LAND","01:11:22:33:45:55",2462,-85);
		TestScanResult ap3 = new TestScanResult("SWIFT","01:11:22:33:24:50",2427,-80);
		TestScanResult ap4 = new TestScanResult("SPAIN","01:11:22:33:12:55",2472,-87);
		TestScanResult ap5 = new TestScanResult("FUNNY","01:11:22:33:12:51",2422,-92);
		TestScanResult ap6 = new TestScanResult("BUGS","01:11:22:33:44:59",2447,-90);
		list_second.add(ap1);
		list_second.add(ap2);
		list_second.add(ap3);
		list_second.add(ap4);
		list_second.add(ap5);
		list_second.add(ap6);
	}
	static void FillListThird(ArrayList<TestScanResult> list_third) {
		TestScanResult ap1 = new TestScanResult("HELLO","01:11:21:33:44:55",2417,-90);
		TestScanResult ap2 = new TestScanResult("COOL","01:11:23:33:41:55",2452,-70);
		TestScanResult ap3 = new TestScanResult("ENGLAND","01:11:24:33:44:50",2457,-80);
		TestScanResult ap4 = new TestScanResult("LONDON","01:11:25:33:31:55",2484,-67);
		TestScanResult ap5 = new TestScanResult("TOM","01:11:26:33:44:51",2437,-87);
		TestScanResult ap6 = new TestScanResult("TOM","01:11:27:33:44:51",2467,-57);
		list_third.add(ap1);
		list_third.add(ap2);
		list_third.add(ap3);
		list_third.add(ap4);
		list_third.add(ap5);
		list_third.add(ap6);
	}
	
}

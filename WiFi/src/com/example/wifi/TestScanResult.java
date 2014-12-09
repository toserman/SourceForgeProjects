package com.example.wifi;

import java.util.ArrayList;
import java.util.List;

import android.net.wifi.ScanResult;
import android.util.Log;

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
	
	static void FillListFromWIFI(List<ScanResult> results,ArrayList<TestScanResult> inp_list) {
//		for (int i = 0; i < results.size(); i++)			
//		{
//			TestScanResult ap_obj = new TestScanResult(results.get(i).SSID,
//													   results.get(i).BSSID,
//													   results.get(i).frequency,
//													   results.get(i).level);
//			inp_list.add(ap_obj);
//		}
//		
		TestScanResult ap_obj0 = new TestScanResult(results.get(0).SSID,
													   results.get(0).BSSID,
													   results.get(0).frequency,
													   results.get(0).level);
		TestScanResult ap_obj1 = new TestScanResult(results.get(1).SSID,
													   results.get(1).BSSID,
													   results.get(1).frequency,
													   results.get(1).level);
		TestScanResult ap_obj2 = new TestScanResult(results.get(2).SSID,
				   results.get(2).BSSID,
				   results.get(2).frequency,
				   results.get(2).level);
		TestScanResult ap_obj3 = new TestScanResult(results.get(2).SSID,
				   results.get(3).BSSID,
				   results.get(3).frequency,
				   results.get(3).level);
//
		inp_list.add(ap_obj0);
		inp_list.add(ap_obj1);
		inp_list.add(ap_obj2);
		inp_list.add(ap_obj3);
		

		
//		Log.e("MY FillListFromWIFI ", "PRINT inp_list AFTER get from WIFI = ");
//		for (int i=0; i < inp_list.size();i++)
//    	{
//  		  Log.d("MY FillListFromWIFI ", "i = " + Integer.toString(i) + " " + inp_list.get(i).SSID);
//  		  Log.d("MY FillListFromWIFI ", "i = " + Integer.toString(i) + " " + inp_list.get(i).rssi);
//
//    	}		
		
	}
	
	static void FillListOne(ArrayList<TestScanResult> list_one) {
		TestScanResult ap1 = new TestScanResult("ANTON","01:11:22:33:31:55",2452,-90);
		TestScanResult ap2 = new TestScanResult("MOTHERLAND","01:11:22:33:41:55",2412,-70);
		TestScanResult ap3 = new TestScanResult("WINK","01:11:22:33:44:50",2457,-80);
		TestScanResult ap4 = new TestScanResult("JAPAN","01:11:22:33:31:55",2484,-67);
		TestScanResult ap5 = new TestScanResult("TOKIO","01:11:22:33:44:51",2437,-77);
		TestScanResult ap6 = new TestScanResult("LAND","01:11:22:33:45:55",2462,-55);
		
		list_one.add(ap1);
		list_one.add(ap2);
		list_one.add(ap3);
		list_one.add(ap4);
		list_one.add(ap5);
		list_one.add(ap6);
		
	}
	
	static void FillListSecond(ArrayList<TestScanResult> list_second) {		
		TestScanResult ap1 = new TestScanResult("AND","91:11:22:33:45:55",2462,-85);
//		TestScanResult ap2 = new TestScanResult("MOTHERLAND","01:11:22:33:41:55",2412,-79); SECOND BUG CASE
		TestScanResult ap3 = new TestScanResult("SWIFT","01:11:22:33:24:50",2427,-80);
		TestScanResult ap4 = new TestScanResult("SPAIN","01:11:22:33:12:55",2472,-87);
		TestScanResult ap5 = new TestScanResult("FUNNY","01:11:22:33:12:51",2422,-92);
		TestScanResult ap6 = new TestScanResult("BUGS","01:11:22:33:44:59",2447,-72);
		TestScanResult ap7 = new TestScanResult("HELLLLLLO","01:11:28:33:44:51",2422,-67);
		TestScanResult ap8 = new TestScanResult("PEACE","01:11:29:33:44:51",2422,-57);
		TestScanResult ap9 = new TestScanResult("ANTON","01:11:22:33:31:55",2452,-70);
		
		list_second.add(ap1);
//		list_second.add(ap2);
		list_second.add(ap3);
		list_second.add(ap4);
		list_second.add(ap5);
		list_second.add(ap6);
		list_second.add(ap7);
		list_second.add(ap8);
		list_second.add(ap9);
		
	}
	
	static void FillListSecondUpdated_1(ArrayList<TestScanResult> list_second) {
		TestScanResult ap1 = new TestScanResult("ANTON","01:11:22:33:31:55",2452,-80);
		TestScanResult ap2 = new TestScanResult("LAND","01:11:22:33:45:55",2462,-85);
		TestScanResult ap3 = new TestScanResult("SWIFT","01:11:22:33:24:50",2427,-80);
		TestScanResult ap4 = new TestScanResult("SPAIN","01:11:22:33:12:55",2472,-87);
		TestScanResult ap5 = new TestScanResult("FUNNY","01:11:22:33:12:51",2422,-92);
		TestScanResult ap6 = new TestScanResult("BUGS","01:11:22:30:44:59",2447,-90);
		list_second.add(ap1);
		list_second.add(ap2);
		list_second.add(ap3);
		list_second.add(ap4);
		list_second.add(ap5);		
		list_second.add(ap6);
		
		/*For CHECK COLOR SUPPORT BUGGGGGGGGGGGGGGGGGGG !!!!*/
//		for (int g = 0 ; g < 14; g++)
//		{
//			TestScanResult ap = new TestScanResult("COLOR","01:11:22:33:44:59",2447,-90);
//			list_second.add(ap);
//		}	
		
	}
	
	static void FillListSecondUpdated_2(ArrayList<TestScanResult> list_second) {
		TestScanResult ap1 = new TestScanResult("ANTON","01:11:22:33:31:55",2452,-70);
		TestScanResult ap2 = new TestScanResult("LAND","01:11:22:33:45:55",2462,-89);
		TestScanResult ap3 = new TestScanResult("SWIFT","01:11:22:33:24:50",2427,-80);
		TestScanResult ap4 = new TestScanResult("SPAIN","01:11:22:33:12:55",2472,-67);
		TestScanResult ap5 = new TestScanResult("FUNNY","01:11:22:33:12:51",2422,-89);
		TestScanResult ap6 = new TestScanResult("BUGS","01:11:22:33:44:59",2447,-60);
		TestScanResult ap7 = new TestScanResult("NEW AP","01:11:22:33:44:99",2484,-35);
		list_second.add(ap1);
		list_second.add(ap2);
		list_second.add(ap3);
		list_second.add(ap4);
		list_second.add(ap5);
		list_second.add(ap6);
		list_second.add(ap7);
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
	
	
	static void FillList_On1ch(ArrayList<TestScanResult> list_second) {		
		TestScanResult ap1 = new TestScanResult("MNSPD","91:11:22:33:45:55",2462,-65);
		TestScanResult ap2 = new TestScanResult("GL","01:11:22:33:41:55",2462,-59);
		TestScanResult ap3 = new TestScanResult("MUSIC","01:11:22:33:24:50",2462,-68);
//		TestScanResult ap4 = new TestScanResult("BOOM","01:11:22:33:12:55",2462,-60);
//		TestScanResult ap5 = new TestScanResult("FUNNY","01:11:22:33:12:51",2462,-72);
		
		list_second.add(ap1);
		list_second.add(ap2);
		list_second.add(ap3);
//		list_second.add(ap4);
//		list_second.add(ap5);
	}
	
	static void FillListSecondUpdated_On1ch(ArrayList<TestScanResult> list_second) {
		TestScanResult ap1 = new TestScanResult("MNSPD","91:11:22:33:45:55",2462,-85);
		TestScanResult ap2 = new TestScanResult("GL","01:11:22:33:41:55",2462,-59);
		TestScanResult ap3 = new TestScanResult("MUSIC","01:11:22:33:24:50",2462,-78);
//		TestScanResult ap4 = new TestScanResult("BOOM","01:11:22:33:12:55",2462,-88);
//		TestScanResult ap5 = new TestScanResult("FUNNY","01:11:22:33:12:51",2462,-90);

		list_second.add(ap1);
		list_second.add(ap2);
		list_second.add(ap3);
//		list_second.add(ap4);
//		list_second.add(ap5);		
		
	}
	
}

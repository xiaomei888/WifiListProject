package com.example.wifilistpro.wifi;

public class IControl {
	
	interface View {
		void changeData(String data);
	}
	
	interface Percent{
		void onCreat();
		void onDestory();
		void loadWifiData();
	}

}

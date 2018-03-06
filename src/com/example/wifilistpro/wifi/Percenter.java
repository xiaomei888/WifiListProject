package com.example.wifilistpro.wifi;

import java.util.List;

import com.example.wifilistpro.wifi.IControl.Percent;
import com.example.wifilistpro.wifi.IControl.View;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

/** P部分 */
public class Percenter implements Percent {

	private View mView;
	private Context mContext;

	public Percenter(View view) {
		// TODO Auto-generated constructor stub
		this.mView = view;
		this.mContext = (Context) view;
	}

	@Override
	public void onCreat() {
		// TODO Auto-generated method stub
		// 做一些初始化操作 判断手机是否是6.0及以上，如果是，提示用户开启位置服务

	}

	@Override
	public void onDestory() {
		// TODO Auto-generated method stub
		if (mView != null) {
			mView = null;
		}
	}

	@Override
	public void loadWifiData() {
		// TODO Auto-generated method stub
		mView.changeData(getAllConnectedWifiSsid() + getAllConnectWifiSsid());
	}

	/** 获取所有手机连接过的WiFi名称 */
	private String getAllConnectedWifiSsid() {
		StringBuffer result = null;
		WifiManager wifiManager = (WifiManager) mContext.getSystemService(mContext.WIFI_SERVICE);
		List<WifiConfiguration> wifiConfigurations = wifiManager.getConfiguredNetworks();
		if (wifiConfigurations != null && wifiConfigurations.size() > 0) {
			result = new StringBuffer();
			result.append("所有连接过的WiFi名称：");
			for (int i = 0; i < wifiConfigurations.size(); i++) {
				result.append(wifiConfigurations.get(i).SSID);
				if (i != wifiConfigurations.size() - 1) {
					result.append(",   ");
				}
			}
		} else {
			return "无";
		}
		return result.toString();
	}

	/**
	 * 获取当前手机能搜索到的WiFi名称(部分手机需要打开位置服务才能获取到，如华为) 加入没有打开位置信息时，需要引导用户一步步打开位置信息
	 */
	private String getAllConnectWifiSsid() {
		StringBuffer result = null;
		WifiManager wifiManager = (WifiManager) mContext.getSystemService(mContext.WIFI_SERVICE);
		List<ScanResult> scanResults = wifiManager.getScanResults();
		if (scanResults != null && scanResults.size() > 0) {
			result = new StringBuffer();
			result.append("所有搜索到的WiFi名称：");
			for (int i = 0; i < scanResults.size(); i++) {
				result.append(scanResults.get(i).SSID);
				if (i != scanResults.size() - 1) {
					result.append(",   ");
				}
			}
		} else {
			return "无";
		}
		return result.toString();
	}

}

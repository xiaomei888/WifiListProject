package com.example.wifilistpro.wifi;

import java.util.List;

import com.example.wifilistpro.R;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/** 
 * 本是要写人脸识别的代码，结果开始写wifi
 * 
 * 1.判断是否开启定位，没有开启定位信息需引导用户开启定位。 
 * 2.获取当前连接的WiFi的名称，即ssid 
 * 
 * 修改成MVP？
 * */
public class MainActivity extends Activity implements OnClickListener {
	
	private TextView tv1;
	//权限请求码
	private static final int PERMISSION_REQUEST_CODE = 0;
	//两个权限需要动态申请
	private static final String[] NEEDED_PERMISSIONS = new String[]{
			Manifest.permission.ACCESS_COARSE_LOCATION,
			Manifest.permission.ACCESS_FINE_LOCATION
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViewById(R.id.button).setOnClickListener(this);
	}

	/** 获取正在连接的WiFi名称 */
	private String getConnectWifiSsid() {
		WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		// Log.d("wifiInfo", wifiInfo.toString());
		// Log.d("SSID", wifiInfo.getSSID());
		return wifiInfo.getSSID();
	}

	/** 获取所有手机连接过的WiFi名称 */
	private String getAllConnectedWifiSsid() {
		StringBuffer result = null;
		WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
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
		}else{
			return "无";
		}
		return result.toString();
	}

	/** 获取当前手机能搜索到的WiFi名称(部分手机需要打开位置服务才能获取到，如华为) 
	 * 加入没有打开位置信息时，需要引导用户一步步打开位置信息*/
	private String getAllConnectWifiSsid() {
		StringBuffer result = null;
		WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
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
		}else{
			return "无";
		}
		return result.toString();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	 /**
     * 检查是否已经授予权限
     * @return
     */
    private boolean checkPermission() {
    	PackageManager pManager = getPackageManager();
        for (String permission : NEEDED_PERMISSIONS) {
//            if (pManager.checkPermission("", "com.longrise.fingerprintidentificationpro")
//                    != PackageManager.PERMISSION_GRANTED) {
//                return false;
//            }
        }
        return true;
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		tv1 = findViewById(R.id.textview);
		tv1.setText(
				"当前WiFi名称：" + getConnectWifiSsid() + "\n" + getAllConnectedWifiSsid() + "\n" + getAllConnectWifiSsid());
	}
}

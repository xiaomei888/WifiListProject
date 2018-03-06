package com.example.wifilistpro.wifi;

import com.example.wifilistpro.R;
import com.example.wifilistpro.wifi.IControl.View;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

/** 使用MVP框架进行改装 */
public class WifiActivity extends Activity implements View, OnClickListener {

	private TextView tv1;
	private Percenter mPercenter;
	private static final int LOCATION_REQUEST_CODE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mPercenter = new Percenter(this);
		findViewById(R.id.button).setOnClickListener(this);
		tv1 = findViewById(R.id.textview);
		mPercenter.onCreat();
	}

	private boolean isHaveAdrress() {
		if (!isLocationEnabled()) {
			startPermissionsActivity();
		}
		// 这只是判断有没有权限，不是判断系统有没有打开位置
		// if (ContextCompat.checkSelfPermission(this,
		// Manifest.permission.ACCESS_FINE_LOCATION) !=
		// PackageManager.PERMISSION_GRANTED) {
		// ActivityCompat.requestPermissions(this, new String[] {
		// Manifest.permission.ACCESS_COARSE_LOCATION },
		// LOCATION_REQUEST_CODE);
		// startPermissionsActivity();
		//
		// }
		// return (ContextCompat.checkSelfPermission(this,
		// Manifest.permission.ACCESS_COARSE_LOCATION) ==
		// PackageManager.PERMISSION_GRANTED) ? true : false;
		return isLocationEnabled();
	}

	//这种判断方法是正确的
	public boolean isLocationEnabled() {
		int locationMode = 0;
		String locationProviders;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			try {
				locationMode = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
			} catch (Settings.SettingNotFoundException e) {
				e.printStackTrace();
				return false;
			}
			return locationMode != Settings.Secure.LOCATION_MODE_OFF;
		} else {
			locationProviders = Settings.Secure.getString(getContentResolver(),
					Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
			return !TextUtils.isEmpty(locationProviders);
		}
	}

	private void startPermissionsActivity() {
		PermissionsActivity.startActivityForResult(this, LOCATION_REQUEST_CODE,
				new String[] { Manifest.permission.ACCESS_COARSE_LOCATION });
	}

	@Override
	public void changeData(String data) {
		tv1.setText("数据为：" + data + isHaveAdrress());
	}

	// requestPermissions方法执行后的回调方法
	/*
	 * requestCode:相当于一个标志， permissions：需要传进的permission，不能为空
	 * grantResults：用户进行操作之后，或同意或拒绝回调的传进的两个参数;
	 */
	@SuppressLint("NewApi")
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
			@NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		// 这里实现用户操作，或同意或拒绝的逻辑
		/*
		 * grantResults会传进 android.content.pm.PackageManager.PERMISSION_GRANTED
		 * 或 android.content.pm.PackageManager.PERMISSION_DENIED两个常，
		 * 前者代表用户同意程序获取系统权限，后者代表用户拒绝程序获取系统权限
		 */
		if (LOCATION_REQUEST_CODE == requestCode) {
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {// 获取了系统权限
				Toast.makeText(this, "进行下一步", 0).show();
			} else {
				Toast.makeText(this, "当前没有该权限，请前往设置页打开权限", 0).show();
				// ActivityCompat.requestPermissions(this, new String[] {
				// Manifest.permission.ACCESS_COARSE_LOCATION },
				// LOCATION_REQUEST_CODE);
			}
		}
	}

	@Override
	public void onClick(android.view.View v) {
		mPercenter.loadWifiData();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (tv1 != null) {
			tv1 = null;
		}
		if (mPercenter != null) {
			mPercenter.onDestory();
			mPercenter = null;
		}
	}

}

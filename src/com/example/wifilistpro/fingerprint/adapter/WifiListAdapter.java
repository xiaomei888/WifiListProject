package com.example.wifilistpro.fingerprint.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import com.example.wifilistpro.R;
import com.example.wifilistpro.fingerprint.app.AppContants;
import com.example.wifilistpro.fingerprint.bean.WifiBean;

public class WifiListAdapter extends BaseAdapter {

	private Context mContext;
	private List<WifiBean> resultList;
	private onItemClickListener onItemClickListener;

	public void setOnItemClickListener(WifiListAdapter.onItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	public WifiListAdapter(Context mContext, List<WifiBean> resultList) {
		this.mContext = mContext;
		this.resultList = resultList;
	}

	public interface onItemClickListener {
		void onItemClick(View view, int postion, Object o);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return resultList != null ? resultList.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return resultList != null ? resultList.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_wifi_list, null, false);
		}

		TextView tvItemWifiName, tvItemWifiStatus;
		tvItemWifiName = (TextView) convertView.findViewById(R.id.tv_item_wifi_name);
		tvItemWifiStatus = (TextView) convertView.findViewById(R.id.tv_item_wifi_status);

		final WifiBean bean = resultList.get(position);
		tvItemWifiName.setText(bean.getWifiName());
		tvItemWifiStatus.setText("(" + bean.getState() + ")");

		// 可以传递给adapter的数据都是经过处理的，已连接或者正在连接状态的wifi都是处于集合中的首位，所以可以写出如下判断
		if (position == 0 && (AppContants.WIFI_STATE_ON_CONNECTING.equals(bean.getState())
				|| AppContants.WIFI_STATE_CONNECT.equals(bean.getState()))) {
			tvItemWifiName.setTextColor(mContext.getResources().getColor(R.color.homecolor1));
			tvItemWifiStatus.setTextColor(mContext.getResources().getColor(R.color.homecolor1));
		} else {
			tvItemWifiName.setTextColor(mContext.getResources().getColor(R.color.gray_home));
			tvItemWifiStatus.setTextColor(mContext.getResources().getColor(R.color.gray_home));
		}

		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onItemClickListener.onItemClick(view, position, bean);
			}
		});
		return convertView;
	}

}

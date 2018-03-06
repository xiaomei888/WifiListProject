package com.example.wifilistpro.fingerprint.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**询问用户开启wifi/位置的Dialog*/
public class OpenSomeDialog{

	private AlertDialog.Builder builder;
	private OpenSomeDialog.OnDialogClickListener dialogClickListener;
	
	public OpenSomeDialog(Context context,String message) {
		builder = new AlertDialog.Builder(context);
		if(builder!=null){
			builder.setTitle("提示").setMessage(message).setCancelable(false).create();
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//确定之后打开进入设置页面
					dialogClickListener.onPositiveClickListener(dialog);
				}
			});
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialogClickListener.onNegativeClickListener(dialog);
				}
			});
//			builder.show();
		}
	}
	
	public void  setOpenSomeDialogListener(OnDialogClickListener listener) {
		this.dialogClickListener = listener;
	}

	public interface OnDialogClickListener{
		void onPositiveClickListener(DialogInterface dialog);
		void onNegativeClickListener(DialogInterface dialog);
	}
	
	public void show() {
		builder.show();
	}
	
}

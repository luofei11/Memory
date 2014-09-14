package com.lf.memory;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;

import com.chejo.memory.R;
import com.chejo.root.AppInfoManage;
import com.chejo.root.RootManager;

public class DialogActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_activity);
		txt1 = getString(R.string.clearing);
	}
	@Override
	protected void onResume() {
		super.onResume();
		showDialog();
	}
	@Override
	protected void onPause() {
		super.onPause();
		if(dialog != null && dialog.isShowing())
			dialog.dismiss();
		this.finish();
	}
	private MemoryClearDialog dialog;
	private void showDialog() {
		if(dialog == null){			
			dialog = new MemoryClearDialog(this,R.style.dialog_color);
		}
		if(dialog.isShowing()){
			dialog.dismiss();
			return;
		}
		dialog.exit(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DialogActivity.this.finish();
				dialog.dismiss();
			}
		});
		dialog.clear(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				stopProcess();
			}
		});
		dialog.getWindow().getAttributes().height = SystemData.screenHeight;
		dialog.getWindow().getAttributes().width = SystemData.screenWidth;
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setText();
		long size = AppInfoManage.getInstance(this).getTotalMemory(1);
		String aa = null;
		if(size/1024/1024 < 512){
			aa = 512+" M";
		}else if(size/1024/1024 < 1024){
			aa = 1 +" G";
		}else if((size/1024/1024/1024+0.5) < 2){
			aa = 1.5 +" G";
		}else if((size/1024/1024/1024) < 2){
			aa = 2+" G";
		}else{
			aa = SystemData.dataSizeFormat(AppInfoManage.getInstance(this).getTotalMemory(1));
		}			
		dialog.setClearText(getString(R.string.phone_info), "手机品牌："+android.os.Build.MANUFACTURER+" ，总运行内存"+aa);
		dialog.show();
	}
	/**
	 * 结束进程
	 */
	private void stopProcess() {
		new Thread(new Runnable() {			
			@Override
			public void run() {
				ArrayList<RunningAppProcessInfo> runInfos = AppInfoManage.getInstance(DialogActivity.this).getRunningProcess(2);
				if(runInfos != null){
					int size = runInfos.size();
					if(RootManager.getInstance(DialogActivity.this).grantPermission()){
						handler.sendEmptyMessage(0);
						for(int i=0;i<size;i++){
							String packageName = runInfos.get(i).pkgList[0];
							if(packageName.equals(DialogActivity.this.getPackageName())){						
								continue;
							}
							if(AppInfoManage.getInstance(DialogActivity.this).isSystemApplication(packageName)){
								AppInfoManage.getInstance(DialogActivity.this).stopProcess(packageName);
							}else{	
								if(!packageName.contains("input"))
									RootManager.getInstance(DialogActivity.this).forceStop("am force-stop "+packageName);
							}
							Message msg = handler.obtainMessage();
							msg.what = 1;
							msg.obj = packageName;
							handler.sendMessage(msg);
							if(i ==size-1){
								handler.sendEmptyMessage(2);
							}
						}
					}else{						
						for(int i=0;i<size;i++){
							String packageName = runInfos.get(i).pkgList[0];
							if(packageName.equals(DialogActivity.this.getPackageName())){						
								continue;
							}
							if(!packageName.contains("launcher") && !packageName.equals("android") && !packageName.contains("inputmethod")){						
								AppInfoManage.getInstance(DialogActivity.this).stopProcess(runInfos.get(i).pkgList[0]);
							}
							Message msg = handler.obtainMessage();
							msg.what = 1;
							msg.obj = packageName;
							handler.sendMessage(msg);
							if(i ==size-1){
								handler.sendEmptyMessage(2);
							}
						}
					}
				}
			}
		}).start();
	}

	private String txt1;
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				dialog.showClear();
				break;
			case 1:
				dialog.setClearText(txt1, msg.obj.toString());
				break;
			case 2:
				if(dialog != null){					
					dialog.stopClear();
					dialog.setText();
					dialog.setClearText(getString(R.string.phone_info_over), getString(R.string.phone_info_over_info));
				}
				break;
			}
		};
	};
}
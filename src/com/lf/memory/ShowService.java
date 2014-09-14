package com.lf.memory;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.chejo.root.AppInfoManage;

public class ShowService extends Service {
	public static final String ACTION_CHECK = "check_memory";
	public static final String ACTION_SHOW_DIALOG = "show_dialog";
	private static MemoryView view;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public void onCreate() {
		super.onCreate();
		if(view == null)
			view = new MemoryView(this);
		view.show();
		getCpuInfo();
	}
   
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		setAlarm(System.currentTimeMillis()+5000);
		if(intent != null){
			String action = intent.getAction();
			if(action != null){	
				if(action.equals(ACTION_CHECK)){
					getCpuInfo();
				}
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}

	private void getCpuInfo(){
		long total = AppInfoManage.getInstance(this).getTotalMemory(1);
		long avilible = AppInfoManage.getInstance(this).getNotUseMemory();
		int pro1 = (int) ((total - avilible) *100/ total);
		view.setPercent(pro1);
	}
	
	private void setAlarm(long time) {
		AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);  
		Intent intent = new Intent(this, ShowService.class);
		intent.setAction(ACTION_CHECK);
		PendingIntent pi = PendingIntent.getService(this, 0,intent , Intent.FLAG_ACTIVITY_NEW_TASK);
		am.set(AlarmManager.RTC_WAKEUP, time, pi);
	}
	
}
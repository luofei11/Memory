package com.lf.memory;

import java.util.List;

import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chejo.memory.R;
import com.chejo.root.AppInfoManage;

public class MemoryClearDialog extends Dialog {
	private Button clear;
	private ImageView exit,anim_image;
	private Animation anim;
	private TextView running,aviliable,notify1,notify2;
	public MemoryClearDialog(Context context,int style) {
		super(context,style);
		setContentView(R.layout.dialog_layout);
		anim = AnimationUtils.loadAnimation(context, R.anim.circle);
		clear = (Button) findViewById(R.id.clear_btn);
		exit = (ImageView) findViewById(R.id.exit);
		running = (TextView) findViewById(R.id.running);
		aviliable  =(TextView) findViewById(R.id.aviliabel);
		anim_image = (ImageView) findViewById(R.id.clear_image);
		notify2  = (TextView) findViewById(R.id.notify2);
		notify1  = (TextView) findViewById(R.id.notify1);
	}
	
	private void setRunText(String txt){
		int len = txt.length();
		SpannableString ss = new SpannableString(txt);
		ss.setSpan(new AbsoluteSizeSpan(35), 0, len - 8, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new AbsoluteSizeSpan(17), len - 8, len - 6, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), len -6, len, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
		running.setText(ss);
	}
	private void setAviliableText(String txt){
		int len = txt.length();
		SpannableString ss = new SpannableString(txt);
		ss.setSpan(new AbsoluteSizeSpan(35), 0, len - 8, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new AbsoluteSizeSpan(17), len - 8, len - 6, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), len -6, len, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
		aviliable.setText(ss);
	}
	public void exit(android.view.View.OnClickListener listener){
		exit.setOnClickListener(listener);
	}
	public void clear(android.view.View.OnClickListener listener){
		clear.setOnClickListener(listener);
	}
	public void showClear(){
		ToastUtils.log("showClear");
		clear.setVisibility(View.GONE);
		anim_image.setVisibility(View.VISIBLE);
		anim.reset();
		anim_image.startAnimation(anim);
	}
	public void stopClear(){
		clear.setVisibility(View.VISIBLE);
		anim_image.setVisibility(View.GONE);
		anim.cancel();
		anim_image.setAnimation(null);
	}
	public void setText(){
		long a = AppInfoManage.getInstance(getContext()).getNotUseMemory();
		String aviliable = SystemData.dataSizeFormat(a);
		setAviliableText(aviliable+"\n剩余可用内存");
		
		List<RunningAppProcessInfo> infos = AppInfoManage.getInstance(getContext()).getRunningProcess(2);
		int size = infos.size();
		setRunText(size+"个\n后台运行程序");
	}
	public void setClearText(String txt1,String txt2){
		notify1.setText(txt1);
		notify2.setText(txt2);
	}
}
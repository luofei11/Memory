package com.lf.memory;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class ToastUtils {
	/**
	 * �ռǵ�����
	 * @param msg
	 */
	public static void log(Object msg){
		Log.d("tag",String.valueOf(msg));
	}
	/**
	 * ��ʾ�����Toast
	 * @param context �����Ķ���
	 * @param text ��ʾ������
	 */
	public static void showToast(Context context,String text){
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
	/**
	 * ��ݷ�λ��ʾtoast
	 * @param context �����Ķ���
	 * @param text ��ʾ������
	 * @param gravity ���뷽ʽ
	 */
	public static void makeToast(Context context,String text,int gravity){
		Toast t = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		t.setGravity(gravity, 0, 0);
		t.show();
	}
	/**
	 * �Զ���Toast��View����ʾ��ʽ
	 * @param context �����Ķ���
	 * @param view ��Ҫ��ʾ�Ĳ�����ͼ
	 */
	public static void makeToast(Context context,View view){
		Toast t = Toast.makeText(context, "", Toast.LENGTH_SHORT);
		t.setView(view);
		t.show();
	}
}
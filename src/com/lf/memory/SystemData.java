package com.lf.memory;

import java.text.DecimalFormat;

import android.os.Environment;

public class SystemData {
	public static final int defaultWidth = 480;
	static final int defaultHeight = 800;
	public static int screenWidth;
	public static int screenHeight;
	public static float density;
	public static boolean isSdExists() {
		if(Environment.getExternalStorageState() != null)
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
				return true;
		return false;
	}
	public static int dip2px(int value){
		return (int) (density*value+0.5);
	}
	/**
	 * 返回字节为单位的字符串格式，保留两位小数
	 * @param size 以byte为单位的数字
	 * @return
	 */
	public static String dataSizeFormat(long size) {
		DecimalFormat formater = new DecimalFormat("####.#");
		if (size < 1024) {
			return size + "b";
		} else if (size < (1 << 20)){ // 左移20位，相当于1024 * 1024
			float kSize = size/1024.0f; 
			return formater.format(kSize) + "K";
		} else if (size < (1 << 30)){ // 左移30位，相当于1024 * 1024 * 1024
			float mSize = size/1024.0f/1024.0f;
			return formater.format(mSize) + "M";
		} else {
			float gSize = size/1024.0f/1024.0f/1024.0f;
			return formater.format(gSize) + "G";
		} 
	}
}
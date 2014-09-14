package com.lf.memory;

import com.chejo.memory.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MemoryView extends View implements OnClickListener{
	private TextView text;
	private Context c;
	private WindowManager mWM;
	private WindowManager.LayoutParams mWMParams; 
	private View win;
	private FrameLayout child;
	private int width = 80;
	public MemoryView(Context context) {
		super(context);
		c = context;
		mWM = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
		mWMParams =  new WindowManager.LayoutParams();
		mWMParams.type = 2003;
		mWMParams.flags = 40;
		mWMParams.format = -3;
	}

	public void show() {
		if(win == null)
			win = LayoutInflater.from(c).inflate(R.layout.ctrl_window, null);
		win.setBackgroundColor(Color.TRANSPARENT);
		child = (FrameLayout) win.findViewById(R.id.child);
		win.setOnClickListener(this);
		text = (TextView) win.findViewById(R.id.txt);
		mWMParams.width = width;
		mWMParams.height = width;
		mWMParams.x = -400;//x位置
		mWMParams.y = -200;//y位置
		mWM.addView(win, mWMParams);
		applyRotation(child, 0, 360);
	}
	private void applyRotation(View container,float start, float end) {
		container.measure(-1, -1);
        final float centerX = container.getMeasuredWidth() / 2.0f;
        final float centerY = container.getMeasuredHeight() / 2.0f;
        ToastUtils.log(centerX+":"+centerY);
        final Rotate3dAnimation rotation =
                new Rotate3dAnimation(start, end, centerX, centerY, 0f, true);
        rotation.setDuration(300);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        container.startAnimation(rotation);
    }
	public void setPercent(int percent){
		text.setText(percent+"%");
	}

	@Override
	public void onClick(View v) {
		if(v instanceof Button){
			clear();
		}else{
			applyRotation(child, 0, 360);
			showDialog();
		}
	}

	private void clear() {
		
	}

	private void showDialog() {
		Intent intent = new Intent(c,DialogActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		c.startActivity(intent);
		ToastUtils.log("showDialog");
	}
}
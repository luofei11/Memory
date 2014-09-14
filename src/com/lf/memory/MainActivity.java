package com.lf.memory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private ImageView image;
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        ToastUtils.log(android.os.Build.MANUFACTURER);
        ToastUtils.log(android.os.Build.PRODUCT);
        getSystemConfig();
        this.startService(new Intent(this,ShowService.class));  
        this.finish();
    } 
    private void getSystemConfig() {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		SystemData.screenHeight = metrics.heightPixels;
		SystemData.screenWidth = metrics.widthPixels;
		SystemData.density = metrics.density;
	}
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
    	super.onWindowFocusChanged(hasFocus);
    	if(hasFocus)
            applyRotation(image,-180, 0);
    }
    private void applyRotation(View container,float start, float end) {
        final float centerX = container.getWidth() / 2.0f;
        final float centerY = container.getHeight() / 2.0f;
        final Rotate3dAnimation rotation =
                new Rotate3dAnimation(start, end, centerX, centerY, 310.0f, true);
        rotation.setDuration(300);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
//    	rotation.setAnimationListener(new DisplayNextView(position));
        container.startAnimation(rotation);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	
    	return super.onTouchEvent(event);
    }
}  
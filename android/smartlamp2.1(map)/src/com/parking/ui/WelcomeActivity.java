package com.parking.ui;

import org.json.JSONException;
import org.json.JSONObject;



import com.example.lanya_lingyang_5.MainActivity;
import com.parking.smarthome.R;
import com.parking.util.MapCommonUtil;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

/**
 * ����ҳ
 * 
 * @ClassName: SplashActivity
 * @Description: TODO
 * @author smile
 * @date 2014-6-4 ����9:45:43
 */
public class WelcomeActivity extends Activity {
	// private static final int GO_HOME = 100;
	private ImageView splashIv;
	private final int DURATION_TIME = 2000;
	private String openuid;

	private SharedPreferences sp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.welcome);
		
		splashIv = (ImageView) findViewById(R.id.welcome_splash_iv);

		try {

			WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
			int screenHeight = wm.getDefaultDisplay().getHeight();
			Bitmap orgBitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.welcome);
			int graphicsWidth = orgBitmap.getWidth();
			int graphicsHeight = orgBitmap.getHeight();
			int sceenWidth = MapCommonUtil.getScreenWidth(this);
			graphicsHeight = screenHeight * graphicsWidth / sceenWidth; // width
			Bitmap finalBitmap = Bitmap.createBitmap(orgBitmap, 0, 0,
					graphicsWidth, graphicsHeight, null, false);

			splashIv.setImageBitmap(finalBitmap);
		} catch (Exception e) {
			splashIv.setBackgroundResource(R.drawable.welcome);
		}
		 startAni();
//		AlphaAnimation aa = new AlphaAnimation(0.3f,1.0f);
//		aa.setDuration(2000);
//		splashIv.startAnimation(aa);
	}

	 private void startAni() {
	 // TODO Auto-generated method stub
	 AnimationSet aSet = new AnimationSet(true);
	
	 AlphaAnimation alphaAni = new AlphaAnimation(0.3f, 1f);
	 alphaAni.setDuration(DURATION_TIME);
	 ScaleAnimation scaleAni = new ScaleAnimation(1f, 1.1f, 1f, 1.1f,
	 Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
	 0.5f);
	 scaleAni.setDuration(DURATION_TIME);
	
	 aSet.addAnimation(alphaAni);
	 aSet.addAnimation(scaleAni);
	 aSet.setFillAfter(true);
	 splashIv.startAnimation(aSet);
	
	 aSet.setAnimationListener(new AnimationListener() {
	
	 @Override
	 public void onAnimationStart(Animation animation) {
	 // TODO Auto-generated method stub
	
	 }
	
	 @Override
	 public void onAnimationEnd(Animation animation) {
	 // TODO Auto-generated method stub
	
//		   Intent intent = new Intent();
//		   intent.setClass(WelcomeActivity.this, MainActivity.class);
//		   startActivity(intent);
//		   WelcomeActivity.this.finish();
	
	   Intent intent = new Intent();
	   intent.setClass(WelcomeActivity.this, LoginActivity.class);
	   startActivity(intent);
	   WelcomeActivity.this.finish();
	
	 }
	
	 @Override
	 public void onAnimationRepeat(Animation animation) {
	 // TODO Auto-generated method stub
	
	 }
	
	 });
	 }

//	@Override
//	public void onConnectionSuccessed(boolean autoBind) {
//
//	
//
//		Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
//		startActivity(intent);
//		finish();
//	}

	@Override
	public void onBackPressed() {
		finish();
		
	}

	

}

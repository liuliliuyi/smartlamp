package com.parking.util;

import com.parking.smarthome.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ProDialog extends Dialog {
	private Context context;

	public ProDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
		this.setCanceledOnTouchOutside(false);
		init();
	}

	private void init() {

		setContentView(R.layout.progress_dialog);

		ImageView mImageView = (ImageView) findViewById(R.id.loadingIv);

		final AnimationDrawable anim = (AnimationDrawable) mImageView
				.getBackground();

		mImageView.post(new Runnable() {
			@Override
			public void run() {
				anim.start();
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.dismiss();
		}
		return true;
	}

}

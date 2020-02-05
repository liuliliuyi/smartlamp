package com.lingyang.xml;


import com.parking.smarthome.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("NewApi") 
public class Loading_view extends LinearLayout {

	private Context mContext;   
	@SuppressWarnings("unused")
	private RelativeLayout loading_content;   
	private TextView loadingText;   
	public Loading_view(Context context) {     
		super(context);     
		mContext = context;     
		setupView();   
		}   
	public Loading_view(Context context, AttributeSet attrs) {     
		super(context, attrs);     
		mContext = context;     
		setupView();   
		}   
	
	public Loading_view(Context context, AttributeSet attrs, int defStyleAttr) {     
		super(context, attrs, defStyleAttr);     
		mContext = context;     
		setupView();   
		}   
	private void setupView() { 
 
		View.inflate(mContext, R.layout.loading_view_layout, this);     
		loading_content = (RelativeLayout) findViewById(R.id.loading_content);     
		loadingText = (TextView) findViewById(R.id.loading_txt);     
		   
		}   
	public void setMessage(String msg) {     
		loadingText.setText(msg);   
		} 
	 
}

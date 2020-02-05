package com.parking.ui;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;




import com.parking.smarthome.R;
import com.parking.util.ActivityBase;
import com.parking.util.HttpClientUtil;
import com.parking.util.ProDialog;
import com.parking.util.UrlUtil;
import com.parking.widget.AgeDialog;
import com.parking.widget.AlertDiaLog;
import com.xjs.example.time.view.DateTimePickerView;





import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;






public class TongjiActivity extends ActivityBase implements OnClickListener {

	private RelativeLayout top_fanhui;
	private DateTimePickerView mDateTimePickerView;
	

	private String birthday;
	private RelativeLayout  xuanzhe,xuanzhe1;
    private TextView sn,sousuo;
    private ProDialog myDialog;
	private JSONObject goodsList = null;
	private TextView  kaishi,jiesu;
	Handler d = new Handler() {

		public void handleMessage(Message msg) {
			try {

				//  {"code":200,"success":true,"data":0,"msg":"操作成功"}
				if (msg.what == 92) {
					
					String message = goodsList.getString("code");

					if (message != null && message.equals("200")) {
						
						String data=goodsList.getString("data");
						sn.setText(data);
					
					} else {
                        
					}

				}

			} catch (Exception e) {

				e.printStackTrace();
			}
		};

	};
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.shangbao);
		
		
		init();
		Listener();

	}

	/**
	 * 初始化数据
	 */
	private void init() {

		sn = (TextView)findViewById(R.id.sn);
		sousuo= (TextView)findViewById(R.id.sousuo);
		sousuo.setOnClickListener(this);
		TextView toptitile = (TextView)findViewById(R.id.toptitile);
		toptitile.setText("上报统计");
		top_fanhui = (RelativeLayout) findViewById(R.id.top_fanhui);
		top_fanhui.setVisibility(View.VISIBLE);
		
		
		kaishi=(TextView)findViewById(R.id.kaishi);
		jiesu=(TextView)findViewById(R.id.jiesu);
		
		xuanzhe = (RelativeLayout) findViewById(R.id.xuanzhe);
		xuanzhe1 = (RelativeLayout) findViewById(R.id.xuanzhe1);
		xuanzhe.setOnClickListener(this);
		xuanzhe1.setOnClickListener(this);
		
	}
	
	//請求統計
	private void getGoodsList2() {
		myDialog = new ProDialog(TongjiActivity.this, R.style.progressDialog);
		myDialog.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					
					String startTime =kaishi.getText().toString();
					String endTime =jiesu.getText().toString();
					String	url = UrlUtil.getUserUrl() + "blade-lamp/api/device/getDeviceCountByScanTime?startTime="+startTime+"&endTime="+endTime;		
					
					SharedPreferences sp = getSharedPreferences("mrsoft", MODE_PRIVATE);
					String token = sp.getString("token", "token"); 
					String ruslt = HttpClientUtil.get(TongjiActivity.this,url,token);

					if (!(ruslt == null)) {
						goodsList = new JSONObject(ruslt);
					}
					myDialog.dismiss();

				} catch (JSONException e) {
					e.printStackTrace();
				}
				if (!(myDialog == null)) {
					myDialog.dismiss();
				}
				try {
					Message msg = new Message();
					msg.what = 92;
					d.sendEmptyMessage(msg.what);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}).start();

	}
	

	@SuppressLint("SimpleDateFormat") @Override
	public void onClick(View v) {
		
		if (v == top_fanhui) {
			finish();
		}
		if(v==sousuo){
			//get網絡請求
			
			if(!kaishi.getText().toString().equals(jiesu.getText().toString())){
				
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
				
				//日期比较
				Date dt1 = null;
				Date dt2 = null;
				
				try {
					dt1 = df.parse((kaishi.getText().toString()+" "+"8:00"));
					dt2 = df.parse((jiesu.getText().toString()+" "+"8:00"));
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if (dt2.getTime() <= dt1.getTime()) {
					showCustomToast("截止日期要大于起始日期");
					return ;
				}	
			}
			getGoodsList2();
			
		}
		
        if(v==xuanzhe){
        	String flag="1";
        	openTimePicker(flag);
        	
        }
        
        
        
        if(v==xuanzhe1){
        	String flag="2";
        	openTimePicker(flag);
        	
       	 
        }
		
	}
	
//	    private void setDate() {
//	        Calendar c = Calendar.getInstance();
//	        int curYear = c.get(Calendar.YEAR);
//	        int curMonth = c.get(Calendar.MONTH) + 1;//通过Calendar算出的月数要+1
//	        int curDate = c.get(Calendar.DATE);
//
//	    }

	 /**
     * 打开日期-时间控件
	 * @param flag 
     */
    private void openTimePicker(final String flag) {
        mDateTimePickerView = new DateTimePickerView(this, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int id = v.getId();
				if(id == R.id.btn_ok){
					if("1".equals(flag)){
						kaishi.setText(mDateTimePickerView.wheelMain.getTime().substring(0,10));	
					}else{
						jiesu.setText(mDateTimePickerView.wheelMain.getTime().substring(0,10));	
					}
					
					
//					mTime.setText(mDateTimePickerView.wheelMain.getTime());
		            mDateTimePickerView.dismiss();
				}else{
		            mDateTimePickerView.dismiss();
				}
			}
		});
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		 
        mDateTimePickerView.setOutsideTouchable(true);
        mDateTimePickerView.setWidth(wm.getDefaultDisplay().getWidth()-100);
        mDateTimePickerView.showAsDropDown(kaishi);
    }
	   
	/**
	 * 监听
	 */
	private void Listener() {
		top_fanhui.setOnClickListener(this);
	}

	
		
		
	

}

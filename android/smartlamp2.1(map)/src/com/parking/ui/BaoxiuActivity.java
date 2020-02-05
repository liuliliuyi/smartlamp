package com.parking.ui;






import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;






import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.example.jpushdemo.TagAliasOperatorHelper;
import com.example.jpushdemo.TagAliasOperatorHelper.TagAliasBean;

import com.parking.smarthome.R;
import com.parking.util.ActionSheetDialog;
import com.parking.util.ActionSheetDialog.OnSheetItemClickListener;
import com.parking.util.ActionSheetDialog.SheetItemColor;
import com.parking.util.ActivityBase;
import com.parking.util.AppConstants;
import com.parking.util.HttpClientUtil;
import com.parking.util.ProDialog;
import com.parking.util.UrlUtil;



import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BaoxiuActivity extends ActivityBase implements
		OnClickListener, AMapLocationListener {

	private EditText uid;
	private static String editUid;
	
	private Button login;

	private SharedPreferences sp;
	private Editor editor;
	
	private String flag;
	private RelativeLayout top_fanhui;
	JSONObject goodsList = null;
	private int i;

	
	private ProDialog myDialog;
	private String carPlate, balance;
	public static boolean isForeground = false;
	
	private AMapLocationClient locationClient = null;
	private AMapLocationClientOption locationOption = null;
	private  Double  geoLat,geoLng;
	private String longitude,latitude,sn;
	private RelativeLayout  sex1,sex2;
	private TextView sex;
	private EditText sex22;
    private String dealType;
    private final int ResultCode = 54137;
    private String newQrcode;
	Handler d = new Handler() {

		public void handleMessage(Message msg) {
			try {
				if (msg.what == 90) {
					// 更新界面
//  {"success":true,"errorCode":"-1","msg":"登录成功!","body":{"username":"13627233474","name":"立大侠","office":{"id":"4","remarks":null,"createDate":null,"updateDate":null,"name":"北京市项目","sort":30,"parentIds":"0,1,","area":null,"code":null,"type":"2","grade":null,"equipmentNum":null,"totalPower":null,"master":null,"phone":null,"email":null,"companyName":null,"useable":null,"primaryPerson":null,"deputyPerson":null,"childDeptList":null,"parentId":"1"},"mobileLogin":true,"JSESSIONID":"288fda39684f4ee7802b24dc610087d5"}}
					String message = goodsList.getString("success");
                     //JSONObject  body=goodsList.getJSONObject("body");
					if (message != null && message.equals("true")) {
						
						if("待处理".equals(login.getText().toString())){
							
							login.setText("处理中");
						}else if("处理中".equals(login.getText().toString())){
							
							login.setText("处理完");
							login.setVisibility(View.INVISIBLE);
							finish();
							//getGoodsList();
						}
						
					
					
					} else {
						//showCustomToast("用户名和密码错误");
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
		setContentView(R.layout.baoxiu);
		dealType="0";
		i = 0;
		flag = "0";
		init();
		Listener();
//		save();
		

	}

	

	/**
	 * 初始化数据
	 */
	private void init() {
		
		uid = (EditText) findViewById(R.id.baoxiu_adress);
		login = (Button) findViewById(R.id.baoxiu_queding);
		TextView toptitile = (TextView) findViewById(R.id.toptitile);
		toptitile.setText("运维");
		
		sex = (TextView) findViewById(R.id.sex);
		
		sex1 = (RelativeLayout) findViewById(R.id.sex1);
		sex1.setOnClickListener(this);
		
		sex2 = (RelativeLayout) findViewById(R.id.sex2);
//		sex2.setOnClickListener(this);
		
		sex22= (EditText) findViewById(R.id.sex22);
		
		top_fanhui = (RelativeLayout) findViewById(R.id.top_fanhui);
		top_fanhui.setVisibility(View.VISIBLE);
		
		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		longitude = b.getString("longitude");
		latitude = b.getString("latitude");
		flag= b.getString("status");
        sn= b.getString("sn");
        
        sex22.setText(sn);
        
		
		if("0".equals(flag)){
			
			login.setText("待处理");
		}else if("1".equals(flag)){
			
			 login.setText("处理中");
		}else if("2".equals(flag)){
			
			 login.setText("处理完");
			 login.setVisibility(View.INVISIBLE);
			 finish();
		}
		

	}

	
	/**
	 * 监听
	 */
	private void Listener() {
		
		login.setOnClickListener(this);
		top_fanhui.setOnClickListener(this);
		
	}

	/**
	 * 数据的保存
	 */

//	@SuppressWarnings("unchecked")
//	private void save() {
//		sp = getSharedPreferences("mrsoft", MODE_PRIVATE);
//		String username = sp.getString("shouji", "shouji");
//		String password = sp.getString("editPwd", "editPwd");
//
//		if ("shouji".equals(username)) {
//			uid.setText("");
//		} else {
//			uid.setText(username);
//		}
//		
//	}

	@Override
	public void onClick(View v) {
		if (v == login) {
			editUid = uid.getText().toString();
			
			if (editUid.trim().equals("")) {

				showCustomToast("故障描述不能为空");
				return;
			}
			if("0".equals(dealType)){
				showCustomToast("请选择故障类型");
				return;
			}
			
			
			
			i=0;
			
			
			locationClient = new AMapLocationClient(BaoxiuActivity.this
					.getApplicationContext());
			locationOption = new AMapLocationClientOption();
			locationOption
					.setLocationMode(AMapLocationMode.Hight_Accuracy);
			locationClient.setLocationListener(BaoxiuActivity.this);
			initOption();
			locationClient.setLocationOption(locationOption);
			locationClient.startLocation();

			
			
		}
		
		if(v==sex1){
			
			new ActionSheetDialog(BaoxiuActivity.this)
			.builder()
			.setCancelable(false)
			.setCanceledOnTouchOutside(false)
			.addSheetItem("更换sn", SheetItemColor.Blue,
					new OnSheetItemClickListener() {
						@Override
						public void onClick(int which) {
							dealType="1";
							
							 Intent intent = new Intent(BaoxiuActivity.this, CustomCaptureActivity1.class);
				             startActivityForResult(intent,ResultCode);
						    	sex.setText("更换sn");
						}
					})
			.addSheetItem("更换imei", SheetItemColor.Blue,
					new OnSheetItemClickListener() {
						@Override
						public void onClick(int which) {
							dealType="2";
							sex.setText("更换imei");
						}
					})
					
					.addSheetItem("sn和imei同时更换", SheetItemColor.Blue,
					new OnSheetItemClickListener() {
						@Override
						public void onClick(int which) {
							dealType="3";
							 Intent intent = new Intent(BaoxiuActivity.this, CustomCaptureActivity1.class);
				             startActivityForResult(intent,ResultCode);
						 	 sex.setText("sn和imei同时更换");
						}
					})
					.addSheetItem("普通维修不更换设备", SheetItemColor.Blue,
					new OnSheetItemClickListener() {
						@Override
						public void onClick(int which) {
							dealType="4";
							 sex.setText("普通维修不更换设备");
						//	sex.setText("女");
						}
					}).show();
			
		}
		
	
		if (v == top_fanhui) {
//			/**
//			 * 防止点击系统返回键，断开连接,急用急删
//			 */
//			sp = getSharedPreferences("mrsoft", 0);
//			String logout = sp.getString("logout", "logout");
//			if ("1".equals(logout)) {
//				CIMPushManager.destroy(this);
//				editor = sp.edit();
//				editor.remove("logout");
//				editor.commit();
//			}

			finish();
		}
		
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);

		if (requestCode == ResultCode && resultCode == ResultCode) {
			
			Bundle bundle = intent.getExtras();
			newQrcode = bundle.getString("qrcode");
			sex22.setText(newQrcode);
		}

	}


	// 请求网络数据
	private void getGoodsList() {
		myDialog = new ProDialog(BaoxiuActivity.this, R.style.progressDialog);
		myDialog.show();
		new Thread(new Runnable() {

			@Override
			public void run() {
                    
				try {
					
					SharedPreferences sp = getSharedPreferences("mrsoft", MODE_PRIVATE);
					String token = sp.getString("token", "token");
					
					String url = UrlUtil.getUserUrl() + "blade-lamp/api/faultInfo/deal";		
					List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();  
					if("待处理".equals(login.getText().toString())){
						params.add(new BasicNameValuePair("dealStatus","1"));
						params.add(new BasicNameValuePair("longitude1",String.valueOf(geoLng)));
						params.add(new BasicNameValuePair("latitude1",String.valueOf(geoLat)));
							
					}else if("处理中".equals(login.getText().toString())){
						params.add(new BasicNameValuePair("dealStatus","2"));
						params.add(new BasicNameValuePair("longitude2",String.valueOf(geoLng)));
						params.add(new BasicNameValuePair("latitude2",String.valueOf(geoLat)));
						params.add(new BasicNameValuePair("dealType",dealType));
						if("1".equals(dealType)||"3".equals(dealType)){
							params.add(new BasicNameValuePair("newSn",newQrcode));	
						}
					
					
					}
					
					params.add(new BasicNameValuePair("sn",sn));
					params.add(new BasicNameValuePair("dealDesc",editUid));
					
					
					String ruslt = HttpClientUtil.post(BaoxiuActivity.this,url,token, params);
					
//					String ruslt = HttpClientUtil.post1(BaoxiuActivity.this,url, params);
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
					msg.what = 90;
					d.sendEmptyMessage(msg.what);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}).start();

	}

	private boolean isNetworkAvailable() {
		ConnectivityManager mgr = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] info = mgr.getAllNetworkInfo();
		if (info != null) {
			for (int i = 0; i < info.length; i++) {
				if (info[i].getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void onBackPressed() {

		finish();
	}
	
	
	private void initOption() {
		locationOption.setGpsFirst(true);
		locationOption.setInterval(2000);
//		locationOption.setOnceLocation(true);

	}

	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (null != locationClient) {
			locationClient.onDestroy();
			locationClient = null;
			locationOption = null;
		}
	}
	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		if (amapLocation != null) {
			if (amapLocation != null && amapLocation.getErrorCode() == 0) {
				
				geoLat = amapLocation.getLatitude();
				geoLng = amapLocation.getLongitude();
				LatLng latLng = new LatLng(geoLat,geoLng);	
				LatLng latLng1 = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));	
		    	
		    	
		    	if(i==0){
		    		if(AMapUtils.calculateLineDistance(latLng,latLng1)/1000>1.0){
			    		showCustomToast("您不在维修范围内");
		    		    i++;
			    		return;
			    	}
		    		//先判断距离
					if("待处理".equals(login.getText().toString())){
						
						getGoodsList();
					}else if("处理中".equals(login.getText().toString())){
						
						getGoodsList();
					}else if("处理完".equals(login.getText().toString())){
						
					
					}
		    		i++;
		    	}
		    	
				

				
				
				}

			} 
		}

	
	
	
	
	
}

package com.parking.ui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.LocationSource.OnLocationChangedListener;
import com.amap.api.maps.model.LatLng;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import com.parking.adapter.CarAdapter;
import com.parking.adapter.CardetailAdapter;
import com.parking.adapter.MainAdapter;
import com.parking.model.City;
import com.parking.model.Movie;
import com.parking.smarthome.R;

import com.parking.util.ActivityBase;

import com.parking.util.HttpClientUtil;
import com.parking.util.JsonUtil;
import com.parking.util.ProDialog;
import com.parking.util.UrlUtil;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CarnavdetailActivity extends ActivityBase implements
		OnClickListener, AMapLocationListener {

	private ArrayList<City> datas = new ArrayList<City>();
	private SharedPreferences sp;
	private RelativeLayout top_fanhui;
	JSONObject goodsList = null;
	private ListView mycourse_list;
	private MainAdapter adapter;
	private Double geoLat, geoLng;
	
	private AMapLocationClient locationClient = null;
	private AMapLocationClientOption locationOption = null;
	private String flag;
	private int i;
	private ProDialog myDialog;
	private RelativeLayout oder_nofound;
//	JsonUtil.getnav(goodsList.toString(), datas, flag.replaceAll("��","").replaceAll(",","").trim(),
//			geoLat.toString(), geoLng.toString());
	Handler d = new Handler() {

		public void handleMessage(Message msg) {
			try {

				if (msg.what == 91) {
					
					String message = goodsList.getString("success");

					if (message != null && message.equals("true")) {
						

							datas.clear();

							LatLng latLng = new LatLng(geoLat,geoLng);
							JsonUtil.getnewalarm1(goodsList, datas,latLng,flag.replaceAll("��","").replaceAll(",","").trim());

						
						 if(!(mycourse_list==null)){
      
							if (datas.size() == 0) {
								
								oder_nofound.setVisibility(View.VISIBLE);
								mycourse_list.setVisibility(View.GONE);
							} else {
								 adapter = new MainAdapter(
								 CarnavdetailActivity.this, datas);
						         mycourse_list.setAdapter(adapter);
								 oder_nofound.setVisibility(View.GONE);
								 mycourse_list.setVisibility(View.VISIBLE);
								 mycourse_list.setOnItemClickListener(new OnItemClickListener() {
							 			public void onItemClick(AdapterView<?> parent, View v,
							 					int position, long id) {
							 				
							 				showCustomToast("���ڶ�λ,��ȴ�!");
											i= 0;
										    String	geoLng1=datas.get(position).getLongitude().toString();
										    String	geoLat1=datas.get(position).getLatitude().toString();

											//���ﻹ���øߵ�
											if (isNetworkAvailable()) {

												SharedPreferences sp = getSharedPreferences("mrsoft", 0);
												Editor editor = sp.edit();
												editor.putString("jing", geoLat1);
												editor.putString("wei", geoLng1);

												editor.putString("geoLat", geoLat.toString());
												editor.putString("geoLng", geoLng.toString());
												editor.commit();
												Intent it = new Intent();
												it.setClass(CarnavdetailActivity.this, GPSNaviActivity.class);
												startActivity(it);
												i++;

											} else {
												showCustomToast("��λʧ��!");
											}
											
										
							 				
							 			}
							 		});
							}
//						    } else {
//							if (datas1.size() == 0) {
//								showCustomToast("û��������");
//								return;
//							}
//							adapter.addList(datas1);
//							adapter.notifyDataSetChanged();
//						}

					}
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
		setContentView(R.layout.yunav);
		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		flag = b.getString("flag");

		i = 0;
		init();
		Listener();

	}

	/**
	 * ��ʼ������
	 */
	private void init() {

		TextView toptitile = (TextView) findViewById(R.id.toptitile);
		toptitile.setText("��������");
		mycourse_list = (ListView) findViewById(R.id.navdetail_list);
		oder_nofound = (RelativeLayout)findViewById(R.id.oder_nofound);
		top_fanhui = (RelativeLayout) findViewById(R.id.top_fanhui);
		top_fanhui.setVisibility(View.VISIBLE);

		locationClient = new AMapLocationClient(this.getApplicationContext());
		locationOption = new AMapLocationClientOption();
		// ���ö�λģʽΪ�߾���ģʽ
		locationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
		// ���ö�λ����
		locationClient.setLocationListener(this);

		initOption();
		// ���ö�λ����
		locationClient.setLocationOption(locationOption);
		// ������λ
		locationClient.startLocation();

	}

	// �������ݵĵ���
	public void initarray() {

		// ѡ��㾭�ȣ�116.35723114�� γ�ȣ�39.96291184
		// if(geoLat==null){
		// geoLat=39.96291184;
		// }
		// if(geoLng==null){
		// geoLng=116.35723114;
		// }

		adapter = new MainAdapter(CarnavdetailActivity.this, datas);
		 mycourse_list.setAdapter(adapter);
		         
		
	}

	/**
	 * ����
	 */
	private void Listener() {

		top_fanhui.setOnClickListener(this);
		
	
	
	}

	@Override
	public void onClick(View v) {

		if (v == top_fanhui) {
			finish();
		}

	}

	// ������������
	private void getGoodsList1() {
		myDialog = new ProDialog(CarnavdetailActivity.this, R.style.progressDialog);
		myDialog.show();
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					String url = UrlUtil.getUserUrl() + "admin/api/faultInfo";
					List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();  
//					params.add(new BasicNameValuePair("latelyMonth","6"));
//					params.add(new BasicNameValuePair("dealStatus","0"));
//					params.add(new BasicNameValuePair("mobileLogin","true"));
					
					String ruslt = HttpClientUtil.post(CarnavdetailActivity.this,url, params);
					if (!(ruslt == null)) {
						goodsList = new JSONObject(ruslt);
					}
					myDialog.dismiss();
					if (!(myDialog == null)) {
						myDialog.dismiss();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					Message msg = new Message();
					msg.what = 91;
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
	public void onLocationChanged(AMapLocation amapLocation) {
		if (amapLocation != null) {
			if (amapLocation != null && amapLocation.getErrorCode() == 0) {
				geoLat = amapLocation.getLatitude();
				geoLng = amapLocation.getLongitude();
				if (i == 0) {
					if (isNetworkAvailable()) {
						i++;
						 getGoodsList1();
					} else {
						showCustomToast("��ǰ���粻���ã������������������!");
					}

				}

			} else {
				String errText = "��λʧ��," + amapLocation.getErrorCode() + ": "
						+ amapLocation.getErrorInfo();
				Log.e("AmapErr", errText);

			}
		}
	}

	// ���ݿؼ���ѡ���������ö�λ����
	private void initOption() {
		// �����Ƿ���Ҫ��ʾ��ַ��Ϣ
		/**
		 * �����Ƿ����ȷ���GPS��λ��������30����GPSû�з��ض�λ�����������綨λ ע�⣺ֻ���ڸ߾���ģʽ�µĵ��ζ�λ��Ч��������ʽ��Ч
		 */
		locationOption.setGpsFirst(true);
		// �����Ƿ�������
		// locationOption.setLocationCacheEnable();
		// ���÷��Ͷ�λ�����ʱ����,��СֵΪ1000�����С��1000������1000��
		locationOption.setInterval(2000);

	}

	/**
	 * ����������д
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (null != locationClient) {
			/**
			 * ���AMapLocationClient���ڵ�ǰActivityʵ�����ģ�
			 * ��Activity��onDestroy��һ��Ҫִ��AMapLocationClient��onDestroy
			 */
			locationClient.onDestroy();
			locationClient = null;
			locationOption = null;
		}
	}

}

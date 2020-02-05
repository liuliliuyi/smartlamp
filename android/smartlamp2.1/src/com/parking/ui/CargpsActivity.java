package com.parking.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.parking.model.Parking;

import com.parking.smarthome.R;
import com.parking.ui.LoginActivity;
import com.parking.util.ActivityBase;
import com.parking.util.HttpClientUtil;

import com.parking.util.JsonUtil;
import com.parking.util.LoadUtil;

import com.parking.util.UrlUtil;

public class CargpsActivity extends ActivityBase implements OnClickListener,
		AMapLocationListener {

	private SharedPreferences sp;
	private String tiaozhuanflag1;
	private int i, j, k;

	private LoadUtil loadUtil;
	private String cheid;

	private Timer mTimer;
	private TimerTask mTimerTask;
	private int recLen;
	private JSONObject goodsList = null;
	private ArrayList<Parking> datas = new ArrayList<Parking>();
	private String id;

	private AMapLocationClient locationClient = null;
	private AMapLocationClientOption locationOption = null;
	private Double geoLat, geoLng;

	private ImageView car_night1, car_night2, car_night3, car_night4,
			car_night5;
	private ImageView car_night6, car_night7, car_night8, car_night9,
			car_night10;

	private ImageView car_yes1, car_yes2, car_yes3, car_yes4, car_yes5;
	private ImageView car_yes6, car_yes7, car_yes8, car_yes9, car_yes10;

	Handler d1 = new Handler() {

		public void handleMessage(Message msg) {
			try {
				if (msg.what == 92) {

					String message = goodsList.getString("success");
					if (message != null && message.equals("true")) {
						// д�������ݿ���-----------------
						if (j == 0) {
							datas = new ArrayList<Parking>();
							datas.clear();
							JsonUtil.getParking(goodsList.toString(), datas, id);

							for (int i = 0; i < datas.size(); i++) {

								if (i == 0) {
									if ("0".equals(datas.get(i).getState()
											.toString())) {
										if ("true"
												.equals(datas.get(i).getVip())) {
											car_yes1.setVisibility(View.VISIBLE);
											car_yes1.setBackgroundResource(R.drawable.car_tingche);
										} else {
											car_yes1.setVisibility(View.GONE);
											car_night1
													.setOnClickListener(new OnClickListener() {

														@Override
														public void onClick(
																View arg0) {
															SharedPreferences sp = getSharedPreferences(
																	"mrsoft", 0);
															Editor editor = sp
																	.edit();
															editor.putString(
																	"jing",
																	datas.get(0)
																			.getLongitude());
															editor.putString(
																	"wei",
																	datas.get(0)
																			.getLatitude());
															// ��λ�ľ�γ��
															editor.putString(
																	"geoLat",
																	geoLat.toString());
															editor.putString(
																	"geoLng",
																	geoLng.toString());
															editor.commit();
															Intent it = new Intent();
															it.setClass(
																	CargpsActivity.this,
																	GPSNaviActivity.class);
															startActivity(it);

														}
													});

										}
									} else {
										if ("true".equals(datas.get(i).getVip()
												.toString())) {

											car_yes1.setVisibility(View.VISIBLE);
											car_yes1.setBackgroundResource(R.drawable.close);
										} else {
											car_yes1.setVisibility(View.VISIBLE);
											car_yes1.setBackgroundResource(R.drawable.open);

										}

									}
								}
								// �ڶ���ͣ��λ
								if (i == 1) {
									if ("0".equals(datas.get(i).getState()
											.toString())) {
										if ("true"
												.equals(datas.get(i).getVip())) {
											car_yes2.setVisibility(View.VISIBLE);
											car_yes2.setBackgroundResource(R.drawable.car_tingche);
										} else {
											car_yes2.setVisibility(View.GONE);
											car_night2
													.setOnClickListener(new OnClickListener() {

														@Override
														public void onClick(
																View arg0) {
															SharedPreferences sp = getSharedPreferences(
																	"mrsoft", 0);
															Editor editor = sp
																	.edit();
															editor.putString(
																	"jing",
																	datas.get(1)
																			.getLongitude());
															editor.putString(
																	"wei",
																	datas.get(1)
																			.getLatitude());
															// ��λ�ľ�γ��
															editor.putString(
																	"geoLat",
																	geoLat.toString());
															editor.putString(
																	"geoLng",
																	geoLng.toString());
															editor.commit();
															Intent it = new Intent();
															it.setClass(
																	CargpsActivity.this,
																	GPSNaviActivity.class);
															startActivity(it);

														}
													});
										}
									} else {
										if ("true".equals(datas.get(i).getVip()
												.toString())) {

											car_yes2.setVisibility(View.VISIBLE);
											car_yes2.setBackgroundResource(R.drawable.close);
										} else {
											car_yes2.setVisibility(View.VISIBLE);
											car_yes2.setBackgroundResource(R.drawable.open);

										}

									}
								}

								// ������ͣ��λ
								if (i == 2) {
									if ("0".equals(datas.get(i).getState()
											.toString())) {
										if ("true"
												.equals(datas.get(i).getVip())) {
											car_yes3.setVisibility(View.VISIBLE);
											car_yes3.setBackgroundResource(R.drawable.car_tingche);
										} else {
											car_yes3.setVisibility(View.GONE);
                                       car_night3.setOnClickListener(new OnClickListener() {
												
												@Override
												public void onClick(View arg0) {
													SharedPreferences sp = getSharedPreferences("mrsoft", 0);
													Editor editor = sp.edit();
													editor.putString("jing",datas.get(2).getLongitude());
													editor.putString("wei",datas.get(2).getLatitude());
													//��λ�ľ�γ��
													editor.putString("geoLat", geoLat.toString());
													editor.putString("geoLng", geoLng.toString());
													editor.commit();
													Intent it = new Intent();
													it.setClass(CargpsActivity.this, GPSNaviActivity.class);
													startActivity(it);
													
												}
											});
										
										
										}
									} else {
										if ("true".equals(datas.get(i).getVip()
												.toString())) {

											car_yes3.setVisibility(View.VISIBLE);
											car_yes3.setBackgroundResource(R.drawable.close);
										} else {
											car_yes3.setVisibility(View.VISIBLE);
											car_yes3.setBackgroundResource(R.drawable.open);

										}

									}
								}

								// ���ĸ�ͣ��λ
								if (i == 3) {
									if ("0".equals(datas.get(i).getState()
											.toString())) {
										if ("true"
												.equals(datas.get(i).getVip())) {
											car_yes4.setVisibility(View.VISIBLE);
											car_yes4.setBackgroundResource(R.drawable.car_tingche);
                                          
										
										
										} else {
											car_yes4.setVisibility(View.GONE);
											 car_night4.setOnClickListener(new OnClickListener() {
													
													@Override
													public void onClick(View arg0) {
														SharedPreferences sp = getSharedPreferences("mrsoft", 0);
														Editor editor = sp.edit();
														editor.putString("jing",datas.get(3).getLongitude());
														editor.putString("wei",datas.get(3).getLatitude());
														//��λ�ľ�γ��
														editor.putString("geoLat", geoLat.toString());
														editor.putString("geoLng", geoLng.toString());
														editor.commit();
														Intent it = new Intent();
														it.setClass(CargpsActivity.this, GPSNaviActivity.class);
														startActivity(it);
														
													}
												});
										
										}
									} else {
										if ("true".equals(datas.get(i).getVip()
												.toString())) {

											car_yes4.setVisibility(View.VISIBLE);
											car_yes4.setBackgroundResource(R.drawable.close);
										} else {
											car_yes4.setVisibility(View.VISIBLE);
											car_yes4.setBackgroundResource(R.drawable.open);

										}

									}
								}

								// �����ͣ��λ
								if (i == 4) {
									if ("0".equals(datas.get(i).getState()
											.toString())) {
										if ("true"
												.equals(datas.get(i).getVip())) {
											car_yes5.setVisibility(View.VISIBLE);
											car_yes5.setBackgroundResource(R.drawable.car_tingche);
										} else {
											car_yes5.setVisibility(View.GONE);
                                           car_night5.setOnClickListener(new OnClickListener() {
												
												@Override
												public void onClick(View arg0) {
													SharedPreferences sp = getSharedPreferences("mrsoft", 0);
													Editor editor = sp.edit();
													editor.putString("jing",datas.get(4).getLongitude());
													editor.putString("wei",datas.get(4).getLatitude());
													//��λ�ľ�γ��
													editor.putString("geoLat", geoLat.toString());
													editor.putString("geoLng", geoLng.toString());
													editor.commit();
													Intent it = new Intent();
													it.setClass(CargpsActivity.this, GPSNaviActivity.class);
													startActivity(it);
													
												}
											});
										}
									} else {
										if ("true".equals(datas.get(i).getVip()
												.toString())) {

											car_yes5.setVisibility(View.VISIBLE);
											car_yes5.setBackgroundResource(R.drawable.close);
										} else {
											car_yes5.setVisibility(View.VISIBLE);
											car_yes5.setBackgroundResource(R.drawable.open);

										}

									}
								}

								// ������ͣ��λ
								if (i == 5) {
									if ("0".equals(datas.get(i).getState()
											.toString())) {
										if ("true"
												.equals(datas.get(i).getVip())) {
											car_yes6.setVisibility(View.VISIBLE);
											car_yes6.setBackgroundResource(R.drawable.car_tingche);
										} else {
											car_yes6.setVisibility(View.GONE);
                                            car_night6.setOnClickListener(new OnClickListener() {
												
												@Override
												public void onClick(View arg0) {
													SharedPreferences sp = getSharedPreferences("mrsoft", 0);
													Editor editor = sp.edit();
													editor.putString("jing",datas.get(5).getLongitude());
													editor.putString("wei",datas.get(5).getLatitude());
													//��λ�ľ�γ��
													editor.putString("geoLat", geoLat.toString());
													editor.putString("geoLng", geoLng.toString());
													editor.commit();
													Intent it = new Intent();
													it.setClass(CargpsActivity.this, GPSNaviActivity.class);
													startActivity(it);
													
												}
											});
										}
									} else {
										if ("true".equals(datas.get(i).getVip()
												.toString())) {

											car_yes6.setVisibility(View.VISIBLE);
											car_yes6.setBackgroundResource(R.drawable.close);
										} else {
											car_yes6.setVisibility(View.VISIBLE);
											car_yes6.setBackgroundResource(R.drawable.open);

										}

									}
								}

								// ���߸�ͣ��λ
								if (i == 6) {
									if ("0".equals(datas.get(i).getState()
											.toString())) {
										if ("true"
												.equals(datas.get(i).getVip())) {
											car_yes7.setVisibility(View.VISIBLE);
											car_yes7.setBackgroundResource(R.drawable.car_tingche);
										} else {
											car_yes7.setVisibility(View.GONE);
                                            car_night7.setOnClickListener(new OnClickListener() {
												
												@Override
												public void onClick(View arg0) {
													SharedPreferences sp = getSharedPreferences("mrsoft", 0);
													Editor editor = sp.edit();
													editor.putString("jing",datas.get(6).getLongitude());
													editor.putString("wei",datas.get(6).getLatitude());
													//��λ�ľ�γ��
													editor.putString("geoLat", geoLat.toString());
													editor.putString("geoLng", geoLng.toString());
													editor.commit();
													Intent it = new Intent();
													it.setClass(CargpsActivity.this, GPSNaviActivity.class);
													startActivity(it);
													
												}
											});

										}
									} else {
										if ("true".equals(datas.get(i).getVip()
												.toString())) {

											car_yes7.setVisibility(View.VISIBLE);
											car_yes7.setBackgroundResource(R.drawable.close);
										} else {
											car_yes7.setVisibility(View.VISIBLE);
											car_yes7.setBackgroundResource(R.drawable.open);

										}

									}
								}

								// �ڰ˸�ͣ��λ
								if (i == 7) {
									if ("0".equals(datas.get(i).getState()
											.toString())) {
										if ("true"
												.equals(datas.get(i).getVip())) {
											car_yes8.setVisibility(View.VISIBLE);
											car_yes8.setBackgroundResource(R.drawable.car_tingche);
										} else {
											car_yes8.setVisibility(View.GONE);
                                           car_night8.setOnClickListener(new OnClickListener() {
												
												@Override
												public void onClick(View arg0) {
													SharedPreferences sp = getSharedPreferences("mrsoft", 0);
													Editor editor = sp.edit();
													editor.putString("jing",datas.get(7).getLongitude());
													editor.putString("wei",datas.get(7).getLatitude());
													//��λ�ľ�γ��
													editor.putString("geoLat", geoLat.toString());
													editor.putString("geoLng", geoLng.toString());
													editor.commit();
													Intent it = new Intent();
													it.setClass(CargpsActivity.this, GPSNaviActivity.class);
													startActivity(it);
													
												}
											});
										}
									} else {
										if ("true".equals(datas.get(i).getVip()
												.toString())) {

											car_yes8.setVisibility(View.VISIBLE);
											car_yes8.setBackgroundResource(R.drawable.close);
										} else {
											car_yes8.setVisibility(View.VISIBLE);
											car_yes8.setBackgroundResource(R.drawable.open);

										}

									}
								}

								// �ھŸ�ͣ��λ
								if (i == 8) {
									if ("0".equals(datas.get(i).getState()
											.toString())) {
										if ("true"
												.equals(datas.get(i).getVip())) {
											car_yes9.setVisibility(View.VISIBLE);
											car_yes9.setBackgroundResource(R.drawable.car_tingche);
										} else {
											car_yes9.setVisibility(View.GONE);
                                           car_night9.setOnClickListener(new OnClickListener() {
												
												@Override
												public void onClick(View arg0) {
													SharedPreferences sp = getSharedPreferences("mrsoft", 0);
													Editor editor = sp.edit();
													editor.putString("jing",datas.get(8).getLongitude());
													editor.putString("wei",datas.get(8).getLatitude());
													//��λ�ľ�γ��
													editor.putString("geoLat", geoLat.toString());
													editor.putString("geoLng", geoLng.toString());
													editor.commit();
													Intent it = new Intent();
													it.setClass(CargpsActivity.this, GPSNaviActivity.class);
													startActivity(it);
													
												}
											});
										}
									} else {
										if ("true".equals(datas.get(i).getVip()
												.toString())) {

											car_yes9.setVisibility(View.VISIBLE);
											car_yes9.setBackgroundResource(R.drawable.close);
										} else {
											car_yes9.setVisibility(View.VISIBLE);
											car_yes9.setBackgroundResource(R.drawable.open);

										}

									}
								}

								// ��ʮ��ͣ��λ
								if (i == 9) {
									if ("0".equals(datas.get(i).getState()
											.toString())) {
										if ("true"
												.equals(datas.get(i).getVip())) {
											car_yes10
													.setVisibility(View.VISIBLE);
											car_yes10
													.setBackgroundResource(R.drawable.car_tingche);
										} else {
											car_yes10.setVisibility(View.GONE);
                                             car_night10.setOnClickListener(new OnClickListener() {
												
												@Override
												public void onClick(View arg0) {
													SharedPreferences sp = getSharedPreferences("mrsoft", 0);
													Editor editor = sp.edit();
													editor.putString("jing",datas.get(9).getLongitude());
													editor.putString("wei",datas.get(9).getLatitude());
													//��λ�ľ�γ��
													editor.putString("geoLat", geoLat.toString());
													editor.putString("geoLng", geoLng.toString());
													editor.commit();
													Intent it = new Intent();
													it.setClass(CargpsActivity.this, GPSNaviActivity.class);
													startActivity(it);
													
												}
											});
										}
									} else {
										if ("true".equals(datas.get(i).getVip()
												.toString())) {

											car_yes10
													.setVisibility(View.VISIBLE);
											car_yes10
													.setBackgroundResource(R.drawable.close);
										} else {
											car_yes10
													.setVisibility(View.VISIBLE);
											car_yes10
													.setBackgroundResource(R.drawable.open);

										}

									}
								}

							}

							i++;
						}
					}

				} else {
					showCustomToast("ͣ���������ݷ�������");
				}

			} catch (Exception e) {

				e.printStackTrace();
			}
		};

	};

	final Handler d = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:

				if (recLen > 0) {

					getGoodsList();

				} else {
					clearTimer();
					recLen = 86409;
					inittime();
				}
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// ȥ����
		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.carshiwainav);
//
//		car_night1 = (ImageView) findViewById(R.id.car_night1);
//		car_night2 = (ImageView) findViewById(R.id.car_night2);
//		car_night3 = (ImageView) findViewById(R.id.car_night3);
//		car_night4 = (ImageView) findViewById(R.id.car_night4);
//		car_night5 = (ImageView) findViewById(R.id.car_night5);
//		car_night6 = (ImageView) findViewById(R.id.car_night6);
//		car_night7 = (ImageView) findViewById(R.id.car_night7);
//		car_night8 = (ImageView) findViewById(R.id.car_night8);
//		car_night9 = (ImageView) findViewById(R.id.car_night9);
//		car_night10 = (ImageView) findViewById(R.id.car_night10);
//
//		car_yes1 = (ImageView) findViewById(R.id.car_yes1);
//		car_yes2 = (ImageView) findViewById(R.id.car_yes2);
//		car_yes3 = (ImageView) findViewById(R.id.car_yes3);
//		car_yes4 = (ImageView) findViewById(R.id.car_yes4);
//		car_yes5 = (ImageView) findViewById(R.id.car_yes5);
//		car_yes6 = (ImageView) findViewById(R.id.car_yes6);
//		car_yes7 = (ImageView) findViewById(R.id.car_yes7);
//		car_yes8 = (ImageView) findViewById(R.id.car_yes8);
//		car_yes9 = (ImageView) findViewById(R.id.car_yes9);
//		car_yes10 = (ImageView) findViewById(R.id.car_yes10);
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

		// getGoodsList();
		RelativeLayout top_fanhui = (RelativeLayout) findViewById(R.id.top_fanhui);
		top_fanhui.setVisibility(View.VISIBLE);
		top_fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				clearTimer();
				finish();
				System.exit(0);
			}
		});
		i = 0;
		j = 0;
		k = 0;
		// ʱ���߳�ˢ��
		recLen = 86409;
		inittime();

	}

	// ������������
	private void getGoodsList() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					sp = getSharedPreferences("mrsoft", MODE_PRIVATE);
					String id = sp.getString("goodsId", "goodsId");
					String url = UrlUtil.getUserUrl() + "app/statuslist/";
					// �õ����goodsId
					List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
					params.add(new BasicNameValuePair("goodsId", id));
					String ruslt = HttpClientUtil.post(CargpsActivity.this,url, params);
					if (!(ruslt == null)) {
						goodsList = new JSONObject(ruslt);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

				try {
					Message msg = new Message();
					msg.what = 92;
					d1.sendEmptyMessage(msg.what);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}).start();

	}

	// ���ʱ�����
	private void clearTimer() {

		if (mTimerTask != null) {
			mTimerTask.cancel();
			mTimerTask = null;

		}
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}
	}

	// ��ʼ��ʱ�����
	private void inittime() {
		mTimer = new Timer();
		mTimerTask = new TimerTask() {
			@Override
			public void run() {
				recLen--;
				i = 0;
				Message message = new Message();
				message.what = 1;
				d.sendMessage(message);

			}
		};
		// ��ʼһ����ʱ����
		mTimer.schedule(mTimerTask, 0, 9000);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

		clearTimer();
		this.finish();
		System.exit(0);
	}

	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		if (amapLocation != null) {
			if (amapLocation != null && amapLocation.getErrorCode() == 0) {
				geoLat = amapLocation.getLatitude();
				geoLng = amapLocation.getLongitude();
				if (k == 0) {
					if (isNetworkAvailable()) {
						k++;
						getGoodsList();
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
}

package com.example.lanya_lingyang_5;

import static com.example.publics.Contants.Connect;
import static com.example.publics.Contants.ConnextFailed;
import static com.example.publics.Contants.DisConnect;
import static com.example.publics.Contants.PublicFailed;
import static com.example.publics.Contants.PublicSuccess;
import static com.example.publics.Contants.ReceData;
import static com.example.publics.Contants.TOPIC_Clent_Send;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.message.BasicNameValuePair;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.internal.TCPNetworkModule;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import com.amap.api.col.n3.fi;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.LocationSource.OnLocationChangedListener;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.baidu.android.common.logging.Log;
import com.example.publics.MqttManager;
import com.example.publics.MyMsg;
import com.example.publics.mqttServer;
import com.langyue.ble.BluetoothLeService;
import com.langyue.ble.LeDeviceListAdapter;

import com.lingyang.xml.Data_Toast_Server;
import com.lingyang.xml.DefaultValue;
import com.lingyang.xml.GattUpdateReceiver;
import com.lingyang.xml.GattUpdateReceiver.main_ly;
import com.lingyang.xml.Loading_view;
import com.lingyang.xml.MyApplication;
import com.lingyang.xml.MyDialog;
import com.lingyang.xml.XmlSaveUtil;
import com.lingyang.xml.copyXml;
import com.lingyang.xml.sendServer;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.parking.model.City;
import com.parking.model.NBData;
import com.parking.smarthome.App;
import com.parking.smarthome.App.CtrBroadcastData;
import com.parking.smarthome.R;
import com.parking.ui.AlarmActivity;
import com.parking.ui.BaoxiuActivity;
import com.parking.ui.CarnavActivity;
import com.parking.ui.TongjiActivity;
import com.parking.ui.AlarmActivity.MainAdapter;

import com.parking.ui.SndetailActivity;

import com.parking.util.FileUtil;
import com.parking.util.HttpClientUtil;
import com.parking.util.JsonUtil;
import com.parking.util.ProDialog;
import com.parking.util.UrlUtil;
import com.parking.widget.AlertDiaLog;


import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnDismissListener;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnClickListener,LocationSource,AMapLocationListener
		 , AMap.OnMapClickListener,AMap.OnMapTouchListener ,main_ly{

	// 定位的声明
	// 声明mlocationClient对象
	private AMap aMap;
	private MapView mapView;
	private OnLocationChangedListener mListener;
	private AMapLocationClient mlocationClient;
	private AMapLocationClientOption mLocationOption;
	boolean useMoveToLocationWithMapMode = true;
	//自定义定位小蓝点的Marker
	Marker locationMarker;
	//坐标和经纬度转换工具
	Projection projection;
	private String mapflag;
	Marker marker;
	
	
	private DrawerLayout slideMenu;
	private Double geoLat, geoLng,geoLat1, geoLng1;
	private ProDialog myDialog;
	private JSONObject goodsList = null;
	private byte[] key = { 6, 1, 5, 78, 2, 9, 2, 5, 0, 3, 8, 1, 6, 3, 2, 7, 2,
			2, 11, 2, 6, 23, 7, 9, 1, 5, 76, 9, 3, 5, 7, 98 };
	private String SN;
	private ArrayList<NBData> datas = new ArrayList<NBData>();
	
	private ArrayList<NBData> datas1 = new ArrayList<NBData>();
	// private CtrBroadcastData ctrData;
	private ArrayList<String> qq1 = new ArrayList<String>();
	// private ArrayList<String> qq2 = new ArrayList<String>();
	private MainAdapter adapter;
	private ListView mycourse_list;
	private int i;
	private String shangchuansn;

	// private TextView ma;
	public static MyApplication app;
	private LeDeviceListAdapter mLeDeviceListAdapter;
	private BluetoothAdapter mBluetoothAdapter;
	public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
	public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
	public static final String EXTRAS_SN_PID = "SN_PID";
	public static int pW = 0;
	public static int pH = 0;
	public boolean snoff = true;

	public static float textSize = 0;
	public static float btnSize = 0;
	public static float zt_size = 0;
	public boolean flag = true;
	private ArrayList<String> str = new ArrayList<String>();
	private ArrayList<String> breakData = new ArrayList<String>();
	public static ArrayList<String> Dan_List = new ArrayList<String>();
	private ArrayList<String> Rid = new ArrayList<String>();
	public static ArrayList<String> js_show_dan = new ArrayList<String>();
	public static String sc = null;  
	public static String wm = null, dc = null, jg = null;
	public static int tag = 0;

	private boolean ws_flag = true;

	public boolean xflag = false;// 寻灯开启与关闭
	private boolean tag_1_flag = false;
	private boolean tag_2_flag = true;
	private boolean tag_3_flag = false;
	private boolean tag_4_flag = false;
	private boolean tag_5_flag = false;
	private boolean tag_6_flag = false;
	private boolean tag_7_flag = false;
	private boolean xg_ym_flag = true;
	private boolean cxFlag = true;

	private boolean lianDuan_1 = false;
	private boolean lianduan_2 = true;

//	private Loading_view loading_view;

	public static boolean tb_flag = false;// 更新设置参数
	public static GattUpdateReceiver gur;
	private SharedPreferences preferences;
	private int firsh = 0;
	// ---------------------------------------------------------------------------------------------------------------------

	private Button sm, lian, can_1, can_3, can_4, can_5, can_6, can_7, can_8,
			can_10, can_2, can_17, can_110;

	// -------------------------- Handler
	// ----------------------------------------------------
	private Handler mHandler;
	private Handler scanLeHandler;
	// -------------------------- 蓝牙设置
	// -------------------------------------------------------
	// 10秒后停止查找搜索.
	private static final long SCAN_PERIOD = 60000;
	public boolean toc = true;
	private static final int REQUEST_ENABLE_BT = 1;
	private boolean mScanning;
	private String mDN;
	private String mDA;
	public static ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
	public static boolean mConnected = false;
	private BluetoothGattCharacteristic mNotifyCharacteristic;
	private String mDeviceName;

	// -----------------------------------------发送数据-----------------------------------------------------
	private sendServer s101 = new sendServer(this);
	private int sendCount = 0;

	// ----------------------------------------分线程-------------------------------------------------------
	
	//时间进程
	private int recLen;
	private Timer mTimer;
	private TimerTask mTimerTask;
//	private String timeflag;
	private  int mes;
	private RelativeLayout  oder_nofound;
	//
	 final Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (recLen > 0) {
					
					if(recLen==2){
						getGoodsList2();	
					
					}
//					if(recLen==19){
//						
//						//显示
//						 ArrayList<NBData> newList = new ArrayList<>();
//						 newList.clear();
//						 
//						for (int i = 0; i < datas.size(); i++) {
//							
//							for (int j = 0; j< datas1.size(); j++) {
//							if(datas.get(i).getSn().equals(datas1.get(j).getSn())){
//								datas.get(i).setSnStaus("1");
//							
//							}	
//							}
//								
//							}
//						 
//						 newList = getSingle(datas);
//						 // newList
//			
//						if (newList.size() > 0) {
//							oder_nofound.setVisibility(View.GONE);
//							mycourse_list.setVisibility(View.VISIBLE);
//							adapter = new MainAdapter(MainActivity.this, newList);
//							mycourse_list.setAdapter(adapter);
//			
//						}else{
//							
//							oder_nofound.setVisibility(View.VISIBLE);
//							mycourse_list.setVisibility(View.GONE);
//							
//
//						}
//						
//					}
				
				
				
				
				} else {
					//显示
					 ArrayList<NBData> newList = new ArrayList<>();
					 newList.clear();
					 
					for (int i = 0; i < datas.size(); i++) {
						
						for (int j = 0; j< datas1.size(); j++) {
						if(datas.get(i).getSn().equals(datas1.get(j).getSn())){
							datas.get(i).setSnStaus("1");
						
						}	
						}
							
						}
					 
					 newList = getSingle(datas);
					 // newList
		
					if (newList.size() > 0) {
						oder_nofound.setVisibility(View.GONE);
						mycourse_list.setVisibility(View.VISIBLE);
						adapter = new MainAdapter(MainActivity.this, newList);
						mycourse_list.setAdapter(adapter);
		
					}else{
						
						oder_nofound.setVisibility(View.VISIBLE);
						mycourse_list.setVisibility(View.GONE);
						
//						adapter = new MainAdapter(MainActivity.this, newList);
//						mycourse_list.setAdapter(adapter);
					}
					
					
					
					
					qq1.clear();
					datas.clear();
					datas1.clear();
					clearTimer();
					//15秒后收消息
//					if("1".equals(timeflag)){
						
						recLen=20;
//						timeflag="2";
						inittime();
//					}else{
//
//						recLen=2;
//						timeflag="1";
//						inittime();
//						
//					}
					
					
					
					
				}
			}
		}
	 };

		
	private Handler XT_Handler = new Handler();
	private Runnable XT_R = new Runnable() {
		public void run() {
			MqttManager.getInstance().publish(TOPIC_Clent_Send, 2,
					("开启读写模式" + app.serverFlag).getBytes());
			if (app.serverFlag) {
				app.serverFlag = false;
				SH();
				if (ws_flag) {
					tag_2_flag = true;
					breakData.clear();
					ws_handler.postDelayed(ws_Runnable, 3000);
					ws_flag = false;
				}
				mDeviceName = mDN;
				app.mDeviceAddress = mDA;
				XT_Handler.removeCallbacks(XT_R);
			} else {
				XT_Handler.postDelayed(XT_R, 500);
			}
		}
	};
	private Handler ws_handler = new Handler();
	private Runnable ws_Runnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			ws_flag = false;
			sendServer s = new sendServer(MainActivity.this);
			// if (sendAll_1(s.sendXinTiao(14))) {
			if (sendAll_1("0617000104187004000800000000000000000000")) {
				if (Data_Toast_Server.GetInstance().DATA_TOAST == 1) {
					Toast.makeText(MainActivity.this, "数据已发送!",
							Toast.LENGTH_LONG).show();
				}
			} else {
				if (Data_Toast_Server.GetInstance().DATA_TOAST == 1) {

					Toast.makeText(MainActivity.this, "数据发送已失败!",
							Toast.LENGTH_LONG).show();
				}
			}
		}
	};
	private Handler HL_Handler = new Handler();
	private Runnable HLRunnable = new Runnable() {
		public void run() {
			breakData.clear();
			waitTimeBreak();
		}
	};

	public void SH() {
		if (mGattCharacteristics != null) {
			final BluetoothGattCharacteristic characteristic = mGattCharacteristics
					.get(0).get(0);
			final int charaProp = characteristic.getProperties();
			if ((charaProp | BluetoothGattCharacteristic.PERMISSION_WRITE) > 0) {
				mNotifyCharacteristic = characteristic;
				app.getBluetoothLeService().setCharacteristicNotification(
						characteristic, true);
				app.getBluetoothLeService().wirteCharacteristic(characteristic);
				app.getBluetoothLeService().readCharacteristic(characteristic);
			}
		}
	}

	Handler d = new Handler() {

		public void handleMessage(Message msg) {
			try {
				if (msg.what == 90) {
					mapflag="0";

					String message = goodsList.getString("success");

					if (message != null && message.equals("true")) {
//						aMap.clear(otMarkerOptions);
					
						showCustomToast("上传成功");
						new AlertDiaLog(MainActivity.this)
								.builder()
								.setTitle("路灯编码")
								.setMsg(goodsList.getJSONObject("data")
										.getString("telecomCode"))
								.setPositiveButton("复制", new OnClickListener() {
									@Override
									public void onClick(View v) {

										// qq1.clear();
										// 获取剪贴板管理器：
										ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
										// 创建普通字符型ClipData
										ClipData mClipData = null;
										try {
											mClipData = ClipData.newPlainText(
													"Label",
													goodsList.getJSONObject(
															"data").getString(
															"telecomCode"));
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										// 将ClipData内容放到系统剪贴板里。
										cm.setPrimaryClip(mClipData);
										finish();
										qq1.clear();
										datas.clear();
										mes=0;
										clearTimer();
										if(!(marker==null)){
								    		 marker.remove();
								    	}
										shangchuansn="";
										mapflag="0";

									}
								})
								.setNegativeButton("取消", new OnClickListener() {
									@Override
									public void onClick(View v) {
										shangchuansn="";
										// qq1.clear();
									}
								}).show();

						// finish();
					} else {

						showCustomToast(goodsList.getString("msg"));
						new AlertDiaLog(MainActivity.this).builder()
								.setTitle("信息提示")
								.setMsg(goodsList.getString("msg"))
								.setPositiveButton("确定", new OnClickListener() {
									@Override
									public void onClick(View v) {

									}
								}).show();
					}

				}

				if (msg.what == 92) {
					// 更新界面
					// {"success":false,"errorCode":"1","msg":"路灯已有电信编码=1563039，请确认是否重新生成","body":{"data":"exist"}}
					String message = goodsList.getString("code");
					if(!(marker==null)){
			    		 marker.remove();
			    	}
					if (message != null && message.equals("200")) {

						new AlertDiaLog(MainActivity.this)
								.builder()
								.setTitle("修改上报信息")
								.setMsg("电信编码:"
										+ goodsList.getJSONObject("data")
												.getString("telecomCode")
										+ "\r是否修改？")
								.setPositiveButton("确定", new OnClickListener() {
									@Override
									public void onClick(View v) {
										// 在这里上报
										try {
											getGoodsList(shangchuansn);
//											getGoodsList(goodsList
//													.getJSONObject("data")
//													.getString("telecomCode"));
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

									}
								})
								.setNegativeButton("取消", new OnClickListener() {
									@Override
									public void onClick(View v) {
//										finish();
										// qq1.clear();
									}
								}).show();

					} else {
						// 没有的话上传,就直接上报
//						getGoodsList(goodsList.getJSONObject("data").getString(
//								"telecomCode"));
						
						getGoodsList(shangchuansn);
					}

				}
				
		    if (msg.what == 922) {
					//  {"code":200,"success":true,"data":["0211A0005E"],"msg":"操作成功"}
					String message = goodsList.getString("code");

					if (message != null && message.equals("200")) {
						datas1.clear();
						JsonUtil.getSnStaus(goodsList,datas1);
						
//						String data=goodsList.getString("data");
//						sn.setText(data);
					
					} else {
                        
					}

				}


			} catch (Exception e) {

				e.printStackTrace();
			}
		};

	};

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 检查当前手机是否支持ble 蓝牙,如果不支持退出程序
		
		i = 0;
		mHandler = new Handler();
		scanLeHandler = new Handler();
		if (!getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_BLUETOOTH_LE)) {
			Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT)
					.show();
			finish();
		
			qq1.clear();
			datas.clear();
			mes=0;
			clearTimer();
			shangchuansn="";
			mapflag="0";
		}
		final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
		mBluetoothAdapter = bluetoothManager.getAdapter();
		// 检查设备上是否支持蓝牙
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, R.string.error_bluetooth_not_supported,
					Toast.LENGTH_SHORT).show();
			finish();
			shangchuansn="";
			mapflag="0";
			qq1.clear();
			datas.clear();
			mes=0;
			clearTimer();
			return;
		}
		setContentView(R.layout.ble);
		mes=0;
//		getGoodsList2();
		shangchuansn="";
		mapflag="0";
		oder_nofound=(RelativeLayout) findViewById(R.id.oder_nofound);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);
		if (aMap == null) {
			aMap = mapView.getMap();
			setUpMap();
		}
		ImageView nav_loction=(ImageView) findViewById(R.id.nav_loction);
		nav_loction.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(!(marker==null)){
		    		 marker.remove();
		    	}
				if (slideMenu.isDrawerOpen(Gravity.RIGHT)) {
					slideMenu.closeDrawer(Gravity.RIGHT);
				} else {
					slideMenu.openDrawer(Gravity.RIGHT);
				}

			}
		});

		slideMenu = (DrawerLayout) findViewById(R.id.slide_menu);

		slideMenu.setScrimColor(Color.argb(50, 0, 0, 0));

		mapflag="0";

		// 上报的地址的声明
		TextView pingpai = (TextView) findViewById(R.id.pingpai);
		// ma= (TextView) findViewById(R.id.ma);
		SharedPreferences sp = getSharedPreferences("mrsoft", 0);
		String areaname = sp.getString("areaname", "areaname");
		pingpai.setText(areaname);

		mycourse_list = (ListView) findViewById(R.id.mycourse_list);

		RelativeLayout top_fanhui = (RelativeLayout) findViewById(R.id.top_fanhui);
		top_fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
				qq1.clear();
				datas.clear();
				mes=0;
				clearTimer();
				if(!(marker==null)){
		    		 marker.remove();
		    	}
				shangchuansn="";
				mapflag="0";

			}
		});
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);// 取得窗口属性
		pH = dm.heightPixels;
		pW = dm.widthPixels;
		Intent intent = new Intent(MainActivity.this, mqttServer.class);
		startService(intent);
		textSize = (int) this.getResources().getDimension(R.dimen.Text_size);
		btnSize = (int) this.getResources().getDimension(R.dimen.button_size);
		zt_size = (int) this.getResources().getDimension(R.dimen.zt_size);
		xml_path();
		app = (MyApplication) getApplication();
		Intent bindIntent = new Intent(this, BluetoothLeService.class);
		bindService(bindIntent, app.mServiceConnection,
				Context.BIND_AUTO_CREATE);
		boolean bll = bindService(bindIntent, app.mServiceConnection,
				BIND_AUTO_CREATE);
		gur = new GattUpdateReceiver();
		gur.setBRInteractionListener(this);

		can_1 = (Button) findViewById(R.id.can_1);
		can_2 = (Button) findViewById(R.id.can_2);
		can_3 = (Button) findViewById(R.id.can_3);
		can_4 = (Button) findViewById(R.id.can_4);
		can_5 = (Button) findViewById(R.id.can_5);
		can_6 = (Button) findViewById(R.id.can_6);
		can_7 = (Button) findViewById(R.id.can_7);
		can_8 = (Button) findViewById(R.id.can_8);
		can_10 = (Button) findViewById(R.id.can_10);
		can_17 = (Button) findViewById(R.id.can_17);
		can_110 = (Button) findViewById(R.id.can_110);

		can_1.setTextSize(btnSize);
		can_2.setTextSize(btnSize);
		can_3.setTextSize(btnSize);
		can_4.setTextSize(btnSize);
		can_5.setTextSize(btnSize);
		can_6.setTextSize(btnSize);
		can_7.setTextSize(btnSize);
		can_8.setTextSize(btnSize);
		can_10.setTextSize(btnSize);
		can_17.setTextSize(btnSize);
		can_110.setTextSize(btnSize);

		can_1.setOnClickListener(this);
		can_2.setOnClickListener(this);
		can_3.setOnClickListener(this);
		can_4.setOnClickListener(this);
		can_5.setOnClickListener(this);
		can_6.setOnClickListener(this);
		can_7.setOnClickListener(this);
		can_8.setOnClickListener(this);
		can_10.setOnClickListener(this);
		can_17.setOnClickListener(this);
		can_110.setOnClickListener(this);
		// setCustomActionBar();
		sm = (Button) findViewById(R.id.sao);
		lian = (Button) findViewById(R.id.lian);
		sm.setTextSize(btnSize);
		lian.setTextSize(zt_size);
		sm.setOnClickListener(this);
		lian.setEnabled(false);
		// lian.setBackground(this.getResources().getDrawable(R.drawable.red_thumb));
		lian.setOnClickListener(this);
		preferences = getSharedPreferences("count", MODE_WORLD_READABLE);
		firsh = preferences.getInt("count", 0);
		if (firsh == 0) {
			xmlFirst();
		}
		Editor editor = preferences.edit();
		editor.putInt("count", ++firsh);
		editor.commit();
		final Runnable runnable = new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (app.getBluetoothLeService() != null) {
					// app.getBluetoothLeService().getRssiVal();
					mHandler.postDelayed(this, 1000);
				}

				if (!app.serverFlag_1) {
					ws_flag = true;
					mConnected = false;
					lian.setText(getResources().getText(
							R.string.menu_disconnect));
					mDeviceName = "";
					// lian.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.red_thumb));
					if (lianDuan_1) {
						lianduan_2 = true;
						lianDuan_1 = false;
						Toast.makeText(getApplicationContext(), "蓝牙已经断开",
								Toast.LENGTH_LONG).show();
					
					    // 蓝牙断开之后,刷新就没有了
						
						qq1.clear();
						datas.clear();
						
						 ArrayList<NBData> newList = new ArrayList<>();
						 newList.clear();
						 adapter = new MainAdapter(MainActivity.this, newList);
						 mycourse_list.setAdapter(adapter);

						
					
					}

				} else {
					mConnected = true;
					lian.setText(getResources().getText(R.string.menu_connect));
					// lian.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.green_thumb));
					if (lianduan_2) {
						lianduan_2 = false;
						lianDuan_1 = true;
//						Toast.makeText(getApplicationContext(), "蓝牙已连接",
//								Toast.LENGTH_LONG).show();
					}

				}
			}
		};

		mHandler.postDelayed(runnable, 1000);
//		loading_view = new Loading_view(this);
//		loading_view.setMessage("正在连接蓝牙....");
//		addContentView(loading_view, new ViewGroup.LayoutParams(
//				ViewGroup.LayoutParams.MATCH_PARENT,
//				ViewGroup.LayoutParams.MATCH_PARENT));
//		loading_view.setVisibility(View.GONE);

		breakData.clear();
		EventBus.getDefault().register(this);
	}
	
	
	/**
	 * 设置一些amap的属性
	 */
	private void setUpMap() {
		aMap.setLocationSource(this);// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
//		 aMap.setOnMapLongClickListener(this);
		aMap.setOnMapTouchListener(this);
		aMap.setOnMapClickListener(this);// 对amap添加单击地图事件监听器
	}
	
	
	/**
     * map点击事件
     * @param latLng 经纬度
     */
    @Override
    public void onMapClick(LatLng latLng) {
  //  	原来是你影响了
  //      aMap.clear();
    	if(!(marker==null)){
    		 marker.remove();
    	}
        mapflag="1";
     //   geoLat1, geoLng1
        geoLat1 = latLng.latitude;
        geoLng1 = latLng.longitude;
//        Toast.makeText(this, String.valueOf(geoLat1), Toast.LENGTH_LONG).show();
        MarkerOptions otMarkerOptions= new MarkerOptions();
        otMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.datouzheng));
        otMarkerOptions.position(latLng);
//        getAddressByLatlng(latLng);
       
        marker  = aMap.addMarker(otMarkerOptions);
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
        
//        slideMenu.openDrawer(Gravity.RIGHT);
		//先弹出来,然后在请求
		new AlertDiaLog(MainActivity.this)
		.builder()
		.setTitle("点击地图获取位置")
		.setMsg("大头针位置是此路灯位置?")
		.setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (slideMenu.isDrawerOpen(Gravity.RIGHT)) {
					slideMenu.closeDrawer(Gravity.RIGHT);
				}
				
				if(shangchuansn==null||"".equals(shangchuansn)){
					
	                showCustomToast("未搜索到设备SN");
						
						return;
					
					
				}
				//这里清掉
				

				getGoodsList2(shangchuansn);

			}
		})
		.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(View v) {
				// qq1.clear();
			}
		}).show();
    }




	
	
	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	

	/**
	 * 定位成功后回调函数
	 */
	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		if (mListener != null && amapLocation != null) {
			if (amapLocation != null
					&& amapLocation.getErrorCode() == 0) {
				LatLng latLng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
				// 经纬度在这里验证
				geoLat = amapLocation.getLatitude();
				geoLng = amapLocation.getLongitude();
				
				
				//展示自定义定位小蓝点
				if(locationMarker == null) {
					//首次定位
					//markerOption
					MarkerOptions markerOption = new MarkerOptions();
					locationMarker = aMap.addMarker(markerOption.position(latLng)
							.icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker))
							.anchor(0.5f, 0.5f));

					//首次定位,选择移动到地图中心点并修改级别到15级
					aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
				} else {

					if(useMoveToLocationWithMapMode) {
						//二次以后定位，使用sdk中没有的模式，让地图和小蓝点一起移动到中心点（类似导航锁车时的效果）
						startMoveLocationAndMap(latLng);
					} else {
						startChangeLocation(latLng);
					}
					
				}


			} else {
				String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
				Log.e("AmapErr",errText);
			}
		}
	}


	/**
	 * 修改自定义定位小蓝点的位置
	 * @param latLng
	 */
	private void startChangeLocation(LatLng latLng) {

		if(locationMarker != null) {
			LatLng curLatlng = locationMarker.getPosition();
			if(curLatlng == null || !curLatlng.equals(latLng)) {
				locationMarker.setPosition(latLng);
			}
		}
	}

	/**
	 * 同时修改自定义定位小蓝点和地图的位置
	 * @param latLng
	 */
	private void startMoveLocationAndMap(LatLng latLng) {

		//将小蓝点提取到屏幕上
		if(projection == null) {
			projection = aMap.getProjection();
		}
		if(locationMarker != null && projection != null) {
			LatLng markerLocation = locationMarker.getPosition();
			Point screenPosition = aMap.getProjection().toScreenLocation(markerLocation);
			locationMarker.setPositionByPixels(screenPosition.x, screenPosition.y);

		}

		//移动地图，移动结束后，将小蓝点放到放到地图上
		myCancelCallback.setTargetLatlng(latLng);
		//动画移动的时间，最好不要比定位间隔长，如果定位间隔2000ms 动画移动时间最好小于2000ms，可以使用1000ms
		//如果超过了，需要在myCancelCallback中进行处理被打断的情况
		aMap.animateCamera(CameraUpdateFactory.changeLatLng(latLng),1000,myCancelCallback);

	}


	MyCancelCallback myCancelCallback = new MyCancelCallback();

	@Override
	public void onTouch(MotionEvent motionEvent) {
		Log.i("amap","onTouch 关闭地图和小蓝点一起移动的模式");
		useMoveToLocationWithMapMode = false;
	}

	/**
	 * 监控地图动画移动情况，如果结束或者被打断，都需要执行响应的操作
	 */
	class MyCancelCallback implements AMap.CancelableCallback {

		LatLng targetLatlng;
		public void setTargetLatlng(LatLng latlng) {
			this.targetLatlng = latlng;
		}

		@Override
		public void onFinish() {
			if(locationMarker != null && targetLatlng != null) {
				locationMarker.setPosition(targetLatlng);
			}
		}

		@Override
		public void onCancel() {
			if(locationMarker != null && targetLatlng != null) {
				locationMarker.setPosition(targetLatlng);
			}
		}
	};



	/**
	 * 激活定位
	 */
	@Override
	public void activate(OnLocationChangedListener listener) {
		mListener = listener;
		if (mlocationClient == null) {
			mlocationClient = new AMapLocationClient(this);
			mLocationOption = new AMapLocationClientOption();
			//设置定位监听
			mlocationClient.setLocationListener(this);
			//设置为高精度定位模式
			mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
			//是指定位间隔
			mLocationOption.setInterval(2000);
			//设置定位参数
			mlocationClient.setLocationOption(mLocationOption);
			// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
			// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
			// 在定位结束后，在合适的生命周期调用onDestroy()方法
			// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
			mlocationClient.startLocation();
		}
	}

	/**
	 * 停止定位
	 */
	@Override
	public void deactivate() {
		mListener = null;
		if (mlocationClient != null) {
			mlocationClient.stopLocation();
			mlocationClient.onDestroy();
		}
		mlocationClient = null;
	}
	
	
	//請求統計
		private void getGoodsList2() {
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						
						
						String	url = UrlUtil.getUserUrl() + "blade-lamp/api/device/selectDeviceSnByScanUserId";		
						
						SharedPreferences sp = getSharedPreferences("mrsoft", MODE_PRIVATE);
						String token = sp.getString("token", "token"); 
						String ruslt = HttpClientUtil.get(MainActivity.this,url,token);

						if (!(ruslt == null)) {
							goodsList = new JSONObject(ruslt);
						}
						

					} catch (JSONException e) {
						e.printStackTrace();
					}
				
					try {
						Message msg = new Message();
						msg.what = 922;
						d.sendEmptyMessage(msg.what);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}).start();

		}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
          //这里写需要重写的方法
        	finish();
			qq1.clear();
			datas.clear();
			mes=0;
			clearTimer();
			if(!(marker==null)){
	    		 marker.remove();
	    	}
			shangchuansn="";
			mapflag="0";
            return false;
        }
        return false;

    }

	
	// 清除时间进度
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

			// 初始化时间进度
	       private void inittime() {
				mTimer = new Timer();
				mTimerTask = new TimerTask() {
					@Override
					public void run() {
						recLen--;
						Message message = new Message();
						message.what = 1;
						handler.sendMessage(message);

					}
				};
				// 开始一个定时任务
				
					mTimer.schedule(mTimerTask, 0, 1000);
				
				
			}


//	@SuppressLint("SimpleDateFormat")
//	@Override
//	public void onLocationChanged(AMapLocation amapLocation) {
//		if (amapLocation != null) {
//			if (amapLocation.getErrorCode() == 0) {
//				// 定位成功回调信息，设置相关消息
//				// amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
//				geoLat = amapLocation.getLatitude();// 获取纬度
//				geoLng = amapLocation.getLongitude();// 获取经度
//				amapLocation.getAccuracy();// 获取精度信息
//				SimpleDateFormat df = new SimpleDateFormat(
//						"yyyy-MM-dd HH:mm:ss");
//				Date date = new Date(amapLocation.getTime());
//				df.format(date);// 定位时间
//			} else {
//				// 显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
//				Log.e("AmapError",
//						"location Error, ErrCode:"
//								+ amapLocation.getErrorCode() + ", errInfo:"
//								+ amapLocation.getErrorInfo());
//			}
//		}
//	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		breakData.clear();
		cxFlag = true;
		switch (v.getId()) {
		case R.id.can_1:
			// 参数设置
			// tag = 21;
			// MqttManager.getInstance().publish(TOPIC_Clent_Send,
			// 2,("服务种——参数设置").getBytes());
			// Intent ParameterSet = new
			// Intent(MainActivity.this,AllModesActivity.class);
			// startActivity(ParameterSet);
			break;
		case R.id.can_2:
			// 读取实时 数据
			if (mConnected) {
//				loading_view.setMessage("正在发送....");
				waitTime();
				tag_1_flag = false;
				sendServer s2 = new sendServer(this);
				sendCount = s2.ND;
				tag = 2;
				if (sendAll_1(s2.sendReadTime())) {

				} else {
					if (Data_Toast_Server.GetInstance().DATA_TOAST == 1) {

						Toast.makeText(this, "数据发送失败", Toast.LENGTH_LONG)
								.show();
					}
				}
			} else {
				readBreakDataSeting("蓝牙已断开!");
			}
			break;
		case R.id.can_3:
			// 已存数据
			tag = 22;
			ArrayList<String> n = new ArrayList<String>();
			int x = 0;
			n.clear();
			n = XmlSaveUtil.GetInstance().readXML_data(2, "wPN_2.xml");
			if (n.size() != 0) {
				x = Integer.parseInt(n.get(0));
			}
			switch (x) {
			case 1:
				// Intent RealTimeData_1 = new
				// Intent(MainActivity.this,ControllerParameter_1_Activity.class);
				// startActivity(RealTimeData_1);
				break;
			case 2:
				// Intent RealTimeData_2 = new
				// Intent(MainActivity.this,ControllerParameter_2_Activity.class);
				// startActivity(RealTimeData_2);
				break;
			case 3:
				// Intent RealTimeData_3 = new
				// Intent(MainActivity.this,ControllerParameter_3_Activity.class);
				// startActivity(RealTimeData_3);
				break;
			case 4:
				// Intent RealTimeData_4 = new
				// Intent(MainActivity.this,ControllerParameter_4_Activity.class);
				// startActivity(RealTimeData_4);
				break;
			default:
				readBreakDataSeting("暂时无数据");
				break;
			}
			break;
		case R.id.can_4:
			tag = 17;
			// Intent RemoteControl = new
			// Intent(MainActivity.this,RemoteControlActivity.class);
			// RemoteControl.putExtra("huifu", app.mDeviceAddress);
			// startActivity(RemoteControl);
			break;
		case R.id.can_5:
			a = 0;
			b = 0;
			c = 0;
			if (mConnected) {
				waitTime();
//				loading_view.setMessage("正在发送....");

				tag = 5;
				// can_4.setText(s101.sendData());
				if (sendAll_1(s101.sendData())) {
					if (Data_Toast_Server.GetInstance().DATA_TOAST == 1) {
						Toast.makeText(this, "数据已发送!", Toast.LENGTH_LONG)
								.show();
					}
				} else {
					if (Data_Toast_Server.GetInstance().DATA_TOAST == 1) {

						Toast.makeText(this, "数据发送已失败!", Toast.LENGTH_LONG)
								.show();
					}
				}
			} else {
				readBreakDataSeting("蓝牙已断开!");
			}
			break;
		case R.id.can_6:
			if (mConnected) {
				sendServer s6 = new sendServer(this);
				tag = 6;
				if (sendAll_1(s6.sendRead())) {
//					loading_view.setMessage("正在发送....");
					waitTime();
					if (Data_Toast_Server.GetInstance().DATA_TOAST == 1) {
						Toast.makeText(this, "数据已发送!", Toast.LENGTH_LONG)
								.show();
					}
				} else {
					if (Data_Toast_Server.GetInstance().DATA_TOAST == 1) {

						Toast.makeText(this, "数据发送已失败!", Toast.LENGTH_LONG)
								.show();
					}
				}
				// loading_view.setMessage("正在发送....");
				// waitTime();
			} else {
				readBreakDataSeting("蓝牙已断开!");
			}

			break;
		case R.id.can_7:
			tag = 23;
			Dan_List.clear();
			if (mConnected) {
				xflag = true;
				// Intent xun_intent = new
				// Intent(MainActivity.this,Xun_List_Activity.class);
				// startActivity(xun_intent);
			} else {
				readBreakDataSeting("蓝牙已断开!");
			}
			break;
		case R.id.can_8:
			tag = 18;
			if (mConnected) {

				// Intent wake = new
				// Intent(MainActivity.this,wake_activity.class);
				// wake.putExtra(EXTRAS_DEVICE_ADDRESS, app.mDeviceAddress);
				// startActivity(wake);
			} else {
				if (Data_Toast_Server.GetInstance().DATA_TOAST == 1) {
					Toast.makeText(this, "数据发送已失败!", Toast.LENGTH_LONG).show();
				}
				readBreakDataSeting("蓝牙已断开,请连接蓝牙!");
			}
			break;

		case R.id.can_10:

			if (mConnected) {
				// 开灯
				tag = 12;
//				loading_view.setMessage("正在发送....");
				waitTime();
				sendServer s10 = new sendServer(MainActivity.this);
				if (sendAll_1(s10.sendXunDetails(12, 1))) {

					if (Data_Toast_Server.GetInstance().DATA_TOAST == 1) {

						Toast.makeText(this, "发送成功!", Toast.LENGTH_LONG).show();
					}
				} else {
					if (Data_Toast_Server.GetInstance().DATA_TOAST == 1) {

						Toast.makeText(this, "发送失败!", Toast.LENGTH_LONG).show();
					}
				}
				// loading_view.setMessage("正在发送....");
				// waitTime();
			} else {
				readBreakDataSeting("蓝牙已断开!");
			}

			break;
		case R.id.can_17:
			tag = 30;
			if (mConnected) {
				// MqttManager.getInstance().publish(TOPIC_Clent_Send, 2,
				// ("开始休眠--=========").getBytes());
				// Intent xiuMian = new
				// Intent(MainActivity.this,xiumian_activity.class);
				// xiuMian.putExtra(EXTRAS_DEVICE_ADDRESS, app.mDeviceAddress);
				// startActivity(xiuMian);
			} else {
				readBreakDataSeting("蓝牙已断开!");
			}
			break;
		case R.id.can_110:
			if (mConnected) {
				// 关灯
				tag = 11;
//				loading_view.setMessage("正在发送....");
				waitTime();
				sendServer s10 = new sendServer(this);
				if (sendAll_1(s10.sendXunDetails(12, 2))) {
					if (Data_Toast_Server.GetInstance().DATA_TOAST == 1) {

						Toast.makeText(this, "数据发送已!", Toast.LENGTH_LONG)
								.show();
					}
				} else {
					if (Data_Toast_Server.GetInstance().DATA_TOAST == 1) {

						Toast.makeText(this, "数据发送已失败!", Toast.LENGTH_LONG)
								.show();
					}
				}
				// loading_view.setMessage("正在发送....");
				// waitTime();
			} else {
				readBreakDataSeting("蓝牙已断开!");
			}
			break;
		case R.id.sao:
			if (flag) {
				sm.setText("停止");
				flag = false;
				initViews();
				myclick();
			} else {
				sm.setText("扫描");
				flag = true;
			}
			break;

		default:
			break;
		}

	}

	// --------------------------------------- 搜索蓝牙
	LayoutInflater inflater;
	public View view;

	public void myclick() {
		try {
			
			
			
		
			inflater = LayoutInflater.from(this);// 渲染器
			view = inflater.inflate(R.layout.dialog_normal_layout, null);
			final TextView t_name = (TextView) view
					.findViewById(R.id.xuan_name);
			final TextView t_adds = (TextView) view
					.findViewById(R.id.xuan_adderss);
			t_name.setTextSize(textSize);
			t_adds.setTextSize(textSize);

			if (mConnected) {
				t_name.setText("当前蓝牙名称:" + mDN);
				t_adds.setText("当前蓝牙地址:" + mDA);
			} else {
				t_name.setText("当前蓝牙名称:" + "空");
				t_adds.setText("当前蓝牙地址:" + "空");
			}

			AlertDialog.Builder builder = new Builder(this);
			builder.setTitle("选择蓝牙列表:");
			builder.setAdapter(mLeDeviceListAdapter,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							final BluetoothDevice device = mLeDeviceListAdapter
									.getDevice(which);
							if (mConnected) {
								app.getBluetoothLeService().disconnect();
							}

							dialog.dismiss();
							str.clear();
							tag = 40;
							waitTime();
							if (device == null)
								return;
							mDeviceName = device.getName();
							app.mDeviceAddress = device.getAddress();
							mDN = device.getName();
							asciito16_1(mDeviceName.substring(4),
									device.getAddress(), device.getName());
							mDA = app.mDeviceAddress;
							if (mScanning) {
								mBluetoothAdapter.stopLeScan(mLeScanCallback);
								mScanning = false;
							}
							lian.setEnabled(false);
							flag = true;
							ws_flag = true;
							str.add(device.getName());
							str.add(device.getAddress());

							app.getBluetoothLeService().connect(
									app.mDeviceAddress);

							XmlSaveUtil.GetInstance().writeXML(5, str,
									"lanya_1.xml");
							closeDialog();
							stopviews();
						}
					});
			builder.setView(view);
			builder.setOnDismissListener(new OnDismissListener() {
				@Override
				public void onDismiss(DialogInterface arg0) {
					flag = true;
					closeDialog();
					stopviews();
				}
			});
			AlertDialog alertDialog = builder.create();

			WindowManager.LayoutParams lp = alertDialog.getWindow()
					.getAttributes();

			lp.height = 600;

			alertDialog.getWindow().setAttributes(lp);
			alertDialog.show();
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getApplicationContext(), "蓝牙连接中出现异常,退出蓝牙请重新连接.",
					Toast.LENGTH_LONG).show();
		}
	}

	public void closeDialog() {
		sm.setText("扫描");
	}

	// --------------------------------------- 标题栏
	// -------------------------------------------------------------------------
	@SuppressLint("NewApi")
	// private void setCustomActionBar() {
	// ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
	// ActionBar.LayoutParams.MATCH_PARENT,
	// ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
	// View mActionBarView = LayoutInflater.from(this).inflate(
	// R.layout.actionbar_layout, null);
	// ActionBar actionBar = getActionBar();
	// actionBar.setCustomView(mActionBarView, lp);
	// actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
	// actionBar.setDisplayShowCustomEnabled(true);
	// actionBar.setDisplayShowHomeEnabled(false);
	// actionBar.setDisplayShowTitleEnabled(false);
	// sm = (Button) findViewById(R.id.sao);
	// lian = (Button) findViewById(R.id.lian);
	// sm.setTextSize(btnSize);
	// lian.setTextSize(zt_size);
	// sm.setOnClickListener(this);
	// lian.setEnabled(false);
	// lian.setBackground(this.getResources().getDrawable(R.drawable.red_thumb));
	// lian.setOnClickListener(this);
	//
	// }
	private void initViews() {
		mLeDeviceListAdapter.clear();
		scanLeDevice(true);

	}

	private void stopviews() {
		scanLeDevice(false);
		mLeDeviceListAdapter.clear();
	}

	// ---------------------------------------蓝牙扫描-------------------------------
	private void scanLeDevice(final boolean enable) {
		if (enable) {
			// Stops scanning after a pre-defined scan period.

			scanLeHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					mScanning = false;
					mBluetoothAdapter.stopLeScan(mLeScanCallback);
					flag = true;
					invalidateOptionsMenu();
				}
			}, SCAN_PERIOD);
			mScanning = true;
			mBluetoothAdapter.startLeScan(mLeScanCallback);
		} else {
			mScanning = false;
			mBluetoothAdapter.stopLeScan(mLeScanCallback);
		}
		invalidateOptionsMenu();
	}

	private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
		@Override
		public void onLeScan(final BluetoothDevice device, int rssi,
				byte[] scanRecord) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {

					mLeDeviceListAdapter.addDevice(device);
					mLeDeviceListAdapter.notifyDataSetChanged();

				}
			});
		}
	};

	// 蓝牙自动连接
	public void lanya_initial() {
		MqttManager.getInstance().publish(TOPIC_Clent_Send, 2,
				("服务种——自动连接").getBytes());
		ArrayList<String> ly = new ArrayList<String>();
		ly = XmlSaveUtil.GetInstance().readXML_data(5, "lanya_1.xml");
		if (ly.size() != 0) {
			if (!mConnected) {
				waitTime();
//				loading_view.setMessage("正在发送....");
			}

			initViews();
			mDeviceName = ly.get(0).toString();
			app.mDeviceAddress = ly.get(1).toString();
			mDN = mDeviceName;
			mDA = app.mDeviceAddress;

			if (mScanning) {
				mBluetoothAdapter.stopLeScan(mLeScanCallback);
				mScanning = false;
			}
			lian.setEnabled(false);
			flag = true;
			app.getBluetoothLeService().connect(app.mDeviceAddress);
		} else {
			Toast.makeText(getApplicationContext(), "请按扫描键,寻找蓝牙......",
					Toast.LENGTH_LONG).show();
		}
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		mLeDeviceListAdapter = new LeDeviceListAdapter(this, MainActivity.this);
		scanLeDevice(true);
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}
		registerReceiver(gur, makeGattUpdateIntentFilter());
		xflag = false;
		MqttManager.getInstance().publish(TOPIC_Clent_Send, 2,
				("服务种——自动连接1").getBytes());
		lanya_initial();

		ws_flag = true;
		if (mConnected && tb_flag) {
			tb_flag = false;
			breakData.clear();
			sendData_95();
		}
		snoff = true;
		
		mapView.onResume();

		useMoveToLocationWithMapMode = true;
	}

	private void sendData_95() {
		sendCount = s101.ND;
		tag = 95;
		if (sendAll_1(s101.sendData())) {
			if (Data_Toast_Server.GetInstance().DATA_TOAST == 1) {
				Toast.makeText(this, "数据已发送!", Toast.LENGTH_LONG).show();
			}
		} else {
			if (Data_Toast_Server.GetInstance().DATA_TOAST == 1) {

				Toast.makeText(this, "数据发送已失败!", Toast.LENGTH_LONG).show();
			}
		}
	}

	public boolean sendAll_1(String s) {
		if (mGattCharacteristics != null) {
			final BluetoothGattCharacteristic characteristic = mGattCharacteristics
					.get(0).get(1);
			final int charaProp = characteristic.getProperties();
			if ((charaProp | BluetoothGattCharacteristic.PERMISSION_WRITE) > 0) {
				mNotifyCharacteristic = characteristic;
				app.getBluetoothLeService().setCharacteristicNotification(
						characteristic, true);
				characteristic.setValue(hexStringToBytes(s));
				app.getBluetoothLeService().readCharacteristic(characteristic);
				app.getBluetoothLeService().wirteCharacteristic(characteristic);
				return true;

			}
			return false;
		}
		return false;
	}

	public boolean sendAll_s(byte[] s) {
		if (mGattCharacteristics != null) {
			final BluetoothGattCharacteristic characteristic = mGattCharacteristics
					.get(0).get(1);
			final int charaProp = characteristic.getProperties();
			if ((charaProp | BluetoothGattCharacteristic.PERMISSION_WRITE) > 0) {
				mNotifyCharacteristic = characteristic;
				app.getBluetoothLeService().setCharacteristicNotification(
						characteristic, true);
				characteristic.setValue(s);
				app.getBluetoothLeService().readCharacteristic(characteristic);
				app.getBluetoothLeService().wirteCharacteristic(characteristic);
				return true;

			}
			return false;
		}
		return false;
	}

	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c) {

		return (byte) "0123456789ABCDEF".indexOf(c);

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// tb_flag=false;
		// tb_flag=false;
		// scanLeDevice(false);
		// mLeDeviceListAdapter.clear();
		// lian.setEnabled(false);

		// H_Handler.removeCallbacks(mRunnable);
		mapView.onPause();
		deactivate();

		useMoveToLocationWithMapMode = false;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// EventBus.getDefault().unregister(this);
		// app.serverFlag_1 = false;
		// unbindService(app.mServiceConnection);
		//
		// MqttManager.getInstance().publish(TOPIC_Clent_Send, 2,
		// ("111111111111111111111111111").getBytes());
		// if (app.getBluetoothLeService() != null) {
		// MqttManager.getInstance().publish(TOPIC_Clent_Send, 2,
		// ("222222222222222222222222222").getBytes());
		// app.getBluetoothLeService().disconnect();
		// app.getBluetoothLeService().unbindService(app.mServiceConnection);
		//
		// }
		// if (app.isConnect) {
		// try {
		// MqttManager.getInstance().disConnect();
		// Toast.makeText(this, "你的网络断了，请重新查看！！！！！！！", Toast.LENGTH_LONG)
		// .show();
		// } catch (MqttException e) {
		// e.printStackTrace();
		// }
		// }
		// ws_handler.removeCallbacks(ws_Runnable);
		// HL_Handler.removeCallbacks(HLRunnable);

		mapView.onDestroy();
		if(null != mlocationClient){
			mlocationClient.onDestroy();
		}
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void receEnevnt(MyMsg msg) {
		switch (msg.getTag()) {
		case DisConnect:
			// Toast.makeText(this, "登录-断开连接", Toast.LENGTH_LONG).show();
			break;
		case ReceData:
			MqttMessage msg2 = (MqttMessage) msg.getMsg();

			String string = new String(msg2.getPayload());
			// Toast.makeText(this, "登录-\n接收消息Qos: " +string,
			// Toast.LENGTH_LONG).show();

			break;
		case PublicSuccess:
			// Toast.makeText(this, "登录-\n发送成功: topicName=" + TOPIC_Clent_Send +
			// " qos=" + 2, Toast.LENGTH_LONG).show();
			break;
		case PublicFailed:
			// Toast.makeText(this, "登录-\n发送失败: topicName=" + TOPIC_Clent_Send,
			// Toast.LENGTH_LONG).show();
			break;
		case Connect:
			// Toast.makeText(this, "登录-\n 连接成功", Toast.LENGTH_LONG).show();
			break;
		case ConnextFailed:
			// Toast.makeText(this, "登录-\n 连接失败", Toast.LENGTH_LONG).show();
			break;
		}
	}

	// --------------------------------------------------转换----------------------------------------------------------------------------------
	private String asciito16_1(String s, String a, String b) {
		ArrayList<String> n = new ArrayList<String>();
		String s1 = s;
		String s2 = "";
		int len = s1.length();
		char[] c = s1.toCharArray();
		n.add(zheng(16));
		n.add(zheng(1));
		n.add(zheng(len));
		for (int i = 0; i < len; i++) {
			s2 = s2 + String.valueOf(zheng(c[i]));
			n.add(String.valueOf(zheng(c[i])));
		}
		if (6 - len > 0) {
			for (int i = 0; i < 6 - len; i++) {
				s2 = s2 + "00";
				n.add(String.valueOf("00"));
			}
		}
		for (int i = 0; i < 20 - 9; i++) {
			n.add(String.valueOf("00"));
		}
		XmlSaveUtil.GetInstance().writeXML(10, n, "m_name.xml");
		str.clear();
		str.add(b);
		str.add(a);
		return s2;
	}

	private String zheng(int a) {
		if (a >= 16) {
			return String.valueOf(Integer.toHexString(a));
		} else if (a >= 0) {
			return "0" + String.valueOf(Integer.toHexString(a));
		} else if (a < 0) {
			return String.valueOf(Integer.toHexString((-1) * a + 128));
		}
		return "0" + String.valueOf(Integer.toHexString(0));

	}

	public void xml_path() {
		String strSDpath = Environment.getExternalStorageDirectory().getPath();
		// 存放目录
		File subFile = new File(strSDpath, "xml");
		if (!subFile.exists()) {
			subFile.mkdir();
		}
		// 设置存储路径
		XmlSaveUtil.GetInstance().setConfigPath(subFile.toString());
	}

	// ----------------------------------------------------------------------------------------------------------------------
	public void xmlFirst() {
		// 初始所需要xml
		ArrayList<String> s1 = new ArrayList<String>();
		ArrayList<String> s2 = new ArrayList<String>();
		DefaultValue dv = new DefaultValue();
		dv.init();
		// 初始发送xml
		copyXml cx = new copyXml();
		cx.msData(2);
		cx.msData(3);
		cx.msData(4);
		cx.gbData(1);
		cx.jgData(1);
		cx.xmData();
		cx.BatteryData();

		ArrayList<String> sm = new ArrayList<String>();
		sm.clear();
		for (int i = 0; i < 20; i++) {
			sm.add("0");
		}
		XmlSaveUtil.GetInstance().writeXML(10, sm, "break_MS_1.xml");
		XmlSaveUtil.GetInstance().writeXML(10, sm, "break_MS_2.xml");
		XmlSaveUtil.GetInstance().writeXML(10, sm, "break_MS_3.xml");

		XmlSaveUtil.GetInstance().writeXML(10, sm, "ws_id.xml");
		sm.clear();
		sm.add("0");
		sm.add("0");
		XmlSaveUtil.GetInstance().writeXML(5, sm, "mRemote.xml");
		sm.clear();
		sm.add("1");
		XmlSaveUtil.GetInstance().writeXML(2, sm, "mPattern.xml");
		sm.clear();
		sm.add("1");
		XmlSaveUtil.GetInstance().writeXML(2, sm, "wPN_1.xml");
		sm.clear();
		sm.add("2");
		XmlSaveUtil.GetInstance().writeXML(2, sm, "wPN_2.xml");
		sm.clear();
		sm.add("2");
		XmlSaveUtil.GetInstance().writeXML(2, sm, "wPN_3.xml");

		sm.clear();
		s1 = XmlSaveUtil.GetInstance().readXML_data(10, "m_name.xml");
		s2 = XmlSaveUtil.GetInstance().readXML_data(10, "m_mm.xml");

		// /////////////////////////////////////////////////////////////

		if (s1.size() == 0) {
			sm.clear();
			sm.add("10");
			sm.add("01");
			for (int i = 2; i < 9; i++) {
				sm.add("30");
			}
			for (int i = 9; i < 20; i++) {
				sm.add("0");
			}
			XmlSaveUtil.GetInstance().writeXML(10, sm, "m_name.xml");
		}
		if (s2.size() == 0) {
			sm.clear();
			sm.add("10");
			sm.add("01");
			for (int i = 2; i < 9; i++) {
				sm.add("30");
			}
			for (int i = 9; i < 20; i++) {
				sm.add("0");
			}
			XmlSaveUtil.GetInstance().writeXML(10, sm, "m_mm.xml");

		}

	}

	// -------------------------------------------------------------------------------------------------------------------------------
	@Override
	public void setBoolean(boolean flag) {
		// TODO Auto-generated method stub
		if (app.serverFlag_1) {
//			Toast.makeText(getApplicationContext(), "蓝牙已连接", Toast.LENGTH_LONG)
//					.show();
		}
	}

	@Override
	public void setText(String content) {
//		Toast.makeText(getApplicationContext(), "数据已经返回-----",
//				Toast.LENGTH_SHORT).show();
		if (app.serverFlag_1) {

			MqttManager.getInstance().publish(TOPIC_Clent_Send, 2,
					("1接收数据:" + content).getBytes());
			sc = content;
			//时间进程 每隔20秒SC的情况处理
			toHex(content);
			
//			Toast.makeText(getApplicationContext(), "蓝牙设备返回的是-----" + content,
//					Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void setListBluetoothGattCharacteristic(
			ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics) {
		// TODO Auto-generated method stub
		this.mGattCharacteristics = mGattCharacteristics;

		XT_Handler.postDelayed(XT_R, 500);
	}

	private void getGoodsList(final String SN) {
		myDialog = new ProDialog(MainActivity.this, R.style.progressDialog);
		myDialog.show();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// boolean is=true;
				try {
					SharedPreferences sp = getSharedPreferences("mrsoft",
							MODE_PRIVATE);
					String roadsectionid = sp.getString("roadsectionid",
							"roadsectionid");
					String token = sp.getString("token", "token");
					String url = UrlUtil.getUserUrl()
							+ "blade-lamp/api/device/scanDevice";
					List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
					// if( // flagresult 1 表示扫描 2 表示蓝牙 )

					params.add(new BasicNameValuePair("sn", SN));
					if("0".equals(mapflag)){
						params.add(new BasicNameValuePair("longitude", String
								.valueOf(geoLng)));
						params.add(new BasicNameValuePair("latitude", String
								.valueOf(geoLat)));
					}else{
						
						params.add(new BasicNameValuePair("longitude", String
								.valueOf(geoLng1)));
						params.add(new BasicNameValuePair("latitude", String
								.valueOf(geoLat1)));
					}
					
					params.add(new BasicNameValuePair("roadSection",
							roadsectionid));

					String ruslt = HttpClientUtil.post(MainActivity.this, url,
							token, params);
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
		ConnectivityManager mgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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

	// -----------------------------------------------------返回数据---------------------------------------------------------------------------
//	public static ArrayList<String> onlyList(ArrayList<String> list) {
//		ArrayList<String> list1 = new ArrayList<>();
//		Iterator<String> it = list.iterator();
//		while (it.hasNext()) {
//			String s = (String) it.next();
//			if (!list1.contains(s)) { // 判断list1中是否包括该元素
//				list1.add(s);
//			}
//		}
//		return list1;
//	}
	
	 public static ArrayList<NBData> getSingle(ArrayList<NBData> list) {
		    ArrayList tempList = new ArrayList<>();          //1,创建新集合
		    Iterator it = list.iterator();              //2,根据传入的集合(老集合)获取迭代器
		    while(it.hasNext()) {                  //3,遍历老集合
		      Object obj = it.next();                //记录住每一个元素
		      if(!tempList.contains(obj)) {            //如果新集合中不包含老集合中的元素
		        tempList.add(obj);                //将该元素添加
		      }
		    }
		    return tempList;
		  }
	 
	 
	 public static String replace(String str) {
		    String destination = "";
		    if (str!=null) {
		        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		        Matcher m = p.matcher(str);
		        destination = m.replaceAll("");
		    }
		    return destination;
	}
		

	String strn = "";
	int a = 0, b = 0, c = 0;
	int aa = 0, bb = 0, cc = 0;

	public ArrayList<String> toHex(String str) {
		
//		Toast.makeText(getApplicationContext(), "收到了-----" + str,
//		Toast.LENGTH_SHORT).show();
		
		
	
		// 把结果写到文件里面
//		FileOutputStream fileOutputStream = null;
//		String photoNamedebug =String.valueOf(System.currentTimeMillis()) + ".txt";
//		String pathdebug = FileUtil.getdebug() + photoNamedebug;
//
//		try {
//			File file = new File(pathdebug);
//			fileOutputStream = new FileOutputStream(file);
//
//			FileUtil.saveDate(fileOutputStream, "收到字节码为："+str);
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}

		final ArrayList<String> qq = new ArrayList<String>();
		qq.add("1");
		qq1.add(str);
		datas.clear();
		if(qq1.size()>1){
			
//			for (int i = 0; i < qq1.size(); i++) {

//				final String getsntext = qq1.get(i * 2).toString()
//						.replaceAll(" ", "").substring(8)
//						+ qq1.get((i * 2) + 1).toString().replaceAll(" ", "")
//								.substring(8);
			
			for (int i = 0; i < qq1.size(); ++i)
        	    for (int j = i + 1; j < qq1.size(); ++j)
        	    { 
        	    	
        	    	if ((qq1.get(i).toString()
      						.replaceAll(" ", "").substring(0,2)).equals(qq1.get(j).toString()
      								.replaceAll(" ", "").substring(0,2))) {
        	    		  
                   	   if(("01".equals(qq1.get(i).toString()
       							.replaceAll(" ", "").substring(6,8)))&&("02".equals(qq1.get(j).toString()
       									.replaceAll(" ", "").substring(6,8)))){
                   		   
	    				final String getsntext = replace(qq1.get(i).toString()
						.replaceAll(" ", "").substring(8))
						+ replace(qq1.get(j).toString().replaceAll(" ", "")
								.substring(8));
	                	CtrBroadcastData ctrData =getsn(getsntext);
	    				NBData bean = new NBData();
	    				bean.setHeader(ctrData.header);
	    				bean.setSn(ctrData.sn);
	    				bean.setImei(ctrData.imei);
	    				bean.setCsq(ctrData.csq);
	    				bean.setPvVoltage(ctrData.pvVoltage);
	    				bean.setBatVoltage(ctrData.batVoltage);
	    				bean.setNbStage(ctrData.nbStage);
	    				bean.setUploadInterval(ctrData.uploadInterval);
	    				bean.setGroupNum(ctrData.groupNum);
	    				bean.setCheckSum(ctrData.checkSum);
	    				bean.setVersion(ctrData.version);
	    				bean.setBatType(ctrData.batType);
	    				bean.setBatNum(ctrData.batNum);
	    				bean.setSnStaus("0");
	    				datas.add(bean);
		
	    				System.out.print("getsntext的大小为："+getsntext);
        	    
                   	   }
                   	   }
        	    }	
			
//			for (int i = 0; i < (qq1.size()) * 0.5; i++) {
//
//				final String getsntext = qq1.get(i * 2).toString()
//						.replaceAll(" ", "").substring(8)
//						+ qq1.get((i * 2) + 1).toString().replaceAll(" ", "")
//								.substring(8);
//				
//				
//				CtrBroadcastData ctrData =getsn(getsntext);
//				NBData bean = new NBData();
//				bean.setHeader(ctrData.header);
//				bean.setSn(ctrData.sn);
//				bean.setImei(ctrData.imei);
//				bean.setCsq(ctrData.csq);
//				bean.setPvVoltage(ctrData.pvVoltage);
//				bean.setBatVoltage(ctrData.batVoltage);
//				bean.setNbStage(ctrData.nbStage);
//				bean.setUploadInterval(ctrData.uploadInterval);
//				bean.setGroupNum(ctrData.groupNum);
//				bean.setCheckSum(ctrData.checkSum);
//				bean.setVersion(ctrData.version);
//				bean.setBatType(ctrData.batType);
//				bean.setBatNum(ctrData.batNum);
//				bean.setSnStaus("0");
//				datas.add(bean);
//				
//			}
			
//			每隔2秒更新一次  15秒刷新一次
			// 初始化时间进程
			if(mes==0){
//				timeflag="1";
				recLen = 3;
				inittime();
				mes++;
			}
			
//			 ArrayList<NBData> newList = new ArrayList<>();
//			 newList.clear();
//			 newList = getSingle(datas);  
//
//			if (newList.size() > 0) {
//
//				adapter = new MainAdapter(MainActivity.this, newList);
//				mycourse_list.setAdapter(adapter);
//
//			}
			
		}
		
		

		// 判断连续的长度
		String test = null;

		// if (geoLat == null && geoLng == null) {
		// showCustomToast("请打开手机的GPS");
		// finish();
		// //return;
		// }
		//
		// getGoodsList2(SN);
		//
		// }

		// }
		// }
		// }
		// 握手操作
		// if (qq.get(0).equals("0E")) {
		// waitTimeBreak();
		// switch (Integer.parseInt(qq.get(2).toString().trim(), 16)) {
		// case 1:
		// ArrayList<String> ws = new ArrayList<String>();
		// ws.clear();
		// for (int i = 0; i < 20; i++) {
		// ws.add(qq.get(i).toString().trim());
		// }
		// XmlSaveUtil.GetInstance().writeXML(10, ws,"ws_id.xml");
		// Rid.clear();
		// Rid.add(ws.get(8).toString().trim());
		// Rid.add(String.valueOf(Integer.parseInt(ws.get(9),16))+"."+String.valueOf(Integer.valueOf(ws.get(10),16)));
		// XmlSaveUtil.GetInstance().writeXML(5, Rid, "mRemote.xml");
		// breakData.clear();
		// tag = 95;
		// sendData_95();
		// breakData.clear();
		// break;
		// case 2:
		// readBreakDataSeting("蓝牙传送成功!");
		// breakData.clear();
		// break;
		// case 3:
		// readBreakDataSeting("蓝牙返回传送失败!");
		// breakData.clear();
		// break;
		// case 4:
		// readBreakDataSeting("控制器连接失败!");
		// breakData.clear();
		// break;
		// case 5:
		// readBreakDataSeting("控制器连接失败!");
		// breakData.clear();
		// break;
		// default:
		// break;
		// }
		// }
		// if (tag == 23) {
		//
		// if (qq.get(0).equals("01")) {
		// // MqttManager.getInstance().publish(TOPIC_Clent_Send, 2,
		// ("1____________________单灯--:"+str).getBytes());
		// for (int i = 0; i < 20; i++) {
		// Dan_List.add(qq.get(i));
		// }
		// }
		// if (qq.get(0).equals("02")) {
		// // MqttManager.getInstance().publish(TOPIC_Clent_Send, 2,
		// ("2____________________单灯--:"+str).getBytes());
		// for (int i = 0; i < 20; i++) {
		// Dan_List.add(qq.get(i));
		// }
		// }
		// if (qq.get(0).equals("03")) {
		// // MqttManager.getInstance().publish(TOPIC_Clent_Send, 2,
		// ("3____________________单灯--:"+str).getBytes());
		// for (int i = 0; i < 20; i++) {
		// Dan_List.add(qq.get(i));
		// }
		// }
		// }
		// if (tag == 95) {
		// MqttManager.getInstance().publish(TOPIC_Clent_Send, 2,
		// ("2接收第一条:"+qq.get(0) +" "+qq.get(1) +" "+qq.get(2) +"  "+qq.get(3)
		// +" "+tag).getBytes());
		//
		// if (qq.get(0).equals("01")) {
		// switch (Integer.valueOf(qq.get(2), 16)) {
		// case 1:
		// a=0;
		// breakData.clear();
		// // readBreakDataSeting("读取成功!");
		// break;
		// case 2:
		// if (s101.ND == Integer.valueOf(qq.get(1), 16)) {
		// a=1;
		// }else{
		// a=0;
		// }
		// MqttManager.getInstance().publish(TOPIC_Clent_Send, 2,
		// ("2发送第二条:"+qq.get(0) +" "+qq.get(1) +" "+qq.get(2) +"  "+qq.get(3)
		// +" "+tag).getBytes());
		// sendAll_1(s101.sendWord());
		// readBreakDataSeting("蓝牙传送成功!");
		// breakData.clear();
		// break;
		// case 3:
		// a=0;
		// readBreakDataSeting("蓝牙返回传送失败!");
		// breakData.clear();
		// break;
		// case 4:
		// a=0;
		// readBreakDataSeting("控制器连接失败!");
		// breakData.clear();
		// break;
		// case 5:
		// a=0;
		// readBreakDataSeting("控制器连接失败!");
		// breakData.clear();
		// break;
		//
		// default:
		// break;
		// }
		// }
		// if (qq.get(0).equals("02")) {
		// switch (Integer.valueOf(qq.get(2), 16)) {
		// case 1:
		// b = 0;
		// breakData.clear();
		// // readBreakDataSeting("读取成功!");
		// break;
		// case 2:
		// if (s101.ND == Integer.valueOf(qq.get(1), 16)) {
		// b=1;
		// }else{
		// b = 0;
		// }
		// MqttManager.getInstance().publish(TOPIC_Clent_Send, 2,
		// ("3发送第三条:"+qq.get(0) +" "+qq.get(1) +" "+qq.get(2) +"  "+qq.get(3)
		// +" "+tag).getBytes());
		// sendAll_1(s101.sendGR(1));
		// readBreakDataSeting("蓝牙传送成功!");
		// breakData.clear();
		// break;
		// case 3:
		// b = 0;
		// readBreakDataSeting("蓝牙返回传送失败!");
		// breakData.clear();
		// break;
		// case 4:
		// b = 0;
		// readBreakDataSeting("控制器连接失败!");
		// breakData.clear();
		// break;
		// case 5:
		// b = 0;
		// readBreakDataSeting("控制器连接失败!");
		// breakData.clear();
		// break;
		//
		// default:
		// break;
		// }
		// }
		// if (qq.get(0).equals("03")) {
		// switch (Integer.valueOf(qq.get(2), 16)) {
		// case 1:
		// c = 0;
		// breakData.clear();
		// // readBreakDataSeting("读取成功!");
		// break;
		// case 2:
		// if (s101.ND == Integer.valueOf(qq.get(1), 16)) {
		// c=1;
		// }else{
		// c=0;
		// }
		//
		// MqttManager.getInstance().publish(TOPIC_Clent_Send, 2,
		// ("3发送第三条:"+qq.get(0) +" "+qq.get(1) +" "+qq.get(2) +"  "+qq.get(3)
		// +" "+tag).getBytes());
		// readBreakDataSeting("蓝牙传送成功!");
		// breakData.clear();
		// break;
		// case 3:
		// c=0;
		// readBreakDataSeting("蓝牙返回传送失败!");
		// breakData.clear();
		// break;
		// case 4:
		// c=0;
		// readBreakDataSeting("控制器连接失败!");
		// breakData.clear();
		// break;
		// case 5:
		// c=0;
		// readBreakDataSeting("控制器连接失败!");
		// breakData.clear();
		// break;
		//
		// default:
		// break;
		// }
		// if (a == 1 && b == 1 && c == 1) {
		// waitTimeBreak();
		// a = 0;
		// b = 0;
		// c = 0;
		// readBreakDataSeting("已经同步设置");
		// }else{
		// readBreakDataSeting("参数未同步!");
		// }
		// }
		//
		// }
		// if (tag == 2) {
		// waitTimeBreak();
		// if (qq.get(0).equals("0B")) {
		// switch (Integer.parseInt(breakData.get(2).toString().trim(), 16)) {
		// case 2:
		// readBreakDataSeting("蓝牙传送成功!");
		// break;
		// case 3:
		// readBreakDataSeting("蓝牙返回传送失败!");
		// break;
		// case 4:
		// readBreakDataSeting("控制器连接失败!");
		// break;
		// case 5:
		// readBreakDataSeting("蓝牙连接超时!");
		// break;
		// default:
		//
		// break;
		// }
		// }
		// if (qq.get(0).equals("05")) {
		// // MqttManager.getInstance().publish(TOPIC_Clent_Send, 2,
		// ("主页实时数据："+breakData.size()).getBytes());
		// // Bundle bd = new Bundle();
		// // Intent ControllerParameter = new Intent(MainActivity.this,
		// RealTimeDataActivity.class);
		// // bd.putStringArrayList("si", breakData);
		// // ControllerParameter.putExtras(bd);
		// // startActivity(ControllerParameter);
		//
		// }
		// breakData.clear();
		// }
		// if (tag == 12) {
		// waitTimeBreak();
		// if (qq.get(0).equals("0C")) {
		// switch (Integer.parseInt(qq.get(2).toString().trim(), 16)) {
		// case 2:
		// readBreakDataSeting("蓝牙传送成功!");
		// break;
		// case 3:
		// readBreakDataSeting("蓝牙返回传送失败!");
		// break;
		// case 4:
		// readBreakDataSeting("控制器连接失败!");
		// break;
		// case 5:
		// readBreakDataSeting("蓝牙连接超时!");
		// break;
		//
		// default:
		// break;
		// }
		//
		// }
		// breakData.clear();
		// }
		// if (tag == 11) {
		// waitTimeBreak();
		// if (qq.get(0).equals("0C")) {
		// switch (Integer.parseInt(qq.get(2).toString().trim(), 16)) {
		// case 2:
		// readBreakDataSeting("蓝牙传送成功!");
		// break;
		// case 3:
		// readBreakDataSeting("蓝牙返回传送失败!");
		// break;
		// case 4:
		// readBreakDataSeting("控制器连接失败!");
		// break;
		// case 5:
		// readBreakDataSeting("蓝牙连接超时!");
		// break;
		//
		// default:
		// break;
		// }
		// }
		// breakData.clear();
		// }
		// if (tag == 5) {
		//
		// waitTimeBreak();
		// if (qq.get(0).equals("01")) {
		//
		// switch (Integer.valueOf(qq.get(2), 16)) {
		// case 1:
		// a=0;
		// breakData.clear();
		// // readBreakDataSeting("读取成功!");
		// break;
		// case 2:
		// a=1;
		// MqttManager.getInstance().publish(TOPIC_Clent_Send, 2,
		// ("2发送第二条:"+qq.get(0) +" "+qq.get(1) +" "+qq.get(2) +"  "+qq.get(3)
		// +" "+tag).getBytes());
		// // can_3.setText(strn+"=");
		// // can_4.append("   "+s101.sendWord());
		// sendAll_1(s101.sendWord());
		// readBreakDataSeting("蓝牙传送成功!");
		// breakData.clear();
		// break;
		// case 3:
		// a=0;
		// readBreakDataSeting("蓝牙返回传送失败!");
		// breakData.clear();
		// break;
		// case 4:
		// a=0;
		// readBreakDataSeting("控制器连接失败!");
		// breakData.clear();
		// break;
		// case 5:
		// a=0;
		// readBreakDataSeting("控制器连接失败!");
		// breakData.clear();
		// break;
		//
		// default:
		// break;
		// }
		// }
		// if (qq.get(0).equals("02")) {
		//
		// switch (Integer.valueOf(qq.get(2), 16)) {
		// case 1:
		// b = 0;
		// breakData.clear();
		// // readBreakDataSeting("读取成功!");
		// break;
		// case 2:
		// b=1;
		// MqttManager.getInstance().publish(TOPIC_Clent_Send, 2,
		// ("3发送第三条:"+qq.get(0) +" "+qq.get(1) +" "+qq.get(2) +"  "+qq.get(3)
		// +" "+tag).getBytes());
		// // can_3.append(strn+"=");
		// // can_4.append("   "+s101.sendGR(0));
		// sendAll_1(s101.sendGR(0));
		// readBreakDataSeting("蓝牙传送成功!");
		// breakData.clear();
		// break;
		// case 3:
		// b = 0;
		// readBreakDataSeting("蓝牙返回传送失败!");
		// breakData.clear();
		// break;
		// case 4:
		// b = 0;
		// readBreakDataSeting("控制器连接失败!");
		// breakData.clear();
		// break;
		// case 5:
		// b = 0;
		// readBreakDataSeting("控制器连接失败!");
		// breakData.clear();
		// break;
		//
		// default:
		// break;
		// }
		// }
		// if (qq.get(0).equals("03")) {
		//
		// switch (Integer.valueOf(qq.get(2), 16)) {
		// case 1:
		// if (a == 1 && b == 1 && c == 1) {
		//
		// a = 0;
		// b = 0;
		// c = 0;
		// // can_3.append(strn+"=");
		// readBreakDataSeting("模式设置成功!\n共设置"+
		// String.valueOf(Integer.parseInt(qq.get(3).toString().trim(),16)) +
		// "个");
		//
		// }
		// break;
		// case 2:
		// c=1;
		// MqttManager.getInstance().publish(TOPIC_Clent_Send, 2,
		// ("3发送第三条:"+qq.get(0) +" "+qq.get(1) +" "+qq.get(2) +"  "+qq.get(3)
		// +" "+tag).getBytes());
		// // can_3.append(strn+"=");
		// readBreakDataSeting("蓝牙传送成功!");
		// breakData.clear();
		// break;
		// case 3:
		// c=0;
		// readBreakDataSeting("蓝牙返回传送失败!");
		// breakData.clear();
		// break;
		// case 4:
		// c=0;
		// readBreakDataSeting("控制器连接失败!");
		// breakData.clear();
		// break;
		// case 5:
		// c=0;
		// readBreakDataSeting("控制器连接失败!");
		// breakData.clear();
		// break;
		//
		// default:
		// break;
		// }
		//
		// }
		// }
		// if (tag == 6) {
		// waitTimeBreak();
		// if (qq.get(0).equals("0A")) {
		// switch (Integer.valueOf(qq.get(2))) {
		// case 2:
		// readBreakDataSeting("蓝牙传送成功!");
		// break;
		// case 3:
		// readBreakDataSeting("蓝牙返回传送失败!");
		// break;
		// case 4:
		// readBreakDataSeting("控制器连接失败!");
		// break;
		// case 5:
		// readBreakDataSeting("蓝牙连接超时!");
		// break;
		// default:
		// break;
		// }
		// breakData.clear();
		// }
		// if (qq.get(0).equals("01")) {
		// aa = 1;
		//
		// }
		// if (qq.get(0).equals("02")) {
		// bb = 1;
		//
		// }
		// if (qq.get(0).equals("03")) {
		// cc = 1;
		// if (aa == 1 && bb == 1 && cc == 1) {
		// aa = 0;
		// bb = 0;
		// cc = 0;
		// sr.clear();
		// for (int j = 0; j < 20; j++) {
		// sr.add(breakData.get(j));
		// }
		// dc = breakData.get(2);
		// XmlSaveUtil.GetInstance().writeXML(10, sr,"break_MS_1.xml");
		// jg = breakData.get(14);
		// sr.clear();
		// for (int j = 20; j < 40; j++) {
		// sr.add(breakData.get(j));
		// }
		// wm = breakData.get(22);
		// XmlSaveUtil.GetInstance().writeXML(10, sr,"break_MS_2.xml");
		// sr.clear();
		// for (int j = 40; j < 60; j++) {
		// sr.add(breakData.get(j));
		// }
		// XmlSaveUtil.GetInstance().writeXML(10, sr,"break_MS_3.xml");
		// readBreakDataVictory(1,breakData);
		//
		// }
		//
		// }
		// }

		return qq;
	}

	@SuppressWarnings("deprecation")
	private void readBreakDataVictory(int a, ArrayList<String> js_show) {

		switch (a) {
		case 1:
			if (js_show.size() == 60) {
				if (Integer.valueOf(js_show.get(22), 16) % 16 >= 4) {
					readBreakDataSeting("接收数据出错,请重新操作.");
				} else {
					MyDialog dialog = new MyDialog(this, js_show);
					dialog.show();
				}

			} else if (js_show.size() < 36 && js_show.size() > 0) {
				readBreakDataSeting("接收数据失败!" + "接收数据不完整,请重新发送!");

			} else if (js_show.size() == 0) {
				readBreakDataSeting("接收数据失败!" + "没有接收,请重新发送!");

			}

			js_show.clear();
			break;
		case 2:
			readBreakDataSeting("接收" + "蓝牙接收成功!");

			break;
		case 3:
			readBreakDataSeting("接收" + "蓝牙发送失败!");

			break;
		case 4:
			readBreakDataSeting("接收" + "接收超时失败!");

			break;
		case 5:
			readBreakDataSeting("设置操作" + "模式设置成功!");

			break;
		case 6:
			// loading_view.setVisibility(View.GONE);
			readBreakDataSeting("设置操作" + "很遗憾,发送操作失败\n请检查设备是否连接!");

			break;

		default:
			break;
		}
	}

	public void readBreakDataSeting(String string) {
		AlertDialog.Builder builder = new Builder(this);
		LayoutInflater inflater = LayoutInflater.from(this);
		View v = inflater.inflate(R.layout.text_toast, null);
		TextView content = (TextView) v.findViewById(R.id.message);
		content.setTextSize(20);
		content.setText(string);
		final AlertDialog dialog = builder.create();
		dialog.show();
		dialog.getWindow().setContentView(v);
		dialog.getWindow().setGravity(Gravity.CENTER);
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.height = (int) (pH / 4.0);

		dialog.getWindow().setAttributes(lp);
		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		}, 3000);
	}

	// -----------------------------------------------------蓝牙状态------------------------------------------------------------------------------------
	private static IntentFilter makeGattUpdateIntentFilter() {
		final IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
		// intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
		intentFilter
				.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
		intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
		return intentFilter;
	}

	// ----------------------------------------------------提示信息-------------------------------------------------------
	private void BreakSuccessAndFailure(String string) {
		if (Data_Toast_Server.GetInstance().DATA_TOAST == 1) {
			if (string.equals("02")) {
				new AlertDialog.Builder(this).setTitle("设置操作")
						.setMessage("蓝牙操作成功!").setPositiveButton("确定", null)
						.show();
			}
			if (string.equals("03")) {
				new AlertDialog.Builder(this).setTitle("设置操作")
						.setMessage("蓝牙失败!").setPositiveButton("确定", null)
						.show();
			}
			if (string.equals("04")) {
				new AlertDialog.Builder(this).setTitle("设置操作")
						.setMessage("控制器失败!").setPositiveButton("确定", null)
						.show();
			}
			if (string.equals("05")) {
				new AlertDialog.Builder(this).setTitle("设置操作")
						.setMessage("操作超时!").setPositiveButton("确定", null)
						.show();
			}
		}
	}

	// -------------------------------------------------------------------------------------------------------------------------------------
	private void waitTime() {

		can_1.setEnabled(false);
		can_2.setEnabled(false);
		can_3.setEnabled(false);
		can_4.setEnabled(false);
		can_5.setEnabled(false);
		can_6.setEnabled(false);
		can_7.setEnabled(false);
		can_8.setEnabled(false);
		can_10.setEnabled(false);
//		loading_view.setVisibility(View.VISIBLE);
		HL_Handler.postDelayed(HLRunnable, 10000);
	}

	private void waitTimeBreak() {
//		loading_view.setVisibility(View.GONE);
		can_1.setEnabled(true);
		can_2.setEnabled(true);
		can_3.setEnabled(true);
		can_4.setEnabled(true);
		can_5.setEnabled(true);
		can_6.setEnabled(true);
		can_7.setEnabled(true);
		can_8.setEnabled(true);
		can_10.setEnabled(true);
		HL_Handler.removeCallbacks(HLRunnable);
	}

	public void showCustomToast(String text) {

		View toastRoot = LayoutInflater.from(MainActivity.this).inflate(
				R.layout.common_toast, null);

		((TextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
		Toast toast = new Toast(MainActivity.this);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();

	}

	private void getGoodsList2(final String qrcode) {
		myDialog = new ProDialog(MainActivity.this, R.style.progressDialog);
		myDialog.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String url = UrlUtil.getUserUrl()
							+ "blade-lamp/api/device/checkTelecomNumber?sn="
							+ qrcode;
					SharedPreferences sp = getSharedPreferences("mrsoft",
							MODE_PRIVATE);
					String token = sp.getString("token", "token");
					String ruslt = HttpClientUtil.get(MainActivity.this, url,
							token);
					// String ruslt =
					// HttpClientUtil.post(CarnavActivity.this,url, params);
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

	public class MainAdapter extends BaseAdapter {
		private ArrayList<NBData> list;
		private Context context;
		// private ImageLoader imageLoader = ImageLoader.getInstance();
		// private DisplayImageOptions options;
		private SharedPreferences sp;

		public MainAdapter(Context context, ArrayList<NBData> list) {
			this.context = context;
			this.list = list;

			// imageLoader.init(ImageLoaderConfiguration.createDefault(context));
			// options = new DisplayImageOptions.Builder()
			// .showStubImage(R.drawable.baibaoxiang)
			// .showImageForEmptyUri(R.drawable.baibaoxiang)
			// .showImageOnFail(R.drawable.baibaoxiang)
			// .cacheInMemory()
			// .cacheOnDisc()
			// .imageScaleType(ImageScaleType.EXACTLY)
			// .bitmapConfig(Bitmap.Config.RGB_565)
			// .displayer(new FadeInBitmapDisplayer(300)).build();
		}

		public void addList(ArrayList<NBData> list) {
			this.list.addAll(list);
		}

		@Override
		public int getCount() {
			return (list.size());
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			Viewholder vh = null;
			if (convertView == null) {
				vh = new Viewholder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.snitem, null);
				vh.sn_img = (ImageView) convertView.findViewById(R.id.sn_img);
				
				vh.sn = (TextView) convertView.findViewById(R.id.sn);
				vh.moshixiaojian = (TextView) convertView.findViewById(R.id.moshixiaojian);
				vh.nbchuji = (TextView) convertView.findViewById(R.id.nbchuji);
				vh.nbcqs = (TextView) convertView.findViewById(R.id.nbcqs);
				vh.guangbandianya = (TextView) convertView.findViewById(R.id.guangbandianya);
				
				vh.shangbao = (RelativeLayout) convertView
						.findViewById(R.id.shangbao);

				vh.shandeng = (RelativeLayout) convertView
						.findViewById(R.id.shandeng);
				vh.shangping= (RelativeLayout) convertView
						.findViewById(R.id.shangping);
				// moshixiaojian,nbchuji,nbcqs,guangbandianya
				convertView.setTag(vh);
			} else {

				vh = (Viewholder) convertView.getTag();

			}
			if("1".equals(list.get(position).getSnStaus())){
				vh.sn_img.setBackgroundResource(R.drawable.lu_on);
			}else{
				vh.sn_img.setBackgroundResource(R.drawable.lu_off);
			}

			vh.sn.setText("SN:" + list.get(position).getSn().toString());
			vh.moshixiaojian.setText("模式校验:"+String.valueOf(list.get(position).getCheckSum()));
			vh.nbchuji.setText("NB初始阶段:"+String.valueOf(list.get(position).getNbStage()));
			vh.nbcqs.setText("NB-CSQ:"+String.valueOf(list.get(position).getCsq()));
			vh.guangbandianya.setText("光板电压:"+String.valueOf(list.get(position).getPvVoltage()));
			
			
			vh.shangping.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					
					Intent it = new Intent();
					Bundle b = new Bundle();
					b.putString("sn", list.get(position).getSn().toString());
					b.putString("imei",list.get(position).getImei().toString());
					b.putString("csq",String.valueOf(list.get(position).getCsq()));
//					b.putString("imei",datas.get(position).getImei().toString());
					b.putString("pvVoltage", String.valueOf(list.get(position).getPvVoltage()));
					b.putString("batVoltage",String.valueOf(list.get(position).getBatVoltage()));
					b.putString("nbStage",String.valueOf(list.get(position).getNbStage()));
					b.putString("uploadInterval",String.valueOf(list.get(position).getUploadInterval()));
					b.putString("groupNum",String.valueOf(list.get(position).getGroupNum()));
					b.putString("checkSum", String.valueOf(list.get(position).getCheckSum()));
					b.putString("version",String.valueOf(list.get(position).getVersion()));
					b.putString("batType",String.valueOf(list.get(position).getBatType()));
					b.putString("batNum",String.valueOf(list.get(position).getBatNum()));
					
					
					it.putExtras(b);
					it.setClass(MainActivity.this,
							SndetailActivity.class);
					startActivity(it);
				}
			});
			

			vh.shangbao.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (geoLat == null && geoLng == null) {
	                     showCustomToast("请打开手机的GPS");
						
						return;
					}
					shangchuansn=	list.get(position).getSn().toString();
					
					slideMenu.openDrawer(Gravity.RIGHT);
					//先弹出来,然后在请求
					new AlertDiaLog(MainActivity.this)
					.builder()
					.setTitle("点击地图获取位置")
					.setMsg("此路灯的位置?点击地图获取位置")
					.setPositiveButton("确定", new OnClickListener() {
						@Override
						public void onClick(View v) {

							
							getGoodsList2(list.get(position).getSn().toString());

						}
					})
					.setNegativeButton("取消", new OnClickListener() {
						@Override
						public void onClick(View v) {
							// qq1.clear();
						}
					}).show();
				
				}
			});
			vh.shandeng.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					// String tempStr = list.get(position).toString()
					// .replaceAll(" ", "");
					// // 转换成byte数组
					// byte[] rawData = App.GetByteArray(tempStr);
					// // 解密
					// byte[] plainData = App.DecryptOrEncrypt(rawData, key, 0);
					// // 解码
					// CtrBroadcastData ctrData = App
					// .DecodeBroadcastData(plainData);

					// 获取闪灯的数据包，长度20字节
					// byte[] blkPack = App.GetBlinkDataPack(ctrData.sn,
					// ctrData.groupNum);
					// Toast.makeText(getApplicationContext(),
					// "频段为-----" + ctrData.groupNum, Toast.LENGTH_SHORT)
					// .show();
					// Toast.makeText(getApplicationContext(),
					// "SN-----" + ctrData.sn, Toast.LENGTH_SHORT).show();
					// 用Hex字符串显示出来，仅仅为了显示
					byte[] blkPack = App.GetBlinkDataPack(list.get(position).getSn().toString(), 0);
					System.out.println("加密前数据:" + App.GetHexString(blkPack));
					// 对数据包进行加密
					byte[] packForSend = App.DecryptOrEncrypt(blkPack, key, 4);

					// FileOutputStream fileOutputStream = null;
					// String photoNamedebug = System.currentTimeMillis() +
					// ".txt";
					// String pathdebug = FileUtil.getdebug() + photoNamedebug;
					// File file = new File(pathdebug);
					// try {
					// fileOutputStream = new FileOutputStream(file);
					// FileUtil.saveDate(fileOutputStream, "发送的数据："
					// + new String(packForSend));
					// } catch (Exception e) {
					// // TODO Auto-generated catch block
					// e.printStackTrace();
					// }

					if (sendAll_s(packForSend)) {
						// loading_view.setMessage("闪灯正在发送....");
						Toast.makeText(MainActivity.this, "闪灯数据已发送!",
								Toast.LENGTH_SHORT).show();
						waitTime();
						if (Data_Toast_Server.GetInstance().DATA_TOAST == 1) {
//							Toast.makeText(MainActivity.this, "闪灯数据已发送!",
//									Toast.LENGTH_LONG).show();
						}
					}

				}
			});

			return convertView;
		}

		class Viewholder {
            ImageView sn_img;
			RelativeLayout shangbao, shandeng,shangping;
			TextView sn,moshixiaojian,nbchuji,nbcqs,guangbandianya;
		}
	}

	public CtrBroadcastData getsn(String test) {
		String tempStr = test.replaceAll(" ", "");
		// 转换成byte数组
		byte[] rawData = App.GetByteArray(tempStr);
		// 解密
		byte[] plainData = App.DecryptOrEncrypt(rawData, key, 0);
		// 解码
		CtrBroadcastData ctrData = App.DecodeBroadcastData(plainData);
		// return SN = ctrData.sn;
		return ctrData;

	}

}

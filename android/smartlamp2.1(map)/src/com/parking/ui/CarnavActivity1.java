package com.parking.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.support.v4.widget.DrawerLayout;

import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnCameraChangeListener;
import com.amap.api.maps.AMap.OnInfoWindowClickListener;
import com.amap.api.maps.AMap.OnMapLoadedListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.AMap.OnMarkerDragListener;
import com.amap.api.maps.LocationSource.OnLocationChangedListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;



//import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import com.parking.adapter.CarAdapter;
import com.parking.adapter.DingdanAdapter;
import com.parking.adapter.ResultListAdapter;

import com.parking.model.City;
import com.parking.model.InCart;
import com.parking.model.Order;

import com.parking.smarthome.R;
import com.parking.util.ActivityBase;
import com.parking.util.Constants;
import com.parking.util.FileUtil;
import com.parking.util.HttpClientUtil;
import com.parking.util.JsonUtil;
import com.parking.util.MapDistance;
import com.parking.util.ProDialog;
import com.parking.util.UrlUtil;
import com.parking.widget.AlertDiaLog;
import com.parking.widget.CountDialog;



/**
 *ת
 */
public class CarnavActivity1 extends ActivityBase implements LocationSource,
AMapLocationListener, AMap.OnMapTouchListener {
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
	
//	private TextView mLocationErrText;
	private Marker marker;
	private String flag;
	private ArrayList<MarkerOptions> markerOptionlst;

//	private ArrayList<Order> datas = new ArrayList<Order>();
	private CarAdapter adapter;
	private List<Marker> markerlst;

	
	private TextView dingweitext,ma;
	
	private EditText search;
	private ListView search_result;
	private ResultListAdapter resultListAdapter;
	public PullToRefreshListView pullToRefreshListView;
	private ArrayList<InCart> mData1 = new ArrayList<InCart>();
	

	private int height;
	private int width;

	private Double geoLat, geoLng;

	private ProDialog myDialog;
	private JSONObject goodsList = null;
	private String qrcode;
	
	
	private CartAdapter1  mAdapter1;
	//private HashMap<String, Boolean> inCartMap1 = new HashMap<String, Boolean>();
	//private TextView  weixiuren;
	private ListView listView_cart ;
	private ArrayList<String> strArray,strArray1;
	private TextView  pingpai;
//	private EditText textView4;
	private String roadSectionid;
	private String code;
	private String flagresult;
	private TextView  textView1;
	Handler d = new Handler() {

		public void handleMessage(Message msg) {
			try {
				if (msg.what == 90) {
				
					String message = goodsList.getString("success");

					if (message != null && message.equals("true")) {

						showCustomToast("上传成功");
						new AlertDiaLog(CarnavActivity1.this).builder().setTitle("电信路灯编码")
						.setMsg(goodsList.getJSONObject("data").getString("telecomCode"))
						.setPositiveButton("复制", new OnClickListener() {
							@Override
							public void onClick(View v) {
								 //获取剪贴板管理器：
				                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				                // 创建普通字符型ClipData
				                ClipData mClipData = null;
								try {
									mClipData = ClipData.newPlainText("Label", goodsList.getJSONObject("data").getString("telecomCode"));
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				                // 将ClipData内容放到系统剪贴板里。
				                cm.setPrimaryClip(mClipData);
				                finish();
			
							}
						}).setNegativeButton("取消", new OnClickListener() {
							@Override
							public void onClick(View v) {
                                      
							}
						}).show();	

						
//						finish();
					} else {
						

						showCustomToast(goodsList.getString("msg"));
						new AlertDiaLog(CarnavActivity1.this).builder().setTitle("信息提示")
						.setMsg(goodsList.getString("msg"))
						.setPositiveButton("确定", new OnClickListener() {
							@Override
							public void onClick(View v) {
					          
							}
						}).show();	
					}

				}
				
				if (msg.what == 92) {
					// 更新界面  {"success":false,"errorCode":"1","msg":"路灯已有电信编码=1563039，请确认是否重新生成","body":{"data":"exist"}}
					String message = goodsList.getString("code");

					if (message != null && message.equals("200")) {
						
						new AlertDiaLog(CarnavActivity1.this).builder().setTitle("修改上报信息")
						.setMsg("电信编码:"+goodsList.getJSONObject("data").getString("telecomCode")+"\r是否修改？")
						.setPositiveButton("确定", new OnClickListener() {
							@Override
							public void onClick(View v) {
//								showCustomToast("执行了上报-----");
								getGoodsList();
							}
						}).setNegativeButton("取消", new OnClickListener() {
							@Override
							public void onClick(View v) {
                                finish();      
							}
						}).show();
						
					
					} else {
                        //在这里上报
//						showCustomToast("执行了上报-----");
						getGoodsList();
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
		setContentView(R.layout.carlocation);
		//  flagresult  1 表示扫描     2 表示蓝牙 
		
//		getGoodsList2("0211A0002C");
		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		qrcode = b.getString("qrcode");
		flagresult = b.getString("flagresult");
		
		TextView ma = (TextView) findViewById(R.id.ma);
		ma.setText(qrcode);
		
		pingpai= (TextView) findViewById(R.id.pingpai);
		SharedPreferences sp = getSharedPreferences("mrsoft", 0); 
		
		textView1=(TextView) findViewById(R.id.textView1);
	//  flagresult  1 表示扫描     2 表示蓝牙 
		if("1".equals(flagresult)){
			textView1.setText("二维码");
		}else{
			textView1.setText("设备编号");
		}
		
		
		String areaname= sp.getString("areaname", "areaname");
		pingpai.setText(areaname);
//		textView4= (EditText) findViewById(R.id.textView4);
	
		initWidthandHeight();
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);
		init();
//		if (isNetworkAvailable()) {
//    		
//			getGoodsList2(qrcode);
//		} else {
//			showCustomToast("当前网络不可用，请连接网络后在重试!");
//		}
	
	}
	
	
	private void getGoodsList2() {
		myDialog = new ProDialog(CarnavActivity1.this, R.style.progressDialog);
		myDialog.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String url=null;
					if("1".equals(flagresult)){
						url = UrlUtil.getUserUrl() + "blade-lamp/api/device/checkTelecomNumber?qrcode="+qrcode;	
					}else{
					   url =UrlUtil.getUserUrl() + "blade-lamp/api/device/checkTelecomNumber?sn="+qrcode;
						
					}
						
					SharedPreferences sp = getSharedPreferences("mrsoft", MODE_PRIVATE);
					String token = sp.getString("token", "token"); 
					String ruslt = HttpClientUtil.get(CarnavActivity1.this,url,token);

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

	

	private void initWidthandHeight() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;
		height = dm.heightPixels;
	}

	
	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
			setUpMap();
		}
		ImageView nav_fanhui = (ImageView) findViewById(R.id.nav_fanhui);
		nav_fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
//		mLocationErrText = (TextView) findViewById(R.id.location_errInfo_text);
//		mLocationErrText.setVisibility(View.GONE);
		
		
		
		pingpai.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
//				if (textView4.getText().toString().trim().equals("")) {
//
//					showCustomToast("请填写路灯编号");
//					
//					return;
//					
//				}
				
//				getGoodsList1();
				
			}
		});
		
		RelativeLayout anzhuang = (RelativeLayout) findViewById(R.id.anzhuang);
		anzhuang.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//先选择在上报

				if (geoLat == null && geoLng == null) {
                     showCustomToast("请打开手机的GPS");
					
					return;
				}
				
				if (isNetworkAvailable()) {
//					 showCustomToast("维度数据出来没有"+String.valueOf(geoLng));
//					params.add(new BasicNameValuePair("latitude",String.valueOf(geoLat)));");
					getGoodsList2();
				   } else {
					showCustomToast("当前网络不可用，请连接网络后在重试!");
				 }
				
			//	getGoodsList();
			}
		});
		// flag = "1";
		

		
		
		
	
	}
	
	
	
	
	private void getGoodsList() {
		myDialog = new ProDialog(CarnavActivity1.this, R.style.progressDialog);
		myDialog.show();
		new Thread(new Runnable() {

			@Override
			public void run() {
                    // boolean is=true;
				try {
					SharedPreferences sp = getSharedPreferences("mrsoft", MODE_PRIVATE);
					String roadsectionid = sp.getString("roadsectionid", "roadsectionid");
					String token = sp.getString("token", "token");
//					String url = UrlUtil.getUserUrl() + "blade-lamp/api/device/scanDevice";	
					String url=UrlUtil.getUserUrl() +"blade-city/api/device/scanDevice";
					List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
//					if(	//  flagresult  1 表示扫描     2 表示蓝牙 )
					if("1".equals(flagresult)){
						params.add(new BasicNameValuePair("qrcode",qrcode.trim()));
					}else{
						params.add(new BasicNameValuePair("sn",qrcode.trim()));
					}
					
					params.add(new BasicNameValuePair("longitude",String.valueOf(geoLng)));
					params.add(new BasicNameValuePair("latitude",String.valueOf(geoLat)));
					params.add(new BasicNameValuePair("roadSection",roadsectionid));
					
					
					String ruslt = HttpClientUtil.post(CarnavActivity1.this,url,token, params);
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




	/**
	 * 设置一些amap的属性
	 */
	private void setUpMap() {
		aMap.setLocationSource(this);// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false

		aMap.setOnMapTouchListener(this);
	}


	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();

		useMoveToLocationWithMapMode = true;
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
		deactivate();

		useMoveToLocationWithMapMode = false;
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
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
		if(null != mlocationClient){
			mlocationClient.onDestroy();
		}
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
					locationMarker = aMap.addMarker(new MarkerOptions().position(latLng)
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

	
	class CartAdapter1 extends BaseAdapter {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View inflate = null;
			ViewHolder1 holder = null;
			if (convertView == null) {
				// 复用乱序问题
				inflate = getLayoutInflater().inflate(R.layout.xuanzhe1, null);
				holder = new ViewHolder1();
				holder.tvGoodsName =  (TextView) inflate
						.findViewById(R.id.tv_goods_name);
				inflate.setTag(holder);
			} else {
				inflate = convertView;
				holder = (ViewHolder1) inflate.getTag();
			}
			final InCart inCart = mData1.get(position);
//			// 避免由于复用触发onChecked()事件
//			holder.btnCheck.setOnCheckedChangeListener(null);
			holder.tvGoodsName.setText(inCart.getGoodsName().toString());
			
			return inflate;
		}

		@Override
		public int getCount() {

			return mData1.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

	}

	class ViewHolder1 {
		
		 TextView tvGoodsName;
		// TextView tvGoodsPrice;
	}

//	/**
//	 * 选中商品改变
//	 */
//	private void notifyCheckedChanged1() {
//		mAdapter1.notifyDataSetChanged();
//	}

	

}

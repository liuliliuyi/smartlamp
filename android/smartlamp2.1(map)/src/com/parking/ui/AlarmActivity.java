package com.parking.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;






import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.maps.model.LatLng;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import com.parking.model.City;
import com.parking.model.Order;
import com.parking.smarthome.R;
import com.parking.util.ActivityBase;
import com.parking.util.HttpClientUtil;
import com.parking.util.JsonUtil;
import com.parking.util.ProDialog;
import com.parking.util.UrlUtil;
import com.parking.widget.AlertDiaLog;





import net.tsz.afinal.FinalBitmap;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.view.View.OnTouchListener;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class AlarmActivity extends ActivityBase implements AMapLocationListener,OnClickListener   {

	Intent intent = null;

	private String uid;

	
	private SharedPreferences sp;
	private Editor editor;
	private String shouji;
	
	private String editPwd;
	
	private MainAdapter adapter;
	private ArrayList<City> datas = new ArrayList<City>();
	private ArrayList<City> datas1 = new ArrayList<City>();
	private JSONObject goodsList = null;
	private ListView mycourse_list;
	private RelativeLayout oder_nofound,mylearn_list1;

	private String token;
	private ProDialog myDialog;
	private int page;
	
	private AMapLocationClient locationClient = null;
	private AMapLocationClientOption locationOption = null;
	
	private  Double  geoLat,geoLng;
	private  String  geoLat1,geoLng1;
	private int i;
	private String flag;
	private String wflag;
	
	
	private View indicator1;
	private View indicator2;
	private View indicator3;
	private View indicator4;
	

	private RelativeLayout layout_all, layout_nopay, layout_fahuo,
			layout_shouhuo;
	private TextView tv_all, tv_nopay, tv_fahuo, tv_shouhuo;
	
	Handler d = new Handler() {

		public void handleMessage(Message msg) {
			try {

				if (msg.what == 91) {
					// 更新界面
					new FinishRefresh().execute();
					String message = goodsList.getString("success");

					if (message != null && message.equals("true")) {
						
//						if (page == 1) {
							datas.clear();
//						    "longitude": "114.16353206025",     // 经度
//		                    "latitude": "30.49542382573",       // 纬度
							LatLng latLng = new LatLng(geoLat,geoLng);
							JsonUtil.getnewalarm(goodsList, datas,latLng);
//						} else {
//							datas1 = new ArrayList<City>();
//							JsonUtil.getnewalarm(goodsList, datas1);
//						}
						
						 if(!(mycourse_list==null)){
      
//					  	if (page == 1) {
							if (datas.size() == 0) {
								
								oder_nofound.setVisibility(View.VISIBLE);
								mylearn_list1.setVisibility(View.GONE);
								mycourse_list.setVisibility(View.GONE);
							} else {
								 adapter = new MainAdapter(
								 AlarmActivity.this, datas);
						         mycourse_list.setAdapter(adapter);
								 oder_nofound.setVisibility(View.GONE);
								 mylearn_list1.setVisibility(View.VISIBLE);
								 mycourse_list.setVisibility(View.VISIBLE);
							}
//						    } else {
//							if (datas1.size() == 0) {
//								showCustomToast("没有数据了");
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
		setContentView(R.layout.main2);
		
		TextView toptitile = (TextView) findViewById(R.id.toptitile);
		toptitile.setText("运维");
		RelativeLayout top_fanhui = (RelativeLayout) findViewById(R.id.top_fanhui);
		top_fanhui.setVisibility(View.VISIBLE);
		top_fanhui.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				  finish();	
			}
		});
		
//		page=1;
		
		
	}
	
	@Override  
    protected void onResume() {  
        super.onResume(); 
        i=0;
        page = 1;
        flag="0";
        wflag="0";
        init();
		//getGoodsList1();
        locationClient = new AMapLocationClient(AlarmActivity.this
				.getApplicationContext());
		locationOption = new AMapLocationClientOption();
		locationOption
				.setLocationMode(AMapLocationMode.Hight_Accuracy);
		locationClient.setLocationListener(AlarmActivity.this);
		initOption();
		locationClient.setLocationOption(locationOption);
		locationClient.startLocation();
		
    }  

//	@Override
//	public void onActivityCreated(Bundle savedInstanceState) {
//		super.onActivityCreated(savedInstanceState);
//
//		AlarmActivity.this = getActivity();
//		page=1;
//		// 初始化广告
//		adview();
//		// 初始化其他组件
//		init();
//
//	}



//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		return inflater.inflate(R.layout.main1, null);
//	}

	private void init() {
		
		oder_nofound = (RelativeLayout)findViewById(R.id.oder_nofound);
		sp = getSharedPreferences("mrsoft",0);
		token= sp.getString("token", "token");
		
		mylearn_list1= (RelativeLayout)findViewById(R.id.mylearn_list1);
		
		
		layout_all = (RelativeLayout) findViewById(R.id.layout_all);
		layout_all.setOnClickListener(this);

		layout_nopay = (RelativeLayout) findViewById(R.id.layout_nopay);
		layout_nopay.setOnClickListener(this);

		layout_fahuo = (RelativeLayout) findViewById(R.id.layout_fahuo);
		layout_fahuo.setOnClickListener(this);

		layout_shouhuo = (RelativeLayout) findViewById(R.id.layout_shouhuo);
		layout_shouhuo.setOnClickListener(this);
		
		tv_all = (TextView) findViewById(R.id.tv_all);
		tv_nopay = (TextView) findViewById(R.id.tv_nopay);
		tv_fahuo = (TextView) findViewById(R.id.tv_fahuo);
		tv_shouhuo = (TextView) findViewById(R.id.tv_shouhuo);
		

		indicator1 = findViewById(R.id.indicator1);
		indicator2 = findViewById(R.id.indicator2);
		indicator3 = findViewById(R.id.indicator3);
		indicator4 = findViewById(R.id.indicator4);
		
		
		tv_all.setTextColor(getResources().getColor(R.color.blue));
		tv_nopay.setTextColor(getResources().getColor(R.color.black));
		tv_fahuo.setTextColor(getResources().getColor(R.color.black));
		tv_shouhuo.setTextColor(getResources().getColor(R.color.black));
		
		indicator1.setVisibility(View.VISIBLE);
		indicator2.setVisibility(View.INVISIBLE);
		indicator3.setVisibility(View.INVISIBLE);
		indicator4.setVisibility(View.INVISIBLE);
		

		
		mycourse_list = (ListView)findViewById(R.id.mylearn_list);
		
		
	//	getGoodsList1();
		
		
		
//		mycourse_list.setOnItemClickListener(new OnItemClickListener() {
//			public void onItemClick(AdapterView<?> parent, View v,
//					int position, long id) {
//				
//				showCustomToast("正在定位,请等待!");
//				flag="1";
//				i= 0;
//				geoLng1=	datas.get(position-1).getLongitude().toString();
//				geoLat1=	datas.get(position-1).getLatitude().toString();
//
//				//这里还是用高德
//				if (isNetworkAvailable()) {
//					locationClient = new AMapLocationClient(AlarmActivity.this
//							.getApplicationContext());
//					locationOption = new AMapLocationClientOption();
//					locationOption
//							.setLocationMode(AMapLocationMode.Hight_Accuracy);
//					locationClient.setLocationListener(AlarmActivity.this);
//					initOption();
//					locationClient.setLocationOption(locationOption);
//					locationClient.startLocation();
//				} else {
//					showCustomToast("定位失败!");
//				}
//				
//			}
//		});
		

	
	
	}
	
	@Override
	public void onClick(View v) {
		

		if (v == layout_all) {
			allClick();
			wflag="0";
			getGoodsList1();  
		}
		if (v == layout_nopay) {
			nopayClick();
			wflag="1";
			getGoodsList1();  
		}
		if (v == layout_fahuo) {
			fahuoClick();
			wflag="2";
			getGoodsList1();  
		}
		if (v == layout_shouhuo) {
			shouhuoClick();
			wflag="3";
			getGoodsList1();  
		}
		

	}

	private void fahuoClick() {
		tv_all.setTextColor(getResources().getColor(R.color.black));
		tv_nopay.setTextColor(getResources().getColor(R.color.black));
		tv_fahuo.setTextColor(getResources().getColor(R.color.blue));
		tv_shouhuo.setTextColor(getResources().getColor(R.color.black));
		

		indicator1.setVisibility(View.INVISIBLE);
		indicator2.setVisibility(View.INVISIBLE);
		indicator3.setVisibility(View.VISIBLE);
		indicator4.setVisibility(View.INVISIBLE);
		
	}

	private void shouhuoClick() {
		tv_all.setTextColor(getResources().getColor(R.color.black));
		tv_nopay.setTextColor(getResources().getColor(R.color.black));
		tv_fahuo.setTextColor(getResources().getColor(R.color.black));
		tv_shouhuo.setTextColor(getResources().getColor(R.color.blue));
		

		indicator1.setVisibility(View.INVISIBLE);
		indicator2.setVisibility(View.INVISIBLE);
		indicator3.setVisibility(View.INVISIBLE);
		indicator4.setVisibility(View.VISIBLE);
		
	}

	
	private void allClick() {

		tv_all.setTextColor(getResources().getColor(R.color.blue));
		tv_nopay.setTextColor(getResources().getColor(R.color.black));
		tv_fahuo.setTextColor(getResources().getColor(R.color.black));
		tv_shouhuo.setTextColor(getResources().getColor(R.color.black));
		

		indicator1.setVisibility(View.VISIBLE);
		indicator2.setVisibility(View.INVISIBLE);
		indicator3.setVisibility(View.INVISIBLE);
		indicator4.setVisibility(View.INVISIBLE);
		
	}

	private void nopayClick() {

		tv_all.setTextColor(getResources().getColor(R.color.black));
		tv_nopay.setTextColor(getResources().getColor(R.color.blue));
		tv_fahuo.setTextColor(getResources().getColor(R.color.black));
		tv_shouhuo.setTextColor(getResources().getColor(R.color.black));
		

		indicator1.setVisibility(View.INVISIBLE);
		indicator2.setVisibility(View.VISIBLE);
		indicator3.setVisibility(View.INVISIBLE);
		indicator4.setVisibility(View.INVISIBLE);
		
	}

	
	
	
	// 判断网络模块---
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
	
	
//	public  void showCustomToast(String text) {
//
//		View toastRoot = LayoutInflater.from(AlarmActivity.this).inflate(
//				R.layout.common_toast, null);
//
//		((TextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
//		Toast toast = new Toast(AlarmActivity.this);
//		toast.setGravity(Gravity.CENTER, 0, 0);
//		toast.setDuration(Toast.LENGTH_SHORT);
//		toast.setView(toastRoot);
//		toast.show();
//
//	}
	
//	//请求公告数据
//	private void getGoodsList() {
//		
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//
//				try {
//					String url = UrlUtil.getUserUrl() + "/notice/latest";
//					JSONObject params = new JSONObject();
////					params.put("username", shouji);
////					params.put("password", editPwd);
//					goodsList = HttpClientUtil.doPost(url, params);
//
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//				try {
//					Message msg = new Message();
//					msg.what = 90;
//					d.sendEmptyMessage(msg.what);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//			}
//
//		}).start();
//
//	}
	
	
	private class FinishRefresh extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			return null;
		}

//		@Override
//		protected void onPostExecute(Void result) {
//			mycourse_list.onRefreshComplete();
//		}
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
				
				// 开启定位
				if("0".equals(flag)){
					if(i==0){
						i++;
						getGoodsList1();    
					}
				  }else{
					if(i==0){
						i++;
//						   "longitude": "114.16353206025",     // 经度
//		                    "latitude": "30.49542382573",       // 纬度
						
						SharedPreferences sp = getSharedPreferences("mrsoft", 0);
						Editor editor = sp.edit();
						editor.putString("jing", geoLat1);
						editor.putString("wei", geoLng1);
						editor.putString("geoLat", geoLat.toString());
						editor.putString("geoLng", geoLng.toString());
						editor.commit();
						Intent it = new Intent();
						it.setClass(AlarmActivity.this, GPSNaviActivity.class);
						startActivity(it);
						i++;
					}
					
					
				}
				
				}

			} 
		}

	
	
	//list
	private void getGoodsList1() {
		myDialog = new ProDialog(AlarmActivity.this, R.style.progressDialog);
		myDialog.show();
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					//  params.add(new BasicNameValuePair("dealStatus",wflag));
					String url = UrlUtil.getUserUrl() + "blade-lamp/api/faultInfo/listByDealStatus?dealStatus="+wflag;		
					SharedPreferences sp = getSharedPreferences("mrsoft", MODE_PRIVATE);
					String token = sp.getString("token", "token"); 
					String ruslt = HttpClientUtil.get(AlarmActivity.this,url,token);
					if (!(ruslt == null)) {
						goodsList = new JSONObject(ruslt);
					}
					myDialog.dismiss();
					
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
	
	
	
	public class MainAdapter extends BaseAdapter {
		private ArrayList<City> list;
		private Context context;
		private ImageLoader imageLoader = ImageLoader.getInstance();
		private DisplayImageOptions options; 
		private SharedPreferences sp;

		public MainAdapter(Context context, ArrayList<City> list) {
			this.context = context;
			this.list = list;
			
			imageLoader.init(ImageLoaderConfiguration.createDefault(context));
			
			options = new DisplayImageOptions.Builder()
					.showStubImage(R.drawable.baibaoxiang)
					.showImageForEmptyUri(R.drawable.baibaoxiang)
					.showImageOnFail(R.drawable.baibaoxiang)
					.cacheInMemory()
					.cacheOnDisc()
					.imageScaleType(ImageScaleType.EXACTLY)
					.bitmapConfig(Bitmap.Config.RGB_565)
					.displayer(new FadeInBitmapDisplayer(300)).build();
		}
		
		public void addList(List<City> list) {
			this.list.addAll(list);
		}

		@Override
		public int getCount() {
			return list.size();
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
		public View getView(final int position, View convertView, ViewGroup parent) {

			Viewholder vh = null;
			if (convertView == null) {
				vh = new Viewholder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.mainitem, null);

				vh.dingdanhao = (TextView) convertView
						.findViewById(R.id.dingdanhao);
				
				vh.textView3 = (TextView) convertView
						.findViewById(R.id.textView3);
				
				vh.xiadanshijian = (TextView) convertView
						.findViewById(R.id.xiadanshijian);
				vh.shijian = (TextView) convertView.findViewById(R.id.shijian);
				vh.zhuangtai = (TextView) convertView.findViewById(R.id.zhuangtai);
				vh.jiantouclick = (RelativeLayout) convertView.findViewById(R.id.jiantouclick);
				
				vh.jibie = (TextView) convertView.findViewById(R.id.jibie);
				convertView.setTag(vh);
			   } else {
				vh = (Viewholder) convertView.getTag();
			   }
			  if("0".equals(list.get(position).getDealStatus())){
				  vh.zhuangtai.setText("待处理"); 
			  }else if("1".equals(list.get(position).getDealStatus())){
				  vh.zhuangtai.setText("处理中"); 
			  }else if("2".equals(list.get(position).getDealStatus())){
				  vh.zhuangtai.setText("已提交"); 
			  }else if("3".equals(list.get(position).getDealStatus())){
				  vh.zhuangtai.setText("已完成"); 
			  }
			
			
			   vh.textView3.setText(list.get(position).getSn().toString());
			   vh.xiadanshijian.setText(list.get(position).getDescription().toString());
			   vh.shijian.setText("发生时间:   "+list.get(position).getCreateDate().toString());
			   vh.dingdanhao.setText(list.get(position).getDistance().toString()+"公里");
			   vh.zhuangtai.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					//加跳转
					if("3".equals(datas.get(position).getDealStatus().toString())){
						
					}else{
						Intent it = new Intent();
						Bundle b = new Bundle();
						b.putString("longitude", datas.get(position).getLongitude().toString());
						b.putString("latitude", datas.get(position).getLatitude().toString());
						b.putString("status", datas.get(position).getDealStatus().toString());
						b.putString("sn", datas.get(position).getSn().toString());
						
						it.putExtras(b);
						it.setClass(AlarmActivity.this, BaoxiuActivity.class);
						startActivity(it);
					}
					
					
				}
			});
			   vh.jiantouclick.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					
					showCustomToast("正在定位,请等待!");
					flag="1";
					i= 0;
					geoLng1=	datas.get(position).getLongitude().toString();
					geoLat1=	datas.get(position).getLatitude().toString();
	
					//这里还是用高德
					if (isNetworkAvailable()) {
						locationClient = new AMapLocationClient(AlarmActivity.this
								.getApplicationContext());
						locationOption = new AMapLocationClientOption();
						locationOption
								.setLocationMode(AMapLocationMode.Hight_Accuracy);
						locationClient.setLocationListener(AlarmActivity.this);
						initOption();
						locationClient.setLocationOption(locationOption);
						locationClient.startLocation();
					} else {
						showCustomToast("定位失败!");
					}
				}
			});
//			   vh.zhuangtai.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View arg0) {
//					
//					new AlertDialog(context).builder().setTitle("未安装")
//					.setMsg("您确定要安装吗？")
//					.setPositiveButton("确认安装", new OnClickListener() {
//						@Override
//						public void onClick(View v) {
//							
//						}
//					}).setNegativeButton("取消", new OnClickListener() {
//						@Override
//						public void onClick(View v) {
//
//						}
//					}).show();
//					
//				}
//			});
			    

			
			return convertView;
		}

		class Viewholder {
			TextView mylearn_detail;
			TextView dingdanhao;
			TextView mylearn_dengji;
			TextView shijian;
			TextView xiadanshijian;
			TextView zhuangtai;
			TextView jibie;
	        ImageView jiantou;
	        TextView textView3;
	        RelativeLayout jiantouclick;
		}
	}

	
}
package com.parking.ui;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.an.cityselect.City;
import com.an.cityselect.CityConstant;


import com.an.cityselect.MyRegion;
import com.nineoldandroids.animation.ObjectAnimator;
import com.parking.smarthome.R;
import com.parking.util.HttpClientUtil;
import com.parking.util.JsonUtil;
import com.parking.util.ProDialog;
import com.parking.util.ScreenUtils;
import com.parking.util.UrlUtil;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 单城市选择类
 * 
 * @author Anthony
 * 
 */
public class CitySelectActivity extends Activity implements OnClickListener {

	private Button btn_submit;
	// private ImageView img_back;
	private ListView lv_city;
	private ArrayList<MyRegion> regions;

	private CityAdapter adapter;
	private static int PROVINCE = 0x00;
	private static int CITY = 0x01;
	private static int DISTRICT = 0x02;
	
	private static int STREET = 0x03;
	private static int VILLAGE = 0x04;
	private static int GROUP = 0x05;
		
//	private CityUtils util;

	private TextView[] tvs = new TextView[6];
	private int[] ids = { R.id.rb_province, R.id.rb_city, R.id.rb_district,R.id.rb_street,R.id.rb_village,R.id.rb_group};

	private City city;
	int last, current;
	private int lastIndex; // 用于记录上次被点击的标题标签
	private ProDialog myDialog;
	private JSONObject goodsList = null;
	private String flag1;
	private final int ResultCode = 54135;
	Handler d = new Handler() {

		public void handleMessage(Message msg) {
			if (msg.what == 90) {
				// 更新界面
				try {

					// System.out.println("省份列表what======" + msg.what);
					// regions = (ArrayList<MyRegion>) msg.obj;
//					adapter.clear();
					regions = new ArrayList<MyRegion>();
					//省份解析
					JsonUtil.getcity1(goodsList, regions);
					adapter = new CityAdapter(CitySelectActivity.this);
					lv_city.setAdapter(adapter);
					adapter.addAll(regions);
					adapter.update();

				} catch (Exception e) {

					e.printStackTrace();
				}
			}

			if (msg.what == 91) {
				// 更新界面
				try {

					// System.out.println("城市列表what======" + msg.what);
					// regions = (ArrayList<MyRegion>) msg.obj;
					//adapter.clear();
					regions = new ArrayList<MyRegion>();
					JsonUtil.getcity1(goodsList, regions);
					adapter = new CityAdapter(CitySelectActivity.this);
					lv_city.setAdapter(adapter);
					adapter.addAll(regions);
					adapter.update();
					// break;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (msg.what == 92) {
				// 更新界面
				try {

					// System.out.println("城市列表what======" + msg.what);
					// regions = (ArrayList<MyRegion>) msg.obj;
					//adapter.clear();
					regions = new ArrayList<MyRegion>();
					JsonUtil.getcity1(goodsList, regions);
					adapter = new CityAdapter(CitySelectActivity.this);
					lv_city.setAdapter(adapter);
					adapter.addAll(regions);
					adapter.update();
					// break;

				} catch (Exception e) {

					e.printStackTrace();
				}
			}
			
			
			if (msg.what == 93) {
				// 更新界面
				try {

					// System.out.println("城市列表what======" + msg.what);
					// regions = (ArrayList<MyRegion>) msg.obj;
					//adapter.clear();
					regions = new ArrayList<MyRegion>();
					JsonUtil.getcity1(goodsList, regions);
					adapter = new CityAdapter(CitySelectActivity.this);
					lv_city.setAdapter(adapter);
					adapter.addAll(regions);
					adapter.update();
					// break;

				} catch (Exception e) {

					e.printStackTrace();
				}
			}
			
			
			if (msg.what == 94) {
				// 更新界面
				try {

					// System.out.println("城市列表what======" + msg.what);
					// regions = (ArrayList<MyRegion>) msg.obj;
					//adapter.clear();
					regions = new ArrayList<MyRegion>();
					JsonUtil.getcity1(goodsList, regions);
					adapter = new CityAdapter(CitySelectActivity.this);
					lv_city.setAdapter(adapter);
					adapter.addAll(regions);
					adapter.update();
					// break;

				} catch (Exception e) {

					e.printStackTrace();
				}
			}
			
			
			if (msg.what == 95) {
				// 更新界面
				try {

					// System.out.println("城市列表what======" + msg.what);
					// regions = (ArrayList<MyRegion>) msg.obj;
					//adapter.clear();
					regions = new ArrayList<MyRegion>();
					JsonUtil.getcity1(goodsList, regions);
					adapter = new CityAdapter(CitySelectActivity.this);
					lv_city.setAdapter(adapter);
					adapter.addAll(regions);
					adapter.update();
					// break;

				} catch (Exception e) {

					e.printStackTrace();
				}
			}
			
			
			if (msg.what == 96) {
				// 更新界面
				try {

					// System.out.println("城市列表what======" + msg.what);
					// regions = (ArrayList<MyRegion>) msg.obj;
					//adapter.clear();
					regions = new ArrayList<MyRegion>();
					JsonUtil.getcity1(goodsList, regions);
					adapter = new CityAdapter(CitySelectActivity.this);
					lv_city.setAdapter(adapter);
					adapter.addAll(regions);
					adapter.update();
					// break;

				} catch (Exception e) {

					e.printStackTrace();
				}
			}
			
			

		};

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_city_select);
		TextView toptitile = (TextView)findViewById(R.id.toptitile);
		toptitile.setText("地址选择");
		RelativeLayout	top_fanhui = (RelativeLayout) findViewById(R.id.top_fanhui);
		top_fanhui.setVisibility(View.VISIBLE);
		top_fanhui.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				finish();
			}
		});
		viewInit();

	}

	private void viewInit() {

		city = new City();
		Intent in = getIntent();
		city = in.getParcelableExtra("city");

		for (int i = 0; i < tvs.length; i++) {
			tvs[i] = (TextView) findViewById(ids[i]);
			tvs[i].setOnClickListener(this);
		}

		if (city == null) {
			city = new City();
			city.setProvince("");
			city.setCity("");
			city.setDistrict("");
			
			city.setStreet("");
			city.setVillage("");
			city.setGroup("");
		
		} else {
			if (city.getProvince() != null && !city.getProvince().equals("")) {
				tvs[0].setText(city.getProvince());
			}
			if (city.getCity() != null && !city.getCity().equals("")) {
				tvs[1].setText(city.getCity());
			}
			if (city.getDistrict() != null && !city.getDistrict().equals("")) {
				tvs[2].setText(city.getDistrict());
			}
			
			if (city.getStreet() != null && !city.getStreet().equals("")) {
				tvs[3].setText(city.getStreet());
			}
			if (city.getVillage() != null && !city.getVillage().equals("")) {
				tvs[4].setText(city.getVillage());
			}
			if (city.getGroup() != null && !city.getGroup().equals("")) {
				tvs[5].setText(city.getGroup());
			}
			
		}

		mIndicator = findViewById(R.id.indicator);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		
		// 网络请求初始化
		flag1 ="0";
		getGoodsList("0");

		tvs[current].setTextColor(getResources().getColor(R.color.blue));
		lv_city = (ListView) findViewById(R.id.lv_city);

		

	}

	// 初始化省,市,区
	private void getGoodsList(final String flag) {
		myDialog = new ProDialog(CitySelectActivity.this,
				R.style.progressDialog);
		myDialog.show();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String url =null;
					if("0".equals(flag1)){
						url= UrlUtil.getUserUrl() + "blade-sun/api/area/treeByType?type=1";	
					}else{
						url= UrlUtil.getUserUrl() + "blade-sun/api/area/child?id="+flag;
					}
							
					
					SharedPreferences sp = getSharedPreferences("mrsoft", MODE_PRIVATE);
					String token = sp.getString("token", "token");
					
					String ruslt = HttpClientUtil.get(CitySelectActivity.this,url,token);
				
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
					if("0".equals(flag1)){
						msg.what = 90;	
					}else if("1".equals(flag1)){
						msg.what = 91;	
					}else if("2".equals(flag1)){
						msg.what = 92;	
					}else if("3".equals(flag1)){
						msg.what = 93;	
					}else if("4".equals(flag1)){
						msg.what = 94;	
					}else if("5".equals(flag1)){
						msg.what = 95;	
					}
					d.sendEmptyMessage(msg.what);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}).start();
		
		
	}

	protected void onStart() {
		super.onStart();
		lv_city.setOnItemClickListener(onItemClickListener);
		// img_back.setOnClickListener(this);
		btn_submit.setOnClickListener(this);
	};

	/**
	 * 设置indicator的位置和宽度
	 */
	@Override
	protected void onResume() {
		super.onResume();
		screenWidth = ScreenUtils.getScreenWidth(this);
//		int width = screenWidth / 4;
//		int leftMargin = screenWidth / 6 - width / 2;
		
		int width = screenWidth / 8;
		int leftMargin = screenWidth / 17 - width / 4;
		
		LayoutParams params = (LayoutParams) mIndicator.getLayoutParams();
		params.width = width;
		params.leftMargin = leftMargin;
		mIndicator.setLayoutParams(params);
	}

	/**
	 * indicator动画
	 * 
	 * @param index
	 */
	private void moveIndicator(int index) {
//		int moveWidth = screenWidth / 3;
		int moveWidth = screenWidth / 6;
		ObjectAnimator
				.ofFloat(mIndicator, "translationX", lastIndex * moveWidth,
						index * moveWidth).setDuration(duration).start();
	}


	OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			if (current == PROVINCE) {
				String newProvince = regions.get(arg2).getName();
				if (!newProvince.equals(city.getProvince())) {
					city.setProvince(newProvince);
					city.setCity("");
					city.setDistrict("");
					tvs[0].setText(regions.get(arg2).getName());
					city.setRegionId(regions.get(arg2).getId());
					city.setProvinceCode(regions.get(arg2).getId());
					
					city.setCityCode("");
					city.setDistrictCode("");
					
					city.setStreetId("");
					city.setVillageId("");
					city.setGroupId("");
					
					tvs[1].setText("市");
					tvs[2].setText("区/县 ");
				   
				
				}
				
				moveIndicator(1);
				lastIndex = 1;
				current = 1;
				// 点击省份列表中的省份就初始化城市列表
				flag1 ="1";
				getGoodsList(city.getProvinceCode());
				//util.initCities(city.getProvinceCode());
			} else if (current == CITY) {
				String newCity = regions.get(arg2).getName();
				if (!newCity.equals(city.getCity())) {
					city.setCity(newCity);
					city.setDistrict("");
					tvs[1].setText(regions.get(arg2).getName());
					city.setRegionId(regions.get(arg2).getId());
					city.setCityCode(regions.get(arg2).getId());
					city.setDistrictCode("");

					city.setStreetId("");
					city.setVillageId("");
					city.setGroupId("");
					
					tvs[2].setText("区/县 ");
				}
				moveIndicator(2);
				lastIndex = 2;
				// 点击城市列表中的城市就初始化区县列表
				flag1 ="2";
				getGoodsList(city.getCityCode());
				current = 2;

			} else if (current == DISTRICT) {
				current = 3;
				city.setDistrictCode(regions.get(arg2).getId());
				city.setRegionId(regions.get(arg2).getId());
				city.setDistrict(regions.get(arg2).getName());
//				city.setDistrictCode(regions.get(arg2).getId());
				
				tvs[2].setText(regions.get(arg2).getName());
				moveIndicator(3);
				lastIndex = 3;
				getGoodsList(city.getDistrictCode());
			 //  R.id.rb_street,R.id.rb_village,R.id.rb_group   
			}else if (current == STREET) {
				current = 4;
				
				city.setRegionId(regions.get(arg2).getId());
				city.setStreet(regions.get(arg2).getName());
				city.setStreetId(regions.get(arg2).getId());
				
				tvs[3].setText(regions.get(arg2).getName());
				moveIndicator(4);
				lastIndex = 4;
				getGoodsList(city.getStreetId());
			    
			}else if (current == VILLAGE) {
				current = 5;
				
				city.setRegionId(regions.get(arg2).getId());
				city.setVillage(regions.get(arg2).getName());
				city.setVillageId(regions.get(arg2).getId());
				
				tvs[4].setText(regions.get(arg2).getName());
				moveIndicator(5);
				lastIndex = 5;
				getGoodsList(city.getVillageId());
			    
			}else if (current == GROUP) {
				current = 5;
				
				city.setRegionId(regions.get(arg2).getId());
				city.setGroup(regions.get(arg2).getName());
				city.setGroupId(regions.get(arg2).getId());
				
				tvs[5].setText(regions.get(arg2).getName());
				moveIndicator(5);
				lastIndex = 5;
//				getGoodsList(city.getVillageId());
			    
			}
			
			
			
			
			tvs[last].setTextColor(Color.BLACK);
			tvs[current].setTextColor(getResources().getColor(R.color.blue));
			last = current;
		}
	};
	private View mIndicator;
	private int screenWidth; // 屏幕宽度
	private final long duration = 300; // 动画时间

	class CityAdapter extends ArrayAdapter<MyRegion> {

		LayoutInflater inflater;

		public CityAdapter(Context con) {
			super(con, 0);
			inflater = LayoutInflater.from(CitySelectActivity.this);
		}

		@Override
		public View getView(int arg0, View v, ViewGroup arg2) {
			v = inflater.inflate(R.layout.item_city_select, null);
			TextView tv_city = (TextView) v.findViewById(R.id.tv_city);
			tv_city.setText(getItem(arg0).getName());
			return v;
		}

		public void update() {
			this.notifyDataSetChanged();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.btn_submit:// 确定按钮监听


			
			if (city.getProvinceCode() == null
			|| city.getProvinceCode().equals("")) {
		   
		       Toast.makeText(CitySelectActivity.this, "您还没有选择省份",
				Toast.LENGTH_SHORT).show();
		      return; 	      
	         }
		    if (city.getCityCode() == null
					|| city.getCityCode().equals("")) {
				Toast.makeText(CitySelectActivity.this, "您还没有选择城市",
						Toast.LENGTH_SHORT).show();
				return;
			}if (city.getStreetId() == null
					|| city.getStreetId().equals("")) {
				Toast.makeText(CitySelectActivity.this, "您还没有选择区/县",
						Toast.LENGTH_SHORT).show();
				
				return;
			}else if (city.getVillageId() == null
					|| city.getVillageId().equals("")) {
				Toast.makeText(CitySelectActivity.this, "您还没有选择镇/街道",
						Toast.LENGTH_SHORT).show();
				return;
			}
			
			
		
			 else if (city.getGroupId() == null
					|| city.getGroupId().equals("")) {
				Toast.makeText(CitySelectActivity.this, "您还没有选择屯/组",
						Toast.LENGTH_SHORT).show();
				return;
			}
			
			
			SharedPreferences   sp = getSharedPreferences("mrsoft", MODE_PRIVATE);
			Editor	editor = sp.edit();
			editor.putString("roadsectionid",city.getGroupId());
			editor.putString("areaname", city.getStreet()+city.getVillage()+city.getGroup());
			editor.commit();
			
			Intent it = getIntent();
			it.putExtra("code", city.getGroupId());
			it.putExtra("province", city.getProvince());
			it.putExtra("city", city.getCity());
			it.putExtra("district", city.getDistrict());
			it.putExtra("street", city.getStreet());
			it.putExtra("village", city.getVillage());
			it.putExtra("group", city.getGroup());
			
			
			setResult(ResultCode, it);
			finish();
			
			break;
		}
		if (ids[0] == v.getId()) {
			// 指示条动画
			moveIndicator(0);
			lastIndex = 0;
			current = 0;
			flag1 ="0";
			getGoodsList("0");
			tvs[last].setTextColor(Color.BLACK);
			tvs[current].setTextColor(getResources().getColor(R.color.blue));
			last = current;
		} else if (ids[1] == v.getId()) {
			if (city.getProvinceCode() == null
					|| city.getProvinceCode().equals("")) {
				current = 0;
				Toast.makeText(CitySelectActivity.this, "您还没有选择省份",
						Toast.LENGTH_SHORT).show();
				return;
			}
			// 指示条动画
			moveIndicator(1);
			lastIndex = 1;
			flag1 ="1";
			getGoodsList(city.getProvinceCode());
			current = 1;
			tvs[last].setTextColor(Color.BLACK);
			tvs[current].setTextColor(getResources().getColor(R.color.blue));
			last = current;
		} else if (ids[2] == v.getId()) {
			if (city.getProvinceCode() == null
					|| city.getProvinceCode().equals("")) {
				Toast.makeText(CitySelectActivity.this, "您还没有选择省份",
						Toast.LENGTH_SHORT).show();
//				current = 0;
//				flag1 ="0";
//				getGoodsList("0");
				return;
			} else if (city.getCityCode() == null
					|| city.getCityCode().equals("")) {
				Toast.makeText(CitySelectActivity.this, "您还没有选择城市",
						Toast.LENGTH_SHORT).show();
//				current = 1;
//				flag1 ="1";
//				getGoodsList(city.getProvinceCode());;
				return;
			}
			// 指示条动画
			moveIndicator(2);
			lastIndex = 2;
			current = 2;
			flag1 ="2";
			getGoodsList(city.getCityCode());
			tvs[last].setTextColor(Color.BLACK);
			tvs[current].setTextColor(getResources().getColor(R.color.blue));
			last = current;
		}
		
		else if (ids[3] == v.getId()) {
			if (city.getProvinceCode() == null
					|| city.getProvinceCode().equals("")) {
				Toast.makeText(CitySelectActivity.this, "您还没有选择省份",
						Toast.LENGTH_SHORT).show();
//				current = 0;
//				flag1 ="0";
//				getGoodsList("0");
				return;
			} else if (city.getCityCode() == null
					|| city.getCityCode().equals("")) {
				Toast.makeText(CitySelectActivity.this, "您还没有选择城市",
						Toast.LENGTH_SHORT).show();
//				current = 1;
//				flag1 ="1";
//				getGoodsList(city.getProvinceCode());;
				return;
			} else if (city.getDistrictCode() == null
					|| city.getDistrictCode().equals("")) {
				Toast.makeText(CitySelectActivity.this, "您还没有选择区/县",
						Toast.LENGTH_SHORT).show();
//				current = 2;
//				flag1 ="2";
//				getGoodsList(city.getCityCode());;
				return;
			}
			
			
			else if (city.getStreetId() == null
					|| city.getStreetId().equals("")) {
				Toast.makeText(CitySelectActivity.this, "您还没有选择区/县",
						Toast.LENGTH_SHORT).show();
//				current = 3;
//				flag1 ="3";
//				getGoodsList(city.getDistrictCode());;
				return;
			}
			// 指示条动画
			moveIndicator(3);
			lastIndex = 3;
			current = 3;
			flag1 ="3";
			getGoodsList(city.getDistrictCode());
			tvs[last].setTextColor(Color.BLACK);
			tvs[current].setTextColor(getResources().getColor(R.color.blue));
			last = current;
		
		
		
		}
		
		else if (ids[4] == v.getId()) {
			if (city.getProvinceCode() == null
					|| city.getProvinceCode().equals("")) {
				Toast.makeText(CitySelectActivity.this, "您还没有选择省份",
						Toast.LENGTH_SHORT).show();
//				current = 0;
//				flag1 ="0";
//				getGoodsList("0");
				return;
			} else if (city.getCityCode() == null
					|| city.getCityCode().equals("")) {
				Toast.makeText(CitySelectActivity.this, "您还没有选择城市",
						Toast.LENGTH_SHORT).show();
//				current = 1;
//				flag1 ="1";
//				getGoodsList(city.getProvinceCode());;
				return;
			}else if (city.getDistrictCode() == null
					|| city.getDistrictCode().equals("")) {
				Toast.makeText(CitySelectActivity.this, "您还没有选择区/县",
						Toast.LENGTH_SHORT).show();
//				current = 2;
//				flag1 ="2";
//				getGoodsList(city.getCityCode());;
				return;
			}
			
			
			else if (city.getStreetId() == null
					|| city.getStreetId().equals("")) {
				Toast.makeText(CitySelectActivity.this, "您还没有选择区/县",
						Toast.LENGTH_SHORT).show();
//				current = 3;
//				flag1 ="3";
//				getGoodsList(city.getDistrictCode());;
				return;
			}else if (city.getVillageId() == null
					|| city.getVillageId().equals("")) {
				Toast.makeText(CitySelectActivity.this, "您还没有选择镇/街道",
						Toast.LENGTH_SHORT).show();
//				current = 4;
//				flag1 ="4";
//				getGoodsList(city.getStreetId());;
				return;
			}
			// 指示条动画
			moveIndicator(4);
			lastIndex = 4;
			current = 4;
			flag1 ="4";
			getGoodsList(city.getStreetId());
			tvs[last].setTextColor(Color.BLACK);
			tvs[current].setTextColor(getResources().getColor(R.color.blue));
			last = current;
		
		}
		
		 else if (ids[5] == v.getId()) {
			if (city.getProvinceCode() == null
					|| city.getProvinceCode().equals("")) {
				Toast.makeText(CitySelectActivity.this, "您还没有选择省份",
						Toast.LENGTH_SHORT).show();
//				current = 0;
//				flag1 ="0";
//				getGoodsList("0");
				return;
		   } else if (city.getCityCode() == null
					|| city.getCityCode().equals("")) {
				Toast.makeText(CitySelectActivity.this, "您还没有选择城市",
						Toast.LENGTH_SHORT).show();
//				current = 1;
//				flag1 ="1";
//				getGoodsList(city.getProvinceCode());;
				return;
			}
		   
		   else if (city.getDistrictCode() == null
					|| city.getDistrictCode().equals("")) {
				Toast.makeText(CitySelectActivity.this, "您还没有选择区/县",
						Toast.LENGTH_SHORT).show();
//				current = 2;
//				flag1 ="2";
//				getGoodsList(city.getCityCode());;
				return;
			}
			
			
			else if (city.getStreetId() == null
					|| city.getStreetId().equals("")) {
				Toast.makeText(CitySelectActivity.this, "您还没有选择区/县",
						Toast.LENGTH_SHORT).show();
//				current = 3;
//				flag1 ="3";
//				getGoodsList(city.getDistrictCode());;
				return;
			}else if (city.getVillageId() == null
					|| city.getVillageId().equals("")) {
				Toast.makeText(CitySelectActivity.this, "您还没有选择镇/街道",
						Toast.LENGTH_SHORT).show();
//				current = 4;
//				flag1 ="4";
//				getGoodsList(city.getStreetId());;
				return;
			}
			// 指示条动画
			moveIndicator(5);
			lastIndex = 5;
			current = 5;
			flag1 ="5";
			getGoodsList(city.getVillageId());
			tvs[last].setTextColor(Color.BLACK);
			tvs[current].setTextColor(getResources().getColor(R.color.blue));
			last = current;
		
		}


	
	
	
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (KeyEvent.KEYCODE_BACK == keyCode) {
			finish();
			// overridePendingTransition(0, R.anim.slide_out_right);
		}
		return super.onKeyDown(keyCode, event);
	}

}

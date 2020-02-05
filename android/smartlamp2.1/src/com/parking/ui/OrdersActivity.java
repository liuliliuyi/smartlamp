package com.parking.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.parking.adapter.CardetailAdapter;
import com.parking.adapter.DingdanAdapter;

import com.parking.model.City;
import com.parking.model.Order;
import com.parking.smarthome.R;
import com.parking.util.ActivityBase;
import com.parking.util.Constants;
import com.parking.util.HttpClientUtil;
import com.parking.util.JsonUtil;
import com.parking.util.ProDialog;
import com.parking.util.UrlUtil;


public class OrdersActivity extends ActivityBase {
	private DingdanAdapter adapter;
	private ArrayList<Order> datas = new ArrayList<Order>();
	private SharedPreferences sp;
	JSONObject goodsList = null;
	private ListView dingdan_list;
	private RelativeLayout  oder_nofound;
	private final int ResultCode = 54137;
	Handler d = new Handler() {

		public void handleMessage(Message msg) {
			try {
				if (msg.what == 90) {
				
// {"success":true,"errorCode":"-1","msg":"查询当前用户有权限访问的项目列表成功!","body":{"data":[]}}
					String message = goodsList.getString("success");
					if (message != null && message.equals("true")) {
					
					
						datas=new ArrayList<Order>();
						JsonUtil.getHot(goodsList.toString(), datas);

						if (datas.size() > 0) {
							initarray();
						} else {
							
							oder_nofound.setVisibility(View.VISIBLE);
						}
					} else {
						showCustomToast("当前无状态");
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
		setContentView(R.layout.dingdanlist);
		oder_nofound=(RelativeLayout) findViewById(R.id.oder_nofound);
		dingdan_list = (ListView) findViewById(R.id.dingdan_list);
		dingdan_list.setOnItemClickListener(new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View v,
				int position, long id) {
			
			
//			geoLng1=	datas.get(position-1).getLongitude().toString();
//			geoLat1=	datas.get(position-1).getLatitude().toString();
			sp = getSharedPreferences("mrsoft", MODE_PRIVATE);					
			Editor editor = sp.edit();
			editor.putString("id",datas.get(position).getId());
			editor.putString("id1",datas.get(position).getId1());
			editor.putString("areaname",datas.get(position).getName());
			editor.commit();
			
			String xiangmuflag = sp.getString("xiangmuflag", "xiangmuflag");
			if("1".equals(xiangmuflag)){
				Intent it = new Intent();
				 it.setClass(OrdersActivity.this,
							HomeActivity1.class);
				startActivity(it);
			}else{
				
				Intent it = new Intent();
				Bundle b = new Bundle();
				b.putString("name", datas.get(position).getName().toString());
				it.putExtras(b);
				OrdersActivity.this.setResult(ResultCode, it);
				OrdersActivity.this.finish();
				
			}	
		}
	});
		
		
		TextView toptitile = (TextView) findViewById(R.id.toptitile);
		toptitile.setText("选择项目");
		RelativeLayout top_fanhui = (RelativeLayout) findViewById(R.id.top_fanhui);
		top_fanhui.setVisibility(View.VISIBLE);
		top_fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		if (isNetworkAvailable()) {
		
			getGoodsList();
		} else {
			showCustomToast("网络未连接!");
		}
	
	}

	
	public void initarray() {
		adapter = new DingdanAdapter(OrdersActivity.this,datas);
		dingdan_list.setAdapter(adapter);
	}

	
	private void getGoodsList() {
		final ProDialog myDialog = new ProDialog(OrdersActivity.this,
				R.style.progressDialog);
		myDialog.show();

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					String url = UrlUtil.getUserUrl() + "admin/api/getOfficeList";
					List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();  
					
					sp = getSharedPreferences("mrsoft", MODE_PRIVATE);
					String username = sp.getString("userName", "userName");
					params.add(new BasicNameValuePair("loginName",username));
					
					
					String ruslt = HttpClientUtil.post(OrdersActivity.this,url, params);
					if (!(ruslt == null)) {
						goodsList = new JSONObject(ruslt);
					}
					myDialog.dismiss();

				} catch (Exception e) {
					e.printStackTrace();
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
	

}
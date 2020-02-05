package com.parking.ui;



import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.parking.smarthome.R;
import com.parking.util.ActivityBase;
import com.parking.util.HttpClientUtil;
import com.parking.util.JsonUtil;
import com.parking.util.ProDialog;
import com.parking.util.UrlUtil;


public class AppointActivity extends ActivityBase implements OnClickListener {
	
	JSONObject goodsList = null;
	
	// 地址,地点图片等
		private ImageView carapoint_img;
		private TextView carapoint_name, carapoint_adress, carapoint_shijian;
		// 白天和晚上停车费
		private TextView carapoint_dayshijian1, carapoint_nightshijian1;
		

		private RelativeLayout carapoint_toshijian1;

		private ImageLoader imageLoader = ImageLoader.getInstance();// 得到图片加载器
		private DisplayImageOptions options; // 显示图像设置

	
		private RelativeLayout top_fanhui;
		
		private Button carapoint_queding;
	    private String flag;
	    private ScrollView apoint_find;
	    private RelativeLayout apoint_nofound;
	    private TextView carapoint_totext,carapoint_shijiandetail;
	    
		private SharedPreferences sp;
		private Editor editor;
	    Handler d = new Handler() {

		public void handleMessage(Message msg) {
			try {
				if (msg.what == 90) {
					// 更新界面

					String message = goodsList.getString("success");
					if (message != null && message.equals("true")) {
						// 数组的
                   
                    int a=  goodsList.toString().indexOf("entity");
                    String contain =goodsList.toString().substring(a+8,a+12);
                    if(!"null".equals(contain)){	
					JSONObject  entity=goodsList.getJSONObject("entity");
                     JSONArray  cartItems=entity.getJSONArray("cartItems");
                     if(cartItems.length()>0){
                     
                     apoint_find.setVisibility(View.VISIBLE); 
                     JSONObject  cartItems1 = cartItems.getJSONObject(0);
                     JSONObject  product=cartItems1.getJSONObject("product");
                     String createDate=cartItems1.getString("createDate");
                     String sn=product.getString("sn");
                     String price=product.getString("price");
                     String name=product.getString("name");
                    carapoint_name.setText("订单号:"+sn);
                    carapoint_adress.setText(name);
                    carapoint_dayshijian1.setText(price + "元/时(9:00-21:00)");
             		carapoint_nightshijian1.setText(price + "元/时(21:00-9:00)");
             		carapoint_shijian.setText(createDate);

             		sp = getSharedPreferences("mrsoft", MODE_PRIVATE);
            		String cardetail_shijiandetail = sp.getString("cardetail_shijiandetail", "cardetail_shijiandetail");
            		String cardetail_totext = sp.getString("cardetail_totext", "cardetail_totext");
            		editor = sp.edit();
            		
            		carapoint_totext = (TextView) findViewById(R.id.carapoint_totext);
            		
            		
            		if ("cardetail_shijiandetail".equals(cardetail_shijiandetail)) {
            		
            		} else {
            			carapoint_shijiandetail .setText(cardetail_shijiandetail);	
            		}
            		if ("cardetail_totext".equals(cardetail_totext)) {
            		
            		} else {
            			carapoint_totext.setText(cardetail_totext);
            		}
                     
                     
                     
                     }else{
                    	 apoint_find.setVisibility(View.GONE);
                 	     apoint_nofound.setVisibility(View.VISIBLE);  
                     }
                     }else{
                    	 apoint_find.setVisibility(View.GONE);
                 	     apoint_nofound.setVisibility(View.VISIBLE); 
                     }
					} else {
						showCustomToast("数据出现错误");
					}

				}
				if (msg.what == 91) {
					// 更新界面

					String message = goodsList.getString("success");
					if (message != null && message.equals("true")) {
						//showCustomToast("我的预订车位取消成功");
						 apoint_find.setVisibility(View.GONE); 
						
						 apoint_nofound.setVisibility(View.VISIBLE);

							sp = getSharedPreferences("mrsoft", 0);
							editor = sp.edit();
							editor.remove("jing");
							editor.remove("wei");
							editor.remove("geoLat");
							editor.remove("geoLng");
					
					
					} else {
						showCustomToast("数据出现错误");
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
		setContentView(R.layout.carapoint);
		
		carapoint_totext = (TextView) findViewById(R.id.carapoint_totext);
		carapoint_shijiandetail = (TextView) findViewById(R.id.carapoint_shijiandetail);
		
		apoint_find = (ScrollView) findViewById(R.id.apoint_find);
		apoint_nofound = (RelativeLayout) findViewById(R.id.apoint_nofound);
		apoint_find.setVisibility(View.GONE);
		TextView toptitile = (TextView) findViewById(R.id.toptitile);
		toptitile.setText("我的预订");
		RelativeLayout top_fanhui = (RelativeLayout) findViewById(R.id.top_fanhui);
		top_fanhui.setVisibility(View.VISIBLE);
		top_fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		if (isNetworkAvailable()) {
			flag = "1";
			getGoodsList();
		} else {
			showCustomToast("当前网络不可用，请连接网络后在重试!");
		}
		
		carapoint_name = (TextView) findViewById(R.id.carapoint_name);
		carapoint_adress = (TextView) findViewById(R.id.carapoint_adress);
		carapoint_shijian = (TextView) findViewById(R.id.carapoint_shijian);
		carapoint_img = (ImageView) findViewById(R.id.carapoint_img);
		carapoint_toshijian1 = (RelativeLayout) findViewById(R.id.carapoint_toshijian1);
		carapoint_dayshijian1 = (TextView) findViewById(R.id.carapoint_dayshijian1);
		carapoint_nightshijian1 = (TextView) findViewById(R.id.carapoint_nightshijian1);
		top_fanhui = (RelativeLayout) findViewById(R.id.top_fanhui);
		top_fanhui.setVisibility(View.VISIBLE);
		carapoint_queding = (Button) findViewById(R.id.carapoint_queding);
		carapoint_queding.setOnClickListener(this);
	}

//	// 网络数据的调用
//	public void initarray() {
//		adapter = new DingdanAdapter(AppointActivity.this,datas);
//		dingdan_list.setAdapter(adapter);
//	}

	// 请求网络数据
	private void getGoodsList() {
		final ProDialog myDialog = new ProDialog(AppointActivity.this,
				R.style.progressDialog);
		myDialog.show();

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					String ruslt=null;
					if("1".equals(flag)){
					String url = UrlUtil.getUserUrl() + "app/booked";
					ruslt = HttpClientUtil.doGetHead(url);
					}
					if("2".equals(flag)){
						String url = UrlUtil.getUserUrl() + "app/booked";
						ruslt = HttpClientUtil.delete(url);
						}

					if (!(ruslt == null)) {
						goodsList = new JSONObject(ruslt);
					}
					myDialog.dismiss();

				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					Message msg = new Message();
					if("1".equals(flag)){
					msg.what = 90;
					}
					if("2".equals(flag)){
						msg.what = 91;
						}
					
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
	public void onClick(View v) {
		
		if (v == carapoint_queding) {

			if (isNetworkAvailable()) {
				flag = "2";
				getGoodsList();	
				
				
			} else {
				showCustomToast("当前网络不可用，请连接网络后在重试!");
			}

		}
}
	
	
	
}

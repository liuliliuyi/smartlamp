package com.parking.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;


import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import com.parking.model.City;
import com.parking.model.Order;
import com.parking.model.Parking;



import com.parking.smarthome.R;
import com.parking.util.ActivityBase;
import com.parking.util.AppConstants;

import com.parking.util.HttpClientUtil;
import com.parking.util.JsonUtil;
import com.parking.util.LoadUtil;
import com.parking.util.ProDialog;
import com.parking.util.UrlUtil;
import com.parking.widget.DateTimeSelectorDialogBuilder;
import com.parking.widget.DateTimeSelectorDialogBuilder.OnSaveListener;
import com.parking.widget.DateTimeSelectorDialogBuilder1;
import com.parking.widget.DateTimeSelectorDialogBuilder1.OnSaveListener1;

public class CardetailActivity extends ActivityBase implements OnClickListener,
		OnSaveListener, OnSaveListener1 {
	// ��ַ,�ص�ͼƬ��
	private ImageView cardetail_img;
	private TextView cardetail_name, cardetail_adress, cardetail_count,
			cardetail_emptycount;
	// ���������ͣ����
	private TextView cardetail_dayshijian1, cardetail_nightshijian1;
	// ʱ��ѡ���Ԥ��ʱ��
	private TextView cardetail_shijiandetail, cardetail_totext;

	private RelativeLayout cardetail_countshijian, cardetail_toshijian1;

	private ImageLoader imageLoader = ImageLoader.getInstance();// �õ�ͼƬ������
	private DisplayImageOptions options; // ��ʾͼ������

	private DateTimeSelectorDialogBuilder dialogBuilder;
	private DateTimeSelectorDialogBuilder1 dialogBuilder1;

	private RelativeLayout top_fanhui;
	private JSONObject goodsList = null;
	private Button cardetail_queding;
	private String flag;
	private String id;
	private String name, price, freeplace, distance;
	private SharedPreferences sp;
	private Editor editor;

	// ��ֵ
	private String jing, wei, geoLat, geoLng;

	private RelativeLayout cardetail_bg;

	private LoadUtil loadUtil;

	private ProDialog myDialog;

	private ArrayList<Parking> datas = new ArrayList<Parking>();
	private int i, j, k;

	private String flagtime;

	Handler d = new Handler() {

		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg) {
			if (myDialog.isShowing() || !(myDialog == null)) {
				myDialog.dismiss();
			}
			try {
				if (msg.what == 90) {
					// ���½���

					String message = goodsList.getString("success");
					if (message != null && message.equals("true")) {
						SharedPreferences sp = getSharedPreferences("mrsoft", 0);
						Editor editor = sp.edit(); // ���Editor
						editor.putString("goodsId", id);
						editor.commit();

						showCustomToast("ͣ�������ѯ�ɹ�");
					} else {
						showCustomToast("ͣ���������ݷ�������");
					}

				}
				if (msg.what == 91) {
					// ���½���

					String message = goodsList.getString("success");

					if (message != null && message.equals("true")) {
						if (Integer.parseInt(freeplace) > 0) {
							sp = getSharedPreferences("mrsoft", MODE_PRIVATE);
							editor = sp.edit();
							editor.putString("freeplace", freeplace);
							editor.putString("jing", jing);
							editor.putString("wei", wei);
							editor.putString("geoLat", geoLat);
							editor.putString("geoLng", geoLng);
							editor.putString("cardetail_shijiandetail",
									cardetail_shijiandetail.getText()
											.toString());
							editor.putString("cardetail_totext",
									cardetail_totext.getText().toString());
							editor.commit();

							//if (i == 0) {
								flag = "4";
								getGoodsList();
							//	i++;
							//}
						} else {
							sp = getSharedPreferences("mrsoft", MODE_PRIVATE);
							editor = sp.edit();
							editor.putString("freeplace", freeplace);
							editor.putString("jing", jing);
							editor.putString("wei", wei);
							editor.putString("geoLat", geoLat);
							editor.putString("geoLng", geoLng);
							editor.putString("cardetail_shijiandetail",
									cardetail_shijiandetail.getText()
											.toString());
							editor.putString("cardetail_totext",
									cardetail_totext.getText().toString());
							editor.commit();

							Intent it = new Intent();
							Bundle b = new Bundle();
							b.putString("distance", distance);
							b.putString("name", name);
							b.putString("id", id);
							b.putString("price", price);
							b.putString("freeplace", freeplace);
							it.putExtras(b);
							it.setClass(CardetailActivity.this,
									CarpauseActivity.class);
							startActivity(it);

						}

						//
					} else {
						String message1 = goodsList.getString("message");
						if (message1.contains("��ǰͣ�����Ѿ�û�пճ�λ")) {

							flag = "5";
							getGoodsList();

						} else {
							if (message1.contains("��Աδ��¼")) {
								// ��½һ��
								//if (j == 0) {
									showCustomToast("���緱æ�����ٴε��Ԥ��");
									getGoodsList1();
							    //		j++;
								//}
							} else {
								showCustomToast(message1);
							}

							// {"message":"���Ѿ�Ԥ���˳�λ�������ظ�Ԥ��","returnCode":"0","entity":null,"success":false}
						}

						// showCustomToast(message1);
					}

				}

				if (msg.what == 92) {

					String message = goodsList.getString("success");

					// д�������ݿ���-----------------
					JsonUtil.getParking(goodsList.toString(), datas, id);
					getGpsList(datas);
					if (message != null && message.equals("true")) {
						Intent it = new Intent();
						if ("3".equals(flag)) {
							
							
							
							SharedPreferences sp = getSharedPreferences(
									"mrsoft", 0);
							Editor editor = sp.edit(); // ���Editor
							editor.putString("goodsId", id);
							editor.commit();
                            
							if("49".equals(id)){
								it = new Intent();
								it.setClass(CardetailActivity.this,
									CargpsActivity.class);
								startActivity(it);	
							}else if("51".equals(id)){
								
							}else{
								
							}
							
						}
						if ("4".equals(flag)) {
							SharedPreferences sp = getSharedPreferences(
									"mrsoft", 0);
							Editor editor = sp.edit(); // ���Editor
							editor.putString("goodsId", id);
							editor.commit();

							it = new Intent();
							it.setClass(CardetailActivity.this,
									HomeActivity.class);
							startActivity(it);
						}
						if ("5".equals(flag)) {
							sp = getSharedPreferences("mrsoft", MODE_PRIVATE);
							editor = sp.edit();
							editor.putString("freeplace", freeplace);
							editor.putString("jing", jing);
							editor.putString("wei", wei);
							editor.putString("geoLat", geoLat);
							editor.putString("geoLng", geoLng);
							editor.putString("cardetail_shijiandetail",
									cardetail_shijiandetail.getText()
											.toString());
							editor.putString("cardetail_totext",
									cardetail_totext.getText().toString());
							editor.commit();

							it = new Intent();
							Bundle b = new Bundle();
							b.putString("distance", distance);
							b.putString("name", name);
							b.putString("id", id);
							b.putString("price", price);
							b.putString("freeplace", freeplace);
							it.putExtras(b);
							it.setClass(CardetailActivity.this,
									CarpauseActivity.class);
							startActivity(it);
						}

						if (msg.what == 95) {
							// ���½���

							String message1 = goodsList.getString("success");

							if (message1 != null && message1.equals("true")) {
								//if (k == 0) {
									flag = "2";
									getGoodsList();
								//	k++;
								//}

							}
						}

					} else {
						showCustomToast("ͣ���������ݷ�������");
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
		setContentView(R.layout.cardetail);
		init();
		Listener();
		// ������������
		network();
	}

	private void network() {
		// ͼƬ��������ʼ��
		// imageLoader.init(ImageLoaderConfiguration
		// .createDefault(CardetailActivity.this));
		// // ʹ��DisplayImageOptions.Builder()����DisplayImageOptions
		// options = new DisplayImageOptions.Builder()
		// .showStubImage(R.drawable.yingyunav)
		// // ����ͼƬ�����ڼ���ʾ��ͼƬ
		// .showImageForEmptyUri(R.drawable.yingyunav)
		// // ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
		// .showImageOnFail(R.drawable.yingyunav)
		// // ����ͼƬ���ػ��������з���������ʾ��ͼƬ
		// .cacheInMemory()
		// // �������ص�ͼƬ�Ƿ񻺴����ڴ���
		// .cacheOnDisc()
		// // �������ص�ͼƬ�Ƿ񻺴���SD����
		// .imageScaleType(ImageScaleType.EXACTLY)
		// .bitmapConfig(Bitmap.Config.RGB_565)
		// .displayer(new FadeInBitmapDisplayer(300)).build();
		// imageLoader
		// .displayImage("http://www.baidu.com", cardetail_img, options);// ��ʾͷ��
		// url = UrlUtil.getUserUrl()+"app/parking/"+48;
		if (isNetworkAvailable()) {
			flag = "1";
			getGoodsList();
		} else {
			showCustomToast("��ǰ���粻���ã������������������!");
		}
	}

	/**
	 * ��������뱾�ص����ݿ�
	 * 
	 * @param beans
	 */

	private void getGpsList(ArrayList<Parking> beans) {
		// ��ɾ���ڲ���
		loadUtil.delete_name(id);
		for (int i = 0; i < beans.size(); i += 1) {

			loadUtil.insert_name(beans.get(i).getId(), beans.get(i)
					.getLongitude().toString(), beans.get(i).getLatitude()
					.toString(), beans.get(i).getParkingid().toString(), beans
					.get(i).getState().toString(), beans.get(i).getVip()
					.toString());
		}
	}

	// ������������
	private void getGoodsList1() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					sp = getSharedPreferences("mrsoft", MODE_PRIVATE);
					String username = sp.getString("shouji", "shouji");
					String password = sp.getString("editPwd", "editPwd");
					String url = UrlUtil.getUserUrl() + "app/logon";
					JSONObject params = new JSONObject();
					params.put("username", username);
					params.put("password", password);
					goodsList = HttpClientUtil.doPost(url, params);
					// myDialog.dismiss();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try {
					Message msg = new Message();
					msg.what = 95;
					d.sendEmptyMessage(msg.what);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}).start();

	}

	// ������������
	private void getGoodsList() {
		myDialog = new ProDialog(CardetailActivity.this, R.style.progressDialog);
		myDialog.show();

		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					if ("1".equals(flag)) {
						String url = UrlUtil.getUserUrl() + "app/parking/" + id;
						String ruslt = HttpClientUtil.doGet(url);
						if (!(ruslt == null)) {
							goodsList = new JSONObject(ruslt);
						}
					}
					if ("2".equals(flag)) {

						String url = UrlUtil.getUserUrl() + "app/booked/";
						// id;
						List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();

						params.add(new BasicNameValuePair("goodsId", id));
						String ruslt = HttpClientUtil.postsave(CardetailActivity.this, url, params);
						// String ruslt = HttpClientUtil.doGet(url);
						if (!(ruslt == null)) {
							goodsList = new JSONObject(ruslt);
						}
					}
					if ("3".equals(flag) || "4".equals(flag)
							|| "5".equals(flag)) {

						String url = UrlUtil.getUserUrl() + "app/statuslist/";
						// id;
						List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();

						params.add(new BasicNameValuePair("goodsId", id));
						String ruslt = HttpClientUtil.post(CardetailActivity.this,url, params);
						// String ruslt = HttpClientUtil.doGet(url);
						if (!(ruslt == null)) {
							goodsList = new JSONObject(ruslt);
						}
					}

					if (myDialog.isShowing() || !(myDialog == null))
						myDialog.dismiss();

				} catch (JSONException e) {
					e.printStackTrace();
				}

				try {
					Message msg = new Message();
					if ("1".equals(flag)) {
						msg.what = 90;
					}
					if ("2".equals(flag)) {
						msg.what = 91;
					}
					if ("3".equals(flag) || "4".equals(flag)
							|| "5".equals(flag)) {
						msg.what = 92;
					}
					d.sendEmptyMessage(msg.what);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}).start();

	}

	// ��������
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

	/**
	 * ��ʼ������
	 */
	private void init() {
		// ��ֵ����
		i = 0;
		j = 0;
		k = 0;
		loadUtil = new LoadUtil(CardetailActivity.this);

		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		name = b.getString("name");
		id = b.getString("id");
		price = b.getString("price");
		freeplace = b.getString("freeplace");

		jing = b.getString("jing");
		wei = b.getString("wei");
		geoLat = b.getString("geoLat");
		geoLng = b.getString("geoLng");

		cardetail_bg = (RelativeLayout) findViewById(R.id.cardetail_bg);

		cardetail_name = (TextView) findViewById(R.id.cardetail_name);
		cardetail_name.setText(name);

		cardetail_adress = (TextView) findViewById(R.id.cardetail_adress);
		cardetail_adress.setText(name + "(����)");
		cardetail_adress.setVisibility(View.GONE);
		cardetail_count = (TextView) findViewById(R.id.cardetail_count);
		
		
		if("49".equals(id)){
		cardetail_count.setText("10");
		}else{
		cardetail_count.setText("100");	
		}
		cardetail_emptycount = (TextView) findViewById(R.id.cardetail_emptycount);
		cardetail_emptycount.setText(freeplace);

		TextView toptitile = (TextView) findViewById(R.id.toptitile);
		cardetail_img = (ImageView) findViewById(R.id.cardetail_img);

		toptitile.setText("ͣ������");
		cardetail_countshijian = (RelativeLayout) findViewById(R.id.cardetail_countshijian);
		cardetail_toshijian1 = (RelativeLayout) findViewById(R.id.cardetail_toshijian1);
		cardetail_shijiandetail = (TextView) findViewById(R.id.cardetail_shijiandetail);
		cardetail_totext = (TextView) findViewById(R.id.cardetail_totext);

		cardetail_dayshijian1 = (TextView) findViewById(R.id.cardetail_dayshijian1);
		cardetail_nightshijian1 = (TextView) findViewById(R.id.cardetail_nightshijian1);

		cardetail_dayshijian1.setText(price + "Ԫ/ʱ(9:00-21:00)");
		cardetail_nightshijian1.setText(price + "Ԫ/ʱ(21:00-9:00)");

		top_fanhui = (RelativeLayout) findViewById(R.id.top_fanhui);
		top_fanhui.setVisibility(View.VISIBLE);

		cardetail_queding = (Button) findViewById(R.id.cardetail_queding);
	}

	/**
	 * ����
	 */
	private void Listener() {
		cardetail_countshijian.setOnClickListener(this);
		cardetail_toshijian1.setOnClickListener(this);
		top_fanhui.setOnClickListener(this);
		cardetail_queding.setOnClickListener(this);
		cardetail_bg.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == top_fanhui) {
			finish();
		}

		if (v == cardetail_bg) {
			sp = getSharedPreferences("mrsoft", MODE_PRIVATE);
			editor = sp.edit();
			editor.putString("tiaozhuanflag1", "0");
			editor.commit();

			flag = "3";
			getGoodsList();

		}
		if (v == cardetail_queding) {

			if ("��ѡ��Ԥ������ʱ��".equals(cardetail_totext.getText().toString())) {

				showCustomToast("�����ѡ��Ԥ������ʱ��");
				return;
			}

			// if ("0".equals(timer(cardetail_totext.getText().toString()))) {
			// showCustomToast("Ԥ������ʱ����ڵ�ǰ��ʱ��");
			// return;
			// }

			if ("��ѡ��ͣ��ʱ��".equals(cardetail_shijiandetail.getText().toString())) {

				showCustomToast("�����ѡ��ͣ��ʱ��");
				return;
			}

			if (isNetworkAvailable()) {
				flag = "2";
				SharedPreferences sp = getSharedPreferences("mrsoft", 0); // ���Preferences
				String shouji = sp.getString("shouji", "shouji");

				if (("shouji".equals(shouji))) {

					Intent it = new Intent();
					it.setClass(CardetailActivity.this, LoginActivity.class);
					startActivity(it);

				} else {
					getGoodsList();
				}

			} else {
				showCustomToast("��ǰ���粻���ã������������������!");
			}

		}

		if (v == cardetail_toshijian1) {
			if (dialogBuilder == null) {
				dialogBuilder = DateTimeSelectorDialogBuilder
						.getInstance(CardetailActivity.this);
				dialogBuilder.setOnSaveListener(this);
			}
			dialogBuilder.show();
		}
		if (v == cardetail_countshijian) {
			if (dialogBuilder1 == null) {
				dialogBuilder1 = DateTimeSelectorDialogBuilder1
						.getInstance(CardetailActivity.this);
				dialogBuilder1.setOnSaveListener1(this);
			}
			dialogBuilder1.show();
		}
	}

	// ʱ��Ƚ�
	public String timer(String time) {
		String flagtime = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time1 = time.replaceAll("ʱ", ":").replaceAll("��", ":")
				.replaceAll("-", "").trim()
				+ "00";
		Calendar now = Calendar.getInstance();
		String time2 = now.get(Calendar.YEAR) + "-"
				+ (now.get(Calendar.MONTH) + 1) + "-"
				+ now.get(Calendar.DAY_OF_MONTH) + " " + time1;

		try {
			// ��ȡ��ǰ��ʱ��
			Date today = df.parse(df.format(new Date()));
			Date date = df.parse(time2);
			long l = date.getTime() - today.getTime();

			// long day = l / (24 * 60 * 60 * 1000);
			// long hour = (l / (60 * 60 * 1000) - day * 24);
			// long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
			// if (min > 2) {
			// flag = "1";
			// } else {
			// flag = "0";
			// }
			if (l > 0) {
				flagtime = "1";
			} else {
				flagtime = "0";
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flagtime;

	}

	@Override
	public void onSaveSelectedDate(String selectedDate) {
		if (selectedDate.substring(0, 4).contains("00ʱ")) {

			cardetail_totext.setText("05����");
		} else {
			cardetail_totext.setText(selectedDate.substring(0, 4));
		}

	}

	@Override
	public void onSaveSelectedDate1(String selectedDate) {
		if (selectedDate.substring(0, 4).contains("00ʱ")) {

			cardetail_shijiandetail.setText("1Сʱ��");
		} else {
			if (selectedDate.contains("8Сʱ����")) {
				cardetail_shijiandetail.setText(selectedDate.substring(0, 5));
			} else {
				cardetail_shijiandetail.setText(selectedDate.substring(0, 4));
			}

		}

		
	}
}
package com.parking.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.ScrollView;
import android.widget.TextView;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;


import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.parking.model.Parking;


import com.parking.smarthome.R;
import com.parking.util.ActivityBase;

import com.parking.util.HttpClientUtil;
import com.parking.util.JsonUtil;
import com.parking.util.LoadUtil;
import com.parking.util.ProDialog;
import com.parking.util.UrlUtil;



public class CarpauseActivity extends ActivityBase implements OnClickListener {
	// ��ַ,�ص�ͼƬ��
	private ImageView cardause_img;
	private TextView cardause_name, cardause_adress, cardause_count,
			cardause_emptycount;
	// ���������ͣ����
	private TextView cardause_dayshijian1, cardause_nightshijian1;

	private ImageLoader imageLoader = ImageLoader.getInstance();// �õ�ͼƬ������
	private DisplayImageOptions options; // ��ʾͼ������
	private RelativeLayout top_fanhui;

	private Button cardause_queding;

	//private PullToRefreshScrollView mPtrScrollView;

	
    private TextView  cardause_pepole;
	private String id;

	private JSONObject goodsList = null;
	
	private String flag;
	
	private RelativeLayout  cardause_bg;
	private LoadUtil loadUtil;
	private ArrayList<Parking> datas = new ArrayList<Parking>();
	private Timer mTimer;
	private TimerTask mTimerTask;
	private int recLen;
	private ProDialog myDialog;
	
	final Handler handler = new Handler() {
		
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
					if (recLen > 0) {
//						if(recLen % 5== 0) {
//							flag="1";	
							getGoodsList();
//						}else{
//							
//						}		
					}
					else{
						clearTimer();
						recLen = 86409;
						inittime();
					}		
			}
			}
		};
	Handler d = new Handler() {

		public void handleMessage(Message msg) {
			try {
				if (msg.what == 90) {
					// ���½���

					String message = goodsList.getString("success");
					
					if (message != null && message.equals("true")) {
						String entity = goodsList.getString("entity");
						if(entity==null||"null".equals(entity)){
							clearTimer();
							Intent it = new Intent();
							it.setClass(CarpauseActivity.this,
									HomeActivity.class);
							startActivity(it);
						    CarpauseActivity.this.finish();
						    showCustomToast("��ǰ�пճ�λ,�����Ŷ�,��Ԥ���ɹ�");
						}else{
							cardause_pepole.setText(entity);	
						}
						
						SharedPreferences	sp = getSharedPreferences("mrsoft", 0);
						Editor editor = sp.edit(); // ���Editor
						editor.putString("goodsId",id);
						editor.putString("pepole",entity);
						editor.commit();
					
					} else {
						showCustomToast("ͣ���������ݷ�������");
					}

				}
				if (msg.what == 93) {
					// ���½���

					String message = goodsList.getString("success");
					
					
					if (message != null && message.equals("true")) {
						//cardause_pepole.setText("0");
						SharedPreferences	sp = getSharedPreferences("mrsoft", 0);
						Editor editor = sp.edit(); 
						editor.remove("pepole");
						editor.commit();
						//mPtrScrollView.onRefreshComplete();// �ر�ˢ�¶���
						//showCustomToast("ͣ�������ѯ�ɹ�");
					} else {
						showCustomToast("ͣ���������ݷ�������");
					}
				}
				if (msg.what == 92) {
					String message = goodsList.getString("success");
					// д�������ݿ���-----------------
					JsonUtil.getParking(goodsList.toString(),datas,id);
					getGpsList(datas);
					if (message != null && message.equals("true")) {
						Intent it = new Intent();
						if ("3".equals(flag)) {
							clearTimer();
							if("49".equals(id)){
								it = new Intent();
								it.setClass(CarpauseActivity.this,
										CargpsActivity.class);
								startActivity(it);	
							}else if("51".equals(id)){
//								it = new Intent();
//								it.setClass(CarpauseActivity.this,
//										HouseGPSActivity.class);
//								startActivity(it);
							}else{
//								it = new Intent();
//								it.setClass(CarpauseActivity.this,
//										HouseGPSActivity1.class);
//								startActivity(it);
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
		setContentView(R.layout.carpause);
		init();
		Listener();
		// ������������
		network();
		recLen = 86409;
		inittime();
	}

	private void network() {

		// ͼƬ��������ʼ��
		imageLoader.init(ImageLoaderConfiguration
				.createDefault(CarpauseActivity.this));
		// ʹ��DisplayImageOptions.Builder()����DisplayImageOptions
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.yingyunav)
				// ����ͼƬ�����ڼ���ʾ��ͼƬ
				.showImageForEmptyUri(R.drawable.yingyunav)
				// ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
				.showImageOnFail(R.drawable.yingyunav)
				// ����ͼƬ���ػ��������з���������ʾ��ͼƬ
				.cacheInMemory()
				// �������ص�ͼƬ�Ƿ񻺴����ڴ���
				.cacheOnDisc()
				// �������ص�ͼƬ�Ƿ񻺴���SD����
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new FadeInBitmapDisplayer(300)).build();
		imageLoader.displayImage("http://www.baidu.com", cardause_img, options);// ��ʾͷ��

		if (isNetworkAvailable()) {

			getGoodsList();
		} else {
			showCustomToast("��ǰ���粻���ã������������������!");
		}
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
	 * ��������뱾�ص����ݿ�
	 * 
	 * @param beans
	 */

	private void getGpsList(ArrayList<Parking> beans) {
		// ��ɾ���ڲ���
		loadUtil.delete_name("1");
		for (int i = 0; i < beans.size(); i += 1) {

			loadUtil.insert_name(beans.get(i).getId(), beans.get(i)
					.getLongitude().toString(), beans.get(i).getLatitude()
					.toString(), beans.get(i).getParkingid().toString(), beans
					.get(i).getState().toString(), beans.get(i).getVip()
					.toString());
		}
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
					Message message = new Message();
					message.what = 1;
					handler.sendMessage(message);

				}
			};
			// ��ʼһ����ʱ����
			mTimer.schedule(mTimerTask, 0, 9000);
		}
	
	
	
	/**
	 * ��ʼ������
	 */
	private void init() {
		loadUtil = new LoadUtil(CarpauseActivity.this);
		flag="1";
		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		String name = b.getString("name");
		id = b.getString("id");
		String price = b.getString("price");
		String freeplace = b.getString("freeplace");

		cardause_name = (TextView) findViewById(R.id.cardause_name);
		cardause_name.setText(name);

		cardause_adress = (TextView) findViewById(R.id.cardause_adress);
		cardause_adress.setText(name + "(����)");

		cardause_count = (TextView) findViewById(R.id.cardause_count);
		cardause_count.setText("100");

		
		cardause_pepole=(TextView) findViewById(R.id.cardause_pepole);
		
		cardause_emptycount = (TextView) findViewById(R.id.cardause_emptycount);
		cardause_emptycount.setText(freeplace);

		TextView toptitile = (TextView) findViewById(R.id.toptitile);
		cardause_img = (ImageView) findViewById(R.id.cardause_img);
		toptitile.setText("�Ŷ�����");

		cardause_dayshijian1 = (TextView) findViewById(R.id.cardause_dayshijian1);
		cardause_nightshijian1 = (TextView) findViewById(R.id.cardause_nightshijian1);

		cardause_dayshijian1.setText(price + "Ԫ/ʱ(9:00-21:00)");
		cardause_nightshijian1.setText(price + "Ԫ/ʱ(21:00-9:00)");

		top_fanhui = (RelativeLayout) findViewById(R.id.top_fanhui);
		top_fanhui.setVisibility(View.VISIBLE);
		cardause_queding = (Button) findViewById(R.id.cardause_queding);
		
		cardause_bg = (RelativeLayout) findViewById(R.id.cardause_bg);
		
		
//		// ˢ������
//		mPtrScrollView = (PullToRefreshScrollView) findViewById(R.id.ptrScrollView_home);
//		mPtrScrollView
//				.setOnRefreshListener(new OnRefreshListener<ScrollView>() {
//
//					public void onRefresh(
//							PullToRefreshBase<ScrollView> refreshView) {
//						getGoodsList();
//						//new GetDataTask().execute();
//					}
//				});

	
	
	
	}

	// ������������
	private void getGoodsList() {
		
		if(!"1".equals(flag)){
			myDialog = new ProDialog(CarpauseActivity.this,
				R.style.progressDialog);
		myDialog.show();
		}
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
						
					if ("4".equals(flag)) {

					   String url = UrlUtil.getUserUrl() + "app/removeline/";
					   List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();

						params.add(new BasicNameValuePair("goodsId", id));
						String ruslt = HttpClientUtil.post(CarpauseActivity.this,url, params);
						// String ruslt = HttpClientUtil.doGet(url);
						if (!(ruslt == null)) {
							goodsList = new JSONObject(ruslt);
						}
					}
						
					if ("1".equals(flag)) {

						   String url = UrlUtil.getUserUrl() + "app/line/" + id;
							String ruslt = HttpClientUtil.doGet(url);
							if (!(ruslt == null)) {
								goodsList = new JSONObject(ruslt);
							}
						}
					
					
					if ("3".equals(flag)) {

							String url = UrlUtil.getUserUrl() + "app/statuslist/";
							// id;
							List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();

							params.add(new BasicNameValuePair("goodsId", id));
							String ruslt = HttpClientUtil.post(CarpauseActivity.this,url, params);
							// String ruslt = HttpClientUtil.doGet(url);
							if (!(ruslt == null)) {
								goodsList = new JSONObject(ruslt);
							}
						}
						
					if(!"1".equals(flag)){
						if(myDialog.isShowing()&&!(myDialog==null)){
						myDialog.dismiss();
					}}
				} catch (JSONException e) {
					e.printStackTrace();
				}

				try {
					Message msg = new Message();
					
					if ("1".equals(flag)) {
						msg.what = 90;
					}
					
					if ("3".equals(flag)) {
						msg.what = 92;
					}
	
					if ("4".equals(flag)) {
						msg.what = 93;
					}
					
					
					d.sendEmptyMessage(msg.what);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}).start();

	}

	
	/**
	 * ����
	 */
	private void Listener() {

		top_fanhui.setOnClickListener(this);
		cardause_queding.setOnClickListener(this);
		cardause_bg.setOnClickListener(this);
	
	}

	@Override
	public void onClick(View v) {
		if (v == top_fanhui) {
			clearTimer();
			finish();
		}
		if (v == cardause_queding) {
			cardause_queding.setVisibility(View.GONE);
			flag = "4";
			getGoodsList();
		
		
		}
	
		if (v == cardause_bg) {
			flag = "3";
			getGoodsList();

		}
	
	}


	
	
//	private class GetDataTask extends AsyncTask<Void, Void, String[]> {
//		protected String[] doInBackground(Void... params) {
//			// ����ˢ��
//			try {
//				Thread.sleep(1500);
//			} catch (InterruptedException e) {
//			}
//			return null;
//		}
//
//		protected void onPostExecute(String[] result) {
//			mPtrScrollView.onRefreshComplete();// �ر�ˢ�¶���
//		}
//
//	}
	  @Override  
	    public boolean onKeyDown(int keyCode, KeyEvent event)  
	    {  
	        if (keyCode == KeyEvent.KEYCODE_BACK )  
	        {  
	        	clearTimer();
				finish();  
	
	        }   
	        return false;  
	          
	    }  
}
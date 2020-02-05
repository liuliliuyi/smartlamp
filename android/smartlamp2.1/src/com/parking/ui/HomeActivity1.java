package com.parking.ui;



import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import net.tsz.afinal.FinalBitmap;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;



import com.amap.api.maps.model.Text;
import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;
import com.example.lanya_lingyang_5.MainActivity;


import com.king.zxing.CaptureActivity;
import com.parking.lib.MyAdGallery;
import com.parking.lib.MyAdGallery.MyOnItemClickListener;
import com.parking.model.City;
import com.parking.model.Parking;
import com.parking.smarthome.R;
import com.parking.util.HttpClientUtil;
import com.parking.util.UpdateManager;
import com.parking.util.UrlUtil;







import android.app.Activity;
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
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.View.OnCreateContextMenuListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity1 extends Activity  implements
    OnTouchListener{
	
	
	private final int ResultCode = 54135;
	Intent intent = null;
	
	private RelativeLayout[] relativeLayouts = new RelativeLayout[8];
	private int[] ids = {R.id.main_money, R.id.main_parking, R.id.main_sevice,
			R.id.main_wash, R.id.yuyingnav, R.id.youhuihongdong,R.id.main_shidian,R.id.main_money2};

	
	private MyAdGallery gallery; 
	LinearLayout ovalLayout; 
	
	private int[] imageId = new int[] { R.drawable.img01, R.drawable.img02,
			R.drawable.img03 };

	private String[] mris = {
			"http://www.sksoon.com/linyang/test1.png",
			"http://www.sksoon.com/linyang/test2.png",
			"http://www.sksoon.com/linyang/test1.png"
	
	};

	private String uid;


	private BaiduASRDigitalDialog mDialog = null;
	private DialogRecognitionListener mDialogListener = null;
	
	private String API_KEY = "8MAxI5o7VjKSZOKeBzS4XtxO";
	private String SECRET_KEY = "Ge5GXVdGQpaxOmLzc8fOM8309ATCz9Ha";

	private String recognition_result = null;

	private SharedPreferences sp;
	private Editor editor;
	private String shouji;
	private JSONObject goodsList = null;
	private String editPwd;

	// 瀹氫綅-------
	
	private Double geoLat, geoLng;
	// private int i,k;
	private ArrayList<Parking> datas = new ArrayList<Parking>();
	
	private ArrayList<City> datascity = new ArrayList<City>();
	
	private int j;
	private String booleanj;
	private EditText  find_car;
    private TextView  xuanzhexiangmu;
    

    
    Handler d = new Handler() {

		public void handleMessage(Message msg) {
			try {
				if (msg.what == 90) {
					// 更新界面

					String status = goodsList.getString("success");
					if ("true".equals(status)) {
						JSONObject data = goodsList.getJSONObject("body");
						String version = data.getString("appversion");
						String url1 = data.getString("appurl");

						UpdateManager manager = new UpdateManager(
								HomeActivity1.this, version, url1);
						// 检查软件更新
						manager.checkUpdate();

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
		setContentView(R.layout.main);
		
		sp = getSharedPreferences("mrsoft", 0);
		Editor editor = sp.edit();
		editor.putString("onReply", "onReply");
		editor.commit();
		adview();
		init();	
    }
	
	@Override
	public void onResume() {
		super.onResume();
		j = 0;
		booleanj = null;
		goodsList = null;
		sp = getSharedPreferences("mrsoft", 0);
		editor = sp.edit();
		editor.putString("onReply", "1");
		editor.commit();
		
		getGoodsList();
	}
	
	
     private void getGoodsList() {
		
		new Thread(new Runnable() {

			@Override
			public void run() {
                    
				try {
					String url = UrlUtil.getUserUrl() + "blade-sun/api/version/version";		
		
					SharedPreferences sp = getSharedPreferences("mrsoft", MODE_PRIVATE);
					String token = sp.getString("token", "token");
					
					String ruslt = HttpClientUtil.get(HomeActivity1.this,url,token);
					
//					String ruslt = HttpClientUtil.post(HomeActivity1.this,url, params);
					if (!(ruslt == null)) {
						goodsList = new JSONObject(ruslt);
					}
					

				} catch (JSONException e) {
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

	
	
	

	private void init() {
		for (int i = 0; i < relativeLayouts.length; i++) {
			relativeLayouts[i] = (RelativeLayout) findViewById(ids[i]);
			relativeLayouts[i].setOnTouchListener(this);
		}

		
	}

	// 骞垮憡鎻掍欢
	private void adview() {
		// 骞垮憡鍥剧墖鐨勫０鏄�++++++++++++++++++++++++++++++++++++++++++++++++++
		FinalBitmap.create(HomeActivity1.this); 
		gallery = (MyAdGallery)findViewById(R.id.adgallery); 
		ovalLayout = (LinearLayout)findViewById(R.id.ovalLayout);
		
		gallery.start(HomeActivity1.this, mris, imageId, 3000, ovalLayout,
				R.drawable.dot_focused, R.drawable.dot_normal);

		TextView naba = (TextView) findViewById(R.id.nabatext);
		
		
		xuanzhexiangmu= (TextView) findViewById(R.id.xuanzhexiangmu);

		SharedPreferences sp = getSharedPreferences("mrsoft", 0); 
		
		String areaname= sp.getString("areaname", "areaname");
		if("areaname".equals(areaname)){
			xuanzhexiangmu.setText("");
		}else{
			xuanzhexiangmu.setText(areaname);
		}
		
		
		shouji = sp.getString("shouji", "shouji");
		editPwd = sp.getString("editPwd", "editPwd");
		
//		if ("shouji".equals(shouji)) {
//			naba.setText("鏅烘収杩愮淮鍔熻兘鍗冲皢涓婄嚎锛�");
//		} else {
//			String shouji1 = shouji.substring(0, 3) + "****"
//					+ shouji.substring(7, shouji.length());
//			naba.setText("閬ㄦ硦鍋滆溅娆㈣繋鐢ㄦ埛"+ shouji1 );
//		}

		gallery.setMyOnItemClickListener(new MyOnItemClickListener() {
			public void onItemClick(int curIndex) {
				if (curIndex == 0) {
					
				}
				if (curIndex == 1) {
					
				}
				if (curIndex == 2) {
					
				}

			}
		});
	}
	
	private void onClick(View v) {
		switch (v.getId()) {
		
		        case R.id.main_shidian:
		        	
		        	
		        	SharedPreferences sp2 = getSharedPreferences("mrsoft", 0); 
//					String areaname2= sp2.getString("areaname", "areaname");
					String roadsectionid2=sp2.getString("roadsectionid", "roadsectionid");
					
					if("roadsectionid".equals(roadsectionid2)){
						Intent it3= new Intent();
						it3.setClass(HomeActivity1.this, CitySelectActivity.class);
						startActivityForResult(it3, ResultCode);
						//xuanzhexiangmu.setText("");
					}else{
						Intent it = new Intent();
						it.setClass(HomeActivity1.this, CustomCaptureActivity3.class);
						startActivity(it);
					}
		        	
		        	
		        	break;
	
				case R.id.main_parking:

				
					intent = new Intent(HomeActivity1.this, AlarmActivity.class);//
					startActivity(intent);
					
					
					break;
					
				case R.id.main_wash:
					
                    SharedPreferences sp1 = getSharedPreferences("mrsoft", 0); 
					String roadsectionid1=sp1.getString("roadsectionid", "roadsectionid");
					
					if("roadsectionid".equals(roadsectionid1)){
						Intent it5= new Intent();
						it5.setClass(HomeActivity1.this, CitySelectActivity.class);
						startActivityForResult(it5, ResultCode);
						
					}else{
						Intent it = new Intent();
						it.setClass(HomeActivity1.this, MainActivity.class);
						startActivity(it);
					}
					break;
					
				case R.id.main_sevice:
					
					
					Intent it1 = new Intent();
					it1.setClass(HomeActivity1.this, CustomCaptureActivity2.class);
					startActivity(it1);
					
					break;
			
					// 选择项目
				case R.id.youhuihongdong:
					
					Intent it2 = new Intent();
					it2.setClass(HomeActivity1.this, CitySelectActivity.class);
					startActivityForResult(it2, ResultCode);
					
					break;
					
				case R.id.main_money:
					
					SharedPreferences sp = getSharedPreferences("mrsoft", 0); 
					String areaname= sp.getString("areaname", "areaname");
					String roadsectionid=sp.getString("roadsectionid", "roadsectionid");
					
					if("roadsectionid".equals(roadsectionid)){
						Intent it3= new Intent();
						it3.setClass(HomeActivity1.this, CitySelectActivity.class);
						startActivityForResult(it3, ResultCode);
						//xuanzhexiangmu.setText("");
					}else{
						Intent it = new Intent();
						it.setClass(HomeActivity1.this, CustomCaptureActivity.class);
						startActivity(it);
					}
					
					
					break;
					
					
					
                 case R.id.main_money2:
					
//					SharedPreferences sp11 = getSharedPreferences("mrsoft", 0); 
//
//					String roadsectionid11=sp11.getString("roadsectionid", "roadsectionid");
//					
//					if("roadsectionid".equals(roadsectionid11)){
//						Intent it3= new Intent();
//						it3.setClass(HomeActivity1.this, CitySelectActivity.class);
//						startActivityForResult(it3, ResultCode);
//						
//					}else{
						Intent it = new Intent();
						it.setClass(HomeActivity1.this, TongjiActivity.class);
						startActivity(it);
//					}
					
					
					break;
						
		     
				
				
				
				
				case R.id.yuyingnav:

			     if (mDialog == null) {
				if (mDialog != null) {
					mDialog.dismiss();
				}
				Bundle params = new Bundle();
				// 璁剧疆API_KEY, SECRET_KEY
				params.putString(BaiduASRDigitalDialog.PARAM_API_KEY, API_KEY);
				params.putString(BaiduASRDigitalDialog.PARAM_SECRET_KEY,
						SECRET_KEY);
				// 璁剧疆璇煶璇嗗埆瀵硅瘽妗嗕负钃濊壊楂樹寒涓婚
				params.putInt(BaiduASRDigitalDialog.PARAM_DIALOG_THEME,
						BaiduASRDigitalDialog.THEME_BLUE_LIGHTBG);
				// 瀹炰緥鍖栫櫨搴﹁闊宠瘑鍒璇濇
				mDialog = new BaiduASRDigitalDialog(HomeActivity1.this, params);
				// 璁剧疆鐧惧害璇煶璇嗗埆鍥炶皟鎺ュ彛
				mDialogListener = new DialogRecognitionListener() {

					@Override
					public void onResults(Bundle mResults) {
						ArrayList<String> rs = mResults != null ? mResults
								.getStringArrayList(RESULTS_RECOGNITION) : null;
						if (rs != null && rs.size() > 0) {
							recognition_result = rs.get(0);
							Intent it = new Intent();
							Bundle b = new Bundle();

							b.putString("flag", recognition_result);

							it.putExtras(b);
							it.setClass(HomeActivity1.this, CarnavdetailActivity.class);
							startActivity(it);

						}

					}

				};
				mDialog.setDialogRecognitionListener(mDialogListener);
			}
			// 璁剧疆璇煶璇嗗埆妯″紡涓鸿緭鍏ユā寮�
			mDialog.setSpeechMode(BaiduASRDigitalDialog.SPEECH_MODE_INPUT);
			// 绂佺敤璇箟璇嗗埆
			mDialog.getParams().putBoolean(
					BaiduASRDigitalDialog.PARAM_NLU_ENABLE, false);
			mDialog.show();

			break;

		default:
			break;
		}
	}
	
	
	// 数据的刷新
//		@Override
//		protected void onActivityResult(int requestCode, int resultCode,
//				Intent intent) {
//			super.onActivityResult(requestCode, resultCode, intent);
//
//			if (requestCode == ResultCode && resultCode == ResultCode) {
//				
//				Bundle bundle = intent.getExtras();
//				String name = bundle.getString("name");
//				xuanzhexiangmu.setText(name);
//			}
//
//		}
	
	
	// 回调
		public void onActivityResult(int requestCode, int resultCode, Intent intent) {
			super.onActivityResult(requestCode, resultCode, intent);

			if (requestCode == ResultCode && resultCode == ResultCode) {
				

				Bundle b = intent.getExtras();
				String  street = b.getString("street");
				String  village = b.getString("village");
				String  group = b.getString("group");
				xuanzhexiangmu.setText(street+village+group);

			}
		}

	@Override
	public boolean onTouch(final View v, MotionEvent event) {
		int action = event.getAction();
		Animation anim = null;
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			anim = AnimationUtils.loadAnimation(HomeActivity1.this, R.anim.scale_in);
			v.startAnimation(anim);
			myAnimationListener.setView(v);
			myAnimationListener.startAnimation();
			anim.setAnimationListener(myAnimationListener);
			break;
		case MotionEvent.ACTION_UP:
			myAnimationListener.stopAnimation();
			anim = AnimationUtils.loadAnimation(HomeActivity1.this, R.anim.scale_out);
			v.startAnimation(anim);
			anim.setAnimationListener(new MyAnimationListener() {
				@Override
				public void onAnimationEnd(Animation animation) {
					onClick(v);
				}
			});
			break;
		}
		return true;
	}

	private MyAnimationListener myAnimationListener = new MyAnimationListener();

	private class MyAnimationListener implements AnimationListener {
		private View v;
		private boolean isStop;

		private void setView(View v) {
			this.v = v;
		}

		private void stopAnimation() {
			isStop = true;
		}

		private void startAnimation() {
			isStop = false;
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			if (!isStop) {
				Animation anim = AnimationUtils.loadAnimation(HomeActivity1.this,
						R.anim.scale_in_static);
				v.startAnimation(anim);
				anim.setAnimationListener(this);
			}
		}

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
		}
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager mgr = (ConnectivityManager) 
				getApplicationContext().getSystemService(
						Context.CONNECTIVITY_SERVICE);
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
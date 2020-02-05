package com.parking.fragment;

import java.util.ArrayList;

import net.tsz.afinal.FinalBitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;



import com.king.zxing.CaptureActivity;
import com.parking.adapter.CarAdapter;

import com.parking.lib.MyAdGallery;
import com.parking.lib.MyAdGallery.MyOnItemClickListener;
import com.parking.model.City;
import com.parking.model.Parking;

import com.parking.smarthome.R;
import com.parking.ui.AlarmActivity;
import com.parking.ui.AppointActivity;
import com.parking.ui.CardetailActivity;
import com.parking.ui.CarnavActivity;
import com.parking.ui.CarnavdetailActivity;
import com.parking.ui.GPSNaviActivity;
import com.parking.ui.LoginActivity;
import com.parking.ui.OpenlockActivity;
import com.parking.ui.RechargeActivity;
import com.parking.ui.RegistActivity;
import com.parking.util.HttpClientUtil;
import com.parking.util.JsonUtil;
import com.parking.util.ProDialog;
import com.parking.util.UrlUtil;


@SuppressLint("NewApi")
public class HomeFragment extends Fragment implements
		OnTouchListener {

	Intent intent = null;
	Activity context;
	private RelativeLayout[] relativeLayouts = new RelativeLayout[6];
	private int[] ids = {R.id.main_money, R.id.main_parking, R.id.main_sevice,
			R.id.main_wash, R.id.yuyingnav, R.id.youhuihongdong};

	// 广告+++++++++++++++++++++++++++
	private MyAdGallery gallery; // 广告控件
	LinearLayout ovalLayout; // 圆点容器
	/** 图片id的数组,本地测试用 */
	private int[] imageId = new int[] { R.drawable.img01, R.drawable.img02,
			R.drawable.img03 };
	/** 图片网络路径数组 */
//	http://www.sksoon.com/images/1.png
//		http://www.sksoon.com/images/2.png

	private String[] mris = {
			"http://www.sksoon.com/linyang/test1.png",
			"http://www.sksoon.com/linyang/test2.png",
			"http://www.sksoon.com/linyang/test1.png"
	
	};

	private String uid;

	// 百度语音控件

	private BaiduASRDigitalDialog mDialog = null;
	private DialogRecognitionListener mDialogListener = null;
	// 应用授权信息 ，这里使用了官方SDK中的参数，如果需要，请自行申请，并修改为自己的授权信息
	private String API_KEY = "8MAxI5o7VjKSZOKeBzS4XtxO";
	private String SECRET_KEY = "Ge5GXVdGQpaxOmLzc8fOM8309ATCz9Ha";

	private String recognition_result = null;

	private SharedPreferences sp;
	private Editor editor;
	private String shouji;
	private JSONObject goodsList = null;
	private String editPwd;

	// 定位-------
	
	private Double geoLat, geoLng;
	// private int i,k;
	private ArrayList<Parking> datas = new ArrayList<Parking>();
	//停车场数据列表
	private ArrayList<City> datascity = new ArrayList<City>();
	
	private int j;
	private String booleanj;
	
	private EditText  find_car;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		context = getActivity();
		sp = context.getSharedPreferences("mrsoft", 0);
		Editor editor = sp.edit();
		editor.putString("onReply", "onReply");
		editor.commit();

		// 初始化广告
		adview();
		// 初始化其他组件
		init();

	}

	@Override
	public void onResume() {
		super.onResume();
		j = 0;
		booleanj = null;
		goodsList = null;
		sp = context.getSharedPreferences("mrsoft", 0);
		editor = sp.edit();
		editor.putString("onReply", "1");
		editor.commit();
	}

	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main, null);
	}

	private void init() {
		for (int i = 0; i < relativeLayouts.length; i++) {
			relativeLayouts[i] = (RelativeLayout) context.findViewById(ids[i]);
			relativeLayouts[i].setOnTouchListener(this);
		}

		
	}

	// 广告插件
	private void adview() {
		// 广告图片的声明++++++++++++++++++++++++++++++++++++++++++++++++++
		FinalBitmap.create(context); // android 框架 这里用于加载网络图片 ,
		gallery = (MyAdGallery) context.findViewById(R.id.adgallery); // 获取Gallery组件
		ovalLayout = (LinearLayout) context.findViewById(R.id.ovalLayout);// 获取圆点容器
		// 第二和第三参数 2选1 ,参数2为 图片网络路径数组 ,参数3为图片id的数组,本地测试用 ,2个参数都有优先采用 参数2
		gallery.start(context, mris, imageId, 3000, ovalLayout,
				R.drawable.dot_focused, R.drawable.dot_normal);

		TextView naba = (TextView) context.findViewById(R.id.nabatext);

		SharedPreferences sp = context.getSharedPreferences("mrsoft", 0); // 锟斤拷锟�Preferences
		shouji = sp.getString("shouji", "shouji");
		editPwd = sp.getString("editPwd", "editPwd");
		
//		if ("shouji".equals(shouji)) {
//			naba.setText("智慧运维功能即将上线！");
//		} else {
//			String shouji1 = shouji.substring(0, 3) + "****"
//					+ shouji.substring(7, shouji.length());
//			naba.setText("遨泊停车欢迎用户"+ shouji1 );
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
	
	
	

	/**
	 * 点击事件
	 * 
	 */
	private void onClick(View v) {
		switch (v.getId()) {
		//运维
				case R.id.main_parking:
//					intent = new Intent(context, CarnavActivity.class);//
//					startActivity(intent);
					
					intent = new Intent(context, AlarmActivity.class);//
					startActivity(intent);
					
					
					break;
				case R.id.main_sevice:
					
			
				case R.id.youhuihongdong:

					

					break;
					
				// 安装
				case R.id.main_money:
					Intent it = new Intent();
					it.setClass(context, CaptureActivity.class);
					context.startActivity(it);
					
					break;
		     case R.id.yuyingnav:

			     if (mDialog == null) {
				if (mDialog != null) {
					mDialog.dismiss();
				}
				Bundle params = new Bundle();
				// 设置API_KEY, SECRET_KEY
				params.putString(BaiduASRDigitalDialog.PARAM_API_KEY, API_KEY);
				params.putString(BaiduASRDigitalDialog.PARAM_SECRET_KEY,
						SECRET_KEY);
				// 设置语音识别对话框为蓝色高亮主题
				params.putInt(BaiduASRDigitalDialog.PARAM_DIALOG_THEME,
						BaiduASRDigitalDialog.THEME_BLUE_LIGHTBG);
				// 实例化百度语音识别对话框
				mDialog = new BaiduASRDigitalDialog(context, params);
				// 设置百度语音识别回调接口
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
							it.setClass(context, CarnavdetailActivity.class);
							startActivity(it);

						}

					}

				};
				mDialog.setDialogRecognitionListener(mDialogListener);
			}
			// 设置语音识别模式为输入模式
			mDialog.setSpeechMode(BaiduASRDigitalDialog.SPEECH_MODE_INPUT);
			// 禁用语义识别
			mDialog.getParams().putBoolean(
					BaiduASRDigitalDialog.PARAM_NLU_ENABLE, false);
			mDialog.show();

			break;

		default:
			break;
		}
	}

	@Override
	public boolean onTouch(final View v, MotionEvent event) {
		int action = event.getAction();
		Animation anim = null;
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			anim = AnimationUtils.loadAnimation(context, R.anim.scale_in);
			v.startAnimation(anim);
			myAnimationListener.setView(v);
			myAnimationListener.startAnimation();
			anim.setAnimationListener(myAnimationListener);
			break;
		case MotionEvent.ACTION_UP:
			myAnimationListener.stopAnimation();
			anim = AnimationUtils.loadAnimation(context, R.anim.scale_out);
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
				Animation anim = AnimationUtils.loadAnimation(context,
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
		ConnectivityManager mgr = (ConnectivityManager) context
				.getApplicationContext().getSystemService(
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
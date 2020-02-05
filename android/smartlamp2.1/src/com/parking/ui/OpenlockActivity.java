package com.parking.ui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;





import com.parking.smarthome.R;
import com.parking.util.ActivityBase;
import com.parking.util.AppConstants;
import com.parking.util.HttpClientUtil;
import com.parking.util.IPhoneDialog1;
import com.parking.util.JsonUtil;
import com.parking.util.ProDialog;
import com.parking.util.UrlUtil;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.WindowManager.LayoutParams;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class OpenlockActivity extends ActivityBase implements OnClickListener {

	private ImageView open_img;
	private SharedPreferences sp;
	private Editor editor;

	private RelativeLayout top_fanhui;
	private JSONObject goodsList = null;
	private  String oderid;
	private String cheweiid;
	private TextView open_tishi,open_tishi1;
	
	Handler d = new Handler() {

		public void handleMessage(Message msg) {
			try {
				if (msg.what == 90) {
					// ���½���

					String message = goodsList.getString("success");
					if (message != null && message.equals("true")) {
					//	{"message":"��λ�ѳɹ�������������ָ����λͣ��","returnCode":"0","entity":{"id":23,"value":"6�ų�λ"},"success":true}
						showCustomToast("�����ɹ�");
						open_img.setBackgroundResource(R.drawable.lock_close);
						open_img.setEnabled(false);
						open_tishi.setVisibility(View.GONE);
						open_tishi1.setText("�����ɹ�");
					
//						sp = getSharedPreferences("mrsoft", 0);
//						editor = sp.edit();
//						editor.remove("activityflagopen");
//						editor.commit();
						startActivity(new Intent(OpenlockActivity.this,
						HomeActivity.class));
				}else {
					showCustomToast("�������ݳ��ִ���");
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
		setContentView(R.layout.openlock);

		init();
		Listener();
		save();

	}

	/**
	 * ��ʼ������
	 */
	private void init() {
	
		TextView toptitile = (TextView) findViewById(R.id.toptitile);
		
		open_tishi = (TextView) findViewById(R.id.open_tishi);
		open_tishi1 = (TextView) findViewById(R.id.open_tishi1);
		
		toptitile.setText("����");
		top_fanhui = (RelativeLayout) findViewById(R.id.top_fanhui);
		top_fanhui.setVisibility(View.VISIBLE);
		open_img=(ImageView) findViewById(R.id.open_img);
//		PushManager.sendRequest(OpenlockActivity.this,"http://123.57.253.69:8098/parking/app/place" + "&\n");
		open_img.setBackgroundResource(R.drawable.lock_open);
		sp = getSharedPreferences("mrsoft", MODE_PRIVATE); // ���Preferences

		oderid= sp.getString("oderid", "oderid");
		cheweiid=sp.getString("goodsId","goodsId");
		
	} 
        
        
        
        //editor.putString("cheid",cheid);
	// ������������
		private void getGoodsList() {
//			final ProDialog myDialog = new ProDialog(OpenlockActivity.this,
//					R.style.progressDialog);
//			myDialog.show();

			new Thread(new Runnable() {

				@Override
				public void run() {

					try {    
						String url = UrlUtil.getUserUrl() + "app/place";
						
//						List<NameValuePair> params = new ArrayList<NameValuePair>();

						LinkedList<BasicNameValuePair>	params = new LinkedList<BasicNameValuePair>();  
						params.add(new BasicNameValuePair("goodsId",cheweiid));
						params.add(new BasicNameValuePair("sn",oderid));
						String ruslt = HttpClientUtil.post(OpenlockActivity.this,url,params);
						if (!(ruslt == null)) {
							goodsList = new JSONObject(ruslt);
						}
//						myDialog.dismiss();

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
	
	
	
	/**
	 * ����
	 */
	private void Listener() {

		top_fanhui.setOnClickListener(this);
		open_img.setOnClickListener(this);

	}

	/**
	 * ���ݵı���
	 */

	private void save() {
//		sp = getSharedPreferences("mrsoft", MODE_PRIVATE);
//		String username = sp.getString("shouji", "shouji");

		// int userid=132;
		//
		// String url=UrlUtil.getUserUrl(userid);

		// VolleyRequest.requestGet(url, AppConstants.USERINFO_REQUEST_TAG, new
		// VolleyInterface(this, VolleyInterface.mListener
		// ,VolleyInterface.mErrorListener) {
		// @Override
		// public void success(JSONObject result) {
		// // user = new Gson().fromJson(result.toString(), User.class);
		// // updateuserInfo();
		// }
		//
		// @Override
		// public void error(VolleyError volleyError) {
		//
		// }
		// });

	}

	@Override
	public void onClick(View v) {
		if (v == open_img) {
			if (isNetworkAvailable()) {
				getGoodsList();
			} else {
				showCustomToast("��ǰ���粻���ã������������������!");
			}
		}
		if (v == top_fanhui) {
			finish();
		}

	}

}

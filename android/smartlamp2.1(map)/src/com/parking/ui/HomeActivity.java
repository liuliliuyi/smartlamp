package com.parking.ui;


import java.util.LinkedList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;




import com.parking.fragment.DiandanFragment;
import com.parking.fragment.HomeFragment;
import com.parking.fragment.MycentreFragment;


import com.parking.smarthome.R;
import com.parking.util.ActivityBase;
import com.parking.util.HttpClientUtil;
import com.parking.util.ProDialog;
import com.parking.util.UpdateManager;
import com.parking.util.UrlUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeActivity extends Activity implements OnClickListener {
	// ��ҳ ��ǩ���
	private static final int BAR_HOME_INDEX = 0;
	// ������ǩ���
	private static final int BAR_ODER_INDEX = 1;
	// �ҵı�ǩ���
	private static final int BAR_MY_INDEX = 2;

	// ��ҳ ��ǩ
	LinearLayout homeLayout;
	// ���� ��ǩ
	LinearLayout dingdanLayout;
	// �ҵı�ǩ
	LinearLayout mycentreLayout;

	// ��ҳ ��ǩ ͼ��
	ImageView homeIcon;
	// ���� ��ǩ ͼ��
	ImageView dingdanIcon;
	// �ҵ� ��ǩ ͼ��
	ImageView mycentreIcon;

	// ��ҳ ��ǩ ����
	TextView homeText;
	// ����  ��ǩ ����
	TextView dingdanText;
	// �ҵ�  ��ǩ ����
	TextView mycentreText;
	// ��ҳ ��ǩҳ
	Fragment homeFragment;
	// ���� ��ǩҳ
	Fragment dingdanFragment;
	// �ҵ� ��ǩҳ
	Fragment mycentreFragment;

	Fragment individualFragment;
	FragmentManager fm;
	FragmentTransaction ft;
	int curTabIndex = -1;

	private SharedPreferences sp;
	private Editor editor;
	
	private int i;
	private final int ResultCode = 54139;
	
	JSONObject goodsList = null;
	
	Handler d = new Handler() {

		public void handleMessage(Message msg) {
			try {
				if (msg.what == 90) {
					// 更新界面

					String status = goodsList.getString("code");
					
					if ("0000".equals(status)) {
						JSONObject data = goodsList.getJSONObject("data");
						String version = data.getString("version");
						String url1 = data.getString("downUrl");

						UpdateManager manager = new UpdateManager(
								HomeActivity.this, version, url1);
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
		setContentView(R.layout.main_bottom);

	    i=0;
		initView();
		setCurTab(BAR_HOME_INDEX);
	
		getGoodsList();
	
	
	}
	
	private void getGoodsList() {
		
		new Thread(new Runnable() {

			@Override
			public void run() {
                    
				try {
					String url = UrlUtil.getUserUrl() + "admin/api/getAppInfo?type=apk";		
					List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();  
//					params.add(new BasicNameValuePair("username",editUid));
//					params.add(new BasicNameValuePair("password",editPwd));
//					params.add(new BasicNameValuePair("mobileLogin","true"));
					
					String ruslt = HttpClientUtil.post(HomeActivity.this,url, params);
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
	
	
	
	

	
	@SuppressLint("NewApi")
	public void initView() {
		homeLayout = (LinearLayout) findViewById(R.id.home_bar_layout);
		homeLayout.setOnClickListener(this);
		dingdanLayout = (LinearLayout) findViewById(R.id.dingdan_bar_layout);
		
		//dingdanLayout.setVisibility(View.GONE);
		dingdanLayout.setOnClickListener(this);
		mycentreLayout = (LinearLayout) findViewById(R.id.my_bar_layout);
		mycentreLayout.setOnClickListener(this);

		homeIcon = (ImageView) findViewById(R.id.home_icon);
		dingdanIcon = (ImageView) findViewById(R.id.dingdan_icon);
		mycentreIcon = (ImageView) findViewById(R.id.mycentre_icon);

		homeText = (TextView) findViewById(R.id.home_text);
		dingdanText = (TextView) findViewById(R.id.dingdan_text);
		mycentreText = (TextView) findViewById(R.id.mycentre_text);

		fm = getFragmentManager();
		homeFragment = new HomeFragment();
		dingdanFragment = new DiandanFragment();
		mycentreFragment = new MycentreFragment();
		//individualFragment = new IndividualFragment();
	}
	
	
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode,
//			Intent intent) {
//		super.onActivityResult(requestCode, resultCode, intent);
//
//		if (requestCode == ResultCode && resultCode == ResultCode) {
//		
//		i++;
//		}
//		}

	/**
	 * �趨��ǰ��ǩҳ
	 * 
	 * @return void
	 * @param tab
	 */
	@SuppressLint("NewApi")
	public void setCurTab(int tab) {
		switch (tab) {
		case BAR_HOME_INDEX:
			if (BAR_HOME_INDEX != curTabIndex) {
				curTabIndex = BAR_HOME_INDEX;
				clearTabState();
				homeIcon.setImageResource(R.drawable.shouyeax);
				homeText.setTextColor(getResources().getColor(
						R.color.icon_bar_selected_text_color));

				ft = fm.beginTransaction();
				ft.replace(R.id.content,homeFragment);
				ft.commit();
			}
			break;
		case BAR_ODER_INDEX:
			if (BAR_ODER_INDEX != curTabIndex) {
				curTabIndex = BAR_ODER_INDEX;
				clearTabState();
				dingdanIcon.setImageResource(R.drawable.baibaoxiangaxbottom);
				dingdanText.setTextColor(getResources().getColor(
						R.color.icon_bar_selected_text_color));

				ft = fm.beginTransaction();
				ft.replace(R.id.content,dingdanFragment);
				ft.commit();
			}
			break;
		case BAR_MY_INDEX:
			if (BAR_MY_INDEX != curTabIndex) {
				curTabIndex = BAR_MY_INDEX;
				clearTabState();
				mycentreIcon.setImageResource(R.drawable.yonghuax);
				mycentreText.setTextColor(getResources().getColor(
						R.color.icon_bar_selected_text_color));

				ft = fm.beginTransaction();
				ft.replace(R.id.content,mycentreFragment);
				ft.commit();
			}
			break;

		}
	}

	/**
	 * �����ǩ��ѡ��״̬
	 * 
	 * @return void
	 * @param
	 */
	public void clearTabState() {
		int textColor = getResources().getColor(R.color.icon_bar_text_color);

		homeIcon.setImageResource(R.drawable.shouye);
		homeText.setTextColor(textColor);
		dingdanIcon.setImageResource(R.drawable.baibaoxiangbottom);
		dingdanText.setTextColor(textColor);
		mycentreIcon.setImageResource(R.drawable.yonghu);
		mycentreText.setTextColor(textColor);

	}

	
	@Override
	public void onClick(View v) {
		if (v == homeLayout) {
			setCurTab(BAR_HOME_INDEX);
		} else if (v == dingdanLayout) {
			setCurTab(BAR_ODER_INDEX);
		} else if (v == mycentreLayout) {
			setCurTab(BAR_MY_INDEX);
		}
	}

}

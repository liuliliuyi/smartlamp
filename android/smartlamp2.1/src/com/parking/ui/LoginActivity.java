package com.parking.ui;




import static com.example.jpushdemo.TagAliasOperatorHelper.ACTION_SET;
import static com.example.jpushdemo.TagAliasOperatorHelper.sequence;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;





import com.example.jpushdemo.TagAliasOperatorHelper;
import com.example.jpushdemo.TagAliasOperatorHelper.TagAliasBean;
import com.parking.smarthome.R;
import com.parking.util.ActivityBase;
import com.parking.util.AppConstants;
import com.parking.util.HttpClientUtil;
import com.parking.util.ProDialog;
import com.parking.util.UrlUtil;



import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends ActivityBase implements
		OnClickListener {

	private EditText uid, pwd,zufuhao;
	private static String editUid;
	private static String editPwd;
	private Button login;

	private SharedPreferences sp;
	private Editor editor;
	private TextView reg_zhuche;
	// private LoadUtil loadUtil;
	private String flag;
	private RelativeLayout top_fanhui;
	JSONObject goodsList = null;
	private int i;

	private TextView wangjimima;
	private ProDialog myDialog;
	private String carPlate, balance;
	public static boolean isForeground = false;
	private String ruslt;
	private String zuid;
	Handler d = new Handler() {

		public void handleMessage(Message msg) {
			try {
				if (msg.what == 91) {
					// 更新界面
//  {"success":true,"errorCode":"-1","msg":"登录成功!","body":{"username":"13627233474","name":"立大侠","office":{"id":"4","remarks":null,"createDate":null,"updateDate":null,"name":"北京市项目","sort":30,"parentIds":"0,1,","area":null,"code":null,"type":"2","grade":null,"equipmentNum":null,"totalPower":null,"master":null,"phone":null,"email":null,"companyName":null,"useable":null,"primaryPerson":null,"deputyPerson":null,"childDeptList":null,"parentId":"1"},"mobileLogin":true,"JSESSIONID":"288fda39684f4ee7802b24dc610087d5"}}
					String message = goodsList.getString("success");
                    
					if (message != null && message.equals("true")) {
						
						
						
						
						
//						sp = getSharedPreferences("mrsoft", MODE_PRIVATE);					
//						editor = sp.edit();
//						editor.putString("userName", body.getString("username"));
//						
//						editor.putString("JSESSIONID", body.getString("JSESSIONID"));
//						editor.commit();

						
					
						 String alias = null;
					     int action = -1;
					     boolean isAliasAction = false;
						 alias=editUid;
						 isAliasAction = true;
			             action = ACTION_SET;
			           
			             TagAliasBean tagAliasBean = new TagAliasBean();
			             tagAliasBean.action = action;
			             sequence++;
			             if(isAliasAction){
			                 tagAliasBean.alias = alias;
			             }

			             tagAliasBean.isAliasAction = isAliasAction;
			             TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(),sequence,tagAliasBean);
						
			             
			                sp = getSharedPreferences("mrsoft", MODE_PRIVATE);
							editor = sp.edit();
							editor.putString("shouji",editUid);
							editor.putString("editPwd",editPwd);
							editor.putString("numberid",zuid);
							editor.commit();
//							
//
//							String id = sp.getString("id", "id");
//							String id1 = sp.getString("id1", "id1");
//							if("id".equals(id)&&"id1".equals(id1)){
//								
//								Intent it = new Intent();
//							    it.setClass(LoginActivity.this,
//									OrdersActivity.class);
//							    startActivity(it);
//							    
//							}else{
//								
								Intent it = new Intent();
							    it.setClass(LoginActivity.this,
							    		HomeActivity1.class);
							    startActivity(it);
//								
//							}
			            
							

	
					} else {
						showCustomToast("用户名和密码错误");
					}

				}
				
				
				
				
				if (msg.what == 90) {
					// 更新界面
					    if(ruslt.contains("后台系统出问题了")){
					    	showCustomToast("后台系统出问题了");
					    }else{
					    	
					    	if(ruslt.contains("error_description")){
					    		if (!(ruslt == null)) {
					    			
									goodsList = new JSONObject(ruslt);
									
								    }
					    		
					    	//	{"error":"invalid_grant","error_description":"Bad credentials"}
							  if("Bad credentials".equals(goodsList.getString("error_description"))){
							    	showCustomToast("用户名和密码错误");
							  }else{
							    	String error_description = goodsList.getString("error_description");
						    		showCustomToast(error_description);
							  }
					    		
						    
					    	}else{
						    	
						    	if (!(ruslt == null)) {
									goodsList = new JSONObject(ruslt);
								    }
							    	String access_token = goodsList.getString("access_token");
					                sp = getSharedPreferences("mrsoft", MODE_PRIVATE);
									editor = sp.edit();
									editor.putString("token", access_token);
									editor.commit();
								    getUserinfo(access_token);		
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
		setContentView(R.layout.login);
		
		
		i = 0;
		flag = "0";
		init();
		Listener();
		save();
	}

	

	/**
	 * 初始化数据
	 */
	private void init() {
		reg_zhuche = (TextView) findViewById(R.id.reg_zhuche);
		uid = (EditText) findViewById(R.id.nicheng);
		pwd = (EditText) findViewById(R.id.zhuche_mima);
		login = (Button) findViewById(R.id.login);
		TextView toptitile = (TextView) findViewById(R.id.toptitile);
		toptitile.setText("登录");
		top_fanhui = (RelativeLayout) findViewById(R.id.top_fanhui);
		top_fanhui.setVisibility(View.VISIBLE);
		wangjimima = (TextView) findViewById(R.id.wangjimima);
		
		zufuhao=  (EditText) findViewById(R.id.zufuhao);

	}

	
	/**
	 * 监听
	 */
	private void Listener() {
		reg_zhuche.setOnClickListener(this);
		login.setOnClickListener(this);
		top_fanhui.setOnClickListener(this);
		wangjimima.setOnClickListener(this);
	}

	/**
	 * 数据的保存
	 */

	@SuppressWarnings("unchecked")
	private void save() {
		sp = getSharedPreferences("mrsoft", MODE_PRIVATE);
		String username = sp.getString("shouji", "shouji");
		String password = sp.getString("editPwd", "editPwd");
		String numberid=sp.getString("numberid", "numberid");
		
		

		if ("shouji".equals(username)) {
			uid.setText("");
		} else {
			uid.setText(username);
		}
		if ("editPwd".equals(password)) {
			pwd.setText("");
		} else {
			
			pwd.setText(password);
		}
		
		
		if ("numberid".equals(numberid)) {
			zufuhao.setText("");
		} else {
			zufuhao.setText(numberid);
		}

		
	
	
	}

	@Override
	public void onClick(View v) {
		if (v == login) {
			editUid = uid.getText().toString();
			editPwd = pwd.getText().toString();
			zuid=zufuhao.getText().toString();
			if (editUid.trim().equals("")) {

				showCustomToast("用户名不能为空");
				return;
			}

			if (editPwd.trim().equals("")) {

				showCustomToast("密码不能为空");
				return;
			}
			
			if (zuid.trim().equals("")) {

				showCustomToast("租户号不能为空");
				return;
			}
//			String regExp = "^[1]([3|4|5|7|8][0-9]{1})[0-9]{8}$";
//			Pattern p = Pattern.compile(regExp);
//			Matcher m = p.matcher(editUid);
//			if (m.find() == true) {
				

				
//				String url = UrlUtil.getUserUrl();
				if (isNetworkAvailable()) {
					getGoodsList();
				} else {
					showCustomToast("当前网络不可用，请连接网络后在重试!");
				}

//			} else {
//
//				showCustomToast("请输入正确的手机号码");
//				return;
//
//			}

		}
		if (v == top_fanhui) {
//			/**
//			 * 防止点击系统返回键，断开连接,急用急删
//			 */
//			sp = getSharedPreferences("mrsoft", 0);
//			String logout = sp.getString("logout", "logout");
//			if ("1".equals(logout)) {
//				CIMPushManager.destroy(this);
//				editor = sp.edit();
//				editor.remove("logout");
//				editor.commit();
//			}

			finish();
		}
		if (v == wangjimima) {

//			sp = getSharedPreferences("mrsoft", 0);
//			String uid1 = sp.getString("shouji", "shouji");
//			if ("shouji".equals(uid1)) {
//				showCustomToast("请您输入手机号");
//			} else {
//			if (uid.getText().toString().trim().equals("")) {
//
//				showCustomToast("请先输入手机号");
//				return;
//			}
			
//			     sp = getSharedPreferences("mrsoft", MODE_PRIVATE);
//				 editor = sp.edit();
//				 editor.putString("shouji",uid.getText().toString());
//				 editor.commit();
				
				Intent it = new Intent();
				it.setClass(LoginActivity.this, WangjiActivity.class);
				startActivity(it);
			

		}
		if (v == reg_zhuche) {

			Intent it = new Intent();
			it.setClass(LoginActivity.this, RegistActivity.class);
			startActivity(it);
			// LoginActivity.this.finish();

		}

	}

	
	private void getGoodsList() {
		myDialog = new ProDialog(LoginActivity.this, R.style.progressDialog);
		myDialog.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					
//					editUid = uid.getText().toString();
//					editPwd = pwd.getText().toString();
					
					String url = UrlUtil.getUserUrl() + "blade-auth/oauth/token";		
					List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();  
					params.add(new BasicNameValuePair("username",editUid));
					params.add(new BasicNameValuePair("password",editPwd));
					params.add(new BasicNameValuePair("scope","all"));
					params.add(new BasicNameValuePair("grant_type","password"));
//					
					
				
					ruslt = HttpClientUtil.posttoken(LoginActivity.this,url,zuid, params);
//					if (!(ruslt == null)) {
//						goodsList = new JSONObject(ruslt);
//					}
					myDialog.dismiss();

				} catch (Exception e) {
					e.printStackTrace();
				}
				if (!(myDialog == null)) {
					myDialog.dismiss();
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
	
	
	
	private void getUserinfo(final String token) {
		myDialog = new ProDialog(LoginActivity.this, R.style.progressDialog);
		myDialog.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					
					String url = UrlUtil.getUserUrl() + "blade-auth/oauth/user-info";		
//					List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();  
//					params.add(new BasicNameValuePair("username","admin"));
//					params.add(new BasicNameValuePair("password","admin"));
//					params.add(new BasicNameValuePair("scope","all"));
//					params.add(new BasicNameValuePair("grant_type","password"));
					String ruslt = HttpClientUtil.postUser(LoginActivity.this,url,token );
				
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
					msg.what = 91;
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
	public void onBackPressed() {

//		/**
//		 * 防止点击系统返回键，断开连接,急用急删
//		 */
//		sp = getSharedPreferences("mrsoft", 0);
//		String logout = sp.getString("logout", "logout");
//		if ("1".equals(logout)) {
//			CIMPushManager.destroy(this);
//			editor = sp.edit();
//			editor.remove("logout");
//			editor.remove("logout");
//			editor.commit();
//		}

		finish();
	}
	
	
	
	//推送的相关申明
		// 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
//			private void init(){
//				 JPushInterface.init(getApplicationContext());
//			}


			@Override
			protected void onResume() {
				isForeground = true;
				super.onResume();
			}


			@Override
			protected void onPause() {
				isForeground = false;
				super.onPause();
			}


			@Override
			protected void onDestroy() {
				LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
				super.onDestroy();
			}
			

			//for receive customer msg from jpush server
			private MessageReceiver mMessageReceiver;
			public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
			public static final String KEY_TITLE = "title";
			public static final String KEY_MESSAGE = "message";
			public static final String KEY_EXTRAS = "extras";
			
			public void registerMessageReceiver() {
				mMessageReceiver = new MessageReceiver();
				IntentFilter filter = new IntentFilter();
				filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
				filter.addAction(MESSAGE_RECEIVED_ACTION);
				LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
			}

			public class MessageReceiver extends BroadcastReceiver {

				@Override
				public void onReceive(Context context, Intent intent) {
					try {
						if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
							String messge = intent.getStringExtra(KEY_MESSAGE);
							String extras = intent.getStringExtra(KEY_EXTRAS);
							StringBuilder showMsg = new StringBuilder();
							showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
//							if (!ExampleUtil.isEmpty(extras)) {
//								showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
//							}
//							setCostomMsg(showMsg.toString());
						}
					} catch (Exception e){
					}
				}
			}
			
//			private void setCostomMsg(String msg){
//				 if (null != msgText) {
//					 msgText.setText(msg);
//					 msgText.setVisibility(android.view.View.VISIBLE);
//		         }
//			}


}

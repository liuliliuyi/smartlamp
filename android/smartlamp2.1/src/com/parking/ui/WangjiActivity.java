package com.parking.ui;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import com.parking.smarthome.R;

import com.parking.util.ActivityBase;

import com.parking.util.HttpClientUtil;
import com.parking.util.ProDialog;
import com.parking.util.UrlUtil;


import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WangjiActivity extends ActivityBase implements OnClickListener {

	private EditText   possword, possword1, listence;
	private EditText uid;
	private static String editUid;
	
	private static String passwd;
	private static String passwd1;
	private static String listence_car;

	private Button login;

	private SharedPreferences sp;
	private Editor editor;

	private String flag;

	private RelativeLayout top_fanhui;

	// ��֤��
	private TextView zhuche_yzm;
	private int recLen;
	private Timer mTimer;
	private TimerTask mTimerTask;

	JSONObject goodsList = null;
	ProDialog myDialog = null;
	private String chongfu;
	private int i;
	private String username;
	
	
	private EditText zhuche_yanzhengma;
	private String yanzhengflag,yanzhengflag1;
	
	Handler d = new Handler() {

		public void handleMessage(Message msg) {
			try {
				if (msg.what == 90) {
					// ���½���

					String message = goodsList.getString("success");
					if (message != null && message.equals("true")) {
						showCustomToast("�޸ĳɹ�");
						sp = getSharedPreferences("mrsoft", MODE_PRIVATE);
						editor = sp.edit();
						editor.putString("shouji",username);
						editor.putString("editPwd",passwd1);
						editor.commit();
						
						editor.commit();
						startActivity(new Intent(WangjiActivity.this,LoginActivity.class));

					}

				}
				if (msg.what == 92) {
					// ���½���
					if (chongfu != null && chongfu.equals("true")) {

						showCustomToast("���ֻ���δ��ע��");
						uid.setText("");

					}
				}
				if (msg.what == 93) {
					String message = goodsList.getString("success");
					// {"message":"������֤��ɹ���","returnCode":"0","entity":null,"success":true}
					if (message != null && message.equals("true")) {
						yanzhengflag1 = "1";
					}
				}
				if (msg.what == 94) {
					String message = goodsList.getString("success");
					if (message != null && message.equals("true")) {
						yanzhengflag = "1";
						showCustomToast("��֤����ȷ");
					}else{
						showCustomToast("��֤�����");
						clearTimer();
						// ������֤---
						zhuche_yzm.setText("���·�����֤��");
						zhuche_yzm.setClickable(true);
					}
					
				}
			
			
			
			} catch (Exception e) {

				e.printStackTrace();
			}
		};

	};

	final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				//zhuche_yzm.setText(recLen + "��");
				zhuche_yzm.setClickable(false);
				zhuche_yzm.setText(recLen+"s");
				if (recLen == 0) {
					recLen = 120;
					clearTimer();
					zhuche_yzm.setText("���»�ȡ��֤��");
					zhuche_yzm.setClickable(true);
				}
			}

		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.wangjimima);
		
		recLen = 120;
		i=0;
		yanzhengflag1 = "0";
		init();
		Listener();
		// save();

	}

	// ��ʼ��ʱ�����
	private void initTimer() {
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
		mTimer.schedule(mTimerTask, 0, 1000);
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

	/**
	 * ��ʼ������
	 */
	private void init() {
		yanzhengflag="0";
		uid = (EditText) findViewById(R.id.wangji_shouji);
		zhuche_yanzhengma=(EditText) findViewById(R.id.zhuche_mima);

		possword = (EditText) findViewById(R.id.wjregist_mima);
		possword1 = (EditText) findViewById(R.id.wjregist_mima1);

		TextView toptitile = (TextView) findViewById(R.id.toptitile);
		toptitile.setText("��������");
		login = (Button) findViewById(R.id.wjregist_login);

//		sp = getSharedPreferences("mrsoft", MODE_PRIVATE);
//		username = sp.getString("shouji", "shouji");
//		String password1 = sp.getString("editPwd", "editPwd");
//		possword.setText(password1);
//		uid.setText(username);
		
		
		top_fanhui = (RelativeLayout) findViewById(R.id.top_fanhui);
		top_fanhui.setVisibility(View.VISIBLE);

		uid.addTextChangedListener(new TextWatcher() {
			private CharSequence temp;
			private int selectionStart;
			private int selectionEnd;

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				temp = s;
				System.out.println("s=" + s);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				// int number = s.length();

				selectionStart = uid.getSelectionStart();
				selectionEnd = uid.getSelectionEnd();

				if (temp.length() > 0) {
					s.delete(selectionStart, selectionEnd);
					int tempSelection = selectionStart;
					uid.setSelection(tempSelection);// ���ù�������
					if (uid.getText().toString().length() == 11) {
						flag = "2";
						if (isNetworkAvailable()) {
							getGoodsList();
						} else {
							showCustomToast("��ǰ���粻���ã������������������!");
						}
					}

					if (temp.length() >= 12) {
						showCustomToast("�ֻ��Ų���ȷ,����11λ����");
						uid.setSelection(tempSelection);// ���ù�������
					}
				}
			}
		});
		
		
		
		zhuche_yzm = (TextView) findViewById(R.id.zhuche_yzm);
		//��֤����֤
				zhuche_yanzhengma.addTextChangedListener(new TextWatcher() {
					private CharSequence temp1;
					private int selectionStart1;
					private int selectionEnd1;

					@Override
					public void onTextChanged(CharSequence s, int start, int before,
							int count) {
						temp1 = s;
						System.out.println("s=" + s);
					}

					@Override
					public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {
					}

					@Override
					public void afterTextChanged(Editable s1) {

						selectionStart1 = zhuche_yanzhengma.getSelectionStart();
						selectionEnd1 = zhuche_yanzhengma.getSelectionEnd();
						if (temp1.length() > 0) {
							s1.delete(selectionStart1, selectionEnd1);
							int tempSelection1 = selectionStart1;
							zhuche_yanzhengma.setSelection(tempSelection1);// ���ù�������
							if (zhuche_yanzhengma.getText().toString().length() == 6) {
								flag = "4";
								
								if (isNetworkAvailable()) {
									getGoodsList();
								} else {
									showCustomToast("��ǰ���粻���ã������������������!");
								}
							}
							if (temp1.length() >= 7) {

								showCustomToast("��֤�벻��ȷ,���ܹ�����4������");
								zhuche_yanzhengma.setSelection(tempSelection1);// ���ù�������
								// listence.setText(listence.getText().toString().substring(0,6));
							}
						}
					}
				});

	}

	/**
	 * ����
	 */
	private void Listener() {
		// reg_zhuche.setOnClickListener(this);
		login.setOnClickListener(this);
		top_fanhui.setOnClickListener(this);
		zhuche_yzm.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == zhuche_yzm) {
			//initTimer();
			editUid = uid.getText().toString();
			
			if (editUid.trim().equals("")) {

				showCustomToast("�ֻ��Ų���Ϊ��");
				return;
			}
			String regExp = "^[1]([3|4|5|7|8][0-9]{1})[0-9]{8}$";
			Pattern p = Pattern.compile(regExp);
			Matcher m = p.matcher(editUid);
			if (!(m.find() == true)) {
				showCustomToast("��������ȷ���ֻ�����");
				return;
			}
			
			
			flag = "3";
			
			if (isNetworkAvailable()) {
				getGoodsList();
			} else {
				showCustomToast("��ǰ���粻���ã������������������!");
			}
			//��ʼ��ʱ�����
			recLen = 120;
			initTimer();
		}

		if (v == login) {
			passwd = possword.getText().toString();
			passwd1 = possword1.getText().toString();
			
			if (passwd.trim().equals("")) {

				showCustomToast("���벻��Ϊ��");
				return;
			}
			if (passwd1.trim().equals("")) {

				showCustomToast("�ٴ��������벻��Ϊ��");
				return;
			}
			if ("0".endsWith(yanzhengflag1)) {
				showCustomToast("������֤��ʧ��");
				return;
			}
			if("0".endsWith(yanzhengflag)){
				showCustomToast("��֤�벻��ȷ");
				return;
			}
			flag = "0";
			if (isNetworkAvailable()) {
				getGoodsList();

			} else {

				Toast.makeText(WangjiActivity.this, "���������������磬�ٴβ���",
						Toast.LENGTH_SHORT).show();

			}

		}
		if (v == top_fanhui) {
			finish();
		}

	}

	private void getGoodsList() {

		if ("0".equals(flag)) {
			myDialog = new ProDialog(WangjiActivity.this,
					R.style.progressDialog);
			myDialog.show();
		}
		new Thread(new Runnable() {

			@Override
			public void run() {

				if ("0".equals(flag)) {
					try {
						String url = UrlUtil.getUserUrl() + "app/password";		
						List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();  
						params.add(new BasicNameValuePair("currentPassword",passwd));
						params.add(new BasicNameValuePair("password",passwd1));
						String ruslt = HttpClientUtil.post(WangjiActivity.this,url, params);
						if (!(ruslt == null)) {
							goodsList = new JSONObject(ruslt);
						}
						myDialog.dismiss();
					} catch (Exception e) {
						e.printStackTrace();
					}

				
				} else{
					
					try {
						String url = null;
						if ("2".equals(flag)) {
							url = UrlUtil.getUserUrl() + "app/username/"
									+ uid.getText().toString();

							chongfu = HttpClientUtil.doGet(url);
						}
						
						if ("3".equals(flag)) {
						url = UrlUtil.getUserUrl() + "app/sendcode?mobile="+ editUid + "&type=1";
						goodsList = new JSONObject(HttpClientUtil.doGet(url));
						}
						if ("4".equals(flag)) {
						url = UrlUtil.getUserUrl() + "app/checkcode?mobile="+ editUid+"&code="+zhuche_yanzhengma.getText().toString();
						goodsList = new JSONObject(HttpClientUtil.doGet(url));
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				
				
				}
				try {
					Message msg = new Message();
					if ("0".equals(flag)) {
						msg.what = 90;
					}
					if ("3".equals(flag)) {
						msg.what = 93;
					}
					if ("4".equals(flag)) {
						msg.what = 94;
					}
					if ("2".equals(flag)) {
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			// clearTimer();
		}
		return super.onKeyDown(keyCode, event);
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

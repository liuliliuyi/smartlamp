package com.parking.ui;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;


import com.parking.smarthome.R;

import com.parking.util.ActivityBase;
import com.parking.util.HttpClientUtil;
import com.parking.util.ProDialog;
import com.parking.util.UrlUtil;

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
import android.telephony.TelephonyManager;
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

public class RegistActivity extends ActivityBase implements OnClickListener {

	private EditText uid, pwd, possword, possword1, listence;
	private static String editUid;

	private static String passwd;
	private static String passwd1;
	private static String listence_car;

	private Button login;

	private SharedPreferences sp;
	private Editor editor;

	private String flag;

	private RelativeLayout top_fanhui;

	private int recLen;
	private Timer mTimer;
	private TimerTask mTimerTask;

	JSONObject goodsList = null;
	ProDialog myDialog = null;
	private String chongfu, chongfu1;
	private int i;

	// private String carPlate, balance;
	private String yanzhengflag, yanzhengflag1;
	private TextView zhuche_yzm;
	private EditText zhuche_yanzhengma;
	final Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (recLen > 0) {
					zhuche_yzm.setClickable(false);
					zhuche_yzm.setText(recLen + "s");
				} else {
					clearTimer();
					// 重新验证---
					zhuche_yzm.setText("重新发送验证码");
					zhuche_yzm.setClickable(true);
				}
			}
		}
	};

	Handler d = new Handler() {

		public void handleMessage(Message msg) {
			try {
				if (msg.what == 90) {
					// 更新界面

					String message = goodsList.getString("success");

					if (message != null && message.equals("true")) {

						sp = getSharedPreferences("mrsoft", MODE_PRIVATE);
						editor = sp.edit();
						editor.putString("shouji", editUid);
						editor.putString("editPwd", passwd);
						editor.putString("listence_car", listence_car);
						editor.commit();
						clearTimer();
						startActivity(new Intent(RegistActivity.this,
								LoginActivity.class));
						RegistActivity.this.finish();

					}
				}
				//
				if (msg.what == 91) {
					// 更新界面
					if (chongfu != null && chongfu.equals("false")) {

						showCustomToast("此手机号已经被注册");
						uid.setText("");

					}
				}
				if (msg.what == 92) {
					// 更新界面

					if (chongfu1 != null && chongfu1.equals("false")) {
						showCustomToast("此车牌号已经被注册");
						listence.setText("");
					}

				}

				if (msg.what == 93) {
					String message = goodsList.getString("success");
					// {"message":"发送验证码成功！","returnCode":"0","entity":null,"success":true}
					if (message != null && message.equals("true")) {
						yanzhengflag1 = "1";
					}

					// if (message != null && "codeFail".equals(message)) {
					// Toast.makeText(ZhucheActivity2.this, "验证码失效！",
					// Toast.LENGTH_SHORT).show();
					// return;
					//
					// }
					// if (message != null && "codeerror".equals(message)) {
					// Toast.makeText(ZhucheActivity2.this, "验证码错误！",
					// Toast.LENGTH_SHORT).show();
					// return;
					//
					// }
					// yanzhengflag="1";
				}

				if (msg.what == 94) {
					// if (message != null && "codeFail".equals(message)) {
					// Toast.makeText(ZhucheActivity2.this, "验证码失效！",
					// Toast.LENGTH_SHORT).show();
					// return;
					//
					// }
					// if (message != null && "codeerror".equals(message)) {
					// Toast.makeText(ZhucheActivity2.this, "验证码错误！",
					// Toast.LENGTH_SHORT).show();
					// return;
					//
					// }
					String message = goodsList.getString("success");
					if (message != null && message.equals("true")) {
						yanzhengflag = "1";
						showCustomToast("验证码正确");
					}else{
						showCustomToast("验证码错误");
						clearTimer();
						// 重新验证---
						zhuche_yzm.setText("重新发送验证码");
						zhuche_yzm.setClickable(true);
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
		setContentView(R.layout.regist);
		yanzhengflag = "0";
		yanzhengflag1 = "0";
		recLen = 120;
		i = 0;
		init();
		Listener();
		// save();

	}

	/**
	 * 初始化数据
	 */
	private void init() {
		zhuche_yzm = (TextView) findViewById(R.id.zhuche_yzm);
		zhuche_yanzhengma = (EditText) findViewById(R.id.zhuche_yanzhengma);
		uid = (EditText) findViewById(R.id.regist_shouji);
		pwd = (EditText) findViewById(R.id.zhuche_mima);

		possword = (EditText) findViewById(R.id.regist_mima);
		possword1 = (EditText) findViewById(R.id.regist_mima1);

		TextView toptitile = (TextView) findViewById(R.id.toptitile);
		toptitile.setText("注册");
		login = (Button) findViewById(R.id.regist_login);

		listence = (EditText) findViewById(R.id.regist_listence1);
		// listence.setText("京AA8888");
		// 判断用户和车牌号重复

		// uid.setOnFocusChangeListener(new
		// android.view.View.OnFocusChangeListener() {
		// @Override
		// public void onFocusChange(View v, boolean hasFocus) {
		// if (hasFocus) {
		// // 此处为得到焦点时的处理内容+++++++++++++++++
		//
		// } else {
		// // 此处为没有焦点的处理内容++++++++++++++++
		// // editUid = uid.getText().toString();
		// flag = "1";
		// if (!uid.getText().toString().trim().equals("")) {
		// getGoodsList();
		// } else {
		// showCustomToast("手机号不能为空");
		// }
		// }
		//
		// }
		//
		// });

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
					uid.setSelection(tempSelection);// 设置光标在最后
					if (uid.getText().toString().length() == 11) {
						flag = "1";
						if (isNetworkAvailable()) {

							getGoodsList();
						} else {
							showCustomToast("当前网络不可用，请连接网络后在重试!");
						}
					}

					if (temp.length() >= 12) {
						showCustomToast("手机号不正确,超过11位数字");
						uid.setSelection(tempSelection);// 设置光标在最后
					}
				}
			}
		});

		listence.addTextChangedListener(new TextWatcher() {
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

				selectionStart1 = listence.getSelectionStart();
				selectionEnd1 = listence.getSelectionEnd();
				if (temp1.length() > 0) {
					s1.delete(selectionStart1, selectionEnd1);
					int tempSelection1 = selectionStart1;
					listence.setSelection(tempSelection1);// 设置光标在最后
					if (listence.getText().toString().length() == 7) {
						flag = "2";
						if (isNetworkAvailable()) {
							getGoodsList();
						} else {
							showCustomToast("当前网络不可用，请连接网络后在重试!");
						}
					}
					if (temp1.length() >= 8) {

						showCustomToast("车牌号格式不正确,不能够超过7个字符");
						listence.setSelection(tempSelection1);// 设置光标在最后
						// listence.setText(listence.getText().toString().substring(0,6));
					}
				}
			}
		});
		// 验证码验证
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
					zhuche_yanzhengma.setSelection(tempSelection1);// 设置光标在最后
					if (zhuche_yanzhengma.getText().toString().length() == 6) {
						flag = "4";

						if (isNetworkAvailable()) {
							getGoodsList();
						} else {
							showCustomToast("当前网络不可用，请连接网络后在重试!");
						}
					}
					if (temp1.length() >= 7) {

						showCustomToast("验证码不正确,不能够超过6个数字");
						zhuche_yanzhengma.setSelection(tempSelection1);// 设置光标在最后
						// listence.setText(listence.getText().toString().substring(0,6));
					}
				}
			}
		});

		top_fanhui = (RelativeLayout) findViewById(R.id.top_fanhui);
		top_fanhui.setVisibility(View.VISIBLE);

	}

	/**
	 * 监听
	 */
	private void Listener() {
		// reg_zhuche.setOnClickListener(this);
		login.setOnClickListener(this);
		top_fanhui.setOnClickListener(this);
		zhuche_yzm.setOnClickListener(this);
	}

	// 清除时间进度
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

	// 初始化时间进度
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
		// 开始一个定时任务
		mTimer.schedule(mTimerTask, 0, 1000);
	}

	@Override
	public void onClick(View v) {

		if (v == login) {
			editUid = uid.getText().toString();
			// editPwd = pwd.getText().toString();
			passwd = possword.getText().toString();
			passwd1 = possword1.getText().toString();
			listence_car = listence.getText().toString();

			if (editUid.trim().equals("")) {

				showCustomToast("手机号不能为空");
				return;
			}
			String regExp = "^[1]([3|4|5|7|8][0-9]{1})[0-9]{8}$";
			Pattern p = Pattern.compile(regExp);
			Matcher m = p.matcher(editUid);
			if (!(m.find() == true)) {
				showCustomToast("请输入正确的手机号码");
				return;
			}
			if (zhuche_yanzhengma.getText().toString().trim().equals("")) {

				showCustomToast("验证码不能为空");
				return;

			}

			if (listence_car.trim().equals("")) {

				showCustomToast("车牌号不能为空");
				return;
			}

			Pattern pattern = Pattern
					.compile("^[\u4e00-\u9fa5|WJ]{1}[A-Z0-9]{6}$");
			Matcher matcher = pattern.matcher(listence_car.trim());
			if (!matcher.matches()) {
				showCustomToast("车牌号格式不对！");
				return;

			}

			if (passwd.trim().equals("")) {

				showCustomToast("密码不能为空");
				return;
			}
			if (passwd1.trim().equals("")) {

				showCustomToast("再次输入密码不能为空");
				return;
			}
			if (!passwd.trim().equals(passwd1.trim())) {

				showCustomToast("两次输入的密码不一致");
				return;
			}

			// yanzhengflag1
			if ("0".endsWith(yanzhengflag1)) {
				showCustomToast("发送验证码失败");
				return;
			}
			if ("0".endsWith(yanzhengflag)) {
				showCustomToast("验证码不正确");
				return;
			}
			flag = "0";
			if (isNetworkAvailable()) {
				getGoodsList();

			} else {

				Toast.makeText(RegistActivity.this, "请先连接您的网络，再次操作",
						Toast.LENGTH_SHORT).show();
			}
		}
		if (v == zhuche_yzm) {
			editUid = uid.getText().toString();

			if (editUid.trim().equals("")) {

				showCustomToast("手机号不能为空");
				return;
			}
			String regExp = "^[1]([3|4|5|7|8][0-9]{1})[0-9]{8}$";
			Pattern p = Pattern.compile(regExp);
			Matcher m = p.matcher(editUid);
			if (!(m.find() == true)) {
				showCustomToast("请输入正确的手机号码");
				return;
			}

			flag = "3";

			if (isNetworkAvailable()) {
				getGoodsList();
			} else {
				showCustomToast("当前网络不可用，请连接网络后在重试!");
			}
			// 初始化时间进程
			recLen = 120;
			inittime();
		}
		if (v == top_fanhui) {
			finish();
			clearTimer();
		}
	}

	private void getGoodsList() {

		if ("0".equals(flag)) {
			myDialog = new ProDialog(RegistActivity.this,
					R.style.progressDialog);
			myDialog.show();
		}
		new Thread(new Runnable() {

			@Override
			public void run() {

				if ("0".equals(flag)) {
					try {
						String url = UrlUtil.getUserUrl() + "app/register";
						JSONObject params = new JSONObject();

						params.put("carPlate", listence_car);
						params.put("username", editUid);
						params.put("password", passwd);

						goodsList = HttpClientUtil.doPost(url, params);
						if (!(myDialog == null) && myDialog.isShowing()) {
							myDialog.dismiss();
						}

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

				} else {
					try {
						String url = null;
						if ("1".equals(flag)) {
							url = UrlUtil.getUserUrl() + "app/username/"
									+ uid.getText().toString();

							chongfu = HttpClientUtil.doGet(url);
						}
						if ("2".equals(flag)) {
							url = UrlUtil.getUserUrl() + "app/plate/"
									+ listence.getText().toString();
							chongfu1 = HttpClientUtil.doGet(url);

						}
						if ("3".equals(flag)) {
							url = UrlUtil.getUserUrl() + "app/sendcode?mobile="
									+ editUid + "&type=0";

							goodsList = new JSONObject(
									HttpClientUtil.doGet(url));
						}
						if ("4".equals(flag)) {
							url = UrlUtil.getUserUrl() + "app/checkcode?mobile="
									+ editUid+"&code="+zhuche_yanzhengma.getText().toString();

							goodsList = new JSONObject(
									HttpClientUtil.doGet(url));
						}
						// myDialog.dismiss();

					} catch (Exception e) {
						e.printStackTrace();
					}

					try {
						Message msg = new Message();
						if ("1".equals(flag)) {
							msg.what = 91;
						}
						if ("2".equals(flag)) {
							msg.what = 92;
						}
						if ("3".equals(flag)) {
							msg.what = 93;
						}
						if ("4".equals(flag)) {
							msg.what = 94;
						}
						d.sendEmptyMessage(msg.what);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
		}).start();

	}

	// // 请求网络数据
	// private void getGoodsList1() {
	//
	// new Thread(new Runnable() {
	//
	// @Override
	// public void run() {
	// ConnectWeb cw = new ConnectWeb();
	// try {
	// goodsList = cw.getregist(passwd,editUid,listence_car);
	// if(!(myDialog==null)){
	// myDialog.dismiss();
	// }
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	//
	// try {
	// Message msg = new Message();
	// msg.what = 94;
	// d.sendEmptyMessage(msg.what);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }
	//
	// }).start();
	//
	// }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			clearTimer();
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

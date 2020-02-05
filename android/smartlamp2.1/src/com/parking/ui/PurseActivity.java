package com.parking.ui;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.pay.PayResult;
import com.alipay.sdk.pay.SignUtils;

import com.parking.smarthome.R;
import com.parking.util.ActivityBase;
import com.parking.util.HttpClientUtil;
import com.parking.util.ProDialog;
import com.parking.util.UrlUtil;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class PurseActivity extends ActivityBase implements OnClickListener {
	private RelativeLayout purse_shier, purse_wushi, purse_yibai, purse_erbai;
	// ֧��
	private RelativeLayout purse_weixin, purse_aplay;
	// ѡ��ͼƬ
	private TextView purse_moneyimgshier, purse_moneyimgwushi,
			purse_moneyimgyibai, purse_moneyimgerbai;
	private ImageView purse_weixinquan, purse_aplayquan;

	private String money;

	private Button purse_charge;

	private RelativeLayout top_fanhui;

	/*
	 * ֧�����ĵ�����
	 */

	// �̻�PID
	public static final String PARTNER = "2088221757943976";
	// �̻��տ��˺�
	public static final String SELLER = "oceanbel@yeah.net";
	// �̻�˽Կ��pkcs8��ʽ
	public static final String RSA_PRIVATE = "";
	// ֧������Կ
	public static final String RSA_PUBLIC = "";
	private static final int SDK_PAY_FLAG = 1;

	/*
	 * ΢��֧��������
	 */
	private IWXAPI api;

	private JSONObject goodsList = null;

	private SharedPreferences sp;
	private int i;
	Handler d = new Handler() {

		public void handleMessage(Message msg) {
			try {
				if (msg.what == 90) {
					// ���½���
					String message = goodsList.getString("success");
					if (message != null && message.equals("true")) {
						// String entity = goodsList.getString("entity");
						if (i == 0) {
							payqq();
							i++;
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

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);
				/**
				 * ͬ�����صĽ��������õ�����˽�����֤����֤�Ĺ����뿴https://doc.open.alipay.com/doc2/
				 * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
				 * docType=1) �����̻������첽֪ͨ
				 */
				String resultInfo = payResult.getResult();// ͬ��������Ҫ��֤����Ϣ

				String resultStatus = payResult.getResultStatus();
				// �ж�resultStatus Ϊ��9000�������֧���ɹ�������״̬�������ɲο��ӿ��ĵ�
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(PurseActivity.this, "֧���ɹ�",
							Toast.LENGTH_SHORT).show();
				} else {
					// �ж�resultStatus Ϊ��"9000"��������֧��ʧ��
					// "8000"����֧�������Ϊ֧������ԭ�����ϵͳԭ���ڵȴ�֧�����ȷ�ϣ����ս����Ƿ�ɹ��Է�����첽֪ͨΪ׼��С����״̬��
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(PurseActivity.this, "֧�����ȷ����",
								Toast.LENGTH_SHORT).show();

					} else {
						// ����ֵ�Ϳ����ж�Ϊ֧��ʧ�ܣ������û�����ȡ��֧��������ϵͳ���صĴ���
						Toast.makeText(PurseActivity.this, "֧��ʧ��",
								Toast.LENGTH_SHORT).show();

					}
				}
				break;
			}
			default:
				break;
			}
		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.purse);
		i = 0;
		init();
		Listener();

	}

	/**
	 * ��ʼ������
	 */
	private void init() {
		money = "20";
		purse_charge = (Button) findViewById(R.id.purse_charge);
		TextView  purse_money=(TextView) findViewById(R.id.purse_money);
		sp = getSharedPreferences("mrsoft", MODE_PRIVATE); // ���Preferences
		String count = sp.getString("count", "count");
		purse_money.setText("����ǰ��ͣ������:"+count+"Ԫ");
		
		purse_moneyimgshier = (TextView) findViewById(R.id.purse_moneyimgshier);
		purse_moneyimgwushi = (TextView) findViewById(R.id.purse_moneyimgwushi);
		purse_moneyimgyibai = (TextView) findViewById(R.id.purse_moneyimgyibai);
		purse_moneyimgerbai = (TextView) findViewById(R.id.purse_moneyimgerbai);
		//
		purse_weixinquan = (ImageView) findViewById(R.id.purse_weixinquan);
		purse_aplayquan = (ImageView) findViewById(R.id.purse_aplayquan);
		purse_weixinquan.setBackgroundResource(R.drawable.address_press);
		purse_aplayquan.setBackgroundResource(R.drawable.address_default);

		// ��ʼ��ͼƬ��
		// purse_moneyimgwushi.setVisibility(View.GONE);
		// purse_moneyimgyibai.setVisibility(View.GONE);
		// purse_moneyimgerbai.setVisibility(View.GONE);

		purse_shier = (RelativeLayout) findViewById(R.id.purse_shier);
		purse_wushi = (RelativeLayout) findViewById(R.id.purse_wushi);
		purse_yibai = (RelativeLayout) findViewById(R.id.purse_yibai);
		purse_erbai = (RelativeLayout) findViewById(R.id.purse_erbai);

		purse_shier.setBackgroundResource(R.drawable.chongzhiax);
		purse_wushi.setBackgroundResource(R.drawable.chongzhi);
		purse_yibai.setBackgroundResource(R.drawable.chongzhi);
		purse_erbai.setBackgroundResource(R.drawable.chongzhi);

		purse_weixin = (RelativeLayout) findViewById(R.id.purse_weixin);
		purse_aplay = (RelativeLayout) findViewById(R.id.purse_aplay);

		TextView toptitile = (TextView) findViewById(R.id.toptitile);
		toptitile.setText("֧��");

		top_fanhui = (RelativeLayout) findViewById(R.id.top_fanhui);
		top_fanhui.setVisibility(View.VISIBLE);
		// ΢��֧��
		api = WXAPIFactory.createWXAPI(this, "wxdfc52a465b39afb2");
		api.registerApp("wxdfc52a465b39afb2");
		
	}

	/**
	 * ����
	 */
	private void Listener() {

		purse_shier.setOnClickListener(this);
		purse_wushi.setOnClickListener(this);
		purse_yibai.setOnClickListener(this);
		purse_erbai.setOnClickListener(this);
		purse_weixin.setOnClickListener(this);
		purse_aplay.setOnClickListener(this);
		purse_charge.setOnClickListener(this);
		top_fanhui.setOnClickListener(this);
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public void onClick(View v) {
		String flag = "0";

		if (v == top_fanhui) {
			finish();
		}

		if (v == purse_shier) {
			//

			purse_moneyimgshier.setTextColor(R.color.lanbackground);
			purse_moneyimgwushi.setTextColor(R.color.black);
			purse_moneyimgyibai.setTextColor(R.color.black);
			purse_moneyimgerbai.setTextColor(R.color.black);

			purse_shier.setBackgroundResource(R.drawable.chongzhiax);
			purse_wushi.setBackgroundResource(R.drawable.chongzhi);
			purse_yibai.setBackgroundResource(R.drawable.chongzhi);
			purse_erbai.setBackgroundResource(R.drawable.chongzhi);

			money = "20";
		}
		if (v == purse_wushi) {
			//
			purse_moneyimgshier.setTextColor(R.color.black);
			purse_moneyimgwushi.setTextColor(R.color.lanbackground);
			purse_moneyimgyibai.setTextColor(R.color.black);
			purse_moneyimgerbai.setTextColor(R.color.black);

			purse_shier.setBackgroundResource(R.drawable.chongzhi);
			purse_wushi.setBackgroundResource(R.drawable.chongzhiax);
			purse_yibai.setBackgroundResource(R.drawable.chongzhi);
			purse_erbai.setBackgroundResource(R.drawable.chongzhi);

			money = "50";
		}
		if (v == purse_yibai) {
			purse_moneyimgshier.setTextColor(R.color.black);
			purse_moneyimgwushi.setTextColor(R.color.black);
			purse_moneyimgyibai.setTextColor(R.color.lanbackground);
			purse_moneyimgerbai.setTextColor(R.color.black);

			purse_shier.setBackgroundResource(R.drawable.chongzhi);
			purse_wushi.setBackgroundResource(R.drawable.chongzhi);
			purse_yibai.setBackgroundResource(R.drawable.chongzhiax);
			purse_erbai.setBackgroundResource(R.drawable.chongzhi);
			money = "100";
		}
		if (v == purse_erbai) {
			purse_moneyimgshier.setTextColor(R.color.black);
			purse_moneyimgwushi.setTextColor(R.color.black);
			purse_moneyimgyibai.setTextColor(R.color.black);
			purse_moneyimgerbai.setTextColor(R.color.lanbackground);

			purse_shier.setBackgroundResource(R.drawable.chongzhi);
			purse_wushi.setBackgroundResource(R.drawable.chongzhi);
			purse_yibai.setBackgroundResource(R.drawable.chongzhi);
			purse_erbai.setBackgroundResource(R.drawable.chongzhiax);
			money = "200";
		}
		if (v == purse_weixin) {
			// purse_weixinquan,purse_aplayquan
			purse_weixinquan.setBackgroundResource(R.drawable.address_press);
			purse_aplayquan.setBackgroundResource(R.drawable.address_default);
			flag = "0";
		}
		if (v == purse_aplay) {
			purse_weixinquan.setBackgroundResource(R.drawable.address_default);
			purse_aplayquan.setBackgroundResource(R.drawable.address_press);
			flag = "1";
		}
		if (v == purse_charge) {
			if ("0".equals(flag)) {
				// showCustomToast("��ֵ΢��"+money+"Ԫ");
				boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
				if (!isPaySupported) {
					showCustomToast("����΢�Ű汾̫��,����������֧��");
				}

				if (isNetworkAvailable()) {

					getGoodsList();
				} else {
					showCustomToast("��ǰ���粻���ã������������������!");
				}

			} else {
				showCustomToast("��ֵ֧����" + money + "Ԫ");

				alipay();

			}

		}

	}

	// ΢��֧���ĵ���
	public void payqq() {
		
		String odersucecee="1";
		sp = getSharedPreferences("mrsoft", MODE_PRIVATE);
		Editor	editor = sp.edit();
		editor.putString("odersucecee",odersucecee);
		editor.commit();
		
		String url = "http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php?plat=android";
		//purse_charge.setEnabled(false);
		Toast.makeText(PurseActivity.this, "��ȡ������...", Toast.LENGTH_SHORT)
				.show();
		try {
			// byte[] buf = Util.httpGet(url);
			// if (buf != null && buf.length > 0) {
			// String content = new String(buf);
			// Log.e("get server pay params:",content);
			JSONObject json1 = new JSONObject(goodsList.toString());
			JSONObject json = new JSONObject(json1.getString("entity"));
			
			if (null != json && !json.has("retcode")) {
				PayReq req = new PayReq();
				// req.appId = "wxf8b4f85f3a794e77"; // ������appId
				req.appId = json.getString("appid");
				req.partnerId = json.getString("partnerid");
				req.prepayId = json.getString("prepayid");
				req.nonceStr = json.getString("noncestr");
				req.timeStamp = json.getString("timestamp");
				req.packageValue = json.getString("package");
				req.sign = json.getString("sign");
				req.extData = "app data"; // optional
				Toast.makeText(PurseActivity.this, "��������֧��", Toast.LENGTH_SHORT)
						.show();
				// ��֧��֮ǰ�����Ӧ��û��ע�ᵽ΢�ţ�Ӧ���ȵ���IWXMsg.registerApp��Ӧ��ע�ᵽ΢��
				api.sendReq(req);
			} else {
				Log.d("PAY_GET", "���ش���" + json.getString("retmsg"));
				Toast.makeText(PurseActivity.this,
						"���ش���" + json.getString("retmsg"), Toast.LENGTH_SHORT)
						.show();
			}
			// }
			// else{
			// Log.d("PAY_GET", "�������������");
			// Toast.makeText(PurseActivity.this, "�������������",
			// Toast.LENGTH_SHORT).show();
			// }
		} catch (Exception e) {
			Log.e("PAY_GET", "�쳣��" + e.getMessage());
			Toast.makeText(PurseActivity.this, "�쳣��" + e.getMessage(),
					Toast.LENGTH_SHORT).show();
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

	// ������������
	private void getGoodsList() {
		final ProDialog myDialog = new ProDialog(PurseActivity.this,
				R.style.progressDialog);
		myDialog.show();

		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					// post����
					String url = UrlUtil.getUserUrl() + "app/pay/wxpay";
					sp = getSharedPreferences("mrsoft", MODE_PRIVATE); // ���Preferences
					String oderid = sp.getString("oderid", "oderid");
					JSONObject params = new JSONObject();
					params.put("sn",oderid);
					
					goodsList = HttpClientUtil.doPost(url, params);

					//String ruslt = HttpClientUtil.post(url, params);

					// String ruslt = HttpClientUtil.doGet(url);
//					if (!(ruslt == null)) {
//						goodsList = new JSONObject(ruslt);
//					}
					myDialog.dismiss();

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

	/**
	 * call alipay sdk pay. ����SDK֧��
	 * 
	 */
	public void alipay() {
		if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE)
				|| TextUtils.isEmpty(SELLER)) {
			showCustomToast("֧��������Կ����������");
			return;
		}
		String orderInfo = getOrderInfo("���Ե���Ʒ", "�ò�����Ʒ����ϸ����", "0.01");

		/**
		 * �ر�ע�⣬�����ǩ���߼���Ҫ���ڷ���ˣ�����˽Կй¶�ڴ����У�
		 */
		String sign = sign(orderInfo);
		try {
			/**
			 * �����sign ��URL����
			 */
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		/**
		 * �����ķ���֧���������淶�Ķ�����Ϣ
		 */
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// ����PayTask ����
				PayTask alipay = new PayTask(PurseActivity.this);
				// ����֧���ӿڣ���ȡ֧�����
				String result = alipay.pay(payInfo, true);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// �����첽����
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * get the sdk version. ��ȡSDK�汾��
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(this);
		String version = payTask.getVersion();
		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * create the order info. ����������Ϣ
	 * 
	 */
	private String getOrderInfo(String subject, String body, String price) {

		// ǩԼ���������ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// ǩԼ����֧�����˺�
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// �̻���վΨһ������
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

		// ��Ʒ����
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// ��Ʒ����
		orderInfo += "&body=" + "\"" + body + "\"";

		// ��Ʒ���
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// �������첽֪ͨҳ��·��
		orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
				+ "\"";

		// ����ӿ����ƣ� �̶�ֵ
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// ֧�����ͣ� �̶�ֵ
		orderInfo += "&payment_type=\"1\"";

		// �������룬 �̶�ֵ
		orderInfo += "&_input_charset=\"utf-8\"";

		// ����δ����׵ĳ�ʱʱ��
		// Ĭ��30���ӣ�һ����ʱ���ñʽ��׾ͻ��Զ����رա�
		// ȡֵ��Χ��1m��15d��
		// m-���ӣ�h-Сʱ��d-�죬1c-���죨���۽��׺�ʱ����������0��رգ���
		// �ò�����ֵ������С���㣬��1.5h����ת��Ϊ90m��
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_tokenΪ���������Ȩ��ȡ����alipay_open_id,���ϴ˲����û���ʹ����Ȩ���˻�����֧��
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// ֧��������������󣬵�ǰҳ����ת���̻�ָ��ҳ���·�����ɿ�
		orderInfo += "&return_url=\"m.alipay.com\"";

		// �������п�֧���������ô˲���������ǩ���� �̶�ֵ ����ҪǩԼ���������п����֧��������ʹ�ã�
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. �����̻������ţ���ֵ���̻���Ӧ����Ψһ�����Զ����ʽ�淶��
	 * 
	 */
	private String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	/**
	 * sign the order info. �Զ�����Ϣ����ǩ��
	 * 
	 * @param content
	 *            ��ǩ��������Ϣ
	 */
	private String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. ��ȡǩ����ʽ
	 * 
	 */
	private String getSignType() {
		return "sign_type=\"RSA\"";
	}

}

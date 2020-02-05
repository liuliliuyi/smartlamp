package com.parking.fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import com.parking.smarthome.R;

import com.parking.ui.AppointActivity;
import com.parking.ui.CardetailActivity;
import com.parking.ui.CarnavActivity;
import com.parking.ui.CarpauseActivity;
import com.parking.ui.LoginActivity;
import com.parking.ui.OrdersActivity;
import com.parking.ui.PurseActivity;
import com.parking.ui.WelcomeActivity;


import com.parking.util.HttpClientUtil;
import com.parking.util.UrlUtil;
import com.parking.widget.AlertDiaLog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.media.FaceDetector;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MycentreFragment extends Fragment implements OnClickListener {
	Activity context;
	private SharedPreferences sp;
	private Editor editor;
	private String uid;
	private String pepole;
	private String oderid;
	private String freeplace;
	private RelativeLayout layout_not_logined;
	private String odersucecee;
	private JSONObject goodsList = null;
	private String loginflag;
	private String dianjiflag;
	private TextView balance;
	private int i;

	private Timer mTimer;
	private TimerTask mTimerTask;
	private int recLen;
	private String goodsId;
	


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// layout = inflater.inflate(R.layout.fragment_mine, container, false);
		context = getActivity();
		setOnListener();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_mine, null);
	}

	private void setOnListener() {
		i = 0;
		recLen = 2;
		sp = context.getSharedPreferences("mrsoft", 0);
		uid = sp.getString("shouji", "shouji");
		pepole = sp.getString("pepole", "pepole");
		odersucecee = sp.getString("odersucecee", "odersucecee");
		oderid = sp.getString("oderid", "oderid");
		goodsId= sp.getString("goodsId", "goodsId");
		//freeplace = sp.getString("freeplace", "freeplace");
		String balance2 = sp.getString("balance", "balance");
		String listence_car = sp.getString("listence_car", "listence_car");
		balance = (TextView) context.findViewById(R.id.balance);
		if ("balance".equals(balance2)) {

		} else {
			balance.setText(balance2);
		}
		context.findViewById(R.id.layout_mine_wallet).setOnClickListener(this);
		context.findViewById(R.id.layout_mine_messages)
				.setOnClickListener(this);
		context.findViewById(R.id.layout_mine_yijian).setOnClickListener(this);

		// layout_mine_yijian
		// context.findViewById(R.id.tvloginimg).setOnClickListener(this);

		context.findViewById(R.id.layout_mine_collects)
				.setOnClickListener(this);
		//context.findViewById(R.id.layout_not_logined).setOnClickListener(this);

		context.findViewById(R.id.layout_mine_appoint).setOnClickListener(this);
		TextView toptitile = (TextView) context.findViewById(R.id.toptitile);
		toptitile.setText("��������");

		context.findViewById(R.id.mine_logout).setOnClickListener(this);

		TextView tv_name = (TextView) context.findViewById(R.id.tv_name);
		if ("listence_car".equals(listence_car)) {

		} else {
			tv_name.setText(listence_car);
		}

		TextView tv_login = (TextView) context.findViewById(R.id.tv_login);

		if ("shouji".equals(uid)) {
			tv_login.setText("������¼");
		} else {
			tv_login.setText(uid);
		}
		//tv_login.setOnClickListener(this);

		

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.mine_logout:
			if ("shouji".equals(uid)) {
				
				startActivity(new Intent(getActivity(), LoginActivity.class));
			} else {
				new AlertDiaLog(context).builder().setTitle("�˳���ǰ�˺�")
						.setMsg("��ȷ��Ҫȷ���˳����˺���")
						.setPositiveButton("ȷ���˳�", new OnClickListener() {
							@Override
							public void onClick(View v) {
								
							}
						}).setNegativeButton("ȡ��", new OnClickListener() {
							@Override
							public void onClick(View v) {

							}
						}).show();
			}
			break;
//		case R.id.tv_login:
//			startActivity(new Intent(getActivity(), LoginActivity.class));
//			break;
//		case R.id.layout_not_logined:
//			startActivity(new Intent(getActivity(), LoginActivity.class));
//
//			break;

		// �ҵ��Ŷ�
		case R.id.layout_mine_collects:

			if ("shouji".equals(uid)) {
				
				startActivity(new Intent(getActivity(), LoginActivity.class));
			} else {
				if ("pepole".equals(pepole)) {
					
				} else {
				
				
				}
			}
			break;

		case R.id.layout_mine_wallet: // �ҵ�Ǯ��
			if ("shouji".equals(uid)) {
				
				startActivity(new Intent(getActivity(), LoginActivity.class));
			} else {
				
			}
			break;
		case R.id.layout_mine_messages: // �ҵĶ���
			if ("shouji".equals(uid)) {
				
				startActivity(new Intent(getActivity(), LoginActivity.class));
			} else {
				
				startActivity(new Intent(getActivity(), OrdersActivity.class));
			
			}
			break;
		case R.id.layout_mine_appoint: // �ҵ�ԤԼ

			if ("shouji".equals(uid)) {
				
			} else {
				
				startActivity(new Intent(getActivity(), AppointActivity.class));

			}
			break;
		case R.id.layout_mine_yijian: // �������
			//startActivity(new Intent(getActivity(), YijianActivity.class));

			break;
		default:
			break;
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


	
	public void onBackPressed() {
		clearTimer();
		context.finish();
		
	}

}
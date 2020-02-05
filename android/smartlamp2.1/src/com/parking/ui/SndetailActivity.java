package com.parking.ui;

import com.parking.smarthome.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;

import android.widget.RelativeLayout;
import android.widget.TextView;






public class SndetailActivity extends Activity implements OnClickListener {

	private RelativeLayout top_fanhui;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sndetail);
		 
		init();
		Listener();

	}

	/**
	 * 初始化数据
	 */
	private void init() {

		
		TextView toptitile = (TextView)findViewById(R.id.toptitile);
		toptitile.setText("设备详情");
		top_fanhui = (RelativeLayout) findViewById(R.id.top_fanhui);
		top_fanhui.setVisibility(View.VISIBLE);
		
		
		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		
		
		
		String sn1 = b.getString("sn");
		String imei1 = b.getString("imei");
		String csq = b.getString("csq");
		String pvVoltage = b.getString("pvVoltage");
		String batVoltage = b.getString("batVoltage");
		String nbStage = b.getString("nbStage");
		String uploadInterval = b.getString("uploadInterval");
		String groupNum = b.getString("groupNum");
		String checkSum = b.getString("checkSum");
		String version = b.getString("version");
		String batType = b.getString("batType");
		String batNum = b.getString("batNum");
		
		
		
		
		TextView  sn= (TextView)findViewById(R.id.sn);
		sn.setText("SN:"+sn1);
		TextView moshixiaojian= (TextView)findViewById(R.id.moshixiaojian);
		moshixiaojian.setText("模式校验:"+checkSum);
		
	    TextView  nbchuji= (TextView)findViewById(R.id.nbchuji);
	    nbchuji.setText("NB初始阶段:"+nbStage);
		
	    
	    TextView nbcqs= (TextView)findViewById(R.id.nbcqs);
		nbcqs.setText("NB-CSQ:"+csq);
		
		TextView  guangbandianya= (TextView)findViewById(R.id.guangbandianya);
		
//		String dianya=String.valueOf(list.get(position).getPvVoltage());
		double dev=(double) Math.round(Double.parseDouble(pvVoltage)*100) / 100;
		
		guangbandianya.setText("光板电压:"+String.valueOf(dev));
		
		TextView imei= (TextView)findViewById(R.id.imei);
		imei.setText("IMEI:"+imei1);
		
	    TextView  xinghao= (TextView)findViewById(R.id.xinghao);
	    xinghao.setText("组号:"+groupNum);
	    
	    
		TextView jiange= (TextView)findViewById(R.id.jiange);
		jiange.setText("上传间隔:"+uploadInterval+"秒");
		
		TextView  ruanjianban= (TextView)findViewById(R.id.ruanjianban);
		ruanjianban.setText("版本号:"+version);
		
		TextView dianchidianya= (TextView)findViewById(R.id.dianchidianya);
		double dev1=(double) Math.round(Double.parseDouble(batVoltage)*100) / 100;
		dianchidianya.setText("电池电压:"+String.valueOf(dev1));
		
	    TextView  dianchileixing= (TextView)findViewById(R.id.dianchileixing);
	    dianchileixing.setText("电池类型:"+batType);
	    
	    TextView  dianchishuliang= (TextView)findViewById(R.id.dianchishuliang);
	    
	    dianchishuliang.setText("电池串数:"+batNum);
		
	}

	/**
	 * 监听
	 */
	private void Listener() {
		top_fanhui.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == top_fanhui) {
			finish();
		}
		
	}

}

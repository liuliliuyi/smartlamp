/*
 * Copyright (C) 2018 Jenly Yu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.parking.ui;

import static com.example.jpushdemo.TagAliasOperatorHelper.ACTION_SET;
import static com.example.jpushdemo.TagAliasOperatorHelper.sequence;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;

import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.king.zxing.CaptureActivity;
import com.king.zxing.DecodeFormatManager;

import com.king.zxing.camera.CameraConfigurationUtils;
import com.parking.smarthome.R;

import com.parking.ui.CarnavActivity.CartAdapter1;
import com.parking.util.HttpClientUtil;
import com.parking.util.JsonUtil;
import com.parking.util.ProDialog;
import com.parking.util.UrlUtil;
import com.parking.widget.AlertDiaLog;
import com.parking.widget.CountDialog;




public class CustomCaptureActivity2 extends CaptureActivity {

    private ImageView ivFlash;
    private ProDialog myDialog;
    JSONObject goodsList = null;
    private String result1;
	Handler d = new Handler() {

		public void handleMessage(Message msg) {
			try {
				
				if (msg.what == 92) {
					// 更新界面  {"success":false,"errorCode":"1","msg":"路灯已有电信编码=1563039，请确认是否重新生成","body":{"data":"exist"}}
					String message = goodsList.getString("code");
					if (message != null && message.equals("200")) {
						
						new AlertDiaLog(CustomCaptureActivity2.this).builder().setTitle("查询路灯编码")
						.setMsg("电信编码:"+goodsList.getJSONObject("data").getString("telecomCode")+"\r复制编码?")
						.setPositiveButton("确定", new OnClickListener() {
							@Override
							public void onClick(View v) {
								 //获取剪贴板管理器：
				                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				                // 创建普通字符型ClipData
				                ClipData mClipData = null;
								try {
									mClipData = ClipData.newPlainText("Label", goodsList.getJSONObject("data").getString("telecomCode"));
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				                // 将ClipData内容放到系统剪贴板里。
				                cm.setPrimaryClip(mClipData);
				                Toast.makeText(CustomCaptureActivity2.this,"路灯编码已经复制到剪切板",Toast.LENGTH_SHORT).show();
				                finish();
							}
						}).setNegativeButton("取消", new OnClickListener() {
							@Override
							public void onClick(View v) {
                                finish();      
							}
						}).show();	

	
					} else {
						new AlertDiaLog(CustomCaptureActivity2.this).builder().setTitle("查询路灯编码")
						.setMsg("未安装此编号的路灯")
						.setPositiveButton("确定", new OnClickListener() {
							@Override
							public void onClick(View v) {
								
				                finish();
							}
						}).setNegativeButton("取消", new OnClickListener() {
							@Override
							public void onClick(View v) {
                                finish();      
							}
						}).show();	
					}

				}

			} catch (Exception e) {

				e.printStackTrace();
			}
		};

	};
    @Override
    public int getLayoutId() {
        return R.layout.custom_capture_activity;
    }

    @Override
    public void onCreate(Bundle icicle) {

        super.onCreate(icicle);
       
//        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
//        tvTitle.setText("安装");

        ivFlash = (ImageView) findViewById(R.id.ivFlash);

        if(!hasTorch()){
            ivFlash.setVisibility(View.GONE);
        }

       
      
        getCaptureHelper().playBeep(true)
                .vibrate(true)
                 .decodeFormats(DecodeFormatManager.QR_CODE_FORMATS)//设置只识别二维码会提升速度
                .supportVerticalCode(true)
                .continuousScan(false);
    }

  
    public void setTorch(boolean on){
        Camera camera = getCameraManager().getOpenCamera().getCamera();
        Camera.Parameters parameters = camera.getParameters();
        CameraConfigurationUtils.setTorch(parameters,on);
        camera.setParameters(parameters);

    }

   
    public boolean hasTorch(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }


   
    @Override
    public boolean onResultCallback(String result) {
    	
    	
    	
    	     getGoodsList2(result);
    	     
//             Intent it = new Intent();
//			 Bundle b = new Bundle();
//			 b.putString("qrcode",result);
//			 it.putExtras(b);
//			 it.setClass(this, CarnavActivity.class);
//			 startActivity(it);

        return true;
    }
    
    
    private void getGoodsList2(final String qrcode) {
		myDialog = new ProDialog(CustomCaptureActivity2.this, R.style.progressDialog);
		myDialog.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String url = UrlUtil.getUserUrl() + "blade-lamp/api/device/checkTelecomNumber?qrcode="+qrcode;		
					SharedPreferences sp = getSharedPreferences("mrsoft", MODE_PRIVATE);
					String token = sp.getString("token", "token"); 
					String ruslt = HttpClientUtil.get(CustomCaptureActivity2.this,url,token);
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
					msg.what = 92;
					d.sendEmptyMessage(msg.what);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}).start();

	}
    
    
   public   void showCustomToast(String text) {
		
		View toastRoot = LayoutInflater.from(CustomCaptureActivity2.this).inflate(R.layout.common_toast, null);
	
		((TextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
		Toast toast = new Toast(CustomCaptureActivity2.this);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	
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


    private void clickFlash(View v){
        boolean isSelected = v.isSelected();
        setTorch(!isSelected);
        v.setSelected(!isSelected);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.ivLeft:
                onBackPressed();
                break;
            case R.id.ivFlash:
                clickFlash(v);
                break;
        }
    }
    
    
    
}

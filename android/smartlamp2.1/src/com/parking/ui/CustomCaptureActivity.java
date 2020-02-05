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

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jpushdemo.TagAliasOperatorHelper;
import com.example.jpushdemo.TagAliasOperatorHelper.TagAliasBean;
import com.google.zxing.DecodeHintType;
import com.king.zxing.CaptureActivity;
import com.king.zxing.DecodeFormatManager;

import com.king.zxing.camera.CameraConfigurationUtils;
import com.parking.smarthome.R;
import com.parking.util.HttpClientUtil;
import com.parking.util.ProDialog;
import com.parking.util.UrlUtil;
import com.parking.widget.AlertDiaLog;



public class CustomCaptureActivity extends CaptureActivity {

    private ImageView ivFlash;
    private ProDialog myDialog;
    JSONObject goodsList = null;
    private String result1;
	Handler d = new Handler() {

		public void handleMessage(Message msg) {
			try {
	

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
       
    	
             Intent it = new Intent();
			 Bundle b = new Bundle();
			 b.putString("qrcode",result);
			 b.putString("flagresult","1");
			 it.putExtras(b);
			 it.setClass(this, CarnavActivity.class);
			 startActivity(it);

        return super.onResultCallback(result);
    }
    
    
   public   void showCustomToast(String text) {
		
		View toastRoot = LayoutInflater.from(CustomCaptureActivity.this).inflate(R.layout.common_toast, null);
	
		((TextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
		Toast toast = new Toast(CustomCaptureActivity.this);
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

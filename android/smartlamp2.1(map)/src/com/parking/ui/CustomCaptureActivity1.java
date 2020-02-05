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

import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.DecodeHintType;
import com.king.zxing.CaptureActivity;
import com.king.zxing.DecodeFormatManager;

import com.king.zxing.camera.CameraConfigurationUtils;
import com.parking.smarthome.R;




public class CustomCaptureActivity1 extends CaptureActivity {

    private ImageView ivFlash;

    private final int ResultCode = 54137;
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
       
//            Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
//             Intent it = new Intent();
//			 Bundle b = new Bundle();
//			 b.putString("qrcode",result);
//			 it.putExtras(b);
//			 it.setClass(this, BaoxiuActivity.class);
//			 startActivity(it);
    	
    	Intent it = new Intent();// 创建Intent对象
		Bundle b = new Bundle();
		b.putString("qrcode",result);
		
		it.putExtras(b);
		CustomCaptureActivity1.this.setResult(ResultCode, it);
		CustomCaptureActivity1.this.finish();
    	
    	
    	

        return super.onResultCallback(result);
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

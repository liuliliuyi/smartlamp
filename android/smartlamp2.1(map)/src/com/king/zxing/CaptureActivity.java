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
package com.king.zxing;

import android.app.Activity;
import android.os.Bundle;

import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.Window;


import com.king.zxing.camera.CameraManager;
import com.parking.smarthome.R;


public class CaptureActivity extends Activity implements OnCaptureCallback{

    public static final String KEY_RESULT = Intents.Scan.RESULT;

    private SurfaceView surfaceView;
    private ViewfinderView viewfinderView;

    private CaptureHelper mCaptureHelper;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        int layoutId = getLayoutId();
        if(isContentView(layoutId)){
            setContentView(layoutId);
        }
        initUI();
    }

    /**
     * 閸掓繂顫愰崠锟�
     */
    public void initUI(){
        surfaceView = (SurfaceView) findViewById(getSurfaceViewId());
        viewfinderView = (ViewfinderView) findViewById(getViewfinderViewId());
        mCaptureHelper = new CaptureHelper(this,surfaceView,viewfinderView);
        mCaptureHelper.setOnCaptureCallback(this);
        mCaptureHelper.onCreate();
    }

    /**
     * 鏉╂柨娲杢rue閺冩湹绱伴懛顏勫З閸掓繂顫愰崠鏉瓳link #setContentView(int)}閿涘矁绻戦崶鐐拌礋false閺勵垶娓堕懛顏勭箒閸樿鍨垫慨瀣{@link #setContentView(int)}
     * @param layoutId
     * @return 姒涙顓绘潻鏂挎礀true
     */
    public boolean isContentView( int layoutId){
        return true;
    }

    /**
     * 鐢啫鐪琲d
     * @return
     */
    public int getLayoutId(){
        return R.layout.zxl_capture;
    }

    /**
     * {@link ViewfinderView} 閻拷 id
     * @return
     */
    public int getViewfinderViewId(){
        return R.id.viewfinderView;
    }


    /**
     * 妫板嫯顫嶉悾宀勬桨{@link #surfaceView} 閻ㄥ埇d
     * @return
     */
    public int getSurfaceViewId(){
        return R.id.surfaceView;
    }

    /**
     * Get {@link CaptureHelper}
     * @return {@link #mCaptureHelper}
     */
    public CaptureHelper getCaptureHelper(){
        return mCaptureHelper;
    }

    /**
     * Get {@link CameraManager}
     * @return {@link #mCaptureHelper#getCameraManager()}
     */
    public CameraManager getCameraManager(){
        return mCaptureHelper.getCameraManager();
    }

    @Override
    public void onResume() {
        super.onResume();
        mCaptureHelper.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mCaptureHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCaptureHelper.onDestroy();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mCaptureHelper.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    /**
     * 閹恒儲鏁归幍顐ょ垳缂佹挻鐏夐崶鐐剁殶
     * @param result 閹殿偆鐖滅紒鎾寸亯
     * @return 鏉╂柨娲杢rue鐞涖劎銇氶幏锔藉焻閿涘苯鐨㈡稉宥堝殰閸斻劍澧界悰灞芥倵缂侇參锟芥槒绶敍灞艰礋false鐞涖劎銇氭稉宥嗗閹搭亷绱濇妯款吇娑撳秵瀚ら幋锟�
     */
    @Override
    public boolean onResultCallback(String result) {
        return false;
    }
}
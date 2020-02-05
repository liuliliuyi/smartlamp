/*
 * Copyright (C) 2019 Jenly Yu
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

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;


import com.king.zxing.camera.CameraManager;
import com.parking.smarthome.R;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class CaptureFragment extends Fragment implements OnCaptureCallback {

    public static final String KEY_RESULT = Intents.Scan.RESULT;

    private View mRootView;

    private SurfaceView surfaceView;
    private ViewfinderView viewfinderView;

    private CaptureHelper mCaptureHelper;

    public static CaptureFragment newInstance() {

        Bundle args = new Bundle();
        
        CaptureFragment fragment = new CaptureFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        if(isContentView(layoutId)){
            mRootView = inflater.inflate(getLayoutId(),container,false);
        }
        initUI();
        return mRootView;
    }

    /**
     * 鍒濆鍖�
     */
    public void initUI(){
        surfaceView = (SurfaceView) mRootView.findViewById(getSurfaceViewId());
        viewfinderView = (ViewfinderView) mRootView.findViewById(getViewfinderViewId());
        mCaptureHelper = new CaptureHelper(this,surfaceView,viewfinderView);
        mCaptureHelper.setOnCaptureCallback(this);
    }

    /**
     * 杩斿洖true鏃朵細鑷姩鍒濆鍖杮@link #mRootView}锛岃繑鍥炰负false鏃堕渶鑷繁鍘婚�氳繃{@link #setRootView(View)}鍒濆鍖杮@link #mRootView}
     * @param layoutId
     * @return 榛樿杩斿洖true
     */
    public boolean isContentView(int layoutId){
        return true;
    }

    /**
     * 甯冨眬id
     * @return
     */
    public int getLayoutId(){
        return R.layout.zxl_capture;
    }

    /**
     * {@link ViewfinderView} 鐨� id
     * @return
     */
    public int getViewfinderViewId(){
        return R.id.viewfinderView;
    }


    /**
     * 棰勮鐣岄潰{@link #surfaceView} 鐨刬d
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

    //--------------------------------------------

    public View getRootView() {
        return mRootView;
    }

    public void setRootView(View rootView) {
        this.mRootView = rootView;
    }


    //--------------------------------------------

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCaptureHelper.onCreate();
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

    /**
     * 鎺ユ敹鎵爜缁撴灉鍥炶皟
     * @param result 鎵爜缁撴灉
     * @return 杩斿洖true琛ㄧず鎷︽埅锛屽皢涓嶈嚜鍔ㄦ墽琛屽悗缁�昏緫锛屼负false琛ㄧず涓嶆嫤鎴紝榛樿涓嶆嫤鎴�
     */
    @Override
    public boolean onResultCallback(String result) {
        return false;
    }

}

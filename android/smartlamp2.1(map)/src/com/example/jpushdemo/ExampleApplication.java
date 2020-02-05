package com.example.jpushdemo;

import android.app.Application;
import cn.jpush.android.api.JPushInterface;

/**
 * For developer startup JPush SDK
 * 
 * һ�㽨�����Զ��� Application �����ʼ����Ҳ�������� Activity �
 */
public class ExampleApplication extends Application {
    private static final String TAG = "JIGUANG-Example";

    @Override
    public void onCreate() {    	     
    	 Logger.d(TAG, "[ExampleApplication] onCreate");
         super.onCreate();

         JPushInterface.setDebugMode(true); 	// ���ÿ�����־,����ʱ��ر���־
         JPushInterface.init(this);     		// ��ʼ�� JPush
    }
}

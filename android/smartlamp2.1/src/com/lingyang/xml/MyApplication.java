package com.lingyang.xml;
 
import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;

import com.langyue.ble.BluetoothLeService;
import com.parking.smarthome.CustomApplcation;
import com.parking.util.CrashHandler;
import com.tencent.bugly.crashreport.CrashReport;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.widget.Toast;

public class MyApplication extends Application {
	public static boolean isConnect ;
	private BluetoothLeService mBluetoothLeService= new BluetoothLeService();
	private boolean mRssiMonitorFlag = false;
	public static String mDeviceAddress;
	public static boolean serverFlag = false;
	public static boolean serverFlag_1 = false;
	private static Context sContext;
	public static CustomApplcation mInstance;
	
	public final ServiceConnection mServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName componentName,IBinder service) {
		 
			try {
				 mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
				 
					if ( !mBluetoothLeService.initialize()) {
						 
					} 
//					startMonitorRssi();
					mBluetoothLeService.connect(mDeviceAddress);
			} catch (Exception e) {
				// TODO: handle exception
			}
				
		}

		@Override
		public void onServiceDisconnected(ComponentName componentName) {
			 
			mBluetoothLeService = null;
			stopMonitorRssi();
		}
	};
	public void stopMonitorRssi(){
		this.mRssiMonitorFlag  = false;
	}
	public void startMonitorRssi(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				 
				mRssiMonitorFlag = true;
				while(mRssiMonitorFlag){
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					 
					if(mBluetoothLeService!=null){
						boolean flag = mBluetoothLeService.getRssiVal();
						 
					}
				}
			}
		}){
		}.start();
	}
	@Override
	public void onCreate() {
		super.onCreate();
        
		sContext = getApplicationContext();
		CrashHandler.getInstance(sContext);
		
		 /* Bugly SDK初始化
	        * 参数1：上下文对象
	        * 参数2：APPID，平台注册时得到,注意替换成你的appId
	        * 参数3：是否开启调试模式，调试模式下会输出'CrashReport'tag的日志
	        */
	    CrashReport.initCrashReport(getApplicationContext(), "a5029f1899", true);
		
		JPushInterface.setDebugMode(true);  
		JPushInterface.init(this);  

	}
	
	public static Context getContext() {
		return sContext;
	}

	public static CustomApplcation getInstance() {
		return mInstance;
	}
	
	public BluetoothLeService getBluetoothLeService() {
		return mBluetoothLeService;
	}
	public void unservice(){
		unbindService(mServiceConnection);
		mBluetoothLeService.disconnect();
	}
	public void setBluetoothLeService(BluetoothLeService bl){
	 
		this.mBluetoothLeService = bl;
	}
	 
	
}

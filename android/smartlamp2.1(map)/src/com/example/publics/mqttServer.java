package com.example.publics;
 
 
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import com.example.publics.MqttManager;
import com.lingyang.xml.MyApplication;

import static com.example.publics.Contants.TOPIC;
import static com.example.publics.Contants.URL;
import static com.example.publics.Contants.password;
import static com.example.publics.Contants.userName;

 

public class mqttServer extends Service {
	private final static String TAG = mqttServer.class.getSimpleName();
	 
	private final IBinder mBinder = new LocalBinder();
	MyApplication app = (MyApplication) getApplication();
	public class LocalBinder extends Binder {
		public mqttServer getService() {
			return mqttServer.this;
		}
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mBinder;
	}
 
   @Override
   public int onStartCommand(Intent intent, int flags, int startId) {
	// TODO Auto-generated method stub
	   
	   new Thread(new Runnable() {
           @Override
           public void run() {
        	   app.isConnect =MqttManager.getInstance().creatConnect(URL, userName, password,"15930805364");
               MqttManager.getInstance().subscribe(TOPIC, 2);
           }
       }).start();
	return super.onStartCommand(intent, flags, startId);
}
	 
 
}

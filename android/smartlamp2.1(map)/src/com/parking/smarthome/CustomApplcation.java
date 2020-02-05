package com.parking.smarthome;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;






import cn.jpush.android.api.JPushInterface;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.parking.util.CrashHandler;


public class CustomApplcation extends Application {
	private static Context sContext;
	public static CustomApplcation mInstance;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		sContext = getApplicationContext();
		CrashHandler.getInstance(sContext);
		
		JPushInterface.setDebugMode(true);  
		JPushInterface.init(this);  

	}

	
	public static Context getContext() {
		return sContext;
	}

	public static CustomApplcation getInstance() {
		return mInstance;
	}

}

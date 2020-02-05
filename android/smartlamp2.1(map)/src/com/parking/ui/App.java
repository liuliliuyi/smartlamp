package com.parking.ui;

import com.parking.util.CrashHandler;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;




/**
 * Created by ntop on 15/7/8.
 */
public class App extends Application {
	 private static Context sContext;
	 public static Context mContext;
	 @Override
    public void onCreate() {
        super.onCreate();
    
        sContext = getApplicationContext();
        
        CrashHandler.getInstance(sContext);
    
    }

   
    public static Context getContext() {
        return sContext;
    }

}

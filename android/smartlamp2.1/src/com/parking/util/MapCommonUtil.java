package com.parking.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;



public class MapCommonUtil {

	private static int screenWidth = 0;

	private static int screenHeight = 0;

	private static final int TITLE_HEIGHT = 48;

	
	@SuppressWarnings("deprecation")
	public static int getScreenWidth(Context context) {
		if (screenWidth != 0) {
			return screenWidth;
		}
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		screenWidth = wm.getDefaultDisplay().getWidth();
		return screenWidth;
	}

	@SuppressWarnings("deprecation")
	public static int getScreenHeight(Context context) {
		if (screenHeight != 0) {
			return screenHeight;
		}
		int top = 0;
		if (context instanceof Activity) {
			top = ((Activity) context).getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
			if (top == 0) {
				top = (int) (TITLE_HEIGHT * getScreenDensity(context));
			}
		}
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		screenHeight = wm.getDefaultDisplay().getHeight() - top;
		return screenHeight;
	}

	public static float getScreenDensity(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metric = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metric);
		return metric.density;
	}

	public static float getScreenDensityDpi(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metric = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metric);
		return metric.densityDpi;
	}

	

}

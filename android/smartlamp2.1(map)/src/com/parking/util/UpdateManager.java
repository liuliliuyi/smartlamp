package com.parking.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.w3c.dom.Text;





import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateManager {

	private static final int DOWNLOAD = 1;

	private static final int DOWNLOAD_FINISH = 2;

	private Context mContext;

	private JSONObject update;
	private String version;
	private String versionName;
	private String url1;

	// private int h;

	public UpdateManager(Context context,String version,String url1) {
		this.mContext = context;
		this.version= version;
		this.url1= url1;
	}

	/**
	 * 
	 */
	public void checkUpdate() {

		try {

			versionName = mContext.getPackageManager().getPackageInfo(
					mContext.getPackageName(), 0).versionName;
			// version1 = versionName.substring(0,
			// versionName.lastIndexOf('.'));
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (isUpdate()) {

			showNoticeDialog();
		} else {
			// Toast.makeText(mContext, R.string.soft_update_no,
			// Toast.LENGTH_LONG).show();

		}
	}

	/**
	 * 
	 * 
	 * @return
	 */

	/**
	 * 
	 * 
	 * @return
	 * @throws NameNotFoundException
	 */
	private boolean isUpdate() {

		//
		int versionCode = getVersionCode(mContext);

//		try {
//
//			String url = UrlUtil.getUserUrl() + "version/latest";
//			JSONObject params = new JSONObject();
//			params.put("system", "1");
//			params.put("type", "2");
//
//			JSONObject goodsList = HttpClientUtil.doPostsave(mContext, url,
//					params);
//			
//			String status = goodsList.getString("code");
//			if ("0000".equals(status)) {
//			JSONObject data=goodsList.getJSONObject("data");
//			version = data.getString("version");
//			url1 = data.getString("downUrl");
//
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
//		if (null != update) {
			// 解析版本号
			int serviceCode = Integer.valueOf(version);

			if (serviceCode > versionCode) {
				return true;
			}
//		}
		return false;
	}

	private int getVersionCode(Context context) {
		int versionCode = 0;
		try {

			versionCode = context.getPackageManager().getPackageInfo("com.parking.smarthome",
					0).versionCode;

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	/**
	 * 
	 */
	private void showNoticeDialog() {

		String appName = "智慧路灯";
		Intent intent = new Intent(mContext, UpdateService.class);
		intent.putExtra("Key_App_Name", appName);
		intent.putExtra("Key_Down_Url", url1);
		mContext.startService(intent);

	}

}

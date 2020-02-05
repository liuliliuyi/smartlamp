package com.parking.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

// 访问网站数据库获取数据
public class ConnectWeb {
	private static final int TIMEOUT = 30000;// 10秒
	
	public static final String path = "http://192.168.20.103:8098/parking2/";
   //  http://192.168.20.103:8098/parking2/   http://123.57.253.69:8098/parking/
	public static final String path1 = "http://120.76.180.195/movie/";
	// // 访问网站数据库获取数据

	// post请求用来提交数据，主要用于登陆，添加，修改，下订单等数据提交到服务器
	public String connweb1(String url, List<NameValuePair> params) {

		String body = "";
		try {
			
			HttpClient httpClient = new DefaultHttpClient();
			HttpParams httpParams = httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT);
			HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT);
			HttpPost post = new HttpPost(url);
			post.setHeader("Accept", "application/json");
			post.setHeader("Content-type", "application/json");
			post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
			HttpResponse response = httpClient.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				body = EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return body;
	}

	 public String doHttpPostJSON(String url,String editUid,String passwd,String listence_car) throws IOException, JSONException {
	        //定义一个JSON，用于向服务器提交数据
	        JSONObject jsonObject = new JSONObject();
			jsonObject.put("username", editUid);
			jsonObject.put("password", passwd);
			jsonObject.put("carPlate", listence_car);
//			StringEntity stringEntity = new StringEntity(
//					jsonObject.toString());
	        String jsonString = jsonObject.toString();
	        //指定Post参数
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	        nameValuePairs.add(new BasicNameValuePair("data", jsonString));
	        
	        DefaultHttpClient httpClient = new DefaultHttpClient();
	        HttpPost post = new HttpPost(url);
	        post.setHeader("Accept", "application/json");
			post.setHeader("Content-type", "application/json");
	        post.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));
	      //  post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
	        HttpResponse rsp = httpClient.execute(post);
	        
	       
	        String   displayString = null;
	        if (rsp.getStatusLine().getStatusCode() == 200) {
	        	 HttpEntity httpEntity = rsp.getEntity();
	        	displayString = EntityUtils.toString(httpEntity);
			}
	        
	        
			return displayString;
	        
	        
	    }
	
	
	
	
	
	// get请求+++++++++++++++++++++++++++++++++++++++++
	private String connWeb(String url) {
		String body = "";
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpParams httpParams = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT);
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-type", "application/json");

		try {
			HttpResponse response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				body = EntityUtils.toString(entity);
				return body;
			}

		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return body;
	}
	//delete用来删除数据++===============================
	  private String connweb3(String url) {
	      String body = "";
	      DefaultHttpClient httpClient = new DefaultHttpClient();
	      HttpParams httpParams = httpClient.getParams();
	      HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT);
	      HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT);
	    
	      
	      HttpDelete httpDelete = new HttpDelete(url);
	      httpDelete.setHeader("Accept", "application/json");
	      httpDelete.setHeader("Content-type", "application/json");
	      
	      try {
	         HttpResponse response = httpClient.execute(httpDelete);
	         if (response.getStatusLine().getStatusCode()==200) {
	            HttpEntity entity = response.getEntity();
	            body = EntityUtils.toString(entity);
	            return body;
	         }
	         
	      } catch (IllegalStateException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	      return body;
	   }

	
	

}

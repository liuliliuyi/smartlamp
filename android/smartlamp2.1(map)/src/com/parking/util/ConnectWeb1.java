package com.parking.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import android.R.integer;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

// 访问网站数据库获取数据
public class ConnectWeb1 {
	private static final int TIMEOUT = 30000;// 10秒
	
	
	public static final String path = "http://12qu.com/movie/";
	//public static final String path = "http://120.76.180.195/movie/";

	// // 访问网站数据库获取数据

	// post请求用来提交数据，主要用于登陆，添加，修改，下订单等数据提交到服务器
	public String connweb1(String url, List<NameValuePair> params) {

		String body = "";
		try {
			HttpPost post = new HttpPost(url);
			HttpClient httpClient = new DefaultHttpClient();
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

	// get请求+++++++++++++++++++++++++++++++++++++++++
	private String connWeb(String url) {
		String body = "";
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpParams httpParams = httpClient.getParams();

		HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT);
		HttpGet httpGet = new HttpGet(url);
		// post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
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

	// delete用来删除数据++===============================
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

	/**
	 * 获取图片
	 * 
	 * @param url
	 * @return 图片流
	 */
	public static InputStream conGetImg(String url) {
		try {

			URL uri = new URL(url);
			HttpURLConnection con = (HttpURLConnection) uri.openConnection();
			con.setConnectTimeout(3000);
			con.setRequestMethod("GET");
			InputStream is = con.getInputStream();
			return is;

		} catch (Exception e) {
			Log.d("获取图片输入流", "获取图片网络错误");
			e.printStackTrace();

		}
		return null;
	}

	public Bitmap getimage(String uri) {

		Log.e("uri is ", "" + uri);

		HttpGet httpRequest = new HttpGet(uri);
		// 取得HttpClient 对象
		HttpClient httpclient = new DefaultHttpClient();
		try {
			// 请求httpClient ，取得HttpRestponse
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 取得相关信息 取得HttpEntiy
				HttpEntity httpEntity = httpResponse.getEntity();
				// 获得一个输入流
				InputStream is = httpEntity.getContent();
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				is.close();
				return bitmap;
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getshichanglist(String index) throws JSONException{
		String url2 = path + "toplist.action?currentpage="+index+"&pagesize=20";
		String stryouhui = connWeb(url2);

		//JSONObject temp2 = new JSONObject(stryouhui);
		return stryouhui;
	}

	public JSONObject getfatie(String uid,String biaoti, String content)
			throws JSONException {

		String url3 = path+ "issuetopic.action?";

		try {
			content = URLDecoder.decode(content, "UTF-8");
			biaoti = URLDecoder.decode(biaoti, "UTF-8");
		
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userid",uid));
		params.add(new BasicNameValuePair("title", biaoti));
		params.add(new BasicNameValuePair("content", content));

		String str = connweb1(url3, params);

		JSONObject temp7 = new JSONObject(str);

		return temp7;

	}

	//注册
	public JSONObject getregist(String pwd,String moblie,String listence_car)
			throws JSONException {
		String identity = "其他";
		String agency="其他";
		String url3 = path + "reg.action?";
		String name=null;
		try {
			identity= URLDecoder.decode(identity, "UTF-8");
			agency= URLDecoder.decode(agency, "UTF-8");
			 name= URLDecoder.decode((listence_car.substring(0,1)+moblie), "UTF-8");
		
		
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("sex","1"));
		params.add(new BasicNameValuePair("mobile",moblie));
		params.add(new BasicNameValuePair("name",name));
		params.add(new BasicNameValuePair("password",pwd));
		params.add(new BasicNameValuePair("identity",identity));
		params.add(new BasicNameValuePair("agency",agency));
		String str = connweb1(url3, params);

		JSONObject temp7 = new JSONObject(str);

		return temp7;

	}

	public String getshichanglist1(String index,String topicid) throws JSONException{
		String url2 = path + "commentlist.action?currentpage="+index+"&pagesize=20"+"&topicid="+topicid;
		String stryouhui = connWeb(url2);

		//JSONObject temp2 = new JSONObject(stryouhui);
		return stryouhui;
	}

	public JSONObject pinglun(String comment, String opuid, String id)
			throws JSONException {

		String url3 = path+ "comment.action?";

		try {
			comment = URLDecoder.decode(comment, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("content",comment));
		params.add(new BasicNameValuePair("userid",opuid));
		params.add(new BasicNameValuePair("topicid",id));
		
		
		String str = connweb1(url3, params);

		JSONObject temp7 = new JSONObject(str);

		return temp7;
	}

	public JSONObject delu(String uid, String editPwd) throws JSONException {
	
		String url = path + "login.action?" + "mobile=" + uid + "&password="
				+ editPwd;
		String str = connWeb(url);
		

		JSONObject temp = new JSONObject(str);
		return temp;
	}
	  	
	
	
}
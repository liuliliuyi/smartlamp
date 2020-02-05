package com.parking.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;

import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class HttpClientUtil {
	private static final int TIMEOUT = 30000;// 10秒
	private static HttpParams httpParams;
	private static DefaultHttpClient httpClient;
	private static String JSESSIONID; // 定义一个静态的字段，保存sessionID

	public static HttpClient getHttpClient() throws Exception {
		// 创建 HttpParams 以用来设置 HTTP 参数（这一部分不是必需的）
		httpParams = new BasicHttpParams();
		// 设置连接超时和 Socket 超时，以及 Socket 缓存大小
		HttpConnectionParams.setConnectionTimeout(httpParams, 20 * 1000);
		HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);
		HttpConnectionParams.setSocketBufferSize(httpParams, 8192);
		// 设置重定向，缺省为 true
		HttpClientParams.setRedirecting(httpParams, true);
		// 设置 user agent
		String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6";
		HttpProtocolParams.setUserAgent(httpParams, userAgent);
		// 创建一个 HttpClient 实例
		// 注意 HttpClient httpClient = new HttpClient(); 是Commons HttpClient
		// 中的用法，在 Android 1.5 中我们需要使用 Apache 的缺省实现 DefaultHttpClient
		httpClient = new DefaultHttpClient(httpParams);
		return httpClient;
	}
	// get请求+++++++++++++++++++++++++++++++++++++++++
	public static String doGet(String url) {
		String body = "";
	      try {
	    	 getHttpClient();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//DefaultHttpClient httpClient = new DefaultHttpClient();
//		HttpParams httpParams = httpClient.getParams();
//		HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT);
//		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT);
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-type", "application/json");
		if(null != JSESSIONID){
			httpGet.setHeader("Cookie", "JSESSIONID="+JSESSIONID);
		}
		try {
			HttpResponse response = httpClient.execute(httpGet);
			// 405  返回的状态
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				body = EntityUtils.toString(entity);
				/* 获取cookieStore */
				CookieStore cookieStore = httpClient.getCookieStore();
				List<Cookie> cookies = cookieStore.getCookies();
				for(int i=0;i<cookies.size();i++){
					if("JSESSIONID".equals(cookies.get(i).getName())){
						JSESSIONID = cookies.get(i).getValue();
						break;
					}
				}
				return body;
			}

		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return body;
	}

	// get请求+++++++++++++++++++++++++++++++++++++++++
		public static String doGetHead(String url) {
			String body = "";
			

		      try {
		    	 getHttpClient();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			HttpGet httpGet = new HttpGet(url);
			httpGet.setHeader("Accept", "application/json");
			httpGet.setHeader("Content-type", "application/json");
			httpGet.setHeader("pageNumber", "0");
			httpGet.setHeader("pageSize", "40");
			
			
			if(null != JSESSIONID){
				httpGet.setHeader("Cookie", "JSESSIONID="+JSESSIONID);
			}
			try {
				HttpResponse response = httpClient.execute(httpGet);
				if (response.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = response.getEntity();
					body = EntityUtils.toString(entity);
					/* 获取cookieStore */
					CookieStore cookieStore = httpClient.getCookieStore();
					List<Cookie> cookies = cookieStore.getCookies();
					for(int i=0;i<cookies.size();i++){
						if("JSESSIONID".equals(cookies.get(i).getName())){
							JSESSIONID = cookies.get(i).getValue();
							break;
						}
					}
					
					
					
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
	 * post请求
	 * 
	 * @param url
	 * @param json
	 * @return
	 */

	public static JSONObject doPost(String url, JSONObject json) {
		
		      try {
		    	 getHttpClient();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		//DefaultHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		JSONObject response = null;
		try {
			StringEntity s = new StringEntity(json.toString(), "UTF-8");
			
			s.setContentType("application/json");// 发送json数据需要设置contentType

			post.setEntity(s);
			
			if(null != JSESSIONID){
				post.setHeader("Cookie", "JSESSIONID="+JSESSIONID);
			}
			
			
			HttpResponse res = httpClient.execute(post);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// HttpEntity entity = res.getEntity();
				String result = EntityUtils.toString(res.getEntity(), "UTF-8");// 返回json格式：
				response = new JSONObject(result);
				/* 获取cookieStore */
				CookieStore cookieStore = httpClient.getCookieStore();
				List<Cookie> cookies = cookieStore.getCookies();
				for(int i=0;i<cookies.size();i++){
					if("JSESSIONID".equals(cookies.get(i).getName())){
						JSESSIONID = cookies.get(i).getValue();
						
//						if(!(JSESSIONID==null)){
//							//数据库放到本地去
//						   
//						}
						
						break;
					}
				}
				// response = JSONObject.fromObject(result);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return response;
	}

	/**
	 * post请求
	 * 
	 * @param url
	 * @param json
	 * @return
	 */
   //防止丢失-----
	public static JSONObject doPostsave(Context context,String url, JSONObject json) {
		
		      try {
		    	 getHttpClient();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		//DefaultHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		JSONObject response = null;
		try {
			StringEntity s = new StringEntity(json.toString(), "UTF-8");
			
			s.setContentType("application/json");// 发送json数据需要设置contentType

			post.setEntity(s);
			
			if(null != JSESSIONID){
				post.setHeader("Cookie", "JSESSIONID="+JSESSIONID);
			}
			
			
			HttpResponse res = httpClient.execute(post);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// HttpEntity entity = res.getEntity();
				String result = EntityUtils.toString(res.getEntity(), "UTF-8");// 返回json格式：
				response = new JSONObject(result);
				/* 获取cookieStore */
				CookieStore cookieStore = httpClient.getCookieStore();
				List<Cookie> cookies = cookieStore.getCookies();
				for(int i=0;i<cookies.size();i++){
					if("JSESSIONID".equals(cookies.get(i).getName())){
						JSESSIONID = cookies.get(i).getValue();
						
						if(!(JSESSIONID==null)){
							//数据库放到本地去
							SharedPreferences	sp = context.getSharedPreferences("mrsoft", 0);
							Editor editor = sp.edit();
							editor.putString("JSESSIONID",JSESSIONID);
							editor.commit(); 
						}
						
						break;
					}
				}
				// response = JSONObject.fromObject(result);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return response;
	}	
	
	// post请求用来提交数据，主要用于登陆，添加，修改，下订单等数据提交到服务器
	public static String postsave(Context context,String url, List<BasicNameValuePair> params) {

	      try {
	    	 getHttpClient();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String body = "";
		try {
			HttpPost post = new HttpPost(url);
			
			post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
			if(null != JSESSIONID){
				post.setHeader("Cookie", "JSESSIONID="+JSESSIONID);
			}else{
				//数据库放到本地去
//				SharedPreferences	sp = context.getSharedPreferences("mrsoft", 0);
//				Editor editor = sp.edit();
//				editor.putString("JSESSIONID",JSESSIONID);
//				editor.commit(); 
				SharedPreferences	sp = context.getSharedPreferences("mrsoft",0);
				String JSESSIONID1 = sp.getString("JSESSIONID", "JSESSIONID");
				if("JSESSIONID".equals(JSESSIONID1)){
					
				}else{
				post.setHeader("Cookie", "JSESSIONID="+JSESSIONID);
				}
			
			
			}
			
			HttpResponse response = httpClient.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				body = EntityUtils.toString(response.getEntity());
				/* 获取cookieStore */
				CookieStore cookieStore = httpClient.getCookieStore();
				List<Cookie> cookies = cookieStore.getCookies();
				for(int i=0;i<cookies.size();i++){
					if("JSESSIONID".equals(cookies.get(i).getName())){
						JSESSIONID = cookies.get(i).getValue();
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return body;
	}
	
	
	// post请求用来提交数据，主要用于登陆，添加，修改，下订单等数据提交到服务器
		public static String post(Context conext,String url, List<BasicNameValuePair> params) {

		      try {
		    	 getHttpClient();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String body = "";
			try {
				HttpPost post = new HttpPost(url);
				
				post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
				//  clientId:clientSecret
//				if(null != JSESSIONID){
				String base="clientId:clientSecret";
					post.setHeader("Authorization","Basic "+encode(base.getBytes("UTF-8")));
					post.setHeader("Content-Type","application/x-www-form-urlencoded");
//				}
				
				HttpResponse response = httpClient.execute(post);
				if (response.getStatusLine().getStatusCode() == 200) {
					body = EntityUtils.toString(response.getEntity());
//					/* 获取cookieStore */
//					CookieStore cookieStore = httpClient.getCookieStore();
//					List<Cookie> cookies = cookieStore.getCookies();
//					for(int i=0;i<cookies.size();i++){
//						if("JSESSIONID".equals(cookies.get(i).getName())){
//							JSESSIONID = cookies.get(i).getValue();
//							break;
//						}
//					}
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return body;
		}
		
		
		
		public static String postUser(Context conext,String url,String token) {

		      try {
		    	 getHttpClient();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String body = "";
			try {
				HttpGet post = new HttpGet(url);
				
				// post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
				//  clientId:clientSecret
//				if(null != JSESSIONID){
				
			    post.setHeader("Authorization","bearer "+token);
					
//				}
				
				HttpResponse response = httpClient.execute(post);
				if (response.getStatusLine().getStatusCode() == 200) {
					body = EntityUtils.toString(response.getEntity());
//					/* 获取cookieStore */
//					CookieStore cookieStore = httpClient.getCookieStore();
//					List<Cookie> cookies = cookieStore.getCookies();
//					for(int i=0;i<cookies.size();i++){
//						if("JSESSIONID".equals(cookies.get(i).getName())){
//							JSESSIONID = cookies.get(i).getValue();
//							break;
//						}
//					}
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return body;
		}
		
		
		
		public static String get(Context conext,String url,String token) {

		      try {
		    	 getHttpClient();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String body = "";
			try {
				HttpGet get = new HttpGet(url.trim());
			    get.setHeader("Blade-Auth","bearer "+token);
				HttpResponse response = httpClient.execute(get);
				if (response.getStatusLine().getStatusCode() == 200) {
					body = EntityUtils.toString(response.getEntity());	
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return body;
		}
		
		public static String post(Context conext,String url,String token,List<BasicNameValuePair> params) {

		      try {
		    	 getHttpClient();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String body = "";
			try {
				HttpPost post = new HttpPost(url);
				post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
				post.setHeader("Blade-Auth","bearer "+token);
				HttpResponse response = httpClient.execute(post);
				if (response.getStatusLine().getStatusCode() == 200) {
					body = EntityUtils.toString(response.getEntity());	
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return body;
		}
		
		
		
		
		public static String posttoken(Context conext,String url,String id, List<BasicNameValuePair> params) {

		      try {
		    	 getHttpClient();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String body = "";
			try {
				HttpPost post = new HttpPost(url);
				
				post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
				//  clientId:clientSecret
//				if(null != JSESSIONID){
				String base="clientId:clientSecret";
//					post.setHeader("Authorization","Basic "+encode(base.getBytes("UTF-8")));
				    post.setHeader("Authorization","Basic c2FiZXI6c2FiZXJfc2VjcmV0");
					post.setHeader("Content-Type","application/x-www-form-urlencoded");
					post.setHeader("Tenant-Id",id);
					
//				}
				
				HttpResponse response = httpClient.execute(post);
				if (response.getStatusLine().getStatusCode() == 200) {
					body = EntityUtils.toString(response.getEntity());
//					/* 获取cookieStore */
					
				}else if(response.getStatusLine().getStatusCode() == 400){
					//  {"error":"access_denied","error_description":"未获得用户的角色信息"}
					body = EntityUtils.toString(response.getEntity());
				
				}else {
					//  {"error":"access_denied","error_description":"未获得用户的角色信息"}
                    //	body = EntityUtils.toString(response.getEntity());
					body="后台系统出问题了";	
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return body;
		}
		
		
		/** 
	     * 二进制数据编码为BASE64字符串 
	     * 
	     * @param bytes 
	     * @return 
	     * @throws Exception 
	     */  
	    public static String encode(final byte[] bytes) {  
	        return new String(Base64.encodeBase64(bytes));  
	    }  
	    
	   
	    
	 
		
		
		
		public static String post1(Context conext,String url, List<BasicNameValuePair> params) {

		      try {
		    	 getHttpClient();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String body = "";
			try {
				HttpPost post = new HttpPost(url);
				
				post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
//				if(null != JSESSIONID){
//					post.setHeader("Cookie", "JSESSIONID="+JSESSIONID);
//				}
				SharedPreferences sp = conext.getSharedPreferences("mrsoft", 0);
				String JSESSIONID = sp.getString("JSESSIONID", "JSESSIONID");
				post.setHeader("Cookie", "JSESSIONID="+JSESSIONID);
				
				HttpResponse response = httpClient.execute(post);
				if (response.getStatusLine().getStatusCode() == 200) {
					body = EntityUtils.toString(response.getEntity());
					/* 获取cookieStore */
					CookieStore cookieStore = httpClient.getCookieStore();
					List<Cookie> cookies = cookieStore.getCookies();
					for(int i=0;i<cookies.size();i++){
						if("JSESSIONID".equals(cookies.get(i).getName())){
							JSESSIONID = cookies.get(i).getValue();
							break;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return body;
		}
	
	
	
	
	
	
	// post请求用来提交数据，主要用于登陆，添加，修改，下订单等数据提交到服务器
		public static String put(String url, List<BasicNameValuePair> params) {

		      try {
		    	 getHttpClient();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String body = "";
			try {
				HttpPut post = new HttpPut(url);
				
				post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
				if(null != JSESSIONID){
					post.setHeader("Cookie", "JSESSIONID="+JSESSIONID);
				}
				
				HttpResponse response = httpClient.execute(post);
				if (response.getStatusLine().getStatusCode() == 200) {
					body = EntityUtils.toString(response.getEntity());
					/* 获取cookieStore */
					CookieStore cookieStore = httpClient.getCookieStore();
					List<Cookie> cookies = cookieStore.getCookies();
					for(int i=0;i<cookies.size();i++){
						if("JSESSIONID".equals(cookies.get(i).getName())){
							JSESSIONID = cookies.get(i).getValue();
							break;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return body;
		}
	
	
	
	
	
	//delete用来删除数据++===============================
	  public static String delete(String url) {
	      String body = "";
	      DefaultHttpClient httpClient = new DefaultHttpClient();
	      HttpParams httpParams = httpClient.getParams();
	      HttpConnectionParams.setConnectionTimeout(httpParams,TIMEOUT);
	      HttpConnectionParams.setSoTimeout(httpParams,TIMEOUT);
	      MyHttpDelete delete = new MyHttpDelete(url); 
//	      List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(); 
//        nameValuePairs.add(new BasicNameValuePair("glasses_sn",glasses_sn));  
	      try {
	      delete.setHeader("Accept", "application/json");
	      delete.setHeader("Content-type", "application/json");
	    //  delete.setEntity(new UrlEncodedFormEntity(nameValuePairs)); 
	      if(null != JSESSIONID){
	    	  delete.setHeader("Cookie", "JSESSIONID="+JSESSIONID);
			}
	      
	      
	      HttpResponse response = httpClient.execute(delete);
	         if (response.getStatusLine().getStatusCode()==200) {
	            HttpEntity entity = response.getEntity();
	            body = EntityUtils.toString(entity);
	        	/* 获取cookieStore */
				CookieStore cookieStore = httpClient.getCookieStore();
				List<Cookie> cookies = cookieStore.getCookies();
				for(int i=0;i<cookies.size();i++){
					if("JSESSIONID".equals(cookies.get(i).getName())){
						JSESSIONID = cookies.get(i).getValue();
						break;
					}
				}
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

package com.parking.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;


import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.os.Handler;
import android.os.Message;

/**
 * 封装网络的操作工具类：实现可以从网络获取或者上传数据
 * 
 * @author Administrator
 * 
 */
public class HttpUtils {
private static 	JSONObject zhuche; 
	public HttpUtils() {
		// TODO Auto-generated constructor stub
	}

	public static void uploadMultipartEntity(final String uploadPath,
			final String image_info, final File imageFile,
			final UploadImageCallBack callBack) {

		final Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				String result = msg.toString();
				callBack.getResult(zhuche);
			}
		};
		new Thread(new Runnable() {

			@Override
			public void run() {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(uploadPath);
				HttpResponse response = null;

				String a[] = image_info.split(",");  
			      
				  String shouji = a[0];  
				  
				String name=a[1];
	
				String pwd=a[2];
				String sex=a[3];
				String shengfenzheng=a[4];
				String jigou=a[5];
				try {
					// 先封装文本的信息
		//			image上传的文件，mobile手机号，password密码，name呢称，identity身份，agency 机构，sex性别
					MultipartEntity multipartEntity = new MultipartEntity();
					
					
					
					if(!(imageFile==null)){
					multipartEntity.addPart("image", new FileBody(imageFile));
					}
					multipartEntity.addPart("sex", new StringBody(sex));
					

					multipartEntity.addPart("mobile",new StringBody((shouji),Charset.forName(org.apache.http.protocol.HTTP.UTF_8)));
					multipartEntity.addPart("name",new StringBody((name),Charset.forName(org.apache.http.protocol.HTTP.UTF_8)));
					multipartEntity.addPart("password",new StringBody(pwd));

					multipartEntity.addPart("identity",new StringBody((shengfenzheng),Charset.forName(org.apache.http.protocol.HTTP.UTF_8)));
					multipartEntity.addPart("agency",new StringBody((jigou),Charset.forName(org.apache.http.protocol.HTTP.UTF_8)));
			
					new StringBody("data1", Charset
                            .forName(org.apache.http.protocol.HTTP.UTF_8));

					
					httpPost.setEntity(multipartEntity);
					// /////////////////////////////////
					response = httpClient.execute(httpPost);
					if (response.getStatusLine().getStatusCode() == 200) {
						
						
						
						String result = EntityUtils.toString(
								response.getEntity(), HTTP.UTF_8);
						try {
							 zhuche = new JSONObject(result);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						
						Message message = Message.obtain();
						message.obj = zhuche;
						handler.sendMessage(message);
					}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	public interface UploadImageCallBack {
		public void getResult(JSONObject zhuche);
	}
}

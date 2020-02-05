package com.parking.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.parking.model.City;
import com.parking.model.Order;
import com.parking.smarthome.R;

import com.parking.ui.GPSNaviActivity;
import com.parking.ui.OrdersActivity;
import com.parking.ui.RepayActivity;
import com.parking.util.HttpClientUtil;
import com.parking.util.UrlUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import android.widget.TextView;

public class DingdanAdapter extends BaseAdapter {
	private ArrayList<Order> list;
	private Context context;
	private JSONObject goodsList = null;
	private final int ResultCode = 54135;

	

	public DingdanAdapter(Context context, ArrayList<Order> list) {
		this.context = context;
		this.list = list;

	}

	
	
	
	
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		Viewholder vh = null;
		if (convertView == null) {
			vh = new Viewholder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.apointitem, null);
          
			vh.apiont_price = (TextView) convertView
					.findViewById(R.id.apiont_price);
			

			convertView.setTag(vh);
		} else {
			vh = (Viewholder) convertView.getTag();
		}

		vh.apiont_price.setText(list.get(position).getName().toString());
		
		

		
		return convertView;
	}

	class Viewholder {
        RelativeLayout apoint_dj;
		TextView apiont_adress;
		TextView apiont_price;
		TextView apiont_time;
		ImageView apiont_straus;
	
	
	
	
	
	}

//	private void getGoodsList(final String sn) {
//
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//
//				try {
//					String result = null;
//
//					String url = UrlUtil.getUserUrl() + "app/payment/" + sn;
//					result = HttpClientUtil.doGet(url);
//
//					if (!(result == null)) {
//						goodsList = new JSONObject(result);
//					}
//
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//
//				try {
//					Message msg = new Message();
//					msg.what = 90;
//					d.sendEmptyMessage(msg.what);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//			}
//
//		}).start();
//
//	}

}
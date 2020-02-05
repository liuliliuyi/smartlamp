package com.parking.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.parking.model.City;
import com.parking.model.Order;
import com.parking.smarthome.R;
import com.parking.ui.CardetailActivity;
import com.parking.ui.CarnavdetailActivity;
import com.parking.ui.GPSNaviActivity;
import com.parking.util.MapDistance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import android.widget.TextView;

public class CardetailAdapter extends BaseAdapter {
	private ArrayList<City> list;
	private Context context;
	private String geoLat;
	private String geoLng;

	public CardetailAdapter(Context context, ArrayList<City> list, String geoLat,
			String geoLng) {
		this.context = context;
		this.list = list;
		this.geoLat = geoLat;
		this.geoLng = geoLng;
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
					R.layout.caritem2, null);
			//cardetail_nav
			vh.cardetail_nav = (RelativeLayout) convertView.findViewById(R.id.cardetail_nav);
			vh.car_adress = (TextView) convertView.findViewById(R.id.car_adress1);
			vh.car_count = (TextView) convertView.findViewById(R.id.car_count1);
			vh.car_distance = (TextView) convertView
					.findViewById(R.id.car_distance1);
			vh.car_daohang = (RelativeLayout) convertView
					.findViewById(R.id.car_daohang1);

			convertView.setTag(vh);
		} else {
			vh = (Viewholder) convertView.getTag();
		}
		vh.car_adress.setText(list.get(position).getName().toString());
		vh.car_count.setText(list.get(position).getFreePlace().toString());

		String a[] = list.get(position).getDistance().split(",");
		final String jing = a[0];
		final String wei = a[1];
		if (!(geoLat == null) && !(geoLng == null)) {
			String distance = MapDistance
					.getDistance(geoLat, geoLng, jing, wei);
			vh.car_distance.setText(distance + "千米");

		}
		vh.car_daohang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				SharedPreferences sp = context.getSharedPreferences("mrsoft", 0);
				Editor editor = sp.edit();
				editor.putString("jing", jing);
				editor.putString("wei", wei);
				//定位的经纬度
				editor.putString("geoLat", geoLat);
				editor.putString("geoLng", geoLng);
				editor.commit();
				Intent it = new Intent();
				it.setClass(context, GPSNaviActivity.class);
				context.startActivity(it);

			}
		});
		vh.cardetail_nav.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent it = new Intent();
				Bundle b = new Bundle();
				String a[] = list.get(position).getDistance().split(",");
				String jing = a[0];
				String wei = a[1];
				b.putString("distance", list.get(position).getDistance());
				b.putString("name", list.get(position).getName());
				b.putString("id", list.get(position).getId());
				b.putString("price", list.get(position).getPrice());
				b.putString("jing",jing);
				b.putString("wei",wei);
				b.putString("geoLat",String.valueOf(geoLat));
				b.putString("geoLng",String.valueOf(geoLng));
				b.putString("freeplace", list.get(position).getFreePlace());
				it.putExtras(b);
				it.setClass(context, CardetailActivity.class);
				context.startActivity(it);

			}
		});
		
		
		
		return convertView;
	}

	class Viewholder {

		TextView car_adress;
		TextView car_count, car_distance;
		RelativeLayout car_daohang,cardetail_nav;
       
	}

	// public void resh(List<Map<String, Object>> list1) {
	// this.list.addAll(list1);
	// this.notifyDataSetChanged();
	// }

}
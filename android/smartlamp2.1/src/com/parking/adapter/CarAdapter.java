package com.parking.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.parking.model.City;
import com.parking.model.Order;
import com.parking.smarthome.R;
import com.parking.ui.GPSNaviActivity;
import com.parking.util.MapDistance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import android.widget.TextView;

public class CarAdapter extends BaseAdapter {
	private ArrayList<City> list;
	private Context context;
	private String geoLat;
	private String geoLng;

	public CarAdapter(Context context, ArrayList<City> list, String geoLat,
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
	public View getView(int position, View convertView, ViewGroup parent) {

		Viewholder vh = null;
		if (convertView == null) {
			vh = new Viewholder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.caritem, null);
			vh.car_adress = (TextView) convertView
					.findViewById(R.id.car_adress);
			vh.car_count = (TextView) convertView.findViewById(R.id.car_count);
			vh.car_distance = (TextView) convertView
					.findViewById(R.id.car_distance);
			vh.car_daohang = (RelativeLayout) convertView
					.findViewById(R.id.car_daohang);

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
		return convertView;
	}

	class Viewholder {

		TextView car_adress;
		TextView car_count, car_distance;
		RelativeLayout car_daohang;

	}

	// public void resh(List<Map<String, Object>> list1) {
	// this.list.addAll(list1);
	// this.notifyDataSetChanged();
	// }

}
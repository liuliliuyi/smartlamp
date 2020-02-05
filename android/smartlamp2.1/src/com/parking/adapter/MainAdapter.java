package com.parking.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.parking.model.City;
import com.parking.smarthome.R;
import com.parking.widget.AlertDiaLog;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import android.widget.TextView;

public class MainAdapter extends BaseAdapter {
	private ArrayList<City> list;
	private Context context;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options; 
	private SharedPreferences sp;

	public MainAdapter(Context context, ArrayList<City> list) {
		this.context = context;
		this.list = list;
		
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.baibaoxiang)
				.showImageForEmptyUri(R.drawable.baibaoxiang)
				.showImageOnFail(R.drawable.baibaoxiang)
				.cacheInMemory()
				.cacheOnDisc()
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new FadeInBitmapDisplayer(300)).build();
	}
	
	public void addList(List<City> list) {
		this.list.addAll(list);
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
					R.layout.mainitem, null);

			vh.dingdanhao = (TextView) convertView
					.findViewById(R.id.dingdanhao);
			
			vh.textView3 = (TextView) convertView
					.findViewById(R.id.textView3);
			
			vh.xiadanshijian = (TextView) convertView
					.findViewById(R.id.xiadanshijian);
			vh.shijian = (TextView) convertView.findViewById(R.id.shijian);
			vh.zhuangtai = (TextView) convertView.findViewById(R.id.zhuangtai);
			
			vh.jibie = (TextView) convertView.findViewById(R.id.jibie);
			convertView.setTag(vh);
		   } else {
			vh = (Viewholder) convertView.getTag();
		   }
		   vh.textView3.setText(list.get(position).getSn().toString());
		   vh.xiadanshijian.setText(list.get(position).getDescription().toString());
		   vh.shijian.setText("发生时间:   "+list.get(position).getCreateDate().toString());
		   vh.dingdanhao.setText(list.get(position).getDistance().toString()+"公里");
		 // description
		   vh.zhuangtai.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				new AlertDiaLog(context).builder().setTitle("未安装")
				.setMsg("您确定要安装吗？")
				.setPositiveButton("确认安装", new OnClickListener() {
					@Override
					public void onClick(View v) {
						
					}
				}).setNegativeButton("取消", new OnClickListener() {
					@Override
					public void onClick(View v) {

					}
				}).show();
				
			}
		});
		    

		
		return convertView;
	}

	class Viewholder {
		TextView mylearn_detail;
		TextView dingdanhao;
		TextView mylearn_dengji;
		TextView shijian;
		TextView xiadanshijian;
		TextView zhuangtai;
		TextView jibie;
        ImageView jiantou;
        TextView textView3;
	}

}
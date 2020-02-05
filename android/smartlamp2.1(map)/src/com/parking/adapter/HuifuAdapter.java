package com.parking.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.parking.model.Movie;
import com.parking.smarthome.R;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HuifuAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<Movie> list1;
	private Context context;
	private String txt;
	private ImageLoader imageLoader = ImageLoader.getInstance();// 得到图片加载器
	private DisplayImageOptions options; // 显示图像设置
	private android.view.animation.Animation animation;

	public HuifuAdapter(Context context) {
		this.context = context;
		list1 = new ArrayList<Movie>();

		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		// 使用DisplayImageOptions.Builder()创建DisplayImageOptions
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.huancun)
				// 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.huancun)
				// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.huancun)
				// 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory()
				// 设置下载的图片是否缓存在内存中
				.cacheOnDisc()
				// 设置下载的图片是否缓存在SD卡中
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new FadeInBitmapDisplayer(300)).build();

	}

	public void setList(List<Movie> list) {
		this.list1 = list;
	}

	public void addList(List<Movie> list) {
		this.list1.addAll(list);
	}

	public void clearList() {
		this.list1.clear();
	}

	public List<Movie> getList() {
		return list1;
	}

	public void removeItem(int position) {
		if (list1.size() > 0) {
			list1.remove(position);
		}
	}

	@Override
	public int getCount() {
		return list1.size();
	}

	@Override
	public Object getItem(int position) {
		return list1.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		final Viewholder vh;
		if (convertView == null) {
			vh = new Viewholder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.commentrow, null);
			vh.yongfuimg = (ImageView) convertView
					.findViewById(R.id.comment_name);
			
			vh.yongfuname = (TextView) convertView
					.findViewById(R.id.comment_item_name);

			vh.yongfushijian = (TextView) convertView
					.findViewById(R.id.comment_item_shijian);

			vh.wenzhangneirong = (TextView) convertView
					.findViewById(R.id.comment_neirong);

			convertView.setTag(vh);
		} else {
			vh = (Viewholder) convertView.getTag();
		}


		
		
//		if (position % 2 == 0) {
//			convertView.setBackgroundColor(Color.parseColor("#110A2C"));
//		} else {
//			convertView.setBackgroundColor(Color.parseColor("#2D2C56"));
//		}
		
		 imageLoader.displayImage(list1.get(position).getMovietupian().toString(),
				 vh.yongfuimg, options);// 显示头像
		
		 String regExp = "^[1]([3|4|5|7|8][0-9]{1})[0-9]{8}$";
			Pattern p = Pattern.compile(regExp);
			Matcher m = p.matcher(list1.get(position).getFabiaoren().toString());
			if ((m.find() == true)) {
		  vh.yongfuname.setText(list1.get(position).getFabiaoren().toString().substring(0, 3) +
		 "****"+ list1.get(position).getFabiaoren().toString().substring(7, list1.get(position).getFabiaoren().toString().length()));
			}else{
		 vh.yongfuname.setText(list1.get(position).getFabiaoren().toString());
			}
		vh.yongfushijian.setText(list1.get(position).getShijian().toString());
		
		 vh.wenzhangneirong.setText(list1.get(position).getNeirong());
		
		// vh.wenzhanghuifushu.setText(list1.get(position).get("pinglunshu")
		// .toString());

		return convertView;
	}

	class Viewholder {
		ImageView yongfuimg;
		TextView yongfuname;
		TextView yongfushijian;
		TextView wenzhangbiaoti;
		TextView wenzhangneirong;

		Button wenzhangdianzanImg1;

		TextView textView;

	}

	
}
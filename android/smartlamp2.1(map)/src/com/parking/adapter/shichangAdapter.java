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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class shichangAdapter extends BaseAdapter {
	private List<Movie> list;
	private LayoutInflater inflater;
	private ImageLoader imageLoader = ImageLoader.getInstance();// �õ�ͼƬ������
	private DisplayImageOptions options; // ��ʾͼ������
	private Context context;

	public shichangAdapter(Context context) {
		this.context = context;
		list = new ArrayList<Movie>();
		// ͼƬ��������ʼ��
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		// ʹ��DisplayImageOptions.Builder()����DisplayImageOptions
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.huancun)
				// ����ͼƬ�����ڼ���ʾ��ͼƬ
				.showImageForEmptyUri(R.drawable.huancun)
				// ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
				.showImageOnFail(R.drawable.huancun)
				// ����ͼƬ���ػ��������з���������ʾ��ͼƬ
				.cacheInMemory()
				// �������ص�ͼƬ�Ƿ񻺴����ڴ���
				.cacheOnDisc()
				// �������ص�ͼƬ�Ƿ񻺴���SD����
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new FadeInBitmapDisplayer(300)).build();
	}

	public void setList(List<Movie> list) {
		this.list = list;
	}

	public void addList(List<Movie> list) {
		this.list.addAll(list);
	}

	public void clearList() {
		this.list.clear();
	}

	public List<Movie> getList() {
		return list;
	}

	public void removeItem(int position) {
		if (list.size() > 0) {
			list.remove(position);
		}
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
					R.layout.shichangitem, null);

			vh.shichang_name = (TextView) convertView
					.findViewById(R.id.shichang_name);
			vh.shichang_neirong = (TextView) convertView
					.findViewById(R.id.shichang_neirong);
			vh.shichang_shijian = (TextView) convertView
					.findViewById(R.id.shichang_shijian);
			vh.shijian_mingzhi = (TextView) convertView
					.findViewById(R.id.shijian_mingzhi);
			vh.shichang_cishu = (TextView) convertView
					.findViewById(R.id.shichang_cishu);
			vh.shichang_imageView1 = (ImageView) convertView
					.findViewById(R.id.shichang_imageView1);
			convertView.setTag(vh);
		} else {
			vh = (Viewholder) convertView.getTag();
		}
//		if (position % 2 == 0) {
//			//convertView.setBackgroundColor(Color.parseColor("#110A2C"));
//		} else {
//			//convertView.setBackgroundColor(Color.parseColor("#2D2C56"));
//		}
		

		vh.shichang_name.setText(list.get(position).getFabiaotumu().toString()); // get("title").toString());
		vh.shichang_shijian.setText(list.get(position).getShijian().toString());
		vh.shichang_neirong.setText(list.get(position).getNeirong().toString());
		vh.shichang_cishu.setText(list.get(position).getHufushu().toString());
		String regExp = "^[1]([3|4|5|7|8][0-9]{1})[0-9]{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(list.get(position).getFabiaoren().toString());
		if ((m.find() == true)) {
		vh.shijian_mingzhi.setText(list.get(position).getFabiaoren().toString().substring(0, 3) +
		"****"+ list.get(position).getFabiaoren().toString().substring(7, list.get(position).getFabiaoren().toString().length()));
		}else{
		vh.shijian_mingzhi.setText(list.get(position).getFabiaoren().toString());
		}
		imageLoader.displayImage(list.get(position).getMovietupian(),
				vh.shichang_imageView1, options);// ��ʾͷ��
		return convertView;
	}

	class Viewholder {

		TextView shichang_name, shichang_neirong, shichang_shijian,
				shijian_mingzhi, shichang_cishu;
		ImageView shichang_imageView1;

	}

}
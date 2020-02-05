package com.parking.ui;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.parking.adapter.HuifuAdapter;
import com.parking.model.Movie;
import com.parking.model.Page;
import com.parking.smarthome.R;
import com.parking.util.ActivityBase;
import com.parking.util.ConnectWeb;
import com.parking.util.ConnectWeb1;
import com.parking.util.Constants;
import com.parking.util.DateUtil;
import com.parking.util.JsoupUtil;
import com.parking.util.ProDialog;


import com.viewflow.xlistviewfresh.XListView;
import com.viewflow.xlistviewfresh.XListView.IXListViewListener;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShichangxqActivity extends Activity implements
		IXListViewListener {
	private XListView youhuilist;

	private HuifuAdapter adapter;

	private ProDialog myDialog;
	private String ctype;
	private int count;

	private String pcount, name, url, shijian, id, title, content, comment;
	private EditText pinglun_comment;
	private Button pinglun_fasong;
	private JSONObject goodsList;// 声明List类型变量
	private String message;
	private String namehuifu;

	private View topView;
	private SharedPreferences sp;
	private String opuid;
	private int pageIndex = 1;
	private int pageSize = 20;
	private Page page;
	private ImageLoader imageLoader = ImageLoader.getInstance();// 得到图片加载器
	private DisplayImageOptions options; // 显示图像设置
	private LinearLayout pinglun;
//	private ProgressBar progressBar;
	

	
	Handler d = new Handler() {

		public void handleMessage(Message msg) {

			if (msg.what == 90) {
				// 更新界面
				try {

					message = goodsList.getString("status");
					pinglun_comment.setText(" ");
					if (message != null && message.equals("success")) {
						pinglun_comment.setText("");
						Toast.makeText(ShichangxqActivity.this, "发表成功",
								Toast.LENGTH_SHORT).show();
						
				//		hideSoftInputView();
						page.setPage(1);
						new MainTask().execute(Constants.DEF_TASK_TYPE.REFRESH);

					} else {
						Toast.makeText(ShichangxqActivity.this, "发表失败",
								Toast.LENGTH_SHORT).show();
					}

				} catch (JSONException e) {

					e.printStackTrace();
				}
			}

		};

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 全屏
		setContentView(R.layout.huifu);
		
		sp = getSharedPreferences("mrsoft", MODE_PRIVATE); // 获得Preferences
		opuid = sp.getString("editUid", "editUid");
		imageLoader.init(ImageLoaderConfiguration
				.createDefault(ShichangxqActivity.this));
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

		page = new Page();
		adapter = new HuifuAdapter(this);
		myDialog= new ProDialog(ShichangxqActivity.this,
				R.style.progressDialog);
		myDialog.show();
		topView = LayoutInflater.from(this).inflate(R.layout.shichangtop, null);
		ImageView nameImg = (ImageView) topView
				.findViewById(R.id.shichangxq_tu);

		TextView name1 = (TextView) topView
				.findViewById(R.id.shijianxq_mingzhi1);
		name1.setText("论坛详情");
		
		
		TextView shijian1 = (TextView) topView
				.findViewById(R.id.shijianxq_shijian);
		TextView biaoti = (TextView) topView.findViewById(R.id.shichangxq_name);
		TextView biaotineirong = (TextView) topView
				.findViewById(R.id.shichangxq_neirong);

		TextView shichangxq_timu = (TextView) findViewById(R.id.shichangxq_timu);

		pinglun_comment = (EditText) findViewById(R.id.pinglun_comment);
		pinglun = (LinearLayout) findViewById(R.id.pinglun);
		pinglun.setVisibility(View.GONE);
		RelativeLayout pinglun_fasong1 = (RelativeLayout) topView
				.findViewById(R.id.pinglun_comment);
		pinglun_fasong1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pinglun.setVisibility(View.VISIBLE);

			}
		});
		pinglun_fasong = (Button) findViewById(R.id.pinglun_fasong);

		pinglun_fasong.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				comment = pinglun_comment.getText().toString().trim();
				if (comment.equals("")) {
					Toast.makeText(ShichangxqActivity.this, "评论的内容不能够为空", 1)
							.show();
					return;
				}

				getGoodsList(comment,opuid,id);

			}
		});
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();

		pcount = bundle.getString("pcount");
		name = bundle.getString("name");
		url = bundle.getString("url");

		shijian = bundle.getString("shijian");
		id = bundle.getString("id");
		title = bundle.getString("title");
		content = bundle.getString("content");
		shijian = bundle.getString("shijian");
		shijian1.setText(shijian);
		biaotineirong.setText(content);
		 String regExp = "^[1]([3|4|5|7|8][0-9]{1})[0-9]{8}$";
			Pattern p = Pattern.compile(regExp);
			Matcher m = p.matcher(name);
			if ((m.find() == true)) {
		 name1.setText(name.substring(0, 3) +
		 "****"+ name.substring(7, name.length()));
			}else{
				name1.setText(name);
			}
		//name1.setText(name);
		shichangxq_timu.setText("停车论坛详情");
		imageLoader.displayImage(url, nameImg, options);// 显示头像
		biaoti.setText(title);

		LinearLayout huifu_fanhui = (LinearLayout) findViewById(R.id.huifu_fanhui);
		huifu_fanhui.setOnTouchListener(scollTouchListener);
		
		youhuilist = (XListView) findViewById(R.id.huifu_list);
		youhuilist.setAdapter(adapter);
		youhuilist.setPullRefreshEnable(true);// 开启下拉刷新
		youhuilist.setPullLoadEnable(true);// 开启上滑加载更多
		youhuilist.setXListViewListener(this);// 设置监听器
		youhuilist.addHeaderView(topView);
//		if (!isNetworkConnected(ShichangxqActivity.this)) {
//			showCustomToast("当前网络不可用，请连接网络后在重试!");
//		} else {
//		page.setPage(1);
//		new MainTask().execute(Constants.DEF_TASK_TYPE.REFRESH);
//	}
	}
	private void getGoodsList(final String comment, final String opuid,
			final String id) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				ConnectWeb1 cw = new ConnectWeb1();
				try {

					try {
						goodsList = cw.pinglun(
								URLDecoder.decode(comment, "utf-8"), opuid, id);
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

				try {
					Message msg = new Message();
					msg.what = 90;
					d.sendEmptyMessage(msg.what);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();

	}

	/* 点击Scoller空白区,软键盘消失 */
	private OnTouchListener scollTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {

			finish();
			return false;
		}

	};
	
	
	
	private class MainTask extends AsyncTask<String, Void, Integer> {

		@SuppressWarnings("null")
		@Override
		protected Integer doInBackground(String... params) {

			// 获得返回json字符串
			// String temp = HttpUtil.httpGet(URLUtil.getCommentListURL(
			// page.getCurrentPage(), flag));

			ConnectWeb1 cw = new ConnectWeb1();

			String temp = null;
			try {
				temp = cw.getshichanglist1(page.getCurrentPage(), id);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}// 获取商品列表

			if (temp == null) {
				return Constants.DEF_RESULT_CODE.ERROR;
			}

			List<Movie> list = JsoupUtil.getBlogCommentList3(temp,
					Integer.valueOf(page.getCurrentPage()), pageSize);

			if (list.size() == 0) {
				return Constants.DEF_RESULT_CODE.NO_DATA;
			}

			if (params[0].equals(Constants.DEF_TASK_TYPE.LOAD)) {
				adapter.addList(list);
				// list.addAll(list);
				return Constants.DEF_RESULT_CODE.LOAD;
			} else {
				adapter.setList(list);

				return Constants.DEF_RESULT_CODE.REFRESH;
			}

		}

		@Override
		protected void onPostExecute(Integer result) {

			if (result == Constants.DEF_RESULT_CODE.ERROR) {
				Toast.makeText(getApplicationContext(), "网络信号不佳",
						Toast.LENGTH_SHORT).show();
				youhuilist.stopRefresh(DateUtil.getDate());

				youhuilist.stopLoadMore();

			} else if (result == Constants.DEF_RESULT_CODE.NO_DATA) {
				Toast.makeText(getApplicationContext(), "无更多内容",
						Toast.LENGTH_SHORT).show();

				youhuilist.stopLoadMore();

				youhuilist.stopRefresh(DateUtil.getDate());

			} else if (result == Constants.DEF_RESULT_CODE.LOAD) {
				page.addPage();
				pageIndex++;
				adapter.notifyDataSetChanged();
				youhuilist.stopLoadMore();
			} else if (result == Constants.DEF_RESULT_CODE.REFRESH) {
				adapter.notifyDataSetChanged();
				youhuilist.stopRefresh(DateUtil.getDate());
				page.setPage(2);

			}
			myDialog.dismiss();
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {

			// TODO Auto-generated method stub

			super.onPreExecute();
			// progressDialog.show();

		}

	}

	@Override
	public void onLoadMore() {
		new MainTask().execute(Constants.DEF_TASK_TYPE.LOAD);
	}

	@Override
	public void onRefresh() {

		page.setPage(1);
		new MainTask().execute(Constants.DEF_TASK_TYPE.REFRESH);
	}

	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}
}

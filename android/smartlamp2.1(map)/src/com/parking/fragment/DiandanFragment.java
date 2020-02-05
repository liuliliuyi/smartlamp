package com.parking.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import com.parking.adapter.DingdanAdapter;
import com.parking.adapter.shichangAdapter;

import com.parking.model.Movie;
import com.parking.model.Order;
import com.parking.model.Orderlist;
import com.parking.model.Page;
import com.parking.smarthome.R;


import com.parking.ui.LoginActivity;

import com.parking.ui.ShichangxqActivity;

import com.parking.util.Common;
import com.parking.util.ConnectWeb;
import com.parking.util.ConnectWeb1;
import com.parking.util.Constants;
import com.parking.util.DateUtil;
import com.parking.util.HttpUtils;
import com.parking.util.HttpUtils.UploadImageCallBack;

import com.parking.util.JsonUtil;
import com.parking.util.JsoupUtil;
import com.parking.util.ProDialog;
import com.parking.util.UrlUtil;

import com.viewflow.xlistviewfresh.XListView;
import com.viewflow.xlistviewfresh.XListView.IXListViewListener;

import android.annotation.SuppressLint;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;

import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("NewApi")
public class DiandanFragment extends Fragment implements IXListViewListener {

	Activity context;
	private XListView youhuilist;
	private shichangAdapter adapter;
	private SharedPreferences sp;

	private final int ResultCode = 54135;

	private int pageIndex = 1;
	private int pageSize = 20;
	private Page page;
	private ProDialog myDialog;
	JSONObject goodsList = null;
	private int i;
	private File file;
	private String editUid;
	Handler d = new Handler() {

		public void handleMessage(Message msg) {

			if (msg.what == 90) {
				// 更新界面
				try {

					String message = goodsList.getString("status");

					if (message != null && "success".equals(message)) {
						JSONArray data = goodsList.getJSONArray("data");

						JSONObject goodsList1 = data.getJSONObject(0);
						String editUid = goodsList1.getString("userid");
						sp = context.getSharedPreferences("mrsoft", 0);
						Editor editor = sp.edit(); // 获得Editor
						editor.putString("editUid", editUid);
						editor.commit();
						if (i == 0) {
						// 就直接显示
						if (!isNetworkConnected(context)) {
							Toast.makeText(context, "网络未连接",
									Toast.LENGTH_SHORT).show();
						} else {
						myDialog = new ProDialog(context, R.style.progressDialog);
						myDialog.show();
						page.setPage(1);
						new MainTask().execute(Constants.DEF_TASK_TYPE.REFRESH);
					
						}
					i++;
						
						}
					
					}
				} catch (JSONException e) {

					e.printStackTrace();
				}
			}

		};

	};

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		context = getActivity();
		TextView toptitile = (TextView) context.findViewById(R.id.toptitile);
		toptitile.setText("停车论坛");
		i = 0;
		initview();
	}

	private void initview() {

		sp = context.getSharedPreferences("mrsoft", 0); // 获得Preferences

		page = new Page();
		adapter = new shichangAdapter(context);

		ImageView shichang_fatie = (ImageView) context
				.findViewById(R.id.shichang_fatie);
		shichang_fatie.setVisibility(View.VISIBLE);
		shichang_fatie.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

//				Intent intent = new Intent(context, FabiaohuatiAcitivity.class);
//				startActivityForResult(intent, ResultCode);

			}
		});
		youhuilist = (XListView) context.findViewById(R.id.shichang_yhlist);
		youhuilist.setAdapter(adapter);
		youhuilist.setPullRefreshEnable(true);// 开启下拉刷新
		youhuilist.setPullLoadEnable(true);// 开启上滑加载更多
		youhuilist.setXListViewListener(this);// 设置监听器
		youhuilist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent it = new Intent();// 创建Intent对象
				Bundle b = new Bundle();

				b.putString("id", (String) ((Movie) adapter
						.getItem(position - 1)).getId());
				b.putString("title", (String) ((Movie) adapter
						.getItem(position - 1)).getFabiaotumu());
				b.putString("content", (String) ((Movie) adapter
						.getItem(position - 1)).getNeirong());
				b.putString("shijian", (String) ((Movie) adapter
						.getItem(position - 1)).getShijian());
				b.putString("pcount", (String) ((Movie) adapter
						.getItem(position - 1)).getHufushu());
				b.putString("name", (String) ((Movie) adapter
						.getItem(position - 1)).getFabiaoren());
				b.putString("url", (String) ((Movie) adapter
						.getItem(position - 1)).getMovietupian());

				it.putExtras(b);
				it.setClass(context, ShichangxqActivity.class);
				startActivity(it);

			}
		});
		sp = context.getSharedPreferences("mrsoft", 0);

		
		editUid = sp.getString("editUid", "editUid");

		if ("editUid".equals(editUid)) {
			String shouji = sp.getString("shouji", "shouji");
			String password = sp.getString("editPwd", "editPwd");

			String image_info = shouji + "," + shouji + "," + password + ","
					+ "2" + "," + "参展商" + "," + "其他";
			String upload_path = Common.url + "reg.action?";
			HttpUtils.uploadMultipartEntity(upload_path, image_info, file,
					new UploadImageCallBack() {

						@Override
						public void getResult(JSONObject result) {

							try {
								// {"status":"nameandmobile"}
								String status = result.getString("status");

								if (status.equals("success")) {

									JSONArray json1 = result
											.getJSONArray("data");
									JSONObject json = json1.getJSONObject(0);
									editUid = json.getString("userid");
									sp = context.getSharedPreferences("mrsoft",
											0);
									Editor editor = sp.edit();
									editor.putString("editUid", editUid);
									editor.commit();

									if (!(file == null)) {
										file.delete();
									}
									if (i == 0) {

										if (!isNetworkConnected(context)) {
											Toast.makeText(context, "网络未连接",
													Toast.LENGTH_SHORT).show();
										} else {
											myDialog = new ProDialog(context,
													R.style.progressDialog);
											myDialog.show();
											page.setPage(1);
											new MainTask()
													.execute(Constants.DEF_TASK_TYPE.REFRESH);
										}

										i++;
									}
								} else {
									//在这里登录一下
									
									String username = sp.getString("shouji", "shouji");
									String password = sp.getString("editPwd", "editPwd");

									if (isNetworkAvailable()) {
										getGoodsList(username, password);

									} else {

										Toast.makeText(context, "当前网络不可用，请连接网络后在重试",
												Toast.LENGTH_SHORT).show();
									}
								}

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					});

		} else {
			// 就直接显示
			if (!isNetworkConnected(context)) {
				Toast.makeText(context, "网络未连接",
						Toast.LENGTH_SHORT).show();
			} else {
			myDialog = new ProDialog(context, R.style.progressDialog);
			myDialog.show();
			page.setPage(1);
			new MainTask().execute(Constants.DEF_TASK_TYPE.REFRESH);
		}
	}
		}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.shichang, null);
	}

	private void getGoodsList(final String editUid2, final String editPwd2) {
		
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				ConnectWeb1	cw = new ConnectWeb1();
				try {
					goodsList = cw.delu(editUid2, editPwd2);
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

	
	
	
	private class MainTask extends AsyncTask<String, Void, Integer> {

		protected Integer doInBackground(String... params) {

			// 获得返回json字符串
			// String temp = HttpUtil.httpGet(URLUtil.getCommentListURL(
			// page.getCurrentPage(), flag));

			ConnectWeb1 cw = new ConnectWeb1();

			String temp = null;
			try {
				temp = cw.getshichanglist(page.getCurrentPage());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}// 获取商品列表

			if (temp == null) {
				return Constants.DEF_RESULT_CODE.ERROR;
			}

			List<Movie> list = JsoupUtil.getBlogCommentList2(temp,
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
				Toast.makeText(context.getApplicationContext(), "网络信号不佳",
						Toast.LENGTH_SHORT).show();
				youhuilist.stopRefresh(DateUtil.getDate());

				youhuilist.stopLoadMore();

			} else if (result == Constants.DEF_RESULT_CODE.NO_DATA) {
				Toast.makeText(context.getApplicationContext(), "无更多内容",
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
			if (myDialog != null && myDialog.isShowing()) {
				myDialog.dismiss();
			}
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

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);

		if (requestCode == ResultCode && resultCode == ResultCode) {
			page.setPage(1);
			new MainTask().execute(Constants.DEF_TASK_TYPE.REFRESH);

		}
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager mgr = (ConnectivityManager) context
				.getApplicationContext().getSystemService(
						Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] info = mgr.getAllNetworkInfo();
		if (info != null) {
			for (int i = 0; i < info.length; i++) {
				if (info[i].getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
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

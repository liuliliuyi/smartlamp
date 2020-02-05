package com.parking.widget;

import com.parking.lib.DateUtils;
import com.parking.smarthome.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;



/**
 * 项目名称: 
 * 下午2:15:14 版本: v1.0 
 */
public class DateTimeSelectorDialogBuilder1 extends NiftyDialogBuilder implements
		android.view.View.OnClickListener {

	private Context context;
	private RelativeLayout rlCustomLayout;
	private DateSelectorWheelView1 dateWheelView;
	private FrameLayout flSecondeCustomLayout;
	private OnSaveListener1 saveListener;
	/**
	 * 默认方向标示
	 */
	private static int mOrientation = 1;
	private static DateTimeSelectorDialogBuilder1 instance;

	public interface OnSaveListener1 {
		abstract void onSaveSelectedDate1(String selectedDate);
	}

	public DateTimeSelectorDialogBuilder1(Context context) {
		super(context);
		this.context = context;
		initDialog();
	}

	public DateTimeSelectorDialogBuilder1(Context context, int theme) {
		super(context, theme);
		this.context = context;
		initDialog();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setBackgroundDrawableResource(R.drawable.edit_dialog_coner);
	}

	public static DateTimeSelectorDialogBuilder1 getInstance(Context context) {

		int ort = context.getResources().getConfiguration().orientation;
		if (mOrientation != ort) {
			mOrientation = ort;
			instance = null;
		}

		if (instance == null || ((Activity) context).isFinishing()) {
			synchronized (DateTimeSelectorDialogBuilder1.class) {
				if (instance == null) {
					instance = new DateTimeSelectorDialogBuilder1(context,
							R.style.dialog_untran);
				}
			}
		}
		return instance;

	}

	private void initDialog() {
		rlCustomLayout = (RelativeLayout) LayoutInflater.from(context).inflate(
				R.layout.date_time_selector_dialog_layout1, null);
		dateWheelView = (DateSelectorWheelView1) rlCustomLayout
				.findViewById(R.id.pdwv_date_time_selector_wheelView);
		dateWheelView.setTitleClick(this);
		flSecondeCustomLayout = (FrameLayout) rlCustomLayout
				.findViewById(R.id.fl_date_time_custom_layout);
		setDialogProperties();
	}

	private void setDialogProperties() {
		int width = DateUtils.getScreenWidth(context) * 3 / 4;
		this.withDialogWindows(width, LayoutParams.WRAP_CONTENT)
				.withTitleColor("#FFFFFF").withTitle("预订时间")
				.setDialogClick(this).withPreviousText("取消")
				.withPreviousTextColor("#3598da").withDuration(100)
				.setPreviousLayoutClick(this).withNextText("保存")
				.withMessageMiss(View.GONE).withNextTextColor("#3598da")
				.setNextLayoutClick(this)
				.setCustomView(rlCustomLayout, context);

	}

	/**
	 * 设置自定义布�?
	 * 
	 * @param view
	 * @param context
	 * @return
	 */
	public DateTimeSelectorDialogBuilder1 setSencondeCustomView(View view,
			Context context) {
		if (flSecondeCustomLayout.getChildCount() > 0) {
			flSecondeCustomLayout.removeAllViews();
		}
		flSecondeCustomLayout.addView(view);
		return this;
	}

	/**
	 * 设置自定义布�?
	 * 
	 * @param resId
	 * @param context
	 * @return
	 */
	public DateTimeSelectorDialogBuilder1 setSencondeCustomView(int resId,
			Context context) {
		View view = View.inflate(context, resId, null);
		if (flSecondeCustomLayout.getChildCount() > 0) {
			flSecondeCustomLayout.removeAllViews();
		}
		flSecondeCustomLayout.addView(view);
		return this;
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.rl_date_time_title:
			if (dateWheelView.getDateSelectorVisibility() == View.VISIBLE) {
				dateWheelView.setDateSelectorVisiblility(View.GONE);
			} else {
				dateWheelView.setDateSelectorVisiblility(View.VISIBLE);
			}
			break;
		case R.id.fl_dialog_title_previous:
			dismiss();
			break;
		case R.id.fl_dialog_title_next:
			if (null != saveListener) {
				saveListener.onSaveSelectedDate1(dateWheelView.getSelectedDate());
			}
			dismiss();
			break;
		}
	}

	/**
	 * 获取日期选择�?
	 * 
	 * @return
	 */
	public DateSelectorWheelView1 getDateWheelView() {
		return dateWheelView;
	}

	/**
	 * 设置保存监听
	 * 
	 * @param saveListener
	 */
	public void setOnSaveListener1(OnSaveListener1 saveListener) {
		this.saveListener = saveListener;
	}

	/**
	 * �?初显示时是否可以可见
	 * 
	 * @param visibility
	 */
	public void setWheelViewVisibility(int visibility) {
		dateWheelView.setDateSelectorVisiblility(visibility);
	}

	@Override
	public void dismiss() {
		super.dismiss();
		instance = null;
	}

}

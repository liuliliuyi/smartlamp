package com.parking.widget;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.parking.lib.DateUtils;
import com.parking.smarthome.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 项目名称: DateSelector 类名�?: DateSelectorWheelView 创建�?: xhl 创建时间: 2015-1-14
 * 下午5:07:34 版本: v1.0 类描�?:
 */
public class DateSelectorWheelView extends RelativeLayout implements
		OnWheelChangedListener {
	private final String flag = "PfpsDateWheelView";
	private RelativeLayout rlTitle;
	private LinearLayout llWheelViews;
	private TextView tvSubTitle;
	private TextView tvYear;
	private TextView tvMonth;
	private TextView tvDay;
	private WheelView wvYear;
	// private WheelView wvMonth;
	// private WheelView wvDay;
	private String[] years = new String[6];
	// private String[] months = new String[59];
	// private String[] tinyDays = new String[59];

	private StrericWheelAdapter yearsAdapter;

	// private StrericWheelAdapter monthsAdapter;

	public DateSelectorWheelView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initLayout(context);
	}

	public DateSelectorWheelView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initLayout(context);
	}

	public DateSelectorWheelView(Context context) {
		super(context);
		initLayout(context);
	}

	private void initLayout(Context context) {
		LayoutInflater.from(context).inflate(R.layout.dete_time_layout, this,
				true);
		rlTitle = (RelativeLayout) findViewById(R.id.rl_date_time_title);
		llWheelViews = (LinearLayout) findViewById(R.id.ll_wheel_views);
		tvSubTitle = (TextView) findViewById(R.id.tv_date_time_subtitle);
		tvYear = (TextView) findViewById(R.id.tv_date_time_year);
		tvMonth = (TextView) findViewById(R.id.tv_date_time_month);
		tvDay = (TextView) findViewById(R.id.tv_date_time_day);
		wvYear = (WheelView) findViewById(R.id.wv_date_of_year);
		// wvMonth = (WheelView) findViewById(R.id.wv_date_of_month);

		wvYear.addChangingListener(this);
		// wvMonth.addChangingListener(this);

		setData();
	}

	private void setData() {
		for (int i = 0; i < years.length; i++) {
			 if(i==0){
				 years[i] = "0" + (5) + "分钟"; 
			 }
             if(i==1){
            	 years[i] = (15) + "分钟"; 	 
             }
             if(i==2){
            	 years[i] = (30) + "分钟"; 	 
             }
             if(i==3){
            	 years[i] = (45) + "分钟"; 	 
             }
             if(i==4){
            	 years[i] = "0" + (1) + "小时";	 
             }
             if(i==5){
            	 years[i] = "0" + (2) + "小时";	 
             }
//			if (i == 0) {
//				// 5分钟到 15分钟到 30分钟到 45分钟到 1小时到 2小时
//				years[i] = "半" + "小时";
//			}
//			else if (i < 9&&i>0) {
//				years[i] = "0" + (0+i) + "小时";
//			}else if (i>=9&&i<=11) {
//				years[i] = (1+i) + "小时";
//			}

		}
		

		yearsAdapter = new StrericWheelAdapter(years);
		// monthsAdapter = new StrericWheelAdapter(months);
		wvYear.setAdapter(yearsAdapter);
		wvYear.setCurrentItem(00);
		wvYear.setCyclic(true);
		// wvMonth.setAdapter(monthsAdapter);
		// wvMonth.setCurrentItem(00);
		// wvMonth.setCyclic(true);

	}

	/**
	 * 获取选择的日期的�?
	 * 
	 * @return
	 */
	public String getSelectedDate() {
		return tvYear.getText().toString().trim() + "-"
				+ tvMonth.getText().toString().trim();
	}

	/**
	 * 设置标题的点击事�?
	 * 
	 * @param onClickListener
	 */
	public void setTitleClick(OnClickListener onClickListener) {
		rlTitle.setOnClickListener(onClickListener);
	}

	/**
	 * 设置日期选择器的日期转轮是否可见
	 * 
	 * @param visibility
	 */
	public void setDateSelectorVisiblility(int visibility) {
		llWheelViews.setVisibility(visibility);
	}

	public int getDateSelectorVisibility() {
		return llWheelViews.getVisibility();
	}

	// int currentMonth = 1;

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		String trim = null;
		switch (wheel.getId()) {
		case R.id.wv_date_of_year:
			trim = DateUtils.splitDateString(wvYear.getCurrentItemValue())
					.trim();
			tvYear.setText(trim);

			break;
		
		default:
			break;
		}

	}

}

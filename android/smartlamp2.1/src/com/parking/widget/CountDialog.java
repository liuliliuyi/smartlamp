package com.parking.widget;













import com.parking.smarthome.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.RelativeLayout.LayoutParams;

public class CountDialog extends Dialog {

	private Context context;

	public CountDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;

	}

	public CountDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guige1);
		this.setCanceledOnTouchOutside(true);
	}

}

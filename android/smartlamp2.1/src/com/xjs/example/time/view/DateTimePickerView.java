
package com.xjs.example.time.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.jovision.mobbroadcast.time.WheelMain;
import com.parking.smarthome.R;


public class DateTimePickerView extends PopupWindow {

    private View mContentView;
    private View timepickerview;
    public WheelMain wheelMain;
    private Button mSave, mCancel;

    public DateTimePickerView(Activity context, OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       
        mContentView = inflater.inflate(R.layout.dialog_time, null);
        timepickerview = (LinearLayout) mContentView.findViewById(R.id.timePicker1);
        wheelMain = new WheelMain(timepickerview, 0);
        mSave = (Button) mContentView.findViewById(R.id.btn_ok);
        mCancel = (Button) mContentView.findViewById(R.id.btn_ng);
        wheelMain.initDateTimePicker();

        mSave.setOnClickListener(itemsOnClick);
        mCancel.setOnClickListener(itemsOnClick);
        // 设置SelectPicPopupWindow的View
        this.setContentView(mContentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00);
        setBackgroundDrawable(dw);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        // this.setAnimationStyle(R.style.popupAnimation);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
    }

}

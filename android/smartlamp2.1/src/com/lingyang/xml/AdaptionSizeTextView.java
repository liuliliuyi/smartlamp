package com.lingyang.xml;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.TextView;

public class AdaptionSizeTextView extends TextView implements   OnGlobalLayoutListener {

	public AdaptionSizeTextView(Context context) {
	  
		this(context, null);
	}
	public AdaptionSizeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdaptionSizeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //第一步：给TextVIew添加布局改变监听，以便当调用setText方法时收到�?�知
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    public void onGlobalLayout() {
        //当外部调用setText(String text)方法时回�?
        int lineCount = getLineCount();//获取当前行数
        if (lineCount > 1) {//如果当前行数大于1�?
            float textSize = getTextSize();//获得的是px单位
            textSize--;//将size-1�?
            setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);//重新设置大小,该方法会立即触发onGlobalLayout()方法。这里相当于递归调用，直至文本行数小�?1行为止�??
        }
    }
}
 
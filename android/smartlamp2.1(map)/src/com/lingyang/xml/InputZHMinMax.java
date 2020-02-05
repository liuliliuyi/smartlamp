package com.lingyang.xml;
import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Toast;
public class InputZHMinMax implements InputFilter  {

	private int min, max;
	private Context context;
    public InputZHMinMax(int min, int max,Context context) {
        this.min = min;
        this.max = max;
        this.context = context;
    }
 
    public InputZHMinMax(String min, String max,Context context) {
        this.min = Integer.parseInt(min);
        this.max = Integer.parseInt(max);
        this.context = context;
    }
 
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {   
        try {
            int input = Integer.parseInt(dest.toString() + source.toString());
            if (isInRange(min, max, input))
                return null;
        } catch (NumberFormatException nfe) { }     
        return "";
    }
 
    private boolean isInRange(int a, int b, int c) {
    	if (c < a && c >b) {
    		Toast.makeText(context, "请输�?0~100以内的数", Toast.LENGTH_LONG).show();
		}
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }


}

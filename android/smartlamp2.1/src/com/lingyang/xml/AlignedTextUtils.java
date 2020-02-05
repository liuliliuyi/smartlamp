package com.lingyang.xml;
 
import android.text.TextUtils;
public class AlignedTextUtils {

	private static int n = 0;// 原Str拥有的字符个�?
	 							// 输出结果：出正生正年正月 */

	public static String formatStr6(String str) {
		if (TextUtils.isEmpty(str)) {
			return "";
		}
		n = str.length();
		if (n >= 6) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		for (int i = n-2 ; i > 0; i--) {
			sb.insert(i, "  ");
		}
		return sb.toString();

	}
	
	public static String formatStr4(String str) {
		if (TextUtils.isEmpty(str)) {
			return "";
		}
		n = str.length();
		if (n >= 6) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		for (int i = n ; i > 0; i--) {
			sb.insert(i, "  ");
		}
		return sb.toString();

	}


}

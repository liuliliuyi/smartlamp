package com.parking.util;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateUtil {
	public static String getDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM��dd�� HH:mm",
				Locale.CHINA);
		return sdf.format(new java.util.Date());
	}
}

package com.sys.pp.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class DateUtil {
	public static boolean validateDate(String yyyyMMDD) {
		Pattern pattern = Pattern.compile("[0-9]{4}/[0-9]{2}/[0-9]{2}");
		return pattern.matcher(yyyyMMDD).matches();
	}

	public static Date convertFromString(String strDate) {
		Date date;
		try {
			date = new SimpleDateFormat("yyyy/MM/dd").parse(strDate);
		} catch (Exception e) {
			return null;
		}
		return date;
	}

	public static long getDiffDayByDate(Date d1, Date d2) {
		long diffInMillies = Math.abs(d2.getTime() - d1.getTime());
		long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		return diff;
	}
}

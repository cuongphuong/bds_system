package com.sys.pp.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class DateUtil {
	public static final String YYYYMMDD_FORMAT = "yyyy/MM/dd";
	public static final String DDMMYYYY_FORMAT = "dd/MM/yyyy";
	public static final String YYYYMMDD_FORMAT_MINUS = "yyyy-MM-dd";

	public static boolean validateDate(String yyyyMMDD) {
		Pattern pattern = Pattern.compile("[0-9]{4}/[0-9]{2}/[0-9]{2}");
		return pattern.matcher(yyyyMMDD).matches();
	}

	public static Date convertFromString(String strDate) {
		Date date;
		try {
			date = new SimpleDateFormat(YYYYMMDD_FORMAT).parse(strDate);
		} catch (Exception e) {
			return null;
		}
		return date;
	}

	public static Date convertFromString(String strDate, String format) {
		Date date;
		try {
			date = new SimpleDateFormat(format).parse(strDate);
		} catch (Exception e) {
			return null;
		}
		return date;
	}

	public static String convertDDMMYYYYString(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(DDMMYYYY_FORMAT);
		String strDate = formatter.format(date);
		return strDate;
	}

	public static long getDiffDayByDate(Date d1, Date d2) {
		long diffInMillies = Math.abs(d2.getTime() - d1.getTime());
		long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		return diff;
	}

	public static String convertToString(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(YYYYMMDD_FORMAT_MINUS);
		String strDate = formatter.format(date);
		return strDate;
	}

	public static java.util.Date getMoveDay(int move) {
		java.util.Date date = new Date();
		return getMoveDay(move, date);
	}

	public static java.util.Date getMoveDay(int move, java.util.Date date) {
		long time = date.getTime();
		time += (long) ((long) move * 1000 * 60 * 60 * 24);
		java.util.Date rc = new java.util.Date(time);
		return rc;
	}

}

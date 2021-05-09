package com.sys.pp.util;

import java.util.regex.Pattern;

public class NumberUtils {
	private static Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		return pattern.matcher(strNum).matches();
	}

	public int _change(int x){
	    return -x;
	}
}

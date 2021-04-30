package com.sys.pp.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Locale;
import java.util.regex.Pattern;

public class StringUtils {
	private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
	private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

	public static final String EMPTY = "";

	public static String getMD5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			return convertByteToHex(messageDigest);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static String nullToEmpty(Object str) {
		if (null == str)
			return "";
		return String.valueOf(str);
	}

	public static String toSlug(String input) {
		String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
		String normalized = Normalizer.normalize(nowhitespace, Form.NFD);
		String slug = NONLATIN.matcher(normalized).replaceAll("");
		return slug.toLowerCase(Locale.ENGLISH);
	}

	public static String getNumberOfString(final CharSequence input) {
		final StringBuilder sb = new StringBuilder(input.length());
		for (int i = 0; i < input.length(); i++) {
			final char c = input.charAt(i);
			if (c > 47 && c < 58) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static boolean isNullOrEmpty(String str) {
		if (str == null || str.length() == 0) {
			return true;
		}
		return false;
	}

	public static boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}

	public static boolean isValidPhone(String phone) {
		Pattern pattern = Pattern.compile("[0-9]{10}");
		return pattern.matcher(phone).matches();
	}

	private static String convertByteToHex(byte[] data) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			sb.append(Integer.toString((data[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}

	public static boolean isYoutubeUrl(String youTubeURl) {
		boolean success;
		String pattern = "^(http(s)?:\\/\\/)?((w){3}.)?youtu(be|.be)?(\\.com)?\\/.+";
		if (!youTubeURl.isEmpty() && youTubeURl.matches(pattern)) {
			success = true;
		} else {
			// Not Valid youtube URL
			success = false;
		}
		return success;
	}
}

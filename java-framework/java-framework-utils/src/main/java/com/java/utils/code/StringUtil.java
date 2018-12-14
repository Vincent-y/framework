package com.java.utils.code;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {

	/**
	 * 字符串转ASCII
	 * @param value
	 * @return
	 */
	public static String string2Ascii(String value) {
		StringBuffer sbu = new StringBuffer();
		char[] chars = value.toCharArray();

		for (int i = 0; i < chars.length; i++) {
			if (i != chars.length - 1) {
				sbu.append((int) chars[i]).append("");
			} else {
				sbu.append((int) chars[i]);
			}
		}
		return sbu.toString().trim();
	}

	/**
	 * ascii转字符串
	 * 
	 * @param value
	 * @return
	 */
	public static String asciiToString(String value) {
		StringBuffer sbu = new StringBuffer();
		String[] chars = value.split(",");
		for (int i = 0; i < chars.length; i++) {
			sbu.append((char) Integer.parseInt(chars[i]));
		}
		return sbu.toString();
	}

	/**
	 * 转16进制
	 * @param s
	 * @return
	 */
	public static String toHexString(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}
		return str;
	}

	/**
	 * 字符串转ascii总和
	 * @param value
	 * @return
	 */
	public static int string2AsciiSum(String value) {
		StringBuffer sbu = new StringBuffer();
		char[] chars = value.toCharArray();

		for (int i = 0; i < chars.length; i++) {
			if (i != chars.length - 1) {
				sbu.append((int) chars[i]).append(",");
			} else {
				sbu.append((int) chars[i]);
			}
		}
		String[] tmp = sbu.toString().trim().split(",");
		int sum = 0;
		for (int i = 0; i < tmp.length; i++) {
			sum+=Integer.parseInt(tmp[i]);
		}
		return sum;
	}
	
	/**
	 * 根据规则分隔字符串
	 * @param value
	 * @param regex
	 * @return
	 */
	public static List<String> splitByRegex(String value,String regex){
		List<String> arrayList = new ArrayList<>();
		if (value == null) {
			return arrayList;
		}
		
		String[] result = value.split(regex);
		for (int i = 0; i < result.length; i++) {
			String string = result[i];
			if (string.equals("")) {
				continue;
			}
			arrayList.add(string);
		}
		
		return arrayList;
	}
	
	/**
	 * 按照,号分隔
	 * @param value
	 * @return
	 */
	public List<String> splitByComma(String value){
		return splitByRegex(value, ",");
	}
	
	/**
	 * 按照#号分隔
	 * @param value
	 * @return
	 */
	public List<String> splitBySharp(String value){
		return splitByRegex(value, "#");
	}
	
}

package com.java.validate;

/**
 * <p>校验输入
 * <p>@author DRAGON
 * <p>@date 2017年5月22日
 * <p>@version 1.0
 */
public class Validate {

	/**
	 * null or ""
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(str == null || "".equals(str)){
			return true;
		}
		return false;
	}
	
	/**
	 * null or ""
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		return !isEmpty(str);
	}
	
	/**
	 * null
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str){
		if(str == null){
			return true;
		}
		return false;
	}

	public static boolean isNull(Integer num){
		if(num == null){
			return true;
		}
		return false;
	}

	public static boolean isNull(Long num){
		if(num == null){
			return true;
		}
		return false;
	}

	public static boolean isNull(Float num){
		if(num == null){
			return true;
		}
		return false;
	}

	public static boolean isNull(Double num){
		if(num == null){
			return true;
		}
		return false;
	}

	/**
	 * null
	 * @param str
	 * @return
	 */
	public static boolean isNotNull(String str){
		return !isNull(str);
	}
	
}

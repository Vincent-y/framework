package com.java.jedis.util;

/**
 * <p>key序列化与反序列化
 * <p>@author DRAGON
 * <p>@date 2015年1月27日
 * <p>@version 1.0
 */
public class RedisKeyMaker {

	public static String makeKey(Class<?> clazz, long id) {
		String tableName = clazz.getSimpleName();
		StringBuilder sb = new StringBuilder();
		sb.append(tableName);
		sb.append('_');
		sb.append(id);
		return sb.toString();
	}
	
	public static byte[] makeKey(String key) {
		return key.getBytes();
	}
	
}

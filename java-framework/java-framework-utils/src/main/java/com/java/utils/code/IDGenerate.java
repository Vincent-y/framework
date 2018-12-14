package com.java.utils.code;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * <p>唯一ID生成
 * <p>@author dragon
 * <p>2015年8月24日
 * <p>@version 1.0
 */
public class IDGenerate {

	/**
	 * UUID
	 * @return
	 */
	public static String UUID(){
		return UUID.randomUUID().toString().trim().replace("-", "");
	}

	/**
	 * 獲取時間戳
	 * @return
	 */
	public static String TimeStamp(){
		return new Date().getTime()+"";
	}
	
	/**
	 * UUID+时间戳
	 * @return
	 */
	public static String UUIDWithTimeStamp(){
		return UUID.randomUUID().toString().trim().replace("-", "")+new Date().getTime();
	}
	
	/**
	 * 根据GUID获取16位的唯一字符串  
	 * @return
	 */
	public static String GuidTo16String()  {  
	    long i = 1;  
	    for (byte b:UUID.randomUUID().toString().getBytes()) {
	    	 i *= ((int)b + 1);  
		}
	    return String.format("%x",i - new Date().getTime());  
	} 
	
	/**
	 * 随机生成字母
	 * @param len
	 * @return
	 */
	public static String getRandomLeter(int len){
		String result = "";
        String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";   
        char[] c = s.toCharArray();   
        Random random = new Random();   
        for( int i = 0; i <len; i ++) {   
        	result += c[random.nextInt(c.length)];   
        }   
        return result;
	}

}

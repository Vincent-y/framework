package com.java.utils.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * <p>检测网络
 * <p>@author DRAGON
 * <p>日期 2014年12月18日
 * <p>@version 1.0
 */
public class NetCheck {

	/**
	 * 网络检测
	 * @param host ---输入一个可访问网络主机名
	 * @return
	 */
	public static boolean checkNet(String host){
        URL url = null;  
        try {  
            url = new URL(host);  
            try {  
                InputStream in = url.openStream();  
                in.close();  
                return true;  
            } catch (IOException e) {  
                return false;
            }  
        } catch (MalformedURLException e) {  
            return false;
        } 
	}
	
	
}

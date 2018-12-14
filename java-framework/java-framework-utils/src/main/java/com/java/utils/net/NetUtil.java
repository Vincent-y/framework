package com.java.utils.net;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

/**
 * <p>检测端口
 * <p>@author DRAGON
 * <p>@date 2015年5月28日
 * <p>@version 1.0
 */
public class NetUtil {
	
	/**
	 * true:表示已经使用
	 * @param host
	 * @param port
	 */
	public static boolean isLoclePortUsing(String host, int port) {
		boolean flag = true;
		try {
			flag = isPortUsing(host, port);
		} catch (Exception e) {
			return false;
		}
		return flag;
	}

	/***
	 * true:表示已经启用
	 * @param host
	 * @param port
	 * @throws UnknownHostException
	 */
	@SuppressWarnings("resource")
	public static boolean isPortUsing(String host, int port)throws UnknownHostException {
		boolean flag = false;
		InetAddress theAddress = InetAddress.getByName(host);
		try {
			new Socket(theAddress, port);
			flag = true;
		} catch (IOException e) {
			return false;
		}
		return flag;
	}
	
	/**
	 * 获取IP地址
	 * @return
	 */
	public static String getIPAddress() {
		Enumeration<NetworkInterface> allNetInterfaces = null;
		try {
			allNetInterfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		String address = "";
		InetAddress ip = null;
		while (allNetInterfaces.hasMoreElements()) {
			NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
			String netName = netInterface.getName();
			//System.out.println(netInterface.getName());
			Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
			while (addresses.hasMoreElements()) {
				ip = (InetAddress) addresses.nextElement();
				if (ip != null && ip instanceof Inet4Address) {
					if(netName.indexOf("en")!= -1){
						address = ip.getHostAddress().trim();
					}
					//System.out.println("address:" + address);
				}
			}
		}
		return address;
	}
	
	
	
}

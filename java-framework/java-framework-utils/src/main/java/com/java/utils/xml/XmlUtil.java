package com.java.utils.xml;

import com.thoughtworks.xstream.XStream;
import org.omg.IOP.Encoding;

import java.lang.reflect.Field;

/**
 * <p>XML工具类
 * <p>@author DRAGON
 * <p>@date 2015年1月23日
 * <p>@version 1.0
 */
public class XmlUtil {

	/**
	 * object => xml
	 * @param obj
	 * @param encoding
	 * @return
	 */
	public static String Object2Xml(Object obj, String encoding) {
		XStream xs = new XStream();
		xs.processAnnotations(obj.getClass());
		setAnnotations(xs, obj.getClass());
		String xmlStr = xs.toXML(obj);

		if (null == encoding) {
			return xmlStr;
		}
		String xmlHead = "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>\n";
		return xmlHead + xmlStr;
	}
	
	/**
	 * object => xml
	 * @param obj
	 * @param encoding
	 * @return
	 */
	public static String Object2Xml(Object obj, Encoding encoding) {
		XStream xs = new XStream();
		xs.processAnnotations(obj.getClass());
		setAnnotations(xs, obj.getClass());
		String xmlStr = xs.toXML(obj);
		
		if (null == encoding) {
			return xmlStr;
		}
		
		String xmlHead = "<?xml version=\"1.0\" encoding=\"" + encoding.toString() + "\"?>\n";
		return xmlHead + xmlStr;
	}

	/**
	 * xml=>object
	 * @param xml
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T Xml2Object(String xml, Class<T> cls) {
		XStream xs = new XStream();
		xs.processAnnotations(cls);
		setAnnotations(xs, cls);
		T object = (T) xs.fromXML(xml);
		return object;
	}

	/**
	 * 注解设置
	 * @param xs
	 * @param cls
	 */
	private static <T> void setAnnotations(XStream xs, Class<T> cls) {
		Field f[] = cls.getDeclaredFields();
		for (int i = 0; i < f.length; i++) {
			Field field = f[i];
			xs.processAnnotations(field.getType());
		}
	}

}
